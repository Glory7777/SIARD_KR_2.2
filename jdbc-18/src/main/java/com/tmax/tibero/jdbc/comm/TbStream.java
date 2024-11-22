package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.data.ConnectionInfo;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.driver.TbKeepAlive;
import com.tmax.tibero.jdbc.driver.TbResultSetBase;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbMsgFactory;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TbStream {
  private Socket socket;
  
  private TbConnection conn;
  
  private InputStream input;
  
  private OutputStream output;
  
  private String host;
  
  private int port;
  
  private int tduSize = 0;
  
  private int msgType;
  
  private int msgBodySize = 0;
  
  private StreamBuffer readBuf;
  
  private StreamBuffer writeBuf;
  
  private TbStreamDataReader reader;
  
  private TbStreamDataWriter writer;
  
  private StreamBuffer dplBuf;
  
  private TbStreamDataWriter dplWriter;
  
  private boolean useSelfKeepAlive;
  
  private long selfKeepIdle;
  
  private long selfKeepInterval;
  
  private int selfKeepCount;
  
  private TbKeepAlive keepAlive;
  
  public TbStream(TbConnection paramTbConnection, Socket paramSocket, DataTypeConverter paramDataTypeConverter, ConnectionInfo paramConnectionInfo) throws SQLException {
    try {
      this.conn = paramTbConnection;
      initTCP(paramSocket, paramDataTypeConverter, paramConnectionInfo);
    } catch (ConnectException connectException) {
      throw TbError.newSQLException(-90401, connectException.getMessage());
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90400, iOException.getMessage());
    } 
  }
  
  public TbStream(TbConnection paramTbConnection, String paramString, int paramInt, DataTypeConverter paramDataTypeConverter, ConnectionInfo paramConnectionInfo) throws SQLException {
    try {
      this.conn = paramTbConnection;
      this.host = paramString;
      this.port = paramInt;
      if (paramConnectionInfo.getNetworkProtocol().equalsIgnoreCase("TCPS")) {
        initTCPS(paramDataTypeConverter, paramConnectionInfo);
      } else {
        Socket socket = new Socket();
        InetSocketAddress inetSocketAddress = (this.host != null) ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(InetAddress.getByName(null), paramInt);
        socket.connect(inetSocketAddress, paramConnectionInfo.getLoginTimeout());
        initTCP(socket, paramDataTypeConverter, paramConnectionInfo);
      } 
    } catch (ConnectException connectException) {
      throw TbError.newSQLException(-90401, connectException.getMessage());
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90400, iOException.getMessage());
    } 
  }
  
  public void abort() throws SQLException {
    try {
      if (this.socket != null)
        this.socket.close(); 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90409, iOException.getMessage());
    } 
  }
  
  public void close() throws SQLException {
    try {
      reset();
      IOException iOException = null;
      try {
        if (this.input != null)
          this.input.close(); 
      } catch (IOException iOException1) {
        iOException = iOException1;
      } 
      try {
        if (this.output != null)
          this.output.close(); 
      } catch (IOException iOException1) {
        iOException = iOException1;
      } 
      try {
        if (this.socket != null && !this.conn.info.isInternal())
          this.socket.close(); 
      } catch (IOException iOException1) {
        iOException = iOException1;
      } 
      if (iOException != null)
        throw iOException; 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90409, iOException.getMessage());
    } 
  }
  
  public TbStreamDataWriter createDirPathWriter(int paramInt) {
    if (this.dplBuf == null) {
      this.dplBuf = new StreamBuffer(paramInt);
    } else if (paramInt != this.dplBuf.getSize()) {
      this.dplBuf.resize(paramInt);
    } 
    if (this.dplWriter == null)
      this.dplWriter = new TbStreamDataWriter(this.dplBuf); 
    return this.dplWriter;
  }
  
  public void flush() throws SQLException {
    flushInternal(this.writeBuf);
  }
  
  public void flush(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {
    synchronized (this.writeBuf) {
      flushInternal(paramTbStreamDataWriter.getStreamBuf());
    } 
  }
  
  private void flushInternal(StreamBuffer paramStreamBuffer) throws SQLException {
    try {
      if (paramStreamBuffer.getCurDataSize() > 0)
        if (this.useSelfKeepAlive) {
          try {
            this.keepAlive = TbKeepAlive.register(this.socket, this.selfKeepIdle, this.selfKeepInterval, this.selfKeepCount, this.conn.getMthrPid(), this.conn.toString());
            this.output.write(paramStreamBuffer.getRawBytes(), 0, paramStreamBuffer.getCurDataSize());
            this.output.flush();
          } finally {
            if (this.keepAlive != null) {
              TbKeepAlive.unregister(this.keepAlive);
              this.keepAlive = null;
            } 
          } 
        } else {
          this.output.write(paramStreamBuffer.getRawBytes(), 0, paramStreamBuffer.getCurDataSize());
          this.output.flush();
        }  
    } catch (IOException iOException) {
      SQLException sQLException = TbError.newSQLException(-90406, iOException.getMessage());
      this.conn.reconnect(this.conn.isMiddleOfFailover());
      if (this.conn.isClosed()) {
        if (this.conn.getFOActiveRSetList() != null)
          for (TbResultSetBase tbResultSetBase : this.conn.getFOActiveRSetList())
            tbResultSetBase.setFOECode(-90406);  
        throw sQLException;
      } 
      if (this.conn.getFOActiveRSetList() != null)
        for (TbResultSetBase tbResultSetBase : this.conn.getFOActiveRSetList())
          tbResultSetBase.setFOECode(-90700);  
      throw TbError.newSQLException(-90700, sQLException);
    } 
  }
  
  public int getMsgType() {
    return this.msgType;
  }
  
  public TbStreamDataWriter getMsgWriter() {
    return this.writer;
  }
  
  public StreamBuffer getReadStreamBuffer() {
    return this.readBuf;
  }
  
  public StreamBuffer getWriteStreamBuffer() {
    return this.writeBuf;
  }
  
  public void handshakeSSL() throws SQLException {
    try {
      ((SSLSocket)this.socket).setUseClientMode(true);
      ((SSLSocket)this.socket).setEnableSessionCreation(true);
      ((SSLSocket)this.socket).startHandshake();
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90400, iOException.getMessage());
    } 
  }
  
  private void initTCP(Socket paramSocket, DataTypeConverter paramDataTypeConverter, ConnectionInfo paramConnectionInfo) throws IOException {
    this.socket = paramSocket;
    this.socket.setTcpNoDelay(true);
    this.socket.setKeepAlive(paramConnectionInfo.isFailoverSessionEnabled());
    this.socket.setSoTimeout(paramConnectionInfo.getLoginTimeout());
    this.input = paramSocket.getInputStream();
    this.output = paramSocket.getOutputStream();
    this.tduSize = paramConnectionInfo.getTDU();
    this.readBuf = new StreamBuffer(this.tduSize);
    this.writeBuf = new StreamBuffer(this.tduSize);
    this.reader = new TbStreamDataReader(this.readBuf, paramDataTypeConverter);
    this.writer = new TbStreamDataWriter(this.writeBuf, paramDataTypeConverter);
    this.useSelfKeepAlive = paramConnectionInfo.useSelfKeepAlive();
    this.selfKeepIdle = (paramConnectionInfo.getSelfKeepIdle() * 1000);
    this.selfKeepInterval = (paramConnectionInfo.getSelfKeepInterval() * 1000);
    this.selfKeepCount = paramConnectionInfo.getSelfKeepCount();
  }
  
  private void initTCPS(DataTypeConverter paramDataTypeConverter, ConnectionInfo paramConnectionInfo) throws IOException {
    try {
      TrustManager[] arrayOfTrustManager = { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[0];
            }
            
            public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) {}
            
            public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) {}
          } };
      SSLContext sSLContext = SSLContext.getInstance("TLSv1");
      sSLContext.init(null, arrayOfTrustManager, null);
      SSLSocketFactory sSLSocketFactory = sSLContext.getSocketFactory();
      this.port += 2;
      this.socket = sSLSocketFactory.createSocket(this.host, this.port);
      String[] arrayOfString = { "TLSv1" };
      ((SSLSocket)this.socket).setEnabledProtocols(arrayOfString);
      ((SSLSocket)this.socket).startHandshake();
    } catch (Exception exception) {
      throw new IOException(exception.getMessage());
    } 
    this.socket.setTcpNoDelay(true);
    this.socket.setKeepAlive(paramConnectionInfo.isFailoverSessionEnabled());
    this.socket.setSoTimeout(paramConnectionInfo.getLoginTimeout());
    this.input = this.socket.getInputStream();
    this.output = this.socket.getOutputStream();
    this.tduSize = paramConnectionInfo.getTDU();
    this.readBuf = new StreamBuffer(this.tduSize);
    this.writeBuf = new StreamBuffer(this.tduSize);
    this.reader = new TbStreamDataReader(this.readBuf, paramDataTypeConverter);
    this.writer = new TbStreamDataWriter(this.writeBuf, paramDataTypeConverter);
    this.useSelfKeepAlive = paramConnectionInfo.useSelfKeepAlive();
    this.useSelfKeepAlive = paramConnectionInfo.useSelfKeepAlive();
    this.selfKeepIdle = (paramConnectionInfo.getSelfKeepIdle() * 1000);
    this.selfKeepInterval = (paramConnectionInfo.getSelfKeepInterval() * 1000);
    this.selfKeepCount = paramConnectionInfo.getSelfKeepCount();
  }
  
  public void readChunkData(byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (this.readBuf) {
      if (readNBytes(paramArrayOfbyte, 0, paramInt) != paramInt)
        throw TbError.newSQLException(-590729); 
      this.msgBodySize = 0;
    } 
  }
  
  public TbMsg readMsg() throws SQLException {
    TbMsg tbMsg = null;
    synchronized (this.readBuf) {
      byte[] arrayOfByte = new byte[16];
      readNBytes(arrayOfByte, 0, 16);
      this.msgType = TbCommon.bytes2Int(arrayOfByte, 0, 4);
      this.msgBodySize = TbCommon.bytes2Int(arrayOfByte, 4, 4);
      tbMsg = TbMsgFactory.createMessage(this.msgType);
      tbMsg.setTsn(TbCommon.bytes2Long(arrayOfByte, 8, 8));
      if (this.msgBodySize <= 0)
        return tbMsg; 
      this.readBuf.init(this.msgBodySize);
      if (readNBytes(this.readBuf.getRawBytes(), 0, this.msgBodySize) != this.msgBodySize)
        throw TbError.newSQLException(-590729); 
      this.readBuf.setCurDataSize(this.msgBodySize);
      this.reader.initialize(this.readBuf);
      this.msgBodySize = 0;
      tbMsg.deserialize(this.reader);
    } 
    return tbMsg;
  }
  
  private int readNBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    try {
      long l = 0L;
      int i = 0;
      if (this.conn.isActivatedTimer())
        l = System.currentTimeMillis(); 
      if (this.useSelfKeepAlive) {
        int j = 0;
        while (i != paramInt2) {
          try {
            this.keepAlive = TbKeepAlive.register(this.socket, this.selfKeepIdle, this.selfKeepInterval, this.selfKeepCount, this.conn.getMthrPid(), this.conn.toString());
            j = this.input.read(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
          } finally {
            if (this.keepAlive != null) {
              TbKeepAlive.unregister(this.keepAlive);
              this.keepAlive = null;
            } 
          } 
          if (j == -1)
            throw new IOException("End Of Stream"); 
          i += j;
        } 
      } else {
        int j = 0;
        while (i != paramInt2) {
          j = this.input.read(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
          if (j == -1)
            throw new IOException("End Of Stream"); 
          i += j;
        } 
      } 
      if (this.conn.isActivatedTimer())
        this.conn.addWaitingTime(System.currentTimeMillis() - l); 
      return i;
    } catch (IOException iOException) {
      SQLException sQLException = TbError.newSQLException(-90405, iOException.getMessage());
      this.conn.reconnect(this.conn.isMiddleOfFailover());
      if (this.conn.isClosed()) {
        if (this.conn.getFOActiveRSetList() != null)
          for (TbResultSetBase tbResultSetBase : this.conn.getFOActiveRSetList())
            tbResultSetBase.setFOECode(-90405);  
        throw sQLException;
      } 
      if (this.conn.getFOActiveRSetList() != null)
        for (TbResultSetBase tbResultSetBase : this.conn.getFOActiveRSetList())
          tbResultSetBase.setFOECode(-90700);  
      throw TbError.newSQLException(-90700, sQLException);
    } 
  }
  
  public void reset() {
    this.tduSize = 0;
    this.input = null;
    this.output = null;
    this.reader.reset();
    this.writer.reset();
  }
  
  public void setSoTimeout(int paramInt) {
    try {
      this.socket.setSoTimeout(paramInt);
    } catch (Exception exception) {}
  }
  
  public int getSoTimeout() throws SQLException {
    try {
      return this.socket.getSoTimeout();
    } catch (Exception exception) {
      throw new SQLException(exception.getMessage());
    } 
  }
  
  public void setSelfKeepAliveEnabled(boolean paramBoolean) {
    this.useSelfKeepAlive = paramBoolean;
  }
  
  public void startWritingPacketData() {
    this.writer.initialize(this.writeBuf);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\comm\TbStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
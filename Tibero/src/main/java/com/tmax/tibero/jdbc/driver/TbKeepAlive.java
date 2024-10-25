package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.util.TbCommon;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TbKeepAlive {
  private static final int FIELD_OFFSET__MSG_TYPE = 0;
  
  private static final int FIELD_LEN__MSG_TYPE = 4;
  
  private static final int FIELD_OFFSET__BODY_SIZE = 4;
  
  private static final int FIELD_LEN__BODY_SIZE = 4;
  
  private static final int FIELD_OFFSET_FROM_END__MTHR_PID = 16;
  
  private static final int FIELD_LEN__MTHR_PID = 4;
  
  private static final int BUFFER_SIZE = 256;
  
  public static final int STATE_UNKNOWN = 0;
  
  public static final int STATE_SVR_IS_ALIVE = 1;
  
  public static final int STATE_REPLY_NOT_FINISHED = 2;
  
  public static final int STATE_NO_REPLY = 3;
  
  public static final int STATE_REPLY_FROM_WRONG_SVR = 4;
  
  public static final int STATE_CHECK_FAILED = 5;
  
  private Socket socket;
  
  private int svrIntanceIdFromMthrPid;
  
  private SocketAddress address;
  
  private SocketChannel channel;
  
  private int replyMsgBodySize;
  
  private int tryCount;
  
  private long baseTime;
  
  private ByteBuffer buf;
  
  private int maxRetryCount;
  
  private long idleMillis;
  
  private long intervalMillis;
  
  private String connNameForLog;
  
  private boolean registered = false;
  
  private static final TbKeepAlivePollingThread pollingThread = new TbKeepAlivePollingThread();
  
  public static TbKeepAlive register(Socket paramSocket, long paramLong1, long paramLong2, int paramInt1, int paramInt2, String paramString) {
    TbKeepAlive tbKeepAlive = new TbKeepAlive(paramSocket, paramLong1, paramLong2, paramInt1, paramInt2, paramString);
    pollingThread.add(tbKeepAlive);
    tbKeepAlive.registered = true;
    return tbKeepAlive;
  }
  
  public static void register(TbKeepAlive paramTbKeepAlive) {
    pollingThread.add(paramTbKeepAlive);
    paramTbKeepAlive.registered = true;
  }
  
  public static void unregister(TbKeepAlive paramTbKeepAlive) {
    pollingThread.remove(paramTbKeepAlive);
    paramTbKeepAlive.registered = false;
  }
  
  TbKeepAlive(Socket paramSocket, long paramLong1, long paramLong2, int paramInt1, int paramInt2, String paramString) {
    this.socket = paramSocket;
    this.address = paramSocket.getRemoteSocketAddress();
    this.baseTime = System.currentTimeMillis();
    this.channel = null;
    this.replyMsgBodySize = -1;
    this.tryCount = 0;
    this.idleMillis = paramLong1;
    this.intervalMillis = paramLong2;
    this.maxRetryCount = paramInt1;
    this.svrIntanceIdFromMthrPid = paramInt2 >> 16;
    this.connNameForLog = paramString;
    this.buf = ByteBuffer.allocate(256);
  }
  
  void resetBaseTime() {
    this.baseTime = System.currentTimeMillis();
    this.tryCount = 0;
  }
  
  synchronized SocketChannel getChannel() {
    return this.registered ? this.channel : null;
  }
  
  synchronized void closeCheckChannel(int paramInt) {
    if (this.channel != null) {
      try {
        this.channel.close();
      } catch (IOException iOException) {}
      this.channel = null;
    } 
  }
  
  void closeOrgConnSocket(int paramInt) {
    try {
      this.socket.close();
    } catch (Exception exception) {}
  }
  
  synchronized void tryConnect() throws IOException {
    if (this.registered && !this.socket.isClosed()) {
      this.tryCount++;
      this.buf.position(0);
      this.replyMsgBodySize = -1;
      this.baseTime = System.currentTimeMillis();
      this.channel = SocketChannel.open();
      this.channel.configureBlocking(false);
      this.channel.connect(this.address);
    } 
  }
  
  synchronized int checkReply() throws IOException {
    if (!this.registered)
      return 1; 
    this.channel.read(this.buf);
    if (this.replyMsgBodySize < 0) {
      if (this.buf.position() < 16)
        return 2; 
      if (0 != TbCommon.bytes2Int(this.buf.array(), 0, 4))
        return 4; 
      this.replyMsgBodySize = TbCommon.bytes2Int(this.buf.array(), 4, 4);
    } 
    if (this.replyMsgBodySize > 0 && this.buf.position() >= 16 + this.replyMsgBodySize - 16 + 4) {
      int i = TbCommon.bytes2Int(this.buf.array(), 16 + this.replyMsgBodySize - 16, 4) >> 16;
      return (this.svrIntanceIdFromMthrPid == i) ? 1 : 4;
    } 
    return 2;
  }
  
  long getBaseTime() {
    return this.baseTime;
  }
  
  int getTryCount() {
    return this.tryCount;
  }
  
  long getIdleMillis() {
    return this.idleMillis;
  }
  
  long getIntervalMillis() {
    return this.intervalMillis;
  }
  
  int getMaxRetryCount() {
    return this.maxRetryCount;
  }
  
  String getTargetAddressWithSvrInstId() {
    return String.valueOf(this.address) + ":" + this.svrIntanceIdFromMthrPid;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbKeepAlive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
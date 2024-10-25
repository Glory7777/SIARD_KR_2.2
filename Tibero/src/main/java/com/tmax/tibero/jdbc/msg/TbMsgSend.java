package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStream;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import java.sql.SQLException;

public class TbMsgSend {
  private static void NLS_PARAM(TbStreamDataWriter paramTbStreamDataWriter, TbNlsParam paramTbNlsParam) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbNlsParam.nlsParamId, 4);
    if (paramTbNlsParam.nlsParamVal != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbNlsParam.nlsParamVal);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void AUTH_REQ(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, String paramString8) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(1, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString4 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString4);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString5 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString5);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString6 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString6);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString7 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString7);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString8 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString8);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void PREPARE(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(3, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void COLUMN_DESC(TbStreamDataWriter paramTbStreamDataWriter, TbColumnDesc paramTbColumnDesc) throws SQLException {
    if (paramTbColumnDesc.name != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbColumnDesc.name);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
    paramTbStreamDataWriter.writeInt(paramTbColumnDesc.dataType, 4);
    paramTbStreamDataWriter.writeInt(paramTbColumnDesc.precision, 4);
    paramTbStreamDataWriter.writeInt(paramTbColumnDesc.scale, 4);
    paramTbStreamDataWriter.writeInt(paramTbColumnDesc.etcMeta, 4);
    paramTbStreamDataWriter.writeInt(paramTbColumnDesc.maxSize, 4);
  }
  
  private static void BIND_PARAM_DESC(TbStreamDataWriter paramTbStreamDataWriter, TbBindParamDesc paramTbBindParamDesc) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbBindParamDesc.type, 4);
    if (paramTbBindParamDesc.placeHolderName != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbBindParamDesc.placeHolderName);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  private static void BINDPARAM(TbStreamDataWriter paramTbStreamDataWriter, TbBindparam paramTbBindparam) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbBindparam.flag, 4);
    paramTbStreamDataWriter.writeRpcolData(paramTbBindparam.dataValue, paramTbBindparam.dataValueLen);
  }
  
  public static void EXECUTE(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, TbBindparam[] paramArrayOfTbBindparam) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(5, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeBytes(paramArrayOfbyte);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbBindparam != null)
        for (byte b = 0; b < paramInt3; b++)
          BINDPARAM(tbStreamDataWriter, paramArrayOfTbBindparam[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void EXECDIR(TbStream paramTbStream, int paramInt1, int paramInt2, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(6, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void PREPARE_EXECUTE(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, int paramInt3, TbBindparam[] paramArrayOfTbBindparam) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(7, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbBindparam != null)
        for (byte b = 0; b < paramInt3; b++)
          BINDPARAM(tbStreamDataWriter, paramArrayOfTbBindparam[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void FETCH(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(18, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void FETCH_PIVOT(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(20, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void CLOSE_CSR(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(22, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void COMMIT(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(23, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void ROLLBACK(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(24, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SAVEPT(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(25, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SET_ISL_LVL(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(26, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OPEN_SESS(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(27, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void CLOSE_SESS(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(28, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void CANCEL(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(29, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void BATCH_UPDATE(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TbBindparam[] paramArrayOfTbBindparam) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(30, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeBytes(paramArrayOfbyte);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      if (paramArrayOfTbBindparam != null)
        for (byte b = 0; b < paramInt5; b++)
          BINDPARAM(tbStreamDataWriter, paramArrayOfTbBindparam[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void PREPARE_BATCHUPDATE(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TbBindparam[] paramArrayOfTbBindparam) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(31, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      if (paramArrayOfTbBindparam != null)
        for (byte b = 0; b < paramInt5; b++)
          BINDPARAM(tbStreamDataWriter, paramArrayOfTbBindparam[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_LENGTH(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(33, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt != 0) {
        tbStreamDataWriter.writeInt(paramInt, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_READ(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(35, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_TRUNC(TbStream paramTbStream, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(37, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_INSTR(TbStream paramTbStream, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(39, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_WRITE(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte1, int paramInt4, byte[] paramArrayOfbyte2, int paramInt5) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(41, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte1, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      if (paramInt5 != 0) {
        tbStreamDataWriter.writeInt(paramInt5, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte2, 0, paramInt5);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_CREATE_TEMP(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(43, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_DELETE_TEMP(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(45, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt != 0) {
        tbStreamDataWriter.writeInt(paramInt, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_INLOB(TbStream paramTbStream, int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, int paramInt3, byte[] paramArrayOfbyte2, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(46, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte1, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte2, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_OPEN(TbStream paramTbStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(48, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramInt2 != 0) {
        tbStreamDataWriter.writeInt(paramInt2, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt2);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LOB_CLOSE(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(50, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt != 0) {
        tbStreamDataWriter.writeInt(paramInt, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void LONG_READ(TbStream paramTbStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(52, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramInt2 != 0) {
        tbStreamDataWriter.writeInt(paramInt2, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt2);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void PUT_DATA(TbStream paramTbStream, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(54, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void COL_NAME_LIST(TbStreamDataWriter paramTbStreamDataWriter, TbColNameList paramTbColNameList) throws SQLException {
    if (paramTbColNameList.colName != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbColNameList.colName);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void DPL_PREPARE(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, int paramInt2, TbColNameList[] paramArrayOfTbColNameList) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(55, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramArrayOfTbColNameList != null)
        for (byte b = 0; b < paramInt2; b++)
          COL_NAME_LIST(tbStreamDataWriter, paramArrayOfTbColNameList[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_LOAD_STREAM(TbStream paramTbStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(57, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramInt2 != 0) {
        tbStreamDataWriter.writeInt(paramInt2, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt2);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_FLUSH_ROW(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(59, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_FINISH(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(60, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_DATASAVE(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(61, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_ABORT(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(62, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SHUTDOWN(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(63, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SESSKILL(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(64, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void STMTCANCEL(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(65, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void XA_OPEN(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(66, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void XA_CLOSE(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(67, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void XA_RECOVER(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(68, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void XA(TbStream paramTbStream, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(69, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void PC_SEMANTIC_CHECK(TbStream paramTbStream, int paramInt1, String paramString, int paramInt2, TbBindParamDesc[] paramArrayOfTbBindParamDesc) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(71, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramArrayOfTbBindParamDesc != null)
        for (byte b = 0; b < paramInt2; b++)
          BIND_PARAM_DESC(tbStreamDataWriter, paramArrayOfTbBindParamDesc[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void GET_LAST_EXECUTED_SQLINFO(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(73, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void VALIDATE_OBJ(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(77, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_AUTH_REQ(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, byte[] paramArrayOfbyte, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(108, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramInt2 != 0) {
        tbStreamDataWriter.writeInt(paramInt2, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt2);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TB_PING(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(129, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void CLNT_INFO_PARAM(TbStreamDataWriter paramTbStreamDataWriter, TbClntInfoParam paramTbClntInfoParam) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbClntInfoParam.clntParamId, 4);
    if (paramTbClntInfoParam.clntParamVal != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbClntInfoParam.clntParamVal);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void AUTH_REQ_WITH_VER(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, int paramInt4, TbClntInfoParam[] paramArrayOfTbClntInfoParam, int paramInt5, TbNlsParam[] paramArrayOfTbNlsParam, int paramInt6) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(137, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt4, 4);
      if (paramArrayOfTbClntInfoParam != null)
        for (byte b = 0; b < paramInt4; b++)
          CLNT_INFO_PARAM(tbStreamDataWriter, paramArrayOfTbClntInfoParam[b]);  
      tbStreamDataWriter.writeInt(paramInt5, 4);
      if (paramArrayOfTbNlsParam != null)
        for (byte b = 0; b < paramInt5; b++)
          NLS_PARAM(tbStreamDataWriter, paramArrayOfTbNlsParam[b]);  
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_AUTH_REQ_WITH_VER(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, byte[] paramArrayOfbyte, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(138, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void SESS_ATTR_DESC(TbStreamDataWriter paramTbStreamDataWriter, TbSessAttrDesc paramTbSessAttrDesc) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbSessAttrDesc.key, 4);
    if (paramTbSessAttrDesc.value != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbSessAttrDesc.value);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void SESS_ATTR(TbStream paramTbStream, int paramInt, TbSessAttrDesc[] paramArrayOfTbSessAttrDesc) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(143, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      if (paramArrayOfTbSessAttrDesc != null)
        for (byte b = 0; b < paramInt; b++)
          SESS_ATTR_DESC(tbStreamDataWriter, paramArrayOfTbSessAttrDesc[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void READINESS_TO_ACCEPT_SSL_CONN(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(144, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DESCRIBE_CSR(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(158, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_AUTH_REQ_WITH_RTH_ID(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, byte[] paramArrayOfbyte, int paramInt4, int paramInt5) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(159, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_TSN_UPDATE(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(160, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void THREAD_LOG(TbStreamDataWriter paramTbStreamDataWriter, TbThreadLog paramTbThreadLog) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbThreadLog.rthId, 4);
    paramTbStreamDataWriter.writeInt(paramTbThreadLog.rbaSeqno, 4);
    paramTbStreamDataWriter.writeInt(paramTbThreadLog.rbaBlkno, 4);
    if (paramTbThreadLog.logblksLen != 0) {
      paramTbStreamDataWriter.writeInt(paramTbThreadLog.logblksLen, 4);
      paramTbStreamDataWriter.writePadBytes(paramTbThreadLog.logblks, 0, paramTbThreadLog.logblksLen);
    } else {
      paramTbStreamDataWriter.writeInt(0, 4);
      paramTbStreamDataWriter.writeInt(0, 4);
    } 
  }
  
  public static void DESCRIBE_SESS_INFO(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(166, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void RESET_SESS(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(167, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DESCRIBE_CONNECT_INFO(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(168, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DEADLOCK_PRIORITY(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(169, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_RECONFIGURATION(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(170, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void MODIFY_TCP_SNDBUF_SIZE(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(171, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void MODIFY_TCP_RCVBUF_SIZE(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(172, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void GET_TCP_SNDBUF_SIZE(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(175, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void GET_TCP_RCVBUF_SIZE(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(176, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void PROBE_TRANS_RATE(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(179, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void COL_EXP_LIST(TbStreamDataWriter paramTbStreamDataWriter, TbColExpList paramTbColExpList) throws SQLException {
    if (paramTbColExpList.colExp != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbColExpList.colExp);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void DPL_PREPARE_WITH_EXP(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, int paramInt2, TbColNameList[] paramArrayOfTbColNameList, int paramInt3, TbColExpList[] paramArrayOfTbColExpList) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(180, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramArrayOfTbColNameList != null)
        for (byte b = 0; b < paramInt2; b++)
          COL_NAME_LIST(tbStreamDataWriter, paramArrayOfTbColNameList[b]);  
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbColExpList != null)
        for (byte b = 0; b < paramInt3; b++)
          COL_EXP_LIST(tbStreamDataWriter, paramArrayOfTbColExpList[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void NOTIFY_TQ_CLI(TbStream paramTbStream, int paramInt, String paramString1, String paramString2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(181, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void NOTIFY_FAN(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(182, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void BINDPARAM_UDT(TbStreamDataWriter paramTbStreamDataWriter, TbBindparamUdt paramTbBindparamUdt) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbBindparamUdt.flag, 4);
    paramTbStreamDataWriter.writeRpcolData(paramTbBindparamUdt.dataValue, paramTbBindparamUdt.dataValueLen);
    paramTbStreamDataWriter.writeInt(paramTbBindparamUdt.subBindparamArrayCnt, 4);
    if (paramTbBindparamUdt.subBindparam != null)
      for (byte b = 0; b < paramTbBindparamUdt.subBindparamArrayCnt; b++)
        BINDPARAM_UDT(paramTbStreamDataWriter, paramTbBindparamUdt.subBindparam[b]);  
  }
  
  public static void PREPARE_EXECUTE_UDT(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, int paramInt3, TbBindparamUdt[] paramArrayOfTbBindparamUdt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(184, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbBindparamUdt != null)
        for (byte b = 0; b < paramInt3; b++)
          BINDPARAM_UDT(tbStreamDataWriter, paramArrayOfTbBindparamUdt[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void EXECUTE_UDT(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, TbBindparamUdt[] paramArrayOfTbBindparamUdt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(185, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeBytes(paramArrayOfbyte);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbBindparamUdt != null)
        for (byte b = 0; b < paramInt3; b++)
          BINDPARAM_UDT(tbStreamDataWriter, paramArrayOfTbBindparamUdt[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_PREPARE_PARALLEL(TbStream paramTbStream, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, TbColNameList[] paramArrayOfTbColNameList) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(188, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbColNameList != null)
        for (byte b = 0; b < paramInt3; b++)
          COL_NAME_LIST(tbStreamDataWriter, paramArrayOfTbColNameList[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void PID_LIST(TbStreamDataWriter paramTbStreamDataWriter, TbPidList paramTbPidList) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbPidList.pid, 4);
  }
  
  public static void SET_LOCAL_SESS(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(193, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_ASSIGN(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(194, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      tbStreamDataWriter.writeInt(paramInt9, 4);
      tbStreamDataWriter.writeInt(paramInt10, 4);
      tbStreamDataWriter.writeInt(paramInt11, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_RPC(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, byte[] paramArrayOfbyte, int paramInt9) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(196, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      if (paramInt9 != 0) {
        tbStreamDataWriter.writeInt(paramInt9, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt9);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_ECHO(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(198, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt != 0) {
        tbStreamDataWriter.writeInt(paramInt, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DDL_SEND(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(199, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_MPC(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(200, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      tbStreamDataWriter.writeInt(paramInt9, 4);
      tbStreamDataWriter.writeInt(paramInt10, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void GPP_CHUNK_PTR(TbStreamDataWriter paramTbStreamDataWriter, TbGppChunkPtr paramTbGppChunkPtr) throws SQLException {
    paramTbStreamDataWriter.writeBytes(paramTbGppChunkPtr.ptr);
  }
  
  public static void TMC_GOPT(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(202, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_GLOBAL_SWITCH(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(204, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_NEXT_TABLET(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(205, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_CHUNK(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(207, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt != 0) {
        tbStreamDataWriter.writeInt(paramInt, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_SLAVE_EXEC_REQUEST(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, byte[] paramArrayOfbyte1, int paramInt5, byte[] paramArrayOfbyte2, int paramInt6, byte[] paramArrayOfbyte3, int paramInt7, TbBindparam[] paramArrayOfTbBindparam) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(208, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeBytes(paramArrayOfbyte1);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      if (paramInt6 != 0) {
        tbStreamDataWriter.writeInt(paramInt6, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte2, 0, paramInt6);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeBytes(paramArrayOfbyte3);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      if (paramArrayOfTbBindparam != null)
        for (byte b = 0; b < paramInt7; b++)
          BINDPARAM(tbStreamDataWriter, paramArrayOfTbBindparam[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_SEND_CHUNK(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(209, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void HORDE_ARG_ENTRY(TbStreamDataWriter paramTbStreamDataWriter, TbHordeArgEntry paramTbHordeArgEntry) throws SQLException {
    if (paramTbHordeArgEntry.key != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbHordeArgEntry.key);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
    if (paramTbHordeArgEntry.value != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbHordeArgEntry.value);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void HORDE(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, TbHordeArgEntry[] paramArrayOfTbHordeArgEntry) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(210, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbHordeArgEntry != null)
        for (byte b = 0; b < paramInt3; b++)
          HORDE_ARG_ENTRY(tbStreamDataWriter, paramArrayOfTbHordeArgEntry[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void UDF_ARG_META(TbStreamDataWriter paramTbStreamDataWriter, TbUdfArgMeta paramTbUdfArgMeta) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbUdfArgMeta.type, 4);
  }
  
  public static void TMC_SUB_DUMMY(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(211, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_PSM_BCODE_REQUEST(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(212, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_MASTER_UDF_RESOLVE_REQUEST(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, TbUdfArgMeta[] paramArrayOfTbUdfArgMeta, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(214, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      if (paramArrayOfTbUdfArgMeta != null)
        for (byte b = 0; b < paramInt6; b++)
          UDF_ARG_META(tbStreamDataWriter, paramArrayOfTbUdfArgMeta[b]);  
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_REFSUBQRY_EXEC_REQUEST(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(216, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_NEED_GPP(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(217, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_SEND_GPP(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(218, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt != 0) {
        tbStreamDataWriter.writeInt(paramInt, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void UNIQ_CHK_IDX(TbStreamDataWriter paramTbStreamDataWriter, TbUniqChkIdx paramTbUniqChkIdx) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbUniqChkIdx.idxGtid, 4);
  }
  
  private static void BUFF_COLNO(TbStreamDataWriter paramTbStreamDataWriter, TbBuffColno paramTbBuffColno) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbBuffColno.sgmtColno, 4);
  }
  
  public static void TMC_CHECK_UNIQUE(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, TbUniqChkIdx[] paramArrayOfTbUniqChkIdx, int paramInt4, TbBuffColno[] paramArrayOfTbBuffColno, int paramInt5, int paramInt6, int paramInt7) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(220, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbUniqChkIdx != null)
        for (byte b = 0; b < paramInt3; b++)
          UNIQ_CHK_IDX(tbStreamDataWriter, paramArrayOfTbUniqChkIdx[b]);  
      tbStreamDataWriter.writeInt(paramInt4, 4);
      if (paramArrayOfTbBuffColno != null)
        for (byte b = 0; b < paramInt4; b++)
          BUFF_COLNO(tbStreamDataWriter, paramArrayOfTbBuffColno[b]);  
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DML_LOCK(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(222, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TID_VCON_CONNECT(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(224, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TID_VCON_CLOSE(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(226, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TID_VCON_RCV_WIN(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(227, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_ANON_PSM(TbStream paramTbStream, int paramInt1, String paramString, int paramInt2, TbBindparam[] paramArrayOfTbBindparam) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(228, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramArrayOfTbBindparam != null)
        for (byte b = 0; b < paramInt2; b++)
          BINDPARAM(tbStreamDataWriter, paramArrayOfTbBindparam[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_CONN_EXEC(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfbyte, int paramInt6) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(229, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      if (paramInt6 != 0) {
        tbStreamDataWriter.writeInt(paramInt6, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt6);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DML_RESULT(TbStream paramTbStream, byte[] paramArrayOfbyte) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(231, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeBytes(paramArrayOfbyte);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_GET_INDEX_STAT(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(232, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DUP_TX_RESET(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(234, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DUP_TX_SET(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(235, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void TBL_INFO(TbStreamDataWriter paramTbStreamDataWriter, TbTblInfo paramTbTblInfo) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbTblInfo.gtid, 4);
    paramTbStreamDataWriter.writeInt(paramTbTblInfo.tabletNo, 4);
  }
  
  private static void IDX_INFO(TbStreamDataWriter paramTbStreamDataWriter, TbIdxInfo paramTbIdxInfo) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbIdxInfo.blkcnt, 4);
    paramTbStreamDataWriter.writeInt(paramTbIdxInfo.leafblkcnt, 4);
    paramTbStreamDataWriter.writeBytes(paramTbIdxInfo.distinctkey);
    paramTbStreamDataWriter.writeBytes(paramTbIdxInfo.clufac);
    paramTbStreamDataWriter.writeInt(paramTbIdxInfo.blevel, 4);
  }
  
  private static void SAMPLE_BLK(TbStreamDataWriter paramTbStreamDataWriter, TbSampleBlk paramTbSampleBlk) throws SQLException {
    if (paramTbSampleBlk.chunkLen != 0) {
      paramTbStreamDataWriter.writeInt(paramTbSampleBlk.chunkLen, 4);
      paramTbStreamDataWriter.writePadBytes(paramTbSampleBlk.chunk, 0, paramTbSampleBlk.chunkLen);
    } else {
      paramTbStreamDataWriter.writeInt(0, 4);
      paramTbStreamDataWriter.writeInt(0, 4);
    } 
  }
  
  public static void TMC_GET_DSAMPLE(TbStream paramTbStream, int paramInt, TbTblInfo[] paramArrayOfTbTblInfo) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(237, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      if (paramArrayOfTbTblInfo != null)
        for (byte b = 0; b < paramInt; b++)
          TBL_INFO(tbStreamDataWriter, paramArrayOfTbTblInfo[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_CHECK_REF(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(238, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_SSS_CHECK_REF(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfbyte1, int paramInt7, byte[] paramArrayOfbyte2, int paramInt8) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(240, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      if (paramInt7 != 0) {
        tbStreamDataWriter.writeInt(paramInt7, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte1, 0, paramInt7);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      if (paramInt8 != 0) {
        tbStreamDataWriter.writeInt(paramInt8, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte2, 0, paramInt8);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DDL_TABLET_STR(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(242, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_DDL_RECYCLE(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(243, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void SYS_BIND_VAR(TbStreamDataWriter paramTbStreamDataWriter, TbSysBindVar paramTbSysBindVar) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbSysBindVar.type, 4);
    if (paramTbSysBindVar.dtvLen != 0) {
      paramTbStreamDataWriter.writeInt(paramTbSysBindVar.dtvLen, 4);
      paramTbStreamDataWriter.writePadBytes(paramTbSysBindVar.dtv, 0, paramTbSysBindVar.dtvLen);
    } else {
      paramTbStreamDataWriter.writeInt(0, 4);
      paramTbStreamDataWriter.writeInt(0, 4);
    } 
    if (paramTbSysBindVar.literal != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbSysBindVar.literal);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
  }
  
  public static void TMC_GPP_GET(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, TbSysBindVar[] paramArrayOfTbSysBindVar) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(246, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbSysBindVar != null)
        for (byte b = 0; b < paramInt3; b++)
          SYS_BIND_VAR(tbStreamDataWriter, paramArrayOfTbSysBindVar[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void TMC_RESET_COORD(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(247, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void GET_LAST_EXECUTED_SQLINFO2(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(248, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void DPL_PREPARE_WITH_EXP_PARALLEL(TbStream paramTbStream, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, TbColNameList[] paramArrayOfTbColNameList, int paramInt4, TbColExpList[] paramArrayOfTbColExpList) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(272, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramArrayOfTbColNameList != null)
        for (byte b = 0; b < paramInt3; b++)
          COL_NAME_LIST(tbStreamDataWriter, paramArrayOfTbColNameList[b]);  
      tbStreamDataWriter.writeInt(paramInt4, 4);
      if (paramArrayOfTbColExpList != null)
        for (byte b = 0; b < paramInt4; b++)
          COL_EXP_LIST(tbStreamDataWriter, paramArrayOfTbColExpList[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void REQUEST_SESSKEY(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(282, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void AUTH_REQ_TAS(TbStream paramTbStream, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(284, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString4 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString4);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void COLUMN_DATA(TbStreamDataWriter paramTbStreamDataWriter, TbColumnData paramTbColumnData) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(paramTbColumnData.value, paramTbColumnData.valueLen);
  }
  
  public static void PREPARE_BATCHUPDATE2(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, TbColumnDesc[] paramArrayOfTbColumnDesc, byte[] paramArrayOfbyte, int paramInt7) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(285, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      if (paramArrayOfTbColumnDesc != null)
        for (byte b = 0; b < paramInt6; b++)
          COLUMN_DESC(tbStreamDataWriter, paramArrayOfTbColumnDesc[b]);  
      if (paramInt7 != 0) {
        tbStreamDataWriter.writeInt(paramInt7, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt7);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void EXECUTE_PIVOT(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(314, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void XA_START_TO_OWNER(TbStream paramTbStream, String paramString) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(318, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_GREETING(TbStream paramTbStream, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(326, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_HB(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(331, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_NODE_INFO(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(332, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_SWITCHOVER(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(333, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_AUTODIE_SET(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(334, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_SHUTDOWN(TbStream paramTbStream, int paramInt) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(336, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void OBS_SCLSID(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(337, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_BLOCK_SYNC(TbStream paramTbStream, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(343, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void AUTH_REQ_WITH_VER_SVC_NAME(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, int paramInt4, TbClntInfoParam[] paramArrayOfTbClntInfoParam, int paramInt5, TbNlsParam[] paramArrayOfTbNlsParam, int paramInt6, String paramString4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(348, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt4, 4);
      if (paramArrayOfTbClntInfoParam != null)
        for (byte b = 0; b < paramInt4; b++)
          CLNT_INFO_PARAM(tbStreamDataWriter, paramArrayOfTbClntInfoParam[b]);  
      tbStreamDataWriter.writeInt(paramInt5, 4);
      if (paramArrayOfTbNlsParam != null)
        for (byte b = 0; b < paramInt5; b++)
          NLS_PARAM(tbStreamDataWriter, paramArrayOfTbNlsParam[b]);  
      tbStreamDataWriter.writeInt(paramInt6, 4);
      if (paramString4 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString4);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_AUTH_REQ_WITH_VER_SVC_NAME(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, byte[] paramArrayOfbyte, int paramInt4, String paramString3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(349, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void SVR_AUTH_REQ_WITH_RTH_ID_SVC_NAME(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, byte[] paramArrayOfbyte, int paramInt4, int paramInt5, int paramInt6, String paramString3) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(350, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramInt4 != 0) {
        tbStreamDataWriter.writeInt(paramInt4, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt4);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_DOWN(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(351, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_ADMIN_TO_HYPER_DATA(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(352, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt1 != 0) {
        tbStreamDataWriter.writeInt(paramInt1, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt1);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_HYPER_DATA_TO_ADMIN(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(353, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt1 != 0) {
        tbStreamDataWriter.writeInt(paramInt1, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt1);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_USER_CUSTOM(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(354, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramInt3 != 0) {
        tbStreamDataWriter.writeInt(paramInt3, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt3);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_SET_BUFFER_COUNT(TbStream paramTbStream, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(355, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_CREATE_BUFFER_POOL(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(356, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_INITIATE_LOAD(TbStream paramTbStream, String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(357, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString4 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString4);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramInt2 != 0) {
        tbStreamDataWriter.writeInt(paramInt2, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt2);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void HL_COL_META(TbStreamDataWriter paramTbStreamDataWriter, TbHlColMeta paramTbHlColMeta) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbHlColMeta.colIdx, 4);
    paramTbStreamDataWriter.writeInt(paramTbHlColMeta.isLob, 4);
  }
  
  public static void HL_LOAD_DATA(TbStream paramTbStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(359, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramInt1 != 0) {
        tbStreamDataWriter.writeInt(paramInt1, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt1);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_LOAD_DATA_WITH_LOB(TbStream paramTbStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfbyte, int paramInt7) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(360, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      if (paramInt7 != 0) {
        tbStreamDataWriter.writeInt(paramInt7, 4);
        tbStreamDataWriter.writePadBytes(paramArrayOfbyte, 0, paramInt7);
      } else {
        tbStreamDataWriter.writeInt(0, 4);
        tbStreamDataWriter.writeInt(0, 4);
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void HL_LOG_INFO(TbStreamDataWriter paramTbStreamDataWriter, TbHlLogInfo paramTbHlLogInfo) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbHlLogInfo.errorCode, 4);
    paramTbStreamDataWriter.writeInt(paramTbHlLogInfo.failedRecordNumber, 4);
  }
  
  public static void HL_LOG_INFO_ARRAY(TbStream paramTbStream, int paramInt, TbHlLogInfo[] paramArrayOfTbHlLogInfo) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(362, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt, 4);
      if (paramArrayOfTbHlLogInfo != null)
        for (byte b = 0; b < paramInt; b++)
          HL_LOG_INFO(tbStreamDataWriter, paramArrayOfTbHlLogInfo[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_FINISH_LOAD(TbStream paramTbStream) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(363, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void HL_ADAPTER_PORT(TbStreamDataWriter paramTbStreamDataWriter, TbHlAdapterPort paramTbHlAdapterPort) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbHlAdapterPort.portNumber, 4);
  }
  
  private static void HL_ADAPTER(TbStreamDataWriter paramTbStreamDataWriter, TbHlAdapter paramTbHlAdapter) throws SQLException {
    if (paramTbHlAdapter.loadType != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbHlAdapter.loadType);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
    paramTbStreamDataWriter.writeInt(paramTbHlAdapter.portListArrayCnt, 4);
    if (paramTbHlAdapter.portList != null)
      for (byte b = 0; b < paramTbHlAdapter.portListArrayCnt; b++)
        HL_ADAPTER_PORT(paramTbStreamDataWriter, paramTbHlAdapter.portList[b]);  
  }
  
  public static void HL_ADMIN_CONNECT(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, TbHlAdapter[] paramArrayOfTbHlAdapter) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(365, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      if (paramArrayOfTbHlAdapter != null)
        for (byte b = 0; b < paramInt8; b++)
          HL_ADAPTER(tbStreamDataWriter, paramArrayOfTbHlAdapter[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_ACTIVE(TbStream paramTbStream, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, TbHlAdapter[] paramArrayOfTbHlAdapter) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(366, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      tbStreamDataWriter.writeInt(paramInt9, 4);
      if (paramArrayOfTbHlAdapter != null)
        for (byte b = 0; b < paramInt9; b++)
          HL_ADAPTER(tbStreamDataWriter, paramArrayOfTbHlAdapter[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void HL_LOAD_QUEUE(TbStreamDataWriter paramTbStreamDataWriter, TbHlLoadQueue paramTbHlLoadQueue) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbHlLoadQueue.size, 4);
  }
  
  private static void HL_HANDLE_STATUS(TbStreamDataWriter paramTbStreamDataWriter, TbHlHandleStatus paramTbHlHandleStatus) throws SQLException {
    paramTbStreamDataWriter.writeInt(paramTbHlHandleStatus.handleId, 4);
    paramTbStreamDataWriter.writeInt(paramTbHlHandleStatus.loadType, 4);
    paramTbStreamDataWriter.writeInt(paramTbHlHandleStatus.requestCount, 4);
    paramTbStreamDataWriter.writeInt(paramTbHlHandleStatus.doneCount, 4);
    paramTbStreamDataWriter.writeInt(paramTbHlHandleStatus.loadQueueSizeArrayCnt, 4);
    if (paramTbHlHandleStatus.loadQueueSize != null)
      for (byte b = 0; b < paramTbHlHandleStatus.loadQueueSizeArrayCnt; b++)
        HL_LOAD_QUEUE(paramTbStreamDataWriter, paramTbHlHandleStatus.loadQueueSize[b]);  
    paramTbStreamDataWriter.writeInt(paramTbHlHandleStatus.bufferCountInLoadBuf, 4);
  }
  
  public static void HL_ADMIN_UPDATE_STATUS(TbStream paramTbStream, int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, TbHlHandleStatus[] paramArrayOfTbHlHandleStatus) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(368, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.writeInt(paramInt6, 4);
      tbStreamDataWriter.writeInt(paramInt7, 4);
      tbStreamDataWriter.writeInt(paramInt8, 4);
      tbStreamDataWriter.writeInt(paramInt9, 4);
      tbStreamDataWriter.writeInt(paramInt10, 4);
      tbStreamDataWriter.writeInt(paramInt11, 4);
      tbStreamDataWriter.writeInt(paramInt12, 4);
      tbStreamDataWriter.writeInt(paramInt13, 4);
      tbStreamDataWriter.writeInt(paramInt14, 4);
      tbStreamDataWriter.writeInt(paramInt15, 4);
      tbStreamDataWriter.writeInt(paramInt16, 4);
      tbStreamDataWriter.writeInt(paramInt17, 4);
      if (paramArrayOfTbHlHandleStatus != null)
        for (byte b = 0; b < paramInt17; b++)
          HL_HANDLE_STATUS(tbStreamDataWriter, paramArrayOfTbHlHandleStatus[b]);  
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  public static void HL_DB_CONNECT_REQUEST(TbStream paramTbStream, int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, String paramString4) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(372, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(paramInt1, 4);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString4 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString4);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
  
  private static void HL_TABLE_INFO_LIST(TbStreamDataWriter paramTbStreamDataWriter, TbHlTableInfoList paramTbHlTableInfoList) throws SQLException {
    if (paramTbHlTableInfoList.tableName != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbHlTableInfoList.tableName);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
    if (paramTbHlTableInfoList.owner != null) {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbHlTableInfoList.owner);
    } else {
      paramTbStreamDataWriter.writeLenAndDBEncodedPadString("");
    } 
    paramTbStreamDataWriter.writeInt(paramTbHlTableInfoList.descArrayCnt, 4);
    if (paramTbHlTableInfoList.desc != null)
      for (byte b = 0; b < paramTbHlTableInfoList.descArrayCnt; b++)
        COLUMN_DESC(paramTbStreamDataWriter, paramTbHlTableInfoList.desc[b]);  
  }
  
  public static void HL_TABLE_EXTRACT_REQUEST(TbStream paramTbStream, String paramString1, String paramString2, int paramInt1, int paramInt2, TbHlTableInfoList[] paramArrayOfTbHlTableInfoList, String paramString3, int paramInt3, int paramInt4, int paramInt5) throws SQLException {
    synchronized (paramTbStream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = paramTbStream.getMsgWriter();
      paramTbStream.startWritingPacketData();
      tbStreamDataWriter.writeInt(374, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramString1 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString1);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      if (paramString2 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString2);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt1, 4);
      tbStreamDataWriter.writeInt(paramInt2, 4);
      if (paramArrayOfTbHlTableInfoList != null)
        for (byte b = 0; b < paramInt2; b++)
          HL_TABLE_INFO_LIST(tbStreamDataWriter, paramArrayOfTbHlTableInfoList[b]);  
      if (paramString3 != null) {
        tbStreamDataWriter.writeLenAndDBEncodedPadString(paramString3);
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString("");
      } 
      tbStreamDataWriter.writeInt(paramInt3, 4);
      tbStreamDataWriter.writeInt(paramInt4, 4);
      tbStreamDataWriter.writeInt(paramInt5, 4);
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      paramTbStream.flush();
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgSend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
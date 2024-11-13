package ch.enterag.sqlparser;

import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.dml.DeleteStatement;
import ch.enterag.sqlparser.dml.UpdateStatement;
import ch.enterag.sqlparser.expression.GeneralValueSpecification;
import ch.enterag.sqlparser.expression.QuerySpecification;
import ch.enterag.sqlparser.expression.TablePrimary;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.utils.logging.IndentLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SqlStatement.class.getName());
   private static final int _iMAX_USER_NAME_LENGTH = 64;
   private static final int _iBUFSIZ = 8192;
   private SqlStatement.SsVisitor _visitor = new SqlStatement.SsVisitor();
   private DdlStatement _ddls = null;
   private DmlStatement _dmls = null;
   private QuerySpecification _qs = null;
   private String _sDefaultCatalog = null;
   private String _sDefaultSchema = null;
   private String _sUser = null;
   private Map<GeneralValueSpecification, Object> _mapQuestionMarkValues = new HashMap();
   private Map<GeneralValueSpecification, DataType> _mapQuestionMarkTypes = new HashMap();

   private SqlStatement.SsVisitor getVisitor() {
      return this._visitor;
   }

   public DdlStatement getDdlStatement() {
      return this._ddls;
   }

   public void setDdlStatement(DdlStatement ddls) {
      this._ddls = ddls;
   }

   public DmlStatement getDmlStatement() {
      return this._dmls;
   }

   public void setDmlStatement(DmlStatement dmls) {
      this._dmls = dmls;
   }

   public QuerySpecification getQuerySpecification() {
      return this._qs;
   }

   public void setQuerySpecification(QuerySpecification qs) {
      this._qs = qs;
   }

   public String getDefaultCatalog() {
      return this._sDefaultCatalog;
   }

   public String getDefaultSchema() {
      return this._sDefaultSchema;
   }

   public String getUser() {
      return this._sUser;
   }

   public void setEvaluationContext(String sUser, String sDefaultCatalog, String sDefaultSchema) {
      this._sUser = sUser;
      this._sDefaultCatalog = sDefaultCatalog;
      this._sDefaultSchema = sDefaultSchema;
   }

   public void setQuestionMarks(List<GeneralValueSpecification> listQuestionMarks) {
      this._mapQuestionMarkValues.clear();

      for(int iQuestionMark = 0; iQuestionMark < listQuestionMarks.size(); ++iQuestionMark) {
         GeneralValueSpecification gvs = (GeneralValueSpecification)listQuestionMarks.get(iQuestionMark);
         this._mapQuestionMarkValues.put(gvs, (Object)null);
         this._mapQuestionMarkTypes.put(gvs, (Object)null);
      }

   }

   public void setQuestionMarkType(GeneralValueSpecification gvs, DataType dt) {
      if (this._mapQuestionMarkTypes.containsKey(gvs)) {
         this._mapQuestionMarkValues.put(gvs, dt);
      }

   }

   public void setQuestionMarkValue(GeneralValueSpecification gvs, Object oValue) {
      try {
         if (!this._mapQuestionMarkValues.containsKey(gvs)) {
            throw new IllegalArgumentException("Invalid attempt at setting a question mark value!");
         } else {
            if (oValue instanceof Boolean) {
               oValue = (Boolean)oValue;
            } else if (oValue instanceof Byte) {
               Byte byValue = (Byte)oValue;
               oValue = BigDecimal.valueOf(byValue.longValue());
            } else if (oValue instanceof Short) {
               Short wValue = (Short)oValue;
               oValue = BigDecimal.valueOf(wValue.longValue());
            } else if (oValue instanceof Integer) {
               Integer iValue = (Integer)oValue;
               oValue = BigDecimal.valueOf(iValue.longValue());
            } else if (oValue instanceof Long) {
               Long lValue = (Long)oValue;
               oValue = BigDecimal.valueOf(lValue);
            } else if (oValue instanceof BigDecimal) {
               oValue = (BigDecimal)oValue;
            } else if (oValue instanceof Float) {
               Float fValue = (Float)oValue;
               oValue = fValue.doubleValue();
            } else if (oValue instanceof Double) {
               oValue = (Double)oValue;
            } else if (oValue instanceof byte[]) {
               oValue = (byte[])((byte[])oValue);
            } else if (oValue instanceof Date) {
               oValue = (Date)oValue;
            } else if (oValue instanceof Time) {
               oValue = (Time)oValue;
            } else if (oValue instanceof Timestamp) {
               oValue = (Timestamp)oValue;
            } else if (oValue instanceof String) {
               oValue = (String)oValue;
            } else if (oValue instanceof Clob) {
               Clob clob = (Clob)oValue;
               oValue = clob.getSubString(1L, (int)clob.length());
            } else if (oValue instanceof Blob) {
               Blob blob = (Blob)oValue;
               oValue = blob.getBytes(1L, (int)blob.length());
            } else if (oValue instanceof SQLXML) {
               SQLXML sqlxml = (SQLXML)oValue;
               oValue = sqlxml.getString();
            } else {
               int iRead;
               if (oValue instanceof InputStream) {
                  InputStream is = (InputStream)oValue;
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  byte[] buf = new byte[8192];

                  for(iRead = is.read(buf); iRead != -1; iRead = is.read(buf)) {
                     baos.write(buf, 0, iRead);
                  }

                  baos.close();
                  oValue = baos.toByteArray();
                  is.close();
               } else if (oValue instanceof Reader) {
                  Reader rdr = (Reader)oValue;
                  StringWriter sw = new StringWriter();
                  char[] cbuf = new char[8192];

                  for(iRead = rdr.read(cbuf); iRead != -1; iRead = rdr.read(cbuf)) {
                     sw.write(cbuf, 0, iRead);
                  }

                  sw.close();
                  oValue = sw.toString();
                  rdr.close();
               }
            }

            this._mapQuestionMarkValues.put(gvs, oValue);
         }
      } catch (SQLException var7) {
         throw new IllegalArgumentException("Parameter value could not be set!", var7);
      } catch (IOException var8) {
         throw new IllegalArgumentException("Parameter value could not be set!", var8);
      }
   }

   public DataType getGeneralType(GeneralValueSpecification gvs) {
      DataType dt = null;
      switch(gvs.getGeneralValue()) {
      case CURRENT_PATH:
      case CURRENT_ROLE:
      case VALUE:
         throw new IllegalArgumentException("General value " + gvs.getGeneralValue().getKeywords() + " not supported!");
      case CURRENT_USER:
      case SESSION_USER:
      case SYSTEM_USER:
      case USER:
         dt = this.getSqlFactory().newDataType();
         PredefinedType pt = this.getSqlFactory().newPredefinedType();
         dt.initPredefinedDataType(pt);
         pt.initVarCharType(64);
         break;
      case QUESTION_MARK:
         dt = (DataType)this._mapQuestionMarkTypes.get(gvs);
      }

      return dt;
   }

   public Object getGeneralValue(GeneralValueSpecification gvs) {
      Object oValue = null;
      switch(gvs.getGeneralValue()) {
      case CURRENT_PATH:
      case CURRENT_ROLE:
      case VALUE:
         throw new IllegalArgumentException("General value " + gvs.getGeneralValue().getKeywords() + " not supported!");
      case CURRENT_USER:
      case SESSION_USER:
      case SYSTEM_USER:
      case USER:
         oValue = this.getUser();
         break;
      case QUESTION_MARK:
         oValue = this._mapQuestionMarkValues.get(gvs);
      }

      return oValue;
   }

   public DataType getColumnType(IdChain idcColumn) {
      DataType dt = null;
      int iLength = idcColumn.get().size();
      if (iLength > 0) {
         QuerySpecification qs = this.getQuerySpecification();
         DmlStatement dstmt = this.getDmlStatement();
         String sColumnName = (String)idcColumn.get().get(iLength - 1);
         if (qs != null) {
            TablePrimary tp = qs.getTablePrimary(this, idcColumn);
            dt = tp.getColumnType(sColumnName);
         } else {
            if (dstmt == null) {
               throw new IllegalArgumentException("No column types for DDL statements should be needed!");
            }

            UpdateStatement us = dstmt.getUpdateStatement();
            DeleteStatement ds = dstmt.getDeleteStatement();
            if (us != null) {
               dt = us.getColumnType(sColumnName);
            } else {
               if (ds == null) {
                  throw new IllegalArgumentException("No column types for insert statement should be needed!");
               }

               dt = ds.getColumnType(sColumnName);
            }
         }

         return dt;
      } else {
         throw new IllegalArgumentException("Identifier chain is invalid for column!");
      }
   }

   public Object getColumnValue(IdChain idcColumn, boolean bAggregated) {
      Object oValue = null;
      QuerySpecification qs = this.getQuerySpecification();
      DmlStatement dstmt = this.getDmlStatement();
      int iLength = idcColumn.get().size();
      if (iLength > 0) {
         String sColumnName = (String)idcColumn.get().get(iLength - 1);
         if (qs != null) {
            if (!bAggregated && !qs.isGroupedOk(idcColumn)) {
               throw new IllegalArgumentException("Column " + idcColumn.format() + " not in GROUP BY!");
            }

            TablePrimary tp = qs.getTablePrimary(this, idcColumn);
            oValue = tp.getColumnValue(sColumnName);
         } else {
            if (dstmt == null) {
               throw new IllegalArgumentException("No column values for DDL statements should be needed!");
            }

            if (bAggregated) {
               throw new IllegalArgumentException("Aggregate functions not allowed in DML expressions!");
            }

            UpdateStatement us = dstmt.getUpdateStatement();
            DeleteStatement ds = dstmt.getDeleteStatement();
            if (us != null) {
               oValue = us.getColumnValue(sColumnName);
            } else {
               if (ds == null) {
                  throw new IllegalArgumentException("No column values for insert statement should be needed!");
               }

               oValue = ds.getColumnValue(sColumnName);
            }
         }

         return oValue;
      } else {
         throw new IllegalArgumentException("Identifier chain is invalid for column!");
      }
   }

   public String format() {
      String sStatement = null;
      if (this.getDdlStatement() != null) {
         sStatement = this.getDdlStatement().format();
      } else if (this.getDmlStatement() != null) {
         sStatement = this.getDmlStatement().format();
      } else if (this.getQuerySpecification() != null) {
         sStatement = this.getQuerySpecification().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.SqlStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.SqlStatementContext ctx = null;

      try {
         ctx = this.getParser().sqlStatement();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().sqlStatement();
      }

      this.parse(ctx);
   }

   public void initialize(DdlStatement ddls, DmlStatement dmls, QuerySpecification qs) {
      _il.enter(new Object[0]);
      this.setDdlStatement(ddls);
      this.setDmlStatement(dmls);
      this.setQuerySpecification(qs);
      _il.exit();
   }

   public SqlStatement(SqlFactory sf) {
      super(sf);
   }

   private class SsVisitor extends EnhancedSqlBaseVisitor<SqlStatement> {
      private SsVisitor() {
      }

      public SqlStatement visitDdlStatement(SqlParser.DdlStatementContext ctx) {
         SqlStatement.this.setDdlStatement(SqlStatement.this.getSqlFactory().newDdlStatement());
         SqlStatement.this.getDdlStatement().parse(ctx);
         return SqlStatement.this;
      }

      public SqlStatement visitDmlStatement(SqlParser.DmlStatementContext ctx) {
         SqlStatement.this.setDmlStatement(SqlStatement.this.getSqlFactory().newDmlStatement());
         SqlStatement.this.getDmlStatement().parse(ctx);
         return SqlStatement.this;
      }

      public SqlStatement visitQuerySpecification(SqlParser.QuerySpecificationContext ctx) {
         SqlStatement.this.setQuerySpecification(SqlStatement.this.getSqlFactory().newQuerySpecification());
         SqlStatement.this.getQuerySpecification().parse(ctx);
         return SqlStatement.this;
      }

      // $FF: synthetic method
      SsVisitor(Object x1) {
         this();
      }
   }
}

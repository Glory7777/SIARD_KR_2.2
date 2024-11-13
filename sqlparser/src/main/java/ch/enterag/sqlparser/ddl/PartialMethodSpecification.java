package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.ddl.enums.MethodType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class PartialMethodSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(PartialMethodSpecification.class.getName());
   private PartialMethodSpecification.PmsVisitor _visitor = new PartialMethodSpecification.PmsVisitor();
   private MethodType _mt = null;
   private Identifier _idMethodName = new Identifier();
   private QualifiedId _qSpecificMethodName = new QualifiedId();
   private List<SqlParameterDeclaration> _listSqlParameterDeclarations = new ArrayList();
   private boolean _bReturnsType = false;
   private DataType _dtReturnsType = null;
   private boolean _bResultCast = false;
   private DataType _dtResultCast = null;
   private List<TableColumn> _listTableColumns = new ArrayList();

   private PartialMethodSpecification.PmsVisitor getVisitor() {
      return this._visitor;
   }

   public MethodType getMethodType() {
      return this._mt;
   }

   public void setMethodType(MethodType mt) {
      this._mt = mt;
   }

   public Identifier getMethodName() {
      return this._idMethodName;
   }

   private void setMethodName(Identifier idMethod) {
      this._idMethodName = idMethod;
   }

   public QualifiedId getSpecificMethodName() {
      return this._qSpecificMethodName;
   }

   private void setSpecificMethodName(QualifiedId qSpecificMethodName) {
      this._qSpecificMethodName = qSpecificMethodName;
   }

   public List<SqlParameterDeclaration> getSqlParameterDeclarations() {
      return this._listSqlParameterDeclarations;
   }

   private void setSqlParameterDeclarations(List<SqlParameterDeclaration> listSqlParameterDeclarations) {
      this._listSqlParameterDeclarations = listSqlParameterDeclarations;
   }

   public boolean hasReturnsType() {
      return this._bReturnsType;
   }

   public void setReturnsType(boolean bReturnsType) {
      this._bReturnsType = bReturnsType;
   }

   public DataType getReturnsType() {
      return this._dtReturnsType;
   }

   public void setReturnsType(DataType dtReturnsType) {
      this._dtReturnsType = dtReturnsType;
   }

   public boolean hasResultCast() {
      return this._bResultCast;
   }

   public void setResultCast(boolean bResultCast) {
      this._bResultCast = bResultCast;
   }

   public DataType getResultCast() {
      return this._dtResultCast;
   }

   public void setResultCast(DataType dtResultCast) {
      this._dtResultCast = dtResultCast;
   }

   public List<TableColumn> getTableColumns() {
      return this._listTableColumns;
   }

   private void setTableColumns(List<TableColumn> listTableColumns) {
      this._listTableColumns = listTableColumns;
   }

   private String formatSqlParameterDeclarations() {
      String sList = "(";

      for(int iParameter = 0; iParameter < this.getSqlParameterDeclarations().size(); ++iParameter) {
         if (iParameter > 0) {
            sList = sList + ",";
         }

         sList = sList + "\r\n" + "  " + ((SqlParameterDeclaration)this.getSqlParameterDeclarations().get(iParameter)).format();
      }

      if (this.getSqlParameterDeclarations().size() > 0) {
         sList = sList + "\r\n";
      }

      sList = sList + ")";
      return sList;
   }

   private String formatTableColumns() {
      String sList = "(";

      for(int iColumn = 0; iColumn < this.getTableColumns().size(); ++iColumn) {
         if (iColumn > 0) {
            sList = sList + ",";
         }

         sList = sList + "\r\n" + "  " + ((TableColumn)this.getTableColumns().get(iColumn)).format();
      }

      sList = sList + "\r\n" + ")";
      return sList;
   }

   public String format() {
      String sSpecification = "";
      if (this.getMethodType() != null) {
         sSpecification = sSpecification + this.getMethodType().getKeywords() + " ";
      }

      sSpecification = sSpecification + K.METHOD.getKeyword() + " " + this.getMethodName().format();
      sSpecification = sSpecification + this.formatSqlParameterDeclarations();
      if (this.hasReturnsType()) {
         sSpecification = sSpecification + " " + K.RETURNS.getKeyword();
         if (this.getReturnsType() != null) {
            sSpecification = sSpecification + " " + this.getReturnsType().format();
         } else {
            sSpecification = sSpecification + " " + K.AS.getKeyword() + " " + K.LOCATOR.getKeyword();
         }

         if (this.hasResultCast()) {
            sSpecification = sSpecification + " " + K.CAST.getKeyword() + " " + K.FROM.getKeyword();
            if (this.getResultCast() != null) {
               sSpecification = sSpecification + " " + this.getResultCast().format();
            } else {
               sSpecification = sSpecification + " " + K.AS.getKeyword() + " " + K.LOCATOR.getKeyword();
            }
         }
      } else if (this.getTableColumns().size() > 0) {
         sSpecification = sSpecification + " " + K.RETURNS.getKeyword() + " " + K.TABLE.getKeyword() + this.formatTableColumns();
      }

      if (this.getSpecificMethodName().isSet()) {
         sSpecification = sSpecification + " " + K.SPECIFIC.getKeyword() + " " + this.getSpecificMethodName().format();
      }

      return sSpecification;
   }

   public void parse(SqlParser.PartialMethodSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().partialMethodSpecification());
   }

   public void initialize(MethodType mt, Identifier idMethodName, List<SqlParameterDeclaration> listSqlParameterDeclarations, boolean bReturnsType, DataType dtReturnsType, boolean bResultCast, DataType dtResultCast, List<TableColumn> listTableColumns, QualifiedId qSpecificMethodName) {
      _il.enter(new Object[]{mt, idMethodName, listSqlParameterDeclarations, String.valueOf(bReturnsType), dtReturnsType, String.valueOf(bResultCast), dtResultCast, listTableColumns, qSpecificMethodName});
      this.setMethodType(mt);
      this.setMethodName(idMethodName);
      this.setSqlParameterDeclarations(listSqlParameterDeclarations);
      this.setReturnsType(bReturnsType);
      this.setReturnsType(dtReturnsType);
      this.setResultCast(bResultCast);
      this.setResultCast(dtResultCast);
      this.setTableColumns(listTableColumns);
      this.setSpecificMethodName(qSpecificMethodName);
      _il.exit();
   }

   public PartialMethodSpecification(SqlFactory sf) {
      super(sf);
   }

   private class PmsVisitor extends EnhancedSqlBaseVisitor<PartialMethodSpecification> {
      private PmsVisitor() {
      }

      public PartialMethodSpecification visitMethodType(SqlParser.MethodTypeContext ctx) {
         PartialMethodSpecification.this.setMethodType(this.getMethodType(ctx));
         return PartialMethodSpecification.this;
      }

      public PartialMethodSpecification visitPartialMethodSpecification(SqlParser.PartialMethodSpecificationContext ctx) {
         this.setIdentifier(ctx.IDENTIFIER(), PartialMethodSpecification.this.getMethodName());
         if (ctx.SPECIFIC() != null) {
            this.setSpecificMethodName(ctx.specificMethodName(), PartialMethodSpecification.this.getSpecificMethodName());
         }

         return (PartialMethodSpecification)this.visitChildren(ctx);
      }

      public PartialMethodSpecification visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = PartialMethodSpecification.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         PartialMethodSpecification.this.getSqlParameterDeclarations().add(spd);
         return PartialMethodSpecification.this;
      }

      public PartialMethodSpecification visitReturnsDataType(SqlParser.ReturnsDataTypeContext ctx) {
         PartialMethodSpecification.this.setReturnsType(true);
         if (ctx.dataType() != null) {
            PartialMethodSpecification.this.setReturnsType(PartialMethodSpecification.this.getSqlFactory().newDataType());
            PartialMethodSpecification.this.getReturnsType().parse(ctx.dataType());
         }

         return PartialMethodSpecification.this;
      }

      public PartialMethodSpecification visitResultCast(SqlParser.ResultCastContext ctx) {
         PartialMethodSpecification.this.setResultCast(true);
         if (ctx.dataType() != null) {
            PartialMethodSpecification.this.setResultCast(PartialMethodSpecification.this.getSqlFactory().newDataType());
            PartialMethodSpecification.this.getResultCast().parse(ctx.dataType());
         }

         return PartialMethodSpecification.this;
      }

      public PartialMethodSpecification visitTableColumn(SqlParser.TableColumnContext ctx) {
         TableColumn tc = PartialMethodSpecification.this.getSqlFactory().newTableColumn();
         tc.parse(ctx);
         PartialMethodSpecification.this.getTableColumns().add(tc);
         return PartialMethodSpecification.this;
      }

      // $FF: synthetic method
      PmsVisitor(Object x1) {
         this();
      }
   }
}

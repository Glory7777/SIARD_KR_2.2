package ch.enterag.sqlparser.datatype;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class DataType extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DataType.class.getName());
   public static final int iUNDEFINED = -1;
   protected DataType.DtVisitor _visitor = new DataType.DtVisitor();
   private DataType.Type _type = null;
   private PredefinedType _pt = null;
   private QualifiedId _qUdtName = new QualifiedId();
   private List<FieldDefinition> _listFd = null;
   private QualifiedId _qScopeTable = new QualifiedId();
   private DataType _dt = null;
   private int _iLength = -1;

   protected DataType.DtVisitor getVisitor() {
      return this._visitor;
   }

   public DataType.Type getType() {
      return this._type;
   }

   public void setType(DataType.Type type) {
      this._type = type;
   }

   public PredefinedType getPredefinedType() {
      return this._pt;
   }

   public void setPredefinedType(PredefinedType pt) {
      this._pt = pt;
   }

   public QualifiedId getUdtName() {
      return this._qUdtName;
   }

   private void setUdtName(QualifiedId qUdtName) {
      this._qUdtName = qUdtName;
   }

   public List<FieldDefinition> getFieldDefinitionList() {
      return this._listFd;
   }

   public void setFieldDefinitionList(List<FieldDefinition> listFd) {
      this._listFd = listFd;
   }

   public void addFieldDefinition(FieldDefinition fd) {
      if (this._listFd == null) {
         this._listFd = new ArrayList();
      }

      this._listFd.add(fd);
   }

   public QualifiedId getScopeTable() {
      return this._qScopeTable;
   }

   private void setScopeTable(QualifiedId qScopeTable) {
      this._qScopeTable = qScopeTable;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public int getLength() {
      return this._iLength;
   }

   public void setLength(int iLength) {
      this._iLength = iLength;
   }

   protected String formatStructType() {
      String sDataType = this.getUdtName().format();
      return sDataType;
   }

   protected String formatRowType() {
      String sDataType = K.ROW.getKeyword() + "(";

      for(int iField = 0; iField < this.getFieldDefinitionList().size(); ++iField) {
         if (iField > 0) {
            sDataType = sDataType + "," + " ";
         }

         sDataType = sDataType + ((FieldDefinition)this.getFieldDefinitionList().get(iField)).format();
      }

      sDataType = sDataType + ")";
      return sDataType;
   }

   protected String formatRefType() {
      String sDataType = K.REF.getKeyword() + "(" + this.getUdtName().format() + ")";
      if (this.getScopeTable() != null) {
         sDataType = sDataType + " " + K.SCOPE.getKeyword() + " " + this.getScopeTable().format();
      }

      return sDataType;
   }

   protected String formatArrayType() {
      String sDataType = this.getDataType().format() + " " + K.ARRAY.getKeyword();
      if (this.getLength() != -1) {
         sDataType = sDataType + "[" + this.getLength() + "]";
      }

      return sDataType;
   }

   protected String formatMultisetType() {
      String sDataType = this.getDataType().format() + " " + K.MULTISET.getKeyword();
      return sDataType;
   }

   public String format() {
      String sDataType = null;
      switch(this.getType()) {
      case PRE:
         sDataType = this.getPredefinedType().format();
         break;
      case STRUCT:
         sDataType = this.formatStructType();
         break;
      case ROW:
         sDataType = this.formatRowType();
         break;
      case REF:
         sDataType = this.formatRefType();
         break;
      case ARRAY:
         sDataType = this.formatArrayType();
         break;
      case MULTISET:
         sDataType = this.formatMultisetType();
      }

      return sDataType;
   }

   public void parse(SqlParser.DataTypeContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.DataTypeContext ctx = null;

      try {
         ctx = this.getParser().dataType();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().dataType();
      }

      this.parse(ctx);
   }

   public void initPredefinedDataType(PredefinedType ptType) {
      this.initialize(DataType.Type.PRE, ptType, new QualifiedId(), new ArrayList(), new QualifiedId(), (DataType)null, -1);
   }

   public void initArrayType(PredefinedType ptBase, int iLength) {
      DataType dtBase = this.getSqlFactory().newDataType();
      dtBase.initPredefinedDataType(ptBase);
      this.initialize(DataType.Type.ARRAY, (PredefinedType)null, new QualifiedId(), new ArrayList(), new QualifiedId(), dtBase, iLength);
   }

   public void initStructType(QualifiedId qidTypeName) {
      this.initialize(DataType.Type.STRUCT, (PredefinedType)null, qidTypeName, new ArrayList(), new QualifiedId(), (DataType)null, -1);
   }

   public void initialize(DataType.Type type, PredefinedType pt, QualifiedId qUdtName, List<FieldDefinition> listFieldDefinitions, QualifiedId qScopeTable, DataType dtCollection, int iLength) {
      _il.enter(new Object[]{type, pt, qUdtName, listFieldDefinitions, qScopeTable, dtCollection, String.valueOf(iLength)});
      this.setType(type);
      this.setPredefinedType(pt);
      this.setUdtName(qUdtName);
      this.setFieldDefinitionList(listFieldDefinitions);
      this.setScopeTable(qScopeTable);
      this.setDataType(dtCollection);
      this.setLength(iLength);
      _il.exit();
   }

   public DataType(SqlFactory sf) {
      super(sf);
   }

   public static enum Type {
      PRE,
      STRUCT,
      ROW,
      REF,
      ARRAY,
      MULTISET;
   }

   protected class DtVisitor extends EnhancedSqlBaseVisitor<DataType> {
      public DataType visitDataType(SqlParser.DataTypeContext ctx) {
         this.visitChildren(ctx);
         return DataType.this;
      }

      public DataType visitPreType(SqlParser.PreTypeContext ctx) {
         DataType.this.setType(DataType.Type.PRE);
         DataType.this.setPredefinedType(DataType.this.getSqlFactory().newPredefinedType());
         DataType.this.getPredefinedType().parse(ctx.predefinedType());
         return DataType.this;
      }

      public DataType visitStructType(SqlParser.StructTypeContext ctx) {
         DataType.this.setType(DataType.Type.STRUCT);
         this.setUdtName(ctx.udtName(), DataType.this.getUdtName());
         return DataType.this;
      }

      public DataType visitRowType(SqlParser.RowTypeContext ctx) {
         DataType.this.setType(DataType.Type.ROW);
         return (DataType)this.visitChildren(ctx);
      }

      public DataType visitRefType(SqlParser.RefTypeContext ctx) {
         DataType.this.setType(DataType.Type.REF);
         this.setUdtName(ctx.udtName(), DataType.this.getUdtName());
         if (ctx.scopeDefinition() != null) {
            this.setTableName(ctx.scopeDefinition().tableName(), DataType.this.getScopeTable());
         }

         return DataType.this;
      }

      public DataType visitArrayType(SqlParser.ArrayTypeContext ctx) {
         DataType.this.setType(DataType.Type.ARRAY);
         DataType.this.setDataType(DataType.this.getSqlFactory().newDataType());
         DataType.this.getDataType().parse(ctx.dataType());
         if (ctx.length() != null) {
            DataType.this.setLength(Integer.parseInt(ctx.length().getText()));
         }

         return DataType.this;
      }

      public DataType visitMultisetType(SqlParser.MultisetTypeContext ctx) {
         DataType.this.setType(DataType.Type.MULTISET);
         DataType.this.setDataType(DataType.this.getSqlFactory().newDataType());
         DataType.this.getDataType().parse(ctx.dataType());
         return DataType.this;
      }

      public DataType visitFieldDefinition(SqlParser.FieldDefinitionContext ctx) {
         FieldDefinition fd = DataType.this.getSqlFactory().newFieldDefinition();
         fd.parse(ctx);
         DataType.this.addFieldDefinition(fd);
         return DataType.this;
      }
   }
}

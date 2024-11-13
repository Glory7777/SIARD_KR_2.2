package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.ddl.enums.MethodType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class MethodDesignator extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(MethodDesignator.class.getName());
   private MethodDesignator.MdVisitor _visitor = new MethodDesignator.MdVisitor();
   private MethodType _mt = null;
   private Identifier _idMethodName = new Identifier();
   private List<DataType> _listDataTypes = new ArrayList();

   private MethodDesignator.MdVisitor getVisitor() {
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

   public List<DataType> getDataTypes() {
      return this._listDataTypes;
   }

   private void setDataTypes(List<DataType> listDataTypes) {
      this._listDataTypes = listDataTypes;
   }

   private String formatDataTypes() {
      String sDataTypes = "(";

      for(int iDataType = 0; iDataType < this.getDataTypes().size(); ++iDataType) {
         if (iDataType > 0) {
            sDataTypes = sDataTypes + ",";
         }

         sDataTypes = sDataTypes + ((DataType)this.getDataTypes().get(iDataType)).format();
      }

      sDataTypes = sDataTypes + ")";
      return sDataTypes;
   }

   public String format() {
      String sDesignator = "";
      if (this.getMethodType() != null) {
         sDesignator = sDesignator + this.getMethodType().getKeywords() + " ";
      }

      sDesignator = sDesignator + this.getMethodName().format() + this.formatDataTypes();
      return sDesignator;
   }

   public void parse(SqlParser.MethodDesignatorContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().methodDesignator());
   }

   public void initialize(MethodType mt, Identifier idMethod, List<DataType> listDataTypes) {
      _il.enter(new Object[]{mt, idMethod, listDataTypes});
      this.setMethodType(mt);
      this.setMethodName(idMethod);
      this.setDataTypes(listDataTypes);
      _il.exit();
   }

   public MethodDesignator(SqlFactory sf) {
      super(sf);
   }

   private class MdVisitor extends EnhancedSqlBaseVisitor<MethodDesignator> {
      private MdVisitor() {
      }

      public MethodDesignator visitMethodDesignator(SqlParser.MethodDesignatorContext ctx) {
         this.setIdentifier(ctx.IDENTIFIER(), MethodDesignator.this.getMethodName());
         return (MethodDesignator)this.visitChildren(ctx);
      }

      public MethodDesignator visitMethodType(SqlParser.MethodTypeContext ctx) {
         MethodDesignator.this.setMethodType(this.getMethodType(ctx));
         return MethodDesignator.this;
      }

      public MethodDesignator visitDataType(SqlParser.DataTypeContext ctx) {
         DataType dt = MethodDesignator.this.getSqlFactory().newDataType();
         dt.parse(ctx);
         MethodDesignator.this.getDataTypes().add(dt);
         return MethodDesignator.this;
      }

      // $FF: synthetic method
      MdVisitor(Object x1) {
         this();
      }
   }
}

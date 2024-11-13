package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class SelectSublist extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SelectSublist.class.getName());
   private SelectSublist.SsVisitor _visitor = new SelectSublist.SsVisitor();
   private ValueExpression _ve = null;
   private IdChain _icAsteriskQualifier = new IdChain();
   private boolean _bAsterisk = false;
   private List<Identifier> _listColumnNames = new ArrayList();

   private SelectSublist.SsVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public IdChain getAsteriskQualifier() {
      return this._icAsteriskQualifier;
   }

   private void setAsteriskQualifier(IdChain icAsteriskQualifier) {
      this._icAsteriskQualifier = icAsteriskQualifier;
   }

   public boolean isAsterisk() {
      return this._bAsterisk;
   }

   public void setAsterisk(boolean bAsterisk) {
      this._bAsterisk = bAsterisk;
   }

   public List<Identifier> getColumnNames() {
      return this._listColumnNames;
   }

   private void setColumnNames(List<Identifier> listColumnNames) {
      this._listColumnNames = listColumnNames;
   }

   public String format() {
      String s = "";
      if (this.isAsterisk()) {
         if (this.getAsteriskQualifier().isSet()) {
            s = this.getAsteriskQualifier().format() + "." + "*";
         } else if (this.getValueExpression() != null) {
            s = this.getValueExpression().format() + "." + "*";
            if (this.getColumnNames().size() > 0) {
               s = s + " " + K.AS.getKeyword() + " " + "(";

               for(int i = 0; i < this.getColumnNames().size(); ++i) {
                  if (i > 0) {
                     s = s + "," + " ";
                  }

                  s = s + ((Identifier)this.getColumnNames().get(i)).format();
               }

               s = s + ")";
            }
         }
      } else {
         s = this.getValueExpression().format();
         if (this.getColumnNames().size() > 0) {
            s = s + " " + K.AS.getKeyword() + " " + ((Identifier)this.getColumnNames().get(0)).format();
         }
      }

      return s;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (!this.isAsterisk()) {
         dt = this.getValueExpression().getDataType(ss);
         return dt;
      } else {
         throw new IllegalArgumentException("Qualified asterisk is not supported for evaluation!");
      }
   }

   private Object evaluate(Object oExpressionValue) {
      Object oValue = null;
      if (!this.isAsterisk()) {
         return oExpressionValue;
      } else {
         throw new IllegalArgumentException("Qualified asterisk is not supported for evaluation!");
      }
   }

   public Object evaluate(SqlStatement ss) {
      Object oExpressionValue = null;
      if (this.getValueExpression() != null) {
         oExpressionValue = this.getValueExpression().evaluate(ss, false);
      }

      Object oValue = this.evaluate(oExpressionValue);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oExpressionValue = null;
      if (this.getValueExpression() != null) {
         oExpressionValue = this.getValueExpression().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oExpressionValue);
      return oValue;
   }

   public void parse(SqlParser.SelectSublistContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().selectSublist());
   }

   public void initialize(ValueExpression ve, IdChain icAsteriskQualifier, boolean bAsterisk, List<Identifier> listColumnNames) {
      _il.enter(new Object[]{ve, icAsteriskQualifier, String.valueOf(bAsterisk), listColumnNames});
      this.setValueExpression(ve);
      this.setAsteriskQualifier(icAsteriskQualifier);
      this.setColumnNames(listColumnNames);
      _il.exit();
   }

   public SelectSublist(SqlFactory sf) {
      super(sf);
   }

   private class SsVisitor extends EnhancedSqlBaseVisitor<SelectSublist> {
      private SsVisitor() {
      }

      public SelectSublist visitSelectSublist(SqlParser.SelectSublistContext ctx) {
         if (ctx.ASTERISK() != null) {
            SelectSublist.this.setAsterisk(true);
         }

         for(int i = 0; i < ctx.columnName().size(); ++i) {
            Identifier idColumnName = new Identifier();
            this.setColumnName(ctx.columnName(i), idColumnName);
            SelectSublist.this.getColumnNames().add(idColumnName);
         }

         if (ctx.valueExpression() != null) {
            SelectSublist.this.setValueExpression(SelectSublist.this.getSqlFactory().newValueExpression());
            SelectSublist.this.getValueExpression().parse(ctx.valueExpression());
         }

         if (ctx.identifierChain() != null) {
            this.setIdChain(ctx.identifierChain(), SelectSublist.this.getAsteriskQualifier());
         }

         return SelectSublist.this;
      }

      // $FF: synthetic method
      SsVisitor(Object x1) {
         this();
      }
   }
}

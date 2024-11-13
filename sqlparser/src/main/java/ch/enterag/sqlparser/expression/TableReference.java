package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.JoinOperator;
import ch.enterag.sqlparser.expression.enums.JoinType;
import ch.enterag.sqlparser.expression.enums.SampleMethod;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;
import java.util.List;

public class TableReference extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TableReference.class.getName());
   private TableReference.TrVisitor _visitor = new TableReference.TrVisitor();
   private TablePrimary _tp = null;
   private JoinOperator _jo = null;
   private TableReference _tr = null;
   private TableReference _tr2 = null;
   private JoinType _jt = null;
   private BooleanValueExpression _bve = null;
   private List<Identifier> _listJoinColumnNames = null;
   private SampleMethod _sm = null;
   private NumericValueExpression _nve = null;
   private NumericValueExpression _nveRep = null;

   private TableReference.TrVisitor getVisitor() {
      return this._visitor;
   }

   public TablePrimary getTablePrimary() {
      return this._tp;
   }

   public void setTablePrimary(TablePrimary tp) {
      this._tp = tp;
   }

   public JoinOperator getJoinOperator() {
      return this._jo;
   }

   public void setJoinOperator(JoinOperator jf) {
      this._jo = jf;
   }

   public TableReference getTableReference() {
      return this._tr;
   }

   public void setTableReference(TableReference tr) {
      this._tr = tr;
   }

   public TableReference getSecondTableReference() {
      return this._tr2;
   }

   public void setSecondTableReference(TableReference tr2) {
      this._tr2 = tr2;
   }

   public JoinType getJoinType() {
      return this._jt;
   }

   public void setJoinType(JoinType jt) {
      this._jt = jt;
   }

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public List<Identifier> getJoinColumnNames() {
      return this._listJoinColumnNames;
   }

   private void setJoinColumnNames(List<Identifier> listJoinColumnNames) {
      this._listJoinColumnNames = listJoinColumnNames;
   }

   public SampleMethod getSampleMethod() {
      return this._sm;
   }

   public void setSampleMethod(SampleMethod sm) {
      this._sm = sm;
   }

   public NumericValueExpression getNumericValueExpression() {
      return this._nve;
   }

   public void setNumericValueExpression(NumericValueExpression nve) {
      this._nve = nve;
   }

   public NumericValueExpression getRepeatableNumericValueExpression() {
      return this._nveRep;
   }

   public void setRepeatableNumericValueExpression(NumericValueExpression nveRep) {
      this._nveRep = nveRep;
   }

   public String format() {
      String s = null;
      if (this.getJoinOperator() != null) {
         switch(this.getJoinOperator()) {
         case CROSS_JOIN:
         case UNION:
            s = this.getTableReference().format() + " " + this.getJoinOperator().getKeywords() + " " + this.getTablePrimary().format();
            break;
         case NATURAL:
            s = this.getTableReference().format() + " " + this.getJoinOperator().getKeywords() + " " + this.getJoinType().getKeywords() + " " + K.JOIN.getKeyword() + " " + this.getTablePrimary().format();
            break;
         case JOIN:
            s = this.getTableReference().format() + " " + this.getJoinType().getKeywords() + " " + this.getJoinOperator() + " " + this.getSecondTableReference().format();
            if (this.getBooleanValueExpression() != null) {
               s = s + " " + K.ON.getKeyword() + " " + this.getBooleanValueExpression().format();
            } else {
               s = s + " " + K.USING.getKeyword() + "(";

               for(int i = 0; i < this.getJoinColumnNames().size(); ++i) {
                  if (i > 0) {
                     s = s + "," + " ";
                  }

                  s = s + ((Identifier)this.getJoinColumnNames().get(i)).format();
               }

               s = s + ")";
            }
         }
      } else if (this.getSampleMethod() != null && this.getNumericValueExpression() != null) {
         s = K.TABLESAMPLE.getKeyword() + " " + this.getSampleMethod().getKeywords() + "(" + this.getNumericValueExpression().format() + ")";
         if (this.getRepeatableNumericValueExpression() != null) {
            s = s + " " + K.REPEATABLE.getKeyword() + "(" + this.getRepeatableNumericValueExpression().format() + ")";
         }
      } else {
         s = this.getTablePrimary().format();
      }

      return s;
   }

   public void parse(SqlParser.TableReferenceContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().tableReference());
   }

   public void initialize(TablePrimary tp, JoinOperator jo, TableReference tr, TableReference tr2, JoinType jt, BooleanValueExpression bve, List<Identifier> listJoinColumnNames, SampleMethod sm, NumericValueExpression nve) {
      _il.enter(new Object[0]);
      this.setTablePrimary(tp);
      this.setJoinOperator(jo);
      this.setTableReference(tr);
      this.setSecondTableReference(tr2);
      this.setJoinType(jt);
      this.setBooleanValueExpression(bve);
      this.setJoinColumnNames(listJoinColumnNames);
      this.setSampleMethod(sm);
      this.setNumericValueExpression(nve);
      _il.exit();
   }

   public TableReference(SqlFactory sf) {
      super(sf);
   }

   private class TrVisitor extends EnhancedSqlBaseVisitor<TableReference> {
      private TrVisitor() {
      }

      public TableReference visitTableReference(SqlParser.TableReferenceContext ctx) {
         if (ctx.CROSS() != null) {
            TableReference.this.setJoinOperator(JoinOperator.CROSS_JOIN);
            TableReference.this.setTableReference(TableReference.this.getSqlFactory().newTableReference());
            TableReference.this.getTableReference().parse(ctx.tableReference(0));
            TableReference.this.setTablePrimary(TableReference.this.getSqlFactory().newTablePrimary());
            TableReference.this.getTablePrimary().parse(ctx.tablePrimary());
         } else if (ctx.NATURAL() != null) {
            TableReference.this.setJoinOperator(JoinOperator.NATURAL);
            TableReference.this.setJoinType(this.getJoinType(ctx.joinType()));
            TableReference.this.setTableReference(TableReference.this.getSqlFactory().newTableReference());
            TableReference.this.getTableReference().parse(ctx.tableReference(0));
            TableReference.this.setTablePrimary(TableReference.this.getSqlFactory().newTablePrimary());
            TableReference.this.getTablePrimary().parse(ctx.tablePrimary());
         } else if (ctx.UNION() != null) {
            TableReference.this.setJoinOperator(JoinOperator.UNION);
            TableReference.this.setTableReference(TableReference.this.getSqlFactory().newTableReference());
            TableReference.this.getTableReference().parse(ctx.tableReference(0));
            TableReference.this.setTablePrimary(TableReference.this.getSqlFactory().newTablePrimary());
            TableReference.this.getTablePrimary().parse(ctx.tablePrimary());
         } else if (ctx.JOIN() != null) {
            TableReference.this.setJoinOperator(JoinOperator.JOIN);
            TableReference.this.setJoinType(this.getJoinType(ctx.joinType()));
            TableReference.this.setTableReference(TableReference.this.getSqlFactory().newTableReference());
            TableReference.this.getTableReference().parse(ctx.tableReference(0));
            TableReference.this.setSecondTableReference(TableReference.this.getSqlFactory().newTableReference());
            TableReference.this.getSecondTableReference().parse(ctx.tableReference(1));
            if (ctx.joinSpecification().booleanValueExpression() != null) {
               TableReference.this.setBooleanValueExpression(TableReference.this.getSqlFactory().newBooleanValueExpression());
               TableReference.this.getBooleanValueExpression().parse(ctx.joinSpecification().booleanValueExpression());
            } else {
               for(int i = 0; i < ctx.joinSpecification().columnName().size(); ++i) {
                  Identifier idColumnName = new Identifier();
                  this.setColumnName(ctx.joinSpecification().columnName(i), idColumnName);
                  TableReference.this.getJoinColumnNames().add(idColumnName);
               }
            }
         } else if (ctx.TABLESAMPLE() != null && ctx.numericValueExpression().size() > 0) {
            TableReference.this.setSampleMethod(this.getSampleMethod(ctx.sampleMethod()));
            TableReference.this.setNumericValueExpression(TableReference.this.getSqlFactory().newNumericValueExpression());
            TableReference.this.getNumericValueExpression().parse(ctx.numericValueExpression(0));
            if (ctx.numericValueExpression().size() > 1) {
               TableReference.this.setRepeatableNumericValueExpression(TableReference.this.getSqlFactory().newNumericValueExpression());
               TableReference.this.getRepeatableNumericValueExpression().parse(ctx.numericValueExpression(1));
            }
         } else if (ctx.tablePrimary() != null) {
            TableReference.this.setTablePrimary(TableReference.this.getSqlFactory().newTablePrimary());
            TableReference.this.getTablePrimary().parse(ctx.tablePrimary());
         }

         return TableReference.this;
      }

      // $FF: synthetic method
      TrVisitor(Object x1) {
         this();
      }
   }
}

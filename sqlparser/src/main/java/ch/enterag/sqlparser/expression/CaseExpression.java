package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CaseExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CaseExpression.class.getName());
   private CaseExpression.CeVisitor _visitor = new CaseExpression.CeVisitor();
   private boolean _bNullIf = false;
   private ValueExpression _ve1 = null;
   private ValueExpression _ve2 = null;
   private boolean _bCoalesce = false;
   private List<ValueExpression> _listValueExpressions = new ArrayList();
   private boolean _bOverlaps = false;
   private RowValuePredicand _rvp = null;
   private List<WhenOperand> _listWhenOperands = new ArrayList();
   private List<BooleanValueExpression> _listBooleanValueExpressions = new ArrayList();
   private List<ValueExpression> _listWhenResults = new ArrayList();
   private ValueExpression _veElseResult = null;

   private CaseExpression.CeVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isNullIf() {
      return this._bNullIf;
   }

   public void setNullIf(boolean bNullIf) {
      this._bNullIf = bNullIf;
   }

   public ValueExpression getFirstValueExpression() {
      return this._ve1;
   }

   public void setFirstValueExpression(ValueExpression ve1) {
      this._ve1 = ve1;
   }

   public ValueExpression getSecondValueExpression() {
      return this._ve2;
   }

   public void setSecondValueExpression(ValueExpression ve2) {
      this._ve2 = ve2;
   }

   public boolean isCoalesce() {
      return this._bCoalesce;
   }

   public void setCoalesce(boolean bCoalesce) {
      this._bCoalesce = bCoalesce;
   }

   public List<ValueExpression> getValueExpressions() {
      return this._listValueExpressions;
   }

   private void setValueExpressions(List<ValueExpression> listValueExpressions) {
      this._listValueExpressions = listValueExpressions;
   }

   public boolean isOverlaps() {
      return this._bOverlaps;
   }

   public void setOverlaps(boolean bOverlaps) {
      this._bOverlaps = bOverlaps;
   }

   public RowValuePredicand getRowValuePredicand() {
      return this._rvp;
   }

   public void setRowValuePredicand(RowValuePredicand rvp) {
      this._rvp = rvp;
   }

   public List<WhenOperand> getWhenOperands() {
      return this._listWhenOperands;
   }

   private void setWhenOperands(List<WhenOperand> listWhenOperands) {
      this._listWhenOperands = listWhenOperands;
   }

   public List<BooleanValueExpression> getBooleanValueExpressions() {
      return this._listBooleanValueExpressions;
   }

   private void setBooleanValueExpressions(List<BooleanValueExpression> listBooleanValueExpressions) {
      this._listBooleanValueExpressions = listBooleanValueExpressions;
   }

   public List<ValueExpression> getWhenResults() {
      return this._listWhenResults;
   }

   private void setWhenResults(List<ValueExpression> listWhenResults) {
      this._listWhenResults = listWhenResults;
   }

   public ValueExpression getElseResult() {
      return this._veElseResult;
   }

   public void setElseResult(ValueExpression veElseResult) {
      this._veElseResult = veElseResult;
   }

   public String format() {
      String sExpression = "";
      if (this.isNullIf()) {
         sExpression = K.NULLIF.getKeyword() + "(" + this.getFirstValueExpression().format() + "," + " " + this.getSecondValueExpression().format() + ")";
      } else {
         int i;
         if (this.isCoalesce()) {
            sExpression = K.COALESCE.getKeyword() + "(";

            for(i = 0; i < this.getValueExpressions().size(); ++i) {
               if (i > 0) {
                  sExpression = sExpression + "," + " ";
               }

               sExpression = sExpression + ((ValueExpression)this.getValueExpressions().get(i)).format();
            }

            sExpression = sExpression + ")";
         } else {
            sExpression = K.CASE.getKeyword();
            if (this.getWhenOperands().size() > 0) {
               if (this.isOverlaps()) {
                  sExpression = sExpression + " " + K.OVERLAPS;
               }

               sExpression = sExpression + " " + this.getRowValuePredicand().format();

               for(i = 0; i < this.getWhenOperands().size(); ++i) {
                  sExpression = sExpression + "\r\n" + "  " + K.WHEN.getKeyword() + " " + ((WhenOperand)this.getWhenOperands().get(i)).format() + " " + K.THEN.getKeyword() + " " + ((ValueExpression)this.getWhenResults().get(i)).format();
               }
            } else if (this.getBooleanValueExpressions().size() > 0) {
               for(i = 0; i < this.getBooleanValueExpressions().size(); ++i) {
                  sExpression = sExpression + "\r\n" + "  " + K.WHEN.getKeyword() + " " + ((BooleanValueExpression)this.getBooleanValueExpressions().get(i)).format() + " " + K.THEN.getKeyword() + " " + ((ValueExpression)this.getWhenResults().get(i)).format();
               }
            }

            if (this.getElseResult() != null) {
               sExpression = sExpression + "\r\n" + "  " + K.ELSE.getKeyword() + " " + this.getElseResult().format();
            }

            sExpression = sExpression + "\r\n" + K.END.getKeyword();
         }
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      List<DataType> listTypes = new ArrayList();
      if (this.isNullIf()) {
         listTypes.add(this.getFirstValueExpression().getDataType(ss));
         listTypes.add(this.getSecondValueExpression().getDataType(ss));
      } else {
         int iValueExpression;
         if (this.isCoalesce()) {
            for(iValueExpression = 0; iValueExpression < this.getValueExpressions().size(); ++iValueExpression) {
               listTypes.add(((ValueExpression)this.getValueExpressions().get(iValueExpression)).getDataType(ss));
            }
         } else {
            for(iValueExpression = 0; iValueExpression < this.getWhenResults().size(); ++iValueExpression) {
               listTypes.add(((ValueExpression)this.getWhenResults().get(iValueExpression)).getDataType(ss));
            }

            if (this.getElseResult() != null) {
               listTypes.add(this.getElseResult().getDataType(ss));
            }
         }
      }

      dt = (DataType)listTypes.get(0);
      return dt;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oValue = null;
      Object oTestValue;
      Object o;
      if (this.isNullIf()) {
         oTestValue = this.getFirstValueExpression().evaluate(ss, bAggregated);
         o = this.getFirstValueExpression().evaluate(ss, bAggregated);
         if (!oTestValue.equals(o)) {
            oValue = oTestValue;
         }
      } else {
         int iBooleanValue;
         if (this.isCoalesce()) {
            for(iBooleanValue = 0; oValue == null && iBooleanValue < this.getValueExpressions().size(); ++iBooleanValue) {
               o = ((ValueExpression)this.getValueExpressions().get(iBooleanValue)).evaluate(ss, bAggregated);
               if (o != null) {
                  oValue = o;
               }
            }
         } else {
            if (this.getWhenOperands().size() > 0) {
               oTestValue = this.getRowValuePredicand().evaluate(ss, bAggregated);

               for(int iWhenOperand = 0; iWhenOperand < this.getWhenOperands().size(); ++iWhenOperand) {
                  Object oTestCondition = ((WhenOperand)this.getWhenOperands().get(iWhenOperand)).evaluate(ss, bAggregated);
                  if (oTestValue.equals(oTestCondition)) {
                     oValue = ((ValueExpression)this.getWhenResults().get(iWhenOperand)).evaluate(ss, bAggregated);
                  }
               }
            } else if (this.getBooleanValueExpressions().size() > 0) {
               for(iBooleanValue = 0; oValue == null && iBooleanValue < this.getBooleanValueExpressions().size(); ++iBooleanValue) {
                  Object oTest = ((BooleanValueExpression)this.getBooleanValueExpressions().get(iBooleanValue)).evaluate(ss, bAggregated);
                  if (Boolean.TRUE.equals((Boolean)oTest)) {
                     oValue = ((ValueExpression)this.getWhenResults().get(iBooleanValue)).evaluate(ss, bAggregated);
                  }
               }
            }

            if (oValue == null) {
               oValue = this.getElseResult().evaluate(ss, bAggregated);
            }
         }
      }

      return oValue;
   }

   public void parse(SqlParser.CaseExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().caseExpression());
   }

   public void initialize(boolean bNullIf, ValueExpression ve1, ValueExpression ve2, boolean bCoalesce, List<ValueExpression> listValueExpressions, boolean bOverlaps, RowValuePredicand rvp, List<WhenOperand> listWhenOperands, List<BooleanValueExpression> listBooleanValueExpressions, List<ValueExpression> listWhenResults, ValueExpression veElseResult) {
      _il.enter(new Object[]{String.valueOf(bNullIf), ve1, ve2, String.valueOf(bCoalesce), listValueExpressions, String.valueOf(bOverlaps), rvp, listWhenOperands, listBooleanValueExpressions, listWhenResults, veElseResult});
      this.setNullIf(bNullIf);
      this.setFirstValueExpression(ve1);
      this.setSecondValueExpression(ve2);
      this.setCoalesce(bCoalesce);
      this.setValueExpressions(listValueExpressions);
      this.setOverlaps(bOverlaps);
      this.setRowValuePredicand(rvp);
      this.setWhenOperands(listWhenOperands);
      this.setBooleanValueExpressions(listBooleanValueExpressions);
      this.setWhenResults(listWhenResults);
      this.setElseResult(veElseResult);
      _il.exit();
   }

   public CaseExpression(SqlFactory sf) {
      super(sf);
   }

   private class CeVisitor extends EnhancedSqlBaseVisitor<CaseExpression> {
      private CeVisitor() {
      }

      public CaseExpression visitCaseExpression(SqlParser.CaseExpressionContext ctx) {
         if (ctx.NULLIF() != null) {
            CaseExpression.this.setNullIf(true);
            CaseExpression.this.setFirstValueExpression(CaseExpression.this.getSqlFactory().newValueExpression());
            CaseExpression.this.getFirstValueExpression().parse(ctx.valueExpression(0));
            CaseExpression.this.setSecondValueExpression(CaseExpression.this.getSqlFactory().newValueExpression());
            CaseExpression.this.getSecondValueExpression().parse(ctx.valueExpression(1));
         } else {
            int i;
            if (ctx.COALESCE() != null) {
               CaseExpression.this.setCoalesce(true);

               for(i = 0; i < ctx.valueExpression().size(); ++i) {
                  ValueExpression ve = CaseExpression.this.getSqlFactory().newValueExpression();
                  ve.parse(ctx.valueExpression(i));
                  CaseExpression.this.getValueExpressions().add(ve);
               }
            } else {
               ValueExpression veResult;
               if (ctx.simpleWhenClause().size() > 0) {
                  if (ctx.OVERLAPS() != null) {
                     CaseExpression.this.setOverlaps(true);
                  }

                  CaseExpression.this.setRowValuePredicand(CaseExpression.this.getSqlFactory().newRowValuePredicand());
                  CaseExpression.this.getRowValuePredicand().parse(ctx.rowValuePredicand());

                  for(i = 0; i < ctx.simpleWhenClause().size(); ++i) {
                     WhenOperand wo = CaseExpression.this.getSqlFactory().newWhenOperand();
                     wo.parse(ctx.simpleWhenClause(i).whenOperand());
                     CaseExpression.this.getWhenOperands().add(wo);
                     veResult = CaseExpression.this.getSqlFactory().newValueExpression();
                     veResult.parse(ctx.simpleWhenClause(i).result().valueExpression());
                     CaseExpression.this.getWhenResults().add(veResult);
                  }

                  if (ctx.ELSE() != null) {
                     CaseExpression.this.setElseResult(CaseExpression.this.getSqlFactory().newValueExpression());
                     CaseExpression.this.getElseResult().parse(ctx.result().valueExpression());
                  }
               } else if (ctx.searchedWhenClause().size() > 0) {
                  for(i = 0; i < ctx.searchedWhenClause().size(); ++i) {
                     BooleanValueExpression bve = CaseExpression.this.getSqlFactory().newBooleanValueExpression();
                     bve.parse(ctx.searchedWhenClause(i).booleanValueExpression());
                     CaseExpression.this.getBooleanValueExpressions().add(bve);
                     veResult = CaseExpression.this.getSqlFactory().newValueExpression();
                     veResult.parse(ctx.searchedWhenClause(i).result().valueExpression());
                     CaseExpression.this.getWhenResults().add(veResult);
                  }

                  if (ctx.ELSE() != null) {
                     CaseExpression.this.setElseResult(CaseExpression.this.getSqlFactory().newValueExpression());
                     CaseExpression.this.getElseResult().parse(ctx.result().valueExpression());
                  }
               }
            }
         }

         return CaseExpression.this;
      }

      // $FF: synthetic method
      CeVisitor(Object x1) {
         this();
      }
   }
}

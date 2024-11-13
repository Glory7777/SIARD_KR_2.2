package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.datatype.enums.PreType;
import ch.enterag.sqlparser.expression.enums.BinarySetFunction;
import ch.enterag.sqlparser.expression.enums.InverseDistributionFunction;
import ch.enterag.sqlparser.expression.enums.RankFunction;
import ch.enterag.sqlparser.expression.enums.SetFunction;
import ch.enterag.sqlparser.expression.enums.SetQuantifier;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AggregateFunction extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(AggregateFunction.class.getName());
   private AggregateFunction.AfVisitor _visitor = new AggregateFunction.AfVisitor();
   private boolean _bCountFunction = false;
   private SetFunction _sf = null;
   private SetQuantifier _sq = null;
   private ValueExpression _ve = null;
   private BinarySetFunction _bsf = null;
   private NumericValueExpression _nveDependent = null;
   private NumericValueExpression _nveIndependent = null;
   private RankFunction _rf = null;
   private List<ValueExpression> _listRankArguments = new ArrayList();
   private InverseDistributionFunction _idf = null;
   private NumericValueExpression _nve = null;
   private List<SortSpecification> _listWithinGroupSortSpecifications = new ArrayList();
   private BooleanValueExpression _bveFilter = null;
   private Object _oValue = null;
   private int _iCount = 0;

   private AggregateFunction.AfVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isCountFunction() {
      return this._bCountFunction;
   }

   public void setCountFunction(boolean bCountFunction) {
      this._bCountFunction = bCountFunction;
      this.getSqlFactory().setCount(true);
   }

   public SetFunction getSetFunction() {
      return this._sf;
   }

   public void setSetFunction(SetFunction sf) {
      this._sf = sf;
   }

   public SetQuantifier getSetQuantifier() {
      return this._sq;
   }

   public void setSetQuantifier(SetQuantifier sq) {
      this._sq = sq;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public BinarySetFunction getBinarySetFunction() {
      return this._bsf;
   }

   public void setBinarySetFunction(BinarySetFunction bsf) {
      this._bsf = bsf;
   }

   public NumericValueExpression getDependent() {
      return this._nveDependent;
   }

   public void setDependent(NumericValueExpression nveDependent) {
      this._nveDependent = nveDependent;
   }

   public NumericValueExpression getIndependent() {
      return this._nveIndependent;
   }

   public void setIndependent(NumericValueExpression nveIndependent) {
      this._nveIndependent = nveIndependent;
   }

   public RankFunction getRankFunction() {
      return this._rf;
   }

   public void setRankFunction(RankFunction rf) {
      this._rf = rf;
   }

   public List<ValueExpression> getRankArguments() {
      return this._listRankArguments;
   }

   private void setRankArguments(List<ValueExpression> listRankArguments) {
      this._listRankArguments = listRankArguments;
   }

   public InverseDistributionFunction getInverseDistributionFunction() {
      return this._idf;
   }

   public void setInverseDistributionFunction(InverseDistributionFunction idf) {
      this._idf = idf;
   }

   public NumericValueExpression getNumericValueExpression() {
      return this._nve;
   }

   public void setNumericValueExpression(NumericValueExpression nve) {
      this._nve = nve;
   }

   public List<SortSpecification> getWithinGroupSortSpecifications() {
      return this._listWithinGroupSortSpecifications;
   }

   private void setWithinGroupSortSpecifications(List<SortSpecification> listWithinGroupSortSpecifications) {
      this._listWithinGroupSortSpecifications = listWithinGroupSortSpecifications;
   }

   public BooleanValueExpression getFilter() {
      return this._bveFilter;
   }

   public void setFilter(BooleanValueExpression bveFilter) {
      this._bveFilter = bveFilter;
   }

   private Object getValue() {
      return this._oValue;
   }

   private void setValue(Object oValue) {
      this._oValue = oValue;
   }

   private int getCount() {
      return this._iCount;
   }

   private void setCount(int iCount) {
      this._iCount = iCount;
   }

   public String format() {
      String sExpression = "";
      if (this.isCountFunction()) {
         sExpression = K.COUNT.getKeyword() + "(" + "*" + ")";
      } else if (this.getSetFunction() != null && this.getValueExpression() != null) {
         sExpression = this.getSetFunction().getKeywords() + "(";
         if (this.getSetQuantifier() != null) {
            sExpression = sExpression + this.getSetQuantifier().getKeywords() + " ";
         }

         sExpression = sExpression + this.getValueExpression().format() + ")";
      } else if (this.getBinarySetFunction() != null && this.getDependent() != null && this.getIndependent() != null) {
         sExpression = this.getBinarySetFunction().getKeywords() + "(" + this.getDependent().format() + "," + " " + this.getIndependent().format() + ")";
      } else {
         int i;
         if (this.getRankFunction() == null) {
            if (this.getInverseDistributionFunction() != null) {
               sExpression = sExpression + this.getInverseDistributionFunction().getKeywords() + "(" + this.getNumericValueExpression().format() + ")";
            }
         } else {
            sExpression = this.getRankFunction().getKeywords() + "(";

            for(i = 0; i < this.getRankArguments().size(); ++i) {
               if (i > 0) {
                  sExpression = sExpression + "," + " ";
               }

               sExpression = sExpression + ((ValueExpression)this.getRankArguments().get(i)).format();
            }

            sExpression = sExpression + ")";
         }

         if (this.getWithinGroupSortSpecifications().size() > 0) {
            sExpression = sExpression + " " + K.WITHIN.getKeyword() + " " + K.GROUP.getKeyword() + "(" + K.ORDER.getKeyword() + " " + K.BY.getKeyword() + " ";

            for(i = 0; i < this.getWithinGroupSortSpecifications().size(); ++i) {
               if (i > 0) {
                  sExpression = sExpression + "," + " ";
               }

               sExpression = sExpression + ((SortSpecification)this.getWithinGroupSortSpecifications().get(i)).format();
            }

            sExpression = sExpression + ")";
         }
      }

      if (this.getFilter() != null) {
         sExpression = sExpression + " " + K.FILTER.getKeyword() + "(" + K.WHERE.getKeyword() + " " + this.getFilter().format() + ")";
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      PredefinedType pt;
      if (this.isCountFunction()) {
         dt = this.getSqlFactory().newDataType();
         pt = this.getSqlFactory().newPredefinedType();
         dt.initPredefinedDataType(pt);
         pt.initBigIntType();
      } else {
         dt = this.getValueExpression().getDataType(ss);
         pt = dt.getPredefinedType();
         switch(this.getSetFunction()) {
         case AVG:
            if (pt != null && (pt.getType() == PreType.SMALLINT || pt.getType() == PreType.INTEGER || pt.getType() == PreType.BIGINT)) {
               pt.initDecimalType(-1, -1);
            }
         case SUM:
            if (pt != null && (pt.getType() == PreType.SMALLINT || pt.getType() == PreType.INTEGER || pt.getType() == PreType.BIGINT)) {
               pt.initBigIntType();
            }
         case MAX:
         case MIN:
            break;
         default:
            throw new IllegalArgumentException("Cannot evaluate set function " + this.format() + "!");
         }
      }

      return dt;
   }

   private int compare(Object o1, Object o2) {
      int iCompare = 0;
      Double d1;
      if (o1 instanceof BigDecimal && o2 instanceof Double) {
         d1 = (Double)o2;
         o2 = BigDecimal.valueOf(d1);
      }

      if (o1 instanceof Double && o2 instanceof BigDecimal) {
         d1 = (Double)o1;
         o1 = BigDecimal.valueOf(d1);
      }

      if (o1 instanceof Boolean && o2 instanceof Boolean) {
         Boolean b1 = (Boolean)o1;
         Boolean b2 = (Boolean)o2;
         iCompare = b1.compareTo(b2);
      } else if (o1 instanceof Double && o2 instanceof Double) {
         d1 = (Double)o1;
         Double d2 = (Double)o2;
         iCompare = d1.compareTo(d2);
      } else if (o1 instanceof BigDecimal && o2 instanceof BigDecimal) {
         BigDecimal bd1 = (BigDecimal)o1;
         BigDecimal bd2 = (BigDecimal)o2;
         iCompare = bd1.compareTo(bd2);
      } else if (o1 instanceof String && o2 instanceof String) {
         String s1 = (String)o1;
         String s2 = (String)o2;
         iCompare = s1.compareTo(s2);
      } else if (o1 instanceof Date && o2 instanceof Date) {
         Date date1 = (Date)o1;
         Date date2 = (Date)o2;
         iCompare = date1.compareTo(date2);
      } else if (o1 instanceof Time && o2 instanceof Time) {
         Time time1 = (Time)o1;
         Time time2 = (Time)o2;
         iCompare = time1.compareTo(time2);
      } else if (o1 instanceof Timestamp && o2 instanceof Timestamp) {
         Timestamp ts1 = (Timestamp)o1;
         Timestamp ts2 = (Timestamp)o2;
         iCompare = ts1.compareTo(ts2);
      } else {
         if (!(o1 instanceof byte[]) || !(o2 instanceof byte[])) {
            throw new IllegalArgumentException("Values cannot be compared!");
         }

         byte[] buf1 = (byte[])((byte[])o1);
         byte[] buf2 = (byte[])((byte[])o2);

         for(int i = 0; iCompare == 0 && i < buf1.length && i < buf2.length; ++i) {
            iCompare = Byte.compare(buf1[i], buf2[i]);
         }

         if (iCompare == 0) {
            iCompare = Integer.compare(buf1.length, buf2.length);
         }
      }

      return iCompare;
   }

   private Object evaluateSum(Object o1, Object o2) {
      Object oValue = null;
      Double d1 = null;
      Double d2 = null;
      BigDecimal bd1 = null;
      BigDecimal bd2 = null;
      if (o1 instanceof Double) {
         d1 = (Double)o1;
      } else {
         bd1 = (BigDecimal)o1;
      }

      if (o2 instanceof Double) {
         d2 = (Double)o2;
      } else {
         bd2 = (BigDecimal)o2;
      }

      if (bd1 == null && bd2 == null) {
         oValue = d1 + d2;
      } else {
         if (bd1 == null) {
            bd1 = BigDecimal.valueOf(d1);
         } else if (bd2 == null) {
            bd2 = BigDecimal.valueOf(d2);
         }

         oValue = bd1.add(bd2);
      }

      return oValue;
   }

   private Object evaluateAverage(Object o1, Object o2, int iCount) {
      Object oValue = null;
      Double d1 = null;
      Double d2 = null;
      BigDecimal bd1 = null;
      BigDecimal bd2 = null;
      if (o1 instanceof Double) {
         d1 = (Double)o1;
      } else {
         bd1 = (BigDecimal)o1;
      }

      if (o2 instanceof Double) {
         d2 = (Double)o2;
      } else {
         bd2 = (BigDecimal)o2;
      }

      if (bd1 == null && bd2 == null) {
         oValue = (d1 * (double)(iCount - 1) + d2) / (double)iCount;
      } else {
         if (bd1 == null) {
            bd1 = BigDecimal.valueOf(d1);
         } else if (bd2 == null) {
            bd2 = BigDecimal.valueOf(d2);
         }

         BigDecimal bd = bd1.multiply(BigDecimal.valueOf((long)(iCount - 1)));
         bd = bd.add(bd2);
         oValue = bd.divide(BigDecimal.valueOf((long)iCount));
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss) {
      Object oValue = null;
      this.setCount(this.getCount() + 1);
      if (this.isCountFunction()) {
         oValue = BigDecimal.valueOf((long)this.getCount());
      } else {
         if (this.getSetFunction() == null || this.getValueExpression() == null) {
            throw new IllegalArgumentException("Cannot evaluate complex aggregate function " + this.format() + "!");
         }

         oValue = this.getValueExpression().evaluate(ss, true);
         if (this.getValue() != null && oValue != null) {
            switch(this.getSetFunction()) {
            case AVG:
               oValue = this.evaluateAverage(this.getValue(), oValue, this.getCount());
               break;
            case SUM:
               oValue = this.evaluateSum(this.getValue(), oValue);
               break;
            case MAX:
               if (this.compare(this.getValue(), oValue) > 0) {
                  oValue = this.getValue();
               }
               break;
            case MIN:
               if (this.compare(this.getValue(), oValue) < 0) {
                  oValue = this.getValue();
               }
               break;
            default:
               throw new IllegalArgumentException("Cannot evaluate set function " + this.format() + "!");
            }
         }
      }

      this.setValue(oValue);
      return oValue;
   }

   public Object reset() {
      Object oValue = this.getValue();
      this.setValue((Object)null);
      this.setCount(0);
      return oValue;
   }

   public void parse(SqlParser.AggregateFunctionContext ctx) {
      this.getSqlFactory().setAggregates(true);
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().aggregateFunction());
   }

   public void initialize(boolean bCountFunction, SetFunction sf, SetQuantifier sq, BinarySetFunction bsf, NumericValueExpression nveDependent, NumericValueExpression nveIndependent, RankFunction rf, List<ValueExpression> listRankArguments, InverseDistributionFunction idf, NumericValueExpression nve, List<SortSpecification> listWithinGroupSortSpecifications) {
      _il.enter(new Object[]{String.valueOf(bCountFunction), sf, sq, bsf, nveDependent, nveIndependent, rf, listRankArguments, idf, nve, listWithinGroupSortSpecifications});
      this.setCountFunction(bCountFunction);
      this.setSetFunction(sf);
      this.setSetQuantifier(sq);
      this.setBinarySetFunction(bsf);
      this.setDependent(nveDependent);
      this.setIndependent(nveIndependent);
      this.setRankFunction(rf);
      this.setRankArguments(listRankArguments);
      this.setInverseDistributionFunction(idf);
      this.setNumericValueExpression(nve);
      this.setWithinGroupSortSpecifications(listWithinGroupSortSpecifications);
      _il.exit();
   }

   public AggregateFunction(SqlFactory sf) {
      super(sf);
   }

   private class AfVisitor extends EnhancedSqlBaseVisitor<AggregateFunction> {
      private AfVisitor() {
      }

      public AggregateFunction visitAggregateFunction(SqlParser.AggregateFunctionContext ctx) {
         if (ctx.COUNT() != null) {
            AggregateFunction.this.setCountFunction(true);
         }

         return (AggregateFunction)this.visitChildren(ctx);
      }

      public AggregateFunction visitSetFunction(SqlParser.SetFunctionContext ctx) {
         AggregateFunction.this.setSetFunction(this.getSetFunction(ctx));
         return AggregateFunction.this;
      }

      public AggregateFunction visitSetQuantifier(SqlParser.SetQuantifierContext ctx) {
         AggregateFunction.this.setSetQuantifier(this.getSetQuantifier(ctx));
         return AggregateFunction.this;
      }

      public AggregateFunction visitValueExpression(SqlParser.ValueExpressionContext ctx) {
         AggregateFunction.this.setValueExpression(AggregateFunction.this.getSqlFactory().newValueExpression());
         AggregateFunction.this.getValueExpression().parse(ctx);
         return AggregateFunction.this;
      }

      public AggregateFunction visitBinarySetFunction(SqlParser.BinarySetFunctionContext ctx) {
         AggregateFunction.this.setBinarySetFunction(this.getBinarySetFunction(ctx));
         return AggregateFunction.this;
      }

      public AggregateFunction visitDependentVariableExpression(SqlParser.DependentVariableExpressionContext ctx) {
         AggregateFunction.this.setDependent(AggregateFunction.this.getSqlFactory().newNumericValueExpression());
         AggregateFunction.this.getDependent().parse(ctx.numericValueExpression());
         return AggregateFunction.this;
      }

      public AggregateFunction visitIndependentVariableExpression(SqlParser.IndependentVariableExpressionContext ctx) {
         AggregateFunction.this.setIndependent(AggregateFunction.this.getSqlFactory().newNumericValueExpression());
         AggregateFunction.this.getIndependent().parse(ctx.numericValueExpression());
         return AggregateFunction.this;
      }

      public AggregateFunction visitRankFunction(SqlParser.RankFunctionContext ctx) {
         AggregateFunction.this.setRankFunction(this.getRankFunction(ctx));
         return AggregateFunction.this;
      }

      public AggregateFunction visitRankFunctionArgumentList(SqlParser.RankFunctionArgumentListContext ctx) {
         for(int i = 0; i < ctx.valueExpression().size(); ++i) {
            ValueExpression ve = AggregateFunction.this.getSqlFactory().newValueExpression();
            ve.parse(ctx.valueExpression(i));
            AggregateFunction.this.getRankArguments().add(ve);
         }

         return AggregateFunction.this;
      }

      public AggregateFunction visitInverseDistributionFunction(SqlParser.InverseDistributionFunctionContext ctx) {
         AggregateFunction.this.setInverseDistributionFunction(this.getInverseDistributionFunction(ctx));
         return AggregateFunction.this;
      }

      public AggregateFunction visitNumericValueExpression(SqlParser.NumericValueExpressionContext ctx) {
         AggregateFunction.this.setNumericValueExpression(AggregateFunction.this.getSqlFactory().newNumericValueExpression());
         AggregateFunction.this.getNumericValueExpression().parse(ctx);
         return AggregateFunction.this;
      }

      public AggregateFunction visitSortSpecificationList(SqlParser.SortSpecificationListContext ctx) {
         for(int i = 0; i < ctx.sortSpecification().size(); ++i) {
            SortSpecification ss = AggregateFunction.this.getSqlFactory().newSortSpecification();
            ss.parse(ctx.sortSpecification(i));
            AggregateFunction.this.getWithinGroupSortSpecifications().add(ss);
         }

         return AggregateFunction.this;
      }

      public AggregateFunction visitFilterClause(SqlParser.FilterClauseContext ctx) {
         AggregateFunction.this.setFilter(AggregateFunction.this.getSqlFactory().newBooleanValueExpression());
         AggregateFunction.this.getFilter().parse(ctx.booleanValueExpression());
         return AggregateFunction.this;
      }

      // $FF: synthetic method
      AfVisitor(Object x1) {
         this();
      }
   }
}

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
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class ValueExpressionPrimary extends SqlBase {
   protected static IndentLogger _il = IndentLogger.getIndentLogger(ValueExpressionPrimary.class.getName());
   private ValueExpressionPrimary.VepVisitor _visitor = new ValueExpressionPrimary.VepVisitor();
   private UnsignedLiteral _ul = null;
   private GeneralValueSpecification _gvs = null;
   private AggregateFunction _af = null;
   private List<IdChain> _listGroupingOp = new ArrayList();
   private WindowFunction _wf = null;
   private QueryExpression _qeScalarSubq = null;
   private CaseExpression _ce = null;
   private CastSpecification _cs = null;
   private Identifier _idFieldName = new Identifier();
   private SubtypeTreatment _st = null;
   private ValueExpressionPrimary _vepMethodRef = null;
   private Identifier _idMethodName = new Identifier();
   private boolean _bSqlArgumentList = false;
   private List<SqlArgument> _listSqlArguments = new ArrayList();
   private DataType _dt = null;
   private QualifiedId _qiUdtName = new QualifiedId();
   private boolean _bNew = false;
   private QualifiedId _qiRoutineName = new QualifiedId();
   private ValueExpressionPrimary _vepDeref = null;
   private Identifier _idAttributeOrMethodName = new Identifier();
   private ValueExpressionPrimary _vepReferenceResolution = null;
   private boolean _bArrayValueConstructor = false;
   private boolean _bMultisetValueConstructor = false;
   private boolean _bTableMultisetValueConstructor = false;
   private List<ValueExpression> _listCollectionValueComponents = new ArrayList();
   private QueryExpression _qe = null;
   private List<SortSpecification> _listSortSpecifications = new ArrayList();
   private ArrayValueExpression _ave1 = null;
   private ArrayValueExpression _ave2 = null;
   private NumericValueExpression _nve = null;
   private ValueExpressionPrimary _vepArray = null;
   private MultisetValueExpression _mve = null;
   private QualifiedId _qiSequenceName = new QualifiedId();
   private ValueExpressionPrimary _vep = null;

   private ValueExpressionPrimary.VepVisitor getVisitor() {
      return this._visitor;
   }

   public UnsignedLiteral getUnsignedLit() {
      return this._ul;
   }

   public void setUnsignedLit(UnsignedLiteral ul) {
      this._ul = ul;
   }

   public GeneralValueSpecification getGeneralValueSpecification() {
      return this._gvs;
   }

   public void setGeneralValueSpecification(GeneralValueSpecification gvs) {
      this._gvs = gvs;
   }

   public AggregateFunction getAggregateFunc() {
      return this._af;
   }

   public void setAggregateFunc(AggregateFunction af) {
      this._af = af;
   }

   public List<IdChain> getGroupingOp() {
      return this._listGroupingOp;
   }

   private void setGroupingOp(List<IdChain> listGroupingOp) {
      this._listGroupingOp = listGroupingOp;
   }

   public WindowFunction getWindowFunc() {
      return this._wf;
   }

   public void setWindowFunc(WindowFunction wf) {
      this._wf = wf;
   }

   public QueryExpression getScalarSubq() {
      return this._qeScalarSubq;
   }

   public void setScalarSubq(QueryExpression qeScalarSubq) {
      this._qeScalarSubq = qeScalarSubq;
   }

   public CaseExpression getCaseExpression() {
      return this._ce;
   }

   public void setCaseExpression(CaseExpression ce) {
      this._ce = ce;
   }

   public CastSpecification getCastSpecification() {
      return this._cs;
   }

   public void setCastSpecification(CastSpecification cs) {
      this._cs = cs;
   }

   public Identifier getFieldName() {
      return this._idFieldName;
   }

   private void setFieldName(Identifier idFieldName) {
      this._idFieldName = idFieldName;
   }

   public SubtypeTreatment getSubtypeTreatment() {
      return this._st;
   }

   public void setSubtypeTreatment(SubtypeTreatment st) {
      this._st = st;
   }

   public ValueExpressionPrimary getMethodQualifier() {
      return this._vepMethodRef;
   }

   public void setMethodQualifier(ValueExpressionPrimary vepMethodRef) {
      this._vepMethodRef = vepMethodRef;
   }

   public Identifier getMethodName() {
      return this._idMethodName;
   }

   private void setMethodName(Identifier idMethodName) {
      this._idMethodName = idMethodName;
   }

   public boolean isSqlArgumentList() {
      return this._bSqlArgumentList;
   }

   public void setSqlArgumentList(boolean bSqlArgumentList) {
      this._bSqlArgumentList = bSqlArgumentList;
   }

   public List<SqlArgument> getSqlArguments() {
      return this._listSqlArguments;
   }

   private void setSqlArguments(List<SqlArgument> listSqlArguments) {
      this._listSqlArguments = listSqlArguments;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public QualifiedId getUdtName() {
      return this._qiUdtName;
   }

   private void setUdtName(QualifiedId qiUdtName) {
      this._qiUdtName = qiUdtName;
   }

   public boolean isNew() {
      return this._bNew;
   }

   public void setNew(boolean bNew) {
      this._bNew = bNew;
   }

   public QualifiedId getRoutineName() {
      return this._qiRoutineName;
   }

   protected void setRoutineName(QualifiedId qiRoutineName) {
      this._qiRoutineName = qiRoutineName;
   }

   public ValueExpressionPrimary getDeref() {
      return this._vepDeref;
   }

   public void setDeref(ValueExpressionPrimary vepDeref) {
      this._vepDeref = vepDeref;
   }

   public Identifier getAttributeOrMethodName() {
      return this._idAttributeOrMethodName;
   }

   private void setAttributeOrMethodName(Identifier idAttributeOrMethodName) {
      this._idAttributeOrMethodName = idAttributeOrMethodName;
   }

   public ValueExpressionPrimary getReferenceResolution() {
      return this._vepReferenceResolution;
   }

   public void setReferenceResolution(ValueExpressionPrimary vepReferenceResolution) {
      this._vepReferenceResolution = vepReferenceResolution;
   }

   public boolean isArrayValueConstructor() {
      return this._bArrayValueConstructor;
   }

   public void setArrayValueConstructor(boolean bArrayValueConstructor) {
      this._bArrayValueConstructor = bArrayValueConstructor;
   }

   public boolean isMultisetValueConstructor() {
      return this._bMultisetValueConstructor;
   }

   public void setMultisetValueConstructor(boolean bMultisetValueConstructor) {
      this._bMultisetValueConstructor = bMultisetValueConstructor;
   }

   public boolean isTableMultisetValueConstructor() {
      return this._bTableMultisetValueConstructor;
   }

   public void setTableMultisetValueConstructor(boolean bTableMultisetValueConstructor) {
      this._bTableMultisetValueConstructor = bTableMultisetValueConstructor;
   }

   public List<ValueExpression> getCollectionValueComponents() {
      return this._listCollectionValueComponents;
   }

   private void setCollectionValueComponents(List<ValueExpression> listCollectionValueComponents) {
      this._listCollectionValueComponents = listCollectionValueComponents;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public List<SortSpecification> getSortSpecifications() {
      return this._listSortSpecifications;
   }

   private void setSortSpecifications(List<SortSpecification> listSortSpecifications) {
      this._listSortSpecifications = listSortSpecifications;
   }

   public ArrayValueExpression getFirstArrayValueExpression() {
      return this._ave1;
   }

   public void setFirstArrayValueExpression(ArrayValueExpression ave1) {
      this._ave1 = ave1;
   }

   public ArrayValueExpression getSecondArrayValueExpression() {
      return this._ave2;
   }

   public void setSecondArrayValueExpression(ArrayValueExpression ave2) {
      this._ave2 = ave2;
   }

   public NumericValueExpression getNumericValueExpression() {
      return this._nve;
   }

   public void setNumericValueExpression(NumericValueExpression nve) {
      this._nve = nve;
   }

   public ValueExpressionPrimary getArray() {
      return this._vepArray;
   }

   public void setArray(ValueExpressionPrimary vepArray) {
      this._vepArray = vepArray;
   }

   public MultisetValueExpression getMultisetValueExpression() {
      return this._mve;
   }

   public void setMultisetValueExpression(MultisetValueExpression mve) {
      this._mve = mve;
   }

   public QualifiedId getSequenceName() {
      return this._qiSequenceName;
   }

   private void setSequenceName(QualifiedId qiSequenceName) {
      this._qiSequenceName = qiSequenceName;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   protected String formatSqlArguments() {
      String s = "";
      s = "(";

      for(int i = 0; i < this.getSqlArguments().size(); ++i) {
         if (i > 0) {
            s = s + "," + " ";
         }

         s = s + ((SqlArgument)this.getSqlArguments().get(i)).format();
      }

      s = s + ")";
      return s;
   }

   protected String formatCollectionValueComponents() {
      String s = "";
      if (this.getCollectionValueComponents().size() > 0) {
         s = "[";

         for(int i = 0; i < this.getCollectionValueComponents().size(); ++i) {
            if (i > 0) {
               s = s + "," + " ";
            }

            s = s + ((ValueExpression)this.getCollectionValueComponents().get(i)).format();
         }

         s = s + "]";
      }

      return s;
   }

   public String format() {
      String sExpression = "";
      if (this.getUnsignedLit() != null) {
         sExpression = this.getUnsignedLit().format();
      } else if (this.getGeneralValueSpecification() != null) {
         sExpression = this.getGeneralValueSpecification().format();
      } else if (this.getAggregateFunc() != null) {
         sExpression = this.getAggregateFunc().format();
      } else {
         int i;
         if (this.getGroupingOp().size() > 0) {
            sExpression = K.GROUPING.getKeyword() + "(";

            for(i = 0; i < this.getGroupingOp().size(); ++i) {
               if (i > 0) {
                  sExpression = sExpression + "," + " ";
               }

               sExpression = sExpression + ((IdChain)this.getGroupingOp().get(i)).format();
            }

            sExpression = sExpression + ")";
         } else if (this.getWindowFunc() != null) {
            sExpression = this.getWindowFunc().format();
         } else if (this.getScalarSubq() != null) {
            sExpression = "(" + this.getScalarSubq().format() + ")";
         } else if (this.getCaseExpression() != null) {
            sExpression = this.getCaseExpression().format();
         } else if (this.getCastSpecification() != null) {
            sExpression = this.getCastSpecification().format();
         } else if (this.getSubtypeTreatment() != null) {
            sExpression = this.getSubtypeTreatment().format();
         } else if (this.getMethodQualifier() != null && this.getDataType() != null && this.getMethodName().isSet()) {
            sExpression = "(" + this.getMethodQualifier().format() + " " + K.AS.getKeyword() + " " + this.getDataType().format() + ")" + "." + this.getMethodName().format();
            if (this.isSqlArgumentList()) {
               sExpression = sExpression + this.formatSqlArguments();
            }
         } else if (this.getMethodQualifier() != null && this.getMethodName().isSet()) {
            sExpression = this.getMethodQualifier().format() + "." + this.getMethodName().format();
            if (this.isSqlArgumentList()) {
               sExpression = sExpression + this.formatSqlArguments();
            }
         } else if (this.getUdtName().isSet() && this.getMethodName().isSet()) {
            sExpression = this.getUdtName().format() + "::" + this.getMethodName().format();
            if (this.isSqlArgumentList()) {
               sExpression = sExpression + this.formatSqlArguments();
            }
         } else if (this.isNew() && this.getRoutineName().isSet()) {
            sExpression = K.NEW.getKeyword() + " " + this.getRoutineName().format() + this.formatSqlArguments();
         } else if (this.getDeref() != null && this.getAttributeOrMethodName().isSet()) {
            sExpression = this.getDeref().format() + "->" + this.getAttributeOrMethodName().format();
            if (this.isSqlArgumentList()) {
               sExpression = sExpression + this.formatSqlArguments();
            }
         } else if (this.getReferenceResolution() != null) {
            sExpression = K.DEREF.getKeyword() + "(" + this.getReferenceResolution().format() + ")";
         } else if (this.isArrayValueConstructor()) {
            sExpression = K.ARRAY.getKeyword();
            if (this.getQueryExpression() != null) {
               sExpression = sExpression + "(" + this.getQueryExpression().format();
               if (this.getSortSpecifications().size() > 0) {
                  sExpression = sExpression + "\r\n" + K.ORDER.getKeyword() + " " + K.BY.getKeyword() + " ";

                  for(i = 0; i < this.getSortSpecifications().size(); ++i) {
                     if (i > 0) {
                        sExpression = sExpression + "," + " ";
                     }

                     sExpression = sExpression + ((SortSpecification)this.getSortSpecifications().get(i)).format();
                  }
               }

               sExpression = sExpression + ")";
            } else {
               sExpression = sExpression + this.formatCollectionValueComponents();
            }
         } else if (this.isMultisetValueConstructor()) {
            if (this.isTableMultisetValueConstructor()) {
               sExpression = K.TABLE.getKeyword();
            } else {
               sExpression = K.MULTISET.getKeyword();
            }

            if (this.getQueryExpression() != null) {
               sExpression = sExpression + "(" + this.getQueryExpression().format() + ")";
            } else {
               sExpression = sExpression + this.formatCollectionValueComponents();
            }
         } else if (this.getFirstArrayValueExpression() != null && this.getSecondArrayValueExpression() != null && this.getNumericValueExpression() != null) {
            sExpression = "(" + this.getFirstArrayValueExpression().format() + " " + "||" + " " + this.getSecondArrayValueExpression().format() + ")" + "[" + this.getNumericValueExpression().format() + "]";
         } else if (this.getArray() != null && this.getNumericValueExpression() != null) {
            sExpression = this.getArray().format() + "[" + this.getNumericValueExpression().format() + "]";
         } else if (this.getMultisetValueExpression() != null) {
            sExpression = K.ELEMENT.getKeyword() + "(" + this.getMultisetValueExpression().format() + ")";
         } else if (this.getRoutineName().isSet()) {
            sExpression = this.getRoutineName().format() + this.formatSqlArguments();
         } else if (this.getSequenceName().isSet()) {
            sExpression = K.NEXT.getKeyword() + " " + K.VALUE.getKeyword() + " " + K.FOR.getKeyword() + " " + this.getSequenceName().format();
         } else if (this.getValueExpressionPrimary() != null) {
            sExpression = "(" + this.getValueExpressionPrimary().format() + ")";
         }
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getUnsignedLit() != null) {
         dt = this.getUnsignedLit().getDataType();
      } else if (this.getGeneralValueSpecification() != null) {
         dt = this.getGeneralValueSpecification().getDataType(ss);
      } else if (this.getCaseExpression() != null) {
         dt = this.getCaseExpression().getDataType(ss);
      } else {
         if (this.getAggregateFunc() == null) {
            throw new IllegalArgumentException("Type of ValueExpressionPrimary not supported for evaluation!");
         }

         dt = this.getAggregateFunc().getDataType(ss);
      }

      return dt;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oValue = null;
      if (this.getUnsignedLit() != null) {
         oValue = this.getUnsignedLit().evaluate();
      } else if (this.getGeneralValueSpecification() != null) {
         oValue = this.getGeneralValueSpecification().evaluate(ss, bAggregated);
      } else if (this.getAggregateFunc() != null) {
         if (bAggregated) {
            throw new IllegalArgumentException("Aggregate function must not be referenced within argument of an aggregate function!");
         }

         oValue = this.getAggregateFunc().evaluate(ss);
      } else {
         if (this.getCollectionValueComponents() == null) {
            throw new IllegalArgumentException("Type of ValueExpressionPrimary not supported for evaluation!");
         }

         List<Object> listValues = new ArrayList();

         for(int iElement = 0; iElement < this.getCollectionValueComponents().size(); ++iElement) {
            ValueExpression ve = (ValueExpression)this.getCollectionValueComponents().get(iElement);
            listValues.add(ve.evaluate(ss, bAggregated));
         }

         oValue = listValues;
      }

      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oValue = null;
      if (this.getAggregateFunc() != null) {
         oValue = this.getAggregateFunc().reset();
      } else {
         oValue = this.evaluate(ss, true);
      }

      return oValue;
   }

   public void parse(SqlParser.ValueExpressionPrimaryContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.ValueExpressionPrimaryContext ctx = null;

      try {
         ctx = this.getParser().valueExpressionPrimary();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().valueExpressionPrimary();
      }

      this.parse(ctx);
   }

   public void initialize(UnsignedLiteral ul, GeneralValueSpecification gvs, AggregateFunction af, List<IdChain> listGroupingOp, WindowFunction wf, QueryExpression qeScalarSubq, CaseExpression ce, CastSpecification cs, Identifier idFieldName, SubtypeTreatment st, ValueExpressionPrimary vepMethodRef, Identifier idMethodName, List<SqlArgument> listSqlArguments, DataType dt, QualifiedId qiUdtName, boolean bNew, QualifiedId qiRoutineName, ValueExpressionPrimary vepDeref, Identifier idAttributeOrMethodName, ValueExpressionPrimary vepReferenceResolution, boolean bArrayValueConstructor, boolean bMultisetValueConstructor, boolean bTableMultisetValueConstructor, List<ValueExpression> listCollectionValueComponents, QueryExpression qe, List<SortSpecification> listSortSpecifications, ArrayValueExpression ave1, ArrayValueExpression ave2, NumericValueExpression nve, ValueExpressionPrimary vepArray, MultisetValueExpression mve, QualifiedId qiSequenceName, ValueExpressionPrimary vep) {
      _il.enter(new Object[0]);
      this.setUnsignedLit(ul);
      this.setGeneralValueSpecification(gvs);
      this.setAggregateFunc(af);
      this.setGroupingOp(listGroupingOp);
      this.setWindowFunc(wf);
      this.setScalarSubq(qeScalarSubq);
      this.setCaseExpression(ce);
      this.setCastSpecification(cs);
      this.setFieldName(idFieldName);
      this.setSubtypeTreatment(st);
      this.setMethodQualifier(vepMethodRef);
      this.setMethodName(idMethodName);
      this.setSqlArguments(listSqlArguments);
      this.setDataType(dt);
      this.setUdtName(qiUdtName);
      this.setNew(bNew);
      this.setRoutineName(qiRoutineName);
      this.setDeref(vepDeref);
      this.setAttributeOrMethodName(idAttributeOrMethodName);
      this.setReferenceResolution(vepReferenceResolution);
      this.setArrayValueConstructor(bArrayValueConstructor);
      this.setMultisetValueConstructor(bMultisetValueConstructor);
      this.setTableMultisetValueConstructor(bTableMultisetValueConstructor);
      this.setCollectionValueComponents(listCollectionValueComponents);
      this.setQueryExpression(qe);
      this.setSortSpecifications(listSortSpecifications);
      this.setFirstArrayValueExpression(ave1);
      this.setSecondArrayValueExpression(ave2);
      this.setNumericValueExpression(nve);
      this.setArray(vepArray);
      this.setMultisetValueExpression(mve);
      this.setSequenceName(qiSequenceName);
      this.setValueExpressionPrimary(vep);
      _il.exit();
   }

   public void initUnsignedLiteral(UnsignedLiteral ul) {
      _il.enter(new Object[0]);
      this.setUnsignedLit(ul);
      _il.exit();
   }

   public void initArrayValueConstructor(List<ValueExpression> listArrayValueComponents) {
      _il.enter(new Object[0]);
      this.setArrayValueConstructor(true);
      this.setCollectionValueComponents(listArrayValueComponents);
      _il.exit();
   }

   public void initUdtValueConstructor(QualifiedId qiUdt, List<SqlArgument> listAttributeValues) {
      _il.enter(new Object[0]);
      this.setNew(true);
      this.setRoutineName(qiUdt);
      this.setSqlArguments(listAttributeValues);
      _il.exit();
   }

   public void initGeneralValueSpecification(GeneralValueSpecification gvs) {
      _il.enter(new Object[0]);
      this.setGeneralValueSpecification(gvs);
      _il.exit();
   }

   public ValueExpressionPrimary(SqlFactory sf) {
      super(sf);
   }

   private class VepVisitor extends EnhancedSqlBaseVisitor<ValueExpressionPrimary> {
      private VepVisitor() {
      }

      private void getSqlArgumentList(SqlParser.SqlArgumentListContext ctx, List<SqlArgument> listSqlArguments) {
         ValueExpressionPrimary.this.setSqlArgumentList(true);

         for(int i = 0; i < ctx.sqlArgument().size(); ++i) {
            SqlArgument sa = ValueExpressionPrimary.this.getSqlFactory().newSqlArgument();
            sa.parse(ctx.sqlArgument(i));
            listSqlArguments.add(sa);
         }

      }

      private void getCollectionValueComponentList(List<SqlParser.ValueExpressionContext> listCtx) {
         for(int i = 0; i < listCtx.size(); ++i) {
            ValueExpression ve = ValueExpressionPrimary.this.getSqlFactory().newValueExpression();
            ve.parse((SqlParser.ValueExpressionContext)listCtx.get(i));
            ValueExpressionPrimary.this.getCollectionValueComponents().add(ve);
         }

      }

      public ValueExpressionPrimary visitUnsignedLiteral(SqlParser.UnsignedLiteralContext ctx) {
         ValueExpressionPrimary.this.setUnsignedLit(ValueExpressionPrimary.this.getSqlFactory().newUnsignedLiteral());
         ValueExpressionPrimary.this.getUnsignedLit().parse(ctx);
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitGeneralValueSpec(SqlParser.GeneralValueSpecContext ctx) {
         ValueExpressionPrimary.this.setGeneralValueSpecification(ValueExpressionPrimary.this.getSqlFactory().newGeneralValueSpecification());
         ValueExpressionPrimary.this.getGeneralValueSpecification().parse(ctx.generalValueSpecification());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitAggregateFunc(SqlParser.AggregateFuncContext ctx) {
         ValueExpressionPrimary.this.setAggregateFunc(ValueExpressionPrimary.this.getSqlFactory().newAggregateFunction());
         ValueExpressionPrimary.this.getAggregateFunc().parse(ctx.aggregateFunction());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitGroupingOp(SqlParser.GroupingOpContext ctx) {
         for(int i = 0; i < ctx.groupingOperation().columnReference().size(); ++i) {
            IdChain idcColumnReference = new IdChain();
            this.setIdChain(ctx.groupingOperation().columnReference(i).identifierChain(), idcColumnReference);
            ValueExpressionPrimary.this.getGroupingOp().add(idcColumnReference);
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitWindowFunc(SqlParser.WindowFuncContext ctx) {
         ValueExpressionPrimary.this.setWindowFunc(ValueExpressionPrimary.this.getSqlFactory().newWindowFunction());
         ValueExpressionPrimary.this.getWindowFunc().parse(ctx.windowFunction());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitScalarSubq(SqlParser.ScalarSubqContext ctx) {
         ValueExpressionPrimary.this.setScalarSubq(ValueExpressionPrimary.this.getSqlFactory().newQueryExpression());
         ValueExpressionPrimary.this.getScalarSubq().parse(ctx.scalarSubquery().queryExpression());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitCaseExp(SqlParser.CaseExpContext ctx) {
         ValueExpressionPrimary.this.setCaseExpression(ValueExpressionPrimary.this.getSqlFactory().newCaseExpression());
         ValueExpressionPrimary.this.getCaseExpression().parse(ctx.caseExpression());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitCastSpec(SqlParser.CastSpecContext ctx) {
         ValueExpressionPrimary.this.setCastSpecification(ValueExpressionPrimary.this.getSqlFactory().newCastSpecification());
         ValueExpressionPrimary.this.getCastSpecification().parse(ctx.castSpecification());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitSubtypeTreat(SqlParser.SubtypeTreatContext ctx) {
         ValueExpressionPrimary.this.setSubtypeTreatment(ValueExpressionPrimary.this.getSqlFactory().newSubtypeTreatment());
         ValueExpressionPrimary.this.getSubtypeTreatment().parse(ctx.subtypeTreatment());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitMethodInvoc(SqlParser.MethodInvocContext ctx) {
         ValueExpressionPrimary.this.setMethodQualifier(ValueExpressionPrimary.this.getSqlFactory().newValueExpressionPrimary());
         ValueExpressionPrimary.this.getMethodQualifier().parse(ctx.valueExpressionPrimary());
         this.setIdentifier(ctx.methodName().IDENTIFIER(), ValueExpressionPrimary.this.getMethodName());
         if (ctx.sqlArgumentList() != null) {
            this.getSqlArgumentList(ctx.sqlArgumentList(), ValueExpressionPrimary.this.getSqlArguments());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitGeneralizedMethodInvoc(SqlParser.GeneralizedMethodInvocContext ctx) {
         ValueExpressionPrimary.this.setMethodQualifier(ValueExpressionPrimary.this.getSqlFactory().newValueExpressionPrimary());
         ValueExpressionPrimary.this.getMethodQualifier().parse(ctx.generalizedInvocation().valueExpressionPrimary());
         ValueExpressionPrimary.this.setDataType(ValueExpressionPrimary.this.getSqlFactory().newDataType());
         ValueExpressionPrimary.this.getDataType().parse(ctx.generalizedInvocation().dataType());
         this.setIdentifier(ctx.generalizedInvocation().methodName().IDENTIFIER(), ValueExpressionPrimary.this.getMethodName());
         if (ctx.generalizedInvocation().sqlArgumentList() != null) {
            this.getSqlArgumentList(ctx.generalizedInvocation().sqlArgumentList(), ValueExpressionPrimary.this.getSqlArguments());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitStaticMethodInvoc(SqlParser.StaticMethodInvocContext ctx) {
         this.setUdtName(ctx.staticMethodInvocation().udtName(), ValueExpressionPrimary.this.getUdtName());
         this.setIdentifier(ctx.staticMethodInvocation().methodName().IDENTIFIER(), ValueExpressionPrimary.this.getMethodName());
         if (ctx.staticMethodInvocation().sqlArgumentList() != null) {
            this.getSqlArgumentList(ctx.staticMethodInvocation().sqlArgumentList(), ValueExpressionPrimary.this.getSqlArguments());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitNewSpec(SqlParser.NewSpecContext ctx) {
         ValueExpressionPrimary.this.setNew(true);
         this.setRoutineName(ctx.newSpecification().routineInvocation().routineName(), ValueExpressionPrimary.this.getRoutineName());
         if (ctx.newSpecification().routineInvocation().sqlArgumentList() != null) {
            this.getSqlArgumentList(ctx.newSpecification().routineInvocation().sqlArgumentList(), ValueExpressionPrimary.this.getSqlArguments());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitAttributeOrMethodRef(SqlParser.AttributeOrMethodRefContext ctx) {
         ValueExpressionPrimary.this.setDeref(ValueExpressionPrimary.this.getSqlFactory().newValueExpressionPrimary());
         ValueExpressionPrimary.this.getDeref().parse(ctx.valueExpressionPrimary());
         this.setIdentifier(ctx.IDENTIFIER(), ValueExpressionPrimary.this.getAttributeOrMethodName());
         if (ctx.sqlArgumentList() != null) {
            this.getSqlArgumentList(ctx.sqlArgumentList(), ValueExpressionPrimary.this.getSqlArguments());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitReferenceRes(SqlParser.ReferenceResContext ctx) {
         ValueExpressionPrimary.this.setReferenceResolution(ValueExpressionPrimary.this.getSqlFactory().newValueExpressionPrimary());
         ValueExpressionPrimary.this.getReferenceResolution().parse(ctx.referenceResolution().valueExpressionPrimary());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitArrayValueConstruct(SqlParser.ArrayValueConstructContext ctx) {
         ValueExpressionPrimary.this.setArrayValueConstructor(true);
         if (ctx.arrayValueConstructor().queryExpression() != null) {
            ValueExpressionPrimary.this.setQueryExpression(ValueExpressionPrimary.this.getSqlFactory().newQueryExpression());
            ValueExpressionPrimary.this.getQueryExpression().parse(ctx.arrayValueConstructor().queryExpression());
            if (ctx.arrayValueConstructor().sortSpecificationList() != null) {
               for(int i = 0; i < ctx.arrayValueConstructor().sortSpecificationList().sortSpecification().size(); ++i) {
                  SortSpecification ss = ValueExpressionPrimary.this.getSqlFactory().newSortSpecification();
                  ss.parse(ctx.arrayValueConstructor().sortSpecificationList().sortSpecification(i));
                  ValueExpressionPrimary.this.getSortSpecifications().add(ss);
               }
            }
         } else {
            this.getCollectionValueComponentList(ctx.arrayValueConstructor().valueExpression());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitMultisetValueConstruct(SqlParser.MultisetValueConstructContext ctx) {
         ValueExpressionPrimary.this.setMultisetValueConstructor(true);
         if (ctx.multisetValueConstructor().TABLE() != null) {
            ValueExpressionPrimary.this.setTableMultisetValueConstructor(true);
         }

         if (ctx.multisetValueConstructor().queryExpression() != null) {
            ValueExpressionPrimary.this.setQueryExpression(ValueExpressionPrimary.this.getSqlFactory().newQueryExpression());
            ValueExpressionPrimary.this.getQueryExpression().parse(ctx.multisetValueConstructor().queryExpression());
         } else {
            this.getCollectionValueComponentList(ctx.multisetValueConstructor().valueExpression());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitArrayElementRefConcat(SqlParser.ArrayElementRefConcatContext ctx) {
         ValueExpressionPrimary.this.setFirstArrayValueExpression(ValueExpressionPrimary.this.getSqlFactory().newArrayValueExpression());
         ValueExpressionPrimary.this.getFirstArrayValueExpression().parse(ctx.arrayValueExpression(0));
         ValueExpressionPrimary.this.setSecondArrayValueExpression(ValueExpressionPrimary.this.getSqlFactory().newArrayValueExpression());
         ValueExpressionPrimary.this.getSecondArrayValueExpression().parse(ctx.arrayValueExpression(1));
         ValueExpressionPrimary.this.setNumericValueExpression(ValueExpressionPrimary.this.getSqlFactory().newNumericValueExpression());
         ValueExpressionPrimary.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitArrayElementRef(SqlParser.ArrayElementRefContext ctx) {
         ValueExpressionPrimary.this.setArray(ValueExpressionPrimary.this.getSqlFactory().newValueExpressionPrimary());
         ValueExpressionPrimary.this.getArray().parse(ctx.valueExpressionPrimary());
         ValueExpressionPrimary.this.setNumericValueExpression(ValueExpressionPrimary.this.getSqlFactory().newNumericValueExpression());
         ValueExpressionPrimary.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitMultisetElementRef(SqlParser.MultisetElementRefContext ctx) {
         ValueExpressionPrimary.this.setMultisetValueExpression(ValueExpressionPrimary.this.getSqlFactory().newMultisetValueExpression());
         ValueExpressionPrimary.this.getMultisetValueExpression().parse(ctx.multisetElementReference().multisetValueExpression());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitRoutineInvoc(SqlParser.RoutineInvocContext ctx) {
         this.setRoutineName(ctx.routineInvocation().routineName(), ValueExpressionPrimary.this.getRoutineName());
         if (ctx.routineInvocation().sqlArgumentList() != null) {
            this.getSqlArgumentList(ctx.routineInvocation().sqlArgumentList(), ValueExpressionPrimary.this.getSqlArguments());
         }

         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitNextValueExp(SqlParser.NextValueExpContext ctx) {
         this.setSequenceName(ctx.nextValueExpression().sequenceName(), ValueExpressionPrimary.this.getSequenceName());
         return ValueExpressionPrimary.this;
      }

      public ValueExpressionPrimary visitValueExpressionPrimaryParen(SqlParser.ValueExpressionPrimaryParenContext ctx) {
         ValueExpressionPrimary.this.setValueExpressionPrimary(ValueExpressionPrimary.this.getSqlFactory().newValueExpressionPrimary());
         ValueExpressionPrimary.this.getValueExpressionPrimary().parse(ctx.valueExpressionPrimary());
         return ValueExpressionPrimary.this;
      }

      // $FF: synthetic method
      VepVisitor(Object x1) {
         this();
      }
   }
}

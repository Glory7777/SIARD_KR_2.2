package ch.enterag.sqlparser;

import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.FieldDefinition;
import ch.enterag.sqlparser.datatype.IntervalQualifier;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.ddl.AlterColumnAction;
import ch.enterag.sqlparser.ddl.AlterTableStatement;
import ch.enterag.sqlparser.ddl.AlterTypeStatement;
import ch.enterag.sqlparser.ddl.AttributeDefinition;
import ch.enterag.sqlparser.ddl.ColumnConstraintDefinition;
import ch.enterag.sqlparser.ddl.ColumnDefinition;
import ch.enterag.sqlparser.ddl.CreateFunctionStatement;
import ch.enterag.sqlparser.ddl.CreateMethodStatement;
import ch.enterag.sqlparser.ddl.CreateProcedureStatement;
import ch.enterag.sqlparser.ddl.CreateSchemaStatement;
import ch.enterag.sqlparser.ddl.CreateTableStatement;
import ch.enterag.sqlparser.ddl.CreateTriggerStatement;
import ch.enterag.sqlparser.ddl.CreateTypeStatement;
import ch.enterag.sqlparser.ddl.CreateViewStatement;
import ch.enterag.sqlparser.ddl.DropFunctionStatement;
import ch.enterag.sqlparser.ddl.DropMethodStatement;
import ch.enterag.sqlparser.ddl.DropProcedureStatement;
import ch.enterag.sqlparser.ddl.DropSchemaStatement;
import ch.enterag.sqlparser.ddl.DropTableStatement;
import ch.enterag.sqlparser.ddl.DropTriggerStatement;
import ch.enterag.sqlparser.ddl.DropTypeStatement;
import ch.enterag.sqlparser.ddl.DropViewStatement;
import ch.enterag.sqlparser.ddl.MethodDesignator;
import ch.enterag.sqlparser.ddl.MethodSpecification;
import ch.enterag.sqlparser.ddl.PartialMethodSpecification;
import ch.enterag.sqlparser.ddl.ReturnsClause;
import ch.enterag.sqlparser.ddl.RoutineBody;
import ch.enterag.sqlparser.ddl.RoutineCharacteristics;
import ch.enterag.sqlparser.ddl.SqlParameterDeclaration;
import ch.enterag.sqlparser.ddl.TableColumn;
import ch.enterag.sqlparser.ddl.TableConstraintDefinition;
import ch.enterag.sqlparser.ddl.TableElement;
import ch.enterag.sqlparser.ddl.ViewElement;
import ch.enterag.sqlparser.dml.AssignedRow;
import ch.enterag.sqlparser.dml.DeleteStatement;
import ch.enterag.sqlparser.dml.InsertStatement;
import ch.enterag.sqlparser.dml.SetClause;
import ch.enterag.sqlparser.dml.SetTarget;
import ch.enterag.sqlparser.dml.UpdateSource;
import ch.enterag.sqlparser.dml.UpdateStatement;
import ch.enterag.sqlparser.dml.UpdateTarget;
import ch.enterag.sqlparser.expression.AggregateFunction;
import ch.enterag.sqlparser.expression.ArrayValueExpression;
import ch.enterag.sqlparser.expression.BooleanPrimary;
import ch.enterag.sqlparser.expression.BooleanValueExpression;
import ch.enterag.sqlparser.expression.CaseExpression;
import ch.enterag.sqlparser.expression.CastSpecification;
import ch.enterag.sqlparser.expression.CommonValueExpression;
import ch.enterag.sqlparser.expression.DatetimeValueExpression;
import ch.enterag.sqlparser.expression.DatetimeValueFunction;
import ch.enterag.sqlparser.expression.GeneralValueSpecification;
import ch.enterag.sqlparser.expression.GroupingElement;
import ch.enterag.sqlparser.expression.IntervalValueExpression;
import ch.enterag.sqlparser.expression.Literal;
import ch.enterag.sqlparser.expression.MultisetValueExpression;
import ch.enterag.sqlparser.expression.NumericValueExpression;
import ch.enterag.sqlparser.expression.NumericValueFunction;
import ch.enterag.sqlparser.expression.QueryExpression;
import ch.enterag.sqlparser.expression.QueryExpressionBody;
import ch.enterag.sqlparser.expression.QuerySpecification;
import ch.enterag.sqlparser.expression.RowValueExpression;
import ch.enterag.sqlparser.expression.RowValuePredicand;
import ch.enterag.sqlparser.expression.SelectSublist;
import ch.enterag.sqlparser.expression.SimpleValueSpecification;
import ch.enterag.sqlparser.expression.SortSpecification;
import ch.enterag.sqlparser.expression.SqlArgument;
import ch.enterag.sqlparser.expression.StringValueExpression;
import ch.enterag.sqlparser.expression.StringValueFunction;
import ch.enterag.sqlparser.expression.SubtypeTreatment;
import ch.enterag.sqlparser.expression.TablePrimary;
import ch.enterag.sqlparser.expression.TableReference;
import ch.enterag.sqlparser.expression.TableRowValueExpression;
import ch.enterag.sqlparser.expression.TargetSpecification;
import ch.enterag.sqlparser.expression.UnsignedLiteral;
import ch.enterag.sqlparser.expression.ValueExpression;
import ch.enterag.sqlparser.expression.ValueExpressionPrimary;
import ch.enterag.sqlparser.expression.WhenOperand;
import ch.enterag.sqlparser.expression.WindowFrameBound;
import ch.enterag.sqlparser.expression.WindowFunction;
import ch.enterag.sqlparser.expression.WindowSpecification;
import ch.enterag.sqlparser.expression.WithElement;

public class BaseSqlFactory implements SqlFactory {
   private boolean _bAggregates = false;
   private boolean _bCount = false;

   public boolean hasAggregates() {
      return this._bAggregates;
   }

   public void setAggregates(boolean bAggregates) {
      this._bAggregates = bAggregates;
   }

   public boolean hasCount() {
      return this._bCount;
   }

   public void setCount(boolean bCount) {
      this._bCount = bCount;
   }

   public IntervalQualifier newIntervalQualifier() {
      return new IntervalQualifier(this);
   }

   public PredefinedType newPredefinedType() {
      return new PredefinedType(this);
   }

   public DataType newDataType() {
      return new DataType(this);
   }

   public FieldDefinition newFieldDefinition() {
      return new FieldDefinition(this);
   }

   public AggregateFunction newAggregateFunction() {
      return new AggregateFunction(this);
   }

   public ArrayValueExpression newArrayValueExpression() {
      return new ArrayValueExpression(this);
   }

   public BooleanPrimary newBooleanPrimary() {
      return new BooleanPrimary(this);
   }

   public BooleanValueExpression newBooleanValueExpression() {
      return new BooleanValueExpression(this);
   }

   public CaseExpression newCaseExpression() {
      return new CaseExpression(this);
   }

   public CastSpecification newCastSpecification() {
      return new CastSpecification(this);
   }

   public CommonValueExpression newCommonValueExpression() {
      return new CommonValueExpression(this);
   }

   public DatetimeValueExpression newDatetimeValueExpression() {
      return new DatetimeValueExpression(this);
   }

   public DatetimeValueFunction newDatetimeValueFunction() {
      return new DatetimeValueFunction(this);
   }

   public GeneralValueSpecification newGeneralValueSpecification() {
      return new GeneralValueSpecification(this);
   }

   public GroupingElement newGroupingElement() {
      return new GroupingElement(this);
   }

   public IntervalValueExpression newIntervalValueExpression() {
      return new IntervalValueExpression(this);
   }

   public Literal newLiteral() {
      return new Literal(this);
   }

   public MultisetValueExpression newMultisetValueExpression() {
      return new MultisetValueExpression(this);
   }

   public NumericValueExpression newNumericValueExpression() {
      return new NumericValueExpression(this);
   }

   public NumericValueFunction newNumericValueFunction() {
      return new NumericValueFunction(this);
   }

   public QueryExpression newQueryExpression() {
      return new QueryExpression(this);
   }

   public QueryExpressionBody newQueryExpressionBody() {
      return new QueryExpressionBody(this);
   }

   public QuerySpecification newQuerySpecification() {
      return new QuerySpecification(this);
   }

   public RowValueExpression newRowValueExpression() {
      return new RowValueExpression(this);
   }

   public RowValuePredicand newRowValuePredicand() {
      return new RowValuePredicand(this);
   }

   public SelectSublist newSelectSublist() {
      return new SelectSublist(this);
   }

   public SimpleValueSpecification newSimpleValueSpecification() {
      return new SimpleValueSpecification(this);
   }

   public SortSpecification newSortSpecification() {
      return new SortSpecification(this);
   }

   public SqlArgument newSqlArgument() {
      return new SqlArgument(this);
   }

   public StringValueExpression newStringValueExpression() {
      return new StringValueExpression(this);
   }

   public StringValueFunction newStringValueFunction() {
      return new StringValueFunction(this);
   }

   public SubtypeTreatment newSubtypeTreatment() {
      return new SubtypeTreatment(this);
   }

   public TablePrimary newTablePrimary() {
      return new TablePrimary(this);
   }

   public TableReference newTableReference() {
      return new TableReference(this);
   }

   public TableRowValueExpression newTableRowValueExpression() {
      return new TableRowValueExpression(this);
   }

   public TargetSpecification newTargetSpecification() {
      return new TargetSpecification(this);
   }

   public UnsignedLiteral newUnsignedLiteral() {
      return new UnsignedLiteral(this);
   }

   public ValueExpression newValueExpression() {
      return new ValueExpression(this);
   }

   public ValueExpressionPrimary newValueExpressionPrimary() {
      return new ValueExpressionPrimary(this);
   }

   public WhenOperand newWhenOperand() {
      return new WhenOperand(this);
   }

   public WindowFrameBound newWindowFrameBound() {
      return new WindowFrameBound(this);
   }

   public WindowFunction newWindowFunction() {
      return new WindowFunction(this);
   }

   public WindowSpecification newWindowSpecification() {
      return new WindowSpecification(this);
   }

   public WithElement newWithElement() {
      return new WithElement(this);
   }

   public AlterColumnAction newAlterColumnAction() {
      return new AlterColumnAction(this);
   }

   public AlterTableStatement newAlterTableStatement() {
      return new AlterTableStatement(this);
   }

   public AlterTypeStatement newAlterTypeStatement() {
      return new AlterTypeStatement(this);
   }

   public AttributeDefinition newAttributeDefinition() {
      return new AttributeDefinition(this);
   }

   public ColumnConstraintDefinition newColumnConstraintDefinition() {
      return new ColumnConstraintDefinition(this);
   }

   public ColumnDefinition newColumnDefinition() {
      return new ColumnDefinition(this);
   }

   public CreateFunctionStatement newCreateFunctionStatement() {
      return new CreateFunctionStatement(this);
   }

   public CreateMethodStatement newCreateMethodStatement() {
      return new CreateMethodStatement(this);
   }

   public CreateProcedureStatement newCreateProcedureStatement() {
      return new CreateProcedureStatement(this);
   }

   public CreateSchemaStatement newCreateSchemaStatement() {
      return new CreateSchemaStatement(this);
   }

   public CreateTableStatement newCreateTableStatement() {
      return new CreateTableStatement(this);
   }

   public CreateTriggerStatement newCreateTriggerStatement() {
      return new CreateTriggerStatement(this);
   }

   public CreateTypeStatement newCreateTypeStatement() {
      return new CreateTypeStatement(this);
   }

   public CreateViewStatement newCreateViewStatement() {
      return new CreateViewStatement(this);
   }

   public DropFunctionStatement newDropFunctionStatement() {
      return new DropFunctionStatement(this);
   }

   public DropMethodStatement newDropMethodStatement() {
      return new DropMethodStatement(this);
   }

   public DropProcedureStatement newDropProcedureStatement() {
      return new DropProcedureStatement(this);
   }

   public DropSchemaStatement newDropSchemaStatement() {
      return new DropSchemaStatement(this);
   }

   public DropTableStatement newDropTableStatement() {
      return new DropTableStatement(this);
   }

   public DropTriggerStatement newDropTriggerStatement() {
      return new DropTriggerStatement(this);
   }

   public DropTypeStatement newDropTypeStatement() {
      return new DropTypeStatement(this);
   }

   public DropViewStatement newDropViewStatement() {
      return new DropViewStatement(this);
   }

   public MethodDesignator newMethodDesignator() {
      return new MethodDesignator(this);
   }

   public MethodSpecification newMethodSpecification() {
      return new MethodSpecification(this);
   }

   public PartialMethodSpecification newPartialMethodSpecification() {
      return new PartialMethodSpecification(this);
   }

   public ReturnsClause newReturnsClause() {
      return new ReturnsClause(this);
   }

   public RoutineBody newRoutineBody() {
      return new RoutineBody(this);
   }

   public RoutineCharacteristics newRoutineCharacteristics() {
      return new RoutineCharacteristics(this);
   }

   public SqlParameterDeclaration newSqlParameterDeclaration() {
      return new SqlParameterDeclaration(this);
   }

   public TableColumn newTableColumn() {
      return new TableColumn(this);
   }

   public TableConstraintDefinition newTableConstraintDefinition() {
      return new TableConstraintDefinition(this);
   }

   public TableElement newTableElement() {
      return new TableElement(this);
   }

   public ViewElement newViewElement() {
      return new ViewElement(this);
   }

   public AssignedRow newAssignedRow() {
      return new AssignedRow(this);
   }

   public DeleteStatement newDeleteStatement() {
      return new DeleteStatement(this);
   }

   public InsertStatement newInsertStatement() {
      return new InsertStatement(this);
   }

   public SetClause newSetClause() {
      return new SetClause(this);
   }

   public SetTarget newSetTarget() {
      return new SetTarget(this);
   }

   public UpdateSource newUpdateSource() {
      return new UpdateSource(this);
   }

   public UpdateStatement newUpdateStatement() {
      return new UpdateStatement(this);
   }

   public UpdateTarget newUpdateTarget() {
      return new UpdateTarget(this);
   }

   public DdlStatement newDdlStatement() {
      return new DdlStatement(this);
   }

   public DmlStatement newDmlStatement() {
      return new DmlStatement(this);
   }

   public SqlStatement newSqlStatement() {
      return new SqlStatement(this);
   }
}

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

public interface SqlFactory {
   void setCount(boolean var1);

   boolean hasCount();

   void setAggregates(boolean var1);

   boolean hasAggregates();

   IntervalQualifier newIntervalQualifier();

   PredefinedType newPredefinedType();

   DataType newDataType();

   FieldDefinition newFieldDefinition();

   AggregateFunction newAggregateFunction();

   ArrayValueExpression newArrayValueExpression();

   BooleanPrimary newBooleanPrimary();

   BooleanValueExpression newBooleanValueExpression();

   CaseExpression newCaseExpression();

   CastSpecification newCastSpecification();

   CommonValueExpression newCommonValueExpression();

   DatetimeValueExpression newDatetimeValueExpression();

   DatetimeValueFunction newDatetimeValueFunction();

   GeneralValueSpecification newGeneralValueSpecification();

   GroupingElement newGroupingElement();

   IntervalValueExpression newIntervalValueExpression();

   Literal newLiteral();

   MultisetValueExpression newMultisetValueExpression();

   NumericValueExpression newNumericValueExpression();

   NumericValueFunction newNumericValueFunction();

   QueryExpression newQueryExpression();

   QueryExpressionBody newQueryExpressionBody();

   QuerySpecification newQuerySpecification();

   RowValueExpression newRowValueExpression();

   RowValuePredicand newRowValuePredicand();

   SelectSublist newSelectSublist();

   SimpleValueSpecification newSimpleValueSpecification();

   SortSpecification newSortSpecification();

   SqlArgument newSqlArgument();

   StringValueExpression newStringValueExpression();

   StringValueFunction newStringValueFunction();

   SubtypeTreatment newSubtypeTreatment();

   TablePrimary newTablePrimary();

   TableReference newTableReference();

   TableRowValueExpression newTableRowValueExpression();

   TargetSpecification newTargetSpecification();

   UnsignedLiteral newUnsignedLiteral();

   ValueExpression newValueExpression();

   ValueExpressionPrimary newValueExpressionPrimary();

   WhenOperand newWhenOperand();

   WindowFrameBound newWindowFrameBound();

   WindowFunction newWindowFunction();

   WindowSpecification newWindowSpecification();

   WithElement newWithElement();

   AlterColumnAction newAlterColumnAction();

   AlterTableStatement newAlterTableStatement();

   AlterTypeStatement newAlterTypeStatement();

   AttributeDefinition newAttributeDefinition();

   ColumnConstraintDefinition newColumnConstraintDefinition();

   ColumnDefinition newColumnDefinition();

   CreateFunctionStatement newCreateFunctionStatement();

   CreateMethodStatement newCreateMethodStatement();

   CreateProcedureStatement newCreateProcedureStatement();

   CreateSchemaStatement newCreateSchemaStatement();

   CreateTableStatement newCreateTableStatement();

   CreateTriggerStatement newCreateTriggerStatement();

   CreateTypeStatement newCreateTypeStatement();

   CreateViewStatement newCreateViewStatement();

   DropFunctionStatement newDropFunctionStatement();

   DropMethodStatement newDropMethodStatement();

   DropProcedureStatement newDropProcedureStatement();

   DropSchemaStatement newDropSchemaStatement();

   DropTableStatement newDropTableStatement();

   DropTriggerStatement newDropTriggerStatement();

   DropTypeStatement newDropTypeStatement();

   DropViewStatement newDropViewStatement();

   MethodDesignator newMethodDesignator();

   MethodSpecification newMethodSpecification();

   PartialMethodSpecification newPartialMethodSpecification();

   ReturnsClause newReturnsClause();

   RoutineBody newRoutineBody();

   RoutineCharacteristics newRoutineCharacteristics();

   SqlParameterDeclaration newSqlParameterDeclaration();

   TableColumn newTableColumn();

   TableConstraintDefinition newTableConstraintDefinition();

   TableElement newTableElement();

   ViewElement newViewElement();

   AssignedRow newAssignedRow();

   DeleteStatement newDeleteStatement();

   InsertStatement newInsertStatement();

   SetClause newSetClause();

   SetTarget newSetTarget();

   UpdateSource newUpdateSource();

   UpdateStatement newUpdateStatement();

   UpdateTarget newUpdateTarget();

   DdlStatement newDdlStatement();

   DmlStatement newDmlStatement();

   SqlStatement newSqlStatement();
}

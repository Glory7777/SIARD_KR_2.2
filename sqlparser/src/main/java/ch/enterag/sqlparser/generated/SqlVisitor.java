package ch.enterag.sqlparser.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

public interface SqlVisitor<T> extends ParseTreeVisitor<T> {
   T visitSqlStatement(SqlParser.SqlStatementContext var1);

   T visitDdlStatement(SqlParser.DdlStatementContext var1);

   T visitDmlStatement(SqlParser.DmlStatementContext var1);

   T visitDropSchemaStatement(SqlParser.DropSchemaStatementContext var1);

   T visitCreateSchemaStatement(SqlParser.CreateSchemaStatementContext var1);

   T visitUserName(SqlParser.UserNameContext var1);

   T visitRoleName(SqlParser.RoleNameContext var1);

   T visitAuthorizationName(SqlParser.AuthorizationNameContext var1);

   T visitFieldName(SqlParser.FieldNameContext var1);

   T visitColumnName(SqlParser.ColumnNameContext var1);

   T visitAttributeName(SqlParser.AttributeNameContext var1);

   T visitCastIdentifier(SqlParser.CastIdentifierContext var1);

   T visitParameterName(SqlParser.ParameterNameContext var1);

   T visitQueryName(SqlParser.QueryNameContext var1);

   T visitCorrelationName(SqlParser.CorrelationNameContext var1);

   T visitWindowName(SqlParser.WindowNameContext var1);

   T visitCatalogName(SqlParser.CatalogNameContext var1);

   T visitMethodName(SqlParser.MethodNameContext var1);

   T visitVariableName(SqlParser.VariableNameContext var1);

   T visitSchemaName(SqlParser.SchemaNameContext var1);

   T visitQualifiedId(SqlParser.QualifiedIdContext var1);

   T visitIdentifierChain(SqlParser.IdentifierChainContext var1);

   T visitTableName(SqlParser.TableNameContext var1);

   T visitConstraintName(SqlParser.ConstraintNameContext var1);

   T visitUdtName(SqlParser.UdtNameContext var1);

   T visitSpecificMethodName(SqlParser.SpecificMethodNameContext var1);

   T visitRoutineName(SqlParser.RoutineNameContext var1);

   T visitTriggerName(SqlParser.TriggerNameContext var1);

   T visitSequenceName(SqlParser.SequenceNameContext var1);

   T visitColumnReference(SqlParser.ColumnReferenceContext var1);

   T visitTargetArrayReference(SqlParser.TargetArrayReferenceContext var1);

   T visitArrayReference(SqlParser.ArrayReferenceContext var1);

   T visitDropBehavior(SqlParser.DropBehaviorContext var1);

   T visitCommitAction(SqlParser.CommitActionContext var1);

   T visitDefaultsOption(SqlParser.DefaultsOptionContext var1);

   T visitIdentityOption(SqlParser.IdentityOptionContext var1);

   T visitMatch(SqlParser.MatchContext var1);

   T visitReferenceGeneration(SqlParser.ReferenceGenerationContext var1);

   T visitReferenceScopeCheck(SqlParser.ReferenceScopeCheckContext var1);

   T visitReferentialAction(SqlParser.ReferentialActionContext var1);

   T visitTableScope(SqlParser.TableScopeContext var1);

   T visitInstantiability(SqlParser.InstantiabilityContext var1);

   T visitFinality(SqlParser.FinalityContext var1);

   T visitParameterMode(SqlParser.ParameterModeContext var1);

   T visitLanguageName(SqlParser.LanguageNameContext var1);

   T visitParameterStyle(SqlParser.ParameterStyleContext var1);

   T visitNullCallClause(SqlParser.NullCallClauseContext var1);

   T visitDeterministic(SqlParser.DeterministicContext var1);

   T visitDataAccess(SqlParser.DataAccessContext var1);

   T visitMethodType(SqlParser.MethodTypeContext var1);

   T visitSymmetricOption(SqlParser.SymmetricOptionContext var1);

   T visitQuantifier(SqlParser.QuantifierContext var1);

   T visitSetQuantifier(SqlParser.SetQuantifierContext var1);

   T visitSampleMethod(SqlParser.SampleMethodContext var1);

   T visitJoinType(SqlParser.JoinTypeContext var1);

   T visitOrderingSpecification(SqlParser.OrderingSpecificationContext var1);

   T visitNullOrdering(SqlParser.NullOrderingContext var1);

   T visitRankFunction(SqlParser.RankFunctionContext var1);

   T visitSetFunction(SqlParser.SetFunctionContext var1);

   T visitBinarySetFunction(SqlParser.BinarySetFunctionContext var1);

   T visitInverseDistributionFunction(SqlParser.InverseDistributionFunctionContext var1);

   T visitWindowFrameUnits(SqlParser.WindowFrameUnitsContext var1);

   T visitWindowFrameExclusion(SqlParser.WindowFrameExclusionContext var1);

   T visitMultiplicativeOperator(SqlParser.MultiplicativeOperatorContext var1);

   T visitAdditiveOperator(SqlParser.AdditiveOperatorContext var1);

   T visitBooleanOperator(SqlParser.BooleanOperatorContext var1);

   T visitTimeZoneField(SqlParser.TimeZoneFieldContext var1);

   T visitMultiplier(SqlParser.MultiplierContext var1);

   T visitWithOrWithoutTimeZone(SqlParser.WithOrWithoutTimeZoneContext var1);

   T visitMultisetOperator(SqlParser.MultisetOperatorContext var1);

   T visitQueryOperator(SqlParser.QueryOperatorContext var1);

   T visitPrimaryDatetimeField(SqlParser.PrimaryDatetimeFieldContext var1);

   T visitCompOp(SqlParser.CompOpContext var1);

   T visitSign(SqlParser.SignContext var1);

   T visitWithOrWithoutData(SqlParser.WithOrWithoutDataContext var1);

   T visitConstraintCheckTime(SqlParser.ConstraintCheckTimeContext var1);

   T visitDeferrability(SqlParser.DeferrabilityContext var1);

   T visitLevels(SqlParser.LevelsContext var1);

   T visitTriggerActionTime(SqlParser.TriggerActionTimeContext var1);

   T visitOverrideClause(SqlParser.OverrideClauseContext var1);

   T visitSpecialValue(SqlParser.SpecialValueContext var1);

   T visitDropTableStatement(SqlParser.DropTableStatementContext var1);

   T visitCreateTableStatement(SqlParser.CreateTableStatementContext var1);

   T visitAlterTableStatement(SqlParser.AlterTableStatementContext var1);

   T visitDropViewStatement(SqlParser.DropViewStatementContext var1);

   T visitCreateViewStatement(SqlParser.CreateViewStatementContext var1);

   T visitAddColumnDefinition(SqlParser.AddColumnDefinitionContext var1);

   T visitDropColumnDefinition(SqlParser.DropColumnDefinitionContext var1);

   T visitAlterColumnDefinition(SqlParser.AlterColumnDefinitionContext var1);

   T visitAddTableConstraintDefinition(SqlParser.AddTableConstraintDefinitionContext var1);

   T visitDropTableConstraintDefinition(SqlParser.DropTableConstraintDefinitionContext var1);

   T visitSetColumnDefaultClause(SqlParser.SetColumnDefaultClauseContext var1);

   T visitDropColumnDefaultClause(SqlParser.DropColumnDefaultClauseContext var1);

   T visitAddColumnScopeClause(SqlParser.AddColumnScopeClauseContext var1);

   T visitDropColumnScopeClause(SqlParser.DropColumnScopeClauseContext var1);

   T visitTableContents(SqlParser.TableContentsContext var1);

   T visitSubtableClause(SqlParser.SubtableClauseContext var1);

   T visitTableElementList(SqlParser.TableElementListContext var1);

   T visitTableElement(SqlParser.TableElementContext var1);

   T visitViewSpecification(SqlParser.ViewSpecificationContext var1);

   T visitSubviewClause(SqlParser.SubviewClauseContext var1);

   T visitViewElementList(SqlParser.ViewElementListContext var1);

   T visitViewElement(SqlParser.ViewElementContext var1);

   T visitColumnDefinition(SqlParser.ColumnDefinitionContext var1);

   T visitTableConstraintDefinition(SqlParser.TableConstraintDefinitionContext var1);

   T visitLikeClause(SqlParser.LikeClauseContext var1);

   T visitSelfrefColumnSpecification(SqlParser.SelfrefColumnSpecificationContext var1);

   T visitColumnOptions(SqlParser.ColumnOptionsContext var1);

   T visitDefaultOption(SqlParser.DefaultOptionContext var1);

   T visitGenerationClause(SqlParser.GenerationClauseContext var1);

   T visitColumnConstraintDefinition(SqlParser.ColumnConstraintDefinitionContext var1);

   T visitTableConstraint(SqlParser.TableConstraintContext var1);

   T visitConstraintCharacteristics(SqlParser.ConstraintCharacteristicsContext var1);

   T visitColumnConstraint(SqlParser.ColumnConstraintContext var1);

   T visitReferencesSpecification(SqlParser.ReferencesSpecificationContext var1);

   T visitReferentialTriggeredAction(SqlParser.ReferentialTriggeredActionContext var1);

   T visitUpdateAction(SqlParser.UpdateActionContext var1);

   T visitDeleteAction(SqlParser.DeleteActionContext var1);

   T visitMultisetType(SqlParser.MultisetTypeContext var1);

   T visitRowType(SqlParser.RowTypeContext var1);

   T visitArrayType(SqlParser.ArrayTypeContext var1);

   T visitPreType(SqlParser.PreTypeContext var1);

   T visitStructType(SqlParser.StructTypeContext var1);

   T visitRefType(SqlParser.RefTypeContext var1);

   T visitReferenceType(SqlParser.ReferenceTypeContext var1);

   T visitScopeDefinition(SqlParser.ScopeDefinitionContext var1);

   T visitFieldDefinition(SqlParser.FieldDefinitionContext var1);

   T visitPredefinedType(SqlParser.PredefinedTypeContext var1);

   T visitCharType(SqlParser.CharTypeContext var1);

   T visitVarcharType(SqlParser.VarcharTypeContext var1);

   T visitClobType(SqlParser.ClobTypeContext var1);

   T visitNcharType(SqlParser.NcharTypeContext var1);

   T visitNvarcharType(SqlParser.NvarcharTypeContext var1);

   T visitNclobType(SqlParser.NclobTypeContext var1);

   T visitXmlType(SqlParser.XmlTypeContext var1);

   T visitBinaryType(SqlParser.BinaryTypeContext var1);

   T visitVarbinaryType(SqlParser.VarbinaryTypeContext var1);

   T visitBlobType(SqlParser.BlobTypeContext var1);

   T visitNumericType(SqlParser.NumericTypeContext var1);

   T visitDecimalType(SqlParser.DecimalTypeContext var1);

   T visitSmallintType(SqlParser.SmallintTypeContext var1);

   T visitIntegerType(SqlParser.IntegerTypeContext var1);

   T visitBigintType(SqlParser.BigintTypeContext var1);

   T visitFloatType(SqlParser.FloatTypeContext var1);

   T visitRealType(SqlParser.RealTypeContext var1);

   T visitDoubleType(SqlParser.DoubleTypeContext var1);

   T visitBooleanType(SqlParser.BooleanTypeContext var1);

   T visitDateType(SqlParser.DateTypeContext var1);

   T visitTimeType(SqlParser.TimeTypeContext var1);

   T visitTimestampType(SqlParser.TimestampTypeContext var1);

   T visitIntervalType(SqlParser.IntervalTypeContext var1);

   T visitDatalinkType(SqlParser.DatalinkTypeContext var1);

   T visitIntervalQualifier(SqlParser.IntervalQualifierContext var1);

   T visitLobLength(SqlParser.LobLengthContext var1);

   T visitSecondsDecimals(SqlParser.SecondsDecimalsContext var1);

   T visitPrecision(SqlParser.PrecisionContext var1);

   T visitScale(SqlParser.ScaleContext var1);

   T visitLength(SqlParser.LengthContext var1);

   T visitQuerySpecification(SqlParser.QuerySpecificationContext var1);

   T visitSelectList(SqlParser.SelectListContext var1);

   T visitSelectSublist(SqlParser.SelectSublistContext var1);

   T visitFromClause(SqlParser.FromClauseContext var1);

   T visitWhereClause(SqlParser.WhereClauseContext var1);

   T visitGroupByClause(SqlParser.GroupByClauseContext var1);

   T visitHavingClause(SqlParser.HavingClauseContext var1);

   T visitWindowClause(SqlParser.WindowClauseContext var1);

   T visitTableReference(SqlParser.TableReferenceContext var1);

   T visitJoinSpecification(SqlParser.JoinSpecificationContext var1);

   T visitTablePrimary(SqlParser.TablePrimaryContext var1);

   T visitTableAlias(SqlParser.TableAliasContext var1);

   T visitGroupingElement(SqlParser.GroupingElementContext var1);

   T visitOrdinaryGroupingSet(SqlParser.OrdinaryGroupingSetContext var1);

   T visitQueryExpression(SqlParser.QueryExpressionContext var1);

   T visitQueryExpressionBody(SqlParser.QueryExpressionBodyContext var1);

   T visitTableRowValueExpression(SqlParser.TableRowValueExpressionContext var1);

   T visitCorrespondingSpecification(SqlParser.CorrespondingSpecificationContext var1);

   T visitWithClause(SqlParser.WithClauseContext var1);

   T visitWithElement(SqlParser.WithElementContext var1);

   T visitSearchOrCycleClause(SqlParser.SearchOrCycleClauseContext var1);

   T visitSearchClause(SqlParser.SearchClauseContext var1);

   T visitCycleClause(SqlParser.CycleClauseContext var1);

   T visitValueExpression(SqlParser.ValueExpressionContext var1);

   T visitCommonValueExpression(SqlParser.CommonValueExpressionContext var1);

   T visitNumericValueExpression(SqlParser.NumericValueExpressionContext var1);

   T visitLengthExpression(SqlParser.LengthExpressionContext var1);

   T visitPositionExpression(SqlParser.PositionExpressionContext var1);

   T visitExtractExpression(SqlParser.ExtractExpressionContext var1);

   T visitCardinalityExpression(SqlParser.CardinalityExpressionContext var1);

   T visitAbsoluteValueExpression(SqlParser.AbsoluteValueExpressionContext var1);

   T visitModulusExpression(SqlParser.ModulusExpressionContext var1);

   T visitNaturalLogarithm(SqlParser.NaturalLogarithmContext var1);

   T visitExponentialFunction(SqlParser.ExponentialFunctionContext var1);

   T visitPowerFunction(SqlParser.PowerFunctionContext var1);

   T visitSquareRoot(SqlParser.SquareRootContext var1);

   T visitFloorFunction(SqlParser.FloorFunctionContext var1);

   T visitCeilingFunction(SqlParser.CeilingFunctionContext var1);

   T visitWidthBucketFunction(SqlParser.WidthBucketFunctionContext var1);

   T visitExtractField(SqlParser.ExtractFieldContext var1);

   T visitWidthBucketOperand(SqlParser.WidthBucketOperandContext var1);

   T visitWidthBucketBound1(SqlParser.WidthBucketBound1Context var1);

   T visitWidthBucketBound2(SqlParser.WidthBucketBound2Context var1);

   T visitWidthBucketCount(SqlParser.WidthBucketCountContext var1);

   T visitStringValueExpression(SqlParser.StringValueExpressionContext var1);

   T visitStringValueFunction(SqlParser.StringValueFunctionContext var1);

   T visitStartPosition(SqlParser.StartPositionContext var1);

   T visitStringLength(SqlParser.StringLengthContext var1);

   T visitDatetimeValueExpression(SqlParser.DatetimeValueExpressionContext var1);

   T visitTimeZone(SqlParser.TimeZoneContext var1);

   T visitIntervalPrimary(SqlParser.IntervalPrimaryContext var1);

   T visitDatetimeValueFunction(SqlParser.DatetimeValueFunctionContext var1);

   T visitIntervalValueExpression(SqlParser.IntervalValueExpressionContext var1);

   T visitUdtValueExpression(SqlParser.UdtValueExpressionContext var1);

   T visitReferenceValueExpression(SqlParser.ReferenceValueExpressionContext var1);

   T visitArrayValueExpression(SqlParser.ArrayValueExpressionContext var1);

   T visitMultisetValueExpression(SqlParser.MultisetValueExpressionContext var1);

   T visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext var1);

   T visitBooleanPrimary(SqlParser.BooleanPrimaryContext var1);

   T visitComparisonCondition(SqlParser.ComparisonConditionContext var1);

   T visitBetweenCondition(SqlParser.BetweenConditionContext var1);

   T visitInCondition(SqlParser.InConditionContext var1);

   T visitLikeCondition(SqlParser.LikeConditionContext var1);

   T visitSimilarCondition(SqlParser.SimilarConditionContext var1);

   T visitNullCondition(SqlParser.NullConditionContext var1);

   T visitQuantifiedComparisonCondition(SqlParser.QuantifiedComparisonConditionContext var1);

   T visitMatchCondition(SqlParser.MatchConditionContext var1);

   T visitOverlapsCondition(SqlParser.OverlapsConditionContext var1);

   T visitDistinctCondition(SqlParser.DistinctConditionContext var1);

   T visitMemberCondition(SqlParser.MemberConditionContext var1);

   T visitSubmultisetCondition(SqlParser.SubmultisetConditionContext var1);

   T visitSetCondition(SqlParser.SetConditionContext var1);

   T visitTypeCondition(SqlParser.TypeConditionContext var1);

   T visitUdtSpecification(SqlParser.UdtSpecificationContext var1);

   T visitRowValuePredicand(SqlParser.RowValuePredicandContext var1);

   T visitRowValueExpression(SqlParser.RowValueExpressionContext var1);

   T visitUnsignedLit(SqlParser.UnsignedLitContext var1);

   T visitNewSpec(SqlParser.NewSpecContext var1);

   T visitCastSpec(SqlParser.CastSpecContext var1);

   T visitArrayElementRefConcat(SqlParser.ArrayElementRefConcatContext var1);

   T visitMethodInvoc(SqlParser.MethodInvocContext var1);

   T visitScalarSubq(SqlParser.ScalarSubqContext var1);

   T visitMultisetElementRef(SqlParser.MultisetElementRefContext var1);

   T visitAttributeOrMethodRef(SqlParser.AttributeOrMethodRefContext var1);

   T visitGeneralValueSpec(SqlParser.GeneralValueSpecContext var1);

   T visitWindowFunc(SqlParser.WindowFuncContext var1);

   T visitSubtypeTreat(SqlParser.SubtypeTreatContext var1);

   T visitAggregateFunc(SqlParser.AggregateFuncContext var1);

   T visitGeneralizedMethodInvoc(SqlParser.GeneralizedMethodInvocContext var1);

   T visitRoutineInvoc(SqlParser.RoutineInvocContext var1);

   T visitArrayValueConstruct(SqlParser.ArrayValueConstructContext var1);

   T visitNextValueExp(SqlParser.NextValueExpContext var1);

   T visitMultisetValueConstruct(SqlParser.MultisetValueConstructContext var1);

   T visitValueExpressionPrimaryParen(SqlParser.ValueExpressionPrimaryParenContext var1);

   T visitStaticMethodInvoc(SqlParser.StaticMethodInvocContext var1);

   T visitArrayElementRef(SqlParser.ArrayElementRefContext var1);

   T visitReferenceRes(SqlParser.ReferenceResContext var1);

   T visitCaseExp(SqlParser.CaseExpContext var1);

   T visitGroupingOp(SqlParser.GroupingOpContext var1);

   T visitAggregateFunction(SqlParser.AggregateFunctionContext var1);

   T visitRankFunctionArgumentList(SqlParser.RankFunctionArgumentListContext var1);

   T visitDependentVariableExpression(SqlParser.DependentVariableExpressionContext var1);

   T visitIndependentVariableExpression(SqlParser.IndependentVariableExpressionContext var1);

   T visitWithinGroupSpecification(SqlParser.WithinGroupSpecificationContext var1);

   T visitSortSpecificationList(SqlParser.SortSpecificationListContext var1);

   T visitSortSpecification(SqlParser.SortSpecificationContext var1);

   T visitFilterClause(SqlParser.FilterClauseContext var1);

   T visitGroupingOperation(SqlParser.GroupingOperationContext var1);

   T visitWindowFunction(SqlParser.WindowFunctionContext var1);

   T visitWindowFunctionType(SqlParser.WindowFunctionTypeContext var1);

   T visitWindowDefinition(SqlParser.WindowDefinitionContext var1);

   T visitWindowSpecification(SqlParser.WindowSpecificationContext var1);

   T visitWindowPartitionClause(SqlParser.WindowPartitionClauseContext var1);

   T visitWindowOrderClause(SqlParser.WindowOrderClauseContext var1);

   T visitWindowFrameClause(SqlParser.WindowFrameClauseContext var1);

   T visitWindowFrameStart(SqlParser.WindowFrameStartContext var1);

   T visitWindowFrameBetween(SqlParser.WindowFrameBetweenContext var1);

   T visitWindowFrameBound1(SqlParser.WindowFrameBound1Context var1);

   T visitWindowFrameBound2(SqlParser.WindowFrameBound2Context var1);

   T visitWindowFrameBound(SqlParser.WindowFrameBoundContext var1);

   T visitScalarSubquery(SqlParser.ScalarSubqueryContext var1);

   T visitCaseExpression(SqlParser.CaseExpressionContext var1);

   T visitSimpleWhenClause(SqlParser.SimpleWhenClauseContext var1);

   T visitSearchedWhenClause(SqlParser.SearchedWhenClauseContext var1);

   T visitResult(SqlParser.ResultContext var1);

   T visitWhenOperand(SqlParser.WhenOperandContext var1);

   T visitCastSpecification(SqlParser.CastSpecificationContext var1);

   T visitCastOperand(SqlParser.CastOperandContext var1);

   T visitSubtypeTreatment(SqlParser.SubtypeTreatmentContext var1);

   T visitGeneralizedInvocation(SqlParser.GeneralizedInvocationContext var1);

   T visitSqlArgumentList(SqlParser.SqlArgumentListContext var1);

   T visitSqlArgument(SqlParser.SqlArgumentContext var1);

   T visitGeneralizedExpression(SqlParser.GeneralizedExpressionContext var1);

   T visitTargetSpecification(SqlParser.TargetSpecificationContext var1);

   T visitSimpleTargetSpecification(SqlParser.SimpleTargetSpecificationContext var1);

   T visitStaticMethodInvocation(SqlParser.StaticMethodInvocationContext var1);

   T visitNewSpecification(SqlParser.NewSpecificationContext var1);

   T visitRoutineInvocation(SqlParser.RoutineInvocationContext var1);

   T visitReferenceResolution(SqlParser.ReferenceResolutionContext var1);

   T visitArrayValueConstructor(SqlParser.ArrayValueConstructorContext var1);

   T visitMultisetValueConstructor(SqlParser.MultisetValueConstructorContext var1);

   T visitMultisetElementReference(SqlParser.MultisetElementReferenceContext var1);

   T visitNextValueExpression(SqlParser.NextValueExpressionContext var1);

   T visitUnsignedValueSpecification(SqlParser.UnsignedValueSpecificationContext var1);

   T visitGeneralValueSpecification(SqlParser.GeneralValueSpecificationContext var1);

   T visitReference(SqlParser.ReferenceContext var1);

   T visitIndicatorVariable(SqlParser.IndicatorVariableContext var1);

   T visitSimpleValueSpecification(SqlParser.SimpleValueSpecificationContext var1);

   T visitLiteral(SqlParser.LiteralContext var1);

   T visitUnsignedLiteral(SqlParser.UnsignedLiteralContext var1);

   T visitUnsignedNumericLiteral(SqlParser.UnsignedNumericLiteralContext var1);

   T visitExactNumericLiteral(SqlParser.ExactNumericLiteralContext var1);

   T visitGeneralLiteral(SqlParser.GeneralLiteralContext var1);

   T visitIntervalLiteral(SqlParser.IntervalLiteralContext var1);

   T visitDropTypeStatement(SqlParser.DropTypeStatementContext var1);

   T visitCreateTypeStatement(SqlParser.CreateTypeStatementContext var1);

   T visitAlterTypeStatement(SqlParser.AlterTypeStatementContext var1);

   T visitAddAttributeDefinition(SqlParser.AddAttributeDefinitionContext var1);

   T visitDropAttributeDefinition(SqlParser.DropAttributeDefinitionContext var1);

   T visitAddMethodSpecification(SqlParser.AddMethodSpecificationContext var1);

   T visitDropMethodSpecification(SqlParser.DropMethodSpecificationContext var1);

   T visitMethodDesignator(SqlParser.MethodDesignatorContext var1);

   T visitSubTypeClause(SqlParser.SubTypeClauseContext var1);

   T visitAttributeDefinitions(SqlParser.AttributeDefinitionsContext var1);

   T visitAttributeDefinition(SqlParser.AttributeDefinitionContext var1);

   T visitUdtOptions(SqlParser.UdtOptionsContext var1);

   T visitUdtOption(SqlParser.UdtOptionContext var1);

   T visitReferenceTypeSpecification(SqlParser.ReferenceTypeSpecificationContext var1);

   T visitUserDefinedRepresentation(SqlParser.UserDefinedRepresentationContext var1);

   T visitDerivedRepresentation(SqlParser.DerivedRepresentationContext var1);

   T visitSystemGeneratedRepresentation(SqlParser.SystemGeneratedRepresentationContext var1);

   T visitRefCastOption(SqlParser.RefCastOptionContext var1);

   T visitCastToRef(SqlParser.CastToRefContext var1);

   T visitCastToType(SqlParser.CastToTypeContext var1);

   T visitCastOption(SqlParser.CastOptionContext var1);

   T visitCastToDistinct(SqlParser.CastToDistinctContext var1);

   T visitCastToSource(SqlParser.CastToSourceContext var1);

   T visitDropProcedureStatement(SqlParser.DropProcedureStatementContext var1);

   T visitDropFunctionStatement(SqlParser.DropFunctionStatementContext var1);

   T visitDropMethodStatement(SqlParser.DropMethodStatementContext var1);

   T visitDropTriggerStatement(SqlParser.DropTriggerStatementContext var1);

   T visitCreateProcedureStatement(SqlParser.CreateProcedureStatementContext var1);

   T visitCreateFunctionStatement(SqlParser.CreateFunctionStatementContext var1);

   T visitCreateMethodStatement(SqlParser.CreateMethodStatementContext var1);

   T visitCreateTriggerStatement(SqlParser.CreateTriggerStatementContext var1);

   T visitMethodSpecifications(SqlParser.MethodSpecificationsContext var1);

   T visitMethodSpecification(SqlParser.MethodSpecificationContext var1);

   T visitOriginalMethodSpecification(SqlParser.OriginalMethodSpecificationContext var1);

   T visitOverridingMethodSpecification(SqlParser.OverridingMethodSpecificationContext var1);

   T visitPartialMethodSpecification(SqlParser.PartialMethodSpecificationContext var1);

   T visitTriggerEvent(SqlParser.TriggerEventContext var1);

   T visitOldOrNewValue(SqlParser.OldOrNewValueContext var1);

   T visitTriggeredAction(SqlParser.TriggeredActionContext var1);

   T visitSqlParameterDeclarations(SqlParser.SqlParameterDeclarationsContext var1);

   T visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext var1);

   T visitParameterType(SqlParser.ParameterTypeContext var1);

   T visitReturnsClause(SqlParser.ReturnsClauseContext var1);

   T visitReturnsType(SqlParser.ReturnsTypeContext var1);

   T visitReturnsDataType(SqlParser.ReturnsDataTypeContext var1);

   T visitResultCast(SqlParser.ResultCastContext var1);

   T visitReturnsTableType(SqlParser.ReturnsTableTypeContext var1);

   T visitTableColumns(SqlParser.TableColumnsContext var1);

   T visitTableColumn(SqlParser.TableColumnContext var1);

   T visitRoutineCharacteristics(SqlParser.RoutineCharacteristicsContext var1);

   T visitRoutineCharacteristic(SqlParser.RoutineCharacteristicContext var1);

   T visitRoutineBody(SqlParser.RoutineBodyContext var1);

   T visitInsertStatement(SqlParser.InsertStatementContext var1);

   T visitFromConstructor(SqlParser.FromConstructorContext var1);

   T visitFromSubquery(SqlParser.FromSubqueryContext var1);

   T visitInsertColumnList(SqlParser.InsertColumnListContext var1);

   T visitFromDefault(SqlParser.FromDefaultContext var1);

   T visitDeleteStatement(SqlParser.DeleteStatementContext var1);

   T visitUpdateStatement(SqlParser.UpdateStatementContext var1);

   T visitSetClause(SqlParser.SetClauseContext var1);

   T visitSetTarget(SqlParser.SetTargetContext var1);

   T visitUpdateTarget(SqlParser.UpdateTargetContext var1);

   T visitUpdateSource(SqlParser.UpdateSourceContext var1);

   T visitAssignedRow(SqlParser.AssignedRowContext var1);
}

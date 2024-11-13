package ch.enterag.sqlparser.generated;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class SqlBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements SqlVisitor<T> {
   public T visitSqlStatement(SqlParser.SqlStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDdlStatement(SqlParser.DdlStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDmlStatement(SqlParser.DmlStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropSchemaStatement(SqlParser.DropSchemaStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateSchemaStatement(SqlParser.CreateSchemaStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUserName(SqlParser.UserNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoleName(SqlParser.RoleNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAuthorizationName(SqlParser.AuthorizationNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFieldName(SqlParser.FieldNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitColumnName(SqlParser.ColumnNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAttributeName(SqlParser.AttributeNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastIdentifier(SqlParser.CastIdentifierContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitParameterName(SqlParser.ParameterNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQueryName(SqlParser.QueryNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCorrelationName(SqlParser.CorrelationNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowName(SqlParser.WindowNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCatalogName(SqlParser.CatalogNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMethodName(SqlParser.MethodNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitVariableName(SqlParser.VariableNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSchemaName(SqlParser.SchemaNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQualifiedId(SqlParser.QualifiedIdContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIdentifierChain(SqlParser.IdentifierChainContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableName(SqlParser.TableNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitConstraintName(SqlParser.ConstraintNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUdtName(SqlParser.UdtNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSpecificMethodName(SqlParser.SpecificMethodNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoutineName(SqlParser.RoutineNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTriggerName(SqlParser.TriggerNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSequenceName(SqlParser.SequenceNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitColumnReference(SqlParser.ColumnReferenceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTargetArrayReference(SqlParser.TargetArrayReferenceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayReference(SqlParser.ArrayReferenceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCommitAction(SqlParser.CommitActionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDefaultsOption(SqlParser.DefaultsOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIdentityOption(SqlParser.IdentityOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMatch(SqlParser.MatchContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceGeneration(SqlParser.ReferenceGenerationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceScopeCheck(SqlParser.ReferenceScopeCheckContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferentialAction(SqlParser.ReferentialActionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableScope(SqlParser.TableScopeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitInstantiability(SqlParser.InstantiabilityContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFinality(SqlParser.FinalityContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitParameterMode(SqlParser.ParameterModeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLanguageName(SqlParser.LanguageNameContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitParameterStyle(SqlParser.ParameterStyleContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNullCallClause(SqlParser.NullCallClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDeterministic(SqlParser.DeterministicContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDataAccess(SqlParser.DataAccessContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMethodType(SqlParser.MethodTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSymmetricOption(SqlParser.SymmetricOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQuantifier(SqlParser.QuantifierContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSetQuantifier(SqlParser.SetQuantifierContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSampleMethod(SqlParser.SampleMethodContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitJoinType(SqlParser.JoinTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOrderingSpecification(SqlParser.OrderingSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNullOrdering(SqlParser.NullOrderingContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRankFunction(SqlParser.RankFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSetFunction(SqlParser.SetFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBinarySetFunction(SqlParser.BinarySetFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitInverseDistributionFunction(SqlParser.InverseDistributionFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameUnits(SqlParser.WindowFrameUnitsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameExclusion(SqlParser.WindowFrameExclusionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultiplicativeOperator(SqlParser.MultiplicativeOperatorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAdditiveOperator(SqlParser.AdditiveOperatorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBooleanOperator(SqlParser.BooleanOperatorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTimeZoneField(SqlParser.TimeZoneFieldContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultiplier(SqlParser.MultiplierContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWithOrWithoutTimeZone(SqlParser.WithOrWithoutTimeZoneContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetOperator(SqlParser.MultisetOperatorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQueryOperator(SqlParser.QueryOperatorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPrimaryDatetimeField(SqlParser.PrimaryDatetimeFieldContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCompOp(SqlParser.CompOpContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSign(SqlParser.SignContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWithOrWithoutData(SqlParser.WithOrWithoutDataContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitConstraintCheckTime(SqlParser.ConstraintCheckTimeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDeferrability(SqlParser.DeferrabilityContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLevels(SqlParser.LevelsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTriggerActionTime(SqlParser.TriggerActionTimeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOverrideClause(SqlParser.OverrideClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSpecialValue(SqlParser.SpecialValueContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropTableStatement(SqlParser.DropTableStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAlterTableStatement(SqlParser.AlterTableStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropViewStatement(SqlParser.DropViewStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateViewStatement(SqlParser.CreateViewStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAddColumnDefinition(SqlParser.AddColumnDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropColumnDefinition(SqlParser.DropColumnDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAlterColumnDefinition(SqlParser.AlterColumnDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAddTableConstraintDefinition(SqlParser.AddTableConstraintDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropTableConstraintDefinition(SqlParser.DropTableConstraintDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSetColumnDefaultClause(SqlParser.SetColumnDefaultClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropColumnDefaultClause(SqlParser.DropColumnDefaultClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAddColumnScopeClause(SqlParser.AddColumnScopeClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropColumnScopeClause(SqlParser.DropColumnScopeClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableContents(SqlParser.TableContentsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSubtableClause(SqlParser.SubtableClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableElementList(SqlParser.TableElementListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableElement(SqlParser.TableElementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitViewSpecification(SqlParser.ViewSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSubviewClause(SqlParser.SubviewClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitViewElementList(SqlParser.ViewElementListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitViewElement(SqlParser.ViewElementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableConstraintDefinition(SqlParser.TableConstraintDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLikeClause(SqlParser.LikeClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSelfrefColumnSpecification(SqlParser.SelfrefColumnSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitColumnOptions(SqlParser.ColumnOptionsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDefaultOption(SqlParser.DefaultOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGenerationClause(SqlParser.GenerationClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitColumnConstraintDefinition(SqlParser.ColumnConstraintDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableConstraint(SqlParser.TableConstraintContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitConstraintCharacteristics(SqlParser.ConstraintCharacteristicsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitColumnConstraint(SqlParser.ColumnConstraintContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferencesSpecification(SqlParser.ReferencesSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferentialTriggeredAction(SqlParser.ReferentialTriggeredActionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUpdateAction(SqlParser.UpdateActionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDeleteAction(SqlParser.DeleteActionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetType(SqlParser.MultisetTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRowType(SqlParser.RowTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayType(SqlParser.ArrayTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPreType(SqlParser.PreTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStructType(SqlParser.StructTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRefType(SqlParser.RefTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceType(SqlParser.ReferenceTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitScopeDefinition(SqlParser.ScopeDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFieldDefinition(SqlParser.FieldDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPredefinedType(SqlParser.PredefinedTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCharType(SqlParser.CharTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitVarcharType(SqlParser.VarcharTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitClobType(SqlParser.ClobTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNcharType(SqlParser.NcharTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNvarcharType(SqlParser.NvarcharTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNclobType(SqlParser.NclobTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitXmlType(SqlParser.XmlTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBinaryType(SqlParser.BinaryTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitVarbinaryType(SqlParser.VarbinaryTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBlobType(SqlParser.BlobTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNumericType(SqlParser.NumericTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDecimalType(SqlParser.DecimalTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSmallintType(SqlParser.SmallintTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIntegerType(SqlParser.IntegerTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBigintType(SqlParser.BigintTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFloatType(SqlParser.FloatTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRealType(SqlParser.RealTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDoubleType(SqlParser.DoubleTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBooleanType(SqlParser.BooleanTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDateType(SqlParser.DateTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTimeType(SqlParser.TimeTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTimestampType(SqlParser.TimestampTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIntervalType(SqlParser.IntervalTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDatalinkType(SqlParser.DatalinkTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIntervalQualifier(SqlParser.IntervalQualifierContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLobLength(SqlParser.LobLengthContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSecondsDecimals(SqlParser.SecondsDecimalsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPrecision(SqlParser.PrecisionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitScale(SqlParser.ScaleContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLength(SqlParser.LengthContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQuerySpecification(SqlParser.QuerySpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSelectList(SqlParser.SelectListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSelectSublist(SqlParser.SelectSublistContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFromClause(SqlParser.FromClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWhereClause(SqlParser.WhereClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGroupByClause(SqlParser.GroupByClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitHavingClause(SqlParser.HavingClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowClause(SqlParser.WindowClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableReference(SqlParser.TableReferenceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitJoinSpecification(SqlParser.JoinSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTablePrimary(SqlParser.TablePrimaryContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableAlias(SqlParser.TableAliasContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGroupingElement(SqlParser.GroupingElementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOrdinaryGroupingSet(SqlParser.OrdinaryGroupingSetContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQueryExpressionBody(SqlParser.QueryExpressionBodyContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableRowValueExpression(SqlParser.TableRowValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCorrespondingSpecification(SqlParser.CorrespondingSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWithClause(SqlParser.WithClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWithElement(SqlParser.WithElementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSearchOrCycleClause(SqlParser.SearchOrCycleClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSearchClause(SqlParser.SearchClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCycleClause(SqlParser.CycleClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitValueExpression(SqlParser.ValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCommonValueExpression(SqlParser.CommonValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNumericValueExpression(SqlParser.NumericValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLengthExpression(SqlParser.LengthExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPositionExpression(SqlParser.PositionExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitExtractExpression(SqlParser.ExtractExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCardinalityExpression(SqlParser.CardinalityExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAbsoluteValueExpression(SqlParser.AbsoluteValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitModulusExpression(SqlParser.ModulusExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNaturalLogarithm(SqlParser.NaturalLogarithmContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitExponentialFunction(SqlParser.ExponentialFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPowerFunction(SqlParser.PowerFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSquareRoot(SqlParser.SquareRootContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFloorFunction(SqlParser.FloorFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCeilingFunction(SqlParser.CeilingFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWidthBucketFunction(SqlParser.WidthBucketFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitExtractField(SqlParser.ExtractFieldContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWidthBucketOperand(SqlParser.WidthBucketOperandContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWidthBucketBound1(SqlParser.WidthBucketBound1Context ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWidthBucketBound2(SqlParser.WidthBucketBound2Context ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWidthBucketCount(SqlParser.WidthBucketCountContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStringValueExpression(SqlParser.StringValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStringValueFunction(SqlParser.StringValueFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStartPosition(SqlParser.StartPositionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStringLength(SqlParser.StringLengthContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDatetimeValueExpression(SqlParser.DatetimeValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTimeZone(SqlParser.TimeZoneContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIntervalPrimary(SqlParser.IntervalPrimaryContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDatetimeValueFunction(SqlParser.DatetimeValueFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIntervalValueExpression(SqlParser.IntervalValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUdtValueExpression(SqlParser.UdtValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceValueExpression(SqlParser.ReferenceValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayValueExpression(SqlParser.ArrayValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetValueExpression(SqlParser.MultisetValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBooleanPrimary(SqlParser.BooleanPrimaryContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitComparisonCondition(SqlParser.ComparisonConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitBetweenCondition(SqlParser.BetweenConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitInCondition(SqlParser.InConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLikeCondition(SqlParser.LikeConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSimilarCondition(SqlParser.SimilarConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNullCondition(SqlParser.NullConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitQuantifiedComparisonCondition(SqlParser.QuantifiedComparisonConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMatchCondition(SqlParser.MatchConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOverlapsCondition(SqlParser.OverlapsConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDistinctCondition(SqlParser.DistinctConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMemberCondition(SqlParser.MemberConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSubmultisetCondition(SqlParser.SubmultisetConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSetCondition(SqlParser.SetConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTypeCondition(SqlParser.TypeConditionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUdtSpecification(SqlParser.UdtSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRowValuePredicand(SqlParser.RowValuePredicandContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRowValueExpression(SqlParser.RowValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUnsignedLit(SqlParser.UnsignedLitContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNewSpec(SqlParser.NewSpecContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastSpec(SqlParser.CastSpecContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayElementRefConcat(SqlParser.ArrayElementRefConcatContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMethodInvoc(SqlParser.MethodInvocContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitScalarSubq(SqlParser.ScalarSubqContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetElementRef(SqlParser.MultisetElementRefContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAttributeOrMethodRef(SqlParser.AttributeOrMethodRefContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGeneralValueSpec(SqlParser.GeneralValueSpecContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFunc(SqlParser.WindowFuncContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSubtypeTreat(SqlParser.SubtypeTreatContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAggregateFunc(SqlParser.AggregateFuncContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGeneralizedMethodInvoc(SqlParser.GeneralizedMethodInvocContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoutineInvoc(SqlParser.RoutineInvocContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayValueConstruct(SqlParser.ArrayValueConstructContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNextValueExp(SqlParser.NextValueExpContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetValueConstruct(SqlParser.MultisetValueConstructContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitValueExpressionPrimaryParen(SqlParser.ValueExpressionPrimaryParenContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStaticMethodInvoc(SqlParser.StaticMethodInvocContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayElementRef(SqlParser.ArrayElementRefContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceRes(SqlParser.ReferenceResContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCaseExp(SqlParser.CaseExpContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGroupingOp(SqlParser.GroupingOpContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAggregateFunction(SqlParser.AggregateFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRankFunctionArgumentList(SqlParser.RankFunctionArgumentListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDependentVariableExpression(SqlParser.DependentVariableExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIndependentVariableExpression(SqlParser.IndependentVariableExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWithinGroupSpecification(SqlParser.WithinGroupSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSortSpecificationList(SqlParser.SortSpecificationListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSortSpecification(SqlParser.SortSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFilterClause(SqlParser.FilterClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGroupingOperation(SqlParser.GroupingOperationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFunction(SqlParser.WindowFunctionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFunctionType(SqlParser.WindowFunctionTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowDefinition(SqlParser.WindowDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowSpecification(SqlParser.WindowSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowPartitionClause(SqlParser.WindowPartitionClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowOrderClause(SqlParser.WindowOrderClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameClause(SqlParser.WindowFrameClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameStart(SqlParser.WindowFrameStartContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameBetween(SqlParser.WindowFrameBetweenContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameBound1(SqlParser.WindowFrameBound1Context ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameBound2(SqlParser.WindowFrameBound2Context ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWindowFrameBound(SqlParser.WindowFrameBoundContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitScalarSubquery(SqlParser.ScalarSubqueryContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCaseExpression(SqlParser.CaseExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSimpleWhenClause(SqlParser.SimpleWhenClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSearchedWhenClause(SqlParser.SearchedWhenClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitResult(SqlParser.ResultContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitWhenOperand(SqlParser.WhenOperandContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastSpecification(SqlParser.CastSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastOperand(SqlParser.CastOperandContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSubtypeTreatment(SqlParser.SubtypeTreatmentContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGeneralizedInvocation(SqlParser.GeneralizedInvocationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSqlArgumentList(SqlParser.SqlArgumentListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSqlArgument(SqlParser.SqlArgumentContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGeneralizedExpression(SqlParser.GeneralizedExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTargetSpecification(SqlParser.TargetSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSimpleTargetSpecification(SqlParser.SimpleTargetSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitStaticMethodInvocation(SqlParser.StaticMethodInvocationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNewSpecification(SqlParser.NewSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoutineInvocation(SqlParser.RoutineInvocationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceResolution(SqlParser.ReferenceResolutionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitArrayValueConstructor(SqlParser.ArrayValueConstructorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetValueConstructor(SqlParser.MultisetValueConstructorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMultisetElementReference(SqlParser.MultisetElementReferenceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitNextValueExpression(SqlParser.NextValueExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUnsignedValueSpecification(SqlParser.UnsignedValueSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGeneralValueSpecification(SqlParser.GeneralValueSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReference(SqlParser.ReferenceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIndicatorVariable(SqlParser.IndicatorVariableContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSimpleValueSpecification(SqlParser.SimpleValueSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitLiteral(SqlParser.LiteralContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUnsignedLiteral(SqlParser.UnsignedLiteralContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUnsignedNumericLiteral(SqlParser.UnsignedNumericLiteralContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitExactNumericLiteral(SqlParser.ExactNumericLiteralContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitGeneralLiteral(SqlParser.GeneralLiteralContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitIntervalLiteral(SqlParser.IntervalLiteralContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropTypeStatement(SqlParser.DropTypeStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateTypeStatement(SqlParser.CreateTypeStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAlterTypeStatement(SqlParser.AlterTypeStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAddAttributeDefinition(SqlParser.AddAttributeDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropAttributeDefinition(SqlParser.DropAttributeDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAddMethodSpecification(SqlParser.AddMethodSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropMethodSpecification(SqlParser.DropMethodSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMethodDesignator(SqlParser.MethodDesignatorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSubTypeClause(SqlParser.SubTypeClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAttributeDefinitions(SqlParser.AttributeDefinitionsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAttributeDefinition(SqlParser.AttributeDefinitionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUdtOptions(SqlParser.UdtOptionsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUdtOption(SqlParser.UdtOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReferenceTypeSpecification(SqlParser.ReferenceTypeSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUserDefinedRepresentation(SqlParser.UserDefinedRepresentationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDerivedRepresentation(SqlParser.DerivedRepresentationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSystemGeneratedRepresentation(SqlParser.SystemGeneratedRepresentationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRefCastOption(SqlParser.RefCastOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastToRef(SqlParser.CastToRefContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastToType(SqlParser.CastToTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastOption(SqlParser.CastOptionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastToDistinct(SqlParser.CastToDistinctContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCastToSource(SqlParser.CastToSourceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropProcedureStatement(SqlParser.DropProcedureStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropFunctionStatement(SqlParser.DropFunctionStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropMethodStatement(SqlParser.DropMethodStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDropTriggerStatement(SqlParser.DropTriggerStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateProcedureStatement(SqlParser.CreateProcedureStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateFunctionStatement(SqlParser.CreateFunctionStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateMethodStatement(SqlParser.CreateMethodStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitCreateTriggerStatement(SqlParser.CreateTriggerStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMethodSpecifications(SqlParser.MethodSpecificationsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitMethodSpecification(SqlParser.MethodSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOriginalMethodSpecification(SqlParser.OriginalMethodSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOverridingMethodSpecification(SqlParser.OverridingMethodSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitPartialMethodSpecification(SqlParser.PartialMethodSpecificationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTriggerEvent(SqlParser.TriggerEventContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitOldOrNewValue(SqlParser.OldOrNewValueContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTriggeredAction(SqlParser.TriggeredActionContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSqlParameterDeclarations(SqlParser.SqlParameterDeclarationsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitParameterType(SqlParser.ParameterTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReturnsClause(SqlParser.ReturnsClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReturnsType(SqlParser.ReturnsTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReturnsDataType(SqlParser.ReturnsDataTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitResultCast(SqlParser.ResultCastContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitReturnsTableType(SqlParser.ReturnsTableTypeContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableColumns(SqlParser.TableColumnsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitTableColumn(SqlParser.TableColumnContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoutineCharacteristics(SqlParser.RoutineCharacteristicsContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoutineCharacteristic(SqlParser.RoutineCharacteristicContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitRoutineBody(SqlParser.RoutineBodyContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitInsertStatement(SqlParser.InsertStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFromConstructor(SqlParser.FromConstructorContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFromSubquery(SqlParser.FromSubqueryContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitInsertColumnList(SqlParser.InsertColumnListContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitFromDefault(SqlParser.FromDefaultContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitDeleteStatement(SqlParser.DeleteStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUpdateStatement(SqlParser.UpdateStatementContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSetClause(SqlParser.SetClauseContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitSetTarget(SqlParser.SetTargetContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUpdateTarget(SqlParser.UpdateTargetContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitUpdateSource(SqlParser.UpdateSourceContext ctx) {
      return this.visitChildren(ctx);
   }

   public T visitAssignedRow(SqlParser.AssignedRowContext ctx) {
      return this.visitChildren(ctx);
   }
}

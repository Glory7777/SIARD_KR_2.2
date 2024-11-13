package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.ddl.enums.Finality;
import ch.enterag.sqlparser.ddl.enums.Instantiability;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdList;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CreateTypeStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateTypeStatement.class.getName());
   private CreateTypeStatement.CtsVisitor _visitor = new CreateTypeStatement.CtsVisitor();
   private QualifiedId _qTypeName = new QualifiedId();
   private QualifiedId _qSuperType = new QualifiedId();
   private PredefinedType _pdtDistinctBaseType = null;
   private List<AttributeDefinition> _listAttributeDefinitions = new ArrayList();
   private Instantiability _instantiability = null;
   private Finality _finality = null;
   private PredefinedType _pdtRefUsing = null;
   private IdList _ilRefFromAttributes = new IdList();
   private boolean _bRefSystemGenerated = false;
   private Identifier _idCastToRef = new Identifier();
   private Identifier _idCastToType = new Identifier();
   private Identifier _idCastToDistinct = new Identifier();
   private Identifier _idCastToSource = new Identifier();
   private List<MethodSpecification> _listMethodSpecifications = new ArrayList();

   private CreateTypeStatement.CtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTypeName() {
      return this._qTypeName;
   }

   private void setTypeName(QualifiedId qTypeName) {
      this._qTypeName = qTypeName;
   }

   public QualifiedId getSuperType() {
      return this._qSuperType;
   }

   private void setSuperType(QualifiedId qSuperType) {
      this._qSuperType = qSuperType;
   }

   public PredefinedType getDistinctBaseType() {
      return this._pdtDistinctBaseType;
   }

   public void setDistinctBaseType(PredefinedType pdtDistinctBaseType) {
      this._pdtDistinctBaseType = pdtDistinctBaseType;
   }

   public List<AttributeDefinition> getAttributeDefinitions() {
      return this._listAttributeDefinitions;
   }

   private void setAttributeDefinitions(List<AttributeDefinition> listAttributeDefinitions) {
      this._listAttributeDefinitions = listAttributeDefinitions;
   }

   public Instantiability getInstantiability() {
      return this._instantiability;
   }

   public void setInstantiability(Instantiability instantiability) {
      this._instantiability = instantiability;
   }

   public Finality getFinality() {
      return this._finality;
   }

   public void setFinality(Finality finality) {
      this._finality = finality;
   }

   public PredefinedType getRefUsing() {
      return this._pdtRefUsing;
   }

   public void setRefUsing(PredefinedType pdtRefUsing) {
      this._pdtRefUsing = pdtRefUsing;
   }

   public IdList getRefFromAttributes() {
      return this._ilRefFromAttributes;
   }

   private void setRefFromAttributes(IdList ilRefFromAttributes) {
      this._ilRefFromAttributes = ilRefFromAttributes;
   }

   public boolean getRefSystemGenerated() {
      return this._bRefSystemGenerated;
   }

   public void setRefSystemGenerated(boolean bRefSystemGenerated) {
      this._bRefSystemGenerated = bRefSystemGenerated;
   }

   public Identifier getCastToRef() {
      return this._idCastToRef;
   }

   private void setCastToRef(Identifier idCastToRef) {
      this._idCastToRef = idCastToRef;
   }

   public Identifier getCastToType() {
      return this._idCastToType;
   }

   private void setCastToType(Identifier idCastToType) {
      this._idCastToType = idCastToType;
   }

   public Identifier getCastToDistinct() {
      return this._idCastToDistinct;
   }

   private void setCastToDistinct(Identifier idCastToDistinct) {
      this._idCastToDistinct = idCastToDistinct;
   }

   public Identifier getCastToSource() {
      return this._idCastToSource;
   }

   private void setCastToSource(Identifier idCastToSource) {
      this._idCastToSource = idCastToSource;
   }

   public List<MethodSpecification> getMethodSpecifications() {
      return this._listMethodSpecifications;
   }

   private void setMethodSpecifications(List<MethodSpecification> listMethodSpecifications) {
      this._listMethodSpecifications = listMethodSpecifications;
   }

   protected String formatAttributeDefinitions() {
      String sDefinitions = "(";

      for(int iDefinition = 0; iDefinition < this.getAttributeDefinitions().size(); ++iDefinition) {
         if (iDefinition > 0) {
            sDefinitions = sDefinitions + ",";
         }

         sDefinitions = sDefinitions + "\r\n" + "  " + ((AttributeDefinition)this.getAttributeDefinitions().get(iDefinition)).format();
      }

      sDefinitions = sDefinitions + "\r\n" + ")";
      return sDefinitions;
   }

   protected String formatMethodSpecifications() {
      String sSpecification = "";

      for(int iSpecification = 0; iSpecification < this.getMethodSpecifications().size(); ++iSpecification) {
         if (iSpecification > 0) {
            sSpecification = sSpecification + ",";
         }

         sSpecification = sSpecification + "\r\n" + ((MethodSpecification)this.getMethodSpecifications().get(iSpecification)).format();
      }

      return sSpecification;
   }

   public String format() {
      String sStatement = K.CREATE.getKeyword() + " " + K.TYPE.getKeyword() + " " + this.getTypeName().format();
      if (this.getSuperType().isSet()) {
         sStatement = sStatement + " " + K.UNDER.getKeyword() + " " + this.getSuperType().format();
      }

      if (this.getDistinctBaseType() != null) {
         sStatement = sStatement + " " + K.AS.getKeyword() + " " + this.getDistinctBaseType().format();
      }

      if (this.getAttributeDefinitions().size() > 0) {
         sStatement = sStatement + " " + K.AS.getKeyword() + this.formatAttributeDefinitions();
      }

      if (this.getInstantiability() != null) {
         sStatement = sStatement + " " + this.getInstantiability().getKeywords();
      }

      if (this.getFinality() != null) {
         sStatement = sStatement + " " + this.getFinality().getKeywords();
      }

      if (this.getRefUsing() != null) {
         sStatement = sStatement + " " + K.REF.getKeyword() + " " + K.USING.getKeyword() + " " + this.getRefUsing().format();
      } else if (this.getRefFromAttributes().isSet()) {
         sStatement = sStatement + " " + K.REF.getKeyword() + " " + K.FROM.getKeyword() + "(" + this.getRefFromAttributes().format() + ")";
      } else if (this.getRefSystemGenerated()) {
         sStatement = sStatement + " " + K.REF.getKeyword() + " " + K.IS.getKeyword() + " " + K.SYSTEM.getKeyword() + " " + K.GENERATED.getKeyword();
      }

      if (this.getCastToRef().isSet()) {
         sStatement = sStatement + " " + K.CAST.getKeyword() + "(" + K.SOURCE.getKeyword() + " " + K.AS.getKeyword() + " " + K.REF.getKeyword() + ")" + " " + K.WITH.getKeyword() + " " + this.getCastToRef().format();
      }

      if (this.getCastToType().isSet()) {
         sStatement = sStatement + " " + K.CAST.getKeyword() + "(" + K.REF.getKeyword() + " " + K.AS.getKeyword() + " " + K.SOURCE.getKeyword() + ")" + " " + K.WITH.getKeyword() + " " + this.getCastToRef().format();
      }

      if (this.getCastToDistinct().isSet()) {
         sStatement = sStatement + " " + K.CAST.getKeyword() + "(" + K.SOURCE.getKeyword() + " " + K.AS.getKeyword() + " " + K.DISTINCT.getKeyword() + ")" + " " + K.WITH.getKeyword() + " " + this.getCastToRef().format();
      }

      if (this.getCastToSource().isSet()) {
         sStatement = sStatement + " " + K.CAST.getKeyword() + "(" + K.DISTINCT.getKeyword() + " " + K.AS.getKeyword() + " " + K.SOURCE.getKeyword() + ")" + " " + K.WITH.getKeyword() + " " + this.getCastToRef().format();
      }

      if (this.getMethodSpecifications().size() > 0) {
         sStatement = sStatement + this.formatMethodSpecifications();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateTypeStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.CreateTypeStatementContext ctx = null;

      try {
         ctx = this.getParser().createTypeStatement();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().createTypeStatement();
      }

      this.parse(ctx);
   }

   public void initialize(QualifiedId qTypeName, QualifiedId qSuperType, PredefinedType pdtDistinctBaseType, List<AttributeDefinition> listAttributeDefinitions, Instantiability instantiability, Finality finality, PredefinedType pdtRefUsing, IdList ilRefFromAttributes, boolean bRefSystemGenerated, Identifier idCastToRef, Identifier idCastToType, Identifier idCastToDistinct, Identifier idCastToSource, List<MethodSpecification> listMethodSpecifications) {
      _il.enter(new Object[]{qTypeName, qSuperType, pdtDistinctBaseType, listAttributeDefinitions, instantiability, finality, pdtRefUsing, ilRefFromAttributes, String.valueOf(bRefSystemGenerated), idCastToRef, idCastToType, idCastToDistinct, idCastToSource, listMethodSpecifications});
      this.setTypeName(qTypeName);
      this.setSuperType(qSuperType);
      this.setDistinctBaseType(pdtDistinctBaseType);
      this.setAttributeDefinitions(listAttributeDefinitions);
      this.setInstantiability(instantiability);
      this.setFinality(finality);
      this.setRefUsing(pdtRefUsing);
      this.setRefFromAttributes(ilRefFromAttributes);
      this.setCastToRef(idCastToRef);
      this.setCastToType(idCastToType);
      this.setCastToDistinct(idCastToDistinct);
      this.setCastToSource(idCastToSource);
      this.setMethodSpecifications(listMethodSpecifications);
      _il.exit();
   }

   public void initDistinct(QualifiedId qTypeName, PredefinedType pdtDistinctBaseType) {
      _il.enter(new Object[]{qTypeName, pdtDistinctBaseType});
      this.setTypeName(qTypeName);
      this.setDistinctBaseType(pdtDistinctBaseType);
      _il.exit();
   }

   public void initAttributes(QualifiedId qTypeName, List<AttributeDefinition> listAttributeDefinitions) {
      _il.enter(new Object[]{qTypeName, listAttributeDefinitions});
      this.setTypeName(qTypeName);
      this.setAttributeDefinitions(listAttributeDefinitions);
      _il.exit();
   }

   public CreateTypeStatement(SqlFactory sf) {
      super(sf);
   }

   private class CtsVisitor extends EnhancedSqlBaseVisitor<CreateTypeStatement> {
      private CtsVisitor() {
      }

      public CreateTypeStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, CreateTypeStatement.this.getTypeName());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitSubTypeClause(SqlParser.SubTypeClauseContext ctx) {
         this.setUdtName(ctx.udtName(), CreateTypeStatement.this.getSuperType());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitPredefinedType(SqlParser.PredefinedTypeContext ctx) {
         CreateTypeStatement.this.setDistinctBaseType(CreateTypeStatement.this.getSqlFactory().newPredefinedType());
         CreateTypeStatement.this.getDistinctBaseType().parse(ctx);
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitAttributeDefinition(SqlParser.AttributeDefinitionContext ctx) {
         AttributeDefinition ad = CreateTypeStatement.this.getSqlFactory().newAttributeDefinition();
         ad.parse(ctx);
         CreateTypeStatement.this.getAttributeDefinitions().add(ad);
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitInstantiability(SqlParser.InstantiabilityContext ctx) {
         CreateTypeStatement.this.setInstantiability(this.getInstantiability(ctx));
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitFinality(SqlParser.FinalityContext ctx) {
         CreateTypeStatement.this.setFinality(this.getFinality(ctx));
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitUserDefinedRepresentation(SqlParser.UserDefinedRepresentationContext ctx) {
         CreateTypeStatement.this.setRefUsing(CreateTypeStatement.this.getSqlFactory().newPredefinedType());
         CreateTypeStatement.this.getRefUsing().parse(ctx.predefinedType());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitAttributeName(SqlParser.AttributeNameContext ctx) {
         this.addAttributeName(ctx, CreateTypeStatement.this.getRefFromAttributes());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitSystemGeneratedRepresentation(SqlParser.SystemGeneratedRepresentationContext ctx) {
         CreateTypeStatement.this.setRefSystemGenerated(true);
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitCastToRef(SqlParser.CastToRefContext ctx) {
         this.setIdentifier(ctx.castIdentifier().IDENTIFIER(), CreateTypeStatement.this.getCastToRef());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitCastToType(SqlParser.CastToTypeContext ctx) {
         this.setIdentifier(ctx.castIdentifier().IDENTIFIER(), CreateTypeStatement.this.getCastToType());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitCastToDistinct(SqlParser.CastToDistinctContext ctx) {
         this.setIdentifier(ctx.castIdentifier().IDENTIFIER(), CreateTypeStatement.this.getCastToDistinct());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitCastToSource(SqlParser.CastToSourceContext ctx) {
         this.setIdentifier(ctx.castIdentifier().IDENTIFIER(), CreateTypeStatement.this.getCastToSource());
         return CreateTypeStatement.this;
      }

      public CreateTypeStatement visitMethodSpecification(SqlParser.MethodSpecificationContext ctx) {
         MethodSpecification ms = CreateTypeStatement.this.getSqlFactory().newMethodSpecification();
         ms.parse(ctx);
         CreateTypeStatement.this.getMethodSpecifications().add(ms);
         return CreateTypeStatement.this;
      }

      // $FF: synthetic method
      CtsVisitor(Object x1) {
         this();
      }
   }
}

package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class AlterTypeStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(AlterTypeStatement.class.getName());
   private AlterTypeStatement.AtsVisitor _visitor = new AlterTypeStatement.AtsVisitor();
   private QualifiedId _qTypeName = new QualifiedId();
   private AttributeDefinition _ad = null;
   private Identifier _idAttributeName = null;
   private MethodSpecification _ms = null;
   private MethodDesignator _md = null;

   private AlterTypeStatement.AtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTypeName() {
      return this._qTypeName;
   }

   private void setTypeName(QualifiedId qTypeName) {
      this._qTypeName = qTypeName;
   }

   public AttributeDefinition getAttributeDefinition() {
      return this._ad;
   }

   public void setAttributeDefinition(AttributeDefinition ad) {
      this._ad = ad;
   }

   public Identifier getAttributeName() {
      return this._idAttributeName;
   }

   private void setAttributeName(Identifier idAttributeName) {
      this._idAttributeName = idAttributeName;
   }

   public MethodSpecification getMethodSpecification() {
      return this._ms;
   }

   public void setMethodSpecification(MethodSpecification ms) {
      this._ms = ms;
   }

   public MethodDesignator getMethodDesignator() {
      return this._md;
   }

   public void setMethodDesignator(MethodDesignator md) {
      this._md = md;
   }

   public String format() {
      String sStatement = K.ALTER.getKeyword() + " " + K.TYPE.getKeyword() + " " + this.getTypeName().format();
      if (this.getAttributeDefinition() != null) {
         sStatement = sStatement + " " + K.ADD.getKeyword() + " " + K.ATTRIBUTE.getKeyword() + " " + this.getAttributeDefinition().format();
      } else if (this.getAttributeName() != null) {
         sStatement = sStatement + " " + K.DROP.getKeyword() + " " + K.ATTRIBUTE.getKeyword() + " " + this.getAttributeName().format() + " " + K.RESTRICT.getKeyword();
      } else if (this.getMethodSpecification() != null) {
         sStatement = sStatement + " " + K.ADD.getKeyword() + this.getMethodSpecification().format();
      } else if (this.getMethodDesignator() != null) {
         sStatement = sStatement + " " + K.DROP.getKeyword() + this.getMethodDesignator().format() + " " + K.RESTRICT;
      }

      return sStatement;
   }

   public void parse(SqlParser.AlterTypeStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().alterTypeStatement());
   }

   public void initialize(QualifiedId qTypeName, AttributeDefinition ad, Identifier idAttributeName, MethodSpecification ms, MethodDesignator md) {
      _il.enter(new Object[]{qTypeName, ad, idAttributeName, ms, md});
      this.setTypeName(qTypeName);
      this.setAttributeDefinition(ad);
      this.setAttributeName(idAttributeName);
      this.setMethodSpecification(ms);
      this.setMethodDesignator(md);
      _il.exit();
   }

   public AlterTypeStatement(SqlFactory sf) {
      super(sf);
   }

   private class AtsVisitor extends EnhancedSqlBaseVisitor<AlterTypeStatement> {
      private AtsVisitor() {
      }

      public AlterTypeStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, AlterTypeStatement.this.getTypeName());
         return AlterTypeStatement.this;
      }

      public AlterTypeStatement visitAddAttributeDefinition(SqlParser.AddAttributeDefinitionContext ctx) {
         AlterTypeStatement.this.setAttributeDefinition(AlterTypeStatement.this.getSqlFactory().newAttributeDefinition());
         AlterTypeStatement.this.getAttributeDefinition().parse(ctx.attributeDefinition());
         return AlterTypeStatement.this;
      }

      public AlterTypeStatement visitDropAttributeDefinition(SqlParser.DropAttributeDefinitionContext ctx) {
         this.setAttributeName(ctx.attributeName(), AlterTypeStatement.this.getAttributeName());
         return AlterTypeStatement.this;
      }

      public AlterTypeStatement visitAddMethodSpecification(SqlParser.AddMethodSpecificationContext ctx) {
         AlterTypeStatement.this.setMethodSpecification(AlterTypeStatement.this.getSqlFactory().newMethodSpecification());
         AlterTypeStatement.this.getMethodSpecification().parse(ctx.methodSpecification());
         return AlterTypeStatement.this;
      }

      public AlterTypeStatement visitDropMethodSpecification(SqlParser.DropMethodSpecificationContext ctx) {
         AlterTypeStatement.this.setMethodDesignator(AlterTypeStatement.this.getSqlFactory().newMethodDesignator());
         AlterTypeStatement.this.getMethodDesignator().parse(ctx.methodDesignator());
         return AlterTypeStatement.this;
      }

      // $FF: synthetic method
      AtsVisitor(Object x1) {
         this();
      }
   }
}

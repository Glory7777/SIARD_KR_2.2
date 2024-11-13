package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class DropTriggerStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropTriggerStatement.class.getName());
   private DropTriggerStatement.DtsVisitor _visitor = new DropTriggerStatement.DtsVisitor();
   private QualifiedId _qiTriggerName = new QualifiedId();

   private DropTriggerStatement.DtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTriggerName() {
      return this._qiTriggerName;
   }

   private void setTriggerName(QualifiedId qiTriggerName) {
      this._qiTriggerName = qiTriggerName;
   }

   public String format() {
      String sStatement = K.DROP.getKeyword() + " " + K.TRIGGER.getKeyword() + " " + this.getTriggerName().format();
      return sStatement;
   }

   public void parse(SqlParser.DropTriggerStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropTriggerStatement());
   }

   public void initialize(QualifiedId qiTriggerName) {
      _il.enter(new Object[0]);
      this.setTriggerName(qiTriggerName);
      _il.exit();
   }

   public DropTriggerStatement(SqlFactory sf) {
      super(sf);
   }

   private class DtsVisitor extends EnhancedSqlBaseVisitor<DropTriggerStatement> {
      private DtsVisitor() {
      }

      public DropTriggerStatement visitTriggerName(SqlParser.TriggerNameContext ctx) {
         this.setTriggerName(ctx, DropTriggerStatement.this.getTriggerName());
         return (DropTriggerStatement)this.visitChildren(ctx);
      }

      // $FF: synthetic method
      DtsVisitor(Object x1) {
         this();
      }
   }
}

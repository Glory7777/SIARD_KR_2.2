package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.ForEach;
import ch.enterag.sqlparser.ddl.enums.OldOrNew;
import ch.enterag.sqlparser.ddl.enums.TriggerActionTime;
import ch.enterag.sqlparser.ddl.enums.TriggerEvent;
import ch.enterag.sqlparser.expression.BooleanValueExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdList;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CreateTriggerStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateTriggerStatement.class.getName());
   private CreateTriggerStatement.CtsVisitor _visitor = new CreateTriggerStatement.CtsVisitor();
   private QualifiedId _qiTriggerName = new QualifiedId();
   private TriggerActionTime _tat = null;
   private TriggerEvent _te = null;
   private IdList _ilColumnNames = new IdList();
   private QualifiedId _qiTableName = new QualifiedId();
   private List<OldOrNew> _listOldOrNewRefs = new ArrayList();
   private List<Identifier> _listCorreleationNames = new ArrayList();
   private ForEach _fe = null;
   private BooleanValueExpression _bve = null;
   private boolean _bAtomicBody = false;
   private RoutineBody _rb = null;

   private CreateTriggerStatement.CtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTriggerName() {
      return this._qiTriggerName;
   }

   private void setTriggerName(QualifiedId qiTriggerName) {
      this._qiTriggerName = qiTriggerName;
   }

   public TriggerActionTime getTriggerActionTime() {
      return this._tat;
   }

   public void setTriggerActionTime(TriggerActionTime tat) {
      this._tat = tat;
   }

   public TriggerEvent getTriggerEvent() {
      return this._te;
   }

   public void setTriggerEvent(TriggerEvent te) {
      this._te = te;
   }

   public IdList getColumnNames() {
      return this._ilColumnNames;
   }

   private void setColumnNames(IdList ilColumnNames) {
      this._ilColumnNames = ilColumnNames;
   }

   public QualifiedId getTableName() {
      return this._qiTableName;
   }

   public void setTableName(QualifiedId qiTableName) {
      this._qiTableName = qiTableName;
   }

   public List<OldOrNew> getOldOrNewRefs() {
      return this._listOldOrNewRefs;
   }

   private void setOldOrNewRefs(List<OldOrNew> listOldOrNewRefs) {
      this._listOldOrNewRefs = listOldOrNewRefs;
   }

   public List<Identifier> getCorrelationNames() {
      return this._listCorreleationNames;
   }

   private void setCorrelationNames(List<Identifier> listCorreleationNames) {
      this._listCorreleationNames = listCorreleationNames;
   }

   public ForEach getForEach() {
      return this._fe;
   }

   public void setForEach(ForEach fe) {
      this._fe = fe;
   }

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public boolean hasAtomicBody() {
      return this._bAtomicBody;
   }

   public void setAtomicBody(boolean bAtomicBody) {
      this._bAtomicBody = bAtomicBody;
   }

   public RoutineBody getRoutineBody() {
      return this._rb;
   }

   public void setRoutineBody(RoutineBody rb) {
      this._rb = rb;
   }

   public String format() {
      String sStatement = K.CREATE.getKeyword() + " " + K.TRIGGER.getKeyword() + " " + this.getTriggerName().format() + " " + this.getTriggerActionTime().getKeywords() + " " + this.getTriggerEvent().getKeywords();
      if (this.getColumnNames().isSet()) {
         sStatement = sStatement + " " + K.OF.getKeyword() + " " + this.getColumnNames().format();
      }

      sStatement = sStatement + " " + K.ON.getKeyword() + " " + this.getTableName().format();
      if (this.getOldOrNewRefs().size() > 0) {
         sStatement = sStatement + " " + K.REFERENCING.getKeyword();

         for(int i = 0; i < this.getOldOrNewRefs().size(); ++i) {
            sStatement = sStatement + " " + ((OldOrNew)this.getOldOrNewRefs().get(i)).getKeywords() + " " + ((Identifier)this.getCorrelationNames().get(i)).format();
         }
      }

      if (this.getForEach() != null) {
         sStatement = sStatement + " " + this.getForEach().getKeywords();
      }

      if (this.getBooleanValueExpression() != null) {
         sStatement = sStatement + " " + K.WHEN.getKeyword() + "(" + this.getBooleanValueExpression().format() + ")";
      }

      if (this.hasAtomicBody()) {
         sStatement = sStatement + " " + K.BEGIN.getKeyword() + " " + K.ATOMIC.getKeyword() + " " + this.getRoutineBody() + K.END.getKeyword();
      } else {
         sStatement = sStatement + " " + this.getRoutineBody().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateTriggerStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createTriggerStatement());
   }

   public void initialize(QualifiedId qiTriggerName, TriggerActionTime tat, TriggerEvent te, IdList ilColumnNames, QualifiedId qiTableName, List<OldOrNew> listOldOrNewRefs, List<Identifier> listCorrelationNames, ForEach fe, BooleanValueExpression bve, boolean bAtomicBody, RoutineBody rb) {
      _il.enter(new Object[0]);
      this.setTriggerName(qiTriggerName);
      this.setTriggerActionTime(tat);
      this.setTriggerEvent(te);
      this.setColumnNames(ilColumnNames);
      this.setTableName(qiTableName);
      this.setOldOrNewRefs(listOldOrNewRefs);
      this.setCorrelationNames(listCorrelationNames);
      this.setForEach(fe);
      this.setBooleanValueExpression(bve);
      this.setAtomicBody(bAtomicBody);
      this.setRoutineBody(rb);
      _il.exit();
   }

   public CreateTriggerStatement(SqlFactory sf) {
      super(sf);
   }

   private class CtsVisitor extends EnhancedSqlBaseVisitor<CreateTriggerStatement> {
      private CtsVisitor() {
      }

      public CreateTriggerStatement visitTriggerName(SqlParser.TriggerNameContext ctx) {
         this.setTriggerName(ctx, CreateTriggerStatement.this.getTriggerName());
         return CreateTriggerStatement.this;
      }

      public CreateTriggerStatement visitTriggerActionTime(SqlParser.TriggerActionTimeContext ctx) {
         CreateTriggerStatement.this.setTriggerActionTime(this.getTriggerActionTime(ctx));
         return CreateTriggerStatement.this;
      }

      public CreateTriggerStatement visitTriggerEvent(SqlParser.TriggerEventContext ctx) {
         CreateTriggerStatement.this.setTriggerEvent(this.getTriggerEvent(ctx));
         return (CreateTriggerStatement)this.visitChildren(ctx);
      }

      public CreateTriggerStatement visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.addColumnName(ctx, CreateTriggerStatement.this.getColumnNames());
         return (CreateTriggerStatement)this.visitChildren(ctx);
      }

      public CreateTriggerStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, CreateTriggerStatement.this.getTableName());
         return (CreateTriggerStatement)this.visitChildren(ctx);
      }

      public CreateTriggerStatement visitOldOrNewValue(SqlParser.OldOrNewValueContext ctx) {
         CreateTriggerStatement.this.getOldOrNewRefs().add(this.getOldOrNew(ctx));
         Identifier idCorrelationName = new Identifier();
         this.setIdentifier(ctx.correlationName().IDENTIFIER(), idCorrelationName);
         CreateTriggerStatement.this.getCorrelationNames().add(idCorrelationName);
         return CreateTriggerStatement.this;
      }

      public CreateTriggerStatement visitTriggeredAction(SqlParser.TriggeredActionContext ctx) {
         if (ctx.FOR() != null && ctx.EACH() != null) {
            if (ctx.ROW() != null) {
               CreateTriggerStatement.this.setForEach(ForEach.FOR_EACH_ROW);
            } else if (ctx.STATEMENT() != null) {
               CreateTriggerStatement.this.setForEach(ForEach.FOR_EACH_STATEMENT);
            }
         }

         if (ctx.booleanValueExpression() != null) {
            CreateTriggerStatement.this.setBooleanValueExpression(CreateTriggerStatement.this.getSqlFactory().newBooleanValueExpression());
            CreateTriggerStatement.this.getBooleanValueExpression().parse(ctx.booleanValueExpression());
         }

         if (ctx.ATOMIC() != null) {
            CreateTriggerStatement.this.setAtomicBody(true);
         }

         CreateTriggerStatement.this.setRoutineBody(CreateTriggerStatement.this.getSqlFactory().newRoutineBody());
         CreateTriggerStatement.this.getRoutineBody().parse(ctx.routineBody());
         return CreateTriggerStatement.this;
      }

      // $FF: synthetic method
      CtsVisitor(Object x1) {
         this();
      }
   }
}

package ch.enterag.sqlparser;

import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.AlterTableStatement;
import ch.enterag.sqlparser.ddl.AlterTypeStatement;
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
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class DdlStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DdlStatement.class.getName());
   private DdlStatement.DsVisitor _visitor = new DdlStatement.DsVisitor();
   private CreateSchemaStatement _css = null;
   private DropSchemaStatement _dss = null;
   private CreateTableStatement _cts = null;
   private AlterTableStatement _ats = null;
   private DropTableStatement _dts = null;
   private CreateViewStatement _cvs = null;
   private DropViewStatement _dvs = null;
   private CreateTypeStatement _ctys = null;
   private AlterTypeStatement _atys = null;
   private DropTypeStatement _dtys = null;
   private CreateMethodStatement _cms = null;
   private DropMethodStatement _dms = null;
   private CreateFunctionStatement _cfs = null;
   private DropFunctionStatement _dfs = null;
   private CreateProcedureStatement _cps = null;
   private DropProcedureStatement _dps = null;
   private CreateTriggerStatement _ctrs = null;
   private DropTriggerStatement _dtrs = null;

   private DdlStatement.DsVisitor getVisitor() {
      return this._visitor;
   }

   public CreateSchemaStatement getCreateSchemaStatement() {
      return this._css;
   }

   public void setCreateSchemaStatement(CreateSchemaStatement css) {
      this._css = css;
   }

   public DropSchemaStatement getDropSchemaStatement() {
      return this._dss;
   }

   public void setDropSchemaStatement(DropSchemaStatement dss) {
      this._dss = dss;
   }

   public CreateTableStatement getCreateTableStatement() {
      return this._cts;
   }

   public void setCreateTableStatement(CreateTableStatement cts) {
      this._cts = cts;
   }

   public AlterTableStatement getAlterTableStatement() {
      return this._ats;
   }

   public void setAlterTableStatement(AlterTableStatement ats) {
      this._ats = ats;
   }

   public DropTableStatement getDropTableStatement() {
      return this._dts;
   }

   public void setDropTableStatement(DropTableStatement dts) {
      this._dts = dts;
   }

   public CreateViewStatement getCreateViewStatement() {
      return this._cvs;
   }

   public void setCreateViewStatement(CreateViewStatement cvs) {
      this._cvs = cvs;
   }

   public DropViewStatement getDropViewStatement() {
      return this._dvs;
   }

   public void setDropViewStatement(DropViewStatement dvs) {
      this._dvs = dvs;
   }

   public CreateTypeStatement getCreateTypeStatement() {
      return this._ctys;
   }

   public void setCreateTypeStatement(CreateTypeStatement ctys) {
      this._ctys = ctys;
   }

   public AlterTypeStatement getAlterTypeStatement() {
      return this._atys;
   }

   public void setAlterTypeStatement(AlterTypeStatement atys) {
      this._atys = atys;
   }

   public DropTypeStatement getDropTypeStatement() {
      return this._dtys;
   }

   public void setDropTypeStatement(DropTypeStatement dtys) {
      this._dtys = dtys;
   }

   public CreateMethodStatement getCreateMethodStatement() {
      return this._cms;
   }

   public void setCreateMethodStatement(CreateMethodStatement cms) {
      this._cms = cms;
   }

   public DropMethodStatement getDropMethodStatement() {
      return this._dms;
   }

   public void setDropMethodStatement(DropMethodStatement dms) {
      this._dms = dms;
   }

   public CreateFunctionStatement getCreateFunctionStatement() {
      return this._cfs;
   }

   public void setCreateFunctionStatement(CreateFunctionStatement cfs) {
      this._cfs = cfs;
   }

   public DropFunctionStatement getDropFunctionStatement() {
      return this._dfs;
   }

   public void setDropFunctionStatement(DropFunctionStatement dfs) {
      this._dfs = dfs;
   }

   public CreateProcedureStatement getCreateProcedureStatement() {
      return this._cps;
   }

   public void setCreateProcedureStatement(CreateProcedureStatement cps) {
      this._cps = cps;
   }

   public DropProcedureStatement getDropProcedureStatement() {
      return this._dps;
   }

   public void setDropProcedureStatement(DropProcedureStatement dps) {
      this._dps = dps;
   }

   public CreateTriggerStatement getCreateTriggerStatement() {
      return this._ctrs;
   }

   public void setCreateTriggerStatement(CreateTriggerStatement ctrs) {
      this._ctrs = ctrs;
   }

   public DropTriggerStatement getDropTriggerStatement() {
      return this._dtrs;
   }

   public void setDropTriggerStatement(DropTriggerStatement dtrs) {
      this._dtrs = dtrs;
   }

   public String format() {
      String sStatement = null;
      if (this.getCreateSchemaStatement() != null) {
         sStatement = this.getCreateSchemaStatement().format();
      } else if (this.getDropSchemaStatement() != null) {
         sStatement = this.getDropSchemaStatement().format();
      } else if (this.getCreateTableStatement() != null) {
         sStatement = this.getCreateTableStatement().format();
      } else if (this.getAlterTableStatement() != null) {
         sStatement = this.getAlterTableStatement().format();
      } else if (this.getDropTableStatement() != null) {
         sStatement = this.getDropTableStatement().format();
      } else if (this.getCreateViewStatement() != null) {
         sStatement = this.getCreateViewStatement().format();
      } else if (this.getDropViewStatement() != null) {
         sStatement = this.getDropViewStatement().format();
      } else if (this.getCreateTypeStatement() != null) {
         sStatement = this.getCreateTypeStatement().format();
      } else if (this.getAlterTypeStatement() != null) {
         sStatement = this.getCreateTypeStatement().format();
      } else if (this.getDropTypeStatement() != null) {
         sStatement = this.getDropTypeStatement().format();
      } else if (this.getCreateMethodStatement() != null) {
         sStatement = this.getCreateMethodStatement().format();
      } else if (this.getDropMethodStatement() != null) {
         sStatement = this.getDropMethodStatement().format();
      } else if (this.getCreateFunctionStatement() != null) {
         sStatement = this.getCreateFunctionStatement().format();
      } else if (this.getDropFunctionStatement() != null) {
         sStatement = this.getDropFunctionStatement().format();
      } else if (this.getCreateProcedureStatement() != null) {
         sStatement = this.getCreateProcedureStatement().format();
      } else if (this.getDropProcedureStatement() != null) {
         sStatement = this.getDropProcedureStatement().format();
      } else if (this.getCreateTriggerStatement() != null) {
         sStatement = this.getCreateTriggerStatement().format();
      } else if (this.getDropTriggerStatement() != null) {
         sStatement = this.getDropTriggerStatement().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.DdlStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().ddlStatement());
   }

   public void initialize(CreateSchemaStatement css, DropSchemaStatement dss, CreateTableStatement cts, AlterTableStatement ats, DropTableStatement dts, CreateViewStatement cvs, DropViewStatement dvs, CreateTypeStatement ctys, AlterTypeStatement atys, DropTypeStatement dtys, CreateMethodStatement cms, DropMethodStatement dms, CreateFunctionStatement cfs, DropFunctionStatement dfs, CreateProcedureStatement cps, DropProcedureStatement dps, CreateTriggerStatement ctrs, DropTriggerStatement dtrs) {
      _il.enter(new Object[0]);
      this.setCreateSchemaStatement(css);
      this.setDropSchemaStatement(dss);
      this.setCreateTableStatement(cts);
      this.setAlterTableStatement(ats);
      this.setDropTableStatement(dts);
      this.setCreateViewStatement(cvs);
      this.setDropViewStatement(dvs);
      this.setCreateTypeStatement(ctys);
      this.setAlterTypeStatement(atys);
      this.setDropTypeStatement(dtys);
      this.setCreateMethodStatement(cms);
      this.setDropMethodStatement(dms);
      this.setCreateFunctionStatement(cfs);
      this.setDropFunctionStatement(dfs);
      this.setCreateProcedureStatement(cps);
      this.setDropProcedureStatement(dps);
      this.setCreateTriggerStatement(ctrs);
      this.setDropTriggerStatement(dtrs);
      _il.exit();
   }

   public DdlStatement(SqlFactory sf) {
      super(sf);
   }

   private class DsVisitor extends EnhancedSqlBaseVisitor<DdlStatement> {
      private DsVisitor() {
      }

      public DdlStatement visitCreateSchemaStatement(SqlParser.CreateSchemaStatementContext ctx) {
         DdlStatement.this.setCreateSchemaStatement(DdlStatement.this.getSqlFactory().newCreateSchemaStatement());
         DdlStatement.this.getCreateSchemaStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropSchemaStatement(SqlParser.DropSchemaStatementContext ctx) {
         DdlStatement.this.setDropSchemaStatement(DdlStatement.this.getSqlFactory().newDropSchemaStatement());
         DdlStatement.this.getDropSchemaStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx) {
         DdlStatement.this.setCreateTableStatement(DdlStatement.this.getSqlFactory().newCreateTableStatement());
         DdlStatement.this.getCreateTableStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitAlterTableStatement(SqlParser.AlterTableStatementContext ctx) {
         DdlStatement.this.setAlterTableStatement(DdlStatement.this.getSqlFactory().newAlterTableStatement());
         DdlStatement.this.getAlterTableStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropTableStatement(SqlParser.DropTableStatementContext ctx) {
         DdlStatement.this.setDropTableStatement(DdlStatement.this.getSqlFactory().newDropTableStatement());
         DdlStatement.this.getDropTableStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateViewStatement(SqlParser.CreateViewStatementContext ctx) {
         DdlStatement.this.setCreateViewStatement(DdlStatement.this.getSqlFactory().newCreateViewStatement());
         DdlStatement.this.getCreateViewStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropViewStatement(SqlParser.DropViewStatementContext ctx) {
         DdlStatement.this.setDropViewStatement(DdlStatement.this.getSqlFactory().newDropViewStatement());
         DdlStatement.this.getDropViewStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateTypeStatement(SqlParser.CreateTypeStatementContext ctx) {
         DdlStatement.this.setCreateTypeStatement(DdlStatement.this.getSqlFactory().newCreateTypeStatement());
         DdlStatement.this.getCreateTypeStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitAlterTypeStatement(SqlParser.AlterTypeStatementContext ctx) {
         DdlStatement.this.setAlterTypeStatement(DdlStatement.this.getSqlFactory().newAlterTypeStatement());
         DdlStatement.this.getAlterTypeStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropTypeStatement(SqlParser.DropTypeStatementContext ctx) {
         DdlStatement.this.setDropTypeStatement(DdlStatement.this.getSqlFactory().newDropTypeStatement());
         DdlStatement.this.getDropTypeStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateMethodStatement(SqlParser.CreateMethodStatementContext ctx) {
         DdlStatement.this.setCreateMethodStatement(DdlStatement.this.getSqlFactory().newCreateMethodStatement());
         DdlStatement.this.getCreateMethodStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropMethodStatement(SqlParser.DropMethodStatementContext ctx) {
         DdlStatement.this.setDropMethodStatement(DdlStatement.this.getSqlFactory().newDropMethodStatement());
         DdlStatement.this.getDropMethodStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateFunctionStatement(SqlParser.CreateFunctionStatementContext ctx) {
         DdlStatement.this.setCreateFunctionStatement(DdlStatement.this.getSqlFactory().newCreateFunctionStatement());
         DdlStatement.this.getCreateFunctionStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropFunctionStatement(SqlParser.DropFunctionStatementContext ctx) {
         DdlStatement.this.setDropFunctionStatement(DdlStatement.this.getSqlFactory().newDropFunctionStatement());
         DdlStatement.this.getDropFunctionStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateProcedureStatement(SqlParser.CreateProcedureStatementContext ctx) {
         DdlStatement.this.setCreateProcedureStatement(DdlStatement.this.getSqlFactory().newCreateProcedureStatement());
         DdlStatement.this.getCreateProcedureStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropProcedureStatement(SqlParser.DropProcedureStatementContext ctx) {
         DdlStatement.this.setDropProcedureStatement(DdlStatement.this.getSqlFactory().newDropProcedureStatement());
         DdlStatement.this.getDropProcedureStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitCreateTriggerStatement(SqlParser.CreateTriggerStatementContext ctx) {
         DdlStatement.this.setCreateTriggerStatement(DdlStatement.this.getSqlFactory().newCreateTriggerStatement());
         DdlStatement.this.getCreateTriggerStatement().parse(ctx);
         return DdlStatement.this;
      }

      public DdlStatement visitDropTriggerStatement(SqlParser.DropTriggerStatementContext ctx) {
         DdlStatement.this.setDropTriggerStatement(DdlStatement.this.getSqlFactory().newDropTriggerStatement());
         DdlStatement.this.getDropTriggerStatement().parse(ctx);
         return DdlStatement.this;
      }

      // $FF: synthetic method
      DsVisitor(Object x1) {
         this();
      }
   }
}

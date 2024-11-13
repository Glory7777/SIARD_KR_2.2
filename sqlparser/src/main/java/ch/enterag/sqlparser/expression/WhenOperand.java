package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.expression.enums.BooleanCondition;
import ch.enterag.sqlparser.expression.enums.CompOp;
import ch.enterag.sqlparser.expression.enums.MatchType;
import ch.enterag.sqlparser.expression.enums.NullCondition;
import ch.enterag.sqlparser.expression.enums.Quantifier;
import ch.enterag.sqlparser.expression.enums.SetCondition;
import ch.enterag.sqlparser.expression.enums.SymmetricOption;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WhenOperand extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(WhenOperand.class.getName());
   private WhenOperand.WoVisitor _visitor = new WhenOperand.WoVisitor();
   private RowValuePredicand _rve = null;
   private CompOp _co = null;
   private Boolean _bNot = false;
   private BooleanCondition _bc = null;
   private RowValuePredicand _rve2 = null;
   private RowValuePredicand _rve3 = null;
   private SymmetricOption _so = null;
   private List<RowValueExpression> _listRve = new ArrayList();
   private QueryExpression _qe = null;
   private StringValueExpression _sve = null;
   private String _sEscapeLetter = null;
   private NullCondition _nc = null;
   private Quantifier _q = null;
   private boolean _bUnique = false;
   private MatchType _mt = null;
   private MultisetValueExpression _mve = null;
   private SetCondition _sc = null;
   private List<QualifiedId> _listUdtNames = new ArrayList();
   private List<Boolean> _listExclusives = new ArrayList();

   private WhenOperand.WoVisitor getVisitor() {
      return this._visitor;
   }

   public RowValuePredicand getRowValuePredicand() {
      return this._rve;
   }

   public void setRowValuePredicand(RowValuePredicand rve) {
      this._rve = rve;
   }

   public CompOp getCompOp() {
      return this._co;
   }

   public void setCompOp(CompOp co) {
      this._co = co;
   }

   public boolean isNot() {
      return this._bNot;
   }

   public void setNot(boolean bNot) {
      this._bNot = bNot;
   }

   public BooleanCondition getBooleanCondition() {
      return this._bc;
   }

   public void setBooleanCondition(BooleanCondition bc) {
      this._bc = bc;
   }

   public RowValuePredicand getSecondRowValuePredicand() {
      return this._rve2;
   }

   public void setSecondRowValuePredicand(RowValuePredicand rve2) {
      this._rve2 = rve2;
   }

   public RowValuePredicand getThirdRowValuePredicand() {
      return this._rve3;
   }

   public void setThirdRowValuePredicand(RowValuePredicand rve3) {
      this._rve3 = rve3;
   }

   public SymmetricOption getSymmetricOption() {
      return this._so;
   }

   public void setSymmetricOption(SymmetricOption so) {
      this._so = so;
   }

   public List<RowValueExpression> getRowValueExpressions() {
      return this._listRve;
   }

   private void setRowValueExpressions(List<RowValueExpression> listRve) {
      this._listRve = listRve;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public StringValueExpression getStringValueExpression() {
      return this._sve;
   }

   public void setStringValueExpression(StringValueExpression sve) {
      this._sve = sve;
   }

   public String getEscapeLetter() {
      return this._sEscapeLetter;
   }

   public void setEscapeLetter(String sEscapeLetter) {
      this._sEscapeLetter = sEscapeLetter;
   }

   public NullCondition getNullCondition() {
      return this._nc;
   }

   public void setNullCondition(NullCondition nc) {
      this._nc = nc;
   }

   public Quantifier getQuantifier() {
      return this._q;
   }

   public void setQuantifier(Quantifier q) {
      this._q = q;
   }

   public boolean isUnique() {
      return this._bUnique;
   }

   public void setUnique(boolean bUnique) {
      this._bUnique = bUnique;
   }

   public MatchType getMatchType() {
      return this._mt;
   }

   public void setMatchType(MatchType mt) {
      this._mt = mt;
   }

   public MultisetValueExpression getMultisetValueExpression() {
      return this._mve;
   }

   public void setMultisetValueExpression(MultisetValueExpression mve) {
      this._mve = mve;
   }

   public SetCondition getSetCondition() {
      return this._sc;
   }

   public void setSetCondition(SetCondition sc) {
      this._sc = sc;
   }

   public List<QualifiedId> getUdtNames() {
      return this._listUdtNames;
   }

   private void setUdtNames(List<QualifiedId> listUdtNames) {
      this._listUdtNames = listUdtNames;
   }

   public List<Boolean> getExclusives() {
      return this._listExclusives;
   }

   private void setExclusives(List<Boolean> listExclusives) {
      this._listExclusives = listExclusives;
   }

   public String format() {
      String s = "";
      if (this.getBooleanCondition() != null) {
         int i;
         switch(this.getBooleanCondition()) {
         case BETWEEN:
            if (this.isNot()) {
               s = K.NOT.getKeyword() + " ";
            }

            s = s + this.getBooleanCondition().getKeywords();
            if (this.getSymmetricOption() != null) {
               s = s + " " + this.getSymmetricOption().getKeywords();
            }

            s = s + " " + this.getSecondRowValuePredicand().format() + " " + K.AND.getKeyword() + " " + this.getThirdRowValuePredicand().format();
            break;
         case IN:
            if (this.isNot()) {
               s = K.NOT.getKeyword() + " ";
            }

            s = s + this.getBooleanCondition().getKeywords();
            if (this.getQueryExpression() != null) {
               s = s + "(" + this.getQueryExpression().format() + ")";
               break;
            } else {
               for(i = 0; i < this.getRowValueExpressions().size(); ++i) {
                  s = s + " " + ((RowValueExpression)this.getRowValueExpressions().get(i)).format();
               }

               return s;
            }
         case LIKE:
            if (this.isNot()) {
               s = K.NOT.getKeyword() + " ";
            }

            s = s + this.getBooleanCondition().getKeywords() + " " + this.getStringValueExpression().format();
            if (this.getEscapeLetter() != null) {
               s = s + " " + K.ESCAPE.getKeyword() + " " + SqlLiterals.formatStringLiteral(this.getEscapeLetter());
            }
            break;
         case SIMILAR:
            if (this.isNot()) {
               s = K.NOT.getKeyword() + " ";
            }

            s = s + this.getBooleanCondition().getKeywords() + " " + K.TO.getKeyword() + " " + this.getStringValueExpression().format();
            if (this.getEscapeLetter() != null) {
               s = s + " " + K.ESCAPE.getKeyword() + " " + SqlLiterals.formatStringLiteral(this.getEscapeLetter());
            }
            break;
         case MATCH:
            s = this.getBooleanCondition().getKeywords();
            if (this.isUnique()) {
               s = s + " " + K.UNIQUE.getKeyword();
            }

            s = s + " " + this.getMatchType().getKeywords() + "(" + this.getQueryExpression().format() + ")";
            break;
         case OVERLAPS:
         case IS_DISTINCT_FROM:
            s = this.getBooleanCondition().getKeywords() + " " + this.getSecondRowValuePredicand().format();
            break;
         case MEMBER_OF:
         case SUBMULTISET:
            if (this.isNot()) {
               s = K.NOT.getKeyword() + " ";
            }

            s = s + this.getBooleanCondition().getKeywords() + " " + this.getMultisetValueExpression().format();
            break;
         case OF:
            s = K.IS.getKeyword() + " ";
            if (this.isNot()) {
               s = s + K.NOT.getKeyword() + " ";
            }

            s = s + this.getBooleanCondition().getKeywords() + "(";

            for(i = 0; i < this.getUdtNames().size(); ++i) {
               if (i > 0) {
                  s = s + ",";
               }

               if ((Boolean)this.getExclusives().get(i)) {
                  s = s + " " + K.ONLY.getKeyword();
               }

               s = s + " " + ((QualifiedId)this.getUdtNames().get(i)).format();
            }

            s = s + ")";
            break;
         case EXISTS:
            s = K.EXISTS.getKeyword() + "(" + this.getQueryExpression().format() + ")";
            break;
         default:
            throw new IllegalArgumentException("Boolean condition " + this.getBooleanCondition().getKeywords() + " not (yet) supported in WHEN operand!");
         }
      } else if (this.getQuantifier() != null && this.getCompOp() != null) {
         s = this.getCompOp().getKeywords() + " " + this.getQuantifier().getKeywords() + "(" + this.getQueryExpression().format() + ")";
      } else if (this.getCompOp() != null) {
         s = this.getCompOp().getKeywords() + " " + this.getSecondRowValuePredicand().format();
      } else if (this.getNullCondition() != null) {
         s = this.getNullCondition().getKeywords();
      } else if (this.getSetCondition() != null) {
         s = this.getSetCondition().getKeywords();
      } else if (this.getRowValuePredicand() != null) {
         s = this.getRowValuePredicand().format();
      }

      return s;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = this.getSqlFactory().newDataType();
      PredefinedType ptType = this.getSqlFactory().newPredefinedType();
      ptType.initBooleanType();
      dt.initPredefinedDataType(ptType);
      return dt;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      throw new IllegalArgumentException("Evaluation of WhenOperand is not yet implemented!");
   }

   public void parse(SqlParser.WhenOperandContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().whenOperand());
   }

   public void initialize(CompOp co, Boolean bNot, BooleanCondition bc, RowValuePredicand rve, RowValuePredicand rve2, RowValuePredicand rve3, SymmetricOption so, List<RowValueExpression> listRve, QueryExpression qe, StringValueExpression sve, String sEscapeLetter, NullCondition nc, Quantifier q, boolean bUnique, MatchType mt, MultisetValueExpression mve, SetCondition sc, List<QualifiedId> listUdtNames, List<Boolean> listExclusives) {
      _il.enter(new Object[]{co, String.valueOf(bNot), bc, rve, rve2, rve3, so, listRve, qe, sve, sEscapeLetter, nc, q, bUnique, mt, mve, sc, listUdtNames, listExclusives});
      this.setCompOp(co);
      this.setNot(bNot);
      this.setBooleanCondition(bc);
      this.setRowValuePredicand(rve);
      this.setSecondRowValuePredicand(rve2);
      this.setThirdRowValuePredicand(rve3);
      this.setSymmetricOption(so);
      this.setRowValueExpressions(listRve);
      this.setQueryExpression(qe);
      this.setStringValueExpression(sve);
      this.setEscapeLetter(sEscapeLetter);
      this.setNullCondition(nc);
      this.setQuantifier(q);
      this.setUnique(bUnique);
      this.setMatchType(mt);
      this.setMultisetValueExpression(mve);
      this.setSetCondition(sc);
      this.setUdtNames(listUdtNames);
      this.setExclusives(listExclusives);
      _il.exit();
   }

   public WhenOperand(SqlFactory sf) {
      super(sf);
   }

   private class WoVisitor extends EnhancedSqlBaseVisitor<WhenOperand> {
      private WoVisitor() {
      }

      public WhenOperand visitWhenOperand(SqlParser.WhenOperandContext ctx) {
         if (ctx.rowValuePredicand() != null) {
            WhenOperand.this.setRowValuePredicand(WhenOperand.this.getSqlFactory().newRowValuePredicand());
            WhenOperand.this.getRowValuePredicand().parse(ctx.rowValuePredicand());
         }

         return (WhenOperand)this.visitChildren(ctx);
      }

      public WhenOperand visitComparisonCondition(SqlParser.ComparisonConditionContext ctx) {
         WhenOperand.this.setCompOp(this.getCompOp(ctx.compOp()));
         WhenOperand.this.setSecondRowValuePredicand(WhenOperand.this.getSqlFactory().newRowValuePredicand());
         WhenOperand.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand());
         return WhenOperand.this;
      }

      public WhenOperand visitBetweenCondition(SqlParser.BetweenConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.BETWEEN);
         if (ctx.symmetricOption() != null) {
            WhenOperand.this.setSymmetricOption(this.getSymmetricOption(ctx.symmetricOption()));
         }

         WhenOperand.this.setSecondRowValuePredicand(WhenOperand.this.getSqlFactory().newRowValuePredicand());
         WhenOperand.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand(0));
         WhenOperand.this.setThirdRowValuePredicand(WhenOperand.this.getSqlFactory().newRowValuePredicand());
         WhenOperand.this.getThirdRowValuePredicand().parse(ctx.rowValuePredicand(1));
         return WhenOperand.this;
      }

      public WhenOperand visitInCondition(SqlParser.InConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.IN);
         if (ctx.queryExpression() != null) {
            WhenOperand.this.setQueryExpression(WhenOperand.this.getSqlFactory().newQueryExpression());
            WhenOperand.this.getQueryExpression().parse(ctx.queryExpression());
         } else {
            for(int i = 0; i < ctx.rowValueExpression().size(); ++i) {
               RowValueExpression rve = WhenOperand.this.getSqlFactory().newRowValueExpression();
               rve.parse(ctx.rowValueExpression(i));
               WhenOperand.this.getRowValueExpressions().add(rve);
            }
         }

         return WhenOperand.this;
      }

      public WhenOperand visitLikeCondition(SqlParser.LikeConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.LIKE);
         WhenOperand.this.setStringValueExpression(WhenOperand.this.getSqlFactory().newStringValueExpression());
         WhenOperand.this.getStringValueExpression().parse(ctx.stringValueExpression());
         if (ctx.ESCAPE() != null) {
            try {
               WhenOperand.this.setEscapeLetter(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
            } catch (ParseException var3) {
               throw new IllegalArgumentException("Error visiting escape letter", var3);
            }
         }

         return WhenOperand.this;
      }

      public WhenOperand visitSimilarCondition(SqlParser.SimilarConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.SIMILAR);
         WhenOperand.this.setStringValueExpression(WhenOperand.this.getSqlFactory().newStringValueExpression());
         WhenOperand.this.getStringValueExpression().parse(ctx.stringValueExpression());
         if (ctx.ESCAPE() != null) {
            try {
               WhenOperand.this.setEscapeLetter(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
            } catch (ParseException var3) {
               throw new IllegalArgumentException("Error visiting escape letter", var3);
            }
         }

         return WhenOperand.this;
      }

      public WhenOperand visitNullCondition(SqlParser.NullConditionContext ctx) {
         WhenOperand.this.setNullCondition(this.getNullCondition(ctx));
         return WhenOperand.this;
      }

      public WhenOperand visitQuantifiedComparisonCondition(SqlParser.QuantifiedComparisonConditionContext ctx) {
         WhenOperand.this.setCompOp(this.getCompOp(ctx.compOp()));
         WhenOperand.this.setQuantifier(this.getQuantifier(ctx.quantifier()));
         WhenOperand.this.setQueryExpression(WhenOperand.this.getSqlFactory().newQueryExpression());
         WhenOperand.this.getQueryExpression().parse(ctx.queryExpression());
         return WhenOperand.this;
      }

      public WhenOperand visitMatchCondition(SqlParser.MatchConditionContext ctx) {
         WhenOperand.this.setBooleanCondition(BooleanCondition.MATCH);
         if (ctx.UNIQUE() != null) {
            WhenOperand.this.setUnique(true);
         }

         WhenOperand.this.setMatchType(this.getMatchType(ctx.match()));
         WhenOperand.this.setQueryExpression(WhenOperand.this.getSqlFactory().newQueryExpression());
         WhenOperand.this.getQueryExpression().parse(ctx.queryExpression());
         return WhenOperand.this;
      }

      public WhenOperand visitOverlapsCondition(SqlParser.OverlapsConditionContext ctx) {
         WhenOperand.this.setBooleanCondition(BooleanCondition.OVERLAPS);
         WhenOperand.this.setSecondRowValuePredicand(WhenOperand.this.getSqlFactory().newRowValuePredicand());
         WhenOperand.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand());
         return WhenOperand.this;
      }

      public WhenOperand visitDistinctCondition(SqlParser.DistinctConditionContext ctx) {
         WhenOperand.this.setBooleanCondition(BooleanCondition.IS_DISTINCT_FROM);
         WhenOperand.this.setSecondRowValuePredicand(WhenOperand.this.getSqlFactory().newRowValuePredicand());
         WhenOperand.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand());
         return WhenOperand.this;
      }

      public WhenOperand visitMemberCondition(SqlParser.MemberConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.MEMBER_OF);
         WhenOperand.this.setMultisetValueExpression(WhenOperand.this.getSqlFactory().newMultisetValueExpression());
         WhenOperand.this.getMultisetValueExpression().parse(ctx.multisetValueExpression());
         return WhenOperand.this;
      }

      public WhenOperand visitSubmultisetCondition(SqlParser.SubmultisetConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.SUBMULTISET);
         WhenOperand.this.setMultisetValueExpression(WhenOperand.this.getSqlFactory().newMultisetValueExpression());
         WhenOperand.this.getMultisetValueExpression().parse(ctx.multisetValueExpression());
         return WhenOperand.this;
      }

      public WhenOperand visitSetCondition(SqlParser.SetConditionContext ctx) {
         WhenOperand.this.setSetCondition(this.getSetCondition(ctx));
         return WhenOperand.this;
      }

      public WhenOperand visitTypeCondition(SqlParser.TypeConditionContext ctx) {
         if (ctx.NOT() != null) {
            WhenOperand.this.setNot(true);
         }

         WhenOperand.this.setBooleanCondition(BooleanCondition.OF);

         for(int i = 0; i < ctx.udtSpecification().size(); ++i) {
            QualifiedId qiUdtName = new QualifiedId();
            this.setUdtName(ctx.udtSpecification(i).udtName(), qiUdtName);
            WhenOperand.this.getUdtNames().add(qiUdtName);
            Boolean bExclusive = Boolean.FALSE;
            if (ctx.udtSpecification(i).ONLY() != null) {
               bExclusive = Boolean.TRUE;
            }

            WhenOperand.this.getExclusives().add(bExclusive);
         }

         return WhenOperand.this;
      }

      // $FF: synthetic method
      WoVisitor(Object x1) {
         this();
      }
   }
}

package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
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

public class BooleanPrimary extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(BooleanPrimary.class.getName());
   private BooleanPrimary.BpVisitor _visitor = new BooleanPrimary.BpVisitor();
   private CompOp _co = null;
   private Boolean _bNot = false;
   private BooleanCondition _bc = null;
   private RowValuePredicand _rve = null;
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
   private BooleanValueExpression _bve = null;
   private ValueExpressionPrimary _vep = null;

   private BooleanPrimary.BpVisitor getVisitor() {
      return this._visitor;
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

   public RowValuePredicand getRowValuePredicand() {
      return this._rve;
   }

   public void setRowValuePredicand(RowValuePredicand rve) {
      this._rve = rve;
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

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public String format() {
      String s = "";
      if (this.getRowValuePredicand() != null) {
         s = this.getRowValuePredicand().format();
      }

      if (this.getBooleanCondition() != null) {
         int i;
         switch(this.getBooleanCondition()) {
         case BETWEEN:
            if (this.isNot()) {
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords();
            if (this.getSymmetricOption() != null) {
               s = s + " " + this.getSymmetricOption().getKeywords();
            }

            s = s + " " + this.getSecondRowValuePredicand().format() + " " + K.AND.getKeyword() + " " + this.getThirdRowValuePredicand().format();
            break;
         case IN:
            if (this.isNot()) {
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords();
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
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords() + " " + this.getStringValueExpression().format();
            if (this.getEscapeLetter() != null) {
               s = s + " " + K.ESCAPE.getKeyword() + " " + SqlLiterals.formatStringLiteral(this.getEscapeLetter());
            }
            break;
         case SIMILAR:
            if (this.isNot()) {
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords() + " " + K.TO.getKeyword() + " " + this.getStringValueExpression().format();
            if (this.getEscapeLetter() != null) {
               s = s + " " + K.ESCAPE.getKeyword() + " " + SqlLiterals.formatStringLiteral(this.getEscapeLetter());
            }
            break;
         case EXISTS:
         case UNIQUE:
            s = this.getBooleanCondition().getKeywords() + "(" + this.getQueryExpression().format() + ")";
            break;
         case NORMALIZED:
            s = this.getStringValueExpression().format() + " " + K.IS.getKeyword();
            if (this.isNot()) {
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords();
            break;
         case MATCH:
            s = s + " " + this.getBooleanCondition().getKeywords();
            if (this.isUnique()) {
               s = s + " " + K.UNIQUE.getKeyword();
            }

            s = s + " " + this.getMatchType().getKeywords() + "(" + this.getQueryExpression().format() + ")";
            break;
         case OVERLAPS:
         case IS_DISTINCT_FROM:
            s = s + " " + this.getBooleanCondition().getKeywords() + " " + this.getSecondRowValuePredicand().format();
            break;
         case MEMBER_OF:
         case SUBMULTISET:
            if (this.isNot()) {
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords() + " " + this.getMultisetValueExpression().format();
            break;
         case OF:
            s = s + " " + K.IS.getKeyword();
            if (this.isNot()) {
               s = s + " " + K.NOT.getKeyword();
            }

            s = s + " " + this.getBooleanCondition().getKeywords() + "(";

            for(i = 0; i < this.getUdtNames().size(); ++i) {
               if (i > 0) {
                  s = s + "," + " ";
               }

               if ((Boolean)this.getExclusives().get(i)) {
                  s = s + K.ONLY.getKeyword() + " ";
               }

               s = s + ((QualifiedId)this.getUdtNames().get(i)).format();
            }

            s = s + ")";
         }
      } else if (this.getQuantifier() != null && this.getCompOp() != null) {
         s = s + " " + this.getCompOp().getKeywords() + " " + this.getQuantifier().getKeywords() + "(" + this.getQueryExpression().format() + ")";
      } else if (this.getCompOp() != null) {
         s = s + " " + this.getCompOp().getKeywords() + " " + this.getSecondRowValuePredicand().format();
      } else if (this.getNullCondition() != null) {
         s = s + " " + this.getNullCondition().getKeywords();
      } else if (this.getSetCondition() != null) {
         s = s + " " + this.getSetCondition().getKeywords();
      } else if (this.getBooleanValueExpression() != null) {
         s = "(" + this.getBooleanValueExpression().format() + ")";
      } else if (this.getValueExpressionPrimary() != null) {
         s = this.getValueExpressionPrimary().format();
      }

      return s;
   }

   private Boolean evaluateLike(String sValue, String sPattern, String sEscape) {
      Boolean bValue = null;
      if (sValue != null && sValue != null && sPattern != null) {
         bValue = Boolean.TRUE;
         int iPattern = 0;
         boolean bEscaped = false;

         for(int iPosition = 0; Boolean.TRUE.equals(bValue) && iPosition < sValue.length(); ++iPosition) {
            char c = sValue.charAt(iPosition);
            char cPattern = sValue.charAt(iPattern);
            if (sEscape != null && c == sEscape.charAt(0)) {
               bEscaped = true;
            } else {
               if (!bEscaped) {
                  if (cPattern != '_' && cPattern != '%') {
                     if (c != cPattern) {
                        bValue = Boolean.FALSE;
                     }
                  } else if (cPattern == '%' && iPattern < sPattern.length()) {
                     ++iPosition;
                     if (iPosition < sValue.length()) {
                        ++iPattern;
                        cPattern = sPattern.charAt(iPattern);

                        for(c = sValue.charAt(iPosition); c != cPattern && iPosition < sValue.length(); c = sValue.charAt(iPosition)) {
                           ++iPosition;
                        }

                        --iPattern;
                     }

                     --iPosition;
                  }
               } else {
                  if (c != '_' && c != '%') {
                     throw new IllegalArgumentException("Only '_' and '%' can be escaped with a LIKE escape!");
                  }

                  if (c != cPattern) {
                     bValue = Boolean.FALSE;
                  }
               }

               ++iPattern;
               bEscaped = false;
            }
         }
      }

      return bValue;
   }

   private Boolean evaluateComparison(CompOp co, Object oValue, Object oValue2) {
      Boolean bValue = null;
      if (oValue != null) {
         Comparable cmpValue = null;
         if (oValue instanceof Comparable) {
            cmpValue = (Comparable)oValue;
         }

         switch(co) {
         case EQUALS_OPERATOR:
            bValue = oValue.equals(oValue2);
            break;
         case NOT_EQUALS_OPERATOR:
            bValue = !oValue.equals(oValue2);
            break;
         case LESS_THAN_OPERATOR:
            bValue = cmpValue.compareTo(oValue2) < 0;
            break;
         case LESS_THAN_OR_EQUALS_OPERATOR:
            bValue = cmpValue.compareTo(oValue2) <= 0;
            break;
         case GREATER_THAN_OPERATOR:
            bValue = cmpValue.compareTo(oValue2) > 0;
            break;
         case GREATER_THAN_OR_EQUALS_OPERATOR:
            bValue = cmpValue.compareTo(oValue2) >= 0;
         }
      }

      return bValue;
   }

   private Boolean evaluateNullCondition(NullCondition nc, Object oValue) {
      Boolean bValue = null;
      if (oValue != null) {
         switch(nc) {
         case IS_NULL:
            bValue = oValue == null;
            break;
         case IS_NOT_NULL:
            bValue = oValue != null;
         }
      }

      return bValue;
   }

   private Boolean evaluate(Object oRowValuePredicand, Object oSecondRowValuePredicand, Object oStringValue, Boolean bBooleanValue, Object oValuePrimary) {
      Boolean bValue = null;
      BooleanCondition bc = this.getBooleanCondition();
      if (bc != null) {
         if (bc != BooleanCondition.LIKE) {
            throw new IllegalArgumentException("Boolean condition " + bc.getKeywords() + " cannot be evaluated!");
         }

         bValue = this.evaluateLike((String)oRowValuePredicand, (String)oStringValue, this.getEscapeLetter());
      } else {
         if (this.getQuantifier() != null) {
            throw new IllegalArgumentException("Quantifier " + this.getQuantifier().getKeywords() + " cannot be evaluated!");
         }

         if (this.getCompOp() != null) {
            bValue = this.evaluateComparison(this.getCompOp(), oRowValuePredicand, oSecondRowValuePredicand);
         } else if (this.getNullCondition() != null) {
            bValue = this.evaluateNullCondition(this.getNullCondition(), oRowValuePredicand);
         } else {
            if (this.getSetCondition() != null) {
               throw new IllegalArgumentException("SET condition cannot be evaluated!");
            }

            if (this.getBooleanValueExpression() != null) {
               bValue = bBooleanValue;
            } else if (this.getValueExpressionPrimary() != null) {
               bValue = (Boolean)oValuePrimary;
            }
         }
      }

      return bValue;
   }

   public Boolean evaluate(SqlStatement ss, boolean bAggregated) {
      Object oRowValuePredicand = null;
      if (this.getRowValuePredicand() != null) {
         oRowValuePredicand = this.getRowValuePredicand().evaluate(ss, bAggregated);
      }

      Object oSecondRowValuePredicand = null;
      if (this.getSecondRowValuePredicand() != null) {
         oSecondRowValuePredicand = this.getSecondRowValuePredicand().evaluate(ss, bAggregated);
      }

      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().evaluate(ss, bAggregated);
      }

      Boolean bBooleanValue = null;
      if (this.getBooleanValueExpression() != null) {
         bBooleanValue = this.getBooleanValueExpression().evaluate(ss, bAggregated);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Boolean bValue = this.evaluate(oRowValuePredicand, oSecondRowValuePredicand, oStringValue, bBooleanValue, oValuePrimary);
      return bValue;
   }

   public Boolean resetAggregates(SqlStatement ss) {
      Object oRowValuePredicand = null;
      if (this.getRowValuePredicand() != null) {
         oRowValuePredicand = this.getRowValuePredicand().resetAggregates(ss);
      }

      Object oSecondRowValuePredicand = null;
      if (this.getSecondRowValuePredicand() != null) {
         oSecondRowValuePredicand = this.getSecondRowValuePredicand().resetAggregates(ss);
      }

      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().resetAggregates(ss);
      }

      Boolean bBooleanValue = null;
      if (this.getBooleanValueExpression() != null) {
         bBooleanValue = this.getBooleanValueExpression().resetAggregates(ss);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Boolean bValue = this.evaluate(oRowValuePredicand, oSecondRowValuePredicand, oStringValue, bBooleanValue, oValuePrimary);
      return bValue;
   }

   public void parse(SqlParser.BooleanPrimaryContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().booleanPrimary());
   }

   public void initialize(CompOp co, Boolean bNot, BooleanCondition bc, RowValuePredicand rve, RowValuePredicand rve2, RowValuePredicand rve3, SymmetricOption so, List<RowValueExpression> listRve, QueryExpression qe, StringValueExpression sve, String sEscapeLetter, NullCondition nc, Quantifier q, boolean bUnique, MatchType mt, MultisetValueExpression mve, SetCondition sc, List<QualifiedId> listUdtNames, List<Boolean> listExclusives, BooleanValueExpression bve, ValueExpressionPrimary vep) {
      _il.enter(new Object[]{co, String.valueOf(bNot), bc, rve, rve2, rve3, so, listRve, qe, sve, sEscapeLetter, nc, q, bUnique, mt, mve, sc, listUdtNames, listExclusives, bve, vep});
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
      this.setBooleanValueExpression(bve);
      this.setValueExpressionPrimary(vep);
      _il.exit();
   }

   public BooleanPrimary(SqlFactory sf) {
      super(sf);
   }

   private class BpVisitor extends EnhancedSqlBaseVisitor<BooleanPrimary> {
      private BpVisitor() {
      }

      public BooleanPrimary visitBooleanPrimary(SqlParser.BooleanPrimaryContext ctx) {
         if (ctx.rowValuePredicand() != null) {
            BooleanPrimary.this.setRowValuePredicand(BooleanPrimary.this.getSqlFactory().newRowValuePredicand());
            BooleanPrimary.this.getRowValuePredicand().parse(ctx.rowValuePredicand());
         }

         if (ctx.EXISTS() != null) {
            BooleanPrimary.this.setBooleanCondition(BooleanCondition.EXISTS);
         } else if (ctx.UNIQUE() != null) {
            BooleanPrimary.this.setBooleanCondition(BooleanCondition.UNIQUE);
         } else if (ctx.NORMALIZED() != null) {
            BooleanPrimary.this.setBooleanCondition(BooleanCondition.NORMALIZED);
         }

         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         if (ctx.stringValueExpression() != null) {
            BooleanPrimary.this.setStringValueExpression(BooleanPrimary.this.getSqlFactory().newStringValueExpression());
            BooleanPrimary.this.getStringValueExpression().parse(ctx.stringValueExpression());
         }

         if (ctx.queryExpression() != null) {
            BooleanPrimary.this.setQueryExpression(BooleanPrimary.this.getSqlFactory().newQueryExpression());
            BooleanPrimary.this.getQueryExpression().parse(ctx.queryExpression());
         }

         return (BooleanPrimary)this.visitChildren(ctx);
      }

      public BooleanPrimary visitComparisonCondition(SqlParser.ComparisonConditionContext ctx) {
         BooleanPrimary.this.setCompOp(this.getCompOp(ctx.compOp()));
         BooleanPrimary.this.setSecondRowValuePredicand(BooleanPrimary.this.getSqlFactory().newRowValuePredicand());
         BooleanPrimary.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitBetweenCondition(SqlParser.BetweenConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.BETWEEN);
         if (ctx.symmetricOption() != null) {
            BooleanPrimary.this.setSymmetricOption(this.getSymmetricOption(ctx.symmetricOption()));
         }

         BooleanPrimary.this.setSecondRowValuePredicand(BooleanPrimary.this.getSqlFactory().newRowValuePredicand());
         BooleanPrimary.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand(0));
         BooleanPrimary.this.setThirdRowValuePredicand(BooleanPrimary.this.getSqlFactory().newRowValuePredicand());
         BooleanPrimary.this.getThirdRowValuePredicand().parse(ctx.rowValuePredicand(1));
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitInCondition(SqlParser.InConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.IN);
         if (ctx.queryExpression() != null) {
            BooleanPrimary.this.setQueryExpression(BooleanPrimary.this.getSqlFactory().newQueryExpression());
            BooleanPrimary.this.getQueryExpression().parse(ctx.queryExpression());
         } else {
            for(int i = 0; i < ctx.rowValueExpression().size(); ++i) {
               RowValueExpression rve = BooleanPrimary.this.getSqlFactory().newRowValueExpression();
               rve.parse(ctx.rowValueExpression(i));
               BooleanPrimary.this.getRowValueExpressions().add(rve);
            }
         }

         return BooleanPrimary.this;
      }

      public BooleanPrimary visitLikeCondition(SqlParser.LikeConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.LIKE);
         BooleanPrimary.this.setStringValueExpression(BooleanPrimary.this.getSqlFactory().newStringValueExpression());
         BooleanPrimary.this.getStringValueExpression().parse(ctx.stringValueExpression());
         if (ctx.ESCAPE() != null) {
            try {
               BooleanPrimary.this.setEscapeLetter(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
            } catch (ParseException var3) {
               throw new IllegalArgumentException("Error visiting escape letter", var3);
            }
         }

         return BooleanPrimary.this;
      }

      public BooleanPrimary visitSimilarCondition(SqlParser.SimilarConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.SIMILAR);
         BooleanPrimary.this.setStringValueExpression(BooleanPrimary.this.getSqlFactory().newStringValueExpression());
         BooleanPrimary.this.getStringValueExpression().parse(ctx.stringValueExpression());
         if (ctx.ESCAPE() != null) {
            try {
               BooleanPrimary.this.setEscapeLetter(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
            } catch (ParseException var3) {
               throw new IllegalArgumentException("Error visiting escape letter", var3);
            }
         }

         return BooleanPrimary.this;
      }

      public BooleanPrimary visitNullCondition(SqlParser.NullConditionContext ctx) {
         BooleanPrimary.this.setNullCondition(this.getNullCondition(ctx));
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitQuantifiedComparisonCondition(SqlParser.QuantifiedComparisonConditionContext ctx) {
         BooleanPrimary.this.setCompOp(this.getCompOp(ctx.compOp()));
         BooleanPrimary.this.setQuantifier(this.getQuantifier(ctx.quantifier()));
         BooleanPrimary.this.setQueryExpression(BooleanPrimary.this.getSqlFactory().newQueryExpression());
         BooleanPrimary.this.getQueryExpression().parse(ctx.queryExpression());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitMatchCondition(SqlParser.MatchConditionContext ctx) {
         BooleanPrimary.this.setBooleanCondition(BooleanCondition.MATCH);
         if (ctx.UNIQUE() != null) {
            BooleanPrimary.this.setUnique(true);
         }

         BooleanPrimary.this.setMatchType(this.getMatchType(ctx.match()));
         BooleanPrimary.this.setQueryExpression(BooleanPrimary.this.getSqlFactory().newQueryExpression());
         BooleanPrimary.this.getQueryExpression().parse(ctx.queryExpression());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitOverlapsCondition(SqlParser.OverlapsConditionContext ctx) {
         BooleanPrimary.this.setBooleanCondition(BooleanCondition.OVERLAPS);
         BooleanPrimary.this.setSecondRowValuePredicand(BooleanPrimary.this.getSqlFactory().newRowValuePredicand());
         BooleanPrimary.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitDistinctCondition(SqlParser.DistinctConditionContext ctx) {
         BooleanPrimary.this.setBooleanCondition(BooleanCondition.IS_DISTINCT_FROM);
         BooleanPrimary.this.setSecondRowValuePredicand(BooleanPrimary.this.getSqlFactory().newRowValuePredicand());
         BooleanPrimary.this.getSecondRowValuePredicand().parse(ctx.rowValuePredicand());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitMemberCondition(SqlParser.MemberConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.MEMBER_OF);
         BooleanPrimary.this.setMultisetValueExpression(BooleanPrimary.this.getSqlFactory().newMultisetValueExpression());
         BooleanPrimary.this.getMultisetValueExpression().parse(ctx.multisetValueExpression());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitSubmultisetCondition(SqlParser.SubmultisetConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.SUBMULTISET);
         BooleanPrimary.this.setMultisetValueExpression(BooleanPrimary.this.getSqlFactory().newMultisetValueExpression());
         BooleanPrimary.this.getMultisetValueExpression().parse(ctx.multisetValueExpression());
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitSetCondition(SqlParser.SetConditionContext ctx) {
         BooleanPrimary.this.setSetCondition(this.getSetCondition(ctx));
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitTypeCondition(SqlParser.TypeConditionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanPrimary.this.setNot(true);
         }

         BooleanPrimary.this.setBooleanCondition(BooleanCondition.OF);

         for(int i = 0; i < ctx.udtSpecification().size(); ++i) {
            QualifiedId qiUdtName = new QualifiedId();
            this.setUdtName(ctx.udtSpecification(i).udtName(), qiUdtName);
            BooleanPrimary.this.getUdtNames().add(qiUdtName);
            Boolean bExclusive = Boolean.FALSE;
            if (ctx.udtSpecification(i).ONLY() != null) {
               bExclusive = Boolean.TRUE;
            }

            BooleanPrimary.this.getExclusives().add(bExclusive);
         }

         return BooleanPrimary.this;
      }

      public BooleanPrimary visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         BooleanPrimary.this.setBooleanValueExpression(BooleanPrimary.this.getSqlFactory().newBooleanValueExpression());
         BooleanPrimary.this.getBooleanValueExpression().parse(ctx);
         return BooleanPrimary.this;
      }

      public BooleanPrimary visitValueExpressionPrimary(SqlParser.ValueExpressionPrimaryContext ctx) {
         BooleanPrimary.this.setValueExpressionPrimary(BooleanPrimary.this.getSqlFactory().newValueExpressionPrimary());
         BooleanPrimary.this.getValueExpressionPrimary().parse(ctx);
         return BooleanPrimary.this;
      }

      // $FF: synthetic method
      BpVisitor(Object x1) {
         this();
      }
   }
}

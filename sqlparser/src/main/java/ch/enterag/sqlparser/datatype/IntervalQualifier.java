package ch.enterag.sqlparser.datatype;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.enums.IntervalField;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class IntervalQualifier extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(IntervalQualifier.class.getName());
   public static final int iUNDEFINED = -1;
   public static final int iTIME_PRECISION_DEFAULT = 0;
   public static final int iTIMESTAMP_PRECISION_DEFAULT = 6;
   private IntervalQualifier.IqVisitor _visitor = new IntervalQualifier.IqVisitor();
   private IntervalField _startField = null;
   private IntervalField _endField = null;
   private int _iPrecision = -1;
   private int _iSecondsDecimals = -1;

   private IntervalQualifier.IqVisitor getVisitor() {
      return this._visitor;
   }

   public IntervalField getStartField() {
      return this._startField;
   }

   public void setStartField(IntervalField startField) {
      this._startField = startField;
   }

   public IntervalField getEndField() {
      return this._endField;
   }

   public void setEndField(IntervalField endField) {
      this._endField = endField;
   }

   private void setField(IntervalField field) {
      if (this.getStartField() == null) {
         this.setStartField(field);
      } else {
         this.setEndField(field);
      }

   }

   public int getPrecision() {
      return this._iPrecision;
   }

   public void setPrecision(int iPrecision) {
      this._iPrecision = iPrecision;
   }

   public int getSecondsDecimals() {
      return this._iSecondsDecimals;
   }

   public void setSecondsDecimals(int iSecondsDecimals) {
      this._iSecondsDecimals = iSecondsDecimals;
   }

   public String format() {
      String sQualifier = this.getStartField().getKeywords();
      if (this.getPrecision() != -1) {
         sQualifier = sQualifier + "(" + this.getPrecision();
         if (this.getStartField() == IntervalField.SECOND && this.getEndField() == null && this.getSecondsDecimals() != -1) {
            sQualifier = sQualifier + "," + " " + this.getSecondsDecimals();
         }

         sQualifier = sQualifier + ")";
      }

      if (this.getEndField() != null) {
         sQualifier = sQualifier + " " + K.TO + " " + this.getEndField().getKeywords();
         if (this.getEndField() == IntervalField.SECOND && this.getSecondsDecimals() != -1) {
            sQualifier = sQualifier + "(" + this.getSecondsDecimals() + ")";
         }
      }

      return sQualifier;
   }

   public void parse(SqlParser.IntervalQualifierContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().intervalQualifier());
   }

   public void initialize(IntervalField startField, IntervalField endField, int iPrecision, int iSecondsDecimals) {
      _il.enter(new Object[]{startField, endField, String.valueOf(iPrecision), String.valueOf(iSecondsDecimals)});
      this.setStartField(startField);
      this.setEndField(endField);
      this.setPrecision(iPrecision);
      this.setSecondsDecimals(iSecondsDecimals);
      _il.exit();
   }

   public IntervalQualifier(SqlFactory sf) {
      super(sf);
   }

   private class IqVisitor extends EnhancedSqlBaseVisitor<IntervalQualifier> {
      private IqVisitor() {
      }

      public IntervalQualifier visitIntervalQualifier(SqlParser.IntervalQualifierContext ctx) {
         if (ctx.YEAR() != null) {
            IntervalQualifier.this.setField(IntervalField.YEAR);
         }

         if (ctx.MONTH() != null) {
            IntervalQualifier.this.setField(IntervalField.MONTH);
         }

         if (ctx.DAY() != null) {
            IntervalQualifier.this.setField(IntervalField.DAY);
         }

         if (ctx.HOUR() != null) {
            IntervalQualifier.this.setField(IntervalField.HOUR);
         }

         if (ctx.MINUTE() != null) {
            IntervalQualifier.this.setField(IntervalField.MINUTE);
         }

         if (ctx.SECOND() != null) {
            IntervalQualifier.this.setField(IntervalField.SECOND);
         }

         return (IntervalQualifier)this.visitChildren(ctx);
      }

      public IntervalQualifier visitSecondsDecimals(SqlParser.SecondsDecimalsContext ctx) {
         IntervalQualifier.this.setSecondsDecimals(Integer.parseInt(ctx.getText()));
         return IntervalQualifier.this;
      }

      public IntervalQualifier visitPrecision(SqlParser.PrecisionContext ctx) {
         IntervalQualifier.this.setPrecision(Integer.parseInt(ctx.getText()));
         return IntervalQualifier.this;
      }

      // $FF: synthetic method
      IqVisitor(Object x1) {
         this();
      }
   }
}

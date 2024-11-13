package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.BooleanLiteral;
import ch.enterag.sqlparser.expression.enums.Sign;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;

public class Literal extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(Literal.class.getName());
   private Literal.LitVisitor _visitor = new Literal.LitVisitor();
   private Sign _sign = null;
   private Double _d = null;
   private BigDecimal _bd = null;
   private String _sCharacterString = null;
   private String _sNationalCharacterString = null;
   private String _sBitString = null;
   private byte[] _buf = null;
   private Date _date = null;
   private Time _time = null;
   private Timestamp _ts = null;
   private BooleanLiteral _bl = null;

   private Literal.LitVisitor getVisitor() {
      return this._visitor;
   }

   public Sign getSign() {
      return this._sign;
   }

   public void setSign(Sign sign) {
      this._sign = sign;
   }

   public Double getApproximate() {
      return this._d;
   }

   public void setApproximate(Double d) {
      this._d = d;
   }

   public BigDecimal getExact() {
      return this._bd;
   }

   public void setExact(BigDecimal bd) {
      this._bd = bd;
   }

   public String getCharacterString() {
      return this._sCharacterString;
   }

   public void setCharacterString(String sCharacterStringLiteral) {
      this._sCharacterString = sCharacterStringLiteral;
   }

   public String getNationalCharacterString() {
      return this._sNationalCharacterString;
   }

   public void setNationalCharacterString(String sNationalCharacterString) {
      this._sNationalCharacterString = sNationalCharacterString;
   }

   public String getBitString() {
      return this._sBitString;
   }

   public void setBitString(String sBitString) {
      this._sBitString = sBitString;
   }

   public byte[] getBytes() {
      return this._buf;
   }

   public void setBytes(byte[] buf) {
      this._buf = buf;
   }

   public Date getDate() {
      return this._date;
   }

   public void setDate(Date date) {
      this._date = date;
   }

   public Time getTime() {
      return this._time;
   }

   public void setTime(Time time) {
      this._time = time;
   }

   public Timestamp getTimestamp() {
      return this._ts;
   }

   public void setTimestamp(Timestamp ts) {
      this._ts = ts;
   }

   public BooleanLiteral getBoolean() {
      return this._bl;
   }

   public void setBooleanLiteral(BooleanLiteral bl) {
      this._bl = bl;
   }

   public String format() {
      String sFormatted = "";
      if (this.getApproximate() != null) {
         if (this.getSign() != null) {
            sFormatted = this.getSign().getKeywords();
         }

         sFormatted = sFormatted + SqlLiterals.formatApproximateLiteral(this.getApproximate());
      } else if (this.getExact() != null) {
         if (this.getSign() != null) {
            sFormatted = this.getSign().getKeywords();
         }

         sFormatted = sFormatted + SqlLiterals.formatExactLiteral(this.getExact());
      } else if (this.getCharacterString() != null) {
         sFormatted = SqlLiterals.formatStringLiteral(this.getCharacterString());
      } else if (this.getNationalCharacterString() != null) {
         sFormatted = SqlLiterals.formatNationalStringLiteral(this.getNationalCharacterString());
      } else if (this.getBitString() != null) {
         sFormatted = SqlLiterals.formatBitStringLiteral(this.getBitString());
      } else if (this.getBytes() != null) {
         sFormatted = SqlLiterals.formatBytesLiteral(this.getBytes());
      } else if (this.getDate() != null) {
         sFormatted = SqlLiterals.formatDateLiteral(this.getDate());
      } else if (this.getTime() != null) {
         sFormatted = SqlLiterals.formatTimeLiteral(this.getTime());
      } else if (this.getTimestamp() != null) {
         sFormatted = SqlLiterals.formatTimestampLiteral(this.getTimestamp());
      } else if (this.getBoolean() != null) {
         sFormatted = SqlLiterals.formatBooleanLiteral(this.getBoolean());
      }

      return sFormatted;
   }

   public void parse(SqlParser.LiteralContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().literal());
   }

   public void initialize(Sign sign, Double dApproximate, BigDecimal bdExact, String sCharacterString, String sNationalCharacterString, String sBitString, byte[] bufBytes, Date date, Time time, Timestamp ts, BooleanLiteral bl) {
      _il.enter(new Object[]{sign, dApproximate, bdExact, sCharacterString, sNationalCharacterString, sBitString, bufBytes, date, time, ts, bl});
      this.setSign(sign);
      this.setApproximate(dApproximate);
      this.setExact(bdExact);
      this.setCharacterString(sCharacterString);
      this.setNationalCharacterString(sNationalCharacterString);
      this.setBitString(sBitString);
      this.setBytes(bufBytes);
      this.setDate(date);
      this.setTime(time);
      this.setTimestamp(ts);
      this.setBooleanLiteral(bl);
      _il.exit();
   }

   public Literal(SqlFactory sf) {
      super(sf);
   }

   private class LitVisitor extends EnhancedSqlBaseVisitor<Literal> {
      private LitVisitor() {
      }

      public Literal visitLiteral(SqlParser.LiteralContext ctx) {
         if (ctx.sign() != null) {
            Literal.this.setSign(this.getSign(ctx.sign()));
         }

         return Literal.this;
      }

      public Literal visitGeneralLiteral(SqlParser.GeneralLiteralContext ctx) {
         try {
            if (ctx.CHARACTER_STRING_LITERAL() != null) {
               Literal.this.setCharacterString(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
            } else if (ctx.NATIONAL_CHARACTER_STRING_LITERAL() != null) {
               Literal.this.setNationalCharacterString(SqlLiterals.parseNationalStringLiteral(ctx.NATIONAL_CHARACTER_STRING_LITERAL().getText()));
            } else if (ctx.BIT_STRING_LITERAL() != null) {
               Literal.this.setBitString(SqlLiterals.parseBitStringLiteral(ctx.BIT_STRING_LITERAL().getText()));
            } else if (ctx.BYTE_STRING_LITERAL() != null) {
               Literal.this.setBytes(SqlLiterals.parseBytesLiteral(ctx.BYTE_STRING_LITERAL().getText()));
            } else if (ctx.DATE_LITERAL() != null) {
               Literal.this.setDate(SqlLiterals.parseDateLiteral(ctx.DATE_LITERAL().getText()));
            } else if (ctx.TIME_LITERAL() != null) {
               Literal.this.setTime(SqlLiterals.parseTimeLiteral(ctx.TIME_LITERAL().getText()));
            } else if (ctx.TIMESTAMP_LITERAL() != null) {
               Literal.this.setTimestamp(SqlLiterals.parseTimestampLiteral(ctx.TIMESTAMP_LITERAL().getText()));
            } else if (ctx.BOOLEAN_LITERAL() != null) {
               Literal.this.setBooleanLiteral(SqlLiterals.parseBooleanLiteral(ctx.BOOLEAN_LITERAL().getText()));
            }
         } catch (ParseException var3) {
            throw new IllegalArgumentException("Error visiting general literal!", var3);
         }

         return Literal.this;
      }

      public Literal visitUnsignedNumericLiteral(SqlParser.UnsignedNumericLiteralContext ctx) {
         if (ctx.UNSIGNED_APPROXIMATE() != null) {
            try {
               Literal.this.setApproximate(SqlLiterals.parseApproximateLiteral(ctx.getText()));
            } catch (ParseException var3) {
               throw new IllegalArgumentException("Error visiting approximate numeric literal!", var3);
            }
         }

         return (Literal)this.visitChildren(ctx);
      }

      public Literal visitExactNumericLiteral(SqlParser.ExactNumericLiteralContext ctx) {
         try {
            Literal.this.setExact(SqlLiterals.parseExactLiteral(ctx.getText()));
         } catch (ParseException var3) {
            throw new IllegalArgumentException("Error visiting exact numeric literal!", var3);
         }

         return Literal.this;
      }

      // $FF: synthetic method
      LitVisitor(Object x1) {
         this();
      }
   }
}

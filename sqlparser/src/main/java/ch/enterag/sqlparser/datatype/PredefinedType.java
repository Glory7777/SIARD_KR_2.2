package ch.enterag.sqlparser.datatype;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.enums.IntervalField;
import ch.enterag.sqlparser.datatype.enums.PreType;
import ch.enterag.sqlparser.ddl.enums.Multiplier;
import ch.enterag.sqlparser.ddl.enums.WithOrWithoutTimeZone;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class PredefinedType extends SqlBase {
   protected static IndentLogger _il = IndentLogger.getIndentLogger(PredefinedType.class.getName());
   public static final int iUNDEFINED = -1;
   public static final int iTIME_DECIMALS_DEFAULT = 0;
   public static final int iTIMESTAMP_DECIMALS_DEFAULT = 6;
   private PredefinedType.PdtVisitor _visitor = new PredefinedType.PdtVisitor();
   private PreType _type = null;
   private int _iLength = -1;
   private int _iPrecision = -1;
   private int _iScale = -1;
   private int _iSecondsDecimals = -1;
   private Multiplier _multiplier = null;
   private WithOrWithoutTimeZone _wowtz = null;
   private IntervalQualifier _iq = null;

   private PredefinedType.PdtVisitor getVisitor() {
      return this._visitor;
   }

   public PreType getType() {
      return this._type;
   }

   public void setType(PreType type) {
      this._type = type;
   }

   public int getLength() {
      return this._iLength;
   }

   public void setLength(int iLength) {
      if (iLength != 0) {
         this._iLength = iLength;
      }

   }

   public int getPrecision() {
      return this._iPrecision;
   }

   public void setPrecision(int iPrecision) {
      if (iPrecision != 0) {
         this._iPrecision = iPrecision;
      }

   }

   public int getScale() {
      return this._iScale;
   }

   public void setScale(int iScale) {
      if (iScale != 0) {
         this._iScale = iScale;
      }

   }

   public int getSecondsDecimals() {
      return this._iSecondsDecimals;
   }

   public void setSecondsDecimals(int iSecondsDecimals) {
      if (iSecondsDecimals != 0) {
         this._iSecondsDecimals = iSecondsDecimals;
      }

   }

   public Multiplier getMultiplier() {
      return this._multiplier;
   }

   public void setMultiplier(Multiplier multiplier) {
      this._multiplier = multiplier;
   }

   public WithOrWithoutTimeZone getWithOrWithoutTimeZone() {
      return this._wowtz;
   }

   public void setWithOrWithoutTimeZone(WithOrWithoutTimeZone wowtz) {
      this._wowtz = wowtz;
   }

   public IntervalQualifier getIntervalQualifier() {
      return this._iq;
   }

   public void setIntervalQualifier(IntervalQualifier iq) {
      this._iq = iq;
   }

   protected String formatLength() {
      String sLength = "";
      if (this.getLength() != -1) {
         sLength = "(" + String.valueOf(this.getLength()) + ")";
      }

      return sLength;
   }

   protected String formatLobLength() {
      String sLength = "";
      if (this.getLength() != -1) {
         sLength = "(" + String.valueOf(this.getLength());
         if (this.getMultiplier() != null) {
            sLength = sLength + this.getMultiplier().getKeyword();
         }

         sLength = sLength + ")";
      }

      return sLength;
   }

   protected String formatPrecisionScale() {
      String sPrecisionScale = "";
      if (this.getPrecision() != -1) {
         sPrecisionScale = "(" + String.valueOf(this.getPrecision());
         if (this.getScale() != -1) {
            sPrecisionScale = sPrecisionScale + "," + " " + this.getScale();
         }

         sPrecisionScale = sPrecisionScale + ")";
      }

      return sPrecisionScale;
   }

   protected String formatSecondsDecimals(int iDefaultDecimals) {
      String sSecondsDecimals = "";
      if (this.getSecondsDecimals() != -1 && this.getSecondsDecimals() != iDefaultDecimals) {
         sSecondsDecimals = sSecondsDecimals + "(" + this.getSecondsDecimals() + ")";
      }

      return sSecondsDecimals;
   }

   protected String formatTimeZone() {
      String sTimeZone = "";
      if (this.getWithOrWithoutTimeZone() != null) {
         sTimeZone = " " + this.getWithOrWithoutTimeZone().getKeywords();
      }

      return sTimeZone;
   }

   public String format() {
      String sType = null;
      if (this.getType() != null) {
         sType = this.getType().getKeyword();
         if (this.getType() != PreType.CHAR && this.getType() != PreType.VARCHAR && this.getType() != PreType.NCHAR && this.getType() != PreType.NVARCHAR && this.getType() != PreType.BINARY && this.getType() != PreType.VARBINARY) {
            if (this.getType() != PreType.CLOB && this.getType() != PreType.NCLOB && this.getType() != PreType.BLOB) {
               if (this.getType() != PreType.NUMERIC && this.getType() != PreType.DECIMAL && this.getType() != PreType.FLOAT) {
                  if (this.getType() == PreType.TIME) {
                     sType = sType + this.formatSecondsDecimals(0) + this.formatTimeZone();
                  } else if (this.getType() == PreType.TIMESTAMP) {
                     sType = sType + this.formatSecondsDecimals(6) + this.formatTimeZone();
                  } else if (this.getType() == PreType.INTERVAL) {
                     sType = sType + " " + this.getIntervalQualifier().format();
                  }
               } else {
                  sType = sType + this.formatPrecisionScale();
               }
            } else {
               sType = sType + this.formatLobLength();
            }
         } else {
            sType = sType + this.formatLength();
         }
      }

      return sType;
   }

   public void parse(SqlParser.PredefinedTypeContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.PredefinedTypeContext ctx = null;

      try {
         ctx = this.getParser().predefinedType();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().predefinedType();
      }

      this.parse(ctx);
   }

   public void initCharType(int iLength) {
      this.initialize(PreType.CHAR, iLength, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initVarCharType(int iLength) {
      this.initialize(PreType.VARCHAR, iLength, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initClobType(int iLength, Multiplier multiplier) {
      this.initialize(PreType.CLOB, iLength, multiplier, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initNCharType(int iLength) {
      this.initialize(PreType.NCHAR, iLength, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initNVarCharType(int iLength) {
      this.initialize(PreType.NVARCHAR, iLength, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initNClobType(int iLength, Multiplier multiplier) {
      this.initialize(PreType.NCLOB, iLength, multiplier, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initXmlType() {
      this.initialize(PreType.XML, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initBinaryType(int iLength) {
      this.initialize(PreType.BINARY, iLength, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initVarbinaryType(int iLength) {
      this.initialize(PreType.VARBINARY, iLength, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initBlobType(int iLength, Multiplier multiplier) {
      this.initialize(PreType.BLOB, iLength, multiplier, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initNumericType(int iPrecision, int iScale) {
      this.initialize(PreType.NUMERIC, -1, (Multiplier)null, iPrecision, iScale, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initDecimalType(int iPrecision, int iScale) {
      this.initialize(PreType.DECIMAL, -1, (Multiplier)null, iPrecision, iScale, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initSmallIntType() {
      this.initialize(PreType.SMALLINT, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initIntegerType() {
      this.initialize(PreType.INTEGER, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initBigIntType() {
      this.initialize(PreType.BIGINT, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initFloatType(int iPrecision) {
      this.initialize(PreType.FLOAT, -1, (Multiplier)null, iPrecision, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initRealType() {
      this.initialize(PreType.REAL, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initDoubleType() {
      this.initialize(PreType.DOUBLE, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initBooleanType() {
      this.initialize(PreType.BOOLEAN, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initDatalinkType() {
      this.initialize(PreType.DATALINK, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initDateType() {
      this.initialize(PreType.DATE, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
   }

   public void initTimeType(int iSecondsDecimals, WithOrWithoutTimeZone wowtz) {
      this.initialize(PreType.TIME, -1, (Multiplier)null, -1, -1, iSecondsDecimals, wowtz, (IntervalQualifier)null);
   }

   public void initTimestampType(int iSecondsDecimals, WithOrWithoutTimeZone wowtz) {
      this.initialize(PreType.TIMESTAMP, -1, (Multiplier)null, -1, -1, iSecondsDecimals, wowtz, (IntervalQualifier)null);
   }

   public void initIntervalType(IntervalField ifStart, IntervalField ifEnd, int iPrecision, int iSecondsDecimals) {
      IntervalQualifier iq = this.getSqlFactory().newIntervalQualifier();
      iq.initialize(ifStart, ifEnd, iPrecision, iSecondsDecimals);
      this.initialize(PreType.INTERVAL, -1, (Multiplier)null, -1, -1, -1, (WithOrWithoutTimeZone)null, iq);
   }

   public void initialize(PreType pretype, int iLength, Multiplier multiplier, int iPrecision, int iScale, int iSecondsDecimals, WithOrWithoutTimeZone wowtz, IntervalQualifier iq) {
      _il.enter(new Object[]{pretype, String.valueOf(iLength), multiplier, String.valueOf(iPrecision), String.valueOf(iScale), String.valueOf(iSecondsDecimals), wowtz, iq});
      this.setType(pretype);
      this.setLength(iLength);
      this.setMultiplier(multiplier);
      this.setPrecision(iPrecision);
      this.setScale(iScale);
      this.setSecondsDecimals(iSecondsDecimals);
      this.setWithOrWithoutTimeZone(wowtz);
      this.setIntervalQualifier(iq);
      _il.exit();
   }

   public void initialize(int iDataType, long lPrecision, int iScale) {
      long lGiga = 2147483648L;
      int iPrecision = -1;
      int iLength = -1;
      Multiplier multiplier = null;
      if (lPrecision < lGiga) {
         if (lPrecision > 0L) {
            iPrecision = (int)lPrecision;
            iLength = iPrecision;
         }
      } else {
         long lLength = (lPrecision + lGiga - 1L) / lGiga;
         iLength = (int)lLength;
         multiplier = Multiplier.G;
      }

      if (iScale < 0) {
         iScale = -1;
      }

      if (iDataType != 1111) {
         PreType pt = PreType.getBySqlType(iDataType);
         this.initialize(pt, iLength, multiplier, iPrecision, iScale, iScale, (WithOrWithoutTimeZone)null, (IntervalQualifier)null);
      }

   }

   public PredefinedType(SqlFactory sf) {
      super(sf);
   }

   private class PdtVisitor extends EnhancedSqlBaseVisitor<PredefinedType> {
      private PdtVisitor() {
      }

      public PredefinedType visitCharType(SqlParser.CharTypeContext ctx) {
         PredefinedType.this.setType(PreType.CHAR);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitVarcharType(SqlParser.VarcharTypeContext ctx) {
         PredefinedType.this.setType(PreType.VARCHAR);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitClobType(SqlParser.ClobTypeContext ctx) {
         PredefinedType.this.setType(PreType.CLOB);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitNcharType(SqlParser.NcharTypeContext ctx) {
         PredefinedType.this.setType(PreType.NCHAR);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitNvarcharType(SqlParser.NvarcharTypeContext ctx) {
         PredefinedType.this.setType(PreType.NVARCHAR);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitNclobType(SqlParser.NclobTypeContext ctx) {
         PredefinedType.this.setType(PreType.NCLOB);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitXmlType(SqlParser.XmlTypeContext ctx) {
         PredefinedType.this.setType(PreType.XML);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitBinaryType(SqlParser.BinaryTypeContext ctx) {
         PredefinedType.this.setType(PreType.BINARY);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitVarbinaryType(SqlParser.VarbinaryTypeContext ctx) {
         PredefinedType.this.setType(PreType.VARBINARY);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitBlobType(SqlParser.BlobTypeContext ctx) {
         PredefinedType.this.setType(PreType.BLOB);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitNumericType(SqlParser.NumericTypeContext ctx) {
         PredefinedType.this.setType(PreType.NUMERIC);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitDecimalType(SqlParser.DecimalTypeContext ctx) {
         PredefinedType.this.setType(PreType.DECIMAL);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitSmallintType(SqlParser.SmallintTypeContext ctx) {
         PredefinedType.this.setType(PreType.SMALLINT);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitIntegerType(SqlParser.IntegerTypeContext ctx) {
         PredefinedType.this.setType(PreType.INTEGER);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitBigintType(SqlParser.BigintTypeContext ctx) {
         PredefinedType.this.setType(PreType.BIGINT);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitFloatType(SqlParser.FloatTypeContext ctx) {
         PredefinedType.this.setType(PreType.FLOAT);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitRealType(SqlParser.RealTypeContext ctx) {
         PredefinedType.this.setType(PreType.REAL);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitDoubleType(SqlParser.DoubleTypeContext ctx) {
         PredefinedType.this.setType(PreType.DOUBLE);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitBooleanType(SqlParser.BooleanTypeContext ctx) {
         PredefinedType.this.setType(PreType.BOOLEAN);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitDatalinkType(SqlParser.DatalinkTypeContext ctx) {
         PredefinedType.this.setType(PreType.DATALINK);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitDateType(SqlParser.DateTypeContext ctx) {
         PredefinedType.this.setType(PreType.DATE);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitTimeType(SqlParser.TimeTypeContext ctx) {
         PredefinedType.this.setType(PreType.TIME);
         PredefinedType.this.setSecondsDecimals(0);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitTimestampType(SqlParser.TimestampTypeContext ctx) {
         PredefinedType.this.setType(PreType.TIMESTAMP);
         PredefinedType.this.setSecondsDecimals(6);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitIntervalType(SqlParser.IntervalTypeContext ctx) {
         PredefinedType.this.setType(PreType.INTERVAL);
         return (PredefinedType)this.visitChildren(ctx);
      }

      public PredefinedType visitLength(SqlParser.LengthContext ctx) {
         PredefinedType.this.setLength(Integer.parseInt(ctx.getText()));
         return PredefinedType.this;
      }

      public PredefinedType visitPrecision(SqlParser.PrecisionContext ctx) {
         PredefinedType.this.setPrecision(Integer.parseInt(ctx.getText()));
         return PredefinedType.this;
      }

      public PredefinedType visitScale(SqlParser.ScaleContext ctx) {
         PredefinedType.this.setScale(Integer.parseInt(ctx.getText()));
         return PredefinedType.this;
      }

      public PredefinedType visitSecondsDecimals(SqlParser.SecondsDecimalsContext ctx) {
         PredefinedType.this.setSecondsDecimals(Integer.parseInt(ctx.getText()));
         return PredefinedType.this;
      }

      public PredefinedType visitIntervalQualifier(SqlParser.IntervalQualifierContext ctx) {
         IntervalQualifier iq = PredefinedType.this.getSqlFactory().newIntervalQualifier();
         iq.parse(ctx);
         PredefinedType.this.setIntervalQualifier(iq);
         return PredefinedType.this;
      }

      public PredefinedType visitMultiplier(SqlParser.MultiplierContext ctx) {
         PredefinedType.this.setMultiplier(this.getMultiplier(ctx));
         return PredefinedType.this;
      }

      public PredefinedType visitWithOrWithoutTimeZone(SqlParser.WithOrWithoutTimeZoneContext ctx) {
         PredefinedType.this.setWithOrWithoutTimeZone(this.getWithOrWithoutTimeZone(ctx));
         return PredefinedType.this;
      }

      // $FF: synthetic method
      PdtVisitor(Object x1) {
         this();
      }
   }
}

package ch.enterag.sqlparser;

import ch.enterag.sqlparser.datatype.enums.IntervalField;
import ch.enterag.sqlparser.expression.enums.BooleanLiteral;
import ch.enterag.utils.BU;
import ch.enterag.utils.logging.IndentLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public abstract class SqlLiterals {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SqlLiterals.class.getName());
   protected static final String sMINUS = "-";
   protected static final String sLEFT_PAREN = "(";
   protected static final String sRIGHT_PAREN = ")";
   protected static final String sHYPHEN = "-";
   protected static final String sSP = " ";
   protected static final String sCOLON = ":";
   protected static final String sPERIOD = ".";
   protected static final String sCOMMA = ",";
   protected static final String sZERO = "0";
   protected static final String sAPOSTROPHE = "'";
   protected static final String sDOUBLE_APOSTROPHE = "''";
   protected static final String sQUOTE = "\"";
   protected static final String sDOUBLE_QUOTE = "\"\"";
   protected static final int iMIN_IDENTIFIER_LENGTH = 1;
   protected static final int iMAX_IDENTIFIER_LENGTH = 128;
   public static final String sDOT = ".";
   public static final String sNULL;
   public static final String sNATIONAL_LITERAL_PREFIX = "N";
   public static final String sBIT_LITERAL_PREFIX = "B";
   public static final String sBYTE_LITERAL_PREFIX = "X";
   public static final String sDATE_LITERAL_PREFIX = "DATE";
   public static final String sTIME_LITERAL_PREFIX = "TIME";
   public static final String sTIMESTAMP_LITERAL_PREFIX = "TIMESTAMP";
   public static final String sINTERVAL_LITERAL_PREFIX = "INTERVAL";
   public static final SimpleDateFormat sdfDATE;
   public static final SimpleDateFormat sdfTIME;
   public static final SimpleDateFormat sdfTIMESTAMP;
   public static final DecimalFormatSymbols dfsSYMBOLS;
   public static DecimalFormat dfAPPROXIMATE;

   public static byte[] serializeObject(Object o) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(o);
         oos.close();
      } catch (IOException var3) {
         _il.exception(var3);
      }

      return baos.toByteArray();
   }

   public static Object deserializeObject(byte[] buf) {
      Object o = null;
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);

      try {
         ObjectInputStream ois = new ObjectInputStream(bais);
         o = ois.readObject();
         ois.close();
      } catch (IOException var4) {
         _il.exception(var4);
      } catch (ClassNotFoundException var5) {
         _il.exception(var5);
      }

      return o;
   }

   public static <T> byte[] serialize(T o) {
      return serializeObject(o);
   }

   public static <T> T deserialize(byte[] buf, Class<T> type) {
      return type.cast(deserializeObject(buf));
   }

   public static boolean isReservedKeyword(String s) {
      boolean bReserved = false;
      K k = K.getByKeyword(s);
      if (k != null) {
         bReserved = k.isReserved();
      }

      return bReserved;
   }

   public static boolean isAlNum(char c) {
      return Character.isLetter(c) || c == '_' || c >= '0' && c <= '9';
   }

   public static boolean isRegular(String sIdentifier) {
      boolean bRegular = false;
      if (!isReservedKeyword(sIdentifier.toUpperCase()) && sIdentifier != null && sIdentifier.length() >= 1 && sIdentifier.length() <= 128) {
         char c = sIdentifier.charAt(0);
         bRegular = Character.isLetter(c);

         for(int i = 1; bRegular && i < sIdentifier.length(); ++i) {
            c = sIdentifier.charAt(i);
            bRegular = isAlNum(c);
         }
      }

      return bRegular;
   }

   protected static boolean isDelimited(String sDelimited) {
      return sDelimited != null && sDelimited.length() >= 2 && sDelimited.startsWith("\"") && sDelimited.endsWith("\"");
   }

   public static String parseIdPrefix(String s) throws ParseException {
      String sIdentifier = null;
      StringBuilder sbIdentifier = new StringBuilder();
      if (s != null && s.length() > 0) {
         int i = 0;
         if (s.charAt(0) == "\"".charAt(0)) {
            sbIdentifier.append("\"");
            boolean bInQuote = true;
            ++i;

            for(; bInQuote && i < s.length(); ++i) {
               if (s.charAt(i) != "\"".charAt(0)) {
                  sbIdentifier.append(s.charAt(i));
               } else if (i < s.length() - 1 && s.charAt(i + 1) == "\"".charAt(0)) {
                  sbIdentifier.append("\"");
                  ++i;
               } else {
                  bInQuote = false;
               }
            }

            sbIdentifier.append("\"");
         } else {
            while(isAlNum(s.charAt(i)) && i < s.length()) {
               sbIdentifier.append(s.charAt(i));
               ++i;
            }
         }

         if (sbIdentifier.length() != 0) {
            sIdentifier = sbIdentifier.toString();
         }

         return sIdentifier;
      } else {
         throw new IllegalArgumentException("String with identifier must have length greater than 0!");
      }
   }

   public static String parseId(String sDelimited) throws ParseException {
      String sIdentifier = null;
      if (sDelimited == null) {
         throw new NullPointerException("Identifier must not be null!");
      } else {
         if (!isDelimited(sDelimited)) {
            sIdentifier = sDelimited.toUpperCase();
            if (!isRegular(sIdentifier)) {
               throw new ParseException("Identifier (" + sIdentifier + ") must be regular (start with letter, continue with letter, underscore or digit, and not equal a reserved keyword)!", 0);
            }
         } else {
            sDelimited = sDelimited.substring(1, sDelimited.length() - 1);
            StringBuilder sbIdentifier = new StringBuilder();
            int i = 0;

            while(true) {
               if (i >= sDelimited.length()) {
                  sIdentifier = sbIdentifier.toString();
                  break;
               }

               sbIdentifier.append(sDelimited.charAt(i));
               if (sDelimited.charAt(i) == "\"".charAt(0)) {
                  if (i >= sDelimited.length() - 1 || sDelimited.charAt(i + 1) != "\"".charAt(0)) {
                     throw new ParseException("Delimited identifier (" + sDelimited + ") contains single quote!", i);
                  }

                  ++i;
               }

               ++i;
            }
         }

         if (sIdentifier.length() >= 1 && sIdentifier.length() <= 128) {
            return sIdentifier;
         } else {
            throw new ParseException("Identifier (" + sIdentifier + ") length must be at least " + 1 + " and at most " + 128 + "!", 0);
         }
      }
   }

   public static String quoteId(String sIdentifier) {
      return "\"" + sIdentifier.replace("\"", "\"\"") + "\"";
   }

   public static String formatId(String sIdentifier) {
      String sDelimited = null;
      if (sIdentifier == null) {
         throw new NullPointerException("Identifier must not be null!");
      } else if (sIdentifier.length() >= 1 && sIdentifier.length() <= 128) {
         if (sIdentifier.equals(sIdentifier.toUpperCase()) && isRegular(sIdentifier)) {
            sDelimited = sIdentifier.toUpperCase();
         } else {
            sDelimited = "\"" + sIdentifier.replace("\"", "\"\"") + "\"";
         }

         return sDelimited;
      } else {
         throw new IllegalArgumentException("Identifier length (" + sIdentifier + ") must be at least " + 1 + " and at most " + 128 + "!");
      }
   }

   public static int getIdentifierEnd(String sQualified, int iStart) throws ParseException {
      int iEnd = 1;
      if (sQualified == null) {
         throw new NullPointerException("Qualified identifier must not be null!");
      } else {
         iEnd = sQualified.length();
         char c = sQualified.charAt(iStart);
         if (c != "\"".charAt(0)) {
            if (!Character.isLetter(c)) {
               throw new ParseException("Unquoted identifier must start with a letter!", 0);
            }

            for(int i = iStart + 1; iEnd == sQualified.length() && i < sQualified.length(); ++i) {
               c = sQualified.charAt(i);
               if (!Character.isLetter(c) && c != '_' && (c < '0' || c > '9')) {
                  iEnd = i;
               }
            }
         } else {
            boolean bQuote = false;

            for(int i = iStart + 1; iEnd == sQualified.length() && i < sQualified.length(); ++i) {
               c = sQualified.charAt(i);
               if (c != "\"".charAt(0)) {
                  if (bQuote) {
                     iEnd = i;
                  }
               } else if (!bQuote) {
                  bQuote = true;
               } else {
                  bQuote = false;
               }
            }
         }

         return iEnd;
      }
   }

   public static String quoteQualifiedSchema(String sCatalogName, String sUnqualifiedSchemaName) {
      String sQuotedSchemaName = quoteId(sUnqualifiedSchemaName);
      if (sCatalogName != null) {
         sQuotedSchemaName = quoteId(sCatalogName) + "." + sQuotedSchemaName;
      }

      return sQuotedSchemaName;
   }

   public static String formatQualifiedSchema(String sCatalogName, String sUnqualifiedSchemaName) {
      String sFormattedSchemaName = formatId(sUnqualifiedSchemaName);
      if (sCatalogName != null) {
         sFormattedSchemaName = formatId(sCatalogName) + "." + sFormattedSchemaName;
      }

      return sFormattedSchemaName;
   }

   public static String quoteQualifiedName(String sCatalogName, String sSchemaName, String sUnqualifiedName) {
      String sQuotedQualifiedName = quoteId(sUnqualifiedName);
      if (sSchemaName != null) {
         sQuotedQualifiedName = quoteQualifiedSchema(sCatalogName, sSchemaName) + "." + sQuotedQualifiedName;
      }

      return sQuotedQualifiedName;
   }

   public static String formatQualifiedName(String sCatalogName, String sSchemaName, String sUnqualifiedName) {
      String sFormattedQualifiedName = formatId(sUnqualifiedName);
      if (sSchemaName != null) {
         sFormattedQualifiedName = formatQualifiedSchema(sCatalogName, sSchemaName) + "." + sFormattedQualifiedName;
      }

      return sFormattedQualifiedName;
   }

   public static String quoteIdentifierCommaList(List<String> list) {
      StringBuilder sbCommaList = new StringBuilder();

      for(int i = 0; i < list.size(); ++i) {
         if (i != 0) {
            sbCommaList.append(", ");
         }

         sbCommaList.append(quoteId((String)list.get(i)));
      }

      return sbCommaList.toString();
   }

   public static String formatIdentifierCommaList(List<String> list) {
      StringBuilder sbCommaList = new StringBuilder();

      for(int i = 0; i < list.size(); ++i) {
         if (i != 0) {
            sbCommaList.append(", ");
         }

         sbCommaList.append(formatId((String)list.get(i)));
      }

      return sbCommaList.toString();
   }

   protected static String cutPrefix(String s, String sPrefix) {
      String sResult = null;
      if (s.toUpperCase().startsWith(sPrefix)) {
         sResult = s.substring(sPrefix.length()).trim();
      }

      return sResult;
   }

   public static String parseStringLiteral(String sQuoted) throws ParseException {
      String sParsed = null;
      if (sQuoted.length() >= 2 && sQuoted.startsWith("'") && sQuoted.endsWith("'")) {
         sQuoted = sQuoted.substring(1, sQuoted.length() - 1);
         StringBuilder sbParsed = new StringBuilder();

         for(int i = 0; i < sQuoted.length(); ++i) {
            sbParsed.append(sQuoted.charAt(i));
            if (sQuoted.substring(i, i + 1).equals("'")) {
               if (i >= sQuoted.length() - 1 || !sQuoted.substring(i + 1, i + 2).equals("'")) {
                  throw new ParseException("Quoted string (" + sQuoted + ") contains single apostrophe!", i);
               }

               ++i;
            }
         }

         sParsed = sbParsed.toString();
         return sParsed;
      } else {
         throw new ParseException("Quoted string must be quoted!", 0);
      }
   }

   public static String formatStringLiteral(String sValue) {
      String sQuoted = sNULL;
      if (sValue != null) {
         sQuoted = "'" + sValue.replaceAll("'", "''") + "'";
      }

      return sQuoted;
   }

   public static String parseNationalStringLiteral(String sNationalString) throws ParseException {
      String sParsed = cutPrefix(sNationalString, "N");
      if (sParsed != null) {
         sParsed = parseStringLiteral(sParsed);
         return sParsed;
      } else {
         throw new ParseException("National character string literal must start with N!", 0);
      }
   }

   public static String formatNationalStringLiteral(String sValue) {
      String sFormatted = sNULL;
      if (sValue != null) {
         sFormatted = "N" + formatStringLiteral(sValue);
      }

      return sFormatted;
   }

   public static String parseBitStringLiteral(String sBitString) throws ParseException {
      String sParsed = cutPrefix(sBitString, "B");
      if (sParsed != null) {
         sParsed = parseStringLiteral(sParsed);

         for(int i = 0; i < sParsed.length(); ++i) {
            if ("01".indexOf(sParsed.charAt(i)) < 0) {
               throw new ParseException("Bit string must consist of 0s and 1!", i);
            }
         }

         return sParsed;
      } else {
         throw new ParseException("Bit character string literal must start with B!", 0);
      }
   }

   public static String formatBitStringLiteral(String sValue) {
      String sFormatted = sNULL;
      if (sValue != null) {
         sFormatted = "B" + formatStringLiteral(sValue);
      }

      return sFormatted;
   }

   public static byte[] parseBytesLiteral(String sByteString) throws ParseException {
      byte[] bufParsed = null;
      String sParsed = cutPrefix(sByteString, "X");
      if (sParsed != null) {
         sParsed = parseStringLiteral(sParsed);
         bufParsed = BU.fromHex(sParsed);
         return bufParsed;
      } else {
         throw new ParseException("Byte character string literal must start with X!", 0);
      }
   }

   public static String formatBytesLiteral(byte[] bufValue) {
      String sFormatted = sNULL;
      if (bufValue != null) {
         sFormatted = "X" + formatStringLiteral(BU.toHex(bufValue));
      }

      return sFormatted;
   }

   public static Date parseDateLiteral(String sDateLiteral) throws ParseException {
      Date dateParsed = null;
      String sParsed = cutPrefix(sDateLiteral, "DATE");
      if (sParsed != null) {
         sParsed = parseStringLiteral(sParsed);

         try {
            dateParsed = new Date(sdfDATE.parse(sParsed).getTime());
            return dateParsed;
         } catch (ParseException var4) {
            throw new ParseException("Date format must conform to yyyy-mm-dd!", var4.getErrorOffset());
         }
      } else {
         throw new ParseException("Date character string literal must start with DATE!", 0);
      }
   }

   public static String formatDateLiteral(Date dateValue) {
      String sFormatted = sNULL;
      if (dateValue != null) {
         sFormatted = "DATE" + formatStringLiteral(sdfDATE.format(dateValue));
      }

      return sFormatted;
   }

   public static Time parseTimeLiteral(String sTimeLiteral) throws ParseException {
      Time timeParsed = null;
      String sParsed = cutPrefix(sTimeLiteral, "TIME");
      if (sParsed == null) {
         throw new ParseException("Time character string literal must start with TIME!", 0);
      } else {
         sParsed = parseStringLiteral(sParsed);
         String sMillis = null;
         int iDecimal = sParsed.lastIndexOf(46);
         if (iDecimal >= 0) {
            sMillis = sParsed.substring(iDecimal + 1);
            if (sMillis.length() > 3) {
               sMillis = sMillis.substring(0, 3);
            }

            while(sMillis.length() < 3) {
               sMillis = sMillis + "0";
            }

            sParsed = sParsed.substring(0, iDecimal);
         }

         try {
            timeParsed = new Time(sdfTIME.parse(sParsed).getTime());
            if (sMillis != null) {
               timeParsed.setTime(timeParsed.getTime() + (long)Integer.parseInt(sMillis));
            }

            return timeParsed;
         } catch (ParseException var6) {
            throw new ParseException("Time format must conform to hh:mm:ss.fff!", var6.getErrorOffset());
         }
      }
   }

   public static String formatTimeLiteral(Time timeValue) {
      String sFormatted = sNULL;
      if (timeValue != null) {
         sFormatted = sdfTIME.format(timeValue);
         long lMillis = timeValue.getTime() % 1000L;
         if (lMillis != 0L) {
            String sMillis;
            for(sMillis = String.valueOf(lMillis); sMillis.length() < 3; sMillis = "0" + sMillis) {
            }

            while(sMillis.endsWith("0")) {
               sMillis = sMillis.substring(0, sMillis.length() - 1);
            }

            sFormatted = sFormatted + "." + sMillis;
         }

         sFormatted = "TIME" + formatStringLiteral(sFormatted);
      }

      return sFormatted;
   }

   public static Timestamp parseTimestampLiteral(String sTimestampLiteral) throws ParseException {
      Timestamp tsParsed = null;
      String sParsed = cutPrefix(sTimestampLiteral, "TIMESTAMP");
      if (sParsed == null) {
         throw new ParseException("Timestamp character string literal must start with TIMESTAMP!", 0);
      } else {
         sParsed = parseStringLiteral(sParsed);
         String sNanos = null;
         int iDecimal = sParsed.lastIndexOf(46);
         if (iDecimal >= 0) {
            sNanos = sParsed.substring(iDecimal + 1);
            if (sNanos.length() > 9) {
               sNanos = sNanos.substring(0, 9);
            }

            while(sNanos.length() < 9) {
               sNanos = sNanos + "0";
            }

            sParsed = sParsed.substring(0, iDecimal);
         }

         try {
            tsParsed = new Timestamp(sdfTIMESTAMP.parse(sParsed).getTime());
            if (sNanos != null) {
               tsParsed.setNanos(Integer.parseInt(sNanos));
            }

            return tsParsed;
         } catch (ParseException var6) {
            throw new ParseException("Timestamp format must conform to yyyy-mm-dd hh-mm-ss.fffffffff!", var6.getErrorOffset());
         }
      }
   }

   public static String formatTimestampLiteral(Timestamp tsValue) {
      String sFormatted = sNULL;
      if (tsValue != null) {
         sFormatted = sdfTIMESTAMP.format(tsValue);
         int iNanos = tsValue.getNanos();
         if (iNanos > 0) {
            String sNanos;
            for(sNanos = String.valueOf(iNanos); sNanos.length() < 9; sNanos = "0" + sNanos) {
            }

            while(sNanos.endsWith("0")) {
               sNanos = sNanos.substring(0, sNanos.length() - 1);
            }

            sFormatted = sFormatted + "." + sNanos;
         }

         sFormatted = "TIMESTAMP" + formatStringLiteral(sFormatted);
      }

      return sFormatted;
   }

   public static Interval parseInterval(String sIntervalLiteral) throws ParseException {
      Interval interval = null;
      String sParsed = cutPrefix(sIntervalLiteral, "INTERVAL");
      if (sParsed != null) {
         int iSign = 1;
         if (sParsed.startsWith("-")) {
            iSign = -1;
            sParsed = sParsed.substring(1).trim();
         }

         int iEndQuote = sParsed.lastIndexOf("'");
         if (sParsed.startsWith("'") && iEndQuote >= 0) {
            String sIntervalQualifier = sParsed.substring(iEndQuote + 1).trim().toUpperCase();
            sParsed = sParsed.substring(1, iEndQuote);
            if (sParsed.startsWith("-")) {
               iSign = -iSign;
               sParsed = sParsed.substring(1).trim();
            }

            String sStart = sIntervalQualifier;
            String sEnd = null;
            int iTo = sIntervalQualifier.indexOf(K.TO.getKeyword());
            if (iTo >= 0) {
               sStart = sIntervalQualifier.substring(0, iTo).trim();
               sEnd = sIntervalQualifier.substring(iTo + K.TO.getKeyword().length()).trim();
            }

            int iLeftParen = sStart.indexOf("(");
            if (iLeftParen >= 0) {
               sStart = sStart.substring(0, iLeftParen);
            }

            IntervalField ifStart = IntervalField.getByKeywords(sStart);
            IntervalField ifEnd = null;
            if (sEnd != null) {
               iLeftParen = sEnd.indexOf("(");
               if (iLeftParen >= 0) {
                  sEnd = sEnd.substring(0, iLeftParen);
               }

               ifEnd = IntervalField.getByKeywords(sEnd);
            }

            if (ifStart == null) {
               throw new ParseException("Interval qualifier must start with a valid start field!", "INTERVAL".length());
            } else {
               String sYears;
               String sHours;
               if (ifStart != IntervalField.YEAR && ifStart != IntervalField.MONTH) {
                  sYears = null;
                  sHours = null;
                  String sMinutes = null;
                  String sSeconds = null;
                  String sDecimals = null;
                  int iDays;
                  int iPeriod;
                  if (ifStart == IntervalField.DAY) {
                     sYears = sParsed;
                     if (ifEnd != null) {
                        iDays = sParsed.indexOf(" ");
                        if (iDays < 0) {
                           throw new ParseException("DAY-TIME interval value have contain a space to separate the days from the hours!", "INTERVAL".length());
                        }

                        sYears = sParsed.substring(0, iDays);
                        sParsed = sParsed.substring(iDays + 1);
                        if (ifEnd == IntervalField.HOUR) {
                           sHours = sParsed;
                        } else {
                           iPeriod = sParsed.indexOf(":");
                           if (iPeriod < 0) {
                              throw new ParseException("DAY-TIME interval value must contain a colon to separate the hours from the minutes!", "INTERVAL".length());
                           }

                           sHours = sParsed.substring(0, iPeriod);
                           sParsed = sParsed.substring(iPeriod + 1);
                           if (ifEnd == IntervalField.MINUTE) {
                              sMinutes = sParsed;
                           } else {
                              iPeriod = sParsed.indexOf(":");
                              if (iPeriod < 0) {
                                 throw new ParseException("DAY-TIME interval value must contain a colon to separate the minutes from the seconds!", "INTERVAL".length());
                              }

                              sMinutes = sParsed.substring(0, iPeriod);
                              sParsed = sParsed.substring(iPeriod + 1);
                              if (ifEnd != IntervalField.SECOND) {
                                 throw new ParseException("Invalid DAY-TIME interval qualifier " + sIntervalQualifier + "!", sIntervalLiteral.length());
                              }

                              sSeconds = sParsed;
                              iPeriod = sParsed.indexOf(".");
                              if (iPeriod >= 0) {
                                 sDecimals = sParsed.substring(iPeriod + 1);
                                 sSeconds = sParsed.substring(0, iPeriod);
                              }
                           }
                        }
                     }
                  } else if (ifStart == IntervalField.HOUR) {
                     sHours = sParsed;
                     if (ifEnd != null) {
                        iDays = sParsed.indexOf(":");
                        if (iDays < 0) {
                           throw new ParseException("DAY-TIME interval value must contain a colon to separate the hours from the minutes!", "INTERVAL".length());
                        }

                        sHours = sParsed.substring(0, iDays);
                        sParsed = sParsed.substring(iDays + 1);
                        if (ifEnd == IntervalField.MINUTE) {
                           sMinutes = sParsed;
                        } else {
                           iDays = sParsed.indexOf(":");
                           if (iDays < 0) {
                              throw new ParseException("DAY-TIME interval value must contain a colon to separate the minutes from the seconds!", "INTERVAL".length());
                           }

                           sMinutes = sParsed.substring(0, iDays);
                           sParsed = sParsed.substring(iDays + 1);
                           if (ifEnd != IntervalField.SECOND) {
                              throw new ParseException("Invalid DAY-TIME interval qualifier " + sIntervalQualifier + "!", sIntervalLiteral.length());
                           }

                           sSeconds = sParsed;
                           iPeriod = sParsed.indexOf(".");
                           if (iPeriod >= 0) {
                              sDecimals = sParsed.substring(iPeriod + 1);
                              sSeconds = sParsed.substring(0, iPeriod);
                           }
                        }
                     }
                  } else if (ifStart == IntervalField.MINUTE) {
                     sMinutes = sParsed;
                     if (ifEnd != null) {
                        iDays = sParsed.indexOf(":");
                        if (iDays < 0) {
                           throw new ParseException("DAY-TIME interval value must contain a colon to separate the minutes from the seconds!", "INTERVAL".length());
                        }

                        sMinutes = sParsed.substring(0, iDays);
                        sParsed = sParsed.substring(iDays + 1);
                        if (ifEnd != IntervalField.SECOND) {
                           throw new ParseException("Invalid DAY-TIME interval qualifier " + sIntervalQualifier + "!", sIntervalLiteral.length());
                        }

                        sSeconds = sParsed;
                        iPeriod = sParsed.indexOf(".");
                        if (iPeriod >= 0) {
                           sDecimals = sParsed.substring(iPeriod + 1);
                           sSeconds = sParsed.substring(0, iPeriod);
                        }
                     }
                  } else if (ifStart == IntervalField.SECOND) {
                     sSeconds = sParsed;
                     iDays = sParsed.indexOf(".");
                     if (iDays >= 0) {
                        sDecimals = sParsed.substring(iDays + 1);
                        sSeconds = sParsed.substring(0, iDays);
                     }
                  }

                  iDays = 0;
                  if (sYears != null) {
                     iDays = Integer.parseInt(sYears);
                  }

                  iPeriod = 0;
                  if (sHours != null) {
                     iPeriod = Integer.parseInt(sHours);
                  }

                  iPeriod = 0;
                  if (sMinutes != null) {
                     iPeriod = Integer.parseInt(sMinutes);
                  }

                  int iSeconds = 0;
                  if (sSeconds != null) {
                     iSeconds = Integer.parseInt(sSeconds);
                  }

                  long lNanos = 0L;
                  if (sDecimals != null) {
                     if (sDecimals.length() > 9) {
                        sDecimals = sDecimals.substring(0, 9);
                     }

                     while(sDecimals.length() < 9) {
                        sDecimals = sDecimals + "0";
                     }

                     lNanos = Long.parseLong(sDecimals);
                  }

                  interval = new Interval(iSign, iDays, iPeriod, iPeriod, iSeconds, lNanos);
               } else {
                  sYears = null;
                  sHours = null;
                  int iHyphen;
                  if (ifStart == IntervalField.YEAR) {
                     sYears = sParsed;
                     if (ifEnd != null) {
                        if (ifEnd != IntervalField.MONTH) {
                           throw new ParseException("Invalid YEAR-MONTH interval qualifier " + sIntervalQualifier + "!", sIntervalLiteral.length());
                        }

                        iHyphen = sParsed.indexOf("-");
                        if (iHyphen < 0) {
                           throw new ParseException("YEAR-MONTH interval value must contain a hyphen!", "INTERVAL".length());
                        }

                        sYears = sParsed.substring(0, iHyphen).trim();
                        sHours = sParsed.substring(iHyphen + 1);
                     }
                  } else if (ifStart == IntervalField.MONTH) {
                     sHours = sParsed;
                     if (ifEnd != null) {
                        throw new ParseException("Invalid MONTH interval qualifier " + sIntervalQualifier + "!", sIntervalLiteral.length());
                     }
                  }

                  iHyphen = 0;
                  if (sYears != null) {
                     iHyphen = Integer.parseInt(sYears);
                  }

                  int iMonths = 0;
                  if (sHours != null) {
                     iMonths = Integer.parseInt(sHours);
                  }

                  interval = new Interval(iSign, iHyphen, iMonths);
               }

               return interval;
            }
         } else {
            throw new ParseException("Interval literal must contain quoted value", "INTERVAL".length());
         }
      } else {
         throw new ParseException("Interval literal must start with INTERVAL!", 0);
      }
   }

   public static String formatIntervalLiteral(Interval ivValue) {
      String sFormatted = sNULL;
      if (ivValue != null) {
         IntervalField ifStart = null;
         IntervalField ifEnd = null;
         String sValue = null;
         int iPrecision = -1;
         int iSecondsPrecision = -1;
         if (ivValue.getYears() != 0) {
            ifStart = IntervalField.YEAR;
            sValue = String.valueOf(ivValue.getYears());
            if (sValue.length() > 2) {
               iPrecision = sValue.length();
            }
         }

         if (ivValue.getMonths() != 0) {
            if (ifStart == null) {
               ifStart = IntervalField.MONTH;
               sValue = String.valueOf(ivValue.getMonths());
               if (sValue.length() > 2) {
                  iPrecision = sValue.length();
               }
            } else {
               ifEnd = IntervalField.MONTH;
               sValue = sValue + "-" + ivValue.getMonths();
            }
         }

         if (ivValue.getDays() != 0) {
            ifStart = IntervalField.DAY;
            sValue = String.valueOf(ivValue.getDays());
            if (sValue.length() > 2) {
               iPrecision = sValue.length();
            }
         }

         if (ivValue.getHours() != 0) {
            if (ifStart == null) {
               ifStart = IntervalField.HOUR;
               sValue = String.valueOf(ivValue.getHours());
               if (sValue.length() > 2) {
                  iPrecision = sValue.length();
               }
            } else {
               ifEnd = IntervalField.HOUR;
               sValue = sValue + " " + ivValue.getHours();
            }
         }

         if (ivValue.getMinutes() != 0) {
            if (ifStart == null) {
               ifStart = IntervalField.MINUTE;
               sValue = String.valueOf(ivValue.getMinutes());
               if (sValue.length() > 2) {
                  iPrecision = sValue.length();
               }
            } else {
               ifEnd = IntervalField.MINUTE;
               sValue = sValue + ":" + ivValue.getMinutes();
            }
         }

         if (ivValue.getSeconds() != 0 || ivValue.getNanoSeconds() != 0L) {
            if (ifStart == null) {
               ifStart = IntervalField.SECOND;
               sValue = String.valueOf(ivValue.getSeconds());
               if (sValue.length() > 2) {
                  iPrecision = sValue.length();
               }
            } else {
               ifEnd = IntervalField.SECOND;
               sValue = sValue + ":" + ivValue.getSeconds();
            }

            if (ivValue.getNanoSeconds() != 0L) {
               String sNanos;
               for(sNanos = String.valueOf(ivValue.getNanoSeconds()); sNanos.length() < 9; sNanos = "0" + sNanos) {
               }

               while(sNanos.endsWith("0")) {
                  sNanos = sNanos.substring(0, sNanos.length() - 1);
               }

               sValue = sValue + "." + sNanos;
               if (sNanos.length() > 6) {
                  iSecondsPrecision = sNanos.length();
               }
            }
         }

         sFormatted = "INTERVAL ";
         if (ivValue.getSign() < 0) {
            sValue = "- " + sValue;
         }

         sFormatted = sFormatted + formatStringLiteral(sValue) + " " + ifStart.getKeywords();
         if (iPrecision >= 0 || ifStart == IntervalField.SECOND && iSecondsPrecision >= 0) {
            if (iPrecision < 0) {
               iPrecision = 2;
            }

            sFormatted = sFormatted + "(" + iPrecision;
            if (ifStart == IntervalField.SECOND && iSecondsPrecision >= 0) {
               sFormatted = sFormatted + "," + " " + iSecondsPrecision;
            }

            sFormatted = sFormatted + ")";
         }

         if (ifEnd != null) {
            sFormatted = sFormatted + " " + K.TO.getKeyword() + " " + ifEnd.getKeywords();
         }

         if (ifEnd == IntervalField.SECOND && iSecondsPrecision >= 0) {
            sFormatted = sFormatted + "(" + iSecondsPrecision + ")";
         }
      }

      return sFormatted;
   }

   public static BooleanLiteral parseBooleanLiteral(String sBooleanLiteral) throws ParseException {
      BooleanLiteral blParsed = null;
      sBooleanLiteral = sBooleanLiteral.toUpperCase();
      if (sBooleanLiteral.equals(BooleanLiteral.TRUE.getKeywords())) {
         blParsed = BooleanLiteral.TRUE;
      } else if (sBooleanLiteral.equals(BooleanLiteral.FALSE.getKeywords())) {
         blParsed = BooleanLiteral.FALSE;
      } else {
         if (!sBooleanLiteral.equals(BooleanLiteral.UNKNOWN.getKeywords())) {
            throw new ParseException("Boolean literals must be " + BooleanLiteral.TRUE.getKeywords() + ", " + BooleanLiteral.FALSE.getKeywords() + ", or " + BooleanLiteral.UNKNOWN.getKeywords() + "!", 0);
         }

         blParsed = BooleanLiteral.UNKNOWN;
      }

      return blParsed;
   }

   public static String formatBooleanLiteral(BooleanLiteral blValue) {
      String sFormatted = blValue.getKeywords();
      return sFormatted;
   }

   public static Double parseApproximateLiteral(String sApproximateLiteral) throws ParseException {
      Double dParsed = dfAPPROXIMATE.parse(sApproximateLiteral).doubleValue();
      return dParsed;
   }

   public static String formatApproximateLiteral(Double dValue) {
      String sFormatted = dfAPPROXIMATE.format(dValue);
      return sFormatted;
   }

   public static BigDecimal parseExactLiteral(String sExactLiteral) throws ParseException {
      BigDecimal bd = null;

      try {
         bd = new BigDecimal(sExactLiteral);
         return bd;
      } catch (NumberFormatException var3) {
         throw new ParseException("Invalid exact literal (" + var3.getMessage() + ")!", 0);
      }
   }

   public static String formatExactLiteral(BigDecimal bd) {
      String sFormatted = bd.toPlainString();
      return sFormatted;
   }

   static {
      sNULL = K.NULL.getKeyword();
      sdfDATE = new SimpleDateFormat("yyyy-MM-dd");
      sdfTIME = new SimpleDateFormat("HH:mm:ss");
      sdfTIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      dfsSYMBOLS = new DecimalFormatSymbols();
      dfAPPROXIMATE = null;
      dfsSYMBOLS.setDecimalSeparator('.');
      dfAPPROXIMATE = new DecimalFormat("0.###############E0", dfsSYMBOLS);
   }
}

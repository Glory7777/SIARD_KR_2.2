package com.tmax.tibero.jdbc.util;

import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class TbDTFormatter {
  public static String format(TbDTFormat paramTbDTFormat, TbDate paramTbDate) {
    return (paramTbDate == null) ? null : ((paramTbDTFormat == null || paramTbDTFormat.getElements() == null) ? paramTbDate.toString() : formatInternal(paramTbDTFormat, paramTbDate.getYear(), paramTbDate.getMonth(), paramTbDate.getDayOfMonth(), paramTbDate.getHourOfDay(), paramTbDate.getMinutes(), paramTbDate.getSeconds(), 0));
  }
  
  public static String format(TbDTFormat paramTbDTFormat, TbTimestamp paramTbTimestamp) {
    return (paramTbTimestamp == null) ? null : ((paramTbDTFormat == null || paramTbDTFormat.getElements() == null) ? paramTbTimestamp.toString() : formatInternal(paramTbDTFormat, paramTbTimestamp.getYear(), paramTbTimestamp.getMonth(), paramTbTimestamp.getDayOfMonth(), paramTbTimestamp.getHourOfDay(), paramTbTimestamp.getMinutes(), paramTbTimestamp.getSeconds(), paramTbTimestamp.getNanos()));
  }
  
  public static String format(TbDTFormat paramTbDTFormat, Date paramDate) {
    return (paramDate == null) ? null : ((paramTbDTFormat == null || paramTbDTFormat.getElements() == null) ? paramDate.toString() : null);
  }
  
  public static String format(TbDTFormat paramTbDTFormat, Timestamp paramTimestamp) {
    return (paramTimestamp == null) ? null : ((paramTbDTFormat == null || paramTbDTFormat.getElements() == null) ? paramTimestamp.toString() : null);
  }
  
  private static String formatInternal(TbDTFormat paramTbDTFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    Iterator<TbDTFormatElement> iterator = paramTbDTFormat.getElements().iterator();
    StringBuilder stringBuilder = new StringBuilder(paramTbDTFormat.getElements().size() * 3);
    boolean bool = !paramTbDTFormat.getTrimEnabled() ? true : false;
    Calendar calendar = null;
    Locale locale = Locale.getDefault();
    if (paramTbDTFormat.hasField(0) || paramTbDTFormat.hasField(7) || paramTbDTFormat.isFieldWithAdditionalOption(4) || paramTbDTFormat.isFieldWithAdditionalOption(6)) {
      calendar = Calendar.getInstance(new Locale("GMT+0"));
      calendar.clear();
      calendar.set(paramInt1, paramInt2 - 1, paramInt3, paramInt4, paramInt5, paramInt6);
      calendar.getTime();
    } 
    while (iterator.hasNext()) {
      int i;
      int j;
      int k;
      TbDTFormatElement tbDTFormatElement = iterator.next();
      switch (tbDTFormatElement.getType()) {
        case 24:
          stringBuilder.append(calendar.getDisplayName(0, 2, locale));
          continue;
        case 25:
          i = Math.abs(paramInt1) / 100;
          if (i % 100 > 0)
            i++; 
          stringBuilder.append(i);
          continue;
        case 46:
          j = Math.abs(paramInt1) / 100;
          if (j % 100 > 0)
            if (j > 0) {
              j++;
            } else {
              j--;
            }  
          stringBuilder.append(j);
          continue;
        case 42:
        case 55:
        case 56:
        case 57:
          appendNumber(stringBuilder, paramInt1, tbDTFormatElement.getEtcValue(), bool, false);
          continue;
        case 47:
        case 48:
          appendNumber(stringBuilder, paramInt1, 4, bool, true);
          continue;
        case 41:
          stringBuilder.append((paramInt2 + 2) / 3);
          continue;
        case 38:
        case 39:
          appendNumber(stringBuilder, paramInt2, 2, bool, false);
          continue;
        case 40:
          stringBuilder.append(calendar.getDisplayName(2, 2, locale));
          continue;
        case 37:
          stringBuilder.append(calendar.getDisplayName(2, 1, locale));
          continue;
        case 26:
          appendNumber(stringBuilder, paramInt3, 2, bool, false);
          continue;
        case 27:
          stringBuilder.append(calendar.get(6));
          continue;
        case 29:
          stringBuilder.append(calendar.get(7));
          continue;
        case 30:
          stringBuilder.append(calendar.getDisplayName(7, 2, locale));
          continue;
        case 28:
          stringBuilder.append(calendar.getDisplayName(2, 1, locale));
          continue;
        case 23:
          stringBuilder.append(calendar.getDisplayName(9, 2, locale));
          continue;
        case 34:
          appendNumber(stringBuilder, (paramInt4 > 12) ? (paramInt4 - 12) : paramInt4, 2, bool, false);
          continue;
        case 35:
          appendNumber(stringBuilder, paramInt4, 2, bool, false);
          continue;
        case 36:
          appendNumber(stringBuilder, paramInt5, 2, bool, false);
          continue;
        case 45:
          appendNumber(stringBuilder, paramInt6, 2, bool, false);
          continue;
        case 44:
          appendNumber(stringBuilder, paramInt4 * 3600 + paramInt5 * 60 + paramInt6, 5, bool, false);
          continue;
        case 33:
          appendNumber(stringBuilder, paramInt7, 9, true, false);
          k = tbDTFormatElement.getEtcValue();
          if (k == 0)
            k = 9; 
          stringBuilder.setLength(stringBuilder.length() - 9 - k);
          continue;
        case 43:
          stringBuilder.append((new DecimalFormatSymbols(locale)).getDecimalSeparator());
          continue;
      } 
      stringBuilder.append(tbDTFormatElement.getText());
    } 
    return stringBuilder.toString();
  }
  
  private static void appendNumber(StringBuilder paramStringBuilder, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramInt1 < 0 && paramBoolean2) {
      paramStringBuilder.append(paramInt1);
    } else {
      String str = Integer.toString(Math.abs(paramInt1));
      int i = str.length();
      char[] arrayOfChar = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
      if (paramInt2 > i)
        paramStringBuilder.insert(paramStringBuilder.length(), arrayOfChar, 0, paramInt2 - i); 
      if (paramInt2 < i) {
        paramStringBuilder.append(str.substring(i - paramInt2));
      } else {
        paramStringBuilder.append(str);
      } 
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbDTFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
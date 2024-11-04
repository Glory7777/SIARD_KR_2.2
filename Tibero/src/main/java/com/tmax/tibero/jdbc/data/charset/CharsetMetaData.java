package com.tmax.tibero.jdbc.data.charset;

import java.util.HashMap;
import java.util.Locale;

public class CharsetMetaData {
  private static final HashMap<String, String> language = new HashMap<String, String>(58, 1.0F);
  
  public static String getNLSLanguage(Locale paramLocale) {
    String str = null;
    str = language.get(paramLocale.getLanguage() + "_" + paramLocale.getCountry());
    if (str == null)
      str = language.get(paramLocale.getLanguage()); 
    return str;
  }
  
  static {
    language.put("", "AMERICAN");
    language.put("ar_EG", "EGYPTIAN");
    language.put("ar", "ARABIC");
    language.put("as", "ASSAMESE");
    language.put("bg", "BULGARIAN");
    language.put("bn", "BANGLA");
    language.put("ca", "CATALAN");
    language.put("cs", "CZECH");
    language.put("da", "DANISH");
    language.put("de", "GERMAN");
    language.put("el", "GREEK");
    language.put("en", "AMERICAN");
    language.put("es_ES", "SPANISH");
    language.put("es_MX", "MEXICAN SPANISH");
    language.put("es", "LATIN AMERICAN SPANISH");
    language.put("et", "ESTONIAN");
    language.put("fi", "FINNISH");
    language.put("fr_CA", "CANADIAN FRENCH");
    language.put("fr", "FRENCH");
    language.put("gu", "GUJARATI");
    language.put("he", "HEBREW");
    language.put("hi", "HINDI");
    language.put("hr", "CROATIAN");
    language.put("hu", "HUNGARIAN");
    language.put("id", "INDONESIAN");
    language.put("in", "INDONESIAN");
    language.put("is", "ICELANDIC");
    language.put("it", "ITALIAN");
    language.put("iw", "HEBREW");
    language.put("ja", "JAPANESE");
    language.put("kn", "KANNADA");
    language.put("ko", "KOREAN");
    language.put("lt", "LITHUANIAN");
    language.put("lv", "LATVIAN");
    language.put("mk", "MACEDONIAN");
    language.put("ml", "MALAYALAM");
    language.put("mr", "MARATHI");
    language.put("ms", "MALAY");
    language.put("nl", "DUTCH");
    language.put("no", "NORWEGIAN");
    language.put("or", "ORIYA");
    language.put("pa", "PUNJABI");
    language.put("pl", "POLISH");
    language.put("pt_BR", "BRAZILIAN PORTUGUESE");
    language.put("pt", "PORTUGUESE");
    language.put("ro", "ROMANIAN");
    language.put("ru", "RUSSIAN");
    language.put("sk", "SLOVAK");
    language.put("sq", "ALBANIAN");
    language.put("sl", "SLOVENIAN");
    language.put("sv", "SWEDISH");
    language.put("ta", "TAMIL");
    language.put("te", "TELUGU");
    language.put("th", "THAI");
    language.put("tr", "TURKISH");
    language.put("uk", "UKRAINIAN");
    language.put("vi", "VIETNAMESE");
    language.put("zh_HK", "TRADITIONAL CHINESE");
    language.put("zh_TW", "TRADITIONAL CHINESE");
    language.put("zh", "SIMPLIFIED CHINESE");
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\CharsetMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
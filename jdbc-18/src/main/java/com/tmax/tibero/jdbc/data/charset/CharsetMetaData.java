package com.tmax.tibero.jdbc.data.charset;

import java.util.HashMap;
import java.util.Locale;

public class CharsetMetaData {
    private static final HashMap<String, String> language = new HashMap<>(58, 1.0F);

    private static final HashMap<String, String> territory = new HashMap<>(58, 1.0F);

    public static String getNLSLanguage(Locale paramLocale) {
        String s = null;
        s = getLanguage(System.getProperty("tibero.language"), System.getProperty("tibero.country"));
        if (s != null) return s;
        s = getLanguage(System.getenv("TIBERO_LANGUAGE"), System.getenv("TIBERO_COUNTRY"));
        return (s != null) ? s : getLanguage(paramLocale.getLanguage(), paramLocale.getCountry());
    }

    public static String getNLSTerritory(Locale paramLocale) {
        String s = null;
        s = getTerritory(System.getProperty("tibero.language"), System.getProperty("tibero.country"));
        if (s != null) return s;
        s = getTerritory(System.getenv("TIBERO_LANGUAGE"), System.getenv("TIBERO_COUNTRY"));
        return (s != null) ? s : getTerritory(paramLocale.getLanguage(), paramLocale.getCountry());
    }

    public static String getLanguage(String paramString1, String paramString2) {
        String str = null;
        paramString1 = (paramString1 != null) ? paramString1.toLowerCase() : paramString1;
        paramString2 = (paramString2 != null) ? paramString2.toUpperCase() : paramString2;
        if (paramString1 != null) {
            str = language.get(paramString1 + "_" + paramString2);
            if (str == null)
                str = language.get(paramString1 + "_");
        }
        return str;
    }

    public static String getTerritory(String paramString1, String paramString2) {
        String str = null;
        paramString1 = (paramString1 != null) ? paramString1.toLowerCase() : paramString1;
        paramString2 = (paramString2 != null) ? paramString2.toUpperCase() : paramString2;
        if (paramString2 != null) {
            str = territory.get(paramString1 + "_" + paramString2);
            if (str == null)
                str = territory.get("_" + paramString2);
            if (str == null)
                str = territory.get(paramString1 + "_");
        }
        return str;
    }

    static {
        territory.put("", "KOREA");
        territory.put("_KR", "KOREA");
        territory.put("ko_", "KOREA");
        territory.put("_US", "AMERICA");
        territory.put("en_", "AMERICA");
        territory.put("_JP", "JAPAN");
        territory.put("ja_", "JAPAN");
        territory.put("_CN", "CHINA");
        territory.put("_HK", "HONG KONG");
        territory.put("_TW", "TAIWAN");
        territory.put("zh_", "CHINA");
        territory.put("_AL", "ALBANIA");
        territory.put("sq_", "ALBANIA");
        territory.put("_DZ", "ALGERIA");
        territory.put("_BH", "BAHRAIN");
        territory.put("_EG", "EGYPT");
        territory.put("_IQ", "IRAQ");
        territory.put("_JO", "JORDAN");
        territory.put("_KW", "KUWAIT");
        territory.put("_LB", "LEBANON");
        territory.put("_LY", "LIBYA");
        territory.put("_MA", "MOROCCO");
        territory.put("_OM", "OMAN");
        territory.put("_QA", "QATAR");
        territory.put("_SA", "SAUDI ARABIA");
        territory.put("_SD", "SUDAN");
        territory.put("_SY", "SYRIA");
        territory.put("_TN", "TUNISIA");
        territory.put("_AE", "UNITED ARAB EMIRATES");
        territory.put("_YE", "YEMEN");
        territory.put("ar_", "SAUDI ARABIA");
        territory.put("_BY", "BELARUS");
        territory.put("bg_", "BULGARIA");
        territory.put("_BG", "BULGARIA");
        territory.put("ca_ES", "CATALONIA");
        territory.put("_ES", "SPAIN");
        territory.put("ca_", "CATALONIA");
        territory.put("_SG", "SINGAPORE");
        territory.put("_HR", "CROATIA");
        territory.put("hr_", "CROATIA");
        territory.put("_CZ", "CZECH REPUBLIC");
        territory.put("cs_", "CZECH REPUBLIC");
        territory.put("_DK", "DENMARK");
        territory.put("da_", "DENMARK");
        territory.put("_BE", "BELGIUM");
        territory.put("_NL", "THE NETHERLANDS");
        territory.put("_AU", "AUSTRALIA");
        territory.put("_CA", "CANADA");
        territory.put("_IN", "INDIA");
        territory.put("_IE", "IRELAND");
        territory.put("_MT", "MALTA");
        territory.put("_NZ", "NEW ZEALAND");
        territory.put("_PH", "PHILIPPINES");
        territory.put("_ZA", "SOUTH AFRICA");
        territory.put("_GB", "UNITED KINGDOM");
        territory.put("_EE", "ESTONIA");
        territory.put("et_", "ESTONIA");
        territory.put("_FI", "FINLAND");
        territory.put("fi_", "FINLAND");
        territory.put("_FR", "FRANCE");
        territory.put("_LU", "LUXEMBOURG");
        territory.put("_CH", "SWITZERLAND");
        territory.put("fr_", "FRANCE");
        territory.put("_AT", "AUSTRIA");
        territory.put("_DE", "GERMANY");
        territory.put("de_", "GERMANY");
        territory.put("_CY", "CYPRUS");
        territory.put("_GR", "GREECE");
        territory.put("el_", "GREECE");
        territory.put("_IL", "ISRAEL");
        territory.put("he_", "ISRAEL");
        territory.put("_IN", "INDIA");
        territory.put("as_", "INDIA");
        territory.put("gu_", "INDIA");
        territory.put("hi_", "INDIA");
        territory.put("kn_", "INDIA");
        territory.put("mr_", "INDIA");
        territory.put("or_", "INDIA");
        territory.put("pa_", "INDIA");
        territory.put("ta_", "INDIA");
        territory.put("te_", "INDIA");
        territory.put("_HU", "HUNGARY");
        territory.put("hu_", "HUNGARY");
        territory.put("_IS", "ICELAND");
        territory.put("is_", "ICELAND");
        territory.put("_ID", "INDONESIA");
        territory.put("id_", "INDONESIA");
        territory.put("in_", "INDONESIA");
        territory.put("_IE", "IRELAND");
        territory.put("_IT", "ITALY");
        territory.put("_LV", "LATVIA");
        territory.put("lv_", "LATVIA");
        territory.put("_LT", "LITHUANIA");
        territory.put("lt_", "LITHUANIA");
        territory.put("_MK", "FYR MACEDONIA");
        territory.put("mk_", "FYR MACEDONIA");
        territory.put("_MY", "MALAYSIA");
        territory.put("ms_", "MALAYSIA");
        territory.put("_MT", "MALTA");
        territory.put("_NO", "NORWAY");
        territory.put("no_", "NORWAY");
        territory.put("_PL", "POLAND");
        territory.put("pl_", "POLAND");
        territory.put("_BR", "BRAZIL");
        territory.put("_PT", "PORTUGAL");
        territory.put("pt_", "PORTUGAL");
        territory.put("_RO", "ROMANIA");
        territory.put("ro_", "ROMANIA");
        territory.put("_RU", "RUSSIA");
        territory.put("ru_", "CIS");
        territory.put("_BA", "BOSNIA AND HERZEGOVINA");
        territory.put("_ME", "MONTENEGRO");
        territory.put("_RS", "SERBIA");
        territory.put("sr_", "SERBIA AND MONTENEGRO");
        territory.put("_SK", "SLOVAKIA");
        territory.put("sk_", "SLOVAKIA");
        territory.put("_SI", "SLOVENIA");
        territory.put("sl_", "SLOVENIA");
        territory.put("_AR", "ARGENTINA");
        territory.put("_BO", "BOLIVIA");
        territory.put("_CL", "CHILE");
        territory.put("_CO", "COLOMBIA");
        territory.put("_CR", "COSTA RICA");
        territory.put("_EC", "ECUADOR");
        territory.put("_SV", "EL SALVADOR");
        territory.put("_GT", "GUATEMALA");
        territory.put("_HN", "HONDURAS");
        territory.put("_MX", "MEXICO");
        territory.put("_PA", "PANAMA");
        territory.put("_PY", "PARAGUAY");
        territory.put("_PE", "PERU");
        territory.put("_PR", "PUERTO RICO");
        territory.put("_UY", "URUGUAY");
        territory.put("_VE", "VENEZUELA");
        territory.put("_SE", "SWEDEN");
        territory.put("sv_", "SWEDEN");
        territory.put("_TH", "THAILAND");
        territory.put("th_", "THAILAND");
        territory.put("_TR", "TURKEY");
        territory.put("tr_", "TURKEY");
        territory.put("_UA", "UKRAINE");
        territory.put("uk_", "UKRAINE");
        territory.put("_VN", "VIETNAM");
        territory.put("vi_", "VIETNAM");
        territory.put("bn_", "BANGLA");
    }

    static {
        language.put("", "KOREAN");
        language.put("ko_", "KOREAN");
        language.put("en_", "AMERICAN");
        language.put("ja_", "JAPANESE");
        language.put("zh_HK", "TRADITIONAL CHINESE");
        language.put("zh_TW", "TRADITIONAL CHINESE");
        language.put("zh_CN", "SIMPLIFIED CHINESE");
        language.put("zh_", "SIMPLIFIED CHINESE");
        language.put("sq_", "ALBANIAN");
        language.put("ar_", "ARABIC");
        language.put("as_", "ASSAMESE");
        language.put("ar_EG", "EGYPTIAN");
        language.put("bn_", "BANGLA");
        language.put("be_", "BELARUSIAN");
        language.put("bg_", "BULGARIAN");
        language.put("ca_", "CATALAN");
        language.put("hr_", "CROATIAN");
        language.put("cs_", "CZECH");
        language.put("da_", "DANISH");
        language.put("nl_", "DUTCH");
        language.put("et_", "ESTONIAN");
        language.put("fi_", "FINNISH");
        language.put("fr_CA", "CANADIAN FRENCH");
        language.put("fr_", "FRENCH");
        language.put("de_", "GERMAN");
        language.put("el_", "GREEK");
        language.put("gu_", "GUJARATI");
        language.put("he_", "HEBREW");
        language.put("iw_", "HEBREW");
        language.put("hi_", "HINDI");
        language.put("hu_", "HUNGARIAN");
        language.put("is_", "ICELANDIC");
        language.put("id_", "INDONESIAN");
        language.put("in_", "INDONESIAN");
        language.put("ga_", "IRISH");
        language.put("it_", "ITALIAN");
        language.put("kn_", "KANNADA");
        language.put("lv_", "LATVIAN");
        language.put("lt_", "LITHUANIAN");
        language.put("mk_", "MACEDONIAN");
        language.put("ms_", "MALAY");
        language.put("mt_", "MALTESE");
        language.put("mr_", "MARATHI");
        language.put("no_", "NORWEGIAN");
        language.put("nb_", "NORWEGIAN");
        language.put("or_", "ORIYA");
        language.put("pl_", "POLISH");
        language.put("pt_BR", "BRAZILIAN PORTUGUESE");
        language.put("pt_", "PORTUGUESE");
        language.put("pa_", "PUNJABI");
        language.put("ro_", "ROMANIAN");
        language.put("ru_", "RUSSIAN");
        language.put("sr_", "CYRILLIC SERBIAN");
        language.put("sk_", "SLOVAK");
        language.put("sl_", "SLOVENIAN");
        language.put("es_MX", "MEXICAN SPANISH");
        language.put("es_ES", "SPANISH");
        language.put("es_", "LATIN AMERICAN SPANISH");
        language.put("sv_", "SWEDISH");
        language.put("ta_", "TAMIL");
        language.put("te_", "TELUGU");
        language.put("th_", "THAI");
        language.put("tr_", "TURKISH");
        language.put("uk_", "UKRAINIAN");
        language.put("vi_", "VIETNAMESE");
    }
}
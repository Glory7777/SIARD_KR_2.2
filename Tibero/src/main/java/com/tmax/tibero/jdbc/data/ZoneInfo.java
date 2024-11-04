package com.tmax.tibero.jdbc.data;

import java.util.HashMap;
import java.util.TimeZone;

public class ZoneInfo {
  private final Integer id;
  
  private final String name;
  
  static HashMap zoneTabs = new HashMap<Object, Object>();
  
  public static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");
  
  public static final ZoneInfo TZ_ID_AFRICA_ABIDJAN = new ZoneInfo(new Integer(0), "Africa/Abidjan");
  
  public static final ZoneInfo TZ_ID_AFRICA_ACCRA = new ZoneInfo(new Integer(1), "Africa/Accra");
  
  public static final ZoneInfo TZ_ID_AFRICA_ADDIS_ABABA = new ZoneInfo(new Integer(2), "Africa/Addis_Ababa");
  
  public static final ZoneInfo TZ_ID_AFRICA_ALGIERS = new ZoneInfo(new Integer(3), "Africa/Algiers");
  
  public static final ZoneInfo TZ_ID_AFRICA_ASMARA = new ZoneInfo(new Integer(4), "Africa/Asmara");
  
  public static final ZoneInfo TZ_ID_AFRICA_ASMERA = new ZoneInfo(new Integer(5), "Africa/Asmera");
  
  public static final ZoneInfo TZ_ID_AFRICA_BAMAKO = new ZoneInfo(new Integer(6), "Africa/Bamako");
  
  public static final ZoneInfo TZ_ID_AFRICA_BANGUI = new ZoneInfo(new Integer(7), "Africa/Bangui");
  
  public static final ZoneInfo TZ_ID_AFRICA_BANJUL = new ZoneInfo(new Integer(8), "Africa/Banjul");
  
  public static final ZoneInfo TZ_ID_AFRICA_BISSAU = new ZoneInfo(new Integer(9), "Africa/Bissau");
  
  public static final ZoneInfo TZ_ID_AFRICA_BLANTYRE = new ZoneInfo(new Integer(10), "Africa/Blantyre");
  
  public static final ZoneInfo TZ_ID_AFRICA_BRAZZAVILLE = new ZoneInfo(new Integer(11), "Africa/Brazzaville");
  
  public static final ZoneInfo TZ_ID_AFRICA_BUJUMBURA = new ZoneInfo(new Integer(12), "Africa/Bujumbura");
  
  public static final ZoneInfo TZ_ID_AFRICA_CAIRO = new ZoneInfo(new Integer(13), "Africa/Cairo");
  
  public static final ZoneInfo TZ_ID_AFRICA_CASABLANCA = new ZoneInfo(new Integer(14), "Africa/Casablanca");
  
  public static final ZoneInfo TZ_ID_AFRICA_CEUTA = new ZoneInfo(new Integer(15), "Africa/Ceuta");
  
  public static final ZoneInfo TZ_ID_AFRICA_CONAKRY = new ZoneInfo(new Integer(16), "Africa/Conakry");
  
  public static final ZoneInfo TZ_ID_AFRICA_DAKAR = new ZoneInfo(new Integer(17), "Africa/Dakar");
  
  public static final ZoneInfo TZ_ID_AFRICA_DAR_ES_SALAAM = new ZoneInfo(new Integer(18), "Africa/Dar_es_Salaam");
  
  public static final ZoneInfo TZ_ID_AFRICA_DJIBOUTI = new ZoneInfo(new Integer(19), "Africa/Djibouti");
  
  public static final ZoneInfo TZ_ID_AFRICA_DOUALA = new ZoneInfo(new Integer(20), "Africa/Douala");
  
  public static final ZoneInfo TZ_ID_AFRICA_EL_AAIUN = new ZoneInfo(new Integer(21), "Africa/El_Aaiun");
  
  public static final ZoneInfo TZ_ID_AFRICA_FREETOWN = new ZoneInfo(new Integer(22), "Africa/Freetown");
  
  public static final ZoneInfo TZ_ID_AFRICA_GABORONE = new ZoneInfo(new Integer(23), "Africa/Gaborone");
  
  public static final ZoneInfo TZ_ID_AFRICA_HARARE = new ZoneInfo(new Integer(24), "Africa/Harare");
  
  public static final ZoneInfo TZ_ID_AFRICA_JOHANNESBURG = new ZoneInfo(new Integer(25), "Africa/Johannesburg");
  
  public static final ZoneInfo TZ_ID_AFRICA_JUBA = new ZoneInfo(new Integer(26), "Africa/Juba");
  
  public static final ZoneInfo TZ_ID_AFRICA_KAMPALA = new ZoneInfo(new Integer(27), "Africa/Kampala");
  
  public static final ZoneInfo TZ_ID_AFRICA_KHARTOUM = new ZoneInfo(new Integer(28), "Africa/Khartoum");
  
  public static final ZoneInfo TZ_ID_AFRICA_KIGALI = new ZoneInfo(new Integer(29), "Africa/Kigali");
  
  public static final ZoneInfo TZ_ID_AFRICA_KINSHASA = new ZoneInfo(new Integer(30), "Africa/Kinshasa");
  
  public static final ZoneInfo TZ_ID_AFRICA_LAGOS = new ZoneInfo(new Integer(31), "Africa/Lagos");
  
  public static final ZoneInfo TZ_ID_AFRICA_LIBREVILLE = new ZoneInfo(new Integer(32), "Africa/Libreville");
  
  public static final ZoneInfo TZ_ID_AFRICA_LOME = new ZoneInfo(new Integer(33), "Africa/Lome");
  
  public static final ZoneInfo TZ_ID_AFRICA_LUANDA = new ZoneInfo(new Integer(34), "Africa/Luanda");
  
  public static final ZoneInfo TZ_ID_AFRICA_LUBUMBASHI = new ZoneInfo(new Integer(35), "Africa/Lubumbashi");
  
  public static final ZoneInfo TZ_ID_AFRICA_LUSAKA = new ZoneInfo(new Integer(36), "Africa/Lusaka");
  
  public static final ZoneInfo TZ_ID_AFRICA_MALABO = new ZoneInfo(new Integer(37), "Africa/Malabo");
  
  public static final ZoneInfo TZ_ID_AFRICA_MAPUTO = new ZoneInfo(new Integer(38), "Africa/Maputo");
  
  public static final ZoneInfo TZ_ID_AFRICA_MASERU = new ZoneInfo(new Integer(39), "Africa/Maseru");
  
  public static final ZoneInfo TZ_ID_AFRICA_MBABANE = new ZoneInfo(new Integer(40), "Africa/Mbabane");
  
  public static final ZoneInfo TZ_ID_AFRICA_MOGADISHU = new ZoneInfo(new Integer(41), "Africa/Mogadishu");
  
  public static final ZoneInfo TZ_ID_AFRICA_MONROVIA = new ZoneInfo(new Integer(42), "Africa/Monrovia");
  
  public static final ZoneInfo TZ_ID_AFRICA_NAIROBI = new ZoneInfo(new Integer(43), "Africa/Nairobi");
  
  public static final ZoneInfo TZ_ID_AFRICA_NDJAMENA = new ZoneInfo(new Integer(44), "Africa/Ndjamena");
  
  public static final ZoneInfo TZ_ID_AFRICA_NIAMEY = new ZoneInfo(new Integer(45), "Africa/Niamey");
  
  public static final ZoneInfo TZ_ID_AFRICA_NOUAKCHOTT = new ZoneInfo(new Integer(46), "Africa/Nouakchott");
  
  public static final ZoneInfo TZ_ID_AFRICA_OUAGADOUGOU = new ZoneInfo(new Integer(47), "Africa/Ouagadougou");
  
  public static final ZoneInfo TZ_ID_AFRICA_PORTO_NOVO = new ZoneInfo(new Integer(48), "Africa/Porto-Novo");
  
  public static final ZoneInfo TZ_ID_AFRICA_SAO_TOME = new ZoneInfo(new Integer(49), "Africa/Sao_Tome");
  
  public static final ZoneInfo TZ_ID_AFRICA_TIMBUKTU = new ZoneInfo(new Integer(50), "Africa/Timbuktu");
  
  public static final ZoneInfo TZ_ID_AFRICA_TRIPOLI = new ZoneInfo(new Integer(51), "Africa/Tripoli");
  
  public static final ZoneInfo TZ_ID_AFRICA_TUNIS = new ZoneInfo(new Integer(52), "Africa/Tunis");
  
  public static final ZoneInfo TZ_ID_AFRICA_WINDHOEK = new ZoneInfo(new Integer(53), "Africa/Windhoek");
  
  public static final ZoneInfo TZ_ID_AMERICA_ADAK = new ZoneInfo(new Integer(54), "America/Adak");
  
  public static final ZoneInfo TZ_ID_AMERICA_ANCHORAGE = new ZoneInfo(new Integer(55), "America/Anchorage");
  
  public static final ZoneInfo TZ_ID_AMERICA_ANGUILLA = new ZoneInfo(new Integer(56), "America/Anguilla");
  
  public static final ZoneInfo TZ_ID_AMERICA_ANTIGUA = new ZoneInfo(new Integer(57), "America/Antigua");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARAGUAINA = new ZoneInfo(new Integer(58), "America/Araguaina");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_BUENOS_AIRES = new ZoneInfo(new Integer(59), "America/Argentina/Buenos_Aires");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_CATAMARCA = new ZoneInfo(new Integer(60), "America/Argentina/Catamarca");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_COMODRIVADAVIA = new ZoneInfo(new Integer(61), "America/Argentina/ComodRivadavia");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_CORDOBA = new ZoneInfo(new Integer(62), "America/Argentina/Cordoba");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_JUJUY = new ZoneInfo(new Integer(63), "America/Argentina/Jujuy");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_LA_RIOJA = new ZoneInfo(new Integer(64), "America/Argentina/La_Rioja");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_MENDOZA = new ZoneInfo(new Integer(65), "America/Argentina/Mendoza");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_RIO_GALLEGOS = new ZoneInfo(new Integer(66), "America/Argentina/Rio_Gallegos");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_SALTA = new ZoneInfo(new Integer(67), "America/Argentina/Salta");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_SAN_JUAN = new ZoneInfo(new Integer(68), "America/Argentina/San_Juan");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_SAN_LUIS = new ZoneInfo(new Integer(69), "America/Argentina/San_Luis");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_TUCUMAN = new ZoneInfo(new Integer(70), "America/Argentina/Tucuman");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARGENTINA_USHUAIA = new ZoneInfo(new Integer(71), "America/Argentina/Ushuaia");
  
  public static final ZoneInfo TZ_ID_AMERICA_ARUBA = new ZoneInfo(new Integer(72), "America/Aruba");
  
  public static final ZoneInfo TZ_ID_AMERICA_ASUNCION = new ZoneInfo(new Integer(73), "America/Asuncion");
  
  public static final ZoneInfo TZ_ID_AMERICA_ATIKOKAN = new ZoneInfo(new Integer(74), "America/Atikokan");
  
  public static final ZoneInfo TZ_ID_AMERICA_ATKA = new ZoneInfo(new Integer(75), "America/Atka");
  
  public static final ZoneInfo TZ_ID_AMERICA_BAHIA = new ZoneInfo(new Integer(76), "America/Bahia");
  
  public static final ZoneInfo TZ_ID_AMERICA_BAHIA_BANDERAS = new ZoneInfo(new Integer(77), "America/Bahia_Banderas");
  
  public static final ZoneInfo TZ_ID_AMERICA_BARBADOS = new ZoneInfo(new Integer(78), "America/Barbados");
  
  public static final ZoneInfo TZ_ID_AMERICA_BELEM = new ZoneInfo(new Integer(79), "America/Belem");
  
  public static final ZoneInfo TZ_ID_AMERICA_BELIZE = new ZoneInfo(new Integer(80), "America/Belize");
  
  public static final ZoneInfo TZ_ID_AMERICA_BLANC_SABLON = new ZoneInfo(new Integer(81), "America/Blanc-Sablon");
  
  public static final ZoneInfo TZ_ID_AMERICA_BOA_VISTA = new ZoneInfo(new Integer(82), "America/Boa_Vista");
  
  public static final ZoneInfo TZ_ID_AMERICA_BOGOTA = new ZoneInfo(new Integer(83), "America/Bogota");
  
  public static final ZoneInfo TZ_ID_AMERICA_BOISE = new ZoneInfo(new Integer(84), "America/Boise");
  
  public static final ZoneInfo TZ_ID_AMERICA_BUENOS_AIRES = new ZoneInfo(new Integer(85), "America/Buenos_Aires");
  
  public static final ZoneInfo TZ_ID_AMERICA_CAMBRIDGE_BAY = new ZoneInfo(new Integer(86), "America/Cambridge_Bay");
  
  public static final ZoneInfo TZ_ID_AMERICA_CAMPO_GRANDE = new ZoneInfo(new Integer(87), "America/Campo_Grande");
  
  public static final ZoneInfo TZ_ID_AMERICA_CANCUN = new ZoneInfo(new Integer(88), "America/Cancun");
  
  public static final ZoneInfo TZ_ID_AMERICA_CARACAS = new ZoneInfo(new Integer(89), "America/Caracas");
  
  public static final ZoneInfo TZ_ID_AMERICA_CATAMARCA = new ZoneInfo(new Integer(90), "America/Catamarca");
  
  public static final ZoneInfo TZ_ID_AMERICA_CAYENNE = new ZoneInfo(new Integer(91), "America/Cayenne");
  
  public static final ZoneInfo TZ_ID_AMERICA_CAYMAN = new ZoneInfo(new Integer(92), "America/Cayman");
  
  public static final ZoneInfo TZ_ID_AMERICA_CHICAGO = new ZoneInfo(new Integer(93), "America/Chicago");
  
  public static final ZoneInfo TZ_ID_AMERICA_CHIHUAHUA = new ZoneInfo(new Integer(94), "America/Chihuahua");
  
  public static final ZoneInfo TZ_ID_AMERICA_CORAL_HARBOUR = new ZoneInfo(new Integer(95), "America/Coral_Harbour");
  
  public static final ZoneInfo TZ_ID_AMERICA_CORDOBA = new ZoneInfo(new Integer(96), "America/Cordoba");
  
  public static final ZoneInfo TZ_ID_AMERICA_COSTA_RICA = new ZoneInfo(new Integer(97), "America/Costa_Rica");
  
  public static final ZoneInfo TZ_ID_AMERICA_CUIABA = new ZoneInfo(new Integer(98), "America/Cuiaba");
  
  public static final ZoneInfo TZ_ID_AMERICA_CURACAO = new ZoneInfo(new Integer(99), "America/Curacao");
  
  public static final ZoneInfo TZ_ID_AMERICA_DANMARKSHAVN = new ZoneInfo(new Integer(100), "America/Danmarkshavn");
  
  public static final ZoneInfo TZ_ID_AMERICA_DAWSON = new ZoneInfo(new Integer(101), "America/Dawson");
  
  public static final ZoneInfo TZ_ID_AMERICA_DAWSON_CREEK = new ZoneInfo(new Integer(102), "America/Dawson_Creek");
  
  public static final ZoneInfo TZ_ID_AMERICA_DENVER = new ZoneInfo(new Integer(103), "America/Denver");
  
  public static final ZoneInfo TZ_ID_AMERICA_DETROIT = new ZoneInfo(new Integer(104), "America/Detroit");
  
  public static final ZoneInfo TZ_ID_AMERICA_DOMINICA = new ZoneInfo(new Integer(105), "America/Dominica");
  
  public static final ZoneInfo TZ_ID_AMERICA_EDMONTON = new ZoneInfo(new Integer(106), "America/Edmonton");
  
  public static final ZoneInfo TZ_ID_AMERICA_EIRUNEPE = new ZoneInfo(new Integer(107), "America/Eirunepe");
  
  public static final ZoneInfo TZ_ID_AMERICA_EL_SALVADOR = new ZoneInfo(new Integer(108), "America/El_Salvador");
  
  public static final ZoneInfo TZ_ID_AMERICA_ENSENADA = new ZoneInfo(new Integer(109), "America/Ensenada");
  
  public static final ZoneInfo TZ_ID_AMERICA_FORT_WAYNE = new ZoneInfo(new Integer(110), "America/Fort_Wayne");
  
  public static final ZoneInfo TZ_ID_AMERICA_FORTALEZA = new ZoneInfo(new Integer(111), "America/Fortaleza");
  
  public static final ZoneInfo TZ_ID_AMERICA_GLACE_BAY = new ZoneInfo(new Integer(112), "America/Glace_Bay");
  
  public static final ZoneInfo TZ_ID_AMERICA_GODTHAB = new ZoneInfo(new Integer(113), "America/Godthab");
  
  public static final ZoneInfo TZ_ID_AMERICA_GOOSE_BAY = new ZoneInfo(new Integer(114), "America/Goose_Bay");
  
  public static final ZoneInfo TZ_ID_AMERICA_GRAND_TURK = new ZoneInfo(new Integer(115), "America/Grand_Turk");
  
  public static final ZoneInfo TZ_ID_AMERICA_GRENADA = new ZoneInfo(new Integer(116), "America/Grenada");
  
  public static final ZoneInfo TZ_ID_AMERICA_GUADELOUPE = new ZoneInfo(new Integer(117), "America/Guadeloupe");
  
  public static final ZoneInfo TZ_ID_AMERICA_GUATEMALA = new ZoneInfo(new Integer(118), "America/Guatemala");
  
  public static final ZoneInfo TZ_ID_AMERICA_GUAYAQUIL = new ZoneInfo(new Integer(119), "America/Guayaquil");
  
  public static final ZoneInfo TZ_ID_AMERICA_GUYANA = new ZoneInfo(new Integer(120), "America/Guyana");
  
  public static final ZoneInfo TZ_ID_AMERICA_HALIFAX = new ZoneInfo(new Integer(121), "America/Halifax");
  
  public static final ZoneInfo TZ_ID_AMERICA_HAVANA = new ZoneInfo(new Integer(122), "America/Havana");
  
  public static final ZoneInfo TZ_ID_AMERICA_HERMOSILLO = new ZoneInfo(new Integer(123), "America/Hermosillo");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_INDIANAPOLIS = new ZoneInfo(new Integer(124), "America/Indiana/Indianapolis");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_KNOX = new ZoneInfo(new Integer(125), "America/Indiana/Knox");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_MARENGO = new ZoneInfo(new Integer(126), "America/Indiana/Marengo");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_PETERSBURG = new ZoneInfo(new Integer(127), "America/Indiana/Petersburg");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_TELL_CITY = new ZoneInfo(new Integer(128), "America/Indiana/Tell_City");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_VEVAY = new ZoneInfo(new Integer(129), "America/Indiana/Vevay");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_VINCENNES = new ZoneInfo(new Integer(130), "America/Indiana/Vincennes");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANA_WINAMAC = new ZoneInfo(new Integer(131), "America/Indiana/Winamac");
  
  public static final ZoneInfo TZ_ID_AMERICA_INDIANAPOLIS = new ZoneInfo(new Integer(132), "America/Indianapolis");
  
  public static final ZoneInfo TZ_ID_AMERICA_INUVIK = new ZoneInfo(new Integer(133), "America/Inuvik");
  
  public static final ZoneInfo TZ_ID_AMERICA_IQALUIT = new ZoneInfo(new Integer(134), "America/Iqaluit");
  
  public static final ZoneInfo TZ_ID_AMERICA_JAMAICA = new ZoneInfo(new Integer(135), "America/Jamaica");
  
  public static final ZoneInfo TZ_ID_AMERICA_JUJUY = new ZoneInfo(new Integer(136), "America/Jujuy");
  
  public static final ZoneInfo TZ_ID_AMERICA_JUNEAU = new ZoneInfo(new Integer(137), "America/Juneau");
  
  public static final ZoneInfo TZ_ID_AMERICA_KENTUCKY_LOUISVILLE = new ZoneInfo(new Integer(138), "America/Kentucky/Louisville");
  
  public static final ZoneInfo TZ_ID_AMERICA_KENTUCKY_MONTICELLO = new ZoneInfo(new Integer(139), "America/Kentucky/Monticello");
  
  public static final ZoneInfo TZ_ID_AMERICA_KNOX_IN = new ZoneInfo(new Integer(140), "America/Knox_IN");
  
  public static final ZoneInfo TZ_ID_AMERICA_KRALENDIJK = new ZoneInfo(new Integer(141), "America/Kralendijk");
  
  public static final ZoneInfo TZ_ID_AMERICA_LA_PAZ = new ZoneInfo(new Integer(142), "America/La_Paz");
  
  public static final ZoneInfo TZ_ID_AMERICA_LIMA = new ZoneInfo(new Integer(143), "America/Lima");
  
  public static final ZoneInfo TZ_ID_AMERICA_LOS_ANGELES = new ZoneInfo(new Integer(144), "America/Los_Angeles");
  
  public static final ZoneInfo TZ_ID_AMERICA_LOUISVILLE = new ZoneInfo(new Integer(145), "America/Louisville");
  
  public static final ZoneInfo TZ_ID_AMERICA_LOWER_PRINCES = new ZoneInfo(new Integer(146), "America/Lower_Princes");
  
  public static final ZoneInfo TZ_ID_AMERICA_MACEIO = new ZoneInfo(new Integer(147), "America/Maceio");
  
  public static final ZoneInfo TZ_ID_AMERICA_MANAGUA = new ZoneInfo(new Integer(148), "America/Managua");
  
  public static final ZoneInfo TZ_ID_AMERICA_MANAUS = new ZoneInfo(new Integer(149), "America/Manaus");
  
  public static final ZoneInfo TZ_ID_AMERICA_MARIGOT = new ZoneInfo(new Integer(150), "America/Marigot");
  
  public static final ZoneInfo TZ_ID_AMERICA_MARTINIQUE = new ZoneInfo(new Integer(151), "America/Martinique");
  
  public static final ZoneInfo TZ_ID_AMERICA_MATAMOROS = new ZoneInfo(new Integer(152), "America/Matamoros");
  
  public static final ZoneInfo TZ_ID_AMERICA_MAZATLAN = new ZoneInfo(new Integer(153), "America/Mazatlan");
  
  public static final ZoneInfo TZ_ID_AMERICA_MENDOZA = new ZoneInfo(new Integer(154), "America/Mendoza");
  
  public static final ZoneInfo TZ_ID_AMERICA_MENOMINEE = new ZoneInfo(new Integer(155), "America/Menominee");
  
  public static final ZoneInfo TZ_ID_AMERICA_MERIDA = new ZoneInfo(new Integer(156), "America/Merida");
  
  public static final ZoneInfo TZ_ID_AMERICA_METLAKATLA = new ZoneInfo(new Integer(157), "America/Metlakatla");
  
  public static final ZoneInfo TZ_ID_AMERICA_MEXICO_CITY = new ZoneInfo(new Integer(158), "America/Mexico_City");
  
  public static final ZoneInfo TZ_ID_AMERICA_MIQUELON = new ZoneInfo(new Integer(159), "America/Miquelon");
  
  public static final ZoneInfo TZ_ID_AMERICA_MONCTON = new ZoneInfo(new Integer(160), "America/Moncton");
  
  public static final ZoneInfo TZ_ID_AMERICA_MONTERREY = new ZoneInfo(new Integer(161), "America/Monterrey");
  
  public static final ZoneInfo TZ_ID_AMERICA_MONTEVIDEO = new ZoneInfo(new Integer(162), "America/Montevideo");
  
  public static final ZoneInfo TZ_ID_AMERICA_MONTREAL = new ZoneInfo(new Integer(163), "America/Montreal");
  
  public static final ZoneInfo TZ_ID_AMERICA_MONTSERRAT = new ZoneInfo(new Integer(164), "America/Montserrat");
  
  public static final ZoneInfo TZ_ID_AMERICA_NASSAU = new ZoneInfo(new Integer(165), "America/Nassau");
  
  public static final ZoneInfo TZ_ID_AMERICA_NEW_YORK = new ZoneInfo(new Integer(166), "America/New_York");
  
  public static final ZoneInfo TZ_ID_AMERICA_NIPIGON = new ZoneInfo(new Integer(167), "America/Nipigon");
  
  public static final ZoneInfo TZ_ID_AMERICA_NOME = new ZoneInfo(new Integer(168), "America/Nome");
  
  public static final ZoneInfo TZ_ID_AMERICA_NORONHA = new ZoneInfo(new Integer(169), "America/Noronha");
  
  public static final ZoneInfo TZ_ID_AMERICA_NORTH_DAKOTA_BEULAH = new ZoneInfo(new Integer(170), "America/North_Dakota/Beulah");
  
  public static final ZoneInfo TZ_ID_AMERICA_NORTH_DAKOTA_CENTER = new ZoneInfo(new Integer(171), "America/North_Dakota/Center");
  
  public static final ZoneInfo TZ_ID_AMERICA_NORTH_DAKOTA_NEW_SALEM = new ZoneInfo(new Integer(172), "America/North_Dakota/New_Salem");
  
  public static final ZoneInfo TZ_ID_AMERICA_OJINAGA = new ZoneInfo(new Integer(173), "America/Ojinaga");
  
  public static final ZoneInfo TZ_ID_AMERICA_PANAMA = new ZoneInfo(new Integer(174), "America/Panama");
  
  public static final ZoneInfo TZ_ID_AMERICA_PANGNIRTUNG = new ZoneInfo(new Integer(175), "America/Pangnirtung");
  
  public static final ZoneInfo TZ_ID_AMERICA_PARAMARIBO = new ZoneInfo(new Integer(176), "America/Paramaribo");
  
  public static final ZoneInfo TZ_ID_AMERICA_PHOENIX = new ZoneInfo(new Integer(177), "America/Phoenix");
  
  public static final ZoneInfo TZ_ID_AMERICA_PORT_AU_PRINCE = new ZoneInfo(new Integer(178), "America/Port-au-Prince");
  
  public static final ZoneInfo TZ_ID_AMERICA_PORT_OF_SPAIN = new ZoneInfo(new Integer(179), "America/Port_of_Spain");
  
  public static final ZoneInfo TZ_ID_AMERICA_PORTO_ACRE = new ZoneInfo(new Integer(180), "America/Porto_Acre");
  
  public static final ZoneInfo TZ_ID_AMERICA_PORTO_VELHO = new ZoneInfo(new Integer(181), "America/Porto_Velho");
  
  public static final ZoneInfo TZ_ID_AMERICA_PUERTO_RICO = new ZoneInfo(new Integer(182), "America/Puerto_Rico");
  
  public static final ZoneInfo TZ_ID_AMERICA_RAINY_RIVER = new ZoneInfo(new Integer(183), "America/Rainy_River");
  
  public static final ZoneInfo TZ_ID_AMERICA_RANKIN_INLET = new ZoneInfo(new Integer(184), "America/Rankin_Inlet");
  
  public static final ZoneInfo TZ_ID_AMERICA_RECIFE = new ZoneInfo(new Integer(185), "America/Recife");
  
  public static final ZoneInfo TZ_ID_AMERICA_REGINA = new ZoneInfo(new Integer(186), "America/Regina");
  
  public static final ZoneInfo TZ_ID_AMERICA_RESOLUTE = new ZoneInfo(new Integer(187), "America/Resolute");
  
  public static final ZoneInfo TZ_ID_AMERICA_RIO_BRANCO = new ZoneInfo(new Integer(188), "America/Rio_Branco");
  
  public static final ZoneInfo TZ_ID_AMERICA_ROSARIO = new ZoneInfo(new Integer(189), "America/Rosario");
  
  public static final ZoneInfo TZ_ID_AMERICA_SANTA_ISABEL = new ZoneInfo(new Integer(190), "America/Santa_Isabel");
  
  public static final ZoneInfo TZ_ID_AMERICA_SANTAREM = new ZoneInfo(new Integer(191), "America/Santarem");
  
  public static final ZoneInfo TZ_ID_AMERICA_SANTIAGO = new ZoneInfo(new Integer(192), "America/Santiago");
  
  public static final ZoneInfo TZ_ID_AMERICA_SANTO_DOMINGO = new ZoneInfo(new Integer(193), "America/Santo_Domingo");
  
  public static final ZoneInfo TZ_ID_AMERICA_SAO_PAULO = new ZoneInfo(new Integer(194), "America/Sao_Paulo");
  
  public static final ZoneInfo TZ_ID_AMERICA_SCORESBYSUND = new ZoneInfo(new Integer(195), "America/Scoresbysund");
  
  public static final ZoneInfo TZ_ID_AMERICA_SHIPROCK = new ZoneInfo(new Integer(196), "America/Shiprock");
  
  public static final ZoneInfo TZ_ID_AMERICA_SITKA = new ZoneInfo(new Integer(197), "America/Sitka");
  
  public static final ZoneInfo TZ_ID_AMERICA_ST_BARTHELEMY = new ZoneInfo(new Integer(198), "America/St_Barthelemy");
  
  public static final ZoneInfo TZ_ID_AMERICA_ST_JOHNS = new ZoneInfo(new Integer(199), "America/St_Johns");
  
  public static final ZoneInfo TZ_ID_AMERICA_ST_KITTS = new ZoneInfo(new Integer(200), "America/St_Kitts");
  
  public static final ZoneInfo TZ_ID_AMERICA_ST_LUCIA = new ZoneInfo(new Integer(201), "America/St_Lucia");
  
  public static final ZoneInfo TZ_ID_AMERICA_ST_THOMAS = new ZoneInfo(new Integer(202), "America/St_Thomas");
  
  public static final ZoneInfo TZ_ID_AMERICA_ST_VINCENT = new ZoneInfo(new Integer(203), "America/St_Vincent");
  
  public static final ZoneInfo TZ_ID_AMERICA_SWIFT_CURRENT = new ZoneInfo(new Integer(204), "America/Swift_Current");
  
  public static final ZoneInfo TZ_ID_AMERICA_TEGUCIGALPA = new ZoneInfo(new Integer(205), "America/Tegucigalpa");
  
  public static final ZoneInfo TZ_ID_AMERICA_THULE = new ZoneInfo(new Integer(206), "America/Thule");
  
  public static final ZoneInfo TZ_ID_AMERICA_THUNDER_BAY = new ZoneInfo(new Integer(207), "America/Thunder_Bay");
  
  public static final ZoneInfo TZ_ID_AMERICA_TIJUANA = new ZoneInfo(new Integer(208), "America/Tijuana");
  
  public static final ZoneInfo TZ_ID_AMERICA_TORONTO = new ZoneInfo(new Integer(209), "America/Toronto");
  
  public static final ZoneInfo TZ_ID_AMERICA_TORTOLA = new ZoneInfo(new Integer(210), "America/Tortola");
  
  public static final ZoneInfo TZ_ID_AMERICA_VANCOUVER = new ZoneInfo(new Integer(211), "America/Vancouver");
  
  public static final ZoneInfo TZ_ID_AMERICA_VIRGIN = new ZoneInfo(new Integer(212), "America/Virgin");
  
  public static final ZoneInfo TZ_ID_AMERICA_WHITEHORSE = new ZoneInfo(new Integer(213), "America/Whitehorse");
  
  public static final ZoneInfo TZ_ID_AMERICA_WINNIPEG = new ZoneInfo(new Integer(214), "America/Winnipeg");
  
  public static final ZoneInfo TZ_ID_AMERICA_YAKUTAT = new ZoneInfo(new Integer(215), "America/Yakutat");
  
  public static final ZoneInfo TZ_ID_AMERICA_YELLOWKNIFE = new ZoneInfo(new Integer(216), "America/Yellowknife");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_CASEY = new ZoneInfo(new Integer(217), "Antarctica/Casey");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_DAVIS = new ZoneInfo(new Integer(218), "Antarctica/Davis");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_DUMONTDURVILLE = new ZoneInfo(new Integer(219), "Antarctica/DumontDUrville");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_MACQUARIE = new ZoneInfo(new Integer(220), "Antarctica/Macquarie");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_MAWSON = new ZoneInfo(new Integer(221), "Antarctica/Mawson");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_MCMURDO = new ZoneInfo(new Integer(222), "Antarctica/McMurdo");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_PALMER = new ZoneInfo(new Integer(223), "Antarctica/Palmer");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_ROTHERA = new ZoneInfo(new Integer(224), "Antarctica/Rothera");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_SOUTH_POLE = new ZoneInfo(new Integer(225), "Antarctica/South_Pole");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_SYOWA = new ZoneInfo(new Integer(226), "Antarctica/Syowa");
  
  public static final ZoneInfo TZ_ID_ANTARCTICA_VOSTOK = new ZoneInfo(new Integer(227), "Antarctica/Vostok");
  
  public static final ZoneInfo TZ_ID_ARCTIC_LONGYEARBYEN = new ZoneInfo(new Integer(228), "Arctic/Longyearbyen");
  
  public static final ZoneInfo TZ_ID_ASIA_ADEN = new ZoneInfo(new Integer(229), "Asia/Aden");
  
  public static final ZoneInfo TZ_ID_ASIA_ALMATY = new ZoneInfo(new Integer(230), "Asia/Almaty");
  
  public static final ZoneInfo TZ_ID_ASIA_AMMAN = new ZoneInfo(new Integer(231), "Asia/Amman");
  
  public static final ZoneInfo TZ_ID_ASIA_ANADYR = new ZoneInfo(new Integer(232), "Asia/Anadyr");
  
  public static final ZoneInfo TZ_ID_ASIA_AQTAU = new ZoneInfo(new Integer(233), "Asia/Aqtau");
  
  public static final ZoneInfo TZ_ID_ASIA_AQTOBE = new ZoneInfo(new Integer(234), "Asia/Aqtobe");
  
  public static final ZoneInfo TZ_ID_ASIA_ASHGABAT = new ZoneInfo(new Integer(235), "Asia/Ashgabat");
  
  public static final ZoneInfo TZ_ID_ASIA_ASHKHABAD = new ZoneInfo(new Integer(236), "Asia/Ashkhabad");
  
  public static final ZoneInfo TZ_ID_ASIA_BAGHDAD = new ZoneInfo(new Integer(237), "Asia/Baghdad");
  
  public static final ZoneInfo TZ_ID_ASIA_BAHRAIN = new ZoneInfo(new Integer(238), "Asia/Bahrain");
  
  public static final ZoneInfo TZ_ID_ASIA_BAKU = new ZoneInfo(new Integer(239), "Asia/Baku");
  
  public static final ZoneInfo TZ_ID_ASIA_BANGKOK = new ZoneInfo(new Integer(240), "Asia/Bangkok");
  
  public static final ZoneInfo TZ_ID_ASIA_BEIRUT = new ZoneInfo(new Integer(241), "Asia/Beirut");
  
  public static final ZoneInfo TZ_ID_ASIA_BISHKEK = new ZoneInfo(new Integer(242), "Asia/Bishkek");
  
  public static final ZoneInfo TZ_ID_ASIA_BRUNEI = new ZoneInfo(new Integer(243), "Asia/Brunei");
  
  public static final ZoneInfo TZ_ID_ASIA_CALCUTTA = new ZoneInfo(new Integer(244), "Asia/Calcutta");
  
  public static final ZoneInfo TZ_ID_ASIA_CHOIBALSAN = new ZoneInfo(new Integer(245), "Asia/Choibalsan");
  
  public static final ZoneInfo TZ_ID_ASIA_CHONGQING = new ZoneInfo(new Integer(246), "Asia/Chongqing");
  
  public static final ZoneInfo TZ_ID_ASIA_CHUNGKING = new ZoneInfo(new Integer(247), "Asia/Chungking");
  
  public static final ZoneInfo TZ_ID_ASIA_COLOMBO = new ZoneInfo(new Integer(248), "Asia/Colombo");
  
  public static final ZoneInfo TZ_ID_ASIA_DACCA = new ZoneInfo(new Integer(249), "Asia/Dacca");
  
  public static final ZoneInfo TZ_ID_ASIA_DAMASCUS = new ZoneInfo(new Integer(250), "Asia/Damascus");
  
  public static final ZoneInfo TZ_ID_ASIA_DHAKA = new ZoneInfo(new Integer(251), "Asia/Dhaka");
  
  public static final ZoneInfo TZ_ID_ASIA_DILI = new ZoneInfo(new Integer(252), "Asia/Dili");
  
  public static final ZoneInfo TZ_ID_ASIA_DUBAI = new ZoneInfo(new Integer(253), "Asia/Dubai");
  
  public static final ZoneInfo TZ_ID_ASIA_DUSHANBE = new ZoneInfo(new Integer(254), "Asia/Dushanbe");
  
  public static final ZoneInfo TZ_ID_ASIA_GAZA = new ZoneInfo(new Integer(255), "Asia/Gaza");
  
  public static final ZoneInfo TZ_ID_ASIA_HARBIN = new ZoneInfo(new Integer(256), "Asia/Harbin");
  
  public static final ZoneInfo TZ_ID_ASIA_HEBRON = new ZoneInfo(new Integer(257), "Asia/Hebron");
  
  public static final ZoneInfo TZ_ID_ASIA_HO_CHI_MINH = new ZoneInfo(new Integer(258), "Asia/Ho_Chi_Minh");
  
  public static final ZoneInfo TZ_ID_ASIA_HONG_KONG = new ZoneInfo(new Integer(259), "Asia/Hong_Kong");
  
  public static final ZoneInfo TZ_ID_ASIA_HOVD = new ZoneInfo(new Integer(260), "Asia/Hovd");
  
  public static final ZoneInfo TZ_ID_ASIA_IRKUTSK = new ZoneInfo(new Integer(261), "Asia/Irkutsk");
  
  public static final ZoneInfo TZ_ID_ASIA_ISTANBUL = new ZoneInfo(new Integer(262), "Asia/Istanbul");
  
  public static final ZoneInfo TZ_ID_ASIA_JAKARTA = new ZoneInfo(new Integer(263), "Asia/Jakarta");
  
  public static final ZoneInfo TZ_ID_ASIA_JAYAPURA = new ZoneInfo(new Integer(264), "Asia/Jayapura");
  
  public static final ZoneInfo TZ_ID_ASIA_JERUSALEM = new ZoneInfo(new Integer(265), "Asia/Jerusalem");
  
  public static final ZoneInfo TZ_ID_ASIA_KABUL = new ZoneInfo(new Integer(266), "Asia/Kabul");
  
  public static final ZoneInfo TZ_ID_ASIA_KAMCHATKA = new ZoneInfo(new Integer(267), "Asia/Kamchatka");
  
  public static final ZoneInfo TZ_ID_ASIA_KARACHI = new ZoneInfo(new Integer(268), "Asia/Karachi");
  
  public static final ZoneInfo TZ_ID_ASIA_KASHGAR = new ZoneInfo(new Integer(269), "Asia/Kashgar");
  
  public static final ZoneInfo TZ_ID_ASIA_KATHMANDU = new ZoneInfo(new Integer(270), "Asia/Kathmandu");
  
  public static final ZoneInfo TZ_ID_ASIA_KATMANDU = new ZoneInfo(new Integer(271), "Asia/Katmandu");
  
  public static final ZoneInfo TZ_ID_ASIA_KOLKATA = new ZoneInfo(new Integer(272), "Asia/Kolkata");
  
  public static final ZoneInfo TZ_ID_ASIA_KRASNOYARSK = new ZoneInfo(new Integer(273), "Asia/Krasnoyarsk");
  
  public static final ZoneInfo TZ_ID_ASIA_KUALA_LUMPUR = new ZoneInfo(new Integer(274), "Asia/Kuala_Lumpur");
  
  public static final ZoneInfo TZ_ID_ASIA_KUCHING = new ZoneInfo(new Integer(275), "Asia/Kuching");
  
  public static final ZoneInfo TZ_ID_ASIA_KUWAIT = new ZoneInfo(new Integer(276), "Asia/Kuwait");
  
  public static final ZoneInfo TZ_ID_ASIA_MACAO = new ZoneInfo(new Integer(277), "Asia/Macao");
  
  public static final ZoneInfo TZ_ID_ASIA_MACAU = new ZoneInfo(new Integer(278), "Asia/Macau");
  
  public static final ZoneInfo TZ_ID_ASIA_MAGADAN = new ZoneInfo(new Integer(279), "Asia/Magadan");
  
  public static final ZoneInfo TZ_ID_ASIA_MAKASSAR = new ZoneInfo(new Integer(280), "Asia/Makassar");
  
  public static final ZoneInfo TZ_ID_ASIA_MANILA = new ZoneInfo(new Integer(281), "Asia/Manila");
  
  public static final ZoneInfo TZ_ID_ASIA_MUSCAT = new ZoneInfo(new Integer(282), "Asia/Muscat");
  
  public static final ZoneInfo TZ_ID_ASIA_NICOSIA = new ZoneInfo(new Integer(283), "Asia/Nicosia");
  
  public static final ZoneInfo TZ_ID_ASIA_NOVOKUZNETSK = new ZoneInfo(new Integer(284), "Asia/Novokuznetsk");
  
  public static final ZoneInfo TZ_ID_ASIA_NOVOSIBIRSK = new ZoneInfo(new Integer(285), "Asia/Novosibirsk");
  
  public static final ZoneInfo TZ_ID_ASIA_OMSK = new ZoneInfo(new Integer(286), "Asia/Omsk");
  
  public static final ZoneInfo TZ_ID_ASIA_ORAL = new ZoneInfo(new Integer(287), "Asia/Oral");
  
  public static final ZoneInfo TZ_ID_ASIA_PHNOM_PENH = new ZoneInfo(new Integer(288), "Asia/Phnom_Penh");
  
  public static final ZoneInfo TZ_ID_ASIA_PONTIANAK = new ZoneInfo(new Integer(289), "Asia/Pontianak");
  
  public static final ZoneInfo TZ_ID_ASIA_PYONGYANG = new ZoneInfo(new Integer(290), "Asia/Pyongyang");
  
  public static final ZoneInfo TZ_ID_ASIA_QATAR = new ZoneInfo(new Integer(291), "Asia/Qatar");
  
  public static final ZoneInfo TZ_ID_ASIA_QYZYLORDA = new ZoneInfo(new Integer(292), "Asia/Qyzylorda");
  
  public static final ZoneInfo TZ_ID_ASIA_RANGOON = new ZoneInfo(new Integer(293), "Asia/Rangoon");
  
  public static final ZoneInfo TZ_ID_ASIA_RIYADH = new ZoneInfo(new Integer(294), "Asia/Riyadh");
  
  public static final ZoneInfo TZ_ID_ASIA_RIYADH87 = new ZoneInfo(new Integer(295), "Asia/Riyadh87");
  
  public static final ZoneInfo TZ_ID_ASIA_RIYADH88 = new ZoneInfo(new Integer(296), "Asia/Riyadh88");
  
  public static final ZoneInfo TZ_ID_ASIA_RIYADH89 = new ZoneInfo(new Integer(297), "Asia/Riyadh89");
  
  public static final ZoneInfo TZ_ID_ASIA_SAIGON = new ZoneInfo(new Integer(298), "Asia/Saigon");
  
  public static final ZoneInfo TZ_ID_ASIA_SAKHALIN = new ZoneInfo(new Integer(299), "Asia/Sakhalin");
  
  public static final ZoneInfo TZ_ID_ASIA_SAMARKAND = new ZoneInfo(new Integer(300), "Asia/Samarkand");
  
  public static final ZoneInfo TZ_ID_ASIA_SEOUL = new ZoneInfo(new Integer(301), "Asia/Seoul");
  
  public static final ZoneInfo TZ_ID_ASIA_SHANGHAI = new ZoneInfo(new Integer(302), "Asia/Shanghai");
  
  public static final ZoneInfo TZ_ID_ASIA_SINGAPORE = new ZoneInfo(new Integer(303), "Asia/Singapore");
  
  public static final ZoneInfo TZ_ID_ASIA_TAIPEI = new ZoneInfo(new Integer(304), "Asia/Taipei");
  
  public static final ZoneInfo TZ_ID_ASIA_TASHKENT = new ZoneInfo(new Integer(305), "Asia/Tashkent");
  
  public static final ZoneInfo TZ_ID_ASIA_TBILISI = new ZoneInfo(new Integer(306), "Asia/Tbilisi");
  
  public static final ZoneInfo TZ_ID_ASIA_TEHRAN = new ZoneInfo(new Integer(307), "Asia/Tehran");
  
  public static final ZoneInfo TZ_ID_ASIA_TEL_AVIV = new ZoneInfo(new Integer(308), "Asia/Tel_Aviv");
  
  public static final ZoneInfo TZ_ID_ASIA_THIMBU = new ZoneInfo(new Integer(309), "Asia/Thimbu");
  
  public static final ZoneInfo TZ_ID_ASIA_THIMPHU = new ZoneInfo(new Integer(310), "Asia/Thimphu");
  
  public static final ZoneInfo TZ_ID_ASIA_TOKYO = new ZoneInfo(new Integer(311), "Asia/Tokyo");
  
  public static final ZoneInfo TZ_ID_ASIA_UJUNG_PANDANG = new ZoneInfo(new Integer(312), "Asia/Ujung_Pandang");
  
  public static final ZoneInfo TZ_ID_ASIA_ULAANBAATAR = new ZoneInfo(new Integer(313), "Asia/Ulaanbaatar");
  
  public static final ZoneInfo TZ_ID_ASIA_ULAN_BATOR = new ZoneInfo(new Integer(314), "Asia/Ulan_Bator");
  
  public static final ZoneInfo TZ_ID_ASIA_URUMQI = new ZoneInfo(new Integer(315), "Asia/Urumqi");
  
  public static final ZoneInfo TZ_ID_ASIA_VIENTIANE = new ZoneInfo(new Integer(316), "Asia/Vientiane");
  
  public static final ZoneInfo TZ_ID_ASIA_VLADIVOSTOK = new ZoneInfo(new Integer(317), "Asia/Vladivostok");
  
  public static final ZoneInfo TZ_ID_ASIA_YAKUTSK = new ZoneInfo(new Integer(318), "Asia/Yakutsk");
  
  public static final ZoneInfo TZ_ID_ASIA_YEKATERINBURG = new ZoneInfo(new Integer(319), "Asia/Yekaterinburg");
  
  public static final ZoneInfo TZ_ID_ASIA_YEREVAN = new ZoneInfo(new Integer(320), "Asia/Yerevan");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_AZORES = new ZoneInfo(new Integer(321), "Atlantic/Azores");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_BERMUDA = new ZoneInfo(new Integer(322), "Atlantic/Bermuda");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_CANARY = new ZoneInfo(new Integer(323), "Atlantic/Canary");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_CAPE_VERDE = new ZoneInfo(new Integer(324), "Atlantic/Cape_Verde");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_FAEROE = new ZoneInfo(new Integer(325), "Atlantic/Faeroe");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_FAROE = new ZoneInfo(new Integer(326), "Atlantic/Faroe");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_JAN_MAYEN = new ZoneInfo(new Integer(327), "Atlantic/Jan_Mayen");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_MADEIRA = new ZoneInfo(new Integer(328), "Atlantic/Madeira");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_REYKJAVIK = new ZoneInfo(new Integer(329), "Atlantic/Reykjavik");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_SOUTH_GEORGIA = new ZoneInfo(new Integer(330), "Atlantic/South_Georgia");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_ST_HELENA = new ZoneInfo(new Integer(331), "Atlantic/St_Helena");
  
  public static final ZoneInfo TZ_ID_ATLANTIC_STANLEY = new ZoneInfo(new Integer(332), "Atlantic/Stanley");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_ACT = new ZoneInfo(new Integer(333), "Australia/ACT");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_ADELAIDE = new ZoneInfo(new Integer(334), "Australia/Adelaide");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_BRISBANE = new ZoneInfo(new Integer(335), "Australia/Brisbane");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_BROKEN_HILL = new ZoneInfo(new Integer(336), "Australia/Broken_Hill");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_CANBERRA = new ZoneInfo(new Integer(337), "Australia/Canberra");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_CURRIE = new ZoneInfo(new Integer(338), "Australia/Currie");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_DARWIN = new ZoneInfo(new Integer(339), "Australia/Darwin");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_EUCLA = new ZoneInfo(new Integer(340), "Australia/Eucla");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_HOBART = new ZoneInfo(new Integer(341), "Australia/Hobart");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_LHI = new ZoneInfo(new Integer(342), "Australia/LHI");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_LINDEMAN = new ZoneInfo(new Integer(343), "Australia/Lindeman");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_LORD_HOWE = new ZoneInfo(new Integer(344), "Australia/Lord_Howe");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_MELBOURNE = new ZoneInfo(new Integer(345), "Australia/Melbourne");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_NORTH = new ZoneInfo(new Integer(346), "Australia/North");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_NSW = new ZoneInfo(new Integer(347), "Australia/NSW");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_PERTH = new ZoneInfo(new Integer(348), "Australia/Perth");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_QUEENSLAND = new ZoneInfo(new Integer(349), "Australia/Queensland");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_SOUTH = new ZoneInfo(new Integer(350), "Australia/South");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_SYDNEY = new ZoneInfo(new Integer(351), "Australia/Sydney");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_TASMANIA = new ZoneInfo(new Integer(352), "Australia/Tasmania");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_VICTORIA = new ZoneInfo(new Integer(353), "Australia/Victoria");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_WEST = new ZoneInfo(new Integer(354), "Australia/West");
  
  public static final ZoneInfo TZ_ID_AUSTRALIA_YANCOWINNA = new ZoneInfo(new Integer(355), "Australia/Yancowinna");
  
  public static final ZoneInfo TZ_ID_BRAZIL_ACRE = new ZoneInfo(new Integer(356), "Brazil/Acre");
  
  public static final ZoneInfo TZ_ID_BRAZIL_DENORONHA = new ZoneInfo(new Integer(357), "Brazil/DeNoronha");
  
  public static final ZoneInfo TZ_ID_BRAZIL_EAST = new ZoneInfo(new Integer(358), "Brazil/East");
  
  public static final ZoneInfo TZ_ID_BRAZIL_WEST = new ZoneInfo(new Integer(359), "Brazil/West");
  
  public static final ZoneInfo TZ_ID_CANADA_ATLANTIC = new ZoneInfo(new Integer(360), "Canada/Atlantic");
  
  public static final ZoneInfo TZ_ID_CANADA_CENTRAL = new ZoneInfo(new Integer(361), "Canada/Central");
  
  public static final ZoneInfo TZ_ID_CANADA_EAST_SASKATCHEWAN = new ZoneInfo(new Integer(362), "Canada/East-Saskatchewan");
  
  public static final ZoneInfo TZ_ID_CANADA_EASTERN = new ZoneInfo(new Integer(363), "Canada/Eastern");
  
  public static final ZoneInfo TZ_ID_CANADA_MOUNTAIN = new ZoneInfo(new Integer(364), "Canada/Mountain");
  
  public static final ZoneInfo TZ_ID_CANADA_NEWFOUNDLAND = new ZoneInfo(new Integer(365), "Canada/Newfoundland");
  
  public static final ZoneInfo TZ_ID_CANADA_PACIFIC = new ZoneInfo(new Integer(366), "Canada/Pacific");
  
  public static final ZoneInfo TZ_ID_CANADA_SASKATCHEWAN = new ZoneInfo(new Integer(367), "Canada/Saskatchewan");
  
  public static final ZoneInfo TZ_ID_CANADA_YUKON = new ZoneInfo(new Integer(368), "Canada/Yukon");
  
  public static final ZoneInfo TZ_ID_CET = new ZoneInfo(new Integer(369), "CET");
  
  public static final ZoneInfo TZ_ID_CHILE_CONTINENTAL = new ZoneInfo(new Integer(370), "Chile/Continental");
  
  public static final ZoneInfo TZ_ID_CHILE_EASTERISLAND = new ZoneInfo(new Integer(371), "Chile/EasterIsland");
  
  public static final ZoneInfo TZ_ID_CST6CDT = new ZoneInfo(new Integer(372), "CST6CDT");
  
  public static final ZoneInfo TZ_ID_CUBA = new ZoneInfo(new Integer(373), "Cuba");
  
  public static final ZoneInfo TZ_ID_EET = new ZoneInfo(new Integer(374), "EET");
  
  public static final ZoneInfo TZ_ID_EGYPT = new ZoneInfo(new Integer(375), "Egypt");
  
  public static final ZoneInfo TZ_ID_EIRE = new ZoneInfo(new Integer(376), "Eire");
  
  public static final ZoneInfo TZ_ID_EST = new ZoneInfo(new Integer(377), "EST");
  
  public static final ZoneInfo TZ_ID_EST5EDT = new ZoneInfo(new Integer(378), "EST5EDT");
  
  public static final ZoneInfo TZ_ID_ETC_GMT = new ZoneInfo(new Integer(379), "Etc/GMT");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW0 = new ZoneInfo(new Integer(380), "Etc/GMT+0");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW1 = new ZoneInfo(new Integer(381), "Etc/GMT+1");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW10 = new ZoneInfo(new Integer(382), "Etc/GMT+10");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW11 = new ZoneInfo(new Integer(383), "Etc/GMT+11");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW12 = new ZoneInfo(new Integer(384), "Etc/GMT+12");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW2 = new ZoneInfo(new Integer(385), "Etc/GMT+2");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW3 = new ZoneInfo(new Integer(386), "Etc/GMT+3");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW4 = new ZoneInfo(new Integer(387), "Etc/GMT+4");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW5 = new ZoneInfo(new Integer(388), "Etc/GMT+5");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW6 = new ZoneInfo(new Integer(389), "Etc/GMT+6");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW7 = new ZoneInfo(new Integer(390), "Etc/GMT+7");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW8 = new ZoneInfo(new Integer(391), "Etc/GMT+8");
  
  public static final ZoneInfo TZ_ID_ETC_GMTW9 = new ZoneInfo(new Integer(392), "Etc/GMT+9");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE0 = new ZoneInfo(new Integer(393), "Etc/GMT-0");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE1 = new ZoneInfo(new Integer(394), "Etc/GMT-1");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE10 = new ZoneInfo(new Integer(395), "Etc/GMT-10");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE11 = new ZoneInfo(new Integer(396), "Etc/GMT-11");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE12 = new ZoneInfo(new Integer(397), "Etc/GMT-12");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE13 = new ZoneInfo(new Integer(398), "Etc/GMT-13");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE14 = new ZoneInfo(new Integer(399), "Etc/GMT-14");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE2 = new ZoneInfo(new Integer(400), "Etc/GMT-2");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE3 = new ZoneInfo(new Integer(401), "Etc/GMT-3");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE4 = new ZoneInfo(new Integer(402), "Etc/GMT-4");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE5 = new ZoneInfo(new Integer(403), "Etc/GMT-5");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE6 = new ZoneInfo(new Integer(404), "Etc/GMT-6");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE7 = new ZoneInfo(new Integer(405), "Etc/GMT-7");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE8 = new ZoneInfo(new Integer(406), "Etc/GMT-8");
  
  public static final ZoneInfo TZ_ID_ETC_GMTE9 = new ZoneInfo(new Integer(407), "Etc/GMT-9");
  
  public static final ZoneInfo TZ_ID_ETC_GMT0 = new ZoneInfo(new Integer(408), "Etc/GMT0");
  
  public static final ZoneInfo TZ_ID_ETC_GREENWICH = new ZoneInfo(new Integer(409), "Etc/Greenwich");
  
  public static final ZoneInfo TZ_ID_ETC_UCT = new ZoneInfo(new Integer(410), "Etc/UCT");
  
  public static final ZoneInfo TZ_ID_ETC_UNIVERSAL = new ZoneInfo(new Integer(411), "Etc/Universal");
  
  public static final ZoneInfo TZ_ID_ETC_UTC = new ZoneInfo(new Integer(412), "Etc/UTC");
  
  public static final ZoneInfo TZ_ID_ETC_ZULU = new ZoneInfo(new Integer(413), "Etc/Zulu");
  
  public static final ZoneInfo TZ_ID_EUROPE_AMSTERDAM = new ZoneInfo(new Integer(414), "Europe/Amsterdam");
  
  public static final ZoneInfo TZ_ID_EUROPE_ANDORRA = new ZoneInfo(new Integer(415), "Europe/Andorra");
  
  public static final ZoneInfo TZ_ID_EUROPE_ATHENS = new ZoneInfo(new Integer(416), "Europe/Athens");
  
  public static final ZoneInfo TZ_ID_EUROPE_BELFAST = new ZoneInfo(new Integer(417), "Europe/Belfast");
  
  public static final ZoneInfo TZ_ID_EUROPE_BELGRADE = new ZoneInfo(new Integer(418), "Europe/Belgrade");
  
  public static final ZoneInfo TZ_ID_EUROPE_BERLIN = new ZoneInfo(new Integer(419), "Europe/Berlin");
  
  public static final ZoneInfo TZ_ID_EUROPE_BRATISLAVA = new ZoneInfo(new Integer(420), "Europe/Bratislava");
  
  public static final ZoneInfo TZ_ID_EUROPE_BRUSSELS = new ZoneInfo(new Integer(421), "Europe/Brussels");
  
  public static final ZoneInfo TZ_ID_EUROPE_BUCHAREST = new ZoneInfo(new Integer(422), "Europe/Bucharest");
  
  public static final ZoneInfo TZ_ID_EUROPE_BUDAPEST = new ZoneInfo(new Integer(423), "Europe/Budapest");
  
  public static final ZoneInfo TZ_ID_EUROPE_CHISINAU = new ZoneInfo(new Integer(424), "Europe/Chisinau");
  
  public static final ZoneInfo TZ_ID_EUROPE_COPENHAGEN = new ZoneInfo(new Integer(425), "Europe/Copenhagen");
  
  public static final ZoneInfo TZ_ID_EUROPE_DUBLIN = new ZoneInfo(new Integer(426), "Europe/Dublin");
  
  public static final ZoneInfo TZ_ID_EUROPE_GIBRALTAR = new ZoneInfo(new Integer(427), "Europe/Gibraltar");
  
  public static final ZoneInfo TZ_ID_EUROPE_GUERNSEY = new ZoneInfo(new Integer(428), "Europe/Guernsey");
  
  public static final ZoneInfo TZ_ID_EUROPE_HELSINKI = new ZoneInfo(new Integer(429), "Europe/Helsinki");
  
  public static final ZoneInfo TZ_ID_EUROPE_ISLE_OF_MAN = new ZoneInfo(new Integer(430), "Europe/Isle_of_Man");
  
  public static final ZoneInfo TZ_ID_EUROPE_ISTANBUL = new ZoneInfo(new Integer(431), "Europe/Istanbul");
  
  public static final ZoneInfo TZ_ID_EUROPE_JERSEY = new ZoneInfo(new Integer(432), "Europe/Jersey");
  
  public static final ZoneInfo TZ_ID_EUROPE_KALININGRAD = new ZoneInfo(new Integer(433), "Europe/Kaliningrad");
  
  public static final ZoneInfo TZ_ID_EUROPE_KIEV = new ZoneInfo(new Integer(434), "Europe/Kiev");
  
  public static final ZoneInfo TZ_ID_EUROPE_LISBON = new ZoneInfo(new Integer(435), "Europe/Lisbon");
  
  public static final ZoneInfo TZ_ID_EUROPE_LJUBLJANA = new ZoneInfo(new Integer(436), "Europe/Ljubljana");
  
  public static final ZoneInfo TZ_ID_EUROPE_LONDON = new ZoneInfo(new Integer(437), "Europe/London");
  
  public static final ZoneInfo TZ_ID_EUROPE_LUXEMBOURG = new ZoneInfo(new Integer(438), "Europe/Luxembourg");
  
  public static final ZoneInfo TZ_ID_EUROPE_MADRID = new ZoneInfo(new Integer(439), "Europe/Madrid");
  
  public static final ZoneInfo TZ_ID_EUROPE_MALTA = new ZoneInfo(new Integer(440), "Europe/Malta");
  
  public static final ZoneInfo TZ_ID_EUROPE_MARIEHAMN = new ZoneInfo(new Integer(441), "Europe/Mariehamn");
  
  public static final ZoneInfo TZ_ID_EUROPE_MINSK = new ZoneInfo(new Integer(442), "Europe/Minsk");
  
  public static final ZoneInfo TZ_ID_EUROPE_MONACO = new ZoneInfo(new Integer(443), "Europe/Monaco");
  
  public static final ZoneInfo TZ_ID_EUROPE_MOSCOW = new ZoneInfo(new Integer(444), "Europe/Moscow");
  
  public static final ZoneInfo TZ_ID_EUROPE_NICOSIA = new ZoneInfo(new Integer(445), "Europe/Nicosia");
  
  public static final ZoneInfo TZ_ID_EUROPE_OSLO = new ZoneInfo(new Integer(446), "Europe/Oslo");
  
  public static final ZoneInfo TZ_ID_EUROPE_PARIS = new ZoneInfo(new Integer(447), "Europe/Paris");
  
  public static final ZoneInfo TZ_ID_EUROPE_PODGORICA = new ZoneInfo(new Integer(448), "Europe/Podgorica");
  
  public static final ZoneInfo TZ_ID_EUROPE_PRAGUE = new ZoneInfo(new Integer(449), "Europe/Prague");
  
  public static final ZoneInfo TZ_ID_EUROPE_RIGA = new ZoneInfo(new Integer(450), "Europe/Riga");
  
  public static final ZoneInfo TZ_ID_EUROPE_ROME = new ZoneInfo(new Integer(451), "Europe/Rome");
  
  public static final ZoneInfo TZ_ID_EUROPE_SAMARA = new ZoneInfo(new Integer(452), "Europe/Samara");
  
  public static final ZoneInfo TZ_ID_EUROPE_SAN_MARINO = new ZoneInfo(new Integer(453), "Europe/San_Marino");
  
  public static final ZoneInfo TZ_ID_EUROPE_SARAJEVO = new ZoneInfo(new Integer(454), "Europe/Sarajevo");
  
  public static final ZoneInfo TZ_ID_EUROPE_SIMFEROPOL = new ZoneInfo(new Integer(455), "Europe/Simferopol");
  
  public static final ZoneInfo TZ_ID_EUROPE_SKOPJE = new ZoneInfo(new Integer(456), "Europe/Skopje");
  
  public static final ZoneInfo TZ_ID_EUROPE_SOFIA = new ZoneInfo(new Integer(457), "Europe/Sofia");
  
  public static final ZoneInfo TZ_ID_EUROPE_STOCKHOLM = new ZoneInfo(new Integer(458), "Europe/Stockholm");
  
  public static final ZoneInfo TZ_ID_EUROPE_TALLINN = new ZoneInfo(new Integer(459), "Europe/Tallinn");
  
  public static final ZoneInfo TZ_ID_EUROPE_TIRANE = new ZoneInfo(new Integer(460), "Europe/Tirane");
  
  public static final ZoneInfo TZ_ID_EUROPE_TIRASPOL = new ZoneInfo(new Integer(461), "Europe/Tiraspol");
  
  public static final ZoneInfo TZ_ID_EUROPE_UZHGOROD = new ZoneInfo(new Integer(462), "Europe/Uzhgorod");
  
  public static final ZoneInfo TZ_ID_EUROPE_VADUZ = new ZoneInfo(new Integer(463), "Europe/Vaduz");
  
  public static final ZoneInfo TZ_ID_EUROPE_VATICAN = new ZoneInfo(new Integer(464), "Europe/Vatican");
  
  public static final ZoneInfo TZ_ID_EUROPE_VIENNA = new ZoneInfo(new Integer(465), "Europe/Vienna");
  
  public static final ZoneInfo TZ_ID_EUROPE_VILNIUS = new ZoneInfo(new Integer(466), "Europe/Vilnius");
  
  public static final ZoneInfo TZ_ID_EUROPE_VOLGOGRAD = new ZoneInfo(new Integer(467), "Europe/Volgograd");
  
  public static final ZoneInfo TZ_ID_EUROPE_WARSAW = new ZoneInfo(new Integer(468), "Europe/Warsaw");
  
  public static final ZoneInfo TZ_ID_EUROPE_ZAGREB = new ZoneInfo(new Integer(469), "Europe/Zagreb");
  
  public static final ZoneInfo TZ_ID_EUROPE_ZAPOROZHYE = new ZoneInfo(new Integer(470), "Europe/Zaporozhye");
  
  public static final ZoneInfo TZ_ID_EUROPE_ZURICH = new ZoneInfo(new Integer(471), "Europe/Zurich");
  
  public static final ZoneInfo TZ_ID_GB = new ZoneInfo(new Integer(472), "GB");
  
  public static final ZoneInfo TZ_ID_GB_EIRE = new ZoneInfo(new Integer(473), "GB-Eire");
  
  public static final ZoneInfo TZ_ID_GMT = new ZoneInfo(new Integer(474), "GMT");
  
  public static final ZoneInfo TZ_ID_GMTW0 = new ZoneInfo(new Integer(475), "GMT+0");
  
  public static final ZoneInfo TZ_ID_GMTE0 = new ZoneInfo(new Integer(476), "GMT-0");
  
  public static final ZoneInfo TZ_ID_GMT0 = new ZoneInfo(new Integer(477), "GMT0");
  
  public static final ZoneInfo TZ_ID_GREENWICH = new ZoneInfo(new Integer(478), "Greenwich");
  
  public static final ZoneInfo TZ_ID_HONGKONG = new ZoneInfo(new Integer(479), "Hongkong");
  
  public static final ZoneInfo TZ_ID_HST = new ZoneInfo(new Integer(480), "HST");
  
  public static final ZoneInfo TZ_ID_ICELAND = new ZoneInfo(new Integer(481), "Iceland");
  
  public static final ZoneInfo TZ_ID_INDIAN_ANTANANARIVO = new ZoneInfo(new Integer(482), "Indian/Antananarivo");
  
  public static final ZoneInfo TZ_ID_INDIAN_CHAGOS = new ZoneInfo(new Integer(483), "Indian/Chagos");
  
  public static final ZoneInfo TZ_ID_INDIAN_CHRISTMAS = new ZoneInfo(new Integer(484), "Indian/Christmas");
  
  public static final ZoneInfo TZ_ID_INDIAN_COCOS = new ZoneInfo(new Integer(485), "Indian/Cocos");
  
  public static final ZoneInfo TZ_ID_INDIAN_COMORO = new ZoneInfo(new Integer(486), "Indian/Comoro");
  
  public static final ZoneInfo TZ_ID_INDIAN_KERGUELEN = new ZoneInfo(new Integer(487), "Indian/Kerguelen");
  
  public static final ZoneInfo TZ_ID_INDIAN_MAHE = new ZoneInfo(new Integer(488), "Indian/Mahe");
  
  public static final ZoneInfo TZ_ID_INDIAN_MALDIVES = new ZoneInfo(new Integer(489), "Indian/Maldives");
  
  public static final ZoneInfo TZ_ID_INDIAN_MAURITIUS = new ZoneInfo(new Integer(490), "Indian/Mauritius");
  
  public static final ZoneInfo TZ_ID_INDIAN_MAYOTTE = new ZoneInfo(new Integer(491), "Indian/Mayotte");
  
  public static final ZoneInfo TZ_ID_INDIAN_REUNION = new ZoneInfo(new Integer(492), "Indian/Reunion");
  
  public static final ZoneInfo TZ_ID_IRAN = new ZoneInfo(new Integer(493), "Iran");
  
  public static final ZoneInfo TZ_ID_ISRAEL = new ZoneInfo(new Integer(494), "Israel");
  
  public static final ZoneInfo TZ_ID_JAMAICA = new ZoneInfo(new Integer(495), "Jamaica");
  
  public static final ZoneInfo TZ_ID_JAPAN = new ZoneInfo(new Integer(496), "Japan");
  
  public static final ZoneInfo TZ_ID_KWAJALEIN = new ZoneInfo(new Integer(497), "Kwajalein");
  
  public static final ZoneInfo TZ_ID_LIBYA = new ZoneInfo(new Integer(498), "Libya");
  
  public static final ZoneInfo TZ_ID_MET = new ZoneInfo(new Integer(499), "MET");
  
  public static final ZoneInfo TZ_ID_MEXICO_BAJANORTE = new ZoneInfo(new Integer(500), "Mexico/BajaNorte");
  
  public static final ZoneInfo TZ_ID_MEXICO_BAJASUR = new ZoneInfo(new Integer(501), "Mexico/BajaSur");
  
  public static final ZoneInfo TZ_ID_MEXICO_GENERAL = new ZoneInfo(new Integer(502), "Mexico/General");
  
  public static final ZoneInfo TZ_ID_MIDEAST_RIYADH87 = new ZoneInfo(new Integer(503), "Mideast/Riyadh87");
  
  public static final ZoneInfo TZ_ID_MIDEAST_RIYADH88 = new ZoneInfo(new Integer(504), "Mideast/Riyadh88");
  
  public static final ZoneInfo TZ_ID_MIDEAST_RIYADH89 = new ZoneInfo(new Integer(505), "Mideast/Riyadh89");
  
  public static final ZoneInfo TZ_ID_MST = new ZoneInfo(new Integer(506), "MST");
  
  public static final ZoneInfo TZ_ID_MST7MDT = new ZoneInfo(new Integer(507), "MST7MDT");
  
  public static final ZoneInfo TZ_ID_NAVAJO = new ZoneInfo(new Integer(508), "Navajo");
  
  public static final ZoneInfo TZ_ID_NZ = new ZoneInfo(new Integer(509), "NZ");
  
  public static final ZoneInfo TZ_ID_NZ_CHAT = new ZoneInfo(new Integer(510), "NZ-CHAT");
  
  public static final ZoneInfo TZ_ID_PACIFIC_APIA = new ZoneInfo(new Integer(511), "Pacific/Apia");
  
  public static final ZoneInfo TZ_ID_PACIFIC_AUCKLAND = new ZoneInfo(new Integer(512), "Pacific/Auckland");
  
  public static final ZoneInfo TZ_ID_PACIFIC_CHATHAM = new ZoneInfo(new Integer(513), "Pacific/Chatham");
  
  public static final ZoneInfo TZ_ID_PACIFIC_CHUUK = new ZoneInfo(new Integer(514), "Pacific/Chuuk");
  
  public static final ZoneInfo TZ_ID_PACIFIC_EASTER = new ZoneInfo(new Integer(515), "Pacific/Easter");
  
  public static final ZoneInfo TZ_ID_PACIFIC_EFATE = new ZoneInfo(new Integer(516), "Pacific/Efate");
  
  public static final ZoneInfo TZ_ID_PACIFIC_ENDERBURY = new ZoneInfo(new Integer(517), "Pacific/Enderbury");
  
  public static final ZoneInfo TZ_ID_PACIFIC_FAKAOFO = new ZoneInfo(new Integer(518), "Pacific/Fakaofo");
  
  public static final ZoneInfo TZ_ID_PACIFIC_FIJI = new ZoneInfo(new Integer(519), "Pacific/Fiji");
  
  public static final ZoneInfo TZ_ID_PACIFIC_FUNAFUTI = new ZoneInfo(new Integer(520), "Pacific/Funafuti");
  
  public static final ZoneInfo TZ_ID_PACIFIC_GALAPAGOS = new ZoneInfo(new Integer(521), "Pacific/Galapagos");
  
  public static final ZoneInfo TZ_ID_PACIFIC_GAMBIER = new ZoneInfo(new Integer(522), "Pacific/Gambier");
  
  public static final ZoneInfo TZ_ID_PACIFIC_GUADALCANAL = new ZoneInfo(new Integer(523), "Pacific/Guadalcanal");
  
  public static final ZoneInfo TZ_ID_PACIFIC_GUAM = new ZoneInfo(new Integer(524), "Pacific/Guam");
  
  public static final ZoneInfo TZ_ID_PACIFIC_HONOLULU = new ZoneInfo(new Integer(525), "Pacific/Honolulu");
  
  public static final ZoneInfo TZ_ID_PACIFIC_JOHNSTON = new ZoneInfo(new Integer(526), "Pacific/Johnston");
  
  public static final ZoneInfo TZ_ID_PACIFIC_KIRITIMATI = new ZoneInfo(new Integer(527), "Pacific/Kiritimati");
  
  public static final ZoneInfo TZ_ID_PACIFIC_KOSRAE = new ZoneInfo(new Integer(528), "Pacific/Kosrae");
  
  public static final ZoneInfo TZ_ID_PACIFIC_KWAJALEIN = new ZoneInfo(new Integer(529), "Pacific/Kwajalein");
  
  public static final ZoneInfo TZ_ID_PACIFIC_MAJURO = new ZoneInfo(new Integer(530), "Pacific/Majuro");
  
  public static final ZoneInfo TZ_ID_PACIFIC_MARQUESAS = new ZoneInfo(new Integer(531), "Pacific/Marquesas");
  
  public static final ZoneInfo TZ_ID_PACIFIC_MIDWAY = new ZoneInfo(new Integer(532), "Pacific/Midway");
  
  public static final ZoneInfo TZ_ID_PACIFIC_NAURU = new ZoneInfo(new Integer(533), "Pacific/Nauru");
  
  public static final ZoneInfo TZ_ID_PACIFIC_NIUE = new ZoneInfo(new Integer(534), "Pacific/Niue");
  
  public static final ZoneInfo TZ_ID_PACIFIC_NORFOLK = new ZoneInfo(new Integer(535), "Pacific/Norfolk");
  
  public static final ZoneInfo TZ_ID_PACIFIC_NOUMEA = new ZoneInfo(new Integer(536), "Pacific/Noumea");
  
  public static final ZoneInfo TZ_ID_PACIFIC_PAGO_PAGO = new ZoneInfo(new Integer(537), "Pacific/Pago_Pago");
  
  public static final ZoneInfo TZ_ID_PACIFIC_PALAU = new ZoneInfo(new Integer(538), "Pacific/Palau");
  
  public static final ZoneInfo TZ_ID_PACIFIC_PITCAIRN = new ZoneInfo(new Integer(539), "Pacific/Pitcairn");
  
  public static final ZoneInfo TZ_ID_PACIFIC_POHNPEI = new ZoneInfo(new Integer(540), "Pacific/Pohnpei");
  
  public static final ZoneInfo TZ_ID_PACIFIC_PONAPE = new ZoneInfo(new Integer(541), "Pacific/Ponape");
  
  public static final ZoneInfo TZ_ID_PACIFIC_PORT_MORESBY = new ZoneInfo(new Integer(542), "Pacific/Port_Moresby");
  
  public static final ZoneInfo TZ_ID_PACIFIC_RAROTONGA = new ZoneInfo(new Integer(543), "Pacific/Rarotonga");
  
  public static final ZoneInfo TZ_ID_PACIFIC_SAIPAN = new ZoneInfo(new Integer(544), "Pacific/Saipan");
  
  public static final ZoneInfo TZ_ID_PACIFIC_SAMOA = new ZoneInfo(new Integer(545), "Pacific/Samoa");
  
  public static final ZoneInfo TZ_ID_PACIFIC_TAHITI = new ZoneInfo(new Integer(546), "Pacific/Tahiti");
  
  public static final ZoneInfo TZ_ID_PACIFIC_TARAWA = new ZoneInfo(new Integer(547), "Pacific/Tarawa");
  
  public static final ZoneInfo TZ_ID_PACIFIC_TONGATAPU = new ZoneInfo(new Integer(548), "Pacific/Tongatapu");
  
  public static final ZoneInfo TZ_ID_PACIFIC_TRUK = new ZoneInfo(new Integer(549), "Pacific/Truk");
  
  public static final ZoneInfo TZ_ID_PACIFIC_WAKE = new ZoneInfo(new Integer(550), "Pacific/Wake");
  
  public static final ZoneInfo TZ_ID_PACIFIC_WALLIS = new ZoneInfo(new Integer(551), "Pacific/Wallis");
  
  public static final ZoneInfo TZ_ID_PACIFIC_YAP = new ZoneInfo(new Integer(552), "Pacific/Yap");
  
  public static final ZoneInfo TZ_ID_POLAND = new ZoneInfo(new Integer(553), "Poland");
  
  public static final ZoneInfo TZ_ID_PORTUGAL = new ZoneInfo(new Integer(554), "Portugal");
  
  public static final ZoneInfo TZ_ID_PRC = new ZoneInfo(new Integer(555), "PRC");
  
  public static final ZoneInfo TZ_ID_PST8PDT = new ZoneInfo(new Integer(556), "PST8PDT");
  
  public static final ZoneInfo TZ_ID_ROC = new ZoneInfo(new Integer(557), "ROC");
  
  public static final ZoneInfo TZ_ID_ROK = new ZoneInfo(new Integer(558), "ROK");
  
  public static final ZoneInfo TZ_ID_SINGAPORE = new ZoneInfo(new Integer(559), "Singapore");
  
  public static final ZoneInfo TZ_ID_TURKEY = new ZoneInfo(new Integer(560), "Turkey");
  
  public static final ZoneInfo TZ_ID_UCT = new ZoneInfo(new Integer(561), "UCT");
  
  public static final ZoneInfo TZ_ID_UNIVERSAL = new ZoneInfo(new Integer(562), "Universal");
  
  public static final ZoneInfo TZ_ID_US_ALASKA = new ZoneInfo(new Integer(563), "US/Alaska");
  
  public static final ZoneInfo TZ_ID_US_ALEUTIAN = new ZoneInfo(new Integer(564), "US/Aleutian");
  
  public static final ZoneInfo TZ_ID_US_ARIZONA = new ZoneInfo(new Integer(565), "US/Arizona");
  
  public static final ZoneInfo TZ_ID_US_CENTRAL = new ZoneInfo(new Integer(566), "US/Central");
  
  public static final ZoneInfo TZ_ID_US_EAST_INDIANA = new ZoneInfo(new Integer(567), "US/East-Indiana");
  
  public static final ZoneInfo TZ_ID_US_EASTERN = new ZoneInfo(new Integer(568), "US/Eastern");
  
  public static final ZoneInfo TZ_ID_US_HAWAII = new ZoneInfo(new Integer(569), "US/Hawaii");
  
  public static final ZoneInfo TZ_ID_US_INDIANA_STARKE = new ZoneInfo(new Integer(570), "US/Indiana-Starke");
  
  public static final ZoneInfo TZ_ID_US_MICHIGAN = new ZoneInfo(new Integer(571), "US/Michigan");
  
  public static final ZoneInfo TZ_ID_US_MOUNTAIN = new ZoneInfo(new Integer(572), "US/Mountain");
  
  public static final ZoneInfo TZ_ID_US_PACIFIC = new ZoneInfo(new Integer(573), "US/Pacific");
  
  public static final ZoneInfo TZ_ID_US_PACIFIC_NEW = new ZoneInfo(new Integer(574), "US/Pacific-New");
  
  public static final ZoneInfo TZ_ID_US_SAMOA = new ZoneInfo(new Integer(575), "US/Samoa");
  
  public static final ZoneInfo TZ_ID_UTC = new ZoneInfo(new Integer(576), "UTC");
  
  public static final ZoneInfo TZ_ID_W_SU = new ZoneInfo(new Integer(577), "W-SU");
  
  public static final ZoneInfo TZ_ID_WET = new ZoneInfo(new Integer(578), "WET");
  
  public static final ZoneInfo TZ_ID_ZULU = new ZoneInfo(new Integer(579), "Zulu");
  
  public static final ZoneInfo TZ_ID_EXT_PST = new ZoneInfo(new Integer(580), "PST");
  
  public static final ZoneInfo TZ_ID_EXT_MAX = new ZoneInfo(new Integer(581), "TZ_ID_EXT_MAX");
  
  public static final ZoneInfo TZ_ID_DEFAULT = new ZoneInfo(new Integer(581), "TZ_ID_DEFAULT");
  
  public static final ZoneInfo TZ_ID_OFFSET = new ZoneInfo(new Integer(582), "TZ_ID_OFFSET");
  
  public ZoneInfo(Integer paramInteger, String paramString) {
    this.id = paramInteger;
    this.name = paramString;
  }
  
  public static String getZoneNameById(int paramInt) {
    ZoneInfo zoneInfo = (ZoneInfo)zoneTabs.get(new Integer(paramInt));
    return (zoneInfo == null) ? TZ_ID_OFFSET.getName() : zoneInfo.getName();
  }
  
  public static int getTimeZoneIdByName(String paramString) {
    ZoneInfo zoneInfo = (ZoneInfo)zoneTabs.get(paramString);
    return (zoneInfo == null) ? TZ_ID_OFFSET.getId().intValue() : zoneInfo.getId().intValue();
  }
  
  public static String convertStandardTimeZoneID(String paramString) {
    return (paramString == null || paramString.length() == 0) ? paramString : (paramString.equals("GMT") ? TZ_ID_ETC_GMT.getName() : (paramString.equals("GMT+00:00") ? TZ_ID_ETC_GMT0.getName() : (paramString.equals("GMT+01:00") ? TZ_ID_ETC_GMTE1.getName() : (paramString.equals("GMT+02:00") ? TZ_ID_ETC_GMTE2.getName() : (paramString.equals("GMT+03:00") ? TZ_ID_ETC_GMTE3.getName() : (paramString.equals("GMT+04:00") ? TZ_ID_ETC_GMTE4.getName() : (paramString.equals("GMT+05:00") ? TZ_ID_ETC_GMTE5.getName() : (paramString.equals("GMT+06:00") ? TZ_ID_ETC_GMTE6.getName() : (paramString.equals("GMT+07:00") ? TZ_ID_ETC_GMTE7.getName() : (paramString.equals("GMT+08:00") ? TZ_ID_ETC_GMTE8.getName() : (paramString.equals("GMT+09:00") ? TZ_ID_ETC_GMTE9.getName() : (paramString.equals("GMT+10:00") ? TZ_ID_ETC_GMTE10.getName() : (paramString.equals("GMT+11:00") ? TZ_ID_ETC_GMTE11.getName() : (paramString.equals("GMT+12:00") ? TZ_ID_ETC_GMTE12.getName() : (paramString.equals("GMT+13:00") ? TZ_ID_ETC_GMTE13.getName() : (paramString.equals("GMT+14:00") ? TZ_ID_ETC_GMTE14.getName() : (paramString.equals("GMT-00:00") ? TZ_ID_ETC_GMTW0.getName() : (paramString.equals("GMT-01:00") ? TZ_ID_ETC_GMTW1.getName() : (paramString.equals("GMT-02:00") ? TZ_ID_ETC_GMTW2.getName() : (paramString.equals("GMT-03:00") ? TZ_ID_ETC_GMTW3.getName() : (paramString.equals("GMT-04:00") ? TZ_ID_ETC_GMTW4.getName() : (paramString.equals("GMT-05:00") ? TZ_ID_ETC_GMTW5.getName() : (paramString.equals("GMT-06:00") ? TZ_ID_ETC_GMTW6.getName() : (paramString.equals("GMT-07:00") ? TZ_ID_ETC_GMTW7.getName() : (paramString.equals("GMT-08:00") ? TZ_ID_ETC_GMTW8.getName() : (paramString.equals("GMT-09:00") ? TZ_ID_ETC_GMTW9.getName() : (paramString.equals("GMT-10:00") ? TZ_ID_ETC_GMTW10.getName() : (paramString.equals("GMT-11:00") ? TZ_ID_ETC_GMTW11.getName() : (paramString.equals("GMT-12:00") ? TZ_ID_ETC_GMTW12.getName() : paramString)))))))))))))))))))))))))))));
  }
  
  public Integer getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  static {
    zoneTabs.put(TZ_ID_AFRICA_ABIDJAN.getId(), TZ_ID_AFRICA_ABIDJAN);
    zoneTabs.put(TZ_ID_AFRICA_ACCRA.getId(), TZ_ID_AFRICA_ACCRA);
    zoneTabs.put(TZ_ID_AFRICA_ADDIS_ABABA.getId(), TZ_ID_AFRICA_ADDIS_ABABA);
    zoneTabs.put(TZ_ID_AFRICA_ALGIERS.getId(), TZ_ID_AFRICA_ALGIERS);
    zoneTabs.put(TZ_ID_AFRICA_ASMARA.getId(), TZ_ID_AFRICA_ASMARA);
    zoneTabs.put(TZ_ID_AFRICA_ASMERA.getId(), TZ_ID_AFRICA_ASMERA);
    zoneTabs.put(TZ_ID_AFRICA_BAMAKO.getId(), TZ_ID_AFRICA_BAMAKO);
    zoneTabs.put(TZ_ID_AFRICA_BANGUI.getId(), TZ_ID_AFRICA_BANGUI);
    zoneTabs.put(TZ_ID_AFRICA_BANJUL.getId(), TZ_ID_AFRICA_BANJUL);
    zoneTabs.put(TZ_ID_AFRICA_BISSAU.getId(), TZ_ID_AFRICA_BISSAU);
    zoneTabs.put(TZ_ID_AFRICA_BLANTYRE.getId(), TZ_ID_AFRICA_BLANTYRE);
    zoneTabs.put(TZ_ID_AFRICA_BRAZZAVILLE.getId(), TZ_ID_AFRICA_BRAZZAVILLE);
    zoneTabs.put(TZ_ID_AFRICA_BUJUMBURA.getId(), TZ_ID_AFRICA_BUJUMBURA);
    zoneTabs.put(TZ_ID_AFRICA_CAIRO.getId(), TZ_ID_AFRICA_CAIRO);
    zoneTabs.put(TZ_ID_AFRICA_CASABLANCA.getId(), TZ_ID_AFRICA_CASABLANCA);
    zoneTabs.put(TZ_ID_AFRICA_CEUTA.getId(), TZ_ID_AFRICA_CEUTA);
    zoneTabs.put(TZ_ID_AFRICA_CONAKRY.getId(), TZ_ID_AFRICA_CONAKRY);
    zoneTabs.put(TZ_ID_AFRICA_DAKAR.getId(), TZ_ID_AFRICA_DAKAR);
    zoneTabs.put(TZ_ID_AFRICA_DAR_ES_SALAAM.getId(), TZ_ID_AFRICA_DAR_ES_SALAAM);
    zoneTabs.put(TZ_ID_AFRICA_DJIBOUTI.getId(), TZ_ID_AFRICA_DJIBOUTI);
    zoneTabs.put(TZ_ID_AFRICA_DOUALA.getId(), TZ_ID_AFRICA_DOUALA);
    zoneTabs.put(TZ_ID_AFRICA_EL_AAIUN.getId(), TZ_ID_AFRICA_EL_AAIUN);
    zoneTabs.put(TZ_ID_AFRICA_FREETOWN.getId(), TZ_ID_AFRICA_FREETOWN);
    zoneTabs.put(TZ_ID_AFRICA_GABORONE.getId(), TZ_ID_AFRICA_GABORONE);
    zoneTabs.put(TZ_ID_AFRICA_HARARE.getId(), TZ_ID_AFRICA_HARARE);
    zoneTabs.put(TZ_ID_AFRICA_JOHANNESBURG.getId(), TZ_ID_AFRICA_JOHANNESBURG);
    zoneTabs.put(TZ_ID_AFRICA_JUBA.getId(), TZ_ID_AFRICA_JUBA);
    zoneTabs.put(TZ_ID_AFRICA_KAMPALA.getId(), TZ_ID_AFRICA_KAMPALA);
    zoneTabs.put(TZ_ID_AFRICA_KHARTOUM.getId(), TZ_ID_AFRICA_KHARTOUM);
    zoneTabs.put(TZ_ID_AFRICA_KIGALI.getId(), TZ_ID_AFRICA_KIGALI);
    zoneTabs.put(TZ_ID_AFRICA_KINSHASA.getId(), TZ_ID_AFRICA_KINSHASA);
    zoneTabs.put(TZ_ID_AFRICA_LAGOS.getId(), TZ_ID_AFRICA_LAGOS);
    zoneTabs.put(TZ_ID_AFRICA_LIBREVILLE.getId(), TZ_ID_AFRICA_LIBREVILLE);
    zoneTabs.put(TZ_ID_AFRICA_LOME.getId(), TZ_ID_AFRICA_LOME);
    zoneTabs.put(TZ_ID_AFRICA_LUANDA.getId(), TZ_ID_AFRICA_LUANDA);
    zoneTabs.put(TZ_ID_AFRICA_LUBUMBASHI.getId(), TZ_ID_AFRICA_LUBUMBASHI);
    zoneTabs.put(TZ_ID_AFRICA_LUSAKA.getId(), TZ_ID_AFRICA_LUSAKA);
    zoneTabs.put(TZ_ID_AFRICA_MALABO.getId(), TZ_ID_AFRICA_MALABO);
    zoneTabs.put(TZ_ID_AFRICA_MAPUTO.getId(), TZ_ID_AFRICA_MAPUTO);
    zoneTabs.put(TZ_ID_AFRICA_MASERU.getId(), TZ_ID_AFRICA_MASERU);
    zoneTabs.put(TZ_ID_AFRICA_MBABANE.getId(), TZ_ID_AFRICA_MBABANE);
    zoneTabs.put(TZ_ID_AFRICA_MOGADISHU.getId(), TZ_ID_AFRICA_MOGADISHU);
    zoneTabs.put(TZ_ID_AFRICA_MONROVIA.getId(), TZ_ID_AFRICA_MONROVIA);
    zoneTabs.put(TZ_ID_AFRICA_NAIROBI.getId(), TZ_ID_AFRICA_NAIROBI);
    zoneTabs.put(TZ_ID_AFRICA_NDJAMENA.getId(), TZ_ID_AFRICA_NDJAMENA);
    zoneTabs.put(TZ_ID_AFRICA_NIAMEY.getId(), TZ_ID_AFRICA_NIAMEY);
    zoneTabs.put(TZ_ID_AFRICA_NOUAKCHOTT.getId(), TZ_ID_AFRICA_NOUAKCHOTT);
    zoneTabs.put(TZ_ID_AFRICA_OUAGADOUGOU.getId(), TZ_ID_AFRICA_OUAGADOUGOU);
    zoneTabs.put(TZ_ID_AFRICA_PORTO_NOVO.getId(), TZ_ID_AFRICA_PORTO_NOVO);
    zoneTabs.put(TZ_ID_AFRICA_SAO_TOME.getId(), TZ_ID_AFRICA_SAO_TOME);
    zoneTabs.put(TZ_ID_AFRICA_TIMBUKTU.getId(), TZ_ID_AFRICA_TIMBUKTU);
    zoneTabs.put(TZ_ID_AFRICA_TRIPOLI.getId(), TZ_ID_AFRICA_TRIPOLI);
    zoneTabs.put(TZ_ID_AFRICA_TUNIS.getId(), TZ_ID_AFRICA_TUNIS);
    zoneTabs.put(TZ_ID_AFRICA_WINDHOEK.getId(), TZ_ID_AFRICA_WINDHOEK);
    zoneTabs.put(TZ_ID_AMERICA_ADAK.getId(), TZ_ID_AMERICA_ADAK);
    zoneTabs.put(TZ_ID_AMERICA_ANCHORAGE.getId(), TZ_ID_AMERICA_ANCHORAGE);
    zoneTabs.put(TZ_ID_AMERICA_ANGUILLA.getId(), TZ_ID_AMERICA_ANGUILLA);
    zoneTabs.put(TZ_ID_AMERICA_ANTIGUA.getId(), TZ_ID_AMERICA_ANTIGUA);
    zoneTabs.put(TZ_ID_AMERICA_ARAGUAINA.getId(), TZ_ID_AMERICA_ARAGUAINA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_BUENOS_AIRES.getId(), TZ_ID_AMERICA_ARGENTINA_BUENOS_AIRES);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_CATAMARCA.getId(), TZ_ID_AMERICA_ARGENTINA_CATAMARCA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_COMODRIVADAVIA.getId(), TZ_ID_AMERICA_ARGENTINA_COMODRIVADAVIA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_CORDOBA.getId(), TZ_ID_AMERICA_ARGENTINA_CORDOBA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_JUJUY.getId(), TZ_ID_AMERICA_ARGENTINA_JUJUY);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_LA_RIOJA.getId(), TZ_ID_AMERICA_ARGENTINA_LA_RIOJA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_MENDOZA.getId(), TZ_ID_AMERICA_ARGENTINA_MENDOZA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_RIO_GALLEGOS.getId(), TZ_ID_AMERICA_ARGENTINA_RIO_GALLEGOS);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_SALTA.getId(), TZ_ID_AMERICA_ARGENTINA_SALTA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_SAN_JUAN.getId(), TZ_ID_AMERICA_ARGENTINA_SAN_JUAN);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_SAN_LUIS.getId(), TZ_ID_AMERICA_ARGENTINA_SAN_LUIS);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_TUCUMAN.getId(), TZ_ID_AMERICA_ARGENTINA_TUCUMAN);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_USHUAIA.getId(), TZ_ID_AMERICA_ARGENTINA_USHUAIA);
    zoneTabs.put(TZ_ID_AMERICA_ARUBA.getId(), TZ_ID_AMERICA_ARUBA);
    zoneTabs.put(TZ_ID_AMERICA_ASUNCION.getId(), TZ_ID_AMERICA_ASUNCION);
    zoneTabs.put(TZ_ID_AMERICA_ATIKOKAN.getId(), TZ_ID_AMERICA_ATIKOKAN);
    zoneTabs.put(TZ_ID_AMERICA_ATKA.getId(), TZ_ID_AMERICA_ATKA);
    zoneTabs.put(TZ_ID_AMERICA_BAHIA.getId(), TZ_ID_AMERICA_BAHIA);
    zoneTabs.put(TZ_ID_AMERICA_BAHIA_BANDERAS.getId(), TZ_ID_AMERICA_BAHIA_BANDERAS);
    zoneTabs.put(TZ_ID_AMERICA_BARBADOS.getId(), TZ_ID_AMERICA_BARBADOS);
    zoneTabs.put(TZ_ID_AMERICA_BELEM.getId(), TZ_ID_AMERICA_BELEM);
    zoneTabs.put(TZ_ID_AMERICA_BELIZE.getId(), TZ_ID_AMERICA_BELIZE);
    zoneTabs.put(TZ_ID_AMERICA_BLANC_SABLON.getId(), TZ_ID_AMERICA_BLANC_SABLON);
    zoneTabs.put(TZ_ID_AMERICA_BOA_VISTA.getId(), TZ_ID_AMERICA_BOA_VISTA);
    zoneTabs.put(TZ_ID_AMERICA_BOGOTA.getId(), TZ_ID_AMERICA_BOGOTA);
    zoneTabs.put(TZ_ID_AMERICA_BOISE.getId(), TZ_ID_AMERICA_BOISE);
    zoneTabs.put(TZ_ID_AMERICA_BUENOS_AIRES.getId(), TZ_ID_AMERICA_BUENOS_AIRES);
    zoneTabs.put(TZ_ID_AMERICA_CAMBRIDGE_BAY.getId(), TZ_ID_AMERICA_CAMBRIDGE_BAY);
    zoneTabs.put(TZ_ID_AMERICA_CAMPO_GRANDE.getId(), TZ_ID_AMERICA_CAMPO_GRANDE);
    zoneTabs.put(TZ_ID_AMERICA_CANCUN.getId(), TZ_ID_AMERICA_CANCUN);
    zoneTabs.put(TZ_ID_AMERICA_CARACAS.getId(), TZ_ID_AMERICA_CARACAS);
    zoneTabs.put(TZ_ID_AMERICA_CATAMARCA.getId(), TZ_ID_AMERICA_CATAMARCA);
    zoneTabs.put(TZ_ID_AMERICA_CAYENNE.getId(), TZ_ID_AMERICA_CAYENNE);
    zoneTabs.put(TZ_ID_AMERICA_CAYMAN.getId(), TZ_ID_AMERICA_CAYMAN);
    zoneTabs.put(TZ_ID_AMERICA_CHICAGO.getId(), TZ_ID_AMERICA_CHICAGO);
    zoneTabs.put(TZ_ID_AMERICA_CHIHUAHUA.getId(), TZ_ID_AMERICA_CHIHUAHUA);
    zoneTabs.put(TZ_ID_AMERICA_CORAL_HARBOUR.getId(), TZ_ID_AMERICA_CORAL_HARBOUR);
    zoneTabs.put(TZ_ID_AMERICA_CORDOBA.getId(), TZ_ID_AMERICA_CORDOBA);
    zoneTabs.put(TZ_ID_AMERICA_COSTA_RICA.getId(), TZ_ID_AMERICA_COSTA_RICA);
    zoneTabs.put(TZ_ID_AMERICA_CUIABA.getId(), TZ_ID_AMERICA_CUIABA);
    zoneTabs.put(TZ_ID_AMERICA_CURACAO.getId(), TZ_ID_AMERICA_CURACAO);
    zoneTabs.put(TZ_ID_AMERICA_DANMARKSHAVN.getId(), TZ_ID_AMERICA_DANMARKSHAVN);
    zoneTabs.put(TZ_ID_AMERICA_DAWSON.getId(), TZ_ID_AMERICA_DAWSON);
    zoneTabs.put(TZ_ID_AMERICA_DAWSON_CREEK.getId(), TZ_ID_AMERICA_DAWSON_CREEK);
    zoneTabs.put(TZ_ID_AMERICA_DENVER.getId(), TZ_ID_AMERICA_DENVER);
    zoneTabs.put(TZ_ID_AMERICA_DETROIT.getId(), TZ_ID_AMERICA_DETROIT);
    zoneTabs.put(TZ_ID_AMERICA_DOMINICA.getId(), TZ_ID_AMERICA_DOMINICA);
    zoneTabs.put(TZ_ID_AMERICA_EDMONTON.getId(), TZ_ID_AMERICA_EDMONTON);
    zoneTabs.put(TZ_ID_AMERICA_EIRUNEPE.getId(), TZ_ID_AMERICA_EIRUNEPE);
    zoneTabs.put(TZ_ID_AMERICA_EL_SALVADOR.getId(), TZ_ID_AMERICA_EL_SALVADOR);
    zoneTabs.put(TZ_ID_AMERICA_ENSENADA.getId(), TZ_ID_AMERICA_ENSENADA);
    zoneTabs.put(TZ_ID_AMERICA_FORT_WAYNE.getId(), TZ_ID_AMERICA_FORT_WAYNE);
    zoneTabs.put(TZ_ID_AMERICA_FORTALEZA.getId(), TZ_ID_AMERICA_FORTALEZA);
    zoneTabs.put(TZ_ID_AMERICA_GLACE_BAY.getId(), TZ_ID_AMERICA_GLACE_BAY);
    zoneTabs.put(TZ_ID_AMERICA_GODTHAB.getId(), TZ_ID_AMERICA_GODTHAB);
    zoneTabs.put(TZ_ID_AMERICA_GOOSE_BAY.getId(), TZ_ID_AMERICA_GOOSE_BAY);
    zoneTabs.put(TZ_ID_AMERICA_GRAND_TURK.getId(), TZ_ID_AMERICA_GRAND_TURK);
    zoneTabs.put(TZ_ID_AMERICA_GRENADA.getId(), TZ_ID_AMERICA_GRENADA);
    zoneTabs.put(TZ_ID_AMERICA_GUADELOUPE.getId(), TZ_ID_AMERICA_GUADELOUPE);
    zoneTabs.put(TZ_ID_AMERICA_GUATEMALA.getId(), TZ_ID_AMERICA_GUATEMALA);
    zoneTabs.put(TZ_ID_AMERICA_GUAYAQUIL.getId(), TZ_ID_AMERICA_GUAYAQUIL);
    zoneTabs.put(TZ_ID_AMERICA_GUYANA.getId(), TZ_ID_AMERICA_GUYANA);
    zoneTabs.put(TZ_ID_AMERICA_HALIFAX.getId(), TZ_ID_AMERICA_HALIFAX);
    zoneTabs.put(TZ_ID_AMERICA_HAVANA.getId(), TZ_ID_AMERICA_HAVANA);
    zoneTabs.put(TZ_ID_AMERICA_HERMOSILLO.getId(), TZ_ID_AMERICA_HERMOSILLO);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_INDIANAPOLIS.getId(), TZ_ID_AMERICA_INDIANA_INDIANAPOLIS);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_KNOX.getId(), TZ_ID_AMERICA_INDIANA_KNOX);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_MARENGO.getId(), TZ_ID_AMERICA_INDIANA_MARENGO);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_PETERSBURG.getId(), TZ_ID_AMERICA_INDIANA_PETERSBURG);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_TELL_CITY.getId(), TZ_ID_AMERICA_INDIANA_TELL_CITY);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_VEVAY.getId(), TZ_ID_AMERICA_INDIANA_VEVAY);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_VINCENNES.getId(), TZ_ID_AMERICA_INDIANA_VINCENNES);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_WINAMAC.getId(), TZ_ID_AMERICA_INDIANA_WINAMAC);
    zoneTabs.put(TZ_ID_AMERICA_INDIANAPOLIS.getId(), TZ_ID_AMERICA_INDIANAPOLIS);
    zoneTabs.put(TZ_ID_AMERICA_INUVIK.getId(), TZ_ID_AMERICA_INUVIK);
    zoneTabs.put(TZ_ID_AMERICA_IQALUIT.getId(), TZ_ID_AMERICA_IQALUIT);
    zoneTabs.put(TZ_ID_AMERICA_JAMAICA.getId(), TZ_ID_AMERICA_JAMAICA);
    zoneTabs.put(TZ_ID_AMERICA_JUJUY.getId(), TZ_ID_AMERICA_JUJUY);
    zoneTabs.put(TZ_ID_AMERICA_JUNEAU.getId(), TZ_ID_AMERICA_JUNEAU);
    zoneTabs.put(TZ_ID_AMERICA_KENTUCKY_LOUISVILLE.getId(), TZ_ID_AMERICA_KENTUCKY_LOUISVILLE);
    zoneTabs.put(TZ_ID_AMERICA_KENTUCKY_MONTICELLO.getId(), TZ_ID_AMERICA_KENTUCKY_MONTICELLO);
    zoneTabs.put(TZ_ID_AMERICA_KNOX_IN.getId(), TZ_ID_AMERICA_KNOX_IN);
    zoneTabs.put(TZ_ID_AMERICA_KRALENDIJK.getId(), TZ_ID_AMERICA_KRALENDIJK);
    zoneTabs.put(TZ_ID_AMERICA_LA_PAZ.getId(), TZ_ID_AMERICA_LA_PAZ);
    zoneTabs.put(TZ_ID_AMERICA_LIMA.getId(), TZ_ID_AMERICA_LIMA);
    zoneTabs.put(TZ_ID_AMERICA_LOS_ANGELES.getId(), TZ_ID_AMERICA_LOS_ANGELES);
    zoneTabs.put(TZ_ID_AMERICA_LOUISVILLE.getId(), TZ_ID_AMERICA_LOUISVILLE);
    zoneTabs.put(TZ_ID_AMERICA_LOWER_PRINCES.getId(), TZ_ID_AMERICA_LOWER_PRINCES);
    zoneTabs.put(TZ_ID_AMERICA_MACEIO.getId(), TZ_ID_AMERICA_MACEIO);
    zoneTabs.put(TZ_ID_AMERICA_MANAGUA.getId(), TZ_ID_AMERICA_MANAGUA);
    zoneTabs.put(TZ_ID_AMERICA_MANAUS.getId(), TZ_ID_AMERICA_MANAUS);
    zoneTabs.put(TZ_ID_AMERICA_MARIGOT.getId(), TZ_ID_AMERICA_MARIGOT);
    zoneTabs.put(TZ_ID_AMERICA_MARTINIQUE.getId(), TZ_ID_AMERICA_MARTINIQUE);
    zoneTabs.put(TZ_ID_AMERICA_MATAMOROS.getId(), TZ_ID_AMERICA_MATAMOROS);
    zoneTabs.put(TZ_ID_AMERICA_MAZATLAN.getId(), TZ_ID_AMERICA_MAZATLAN);
    zoneTabs.put(TZ_ID_AMERICA_MENDOZA.getId(), TZ_ID_AMERICA_MENDOZA);
    zoneTabs.put(TZ_ID_AMERICA_MENOMINEE.getId(), TZ_ID_AMERICA_MENOMINEE);
    zoneTabs.put(TZ_ID_AMERICA_MERIDA.getId(), TZ_ID_AMERICA_MERIDA);
    zoneTabs.put(TZ_ID_AMERICA_METLAKATLA.getId(), TZ_ID_AMERICA_METLAKATLA);
    zoneTabs.put(TZ_ID_AMERICA_MEXICO_CITY.getId(), TZ_ID_AMERICA_MEXICO_CITY);
    zoneTabs.put(TZ_ID_AMERICA_MIQUELON.getId(), TZ_ID_AMERICA_MIQUELON);
    zoneTabs.put(TZ_ID_AMERICA_MONCTON.getId(), TZ_ID_AMERICA_MONCTON);
    zoneTabs.put(TZ_ID_AMERICA_MONTERREY.getId(), TZ_ID_AMERICA_MONTERREY);
    zoneTabs.put(TZ_ID_AMERICA_MONTEVIDEO.getId(), TZ_ID_AMERICA_MONTEVIDEO);
    zoneTabs.put(TZ_ID_AMERICA_MONTREAL.getId(), TZ_ID_AMERICA_MONTREAL);
    zoneTabs.put(TZ_ID_AMERICA_MONTSERRAT.getId(), TZ_ID_AMERICA_MONTSERRAT);
    zoneTabs.put(TZ_ID_AMERICA_NASSAU.getId(), TZ_ID_AMERICA_NASSAU);
    zoneTabs.put(TZ_ID_AMERICA_NEW_YORK.getId(), TZ_ID_AMERICA_NEW_YORK);
    zoneTabs.put(TZ_ID_AMERICA_NIPIGON.getId(), TZ_ID_AMERICA_NIPIGON);
    zoneTabs.put(TZ_ID_AMERICA_NOME.getId(), TZ_ID_AMERICA_NOME);
    zoneTabs.put(TZ_ID_AMERICA_NORONHA.getId(), TZ_ID_AMERICA_NORONHA);
    zoneTabs.put(TZ_ID_AMERICA_NORTH_DAKOTA_BEULAH.getId(), TZ_ID_AMERICA_NORTH_DAKOTA_BEULAH);
    zoneTabs.put(TZ_ID_AMERICA_NORTH_DAKOTA_CENTER.getId(), TZ_ID_AMERICA_NORTH_DAKOTA_CENTER);
    zoneTabs.put(TZ_ID_AMERICA_NORTH_DAKOTA_NEW_SALEM.getId(), TZ_ID_AMERICA_NORTH_DAKOTA_NEW_SALEM);
    zoneTabs.put(TZ_ID_AMERICA_OJINAGA.getId(), TZ_ID_AMERICA_OJINAGA);
    zoneTabs.put(TZ_ID_AMERICA_PANAMA.getId(), TZ_ID_AMERICA_PANAMA);
    zoneTabs.put(TZ_ID_AMERICA_PANGNIRTUNG.getId(), TZ_ID_AMERICA_PANGNIRTUNG);
    zoneTabs.put(TZ_ID_AMERICA_PARAMARIBO.getId(), TZ_ID_AMERICA_PARAMARIBO);
    zoneTabs.put(TZ_ID_AMERICA_PHOENIX.getId(), TZ_ID_AMERICA_PHOENIX);
    zoneTabs.put(TZ_ID_AMERICA_PORT_AU_PRINCE.getId(), TZ_ID_AMERICA_PORT_AU_PRINCE);
    zoneTabs.put(TZ_ID_AMERICA_PORT_OF_SPAIN.getId(), TZ_ID_AMERICA_PORT_OF_SPAIN);
    zoneTabs.put(TZ_ID_AMERICA_PORTO_ACRE.getId(), TZ_ID_AMERICA_PORTO_ACRE);
    zoneTabs.put(TZ_ID_AMERICA_PORTO_VELHO.getId(), TZ_ID_AMERICA_PORTO_VELHO);
    zoneTabs.put(TZ_ID_AMERICA_PUERTO_RICO.getId(), TZ_ID_AMERICA_PUERTO_RICO);
    zoneTabs.put(TZ_ID_AMERICA_RAINY_RIVER.getId(), TZ_ID_AMERICA_RAINY_RIVER);
    zoneTabs.put(TZ_ID_AMERICA_RANKIN_INLET.getId(), TZ_ID_AMERICA_RANKIN_INLET);
    zoneTabs.put(TZ_ID_AMERICA_RECIFE.getId(), TZ_ID_AMERICA_RECIFE);
    zoneTabs.put(TZ_ID_AMERICA_REGINA.getId(), TZ_ID_AMERICA_REGINA);
    zoneTabs.put(TZ_ID_AMERICA_RESOLUTE.getId(), TZ_ID_AMERICA_RESOLUTE);
    zoneTabs.put(TZ_ID_AMERICA_RIO_BRANCO.getId(), TZ_ID_AMERICA_RIO_BRANCO);
    zoneTabs.put(TZ_ID_AMERICA_ROSARIO.getId(), TZ_ID_AMERICA_ROSARIO);
    zoneTabs.put(TZ_ID_AMERICA_SANTA_ISABEL.getId(), TZ_ID_AMERICA_SANTA_ISABEL);
    zoneTabs.put(TZ_ID_AMERICA_SANTAREM.getId(), TZ_ID_AMERICA_SANTAREM);
    zoneTabs.put(TZ_ID_AMERICA_SANTIAGO.getId(), TZ_ID_AMERICA_SANTIAGO);
    zoneTabs.put(TZ_ID_AMERICA_SANTO_DOMINGO.getId(), TZ_ID_AMERICA_SANTO_DOMINGO);
    zoneTabs.put(TZ_ID_AMERICA_SAO_PAULO.getId(), TZ_ID_AMERICA_SAO_PAULO);
    zoneTabs.put(TZ_ID_AMERICA_SCORESBYSUND.getId(), TZ_ID_AMERICA_SCORESBYSUND);
    zoneTabs.put(TZ_ID_AMERICA_SHIPROCK.getId(), TZ_ID_AMERICA_SHIPROCK);
    zoneTabs.put(TZ_ID_AMERICA_SITKA.getId(), TZ_ID_AMERICA_SITKA);
    zoneTabs.put(TZ_ID_AMERICA_ST_BARTHELEMY.getId(), TZ_ID_AMERICA_ST_BARTHELEMY);
    zoneTabs.put(TZ_ID_AMERICA_ST_JOHNS.getId(), TZ_ID_AMERICA_ST_JOHNS);
    zoneTabs.put(TZ_ID_AMERICA_ST_KITTS.getId(), TZ_ID_AMERICA_ST_KITTS);
    zoneTabs.put(TZ_ID_AMERICA_ST_LUCIA.getId(), TZ_ID_AMERICA_ST_LUCIA);
    zoneTabs.put(TZ_ID_AMERICA_ST_THOMAS.getId(), TZ_ID_AMERICA_ST_THOMAS);
    zoneTabs.put(TZ_ID_AMERICA_ST_VINCENT.getId(), TZ_ID_AMERICA_ST_VINCENT);
    zoneTabs.put(TZ_ID_AMERICA_SWIFT_CURRENT.getId(), TZ_ID_AMERICA_SWIFT_CURRENT);
    zoneTabs.put(TZ_ID_AMERICA_TEGUCIGALPA.getId(), TZ_ID_AMERICA_TEGUCIGALPA);
    zoneTabs.put(TZ_ID_AMERICA_THULE.getId(), TZ_ID_AMERICA_THULE);
    zoneTabs.put(TZ_ID_AMERICA_THUNDER_BAY.getId(), TZ_ID_AMERICA_THUNDER_BAY);
    zoneTabs.put(TZ_ID_AMERICA_TIJUANA.getId(), TZ_ID_AMERICA_TIJUANA);
    zoneTabs.put(TZ_ID_AMERICA_TORONTO.getId(), TZ_ID_AMERICA_TORONTO);
    zoneTabs.put(TZ_ID_AMERICA_TORTOLA.getId(), TZ_ID_AMERICA_TORTOLA);
    zoneTabs.put(TZ_ID_AMERICA_VANCOUVER.getId(), TZ_ID_AMERICA_VANCOUVER);
    zoneTabs.put(TZ_ID_AMERICA_VIRGIN.getId(), TZ_ID_AMERICA_VIRGIN);
    zoneTabs.put(TZ_ID_AMERICA_WHITEHORSE.getId(), TZ_ID_AMERICA_WHITEHORSE);
    zoneTabs.put(TZ_ID_AMERICA_WINNIPEG.getId(), TZ_ID_AMERICA_WINNIPEG);
    zoneTabs.put(TZ_ID_AMERICA_YAKUTAT.getId(), TZ_ID_AMERICA_YAKUTAT);
    zoneTabs.put(TZ_ID_AMERICA_YELLOWKNIFE.getId(), TZ_ID_AMERICA_YELLOWKNIFE);
    zoneTabs.put(TZ_ID_ANTARCTICA_CASEY.getId(), TZ_ID_ANTARCTICA_CASEY);
    zoneTabs.put(TZ_ID_ANTARCTICA_DAVIS.getId(), TZ_ID_ANTARCTICA_DAVIS);
    zoneTabs.put(TZ_ID_ANTARCTICA_DUMONTDURVILLE.getId(), TZ_ID_ANTARCTICA_DUMONTDURVILLE);
    zoneTabs.put(TZ_ID_ANTARCTICA_MACQUARIE.getId(), TZ_ID_ANTARCTICA_MACQUARIE);
    zoneTabs.put(TZ_ID_ANTARCTICA_MAWSON.getId(), TZ_ID_ANTARCTICA_MAWSON);
    zoneTabs.put(TZ_ID_ANTARCTICA_MCMURDO.getId(), TZ_ID_ANTARCTICA_MCMURDO);
    zoneTabs.put(TZ_ID_ANTARCTICA_PALMER.getId(), TZ_ID_ANTARCTICA_PALMER);
    zoneTabs.put(TZ_ID_ANTARCTICA_ROTHERA.getId(), TZ_ID_ANTARCTICA_ROTHERA);
    zoneTabs.put(TZ_ID_ANTARCTICA_SOUTH_POLE.getId(), TZ_ID_ANTARCTICA_SOUTH_POLE);
    zoneTabs.put(TZ_ID_ANTARCTICA_SYOWA.getId(), TZ_ID_ANTARCTICA_SYOWA);
    zoneTabs.put(TZ_ID_ANTARCTICA_VOSTOK.getId(), TZ_ID_ANTARCTICA_VOSTOK);
    zoneTabs.put(TZ_ID_ARCTIC_LONGYEARBYEN.getId(), TZ_ID_ARCTIC_LONGYEARBYEN);
    zoneTabs.put(TZ_ID_ASIA_ADEN.getId(), TZ_ID_ASIA_ADEN);
    zoneTabs.put(TZ_ID_ASIA_ALMATY.getId(), TZ_ID_ASIA_ALMATY);
    zoneTabs.put(TZ_ID_ASIA_AMMAN.getId(), TZ_ID_ASIA_AMMAN);
    zoneTabs.put(TZ_ID_ASIA_ANADYR.getId(), TZ_ID_ASIA_ANADYR);
    zoneTabs.put(TZ_ID_ASIA_AQTAU.getId(), TZ_ID_ASIA_AQTAU);
    zoneTabs.put(TZ_ID_ASIA_AQTOBE.getId(), TZ_ID_ASIA_AQTOBE);
    zoneTabs.put(TZ_ID_ASIA_ASHGABAT.getId(), TZ_ID_ASIA_ASHGABAT);
    zoneTabs.put(TZ_ID_ASIA_ASHKHABAD.getId(), TZ_ID_ASIA_ASHKHABAD);
    zoneTabs.put(TZ_ID_ASIA_BAGHDAD.getId(), TZ_ID_ASIA_BAGHDAD);
    zoneTabs.put(TZ_ID_ASIA_BAHRAIN.getId(), TZ_ID_ASIA_BAHRAIN);
    zoneTabs.put(TZ_ID_ASIA_BAKU.getId(), TZ_ID_ASIA_BAKU);
    zoneTabs.put(TZ_ID_ASIA_BANGKOK.getId(), TZ_ID_ASIA_BANGKOK);
    zoneTabs.put(TZ_ID_ASIA_BEIRUT.getId(), TZ_ID_ASIA_BEIRUT);
    zoneTabs.put(TZ_ID_ASIA_BISHKEK.getId(), TZ_ID_ASIA_BISHKEK);
    zoneTabs.put(TZ_ID_ASIA_BRUNEI.getId(), TZ_ID_ASIA_BRUNEI);
    zoneTabs.put(TZ_ID_ASIA_CALCUTTA.getId(), TZ_ID_ASIA_CALCUTTA);
    zoneTabs.put(TZ_ID_ASIA_CHOIBALSAN.getId(), TZ_ID_ASIA_CHOIBALSAN);
    zoneTabs.put(TZ_ID_ASIA_CHONGQING.getId(), TZ_ID_ASIA_CHONGQING);
    zoneTabs.put(TZ_ID_ASIA_CHUNGKING.getId(), TZ_ID_ASIA_CHUNGKING);
    zoneTabs.put(TZ_ID_ASIA_COLOMBO.getId(), TZ_ID_ASIA_COLOMBO);
    zoneTabs.put(TZ_ID_ASIA_DACCA.getId(), TZ_ID_ASIA_DACCA);
    zoneTabs.put(TZ_ID_ASIA_DAMASCUS.getId(), TZ_ID_ASIA_DAMASCUS);
    zoneTabs.put(TZ_ID_ASIA_DHAKA.getId(), TZ_ID_ASIA_DHAKA);
    zoneTabs.put(TZ_ID_ASIA_DILI.getId(), TZ_ID_ASIA_DILI);
    zoneTabs.put(TZ_ID_ASIA_DUBAI.getId(), TZ_ID_ASIA_DUBAI);
    zoneTabs.put(TZ_ID_ASIA_DUSHANBE.getId(), TZ_ID_ASIA_DUSHANBE);
    zoneTabs.put(TZ_ID_ASIA_GAZA.getId(), TZ_ID_ASIA_GAZA);
    zoneTabs.put(TZ_ID_ASIA_HARBIN.getId(), TZ_ID_ASIA_HARBIN);
    zoneTabs.put(TZ_ID_ASIA_HEBRON.getId(), TZ_ID_ASIA_HEBRON);
    zoneTabs.put(TZ_ID_ASIA_HO_CHI_MINH.getId(), TZ_ID_ASIA_HO_CHI_MINH);
    zoneTabs.put(TZ_ID_ASIA_HONG_KONG.getId(), TZ_ID_ASIA_HONG_KONG);
    zoneTabs.put(TZ_ID_ASIA_HOVD.getId(), TZ_ID_ASIA_HOVD);
    zoneTabs.put(TZ_ID_ASIA_IRKUTSK.getId(), TZ_ID_ASIA_IRKUTSK);
    zoneTabs.put(TZ_ID_ASIA_ISTANBUL.getId(), TZ_ID_ASIA_ISTANBUL);
    zoneTabs.put(TZ_ID_ASIA_JAKARTA.getId(), TZ_ID_ASIA_JAKARTA);
    zoneTabs.put(TZ_ID_ASIA_JAYAPURA.getId(), TZ_ID_ASIA_JAYAPURA);
    zoneTabs.put(TZ_ID_ASIA_JERUSALEM.getId(), TZ_ID_ASIA_JERUSALEM);
    zoneTabs.put(TZ_ID_ASIA_KABUL.getId(), TZ_ID_ASIA_KABUL);
    zoneTabs.put(TZ_ID_ASIA_KAMCHATKA.getId(), TZ_ID_ASIA_KAMCHATKA);
    zoneTabs.put(TZ_ID_ASIA_KARACHI.getId(), TZ_ID_ASIA_KARACHI);
    zoneTabs.put(TZ_ID_ASIA_KASHGAR.getId(), TZ_ID_ASIA_KASHGAR);
    zoneTabs.put(TZ_ID_ASIA_KATHMANDU.getId(), TZ_ID_ASIA_KATHMANDU);
    zoneTabs.put(TZ_ID_ASIA_KATMANDU.getId(), TZ_ID_ASIA_KATMANDU);
    zoneTabs.put(TZ_ID_ASIA_KOLKATA.getId(), TZ_ID_ASIA_KOLKATA);
    zoneTabs.put(TZ_ID_ASIA_KRASNOYARSK.getId(), TZ_ID_ASIA_KRASNOYARSK);
    zoneTabs.put(TZ_ID_ASIA_KUALA_LUMPUR.getId(), TZ_ID_ASIA_KUALA_LUMPUR);
    zoneTabs.put(TZ_ID_ASIA_KUCHING.getId(), TZ_ID_ASIA_KUCHING);
    zoneTabs.put(TZ_ID_ASIA_KUWAIT.getId(), TZ_ID_ASIA_KUWAIT);
    zoneTabs.put(TZ_ID_ASIA_MACAO.getId(), TZ_ID_ASIA_MACAO);
    zoneTabs.put(TZ_ID_ASIA_MACAU.getId(), TZ_ID_ASIA_MACAU);
    zoneTabs.put(TZ_ID_ASIA_MAGADAN.getId(), TZ_ID_ASIA_MAGADAN);
    zoneTabs.put(TZ_ID_ASIA_MAKASSAR.getId(), TZ_ID_ASIA_MAKASSAR);
    zoneTabs.put(TZ_ID_ASIA_MANILA.getId(), TZ_ID_ASIA_MANILA);
    zoneTabs.put(TZ_ID_ASIA_MUSCAT.getId(), TZ_ID_ASIA_MUSCAT);
    zoneTabs.put(TZ_ID_ASIA_NICOSIA.getId(), TZ_ID_ASIA_NICOSIA);
    zoneTabs.put(TZ_ID_ASIA_NOVOKUZNETSK.getId(), TZ_ID_ASIA_NOVOKUZNETSK);
    zoneTabs.put(TZ_ID_ASIA_NOVOSIBIRSK.getId(), TZ_ID_ASIA_NOVOSIBIRSK);
    zoneTabs.put(TZ_ID_ASIA_OMSK.getId(), TZ_ID_ASIA_OMSK);
    zoneTabs.put(TZ_ID_ASIA_ORAL.getId(), TZ_ID_ASIA_ORAL);
    zoneTabs.put(TZ_ID_ASIA_PHNOM_PENH.getId(), TZ_ID_ASIA_PHNOM_PENH);
    zoneTabs.put(TZ_ID_ASIA_PONTIANAK.getId(), TZ_ID_ASIA_PONTIANAK);
    zoneTabs.put(TZ_ID_ASIA_PYONGYANG.getId(), TZ_ID_ASIA_PYONGYANG);
    zoneTabs.put(TZ_ID_ASIA_QATAR.getId(), TZ_ID_ASIA_QATAR);
    zoneTabs.put(TZ_ID_ASIA_QYZYLORDA.getId(), TZ_ID_ASIA_QYZYLORDA);
    zoneTabs.put(TZ_ID_ASIA_RANGOON.getId(), TZ_ID_ASIA_RANGOON);
    zoneTabs.put(TZ_ID_ASIA_RIYADH.getId(), TZ_ID_ASIA_RIYADH);
    zoneTabs.put(TZ_ID_ASIA_RIYADH87.getId(), TZ_ID_ASIA_RIYADH87);
    zoneTabs.put(TZ_ID_ASIA_RIYADH88.getId(), TZ_ID_ASIA_RIYADH88);
    zoneTabs.put(TZ_ID_ASIA_RIYADH89.getId(), TZ_ID_ASIA_RIYADH89);
    zoneTabs.put(TZ_ID_ASIA_SAIGON.getId(), TZ_ID_ASIA_SAIGON);
    zoneTabs.put(TZ_ID_ASIA_SAKHALIN.getId(), TZ_ID_ASIA_SAKHALIN);
    zoneTabs.put(TZ_ID_ASIA_SAMARKAND.getId(), TZ_ID_ASIA_SAMARKAND);
    zoneTabs.put(TZ_ID_ASIA_SEOUL.getId(), TZ_ID_ASIA_SEOUL);
    zoneTabs.put(TZ_ID_ASIA_SHANGHAI.getId(), TZ_ID_ASIA_SHANGHAI);
    zoneTabs.put(TZ_ID_ASIA_SINGAPORE.getId(), TZ_ID_ASIA_SINGAPORE);
    zoneTabs.put(TZ_ID_ASIA_TAIPEI.getId(), TZ_ID_ASIA_TAIPEI);
    zoneTabs.put(TZ_ID_ASIA_TASHKENT.getId(), TZ_ID_ASIA_TASHKENT);
    zoneTabs.put(TZ_ID_ASIA_TBILISI.getId(), TZ_ID_ASIA_TBILISI);
    zoneTabs.put(TZ_ID_ASIA_TEHRAN.getId(), TZ_ID_ASIA_TEHRAN);
    zoneTabs.put(TZ_ID_ASIA_TEL_AVIV.getId(), TZ_ID_ASIA_TEL_AVIV);
    zoneTabs.put(TZ_ID_ASIA_THIMBU.getId(), TZ_ID_ASIA_THIMBU);
    zoneTabs.put(TZ_ID_ASIA_THIMPHU.getId(), TZ_ID_ASIA_THIMPHU);
    zoneTabs.put(TZ_ID_ASIA_TOKYO.getId(), TZ_ID_ASIA_TOKYO);
    zoneTabs.put(TZ_ID_ASIA_UJUNG_PANDANG.getId(), TZ_ID_ASIA_UJUNG_PANDANG);
    zoneTabs.put(TZ_ID_ASIA_ULAANBAATAR.getId(), TZ_ID_ASIA_ULAANBAATAR);
    zoneTabs.put(TZ_ID_ASIA_ULAN_BATOR.getId(), TZ_ID_ASIA_ULAN_BATOR);
    zoneTabs.put(TZ_ID_ASIA_URUMQI.getId(), TZ_ID_ASIA_URUMQI);
    zoneTabs.put(TZ_ID_ASIA_VIENTIANE.getId(), TZ_ID_ASIA_VIENTIANE);
    zoneTabs.put(TZ_ID_ASIA_VLADIVOSTOK.getId(), TZ_ID_ASIA_VLADIVOSTOK);
    zoneTabs.put(TZ_ID_ASIA_YAKUTSK.getId(), TZ_ID_ASIA_YAKUTSK);
    zoneTabs.put(TZ_ID_ASIA_YEKATERINBURG.getId(), TZ_ID_ASIA_YEKATERINBURG);
    zoneTabs.put(TZ_ID_ASIA_YEREVAN.getId(), TZ_ID_ASIA_YEREVAN);
    zoneTabs.put(TZ_ID_ATLANTIC_AZORES.getId(), TZ_ID_ATLANTIC_AZORES);
    zoneTabs.put(TZ_ID_ATLANTIC_BERMUDA.getId(), TZ_ID_ATLANTIC_BERMUDA);
    zoneTabs.put(TZ_ID_ATLANTIC_CANARY.getId(), TZ_ID_ATLANTIC_CANARY);
    zoneTabs.put(TZ_ID_ATLANTIC_CAPE_VERDE.getId(), TZ_ID_ATLANTIC_CAPE_VERDE);
    zoneTabs.put(TZ_ID_ATLANTIC_FAEROE.getId(), TZ_ID_ATLANTIC_FAEROE);
    zoneTabs.put(TZ_ID_ATLANTIC_FAROE.getId(), TZ_ID_ATLANTIC_FAROE);
    zoneTabs.put(TZ_ID_ATLANTIC_JAN_MAYEN.getId(), TZ_ID_ATLANTIC_JAN_MAYEN);
    zoneTabs.put(TZ_ID_ATLANTIC_MADEIRA.getId(), TZ_ID_ATLANTIC_MADEIRA);
    zoneTabs.put(TZ_ID_ATLANTIC_REYKJAVIK.getId(), TZ_ID_ATLANTIC_REYKJAVIK);
    zoneTabs.put(TZ_ID_ATLANTIC_SOUTH_GEORGIA.getId(), TZ_ID_ATLANTIC_SOUTH_GEORGIA);
    zoneTabs.put(TZ_ID_ATLANTIC_ST_HELENA.getId(), TZ_ID_ATLANTIC_ST_HELENA);
    zoneTabs.put(TZ_ID_ATLANTIC_STANLEY.getId(), TZ_ID_ATLANTIC_STANLEY);
    zoneTabs.put(TZ_ID_AUSTRALIA_ACT.getId(), TZ_ID_AUSTRALIA_ACT);
    zoneTabs.put(TZ_ID_AUSTRALIA_ADELAIDE.getId(), TZ_ID_AUSTRALIA_ADELAIDE);
    zoneTabs.put(TZ_ID_AUSTRALIA_BRISBANE.getId(), TZ_ID_AUSTRALIA_BRISBANE);
    zoneTabs.put(TZ_ID_AUSTRALIA_BROKEN_HILL.getId(), TZ_ID_AUSTRALIA_BROKEN_HILL);
    zoneTabs.put(TZ_ID_AUSTRALIA_CANBERRA.getId(), TZ_ID_AUSTRALIA_CANBERRA);
    zoneTabs.put(TZ_ID_AUSTRALIA_CURRIE.getId(), TZ_ID_AUSTRALIA_CURRIE);
    zoneTabs.put(TZ_ID_AUSTRALIA_DARWIN.getId(), TZ_ID_AUSTRALIA_DARWIN);
    zoneTabs.put(TZ_ID_AUSTRALIA_EUCLA.getId(), TZ_ID_AUSTRALIA_EUCLA);
    zoneTabs.put(TZ_ID_AUSTRALIA_HOBART.getId(), TZ_ID_AUSTRALIA_HOBART);
    zoneTabs.put(TZ_ID_AUSTRALIA_LHI.getId(), TZ_ID_AUSTRALIA_LHI);
    zoneTabs.put(TZ_ID_AUSTRALIA_LINDEMAN.getId(), TZ_ID_AUSTRALIA_LINDEMAN);
    zoneTabs.put(TZ_ID_AUSTRALIA_LORD_HOWE.getId(), TZ_ID_AUSTRALIA_LORD_HOWE);
    zoneTabs.put(TZ_ID_AUSTRALIA_MELBOURNE.getId(), TZ_ID_AUSTRALIA_MELBOURNE);
    zoneTabs.put(TZ_ID_AUSTRALIA_NORTH.getId(), TZ_ID_AUSTRALIA_NORTH);
    zoneTabs.put(TZ_ID_AUSTRALIA_NSW.getId(), TZ_ID_AUSTRALIA_NSW);
    zoneTabs.put(TZ_ID_AUSTRALIA_PERTH.getId(), TZ_ID_AUSTRALIA_PERTH);
    zoneTabs.put(TZ_ID_AUSTRALIA_QUEENSLAND.getId(), TZ_ID_AUSTRALIA_QUEENSLAND);
    zoneTabs.put(TZ_ID_AUSTRALIA_SOUTH.getId(), TZ_ID_AUSTRALIA_SOUTH);
    zoneTabs.put(TZ_ID_AUSTRALIA_SYDNEY.getId(), TZ_ID_AUSTRALIA_SYDNEY);
    zoneTabs.put(TZ_ID_AUSTRALIA_TASMANIA.getId(), TZ_ID_AUSTRALIA_TASMANIA);
    zoneTabs.put(TZ_ID_AUSTRALIA_VICTORIA.getId(), TZ_ID_AUSTRALIA_VICTORIA);
    zoneTabs.put(TZ_ID_AUSTRALIA_WEST.getId(), TZ_ID_AUSTRALIA_WEST);
    zoneTabs.put(TZ_ID_AUSTRALIA_YANCOWINNA.getId(), TZ_ID_AUSTRALIA_YANCOWINNA);
    zoneTabs.put(TZ_ID_BRAZIL_ACRE.getId(), TZ_ID_BRAZIL_ACRE);
    zoneTabs.put(TZ_ID_BRAZIL_DENORONHA.getId(), TZ_ID_BRAZIL_DENORONHA);
    zoneTabs.put(TZ_ID_BRAZIL_EAST.getId(), TZ_ID_BRAZIL_EAST);
    zoneTabs.put(TZ_ID_BRAZIL_WEST.getId(), TZ_ID_BRAZIL_WEST);
    zoneTabs.put(TZ_ID_CANADA_ATLANTIC.getId(), TZ_ID_CANADA_ATLANTIC);
    zoneTabs.put(TZ_ID_CANADA_CENTRAL.getId(), TZ_ID_CANADA_CENTRAL);
    zoneTabs.put(TZ_ID_CANADA_EAST_SASKATCHEWAN.getId(), TZ_ID_CANADA_EAST_SASKATCHEWAN);
    zoneTabs.put(TZ_ID_CANADA_EASTERN.getId(), TZ_ID_CANADA_EASTERN);
    zoneTabs.put(TZ_ID_CANADA_MOUNTAIN.getId(), TZ_ID_CANADA_MOUNTAIN);
    zoneTabs.put(TZ_ID_CANADA_NEWFOUNDLAND.getId(), TZ_ID_CANADA_NEWFOUNDLAND);
    zoneTabs.put(TZ_ID_CANADA_PACIFIC.getId(), TZ_ID_CANADA_PACIFIC);
    zoneTabs.put(TZ_ID_CANADA_SASKATCHEWAN.getId(), TZ_ID_CANADA_SASKATCHEWAN);
    zoneTabs.put(TZ_ID_CANADA_YUKON.getId(), TZ_ID_CANADA_YUKON);
    zoneTabs.put(TZ_ID_CET.getId(), TZ_ID_CET);
    zoneTabs.put(TZ_ID_CHILE_CONTINENTAL.getId(), TZ_ID_CHILE_CONTINENTAL);
    zoneTabs.put(TZ_ID_CHILE_EASTERISLAND.getId(), TZ_ID_CHILE_EASTERISLAND);
    zoneTabs.put(TZ_ID_CST6CDT.getId(), TZ_ID_CST6CDT);
    zoneTabs.put(TZ_ID_CUBA.getId(), TZ_ID_CUBA);
    zoneTabs.put(TZ_ID_EET.getId(), TZ_ID_EET);
    zoneTabs.put(TZ_ID_EGYPT.getId(), TZ_ID_EGYPT);
    zoneTabs.put(TZ_ID_EIRE.getId(), TZ_ID_EIRE);
    zoneTabs.put(TZ_ID_EST.getId(), TZ_ID_EST);
    zoneTabs.put(TZ_ID_EST5EDT.getId(), TZ_ID_EST5EDT);
    zoneTabs.put(TZ_ID_ETC_GMT.getId(), TZ_ID_ETC_GMT);
    zoneTabs.put(TZ_ID_ETC_GMTW0.getId(), TZ_ID_ETC_GMTW0);
    zoneTabs.put(TZ_ID_ETC_GMTW1.getId(), TZ_ID_ETC_GMTW1);
    zoneTabs.put(TZ_ID_ETC_GMTW10.getId(), TZ_ID_ETC_GMTW10);
    zoneTabs.put(TZ_ID_ETC_GMTW11.getId(), TZ_ID_ETC_GMTW11);
    zoneTabs.put(TZ_ID_ETC_GMTW12.getId(), TZ_ID_ETC_GMTW12);
    zoneTabs.put(TZ_ID_ETC_GMTW2.getId(), TZ_ID_ETC_GMTW2);
    zoneTabs.put(TZ_ID_ETC_GMTW3.getId(), TZ_ID_ETC_GMTW3);
    zoneTabs.put(TZ_ID_ETC_GMTW4.getId(), TZ_ID_ETC_GMTW4);
    zoneTabs.put(TZ_ID_ETC_GMTW5.getId(), TZ_ID_ETC_GMTW5);
    zoneTabs.put(TZ_ID_ETC_GMTW6.getId(), TZ_ID_ETC_GMTW6);
    zoneTabs.put(TZ_ID_ETC_GMTW7.getId(), TZ_ID_ETC_GMTW7);
    zoneTabs.put(TZ_ID_ETC_GMTW8.getId(), TZ_ID_ETC_GMTW8);
    zoneTabs.put(TZ_ID_ETC_GMTW9.getId(), TZ_ID_ETC_GMTW9);
    zoneTabs.put(TZ_ID_ETC_GMTE0.getId(), TZ_ID_ETC_GMTE0);
    zoneTabs.put(TZ_ID_ETC_GMTE1.getId(), TZ_ID_ETC_GMTE1);
    zoneTabs.put(TZ_ID_ETC_GMTE10.getId(), TZ_ID_ETC_GMTE10);
    zoneTabs.put(TZ_ID_ETC_GMTE11.getId(), TZ_ID_ETC_GMTE11);
    zoneTabs.put(TZ_ID_ETC_GMTE12.getId(), TZ_ID_ETC_GMTE12);
    zoneTabs.put(TZ_ID_ETC_GMTE13.getId(), TZ_ID_ETC_GMTE13);
    zoneTabs.put(TZ_ID_ETC_GMTE14.getId(), TZ_ID_ETC_GMTE14);
    zoneTabs.put(TZ_ID_ETC_GMTE2.getId(), TZ_ID_ETC_GMTE2);
    zoneTabs.put(TZ_ID_ETC_GMTE3.getId(), TZ_ID_ETC_GMTE3);
    zoneTabs.put(TZ_ID_ETC_GMTE4.getId(), TZ_ID_ETC_GMTE4);
    zoneTabs.put(TZ_ID_ETC_GMTE5.getId(), TZ_ID_ETC_GMTE5);
    zoneTabs.put(TZ_ID_ETC_GMTE6.getId(), TZ_ID_ETC_GMTE6);
    zoneTabs.put(TZ_ID_ETC_GMTE7.getId(), TZ_ID_ETC_GMTE7);
    zoneTabs.put(TZ_ID_ETC_GMTE8.getId(), TZ_ID_ETC_GMTE8);
    zoneTabs.put(TZ_ID_ETC_GMTE9.getId(), TZ_ID_ETC_GMTE9);
    zoneTabs.put(TZ_ID_ETC_GMT0.getId(), TZ_ID_ETC_GMT0);
    zoneTabs.put(TZ_ID_ETC_GREENWICH.getId(), TZ_ID_ETC_GREENWICH);
    zoneTabs.put(TZ_ID_ETC_UCT.getId(), TZ_ID_ETC_UCT);
    zoneTabs.put(TZ_ID_ETC_UNIVERSAL.getId(), TZ_ID_ETC_UNIVERSAL);
    zoneTabs.put(TZ_ID_ETC_UTC.getId(), TZ_ID_ETC_UTC);
    zoneTabs.put(TZ_ID_ETC_ZULU.getId(), TZ_ID_ETC_ZULU);
    zoneTabs.put(TZ_ID_EUROPE_AMSTERDAM.getId(), TZ_ID_EUROPE_AMSTERDAM);
    zoneTabs.put(TZ_ID_EUROPE_ANDORRA.getId(), TZ_ID_EUROPE_ANDORRA);
    zoneTabs.put(TZ_ID_EUROPE_ATHENS.getId(), TZ_ID_EUROPE_ATHENS);
    zoneTabs.put(TZ_ID_EUROPE_BELFAST.getId(), TZ_ID_EUROPE_BELFAST);
    zoneTabs.put(TZ_ID_EUROPE_BELGRADE.getId(), TZ_ID_EUROPE_BELGRADE);
    zoneTabs.put(TZ_ID_EUROPE_BERLIN.getId(), TZ_ID_EUROPE_BERLIN);
    zoneTabs.put(TZ_ID_EUROPE_BRATISLAVA.getId(), TZ_ID_EUROPE_BRATISLAVA);
    zoneTabs.put(TZ_ID_EUROPE_BRUSSELS.getId(), TZ_ID_EUROPE_BRUSSELS);
    zoneTabs.put(TZ_ID_EUROPE_BUCHAREST.getId(), TZ_ID_EUROPE_BUCHAREST);
    zoneTabs.put(TZ_ID_EUROPE_BUDAPEST.getId(), TZ_ID_EUROPE_BUDAPEST);
    zoneTabs.put(TZ_ID_EUROPE_CHISINAU.getId(), TZ_ID_EUROPE_CHISINAU);
    zoneTabs.put(TZ_ID_EUROPE_COPENHAGEN.getId(), TZ_ID_EUROPE_COPENHAGEN);
    zoneTabs.put(TZ_ID_EUROPE_DUBLIN.getId(), TZ_ID_EUROPE_DUBLIN);
    zoneTabs.put(TZ_ID_EUROPE_GIBRALTAR.getId(), TZ_ID_EUROPE_GIBRALTAR);
    zoneTabs.put(TZ_ID_EUROPE_GUERNSEY.getId(), TZ_ID_EUROPE_GUERNSEY);
    zoneTabs.put(TZ_ID_EUROPE_HELSINKI.getId(), TZ_ID_EUROPE_HELSINKI);
    zoneTabs.put(TZ_ID_EUROPE_ISLE_OF_MAN.getId(), TZ_ID_EUROPE_ISLE_OF_MAN);
    zoneTabs.put(TZ_ID_EUROPE_ISTANBUL.getId(), TZ_ID_EUROPE_ISTANBUL);
    zoneTabs.put(TZ_ID_EUROPE_JERSEY.getId(), TZ_ID_EUROPE_JERSEY);
    zoneTabs.put(TZ_ID_EUROPE_KALININGRAD.getId(), TZ_ID_EUROPE_KALININGRAD);
    zoneTabs.put(TZ_ID_EUROPE_KIEV.getId(), TZ_ID_EUROPE_KIEV);
    zoneTabs.put(TZ_ID_EUROPE_LISBON.getId(), TZ_ID_EUROPE_LISBON);
    zoneTabs.put(TZ_ID_EUROPE_LJUBLJANA.getId(), TZ_ID_EUROPE_LJUBLJANA);
    zoneTabs.put(TZ_ID_EUROPE_LONDON.getId(), TZ_ID_EUROPE_LONDON);
    zoneTabs.put(TZ_ID_EUROPE_LUXEMBOURG.getId(), TZ_ID_EUROPE_LUXEMBOURG);
    zoneTabs.put(TZ_ID_EUROPE_MADRID.getId(), TZ_ID_EUROPE_MADRID);
    zoneTabs.put(TZ_ID_EUROPE_MALTA.getId(), TZ_ID_EUROPE_MALTA);
    zoneTabs.put(TZ_ID_EUROPE_MARIEHAMN.getId(), TZ_ID_EUROPE_MARIEHAMN);
    zoneTabs.put(TZ_ID_EUROPE_MINSK.getId(), TZ_ID_EUROPE_MINSK);
    zoneTabs.put(TZ_ID_EUROPE_MONACO.getId(), TZ_ID_EUROPE_MONACO);
    zoneTabs.put(TZ_ID_EUROPE_MOSCOW.getId(), TZ_ID_EUROPE_MOSCOW);
    zoneTabs.put(TZ_ID_EUROPE_NICOSIA.getId(), TZ_ID_EUROPE_NICOSIA);
    zoneTabs.put(TZ_ID_EUROPE_OSLO.getId(), TZ_ID_EUROPE_OSLO);
    zoneTabs.put(TZ_ID_EUROPE_PARIS.getId(), TZ_ID_EUROPE_PARIS);
    zoneTabs.put(TZ_ID_EUROPE_PODGORICA.getId(), TZ_ID_EUROPE_PODGORICA);
    zoneTabs.put(TZ_ID_EUROPE_PRAGUE.getId(), TZ_ID_EUROPE_PRAGUE);
    zoneTabs.put(TZ_ID_EUROPE_RIGA.getId(), TZ_ID_EUROPE_RIGA);
    zoneTabs.put(TZ_ID_EUROPE_ROME.getId(), TZ_ID_EUROPE_ROME);
    zoneTabs.put(TZ_ID_EUROPE_SAMARA.getId(), TZ_ID_EUROPE_SAMARA);
    zoneTabs.put(TZ_ID_EUROPE_SAN_MARINO.getId(), TZ_ID_EUROPE_SAN_MARINO);
    zoneTabs.put(TZ_ID_EUROPE_SARAJEVO.getId(), TZ_ID_EUROPE_SARAJEVO);
    zoneTabs.put(TZ_ID_EUROPE_SIMFEROPOL.getId(), TZ_ID_EUROPE_SIMFEROPOL);
    zoneTabs.put(TZ_ID_EUROPE_SKOPJE.getId(), TZ_ID_EUROPE_SKOPJE);
    zoneTabs.put(TZ_ID_EUROPE_SOFIA.getId(), TZ_ID_EUROPE_SOFIA);
    zoneTabs.put(TZ_ID_EUROPE_STOCKHOLM.getId(), TZ_ID_EUROPE_STOCKHOLM);
    zoneTabs.put(TZ_ID_EUROPE_TALLINN.getId(), TZ_ID_EUROPE_TALLINN);
    zoneTabs.put(TZ_ID_EUROPE_TIRANE.getId(), TZ_ID_EUROPE_TIRANE);
    zoneTabs.put(TZ_ID_EUROPE_TIRASPOL.getId(), TZ_ID_EUROPE_TIRASPOL);
    zoneTabs.put(TZ_ID_EUROPE_UZHGOROD.getId(), TZ_ID_EUROPE_UZHGOROD);
    zoneTabs.put(TZ_ID_EUROPE_VADUZ.getId(), TZ_ID_EUROPE_VADUZ);
    zoneTabs.put(TZ_ID_EUROPE_VATICAN.getId(), TZ_ID_EUROPE_VATICAN);
    zoneTabs.put(TZ_ID_EUROPE_VIENNA.getId(), TZ_ID_EUROPE_VIENNA);
    zoneTabs.put(TZ_ID_EUROPE_VILNIUS.getId(), TZ_ID_EUROPE_VILNIUS);
    zoneTabs.put(TZ_ID_EUROPE_VOLGOGRAD.getId(), TZ_ID_EUROPE_VOLGOGRAD);
    zoneTabs.put(TZ_ID_EUROPE_WARSAW.getId(), TZ_ID_EUROPE_WARSAW);
    zoneTabs.put(TZ_ID_EUROPE_ZAGREB.getId(), TZ_ID_EUROPE_ZAGREB);
    zoneTabs.put(TZ_ID_EUROPE_ZAPOROZHYE.getId(), TZ_ID_EUROPE_ZAPOROZHYE);
    zoneTabs.put(TZ_ID_EUROPE_ZURICH.getId(), TZ_ID_EUROPE_ZURICH);
    zoneTabs.put(TZ_ID_GB.getId(), TZ_ID_GB);
    zoneTabs.put(TZ_ID_GB_EIRE.getId(), TZ_ID_GB_EIRE);
    zoneTabs.put(TZ_ID_GMT.getId(), TZ_ID_GMT);
    zoneTabs.put(TZ_ID_GMTW0.getId(), TZ_ID_GMTW0);
    zoneTabs.put(TZ_ID_GMTE0.getId(), TZ_ID_GMTE0);
    zoneTabs.put(TZ_ID_GMT0.getId(), TZ_ID_GMT0);
    zoneTabs.put(TZ_ID_GREENWICH.getId(), TZ_ID_GREENWICH);
    zoneTabs.put(TZ_ID_HONGKONG.getId(), TZ_ID_HONGKONG);
    zoneTabs.put(TZ_ID_HST.getId(), TZ_ID_HST);
    zoneTabs.put(TZ_ID_ICELAND.getId(), TZ_ID_ICELAND);
    zoneTabs.put(TZ_ID_INDIAN_ANTANANARIVO.getId(), TZ_ID_INDIAN_ANTANANARIVO);
    zoneTabs.put(TZ_ID_INDIAN_CHAGOS.getId(), TZ_ID_INDIAN_CHAGOS);
    zoneTabs.put(TZ_ID_INDIAN_CHRISTMAS.getId(), TZ_ID_INDIAN_CHRISTMAS);
    zoneTabs.put(TZ_ID_INDIAN_COCOS.getId(), TZ_ID_INDIAN_COCOS);
    zoneTabs.put(TZ_ID_INDIAN_COMORO.getId(), TZ_ID_INDIAN_COMORO);
    zoneTabs.put(TZ_ID_INDIAN_KERGUELEN.getId(), TZ_ID_INDIAN_KERGUELEN);
    zoneTabs.put(TZ_ID_INDIAN_MAHE.getId(), TZ_ID_INDIAN_MAHE);
    zoneTabs.put(TZ_ID_INDIAN_MALDIVES.getId(), TZ_ID_INDIAN_MALDIVES);
    zoneTabs.put(TZ_ID_INDIAN_MAURITIUS.getId(), TZ_ID_INDIAN_MAURITIUS);
    zoneTabs.put(TZ_ID_INDIAN_MAYOTTE.getId(), TZ_ID_INDIAN_MAYOTTE);
    zoneTabs.put(TZ_ID_INDIAN_REUNION.getId(), TZ_ID_INDIAN_REUNION);
    zoneTabs.put(TZ_ID_IRAN.getId(), TZ_ID_IRAN);
    zoneTabs.put(TZ_ID_ISRAEL.getId(), TZ_ID_ISRAEL);
    zoneTabs.put(TZ_ID_JAMAICA.getId(), TZ_ID_JAMAICA);
    zoneTabs.put(TZ_ID_JAPAN.getId(), TZ_ID_JAPAN);
    zoneTabs.put(TZ_ID_KWAJALEIN.getId(), TZ_ID_KWAJALEIN);
    zoneTabs.put(TZ_ID_LIBYA.getId(), TZ_ID_LIBYA);
    zoneTabs.put(TZ_ID_MET.getId(), TZ_ID_MET);
    zoneTabs.put(TZ_ID_MEXICO_BAJANORTE.getId(), TZ_ID_MEXICO_BAJANORTE);
    zoneTabs.put(TZ_ID_MEXICO_BAJASUR.getId(), TZ_ID_MEXICO_BAJASUR);
    zoneTabs.put(TZ_ID_MEXICO_GENERAL.getId(), TZ_ID_MEXICO_GENERAL);
    zoneTabs.put(TZ_ID_MIDEAST_RIYADH87.getId(), TZ_ID_MIDEAST_RIYADH87);
    zoneTabs.put(TZ_ID_MIDEAST_RIYADH88.getId(), TZ_ID_MIDEAST_RIYADH88);
    zoneTabs.put(TZ_ID_MIDEAST_RIYADH89.getId(), TZ_ID_MIDEAST_RIYADH89);
    zoneTabs.put(TZ_ID_MST.getId(), TZ_ID_MST);
    zoneTabs.put(TZ_ID_MST7MDT.getId(), TZ_ID_MST7MDT);
    zoneTabs.put(TZ_ID_NAVAJO.getId(), TZ_ID_NAVAJO);
    zoneTabs.put(TZ_ID_NZ.getId(), TZ_ID_NZ);
    zoneTabs.put(TZ_ID_NZ_CHAT.getId(), TZ_ID_NZ_CHAT);
    zoneTabs.put(TZ_ID_PACIFIC_APIA.getId(), TZ_ID_PACIFIC_APIA);
    zoneTabs.put(TZ_ID_PACIFIC_AUCKLAND.getId(), TZ_ID_PACIFIC_AUCKLAND);
    zoneTabs.put(TZ_ID_PACIFIC_CHATHAM.getId(), TZ_ID_PACIFIC_CHATHAM);
    zoneTabs.put(TZ_ID_PACIFIC_CHUUK.getId(), TZ_ID_PACIFIC_CHUUK);
    zoneTabs.put(TZ_ID_PACIFIC_EASTER.getId(), TZ_ID_PACIFIC_EASTER);
    zoneTabs.put(TZ_ID_PACIFIC_EFATE.getId(), TZ_ID_PACIFIC_EFATE);
    zoneTabs.put(TZ_ID_PACIFIC_ENDERBURY.getId(), TZ_ID_PACIFIC_ENDERBURY);
    zoneTabs.put(TZ_ID_PACIFIC_FAKAOFO.getId(), TZ_ID_PACIFIC_FAKAOFO);
    zoneTabs.put(TZ_ID_PACIFIC_FIJI.getId(), TZ_ID_PACIFIC_FIJI);
    zoneTabs.put(TZ_ID_PACIFIC_FUNAFUTI.getId(), TZ_ID_PACIFIC_FUNAFUTI);
    zoneTabs.put(TZ_ID_PACIFIC_GALAPAGOS.getId(), TZ_ID_PACIFIC_GALAPAGOS);
    zoneTabs.put(TZ_ID_PACIFIC_GAMBIER.getId(), TZ_ID_PACIFIC_GAMBIER);
    zoneTabs.put(TZ_ID_PACIFIC_GUADALCANAL.getId(), TZ_ID_PACIFIC_GUADALCANAL);
    zoneTabs.put(TZ_ID_PACIFIC_GUAM.getId(), TZ_ID_PACIFIC_GUAM);
    zoneTabs.put(TZ_ID_PACIFIC_HONOLULU.getId(), TZ_ID_PACIFIC_HONOLULU);
    zoneTabs.put(TZ_ID_PACIFIC_JOHNSTON.getId(), TZ_ID_PACIFIC_JOHNSTON);
    zoneTabs.put(TZ_ID_PACIFIC_KIRITIMATI.getId(), TZ_ID_PACIFIC_KIRITIMATI);
    zoneTabs.put(TZ_ID_PACIFIC_KOSRAE.getId(), TZ_ID_PACIFIC_KOSRAE);
    zoneTabs.put(TZ_ID_PACIFIC_KWAJALEIN.getId(), TZ_ID_PACIFIC_KWAJALEIN);
    zoneTabs.put(TZ_ID_PACIFIC_MAJURO.getId(), TZ_ID_PACIFIC_MAJURO);
    zoneTabs.put(TZ_ID_PACIFIC_MARQUESAS.getId(), TZ_ID_PACIFIC_MARQUESAS);
    zoneTabs.put(TZ_ID_PACIFIC_MIDWAY.getId(), TZ_ID_PACIFIC_MIDWAY);
    zoneTabs.put(TZ_ID_PACIFIC_NAURU.getId(), TZ_ID_PACIFIC_NAURU);
    zoneTabs.put(TZ_ID_PACIFIC_NIUE.getId(), TZ_ID_PACIFIC_NIUE);
    zoneTabs.put(TZ_ID_PACIFIC_NORFOLK.getId(), TZ_ID_PACIFIC_NORFOLK);
    zoneTabs.put(TZ_ID_PACIFIC_NOUMEA.getId(), TZ_ID_PACIFIC_NOUMEA);
    zoneTabs.put(TZ_ID_PACIFIC_PAGO_PAGO.getId(), TZ_ID_PACIFIC_PAGO_PAGO);
    zoneTabs.put(TZ_ID_PACIFIC_PALAU.getId(), TZ_ID_PACIFIC_PALAU);
    zoneTabs.put(TZ_ID_PACIFIC_PITCAIRN.getId(), TZ_ID_PACIFIC_PITCAIRN);
    zoneTabs.put(TZ_ID_PACIFIC_POHNPEI.getId(), TZ_ID_PACIFIC_POHNPEI);
    zoneTabs.put(TZ_ID_PACIFIC_PONAPE.getId(), TZ_ID_PACIFIC_PONAPE);
    zoneTabs.put(TZ_ID_PACIFIC_PORT_MORESBY.getId(), TZ_ID_PACIFIC_PORT_MORESBY);
    zoneTabs.put(TZ_ID_PACIFIC_RAROTONGA.getId(), TZ_ID_PACIFIC_RAROTONGA);
    zoneTabs.put(TZ_ID_PACIFIC_SAIPAN.getId(), TZ_ID_PACIFIC_SAIPAN);
    zoneTabs.put(TZ_ID_PACIFIC_SAMOA.getId(), TZ_ID_PACIFIC_SAMOA);
    zoneTabs.put(TZ_ID_PACIFIC_TAHITI.getId(), TZ_ID_PACIFIC_TAHITI);
    zoneTabs.put(TZ_ID_PACIFIC_TARAWA.getId(), TZ_ID_PACIFIC_TARAWA);
    zoneTabs.put(TZ_ID_PACIFIC_TONGATAPU.getId(), TZ_ID_PACIFIC_TONGATAPU);
    zoneTabs.put(TZ_ID_PACIFIC_TRUK.getId(), TZ_ID_PACIFIC_TRUK);
    zoneTabs.put(TZ_ID_PACIFIC_WAKE.getId(), TZ_ID_PACIFIC_WAKE);
    zoneTabs.put(TZ_ID_PACIFIC_WALLIS.getId(), TZ_ID_PACIFIC_WALLIS);
    zoneTabs.put(TZ_ID_PACIFIC_YAP.getId(), TZ_ID_PACIFIC_YAP);
    zoneTabs.put(TZ_ID_POLAND.getId(), TZ_ID_POLAND);
    zoneTabs.put(TZ_ID_PORTUGAL.getId(), TZ_ID_PORTUGAL);
    zoneTabs.put(TZ_ID_PRC.getId(), TZ_ID_PRC);
    zoneTabs.put(TZ_ID_PST8PDT.getId(), TZ_ID_PST8PDT);
    zoneTabs.put(TZ_ID_ROC.getId(), TZ_ID_ROC);
    zoneTabs.put(TZ_ID_ROK.getId(), TZ_ID_ROK);
    zoneTabs.put(TZ_ID_SINGAPORE.getId(), TZ_ID_SINGAPORE);
    zoneTabs.put(TZ_ID_TURKEY.getId(), TZ_ID_TURKEY);
    zoneTabs.put(TZ_ID_UCT.getId(), TZ_ID_UCT);
    zoneTabs.put(TZ_ID_UNIVERSAL.getId(), TZ_ID_UNIVERSAL);
    zoneTabs.put(TZ_ID_US_ALASKA.getId(), TZ_ID_US_ALASKA);
    zoneTabs.put(TZ_ID_US_ALEUTIAN.getId(), TZ_ID_US_ALEUTIAN);
    zoneTabs.put(TZ_ID_US_ARIZONA.getId(), TZ_ID_US_ARIZONA);
    zoneTabs.put(TZ_ID_US_CENTRAL.getId(), TZ_ID_US_CENTRAL);
    zoneTabs.put(TZ_ID_US_EAST_INDIANA.getId(), TZ_ID_US_EAST_INDIANA);
    zoneTabs.put(TZ_ID_US_EASTERN.getId(), TZ_ID_US_EASTERN);
    zoneTabs.put(TZ_ID_US_HAWAII.getId(), TZ_ID_US_HAWAII);
    zoneTabs.put(TZ_ID_US_INDIANA_STARKE.getId(), TZ_ID_US_INDIANA_STARKE);
    zoneTabs.put(TZ_ID_US_MICHIGAN.getId(), TZ_ID_US_MICHIGAN);
    zoneTabs.put(TZ_ID_US_MOUNTAIN.getId(), TZ_ID_US_MOUNTAIN);
    zoneTabs.put(TZ_ID_US_PACIFIC.getId(), TZ_ID_US_PACIFIC);
    zoneTabs.put(TZ_ID_US_PACIFIC_NEW.getId(), TZ_ID_US_PACIFIC_NEW);
    zoneTabs.put(TZ_ID_US_SAMOA.getId(), TZ_ID_US_SAMOA);
    zoneTabs.put(TZ_ID_UTC.getId(), TZ_ID_UTC);
    zoneTabs.put(TZ_ID_W_SU.getId(), TZ_ID_W_SU);
    zoneTabs.put(TZ_ID_WET.getId(), TZ_ID_WET);
    zoneTabs.put(TZ_ID_ZULU.getId(), TZ_ID_ZULU);
    zoneTabs.put(TZ_ID_EXT_PST.getId(), TZ_ID_EXT_PST);
    zoneTabs.put(TZ_ID_DEFAULT.getId(), TZ_ID_DEFAULT);
    zoneTabs.put(TZ_ID_OFFSET.getId(), TZ_ID_OFFSET);
    zoneTabs.put(TZ_ID_AFRICA_ABIDJAN.getName(), TZ_ID_AFRICA_ABIDJAN);
    zoneTabs.put(TZ_ID_AFRICA_ACCRA.getName(), TZ_ID_AFRICA_ACCRA);
    zoneTabs.put(TZ_ID_AFRICA_ADDIS_ABABA.getName(), TZ_ID_AFRICA_ADDIS_ABABA);
    zoneTabs.put(TZ_ID_AFRICA_ALGIERS.getName(), TZ_ID_AFRICA_ALGIERS);
    zoneTabs.put(TZ_ID_AFRICA_ASMARA.getName(), TZ_ID_AFRICA_ASMARA);
    zoneTabs.put(TZ_ID_AFRICA_ASMERA.getName(), TZ_ID_AFRICA_ASMERA);
    zoneTabs.put(TZ_ID_AFRICA_BAMAKO.getName(), TZ_ID_AFRICA_BAMAKO);
    zoneTabs.put(TZ_ID_AFRICA_BANGUI.getName(), TZ_ID_AFRICA_BANGUI);
    zoneTabs.put(TZ_ID_AFRICA_BANJUL.getName(), TZ_ID_AFRICA_BANJUL);
    zoneTabs.put(TZ_ID_AFRICA_BISSAU.getName(), TZ_ID_AFRICA_BISSAU);
    zoneTabs.put(TZ_ID_AFRICA_BLANTYRE.getName(), TZ_ID_AFRICA_BLANTYRE);
    zoneTabs.put(TZ_ID_AFRICA_BRAZZAVILLE.getName(), TZ_ID_AFRICA_BRAZZAVILLE);
    zoneTabs.put(TZ_ID_AFRICA_BUJUMBURA.getName(), TZ_ID_AFRICA_BUJUMBURA);
    zoneTabs.put(TZ_ID_AFRICA_CAIRO.getName(), TZ_ID_AFRICA_CAIRO);
    zoneTabs.put(TZ_ID_AFRICA_CASABLANCA.getName(), TZ_ID_AFRICA_CASABLANCA);
    zoneTabs.put(TZ_ID_AFRICA_CEUTA.getName(), TZ_ID_AFRICA_CEUTA);
    zoneTabs.put(TZ_ID_AFRICA_CONAKRY.getName(), TZ_ID_AFRICA_CONAKRY);
    zoneTabs.put(TZ_ID_AFRICA_DAKAR.getName(), TZ_ID_AFRICA_DAKAR);
    zoneTabs.put(TZ_ID_AFRICA_DAR_ES_SALAAM.getName(), TZ_ID_AFRICA_DAR_ES_SALAAM);
    zoneTabs.put(TZ_ID_AFRICA_DJIBOUTI.getName(), TZ_ID_AFRICA_DJIBOUTI);
    zoneTabs.put(TZ_ID_AFRICA_DOUALA.getName(), TZ_ID_AFRICA_DOUALA);
    zoneTabs.put(TZ_ID_AFRICA_EL_AAIUN.getName(), TZ_ID_AFRICA_EL_AAIUN);
    zoneTabs.put(TZ_ID_AFRICA_FREETOWN.getName(), TZ_ID_AFRICA_FREETOWN);
    zoneTabs.put(TZ_ID_AFRICA_GABORONE.getName(), TZ_ID_AFRICA_GABORONE);
    zoneTabs.put(TZ_ID_AFRICA_HARARE.getName(), TZ_ID_AFRICA_HARARE);
    zoneTabs.put(TZ_ID_AFRICA_JOHANNESBURG.getName(), TZ_ID_AFRICA_JOHANNESBURG);
    zoneTabs.put(TZ_ID_AFRICA_JUBA.getName(), TZ_ID_AFRICA_JUBA);
    zoneTabs.put(TZ_ID_AFRICA_KAMPALA.getName(), TZ_ID_AFRICA_KAMPALA);
    zoneTabs.put(TZ_ID_AFRICA_KHARTOUM.getName(), TZ_ID_AFRICA_KHARTOUM);
    zoneTabs.put(TZ_ID_AFRICA_KIGALI.getName(), TZ_ID_AFRICA_KIGALI);
    zoneTabs.put(TZ_ID_AFRICA_KINSHASA.getName(), TZ_ID_AFRICA_KINSHASA);
    zoneTabs.put(TZ_ID_AFRICA_LAGOS.getName(), TZ_ID_AFRICA_LAGOS);
    zoneTabs.put(TZ_ID_AFRICA_LIBREVILLE.getName(), TZ_ID_AFRICA_LIBREVILLE);
    zoneTabs.put(TZ_ID_AFRICA_LOME.getName(), TZ_ID_AFRICA_LOME);
    zoneTabs.put(TZ_ID_AFRICA_LUANDA.getName(), TZ_ID_AFRICA_LUANDA);
    zoneTabs.put(TZ_ID_AFRICA_LUBUMBASHI.getName(), TZ_ID_AFRICA_LUBUMBASHI);
    zoneTabs.put(TZ_ID_AFRICA_LUSAKA.getName(), TZ_ID_AFRICA_LUSAKA);
    zoneTabs.put(TZ_ID_AFRICA_MALABO.getName(), TZ_ID_AFRICA_MALABO);
    zoneTabs.put(TZ_ID_AFRICA_MAPUTO.getName(), TZ_ID_AFRICA_MAPUTO);
    zoneTabs.put(TZ_ID_AFRICA_MASERU.getName(), TZ_ID_AFRICA_MASERU);
    zoneTabs.put(TZ_ID_AFRICA_MBABANE.getName(), TZ_ID_AFRICA_MBABANE);
    zoneTabs.put(TZ_ID_AFRICA_MOGADISHU.getName(), TZ_ID_AFRICA_MOGADISHU);
    zoneTabs.put(TZ_ID_AFRICA_MONROVIA.getName(), TZ_ID_AFRICA_MONROVIA);
    zoneTabs.put(TZ_ID_AFRICA_NAIROBI.getName(), TZ_ID_AFRICA_NAIROBI);
    zoneTabs.put(TZ_ID_AFRICA_NDJAMENA.getName(), TZ_ID_AFRICA_NDJAMENA);
    zoneTabs.put(TZ_ID_AFRICA_NIAMEY.getName(), TZ_ID_AFRICA_NIAMEY);
    zoneTabs.put(TZ_ID_AFRICA_NOUAKCHOTT.getName(), TZ_ID_AFRICA_NOUAKCHOTT);
    zoneTabs.put(TZ_ID_AFRICA_OUAGADOUGOU.getName(), TZ_ID_AFRICA_OUAGADOUGOU);
    zoneTabs.put(TZ_ID_AFRICA_PORTO_NOVO.getName(), TZ_ID_AFRICA_PORTO_NOVO);
    zoneTabs.put(TZ_ID_AFRICA_SAO_TOME.getName(), TZ_ID_AFRICA_SAO_TOME);
    zoneTabs.put(TZ_ID_AFRICA_TIMBUKTU.getName(), TZ_ID_AFRICA_TIMBUKTU);
    zoneTabs.put(TZ_ID_AFRICA_TRIPOLI.getName(), TZ_ID_AFRICA_TRIPOLI);
    zoneTabs.put(TZ_ID_AFRICA_TUNIS.getName(), TZ_ID_AFRICA_TUNIS);
    zoneTabs.put(TZ_ID_AFRICA_WINDHOEK.getName(), TZ_ID_AFRICA_WINDHOEK);
    zoneTabs.put(TZ_ID_AMERICA_ADAK.getName(), TZ_ID_AMERICA_ADAK);
    zoneTabs.put(TZ_ID_AMERICA_ANCHORAGE.getName(), TZ_ID_AMERICA_ANCHORAGE);
    zoneTabs.put(TZ_ID_AMERICA_ANGUILLA.getName(), TZ_ID_AMERICA_ANGUILLA);
    zoneTabs.put(TZ_ID_AMERICA_ANTIGUA.getName(), TZ_ID_AMERICA_ANTIGUA);
    zoneTabs.put(TZ_ID_AMERICA_ARAGUAINA.getName(), TZ_ID_AMERICA_ARAGUAINA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_BUENOS_AIRES.getName(), TZ_ID_AMERICA_ARGENTINA_BUENOS_AIRES);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_CATAMARCA.getName(), TZ_ID_AMERICA_ARGENTINA_CATAMARCA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_COMODRIVADAVIA.getName(), TZ_ID_AMERICA_ARGENTINA_COMODRIVADAVIA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_CORDOBA.getName(), TZ_ID_AMERICA_ARGENTINA_CORDOBA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_JUJUY.getName(), TZ_ID_AMERICA_ARGENTINA_JUJUY);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_LA_RIOJA.getName(), TZ_ID_AMERICA_ARGENTINA_LA_RIOJA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_MENDOZA.getName(), TZ_ID_AMERICA_ARGENTINA_MENDOZA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_RIO_GALLEGOS.getName(), TZ_ID_AMERICA_ARGENTINA_RIO_GALLEGOS);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_SALTA.getName(), TZ_ID_AMERICA_ARGENTINA_SALTA);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_SAN_JUAN.getName(), TZ_ID_AMERICA_ARGENTINA_SAN_JUAN);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_SAN_LUIS.getName(), TZ_ID_AMERICA_ARGENTINA_SAN_LUIS);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_TUCUMAN.getName(), TZ_ID_AMERICA_ARGENTINA_TUCUMAN);
    zoneTabs.put(TZ_ID_AMERICA_ARGENTINA_USHUAIA.getName(), TZ_ID_AMERICA_ARGENTINA_USHUAIA);
    zoneTabs.put(TZ_ID_AMERICA_ARUBA.getName(), TZ_ID_AMERICA_ARUBA);
    zoneTabs.put(TZ_ID_AMERICA_ASUNCION.getName(), TZ_ID_AMERICA_ASUNCION);
    zoneTabs.put(TZ_ID_AMERICA_ATIKOKAN.getName(), TZ_ID_AMERICA_ATIKOKAN);
    zoneTabs.put(TZ_ID_AMERICA_ATKA.getName(), TZ_ID_AMERICA_ATKA);
    zoneTabs.put(TZ_ID_AMERICA_BAHIA.getName(), TZ_ID_AMERICA_BAHIA);
    zoneTabs.put(TZ_ID_AMERICA_BAHIA_BANDERAS.getName(), TZ_ID_AMERICA_BAHIA_BANDERAS);
    zoneTabs.put(TZ_ID_AMERICA_BARBADOS.getName(), TZ_ID_AMERICA_BARBADOS);
    zoneTabs.put(TZ_ID_AMERICA_BELEM.getName(), TZ_ID_AMERICA_BELEM);
    zoneTabs.put(TZ_ID_AMERICA_BELIZE.getName(), TZ_ID_AMERICA_BELIZE);
    zoneTabs.put(TZ_ID_AMERICA_BLANC_SABLON.getName(), TZ_ID_AMERICA_BLANC_SABLON);
    zoneTabs.put(TZ_ID_AMERICA_BOA_VISTA.getName(), TZ_ID_AMERICA_BOA_VISTA);
    zoneTabs.put(TZ_ID_AMERICA_BOGOTA.getName(), TZ_ID_AMERICA_BOGOTA);
    zoneTabs.put(TZ_ID_AMERICA_BOISE.getName(), TZ_ID_AMERICA_BOISE);
    zoneTabs.put(TZ_ID_AMERICA_BUENOS_AIRES.getName(), TZ_ID_AMERICA_BUENOS_AIRES);
    zoneTabs.put(TZ_ID_AMERICA_CAMBRIDGE_BAY.getName(), TZ_ID_AMERICA_CAMBRIDGE_BAY);
    zoneTabs.put(TZ_ID_AMERICA_CAMPO_GRANDE.getName(), TZ_ID_AMERICA_CAMPO_GRANDE);
    zoneTabs.put(TZ_ID_AMERICA_CANCUN.getName(), TZ_ID_AMERICA_CANCUN);
    zoneTabs.put(TZ_ID_AMERICA_CARACAS.getName(), TZ_ID_AMERICA_CARACAS);
    zoneTabs.put(TZ_ID_AMERICA_CATAMARCA.getName(), TZ_ID_AMERICA_CATAMARCA);
    zoneTabs.put(TZ_ID_AMERICA_CAYENNE.getName(), TZ_ID_AMERICA_CAYENNE);
    zoneTabs.put(TZ_ID_AMERICA_CAYMAN.getName(), TZ_ID_AMERICA_CAYMAN);
    zoneTabs.put(TZ_ID_AMERICA_CHICAGO.getName(), TZ_ID_AMERICA_CHICAGO);
    zoneTabs.put(TZ_ID_AMERICA_CHIHUAHUA.getName(), TZ_ID_AMERICA_CHIHUAHUA);
    zoneTabs.put(TZ_ID_AMERICA_CORAL_HARBOUR.getName(), TZ_ID_AMERICA_CORAL_HARBOUR);
    zoneTabs.put(TZ_ID_AMERICA_CORDOBA.getName(), TZ_ID_AMERICA_CORDOBA);
    zoneTabs.put(TZ_ID_AMERICA_COSTA_RICA.getName(), TZ_ID_AMERICA_COSTA_RICA);
    zoneTabs.put(TZ_ID_AMERICA_CUIABA.getName(), TZ_ID_AMERICA_CUIABA);
    zoneTabs.put(TZ_ID_AMERICA_CURACAO.getName(), TZ_ID_AMERICA_CURACAO);
    zoneTabs.put(TZ_ID_AMERICA_DANMARKSHAVN.getName(), TZ_ID_AMERICA_DANMARKSHAVN);
    zoneTabs.put(TZ_ID_AMERICA_DAWSON.getName(), TZ_ID_AMERICA_DAWSON);
    zoneTabs.put(TZ_ID_AMERICA_DAWSON_CREEK.getName(), TZ_ID_AMERICA_DAWSON_CREEK);
    zoneTabs.put(TZ_ID_AMERICA_DENVER.getName(), TZ_ID_AMERICA_DENVER);
    zoneTabs.put(TZ_ID_AMERICA_DETROIT.getName(), TZ_ID_AMERICA_DETROIT);
    zoneTabs.put(TZ_ID_AMERICA_DOMINICA.getName(), TZ_ID_AMERICA_DOMINICA);
    zoneTabs.put(TZ_ID_AMERICA_EDMONTON.getName(), TZ_ID_AMERICA_EDMONTON);
    zoneTabs.put(TZ_ID_AMERICA_EIRUNEPE.getName(), TZ_ID_AMERICA_EIRUNEPE);
    zoneTabs.put(TZ_ID_AMERICA_EL_SALVADOR.getName(), TZ_ID_AMERICA_EL_SALVADOR);
    zoneTabs.put(TZ_ID_AMERICA_ENSENADA.getName(), TZ_ID_AMERICA_ENSENADA);
    zoneTabs.put(TZ_ID_AMERICA_FORT_WAYNE.getName(), TZ_ID_AMERICA_FORT_WAYNE);
    zoneTabs.put(TZ_ID_AMERICA_FORTALEZA.getName(), TZ_ID_AMERICA_FORTALEZA);
    zoneTabs.put(TZ_ID_AMERICA_GLACE_BAY.getName(), TZ_ID_AMERICA_GLACE_BAY);
    zoneTabs.put(TZ_ID_AMERICA_GODTHAB.getName(), TZ_ID_AMERICA_GODTHAB);
    zoneTabs.put(TZ_ID_AMERICA_GOOSE_BAY.getName(), TZ_ID_AMERICA_GOOSE_BAY);
    zoneTabs.put(TZ_ID_AMERICA_GRAND_TURK.getName(), TZ_ID_AMERICA_GRAND_TURK);
    zoneTabs.put(TZ_ID_AMERICA_GRENADA.getName(), TZ_ID_AMERICA_GRENADA);
    zoneTabs.put(TZ_ID_AMERICA_GUADELOUPE.getName(), TZ_ID_AMERICA_GUADELOUPE);
    zoneTabs.put(TZ_ID_AMERICA_GUATEMALA.getName(), TZ_ID_AMERICA_GUATEMALA);
    zoneTabs.put(TZ_ID_AMERICA_GUAYAQUIL.getName(), TZ_ID_AMERICA_GUAYAQUIL);
    zoneTabs.put(TZ_ID_AMERICA_GUYANA.getName(), TZ_ID_AMERICA_GUYANA);
    zoneTabs.put(TZ_ID_AMERICA_HALIFAX.getName(), TZ_ID_AMERICA_HALIFAX);
    zoneTabs.put(TZ_ID_AMERICA_HAVANA.getName(), TZ_ID_AMERICA_HAVANA);
    zoneTabs.put(TZ_ID_AMERICA_HERMOSILLO.getName(), TZ_ID_AMERICA_HERMOSILLO);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_INDIANAPOLIS.getName(), TZ_ID_AMERICA_INDIANA_INDIANAPOLIS);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_KNOX.getName(), TZ_ID_AMERICA_INDIANA_KNOX);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_MARENGO.getName(), TZ_ID_AMERICA_INDIANA_MARENGO);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_PETERSBURG.getName(), TZ_ID_AMERICA_INDIANA_PETERSBURG);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_TELL_CITY.getName(), TZ_ID_AMERICA_INDIANA_TELL_CITY);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_VEVAY.getName(), TZ_ID_AMERICA_INDIANA_VEVAY);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_VINCENNES.getName(), TZ_ID_AMERICA_INDIANA_VINCENNES);
    zoneTabs.put(TZ_ID_AMERICA_INDIANA_WINAMAC.getName(), TZ_ID_AMERICA_INDIANA_WINAMAC);
    zoneTabs.put(TZ_ID_AMERICA_INDIANAPOLIS.getName(), TZ_ID_AMERICA_INDIANAPOLIS);
    zoneTabs.put(TZ_ID_AMERICA_INUVIK.getName(), TZ_ID_AMERICA_INUVIK);
    zoneTabs.put(TZ_ID_AMERICA_IQALUIT.getName(), TZ_ID_AMERICA_IQALUIT);
    zoneTabs.put(TZ_ID_AMERICA_JAMAICA.getName(), TZ_ID_AMERICA_JAMAICA);
    zoneTabs.put(TZ_ID_AMERICA_JUJUY.getName(), TZ_ID_AMERICA_JUJUY);
    zoneTabs.put(TZ_ID_AMERICA_JUNEAU.getName(), TZ_ID_AMERICA_JUNEAU);
    zoneTabs.put(TZ_ID_AMERICA_KENTUCKY_LOUISVILLE.getName(), TZ_ID_AMERICA_KENTUCKY_LOUISVILLE);
    zoneTabs.put(TZ_ID_AMERICA_KENTUCKY_MONTICELLO.getName(), TZ_ID_AMERICA_KENTUCKY_MONTICELLO);
    zoneTabs.put(TZ_ID_AMERICA_KNOX_IN.getName(), TZ_ID_AMERICA_KNOX_IN);
    zoneTabs.put(TZ_ID_AMERICA_KRALENDIJK.getName(), TZ_ID_AMERICA_KRALENDIJK);
    zoneTabs.put(TZ_ID_AMERICA_LA_PAZ.getName(), TZ_ID_AMERICA_LA_PAZ);
    zoneTabs.put(TZ_ID_AMERICA_LIMA.getName(), TZ_ID_AMERICA_LIMA);
    zoneTabs.put(TZ_ID_AMERICA_LOS_ANGELES.getName(), TZ_ID_AMERICA_LOS_ANGELES);
    zoneTabs.put(TZ_ID_AMERICA_LOUISVILLE.getName(), TZ_ID_AMERICA_LOUISVILLE);
    zoneTabs.put(TZ_ID_AMERICA_LOWER_PRINCES.getName(), TZ_ID_AMERICA_LOWER_PRINCES);
    zoneTabs.put(TZ_ID_AMERICA_MACEIO.getName(), TZ_ID_AMERICA_MACEIO);
    zoneTabs.put(TZ_ID_AMERICA_MANAGUA.getName(), TZ_ID_AMERICA_MANAGUA);
    zoneTabs.put(TZ_ID_AMERICA_MANAUS.getName(), TZ_ID_AMERICA_MANAUS);
    zoneTabs.put(TZ_ID_AMERICA_MARIGOT.getName(), TZ_ID_AMERICA_MARIGOT);
    zoneTabs.put(TZ_ID_AMERICA_MARTINIQUE.getName(), TZ_ID_AMERICA_MARTINIQUE);
    zoneTabs.put(TZ_ID_AMERICA_MATAMOROS.getName(), TZ_ID_AMERICA_MATAMOROS);
    zoneTabs.put(TZ_ID_AMERICA_MAZATLAN.getName(), TZ_ID_AMERICA_MAZATLAN);
    zoneTabs.put(TZ_ID_AMERICA_MENDOZA.getName(), TZ_ID_AMERICA_MENDOZA);
    zoneTabs.put(TZ_ID_AMERICA_MENOMINEE.getName(), TZ_ID_AMERICA_MENOMINEE);
    zoneTabs.put(TZ_ID_AMERICA_MERIDA.getName(), TZ_ID_AMERICA_MERIDA);
    zoneTabs.put(TZ_ID_AMERICA_METLAKATLA.getName(), TZ_ID_AMERICA_METLAKATLA);
    zoneTabs.put(TZ_ID_AMERICA_MEXICO_CITY.getName(), TZ_ID_AMERICA_MEXICO_CITY);
    zoneTabs.put(TZ_ID_AMERICA_MIQUELON.getName(), TZ_ID_AMERICA_MIQUELON);
    zoneTabs.put(TZ_ID_AMERICA_MONCTON.getName(), TZ_ID_AMERICA_MONCTON);
    zoneTabs.put(TZ_ID_AMERICA_MONTERREY.getName(), TZ_ID_AMERICA_MONTERREY);
    zoneTabs.put(TZ_ID_AMERICA_MONTEVIDEO.getName(), TZ_ID_AMERICA_MONTEVIDEO);
    zoneTabs.put(TZ_ID_AMERICA_MONTREAL.getName(), TZ_ID_AMERICA_MONTREAL);
    zoneTabs.put(TZ_ID_AMERICA_MONTSERRAT.getName(), TZ_ID_AMERICA_MONTSERRAT);
    zoneTabs.put(TZ_ID_AMERICA_NASSAU.getName(), TZ_ID_AMERICA_NASSAU);
    zoneTabs.put(TZ_ID_AMERICA_NEW_YORK.getName(), TZ_ID_AMERICA_NEW_YORK);
    zoneTabs.put(TZ_ID_AMERICA_NIPIGON.getName(), TZ_ID_AMERICA_NIPIGON);
    zoneTabs.put(TZ_ID_AMERICA_NOME.getName(), TZ_ID_AMERICA_NOME);
    zoneTabs.put(TZ_ID_AMERICA_NORONHA.getName(), TZ_ID_AMERICA_NORONHA);
    zoneTabs.put(TZ_ID_AMERICA_NORTH_DAKOTA_BEULAH.getName(), TZ_ID_AMERICA_NORTH_DAKOTA_BEULAH);
    zoneTabs.put(TZ_ID_AMERICA_NORTH_DAKOTA_CENTER.getName(), TZ_ID_AMERICA_NORTH_DAKOTA_CENTER);
    zoneTabs.put(TZ_ID_AMERICA_NORTH_DAKOTA_NEW_SALEM.getName(), TZ_ID_AMERICA_NORTH_DAKOTA_NEW_SALEM);
    zoneTabs.put(TZ_ID_AMERICA_OJINAGA.getName(), TZ_ID_AMERICA_OJINAGA);
    zoneTabs.put(TZ_ID_AMERICA_PANAMA.getName(), TZ_ID_AMERICA_PANAMA);
    zoneTabs.put(TZ_ID_AMERICA_PANGNIRTUNG.getName(), TZ_ID_AMERICA_PANGNIRTUNG);
    zoneTabs.put(TZ_ID_AMERICA_PARAMARIBO.getName(), TZ_ID_AMERICA_PARAMARIBO);
    zoneTabs.put(TZ_ID_AMERICA_PHOENIX.getName(), TZ_ID_AMERICA_PHOENIX);
    zoneTabs.put(TZ_ID_AMERICA_PORT_AU_PRINCE.getName(), TZ_ID_AMERICA_PORT_AU_PRINCE);
    zoneTabs.put(TZ_ID_AMERICA_PORT_OF_SPAIN.getName(), TZ_ID_AMERICA_PORT_OF_SPAIN);
    zoneTabs.put(TZ_ID_AMERICA_PORTO_ACRE.getName(), TZ_ID_AMERICA_PORTO_ACRE);
    zoneTabs.put(TZ_ID_AMERICA_PORTO_VELHO.getName(), TZ_ID_AMERICA_PORTO_VELHO);
    zoneTabs.put(TZ_ID_AMERICA_PUERTO_RICO.getName(), TZ_ID_AMERICA_PUERTO_RICO);
    zoneTabs.put(TZ_ID_AMERICA_RAINY_RIVER.getName(), TZ_ID_AMERICA_RAINY_RIVER);
    zoneTabs.put(TZ_ID_AMERICA_RANKIN_INLET.getName(), TZ_ID_AMERICA_RANKIN_INLET);
    zoneTabs.put(TZ_ID_AMERICA_RECIFE.getName(), TZ_ID_AMERICA_RECIFE);
    zoneTabs.put(TZ_ID_AMERICA_REGINA.getName(), TZ_ID_AMERICA_REGINA);
    zoneTabs.put(TZ_ID_AMERICA_RESOLUTE.getName(), TZ_ID_AMERICA_RESOLUTE);
    zoneTabs.put(TZ_ID_AMERICA_RIO_BRANCO.getName(), TZ_ID_AMERICA_RIO_BRANCO);
    zoneTabs.put(TZ_ID_AMERICA_ROSARIO.getName(), TZ_ID_AMERICA_ROSARIO);
    zoneTabs.put(TZ_ID_AMERICA_SANTA_ISABEL.getName(), TZ_ID_AMERICA_SANTA_ISABEL);
    zoneTabs.put(TZ_ID_AMERICA_SANTAREM.getName(), TZ_ID_AMERICA_SANTAREM);
    zoneTabs.put(TZ_ID_AMERICA_SANTIAGO.getName(), TZ_ID_AMERICA_SANTIAGO);
    zoneTabs.put(TZ_ID_AMERICA_SANTO_DOMINGO.getName(), TZ_ID_AMERICA_SANTO_DOMINGO);
    zoneTabs.put(TZ_ID_AMERICA_SAO_PAULO.getName(), TZ_ID_AMERICA_SAO_PAULO);
    zoneTabs.put(TZ_ID_AMERICA_SCORESBYSUND.getName(), TZ_ID_AMERICA_SCORESBYSUND);
    zoneTabs.put(TZ_ID_AMERICA_SHIPROCK.getName(), TZ_ID_AMERICA_SHIPROCK);
    zoneTabs.put(TZ_ID_AMERICA_SITKA.getName(), TZ_ID_AMERICA_SITKA);
    zoneTabs.put(TZ_ID_AMERICA_ST_BARTHELEMY.getName(), TZ_ID_AMERICA_ST_BARTHELEMY);
    zoneTabs.put(TZ_ID_AMERICA_ST_JOHNS.getName(), TZ_ID_AMERICA_ST_JOHNS);
    zoneTabs.put(TZ_ID_AMERICA_ST_KITTS.getName(), TZ_ID_AMERICA_ST_KITTS);
    zoneTabs.put(TZ_ID_AMERICA_ST_LUCIA.getName(), TZ_ID_AMERICA_ST_LUCIA);
    zoneTabs.put(TZ_ID_AMERICA_ST_THOMAS.getName(), TZ_ID_AMERICA_ST_THOMAS);
    zoneTabs.put(TZ_ID_AMERICA_ST_VINCENT.getName(), TZ_ID_AMERICA_ST_VINCENT);
    zoneTabs.put(TZ_ID_AMERICA_SWIFT_CURRENT.getName(), TZ_ID_AMERICA_SWIFT_CURRENT);
    zoneTabs.put(TZ_ID_AMERICA_TEGUCIGALPA.getName(), TZ_ID_AMERICA_TEGUCIGALPA);
    zoneTabs.put(TZ_ID_AMERICA_THULE.getName(), TZ_ID_AMERICA_THULE);
    zoneTabs.put(TZ_ID_AMERICA_THUNDER_BAY.getName(), TZ_ID_AMERICA_THUNDER_BAY);
    zoneTabs.put(TZ_ID_AMERICA_TIJUANA.getName(), TZ_ID_AMERICA_TIJUANA);
    zoneTabs.put(TZ_ID_AMERICA_TORONTO.getName(), TZ_ID_AMERICA_TORONTO);
    zoneTabs.put(TZ_ID_AMERICA_TORTOLA.getName(), TZ_ID_AMERICA_TORTOLA);
    zoneTabs.put(TZ_ID_AMERICA_VANCOUVER.getName(), TZ_ID_AMERICA_VANCOUVER);
    zoneTabs.put(TZ_ID_AMERICA_VIRGIN.getName(), TZ_ID_AMERICA_VIRGIN);
    zoneTabs.put(TZ_ID_AMERICA_WHITEHORSE.getName(), TZ_ID_AMERICA_WHITEHORSE);
    zoneTabs.put(TZ_ID_AMERICA_WINNIPEG.getName(), TZ_ID_AMERICA_WINNIPEG);
    zoneTabs.put(TZ_ID_AMERICA_YAKUTAT.getName(), TZ_ID_AMERICA_YAKUTAT);
    zoneTabs.put(TZ_ID_AMERICA_YELLOWKNIFE.getName(), TZ_ID_AMERICA_YELLOWKNIFE);
    zoneTabs.put(TZ_ID_ANTARCTICA_CASEY.getName(), TZ_ID_ANTARCTICA_CASEY);
    zoneTabs.put(TZ_ID_ANTARCTICA_DAVIS.getName(), TZ_ID_ANTARCTICA_DAVIS);
    zoneTabs.put(TZ_ID_ANTARCTICA_DUMONTDURVILLE.getName(), TZ_ID_ANTARCTICA_DUMONTDURVILLE);
    zoneTabs.put(TZ_ID_ANTARCTICA_MACQUARIE.getName(), TZ_ID_ANTARCTICA_MACQUARIE);
    zoneTabs.put(TZ_ID_ANTARCTICA_MAWSON.getName(), TZ_ID_ANTARCTICA_MAWSON);
    zoneTabs.put(TZ_ID_ANTARCTICA_MCMURDO.getName(), TZ_ID_ANTARCTICA_MCMURDO);
    zoneTabs.put(TZ_ID_ANTARCTICA_PALMER.getName(), TZ_ID_ANTARCTICA_PALMER);
    zoneTabs.put(TZ_ID_ANTARCTICA_ROTHERA.getName(), TZ_ID_ANTARCTICA_ROTHERA);
    zoneTabs.put(TZ_ID_ANTARCTICA_SOUTH_POLE.getName(), TZ_ID_ANTARCTICA_SOUTH_POLE);
    zoneTabs.put(TZ_ID_ANTARCTICA_SYOWA.getName(), TZ_ID_ANTARCTICA_SYOWA);
    zoneTabs.put(TZ_ID_ANTARCTICA_VOSTOK.getName(), TZ_ID_ANTARCTICA_VOSTOK);
    zoneTabs.put(TZ_ID_ARCTIC_LONGYEARBYEN.getName(), TZ_ID_ARCTIC_LONGYEARBYEN);
    zoneTabs.put(TZ_ID_ASIA_ADEN.getName(), TZ_ID_ASIA_ADEN);
    zoneTabs.put(TZ_ID_ASIA_ALMATY.getName(), TZ_ID_ASIA_ALMATY);
    zoneTabs.put(TZ_ID_ASIA_AMMAN.getName(), TZ_ID_ASIA_AMMAN);
    zoneTabs.put(TZ_ID_ASIA_ANADYR.getName(), TZ_ID_ASIA_ANADYR);
    zoneTabs.put(TZ_ID_ASIA_AQTAU.getName(), TZ_ID_ASIA_AQTAU);
    zoneTabs.put(TZ_ID_ASIA_AQTOBE.getName(), TZ_ID_ASIA_AQTOBE);
    zoneTabs.put(TZ_ID_ASIA_ASHGABAT.getName(), TZ_ID_ASIA_ASHGABAT);
    zoneTabs.put(TZ_ID_ASIA_ASHKHABAD.getName(), TZ_ID_ASIA_ASHKHABAD);
    zoneTabs.put(TZ_ID_ASIA_BAGHDAD.getName(), TZ_ID_ASIA_BAGHDAD);
    zoneTabs.put(TZ_ID_ASIA_BAHRAIN.getName(), TZ_ID_ASIA_BAHRAIN);
    zoneTabs.put(TZ_ID_ASIA_BAKU.getName(), TZ_ID_ASIA_BAKU);
    zoneTabs.put(TZ_ID_ASIA_BANGKOK.getName(), TZ_ID_ASIA_BANGKOK);
    zoneTabs.put(TZ_ID_ASIA_BEIRUT.getName(), TZ_ID_ASIA_BEIRUT);
    zoneTabs.put(TZ_ID_ASIA_BISHKEK.getName(), TZ_ID_ASIA_BISHKEK);
    zoneTabs.put(TZ_ID_ASIA_BRUNEI.getName(), TZ_ID_ASIA_BRUNEI);
    zoneTabs.put(TZ_ID_ASIA_CALCUTTA.getName(), TZ_ID_ASIA_CALCUTTA);
    zoneTabs.put(TZ_ID_ASIA_CHOIBALSAN.getName(), TZ_ID_ASIA_CHOIBALSAN);
    zoneTabs.put(TZ_ID_ASIA_CHONGQING.getName(), TZ_ID_ASIA_CHONGQING);
    zoneTabs.put(TZ_ID_ASIA_CHUNGKING.getName(), TZ_ID_ASIA_CHUNGKING);
    zoneTabs.put(TZ_ID_ASIA_COLOMBO.getName(), TZ_ID_ASIA_COLOMBO);
    zoneTabs.put(TZ_ID_ASIA_DACCA.getName(), TZ_ID_ASIA_DACCA);
    zoneTabs.put(TZ_ID_ASIA_DAMASCUS.getName(), TZ_ID_ASIA_DAMASCUS);
    zoneTabs.put(TZ_ID_ASIA_DHAKA.getName(), TZ_ID_ASIA_DHAKA);
    zoneTabs.put(TZ_ID_ASIA_DILI.getName(), TZ_ID_ASIA_DILI);
    zoneTabs.put(TZ_ID_ASIA_DUBAI.getName(), TZ_ID_ASIA_DUBAI);
    zoneTabs.put(TZ_ID_ASIA_DUSHANBE.getName(), TZ_ID_ASIA_DUSHANBE);
    zoneTabs.put(TZ_ID_ASIA_GAZA.getName(), TZ_ID_ASIA_GAZA);
    zoneTabs.put(TZ_ID_ASIA_HARBIN.getName(), TZ_ID_ASIA_HARBIN);
    zoneTabs.put(TZ_ID_ASIA_HEBRON.getName(), TZ_ID_ASIA_HEBRON);
    zoneTabs.put(TZ_ID_ASIA_HO_CHI_MINH.getName(), TZ_ID_ASIA_HO_CHI_MINH);
    zoneTabs.put(TZ_ID_ASIA_HONG_KONG.getName(), TZ_ID_ASIA_HONG_KONG);
    zoneTabs.put(TZ_ID_ASIA_HOVD.getName(), TZ_ID_ASIA_HOVD);
    zoneTabs.put(TZ_ID_ASIA_IRKUTSK.getName(), TZ_ID_ASIA_IRKUTSK);
    zoneTabs.put(TZ_ID_ASIA_ISTANBUL.getName(), TZ_ID_ASIA_ISTANBUL);
    zoneTabs.put(TZ_ID_ASIA_JAKARTA.getName(), TZ_ID_ASIA_JAKARTA);
    zoneTabs.put(TZ_ID_ASIA_JAYAPURA.getName(), TZ_ID_ASIA_JAYAPURA);
    zoneTabs.put(TZ_ID_ASIA_JERUSALEM.getName(), TZ_ID_ASIA_JERUSALEM);
    zoneTabs.put(TZ_ID_ASIA_KABUL.getName(), TZ_ID_ASIA_KABUL);
    zoneTabs.put(TZ_ID_ASIA_KAMCHATKA.getName(), TZ_ID_ASIA_KAMCHATKA);
    zoneTabs.put(TZ_ID_ASIA_KARACHI.getName(), TZ_ID_ASIA_KARACHI);
    zoneTabs.put(TZ_ID_ASIA_KASHGAR.getName(), TZ_ID_ASIA_KASHGAR);
    zoneTabs.put(TZ_ID_ASIA_KATHMANDU.getName(), TZ_ID_ASIA_KATHMANDU);
    zoneTabs.put(TZ_ID_ASIA_KATMANDU.getName(), TZ_ID_ASIA_KATMANDU);
    zoneTabs.put(TZ_ID_ASIA_KOLKATA.getName(), TZ_ID_ASIA_KOLKATA);
    zoneTabs.put(TZ_ID_ASIA_KRASNOYARSK.getName(), TZ_ID_ASIA_KRASNOYARSK);
    zoneTabs.put(TZ_ID_ASIA_KUALA_LUMPUR.getName(), TZ_ID_ASIA_KUALA_LUMPUR);
    zoneTabs.put(TZ_ID_ASIA_KUCHING.getName(), TZ_ID_ASIA_KUCHING);
    zoneTabs.put(TZ_ID_ASIA_KUWAIT.getName(), TZ_ID_ASIA_KUWAIT);
    zoneTabs.put(TZ_ID_ASIA_MACAO.getName(), TZ_ID_ASIA_MACAO);
    zoneTabs.put(TZ_ID_ASIA_MACAU.getName(), TZ_ID_ASIA_MACAU);
    zoneTabs.put(TZ_ID_ASIA_MAGADAN.getName(), TZ_ID_ASIA_MAGADAN);
    zoneTabs.put(TZ_ID_ASIA_MAKASSAR.getName(), TZ_ID_ASIA_MAKASSAR);
    zoneTabs.put(TZ_ID_ASIA_MANILA.getName(), TZ_ID_ASIA_MANILA);
    zoneTabs.put(TZ_ID_ASIA_MUSCAT.getName(), TZ_ID_ASIA_MUSCAT);
    zoneTabs.put(TZ_ID_ASIA_NICOSIA.getName(), TZ_ID_ASIA_NICOSIA);
    zoneTabs.put(TZ_ID_ASIA_NOVOKUZNETSK.getName(), TZ_ID_ASIA_NOVOKUZNETSK);
    zoneTabs.put(TZ_ID_ASIA_NOVOSIBIRSK.getName(), TZ_ID_ASIA_NOVOSIBIRSK);
    zoneTabs.put(TZ_ID_ASIA_OMSK.getName(), TZ_ID_ASIA_OMSK);
    zoneTabs.put(TZ_ID_ASIA_ORAL.getName(), TZ_ID_ASIA_ORAL);
    zoneTabs.put(TZ_ID_ASIA_PHNOM_PENH.getName(), TZ_ID_ASIA_PHNOM_PENH);
    zoneTabs.put(TZ_ID_ASIA_PONTIANAK.getName(), TZ_ID_ASIA_PONTIANAK);
    zoneTabs.put(TZ_ID_ASIA_PYONGYANG.getName(), TZ_ID_ASIA_PYONGYANG);
    zoneTabs.put(TZ_ID_ASIA_QATAR.getName(), TZ_ID_ASIA_QATAR);
    zoneTabs.put(TZ_ID_ASIA_QYZYLORDA.getName(), TZ_ID_ASIA_QYZYLORDA);
    zoneTabs.put(TZ_ID_ASIA_RANGOON.getName(), TZ_ID_ASIA_RANGOON);
    zoneTabs.put(TZ_ID_ASIA_RIYADH.getName(), TZ_ID_ASIA_RIYADH);
    zoneTabs.put(TZ_ID_ASIA_RIYADH87.getName(), TZ_ID_ASIA_RIYADH87);
    zoneTabs.put(TZ_ID_ASIA_RIYADH88.getName(), TZ_ID_ASIA_RIYADH88);
    zoneTabs.put(TZ_ID_ASIA_RIYADH89.getName(), TZ_ID_ASIA_RIYADH89);
    zoneTabs.put(TZ_ID_ASIA_SAIGON.getName(), TZ_ID_ASIA_SAIGON);
    zoneTabs.put(TZ_ID_ASIA_SAKHALIN.getName(), TZ_ID_ASIA_SAKHALIN);
    zoneTabs.put(TZ_ID_ASIA_SAMARKAND.getName(), TZ_ID_ASIA_SAMARKAND);
    zoneTabs.put(TZ_ID_ASIA_SEOUL.getName(), TZ_ID_ASIA_SEOUL);
    zoneTabs.put(TZ_ID_ASIA_SHANGHAI.getName(), TZ_ID_ASIA_SHANGHAI);
    zoneTabs.put(TZ_ID_ASIA_SINGAPORE.getName(), TZ_ID_ASIA_SINGAPORE);
    zoneTabs.put(TZ_ID_ASIA_TAIPEI.getName(), TZ_ID_ASIA_TAIPEI);
    zoneTabs.put(TZ_ID_ASIA_TASHKENT.getName(), TZ_ID_ASIA_TASHKENT);
    zoneTabs.put(TZ_ID_ASIA_TBILISI.getName(), TZ_ID_ASIA_TBILISI);
    zoneTabs.put(TZ_ID_ASIA_TEHRAN.getName(), TZ_ID_ASIA_TEHRAN);
    zoneTabs.put(TZ_ID_ASIA_TEL_AVIV.getName(), TZ_ID_ASIA_TEL_AVIV);
    zoneTabs.put(TZ_ID_ASIA_THIMBU.getName(), TZ_ID_ASIA_THIMBU);
    zoneTabs.put(TZ_ID_ASIA_THIMPHU.getName(), TZ_ID_ASIA_THIMPHU);
    zoneTabs.put(TZ_ID_ASIA_TOKYO.getName(), TZ_ID_ASIA_TOKYO);
    zoneTabs.put(TZ_ID_ASIA_UJUNG_PANDANG.getName(), TZ_ID_ASIA_UJUNG_PANDANG);
    zoneTabs.put(TZ_ID_ASIA_ULAANBAATAR.getName(), TZ_ID_ASIA_ULAANBAATAR);
    zoneTabs.put(TZ_ID_ASIA_ULAN_BATOR.getName(), TZ_ID_ASIA_ULAN_BATOR);
    zoneTabs.put(TZ_ID_ASIA_URUMQI.getName(), TZ_ID_ASIA_URUMQI);
    zoneTabs.put(TZ_ID_ASIA_VIENTIANE.getName(), TZ_ID_ASIA_VIENTIANE);
    zoneTabs.put(TZ_ID_ASIA_VLADIVOSTOK.getName(), TZ_ID_ASIA_VLADIVOSTOK);
    zoneTabs.put(TZ_ID_ASIA_YAKUTSK.getName(), TZ_ID_ASIA_YAKUTSK);
    zoneTabs.put(TZ_ID_ASIA_YEKATERINBURG.getName(), TZ_ID_ASIA_YEKATERINBURG);
    zoneTabs.put(TZ_ID_ASIA_YEREVAN.getName(), TZ_ID_ASIA_YEREVAN);
    zoneTabs.put(TZ_ID_ATLANTIC_AZORES.getName(), TZ_ID_ATLANTIC_AZORES);
    zoneTabs.put(TZ_ID_ATLANTIC_BERMUDA.getName(), TZ_ID_ATLANTIC_BERMUDA);
    zoneTabs.put(TZ_ID_ATLANTIC_CANARY.getName(), TZ_ID_ATLANTIC_CANARY);
    zoneTabs.put(TZ_ID_ATLANTIC_CAPE_VERDE.getName(), TZ_ID_ATLANTIC_CAPE_VERDE);
    zoneTabs.put(TZ_ID_ATLANTIC_FAEROE.getName(), TZ_ID_ATLANTIC_FAEROE);
    zoneTabs.put(TZ_ID_ATLANTIC_FAROE.getName(), TZ_ID_ATLANTIC_FAROE);
    zoneTabs.put(TZ_ID_ATLANTIC_JAN_MAYEN.getName(), TZ_ID_ATLANTIC_JAN_MAYEN);
    zoneTabs.put(TZ_ID_ATLANTIC_MADEIRA.getName(), TZ_ID_ATLANTIC_MADEIRA);
    zoneTabs.put(TZ_ID_ATLANTIC_REYKJAVIK.getName(), TZ_ID_ATLANTIC_REYKJAVIK);
    zoneTabs.put(TZ_ID_ATLANTIC_SOUTH_GEORGIA.getName(), TZ_ID_ATLANTIC_SOUTH_GEORGIA);
    zoneTabs.put(TZ_ID_ATLANTIC_ST_HELENA.getName(), TZ_ID_ATLANTIC_ST_HELENA);
    zoneTabs.put(TZ_ID_ATLANTIC_STANLEY.getName(), TZ_ID_ATLANTIC_STANLEY);
    zoneTabs.put(TZ_ID_AUSTRALIA_ACT.getName(), TZ_ID_AUSTRALIA_ACT);
    zoneTabs.put(TZ_ID_AUSTRALIA_ADELAIDE.getName(), TZ_ID_AUSTRALIA_ADELAIDE);
    zoneTabs.put(TZ_ID_AUSTRALIA_BRISBANE.getName(), TZ_ID_AUSTRALIA_BRISBANE);
    zoneTabs.put(TZ_ID_AUSTRALIA_BROKEN_HILL.getName(), TZ_ID_AUSTRALIA_BROKEN_HILL);
    zoneTabs.put(TZ_ID_AUSTRALIA_CANBERRA.getName(), TZ_ID_AUSTRALIA_CANBERRA);
    zoneTabs.put(TZ_ID_AUSTRALIA_CURRIE.getName(), TZ_ID_AUSTRALIA_CURRIE);
    zoneTabs.put(TZ_ID_AUSTRALIA_DARWIN.getName(), TZ_ID_AUSTRALIA_DARWIN);
    zoneTabs.put(TZ_ID_AUSTRALIA_EUCLA.getName(), TZ_ID_AUSTRALIA_EUCLA);
    zoneTabs.put(TZ_ID_AUSTRALIA_HOBART.getName(), TZ_ID_AUSTRALIA_HOBART);
    zoneTabs.put(TZ_ID_AUSTRALIA_LHI.getName(), TZ_ID_AUSTRALIA_LHI);
    zoneTabs.put(TZ_ID_AUSTRALIA_LINDEMAN.getName(), TZ_ID_AUSTRALIA_LINDEMAN);
    zoneTabs.put(TZ_ID_AUSTRALIA_LORD_HOWE.getName(), TZ_ID_AUSTRALIA_LORD_HOWE);
    zoneTabs.put(TZ_ID_AUSTRALIA_MELBOURNE.getName(), TZ_ID_AUSTRALIA_MELBOURNE);
    zoneTabs.put(TZ_ID_AUSTRALIA_NORTH.getName(), TZ_ID_AUSTRALIA_NORTH);
    zoneTabs.put(TZ_ID_AUSTRALIA_NSW.getName(), TZ_ID_AUSTRALIA_NSW);
    zoneTabs.put(TZ_ID_AUSTRALIA_PERTH.getName(), TZ_ID_AUSTRALIA_PERTH);
    zoneTabs.put(TZ_ID_AUSTRALIA_QUEENSLAND.getName(), TZ_ID_AUSTRALIA_QUEENSLAND);
    zoneTabs.put(TZ_ID_AUSTRALIA_SOUTH.getName(), TZ_ID_AUSTRALIA_SOUTH);
    zoneTabs.put(TZ_ID_AUSTRALIA_SYDNEY.getName(), TZ_ID_AUSTRALIA_SYDNEY);
    zoneTabs.put(TZ_ID_AUSTRALIA_TASMANIA.getName(), TZ_ID_AUSTRALIA_TASMANIA);
    zoneTabs.put(TZ_ID_AUSTRALIA_VICTORIA.getName(), TZ_ID_AUSTRALIA_VICTORIA);
    zoneTabs.put(TZ_ID_AUSTRALIA_WEST.getName(), TZ_ID_AUSTRALIA_WEST);
    zoneTabs.put(TZ_ID_AUSTRALIA_YANCOWINNA.getName(), TZ_ID_AUSTRALIA_YANCOWINNA);
    zoneTabs.put(TZ_ID_BRAZIL_ACRE.getName(), TZ_ID_BRAZIL_ACRE);
    zoneTabs.put(TZ_ID_BRAZIL_DENORONHA.getName(), TZ_ID_BRAZIL_DENORONHA);
    zoneTabs.put(TZ_ID_BRAZIL_EAST.getName(), TZ_ID_BRAZIL_EAST);
    zoneTabs.put(TZ_ID_BRAZIL_WEST.getName(), TZ_ID_BRAZIL_WEST);
    zoneTabs.put(TZ_ID_CANADA_ATLANTIC.getName(), TZ_ID_CANADA_ATLANTIC);
    zoneTabs.put(TZ_ID_CANADA_CENTRAL.getName(), TZ_ID_CANADA_CENTRAL);
    zoneTabs.put(TZ_ID_CANADA_EAST_SASKATCHEWAN.getName(), TZ_ID_CANADA_EAST_SASKATCHEWAN);
    zoneTabs.put(TZ_ID_CANADA_EASTERN.getName(), TZ_ID_CANADA_EASTERN);
    zoneTabs.put(TZ_ID_CANADA_MOUNTAIN.getName(), TZ_ID_CANADA_MOUNTAIN);
    zoneTabs.put(TZ_ID_CANADA_NEWFOUNDLAND.getName(), TZ_ID_CANADA_NEWFOUNDLAND);
    zoneTabs.put(TZ_ID_CANADA_PACIFIC.getName(), TZ_ID_CANADA_PACIFIC);
    zoneTabs.put(TZ_ID_CANADA_SASKATCHEWAN.getName(), TZ_ID_CANADA_SASKATCHEWAN);
    zoneTabs.put(TZ_ID_CANADA_YUKON.getName(), TZ_ID_CANADA_YUKON);
    zoneTabs.put(TZ_ID_CET.getName(), TZ_ID_CET);
    zoneTabs.put(TZ_ID_CHILE_CONTINENTAL.getName(), TZ_ID_CHILE_CONTINENTAL);
    zoneTabs.put(TZ_ID_CHILE_EASTERISLAND.getName(), TZ_ID_CHILE_EASTERISLAND);
    zoneTabs.put(TZ_ID_CST6CDT.getName(), TZ_ID_CST6CDT);
    zoneTabs.put(TZ_ID_CUBA.getName(), TZ_ID_CUBA);
    zoneTabs.put(TZ_ID_EET.getName(), TZ_ID_EET);
    zoneTabs.put(TZ_ID_EGYPT.getName(), TZ_ID_EGYPT);
    zoneTabs.put(TZ_ID_EIRE.getName(), TZ_ID_EIRE);
    zoneTabs.put(TZ_ID_EST.getName(), TZ_ID_EST);
    zoneTabs.put(TZ_ID_EST5EDT.getName(), TZ_ID_EST5EDT);
    zoneTabs.put(TZ_ID_ETC_GMT.getName(), TZ_ID_ETC_GMT);
    zoneTabs.put(TZ_ID_ETC_GMTW0.getName(), TZ_ID_ETC_GMTW0);
    zoneTabs.put(TZ_ID_ETC_GMTW1.getName(), TZ_ID_ETC_GMTW1);
    zoneTabs.put(TZ_ID_ETC_GMTW10.getName(), TZ_ID_ETC_GMTW10);
    zoneTabs.put(TZ_ID_ETC_GMTW11.getName(), TZ_ID_ETC_GMTW11);
    zoneTabs.put(TZ_ID_ETC_GMTW12.getName(), TZ_ID_ETC_GMTW12);
    zoneTabs.put(TZ_ID_ETC_GMTW2.getName(), TZ_ID_ETC_GMTW2);
    zoneTabs.put(TZ_ID_ETC_GMTW3.getName(), TZ_ID_ETC_GMTW3);
    zoneTabs.put(TZ_ID_ETC_GMTW4.getName(), TZ_ID_ETC_GMTW4);
    zoneTabs.put(TZ_ID_ETC_GMTW5.getName(), TZ_ID_ETC_GMTW5);
    zoneTabs.put(TZ_ID_ETC_GMTW6.getName(), TZ_ID_ETC_GMTW6);
    zoneTabs.put(TZ_ID_ETC_GMTW7.getName(), TZ_ID_ETC_GMTW7);
    zoneTabs.put(TZ_ID_ETC_GMTW8.getName(), TZ_ID_ETC_GMTW8);
    zoneTabs.put(TZ_ID_ETC_GMTW9.getName(), TZ_ID_ETC_GMTW9);
    zoneTabs.put(TZ_ID_ETC_GMTE0.getName(), TZ_ID_ETC_GMTE0);
    zoneTabs.put(TZ_ID_ETC_GMTE1.getName(), TZ_ID_ETC_GMTE1);
    zoneTabs.put(TZ_ID_ETC_GMTE10.getName(), TZ_ID_ETC_GMTE10);
    zoneTabs.put(TZ_ID_ETC_GMTE11.getName(), TZ_ID_ETC_GMTE11);
    zoneTabs.put(TZ_ID_ETC_GMTE12.getName(), TZ_ID_ETC_GMTE12);
    zoneTabs.put(TZ_ID_ETC_GMTE13.getName(), TZ_ID_ETC_GMTE13);
    zoneTabs.put(TZ_ID_ETC_GMTE14.getName(), TZ_ID_ETC_GMTE14);
    zoneTabs.put(TZ_ID_ETC_GMTE2.getName(), TZ_ID_ETC_GMTE2);
    zoneTabs.put(TZ_ID_ETC_GMTE3.getName(), TZ_ID_ETC_GMTE3);
    zoneTabs.put(TZ_ID_ETC_GMTE4.getName(), TZ_ID_ETC_GMTE4);
    zoneTabs.put(TZ_ID_ETC_GMTE5.getName(), TZ_ID_ETC_GMTE5);
    zoneTabs.put(TZ_ID_ETC_GMTE6.getName(), TZ_ID_ETC_GMTE6);
    zoneTabs.put(TZ_ID_ETC_GMTE7.getName(), TZ_ID_ETC_GMTE7);
    zoneTabs.put(TZ_ID_ETC_GMTE8.getName(), TZ_ID_ETC_GMTE8);
    zoneTabs.put(TZ_ID_ETC_GMTE9.getName(), TZ_ID_ETC_GMTE9);
    zoneTabs.put(TZ_ID_ETC_GMT0.getName(), TZ_ID_ETC_GMT0);
    zoneTabs.put(TZ_ID_ETC_GREENWICH.getName(), TZ_ID_ETC_GREENWICH);
    zoneTabs.put(TZ_ID_ETC_UCT.getName(), TZ_ID_ETC_UCT);
    zoneTabs.put(TZ_ID_ETC_UNIVERSAL.getName(), TZ_ID_ETC_UNIVERSAL);
    zoneTabs.put(TZ_ID_ETC_UTC.getName(), TZ_ID_ETC_UTC);
    zoneTabs.put(TZ_ID_ETC_ZULU.getName(), TZ_ID_ETC_ZULU);
    zoneTabs.put(TZ_ID_EUROPE_AMSTERDAM.getName(), TZ_ID_EUROPE_AMSTERDAM);
    zoneTabs.put(TZ_ID_EUROPE_ANDORRA.getName(), TZ_ID_EUROPE_ANDORRA);
    zoneTabs.put(TZ_ID_EUROPE_ATHENS.getName(), TZ_ID_EUROPE_ATHENS);
    zoneTabs.put(TZ_ID_EUROPE_BELFAST.getName(), TZ_ID_EUROPE_BELFAST);
    zoneTabs.put(TZ_ID_EUROPE_BELGRADE.getName(), TZ_ID_EUROPE_BELGRADE);
    zoneTabs.put(TZ_ID_EUROPE_BERLIN.getName(), TZ_ID_EUROPE_BERLIN);
    zoneTabs.put(TZ_ID_EUROPE_BRATISLAVA.getName(), TZ_ID_EUROPE_BRATISLAVA);
    zoneTabs.put(TZ_ID_EUROPE_BRUSSELS.getName(), TZ_ID_EUROPE_BRUSSELS);
    zoneTabs.put(TZ_ID_EUROPE_BUCHAREST.getName(), TZ_ID_EUROPE_BUCHAREST);
    zoneTabs.put(TZ_ID_EUROPE_BUDAPEST.getName(), TZ_ID_EUROPE_BUDAPEST);
    zoneTabs.put(TZ_ID_EUROPE_CHISINAU.getName(), TZ_ID_EUROPE_CHISINAU);
    zoneTabs.put(TZ_ID_EUROPE_COPENHAGEN.getName(), TZ_ID_EUROPE_COPENHAGEN);
    zoneTabs.put(TZ_ID_EUROPE_DUBLIN.getName(), TZ_ID_EUROPE_DUBLIN);
    zoneTabs.put(TZ_ID_EUROPE_GIBRALTAR.getName(), TZ_ID_EUROPE_GIBRALTAR);
    zoneTabs.put(TZ_ID_EUROPE_GUERNSEY.getName(), TZ_ID_EUROPE_GUERNSEY);
    zoneTabs.put(TZ_ID_EUROPE_HELSINKI.getName(), TZ_ID_EUROPE_HELSINKI);
    zoneTabs.put(TZ_ID_EUROPE_ISLE_OF_MAN.getName(), TZ_ID_EUROPE_ISLE_OF_MAN);
    zoneTabs.put(TZ_ID_EUROPE_ISTANBUL.getName(), TZ_ID_EUROPE_ISTANBUL);
    zoneTabs.put(TZ_ID_EUROPE_JERSEY.getName(), TZ_ID_EUROPE_JERSEY);
    zoneTabs.put(TZ_ID_EUROPE_KALININGRAD.getName(), TZ_ID_EUROPE_KALININGRAD);
    zoneTabs.put(TZ_ID_EUROPE_KIEV.getName(), TZ_ID_EUROPE_KIEV);
    zoneTabs.put(TZ_ID_EUROPE_LISBON.getName(), TZ_ID_EUROPE_LISBON);
    zoneTabs.put(TZ_ID_EUROPE_LJUBLJANA.getName(), TZ_ID_EUROPE_LJUBLJANA);
    zoneTabs.put(TZ_ID_EUROPE_LONDON.getName(), TZ_ID_EUROPE_LONDON);
    zoneTabs.put(TZ_ID_EUROPE_LUXEMBOURG.getName(), TZ_ID_EUROPE_LUXEMBOURG);
    zoneTabs.put(TZ_ID_EUROPE_MADRID.getName(), TZ_ID_EUROPE_MADRID);
    zoneTabs.put(TZ_ID_EUROPE_MALTA.getName(), TZ_ID_EUROPE_MALTA);
    zoneTabs.put(TZ_ID_EUROPE_MARIEHAMN.getName(), TZ_ID_EUROPE_MARIEHAMN);
    zoneTabs.put(TZ_ID_EUROPE_MINSK.getName(), TZ_ID_EUROPE_MINSK);
    zoneTabs.put(TZ_ID_EUROPE_MONACO.getName(), TZ_ID_EUROPE_MONACO);
    zoneTabs.put(TZ_ID_EUROPE_MOSCOW.getName(), TZ_ID_EUROPE_MOSCOW);
    zoneTabs.put(TZ_ID_EUROPE_NICOSIA.getName(), TZ_ID_EUROPE_NICOSIA);
    zoneTabs.put(TZ_ID_EUROPE_OSLO.getName(), TZ_ID_EUROPE_OSLO);
    zoneTabs.put(TZ_ID_EUROPE_PARIS.getName(), TZ_ID_EUROPE_PARIS);
    zoneTabs.put(TZ_ID_EUROPE_PODGORICA.getName(), TZ_ID_EUROPE_PODGORICA);
    zoneTabs.put(TZ_ID_EUROPE_PRAGUE.getName(), TZ_ID_EUROPE_PRAGUE);
    zoneTabs.put(TZ_ID_EUROPE_RIGA.getName(), TZ_ID_EUROPE_RIGA);
    zoneTabs.put(TZ_ID_EUROPE_ROME.getName(), TZ_ID_EUROPE_ROME);
    zoneTabs.put(TZ_ID_EUROPE_SAMARA.getName(), TZ_ID_EUROPE_SAMARA);
    zoneTabs.put(TZ_ID_EUROPE_SAN_MARINO.getName(), TZ_ID_EUROPE_SAN_MARINO);
    zoneTabs.put(TZ_ID_EUROPE_SARAJEVO.getName(), TZ_ID_EUROPE_SARAJEVO);
    zoneTabs.put(TZ_ID_EUROPE_SIMFEROPOL.getName(), TZ_ID_EUROPE_SIMFEROPOL);
    zoneTabs.put(TZ_ID_EUROPE_SKOPJE.getName(), TZ_ID_EUROPE_SKOPJE);
    zoneTabs.put(TZ_ID_EUROPE_SOFIA.getName(), TZ_ID_EUROPE_SOFIA);
    zoneTabs.put(TZ_ID_EUROPE_STOCKHOLM.getName(), TZ_ID_EUROPE_STOCKHOLM);
    zoneTabs.put(TZ_ID_EUROPE_TALLINN.getName(), TZ_ID_EUROPE_TALLINN);
    zoneTabs.put(TZ_ID_EUROPE_TIRANE.getName(), TZ_ID_EUROPE_TIRANE);
    zoneTabs.put(TZ_ID_EUROPE_TIRASPOL.getName(), TZ_ID_EUROPE_TIRASPOL);
    zoneTabs.put(TZ_ID_EUROPE_UZHGOROD.getName(), TZ_ID_EUROPE_UZHGOROD);
    zoneTabs.put(TZ_ID_EUROPE_VADUZ.getName(), TZ_ID_EUROPE_VADUZ);
    zoneTabs.put(TZ_ID_EUROPE_VATICAN.getName(), TZ_ID_EUROPE_VATICAN);
    zoneTabs.put(TZ_ID_EUROPE_VIENNA.getName(), TZ_ID_EUROPE_VIENNA);
    zoneTabs.put(TZ_ID_EUROPE_VILNIUS.getName(), TZ_ID_EUROPE_VILNIUS);
    zoneTabs.put(TZ_ID_EUROPE_VOLGOGRAD.getName(), TZ_ID_EUROPE_VOLGOGRAD);
    zoneTabs.put(TZ_ID_EUROPE_WARSAW.getName(), TZ_ID_EUROPE_WARSAW);
    zoneTabs.put(TZ_ID_EUROPE_ZAGREB.getName(), TZ_ID_EUROPE_ZAGREB);
    zoneTabs.put(TZ_ID_EUROPE_ZAPOROZHYE.getName(), TZ_ID_EUROPE_ZAPOROZHYE);
    zoneTabs.put(TZ_ID_EUROPE_ZURICH.getName(), TZ_ID_EUROPE_ZURICH);
    zoneTabs.put(TZ_ID_GB.getName(), TZ_ID_GB);
    zoneTabs.put(TZ_ID_GB_EIRE.getName(), TZ_ID_GB_EIRE);
    zoneTabs.put(TZ_ID_GMT.getName(), TZ_ID_GMT);
    zoneTabs.put(TZ_ID_GMTW0.getName(), TZ_ID_GMTW0);
    zoneTabs.put(TZ_ID_GMTE0.getName(), TZ_ID_GMTE0);
    zoneTabs.put(TZ_ID_GMT0.getName(), TZ_ID_GMT0);
    zoneTabs.put(TZ_ID_GREENWICH.getName(), TZ_ID_GREENWICH);
    zoneTabs.put(TZ_ID_HONGKONG.getName(), TZ_ID_HONGKONG);
    zoneTabs.put(TZ_ID_HST.getName(), TZ_ID_HST);
    zoneTabs.put(TZ_ID_ICELAND.getName(), TZ_ID_ICELAND);
    zoneTabs.put(TZ_ID_INDIAN_ANTANANARIVO.getName(), TZ_ID_INDIAN_ANTANANARIVO);
    zoneTabs.put(TZ_ID_INDIAN_CHAGOS.getName(), TZ_ID_INDIAN_CHAGOS);
    zoneTabs.put(TZ_ID_INDIAN_CHRISTMAS.getName(), TZ_ID_INDIAN_CHRISTMAS);
    zoneTabs.put(TZ_ID_INDIAN_COCOS.getName(), TZ_ID_INDIAN_COCOS);
    zoneTabs.put(TZ_ID_INDIAN_COMORO.getName(), TZ_ID_INDIAN_COMORO);
    zoneTabs.put(TZ_ID_INDIAN_KERGUELEN.getName(), TZ_ID_INDIAN_KERGUELEN);
    zoneTabs.put(TZ_ID_INDIAN_MAHE.getName(), TZ_ID_INDIAN_MAHE);
    zoneTabs.put(TZ_ID_INDIAN_MALDIVES.getName(), TZ_ID_INDIAN_MALDIVES);
    zoneTabs.put(TZ_ID_INDIAN_MAURITIUS.getName(), TZ_ID_INDIAN_MAURITIUS);
    zoneTabs.put(TZ_ID_INDIAN_MAYOTTE.getName(), TZ_ID_INDIAN_MAYOTTE);
    zoneTabs.put(TZ_ID_INDIAN_REUNION.getName(), TZ_ID_INDIAN_REUNION);
    zoneTabs.put(TZ_ID_IRAN.getName(), TZ_ID_IRAN);
    zoneTabs.put(TZ_ID_ISRAEL.getName(), TZ_ID_ISRAEL);
    zoneTabs.put(TZ_ID_JAMAICA.getName(), TZ_ID_JAMAICA);
    zoneTabs.put(TZ_ID_JAPAN.getName(), TZ_ID_JAPAN);
    zoneTabs.put(TZ_ID_KWAJALEIN.getName(), TZ_ID_KWAJALEIN);
    zoneTabs.put(TZ_ID_LIBYA.getName(), TZ_ID_LIBYA);
    zoneTabs.put(TZ_ID_MET.getName(), TZ_ID_MET);
    zoneTabs.put(TZ_ID_MEXICO_BAJANORTE.getName(), TZ_ID_MEXICO_BAJANORTE);
    zoneTabs.put(TZ_ID_MEXICO_BAJASUR.getName(), TZ_ID_MEXICO_BAJASUR);
    zoneTabs.put(TZ_ID_MEXICO_GENERAL.getName(), TZ_ID_MEXICO_GENERAL);
    zoneTabs.put(TZ_ID_MIDEAST_RIYADH87.getName(), TZ_ID_MIDEAST_RIYADH87);
    zoneTabs.put(TZ_ID_MIDEAST_RIYADH88.getName(), TZ_ID_MIDEAST_RIYADH88);
    zoneTabs.put(TZ_ID_MIDEAST_RIYADH89.getName(), TZ_ID_MIDEAST_RIYADH89);
    zoneTabs.put(TZ_ID_MST.getName(), TZ_ID_MST);
    zoneTabs.put(TZ_ID_MST7MDT.getName(), TZ_ID_MST7MDT);
    zoneTabs.put(TZ_ID_NAVAJO.getName(), TZ_ID_NAVAJO);
    zoneTabs.put(TZ_ID_NZ.getName(), TZ_ID_NZ);
    zoneTabs.put(TZ_ID_NZ_CHAT.getName(), TZ_ID_NZ_CHAT);
    zoneTabs.put(TZ_ID_PACIFIC_APIA.getName(), TZ_ID_PACIFIC_APIA);
    zoneTabs.put(TZ_ID_PACIFIC_AUCKLAND.getName(), TZ_ID_PACIFIC_AUCKLAND);
    zoneTabs.put(TZ_ID_PACIFIC_CHATHAM.getName(), TZ_ID_PACIFIC_CHATHAM);
    zoneTabs.put(TZ_ID_PACIFIC_CHUUK.getName(), TZ_ID_PACIFIC_CHUUK);
    zoneTabs.put(TZ_ID_PACIFIC_EASTER.getName(), TZ_ID_PACIFIC_EASTER);
    zoneTabs.put(TZ_ID_PACIFIC_EFATE.getName(), TZ_ID_PACIFIC_EFATE);
    zoneTabs.put(TZ_ID_PACIFIC_ENDERBURY.getName(), TZ_ID_PACIFIC_ENDERBURY);
    zoneTabs.put(TZ_ID_PACIFIC_FAKAOFO.getName(), TZ_ID_PACIFIC_FAKAOFO);
    zoneTabs.put(TZ_ID_PACIFIC_FIJI.getName(), TZ_ID_PACIFIC_FIJI);
    zoneTabs.put(TZ_ID_PACIFIC_FUNAFUTI.getName(), TZ_ID_PACIFIC_FUNAFUTI);
    zoneTabs.put(TZ_ID_PACIFIC_GALAPAGOS.getName(), TZ_ID_PACIFIC_GALAPAGOS);
    zoneTabs.put(TZ_ID_PACIFIC_GAMBIER.getName(), TZ_ID_PACIFIC_GAMBIER);
    zoneTabs.put(TZ_ID_PACIFIC_GUADALCANAL.getName(), TZ_ID_PACIFIC_GUADALCANAL);
    zoneTabs.put(TZ_ID_PACIFIC_GUAM.getName(), TZ_ID_PACIFIC_GUAM);
    zoneTabs.put(TZ_ID_PACIFIC_HONOLULU.getName(), TZ_ID_PACIFIC_HONOLULU);
    zoneTabs.put(TZ_ID_PACIFIC_JOHNSTON.getName(), TZ_ID_PACIFIC_JOHNSTON);
    zoneTabs.put(TZ_ID_PACIFIC_KIRITIMATI.getName(), TZ_ID_PACIFIC_KIRITIMATI);
    zoneTabs.put(TZ_ID_PACIFIC_KOSRAE.getName(), TZ_ID_PACIFIC_KOSRAE);
    zoneTabs.put(TZ_ID_PACIFIC_KWAJALEIN.getName(), TZ_ID_PACIFIC_KWAJALEIN);
    zoneTabs.put(TZ_ID_PACIFIC_MAJURO.getName(), TZ_ID_PACIFIC_MAJURO);
    zoneTabs.put(TZ_ID_PACIFIC_MARQUESAS.getName(), TZ_ID_PACIFIC_MARQUESAS);
    zoneTabs.put(TZ_ID_PACIFIC_MIDWAY.getName(), TZ_ID_PACIFIC_MIDWAY);
    zoneTabs.put(TZ_ID_PACIFIC_NAURU.getName(), TZ_ID_PACIFIC_NAURU);
    zoneTabs.put(TZ_ID_PACIFIC_NIUE.getName(), TZ_ID_PACIFIC_NIUE);
    zoneTabs.put(TZ_ID_PACIFIC_NORFOLK.getName(), TZ_ID_PACIFIC_NORFOLK);
    zoneTabs.put(TZ_ID_PACIFIC_NOUMEA.getName(), TZ_ID_PACIFIC_NOUMEA);
    zoneTabs.put(TZ_ID_PACIFIC_PAGO_PAGO.getName(), TZ_ID_PACIFIC_PAGO_PAGO);
    zoneTabs.put(TZ_ID_PACIFIC_PALAU.getName(), TZ_ID_PACIFIC_PALAU);
    zoneTabs.put(TZ_ID_PACIFIC_PITCAIRN.getName(), TZ_ID_PACIFIC_PITCAIRN);
    zoneTabs.put(TZ_ID_PACIFIC_POHNPEI.getName(), TZ_ID_PACIFIC_POHNPEI);
    zoneTabs.put(TZ_ID_PACIFIC_PONAPE.getName(), TZ_ID_PACIFIC_PONAPE);
    zoneTabs.put(TZ_ID_PACIFIC_PORT_MORESBY.getName(), TZ_ID_PACIFIC_PORT_MORESBY);
    zoneTabs.put(TZ_ID_PACIFIC_RAROTONGA.getName(), TZ_ID_PACIFIC_RAROTONGA);
    zoneTabs.put(TZ_ID_PACIFIC_SAIPAN.getName(), TZ_ID_PACIFIC_SAIPAN);
    zoneTabs.put(TZ_ID_PACIFIC_SAMOA.getName(), TZ_ID_PACIFIC_SAMOA);
    zoneTabs.put(TZ_ID_PACIFIC_TAHITI.getName(), TZ_ID_PACIFIC_TAHITI);
    zoneTabs.put(TZ_ID_PACIFIC_TARAWA.getName(), TZ_ID_PACIFIC_TARAWA);
    zoneTabs.put(TZ_ID_PACIFIC_TONGATAPU.getName(), TZ_ID_PACIFIC_TONGATAPU);
    zoneTabs.put(TZ_ID_PACIFIC_TRUK.getName(), TZ_ID_PACIFIC_TRUK);
    zoneTabs.put(TZ_ID_PACIFIC_WAKE.getName(), TZ_ID_PACIFIC_WAKE);
    zoneTabs.put(TZ_ID_PACIFIC_WALLIS.getName(), TZ_ID_PACIFIC_WALLIS);
    zoneTabs.put(TZ_ID_PACIFIC_YAP.getName(), TZ_ID_PACIFIC_YAP);
    zoneTabs.put(TZ_ID_POLAND.getName(), TZ_ID_POLAND);
    zoneTabs.put(TZ_ID_PORTUGAL.getName(), TZ_ID_PORTUGAL);
    zoneTabs.put(TZ_ID_PRC.getName(), TZ_ID_PRC);
    zoneTabs.put(TZ_ID_PST8PDT.getName(), TZ_ID_PST8PDT);
    zoneTabs.put(TZ_ID_ROC.getName(), TZ_ID_ROC);
    zoneTabs.put(TZ_ID_ROK.getName(), TZ_ID_ROK);
    zoneTabs.put(TZ_ID_SINGAPORE.getName(), TZ_ID_SINGAPORE);
    zoneTabs.put(TZ_ID_TURKEY.getName(), TZ_ID_TURKEY);
    zoneTabs.put(TZ_ID_UCT.getName(), TZ_ID_UCT);
    zoneTabs.put(TZ_ID_UNIVERSAL.getName(), TZ_ID_UNIVERSAL);
    zoneTabs.put(TZ_ID_US_ALASKA.getName(), TZ_ID_US_ALASKA);
    zoneTabs.put(TZ_ID_US_ALEUTIAN.getName(), TZ_ID_US_ALEUTIAN);
    zoneTabs.put(TZ_ID_US_ARIZONA.getName(), TZ_ID_US_ARIZONA);
    zoneTabs.put(TZ_ID_US_CENTRAL.getName(), TZ_ID_US_CENTRAL);
    zoneTabs.put(TZ_ID_US_EAST_INDIANA.getName(), TZ_ID_US_EAST_INDIANA);
    zoneTabs.put(TZ_ID_US_EASTERN.getName(), TZ_ID_US_EASTERN);
    zoneTabs.put(TZ_ID_US_HAWAII.getName(), TZ_ID_US_HAWAII);
    zoneTabs.put(TZ_ID_US_INDIANA_STARKE.getName(), TZ_ID_US_INDIANA_STARKE);
    zoneTabs.put(TZ_ID_US_MICHIGAN.getName(), TZ_ID_US_MICHIGAN);
    zoneTabs.put(TZ_ID_US_MOUNTAIN.getName(), TZ_ID_US_MOUNTAIN);
    zoneTabs.put(TZ_ID_US_PACIFIC.getName(), TZ_ID_US_PACIFIC);
    zoneTabs.put(TZ_ID_US_PACIFIC_NEW.getName(), TZ_ID_US_PACIFIC_NEW);
    zoneTabs.put(TZ_ID_US_SAMOA.getName(), TZ_ID_US_SAMOA);
    zoneTabs.put(TZ_ID_UTC.getName(), TZ_ID_UTC);
    zoneTabs.put(TZ_ID_W_SU.getName(), TZ_ID_W_SU);
    zoneTabs.put(TZ_ID_WET.getName(), TZ_ID_WET);
    zoneTabs.put(TZ_ID_ZULU.getName(), TZ_ID_ZULU);
    zoneTabs.put(TZ_ID_EXT_PST.getName(), TZ_ID_EXT_PST);
    zoneTabs.put(TZ_ID_DEFAULT.getName(), TZ_ID_DEFAULT);
    zoneTabs.put(TZ_ID_OFFSET.getName(), TZ_ID_OFFSET);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\ZoneInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
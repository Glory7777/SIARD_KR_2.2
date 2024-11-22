package com.tmax.tibero.jdbc.data.charset;

public class Big5CharToByteConverter implements Big5Charset, Big5Charset2 {
  private static final char[][] BIG5_TO_UNICODE_PAGE00 = new char[][] { 
      { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, 
      { Character.MIN_VALUE, '\u00AC' }, { '\004', '\u0083' }, { '\007', Character.MIN_VALUE }, { '\007', '\u0080' }, { '\b', Character.MIN_VALUE }, { '\b', '\u0080' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGE02 = new char[][] { 
      { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, 
      { '\t', Character.MIN_VALUE }, { '\t', Character.MIN_VALUE }, { '\t', '\u0E80' }, { '\r', '\u0200' }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, 
      { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', '\uFFFE' }, { '\035', '\u03FB' }, { '&', '\uFFFE' }, { '5', '\u03FB' }, { '>', Character.MIN_VALUE }, 
      { '>', Character.MIN_VALUE }, { '>', Character.MIN_VALUE }, { '>', '\002' }, { '?', '\u1FF0' }, { 'H', '\uFFF8' }, { 'U', Character.MAX_VALUE }, { 'e', Character.MAX_VALUE }, { 'u', '\002' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGE20 = new char[][] { 
      { 'v', Character.MIN_VALUE }, { 'v', '\u3318' }, { '|', 'd' }, { '', '\u4824' }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, 
      { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', Character.MIN_VALUE }, { '\u0083', '\u0228' }, { '\u0086', Character.MIN_VALUE }, { '\u0086', Character.MIN_VALUE }, { '\u0086', Character.MIN_VALUE }, 
      { '\u0086', Character.MIN_VALUE }, { '\u0086', Character.MIN_VALUE }, { '\u0086', '\u03FF' }, { '\u0090', Character.MIN_VALUE }, { '\u0090', Character.MIN_VALUE }, { '\u0090', '\u03CF' }, { '\u0098', Character.MIN_VALUE }, { '\u0098', Character.MIN_VALUE }, { '\u0098', Character.MIN_VALUE }, { '\u0098', Character.MIN_VALUE }, 
      { '\u0098', Character.MIN_VALUE }, { '\u0098', Character.MIN_VALUE }, { '\u0098', Character.MIN_VALUE }, { '\u0098', '\uC400' }, { '\u009B', '\u4E29' }, { '\u00A2', '\u1030' }, { '\u00A5', Character.MIN_VALUE }, { '\u00A5', '\004' }, { '\u00A6', '\u00C3' }, { '\u00AA', Character.MIN_VALUE }, 
      { '\u00AA', Character.MIN_VALUE }, { '\u00AA', Character.MIN_VALUE }, { '\u00AA', ' ' }, { '\u00AB', '\u8000' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGE24 = new char[][] { 
      { '\u00AC', Character.MIN_VALUE }, { '\u00AC', Character.MIN_VALUE }, { '\u00AC', Character.MIN_VALUE }, { '\u00AC', Character.MIN_VALUE }, { '\u00AC', Character.MIN_VALUE }, { '\u00AC', Character.MIN_VALUE }, { '\u00AC', '\u03FF' }, { '\u00B6', '\u3FF0' }, { '\u00C0', Character.MIN_VALUE }, { '\u00C0', Character.MIN_VALUE }, 
      { '\u00C0', Character.MIN_VALUE }, { '\u00C0', Character.MIN_VALUE }, { '\u00C0', Character.MIN_VALUE }, { '\u00C0', Character.MIN_VALUE }, { '\u00C0', Character.MIN_VALUE }, { '\u00C0', Character.MIN_VALUE }, { '\u00C0', '\u1005' }, { '\u00C3', '\u1111' }, { '\u00C7', '\u1010' }, { '\u00C9', '\u1010' }, 
      { '\u00CB', Character.MIN_VALUE }, { '\u00CB', '\u4001' }, { '\u00CD', '\uE402' }, { '\u00D2', '\017' }, { '\u00D6', '\uFFFE' }, { '\u00E5', '0' }, { '\u00E7', '\003' }, { '\u00E9', '\u300C' }, { '\u00ED', '\uC8C0' }, { '\u00F2', Character.MIN_VALUE }, 
      { '\u00F2', '<' }, { '\u00F6', Character.MIN_VALUE }, { '\u00F6', '\u0260' }, { '\u00F9', Character.MIN_VALUE }, { '\u00F9', Character.MIN_VALUE }, { '\u00F9', Character.MIN_VALUE }, { '\u00F9', '\007' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGE30 = new char[][] { 
      { '\u00FC', '\uFF2F' }, { '\u0109', '\u6037' }, { '\u0110', '\u03FE' }, { '\u0119', Character.MIN_VALUE }, { '\u0119', '\uFFFE' }, { '\u0128', Character.MAX_VALUE }, { '\u0138', Character.MAX_VALUE }, { '\u0148', Character.MAX_VALUE }, { '\u0158', Character.MAX_VALUE }, { '\u0168', '\u600F' }, 
      { '\u016E', '\uFFFE' }, { '\u017D', Character.MAX_VALUE }, { '\u018D', Character.MAX_VALUE }, { '\u019D', Character.MAX_VALUE }, { '\u01AD', Character.MAX_VALUE }, { '\u01BD', '\u407F' }, { '\u01C5', '\uFFE0' }, { '\u01D0', Character.MAX_VALUE }, { '\u01E0', '\u03FF' }, { '\u01EA', Character.MIN_VALUE }, 
      { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, 
      { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, 
      { '\u01EA', Character.MIN_VALUE }, { '\u01EA', Character.MIN_VALUE }, { '\u01EA', '\b' }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, 
      { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', Character.MIN_VALUE }, { '\u01EB', '\uC000' }, { '\u01ED', '\u7000' }, { '\u01F0', '\002' }, { '\u01F1', Character.MIN_VALUE }, 
      { '\u01F1', '\u4010' }, { '\u01F3', '&' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGE4E = new char[][] { 
      { '\u01F6', '\uFF8B' }, { '\u0202', '\uC373' }, { '\u020B', '\u6840' }, { '\u020F', '\u1B0F' }, { '\u0217', '\uE9AC' }, { '\u0220', '\uF34C' }, { '\u0229', '\u0200' }, { '\u022A', '\uC008' }, { '\u022D', '\u795C' }, { '\u0236', '\uCA3E' }, 
      { '\u023F', '\u7976' }, { '\u0249', '\u0648' }, { '\u024D', '\u2FDF' }, { '\u0259', '\uF7F0' }, { '\u0264', '\u033A' }, { '\u026A', '\uA8FF' }, { '\u0275', '\uEF37' }, { '\u0281', '\u233F' }, { '\u028A', '\uB004' }, { '\u028E', '\uFD59' }, 
      { '\u0299', '\uF3CA' }, { '\u02A3', Character.MAX_VALUE }, { '\u02B3', '\uDE9F' }, { '\u02BF', '\uFFF9' }, { '\u02CD', '\uABFF' }, { '\u02DA', '\u7DF7' }, { '\u02E7', '\uC000' }, { '\u02E9', '\u8EEC' }, { '\u02F2', '\uEEBF' }, { '\u02FF', '\uFFDB' }, 
      { '\u030D', '\uD003' }, { '\u0312', '\u45FA' }, { '\u031B', '\uFAE1' }, { '\u0325', '\uDFFE' }, { '\u0333', '\uBFEF' }, { '\u0341', '\u10AB' }, { '\u0347', '\uFFEB' }, { '\u0355', '\uFCAA' }, { '\u035F', '\uEF3F' }, { '\u036C', '\u24FD' }, 
      { '\u0375', '\u78AD' }, { '\u037E', '\u7F76' }, { '\u038A', '\uF00C' }, { '\u0390', '\uEDFF' }, { '\u039E', '\uCFF6' }, { '\u03AA', '\u2CFA' }, { '\u03B3', '\uF7F9' }, { '\u03C0', '\uEB6B' }, { '\u03CB', '\u1FFD' }, { '\u03D7', '\u95BF' }, 
      { '\u03E2', '\u6677' }, { '\u03EC', '\uBFBF' }, { '\u03FA', '\u3BFB' }, { '\u0406', '\uFEB4' }, { '\u0411', '\u7BAE' }, { '\u041C', '\u11E2' }, { '\u0422', '\uA681' }, { '\u0428', '\u41BE' }, { '\u0430', '\u1435' }, { '\u0436', '\u72C3' }, 
      { '\u043E', '\u7D70' }, { '\u0447', '\u7191' }, { '\u044E', '\003' }, { '\u0450', '\u276B' }, { '\u0459', '\u57CB' }, { '\u0463', '\u70CF' }, { '\u046C', '\u4732' }, { '\u0473', '\u0DEF' }, { '\u047D', '\u7EDA' }, { '\u0488', '\uFC74' }, 
      { '\u0492', '\uFE06' }, { '\u049B', '\uBDB4' }, { '\u04A5', '\u3F9F' }, { '\u04B1', '\u8BCA' }, { '\u04B9', '\u7E49' }, { '\u04C2', '\u5800' }, { '\u04C5', '\u228F' }, { '\u04CC', '\uEBEC' }, { '\u04D7', '\u8A5C' }, { '\u04DE', '\uDDBB' }, 
      { '\u04EA', '\uEF60' }, { '\u04F3', '\uB6E7' }, { '\u04FE', '\uA40F' }, { '\u0505', '\uF293' }, { '\u050E', '\u37BB' }, { '\u0519', '\u549E' }, { '\u0521', '\uD04B' }, { '\u0528', '\u9BAF' }, { '\u0533', '\uC414' }, { '\u0538', '\uF7D4' }, 
      { '\u0543', '\u30B0' }, { '\u0548', '\u0A14' }, { '\u054C', '\u2F08' }, { '\u0552', '\u88D0' }, { '\u0557', '\uFF7E' }, { '\u0565', '\u192F' }, { '\u056D', '\uFFDA' }, { '\u057A', '\uFB07' }, { '\u0584', '\u7FF1' }, { '\u0590', '\u7BEB' }, 
      { '\u059C', '\uC5EF' }, { '\u05A7', '\020' }, { '\u05A8', '\u99FF' }, { '\u05B4', '\uFDFF' }, { '\u05C3', '\u79D7' }, { '\u05CE', '\u0567' }, { '\u05D5', '\uFFE7' }, { '\u05E3', '\uFDCB' }, { '\u05EF', '\uC3FF' }, { '\u05FB', '\u4040' }, 
      { '\u05FD', '\u6FF7' }, { '\u060A', '\uBD8E' }, { '\u0614', '\uDFFA' }, { '\u0621', '\u0497' }, { '\u0627', '\uF4C0' }, { '\u062E', '\u5BFF' }, { '\u063B', '\uED7B' }, { '\u0647', '\uD0E7' }, { '\u0650', '\u047E' }, { '\u0657', '\uF8E0' }, 
      { '\u065F', '\uFF9F' }, { '\u066D', '\uB73E' }, { '\u0678', '\u7DFE' }, { '\u0685', '\u882E' }, { '\u068B', '\uFFFD' }, { '\u069A', '\uBE7F' }, { '\u06A7', '\u83FE' }, { '\u06B1', '\uF6C4' }, { '\u06BA', '\uF357' }, { '\u06C5', '\uB8FD' }, 
      { '\u06D0', '\uD680' }, { '\u06D6', '\uEF7D' }, { '\u06E3', '\u5767' }, { '\u06ED', '\u4788' }, { '\u06F3', '\uFF7D' }, { '\u0701', '\uC3DF' }, { '\u070C', '\uF0FF' }, { '\u0718', '\u37A9' }, { '\u0721', '\u7DE0' }, { '\u072A', '\u70FC' }, 
      { '\u0733', '\u3F6F' }, { '\u073F', '\uEC9A' }, { '\u0748', '\u4CB3' }, { '\u0750', '\u8681' }, { '\u0755', '\u3F9E' }, { '\u0760', '\uDD5C' }, { '\u076A', '\uF70D' }, { '\u0774', '\u4819' }, { '\u0779', '\uFEA3' }, { '\u0784', '\007' }, 
      { '\u0787', '\uAF56' }, { '\u0791', '\u38FF' }, { '\u079C', '\u980D' }, { '\u07A2', '\uEFB8' }, { '\u07AD', '\u403D' }, { '\u07B3', '\uB760' }, { '\u07BB', '\uD8CE' }, { '\u07C4', '\u9035' }, { '\u07CA', '\u72BF' }, { '\u07D5', '\u3FFF' }, 
      { '\u07E3', '\u7FF7' }, { '\u07F1', '\u7A11' }, { '\u07F8', '\uF7BB' }, { '\u0805', '\uABFF' }, { '\u0812', '\uFF00' }, { '\u081A', '\u6FBE' }, { '\u0826', '\uA93C' }, { '\u082E', '\uFE72' }, { '\u0839', '\uCFEF' }, { '\u0846', '\uF11B' }, 
      { '\u084F', '\uDB6B' }, { '\u085A', '\uF40A' }, { '\u0861', '\uC3E6' }, { '\u086A', '\uEF7E' }, { '\u0877', '\u9B9C' }, { '\u0880', '\uF610' }, { '\u0887', '\uF048' }, { '\u088D', '\u16F4' }, { '\u0895', '\uFEB5' }, { '\u08A1', '\u5182' }, 
      { '\u08A6', '\uC7B1' }, { '\u08AF', '\u15BB' }, { '\u08B8', '\u6E87' }, { '\u08C1', '\uFBDF' }, { '\u08CF', '\uE43F' }, { '\u08D9', '\u63CD' }, { '\u08E2', '\uC1FF' }, { '\u08ED', '\u7E7E' }, { '\u08F9', '\uFDEB' }, { '\u0906', '\u7D5F' }, 
      { '\u0912', '\u777B' }, { '\u091E', '\uFCFE' }, { '\u092B', '\u960B' }, { '\u0932', '\uDBEA' }, { '\u093D', '\u6229' }, { '\u0943', '\u53E8' }, { '\u094B', '\u37DF' }, { '\u0957', '\uFDEF' }, { '\u0965', '\u36F5' }, { '\u096F', '\uBD81' }, 
      { '\u0977', '\uDC18' }, { '\u097E', '\uFCBD' }, { '\u098A', '\uD2E4' }, { '\u0992', Character.MAX_VALUE }, { '\u09A2', '\u3FD7' }, { '\u09AE', '\uFFE0' }, { '\u09B9', '\u7F6F' }, { '\u09C6', '\uABF8' }, { '\u09D0', '\u9BAE' }, { '\u09DA', '\u6ED9' }, 
      { '\u09E4', '\uF5FB' }, { '\u09F1', '\uF115' }, { '\u09F9', '\u79A9' }, { '\u0A02', '\uBDFB' }, { '\u0A0F', '\u5A3C' }, { '\u0A17', '\uADAF' }, { '\u0A22', '\uDBBA' }, { '\u0A2D', '\u1FAC' }, { '\u0A36', '\u71FC' }, { '\u0A40', '\u8379' }, 
      { '\u0A48', '\u7CF7' }, { '\u0A54', '\uC35F' }, { '\u0A5E', '\uDFFF' }, { '\u0A6D', '\u0567' }, { '\u0A74', '\uFF9A' }, { '\u0A80', '\u8467' }, { '\u0A87', '\u1534' }, { '\u0A8D', '\uDF8B' }, { '\u0A98', '\uF9F3' }, { '\u0AA4', '\u3373' }, 
      { '\u0AAD', '\uF7BD' }, { '\u0ABA', '\u5E1A' }, { '\u0AC2', '\uBF40' }, { '\u0ACA', '\uA03F' }, { '\u0AD2', Character.MAX_VALUE }, { '\u0AE2', '\u01EB' }, { '\u0AE9', '\uDFC0' }, { '\u0AF2', '\uCFDD' }, { '\u0AFE', '\u7500' }, { '\u0B03', '\uABD3' }, 
      { '\u0B0D', '\uF8C3' }, { '\u0B16', '\uEED6' }, { '\u0B21', '\u43FD' }, { '\u0B2B', '\uB7FF' }, { '\u0B39', '\u5EAF' }, { '\u0B44', '\u4227' }, { '\u0B4A', '\u9BAC' }, { '\u0B53', '\uF686' }, { '\u0B5C', '\u27D7' }, { '\u0B66', '\uF6BC' }, 
      { '\u0B71', '\uF787' }, { '\u0B7C', '\u35B7' }, { '\u0B86', '\uAACD' }, { '\u0B8F', '\uE176' }, { '\u0B98', '\u49E7' }, { '\u0BA1', '\uE29F' }, { '\u0BAB', '\u545C' }, { '\u0BB2', '\uAFF2' }, { '\u0BBD', '\u2B3F' }, { '\u0BC7', '\u61D8' }, 
      { '\u0BCE', '\uFC3B' }, { '\u0BD9', '\uBBB8' }, { '\u0BE3', '\uFFCF' }, { '\u0BF1', '\u7B7D' }, { '\u0BFD', '\uBF95' }, { '\u0C08', '\u1CE0' }, { '\u0C0E', '\u7DFD' }, { '\u0C1B', '\u43FF' }, { '\u0C26', '\u5FF6' }, { '\u0C32', '\uFFFE' }, 
      { '\u0C41', '\uD3EF' }, { '\u0C4D', '\uC4CE' }, { '\u0C55', '\u8DB6' }, { '\u0C5E', '\uADBC' }, { '\u0C68', '\u63DC' }, { '\u0C71', '\u11EB' }, { '\u0C79', '\uDF59' }, { '\u0C84', '\u23D0' }, { '\u0C8A', '\uBEB4' }, { '\u0C94', '\uF3DB' }, 
      { '\u0CA0', '\u1FE7' }, { '\u0CAB', '\uDBC7' }, { '\u0CB6', '\uFF63' }, { '\u0CC2', '\uFAE4' }, { '\u0CCC', '\uB22B' }, { '\u0CD4', '\u63F7' }, { '\u0CDF', '\uED3B' }, { '\u0CEA', '\uADBA' }, { '\u0CF4', '\uFE01' }, { '\u0CFC', '\u7EFF' }, 
      { '\u0D0A', '\uFFF7' }, { '\u0D19', '\u02BC' }, { '\u0D1F', '\u32FF' }, { '\u0D2A', '\uEF39' }, { '\u0D35', '\uFFFC' }, { '\u0D43', '\u8005' }, { '\u0D46', '\u77FB' }, { '\u0D53', '\uBCF5' }, { '\u0D5E', '\u010D' }, { '\u0D62', '\uFFF7' }, 
      { '\u0D71', '\uFFFB' }, { '\u0D80', '\uBF3A' }, { '\u0D8B', 'W' }, { '\u0D90', '\uDFFF' }, { '\u0D9F', '\uEF7B' }, { '\u0DAC', '\uBD7D' }, { '\u0DB8', '\uDB88' }, { '\u0DC0', '\uC8D4' }, { '\u0DC7', '\uFFF3' }, { '\u0DD5', '\uED7C' }, 
      { '\u0DE0', '\u5DEE' }, { '\u0DEB', '\u56FF' }, { '\u0DF7', '\u7E0D' }, { '\u0E00', '\uAC5F' }, { '\u0E0A', '\uFF96' }, { '\u0E16', '\uD57F' }, { '\u0E22', '\u3FEE' }, { '\u0E2E', '\uC140' }, { '\u0E32', '\u6FF9' }, { '\u0E3E', '\uFFE7' }, 
      { '\u0E4C', '\u779B' }, { '\u0E57', '\u8E77' }, { '\u0E61', '\u6EBF' }, { '\u0E6D', '\uE45D' }, { '\u0E76', '\u6FCF' }, { '\u0E82', '\u5F1F' }, { '\u0E8D', '\uE07F' }, { '\u0E97', '\uFEDF' }, { '\u0EA5', '\uD7DB' }, { '\u0EB1', '\u01FE' }, 
      { '\u0EB9', '\uFF00' }, { '\u0EC1', '\uFB7B' }, { '\u0ECE', '\uFFD4' }, { '\u0EDA', '\u1FDF' }, { '\u0EE6', '\uF800' }, { '\u0EEB', Character.MAX_VALUE }, { '\u0EFB', '\uFB8F' }, { '\u0F07', '{' }, { '\u0F0D', '\uBF00' }, { '\u0F14', '\u7F5C' }, 
      { '\u0F1F', Character.MAX_VALUE }, { '\u0F2F', '\u07F3' }, { '\u0F38', '\uEBA0' }, { '\u0F40', '\u3DE7' }, { '\u0F4B', '\uF7BF' }, { '\u0F59', '\uFBD7' }, { '\u0F66', '\uFFBF' }, { '\u0F75', '\u6003' }, { '\u0F79', '\uFFFD' }, { '\u0F88', '\uBFED' }, 
      { '\u0F95', '\uEFBB' }, { '\u0FA2', '\u027F' }, { '\u0FAA', '\uFE40' }, { '\u0FB2', '\uDDFD' }, { '\u0FBF', '\uFDFF' }, { '\u0FCE', '\uE2F9' }, { '\u0FD8', '\u680B' }, { '\u0FDE', '\uFB1F' }, { '\u0FEA', '\uFBE3' }, { '\u0FF6', '\uAFFD' }, 
      { '\u1003', '\u9FA4' }, { '\u100C', '\uF7ED' }, { '\u1019', '\u7A7D' }, { '\u1024', '\uF80F' }, { '\u102D', '\uEEBE' }, { '\u1039', '\u0FD5' }, { '\u1042', '\uBB5D' }, { '\u104D', '\uFD9F' }, { '\u105A', '\uF2DB' }, { '\u1065', '\u3BF9' }, 
      { '\u1070', '\uFE7F' }, { '\u107E', '\uEBCC' }, { '\u1088', '\u876A' }, { '\u1090', '\u73FA' }, { '\u109B', '\u95FC' }, { '\u10A5', '\u9FFC' }, { '\u10B1', '\u109F' }, { '\u10B8', '\uFAF7' }, { '\u10C5', '\uDDB7' }, { '\u10D1', '\uBBCD' }, 
      { '\u10DC', '\uF87E' }, { '\u10E7', '\uECCD' }, { '\u10F1', '\uF366' }, { '\u10FB', '\u3C3F' }, { '\u1105', '\uFFFD' }, { '\u1114', '\uB03F' }, { '\u111D', '\uE9F7' }, { '\u1129', '\u067E' }, { '\u1131', '\u96AE' }, { '\u113A', '\uFE06' }, 
      { '\u1143', '\uD576' }, { '\u114D', '\u5FD7' }, { '\u1159', '\u3FD1' }, { '\u1163', '\uA3F3' }, { '\u116D', '\uCF07' }, { '\u1176', '\u6FB7' }, { '\u1182', '\u9FD1' }, { '\u118C', '\u7F44' }, { '\u1195', '\u7B59' }, { '\u119F', '\uD3DD' }, 
      { '\u11AA', '\uAF3B' }, { '\u11B5', '\uA9BD' }, { '\u11BF', '\u7DCF' }, { '\u11CB', '\uFF3A' }, { '\u11D7', '\uFBE0' }, { '\u11E1', '\uF6EB' }, { '\u11ED', '\uB401' }, { '\u11F2', Character.MAX_VALUE }, { '\u1202', '\u7AFA' }, { '\u120D', '\uB7BF' }, 
      { '\u121A', '\uC000' }, { '\u121C', '\u0FFD' }, { '\u1227', '\uFF7F' }, { '\u1236', '\uFF1F' }, { '\u1243', '\uFEFC' }, { '\u1250', '\u95FF' }, { '\u125C', Character.MIN_VALUE }, { '\u125C', '\uB5DC' }, { '\u1266', '\uEF63' }, { '\u1271', '\u3F3E' }, 
      { '\u127C', '\uFB7F' }, { '\u128A', '\033' }, { '\u128E', '\uE800' }, { '\u1292', '\uFBF6' }, { '\u129F', '\u9EEF' }, { '\u12AB', '\uB8DF' }, { '\u12B6', '\uFF9F' }, { '\u12C4', '?' }, { '\u12CA', '\u7BD0' }, { '\u12D3', '\uF5FF' }, 
      { '\u12E1', '\uDFDB' }, { '\u12EE', '\u3FFF' }, { '\u12FC', '\uFDF0' }, { '\u1307', '\u00BF' }, { '\u130E', '\u8420' }, { '\u1311', '\uBBBD' }, { '\u131D', '\uDF37' }, { '\u1329', '\uFFDE' }, { '\u1337', '\uFF6D' }, { '\u1344', '\u0FF3' }, 
      { '\u134E', '\u604C' }, { '\u1353', '\u5EFB' }, { '\u135F', '\uFFFB' }, { '\u136E', '\uFAFB' }, { '\u137B', '\uFE5E' }, { '\u1387', '\u0219' }, { '\u138B', '\u79F4' }, { '\u1395', '\uF9DE' }, { '\u13A1', '\uA7F7' }, { '\u13AD', '\uEBFA' }, 
      { '\u13B9', '\u01EB' }, { '\u13C0', '\uFF34' }, { '\u13CB', '\uEBD3' }, { '\u13D6', '\uEF73' }, { '\u13E2', '\uAFD7' }, { '\u13EE', '\uC040' }, { '\u13F1', '\u72BB' }, { '\u13FB', '\uDCFF' }, { '\u1408', '\uF17F' }, { '\u1414', '\u2FD8' }, 
      { '\u141D', '\uB8EC' }, { '\u1426', '\uFE0B' }, { '\u1430', '\uDDA3' }, { '\u143A', '\u1F0B' }, { '\u1442', '\u8F1D' }, { '\u144B', '\u47CF' }, { '\u1455', '\uB12B' }, { '\u145D', '\uFFDE' }, { '\u146B', '\u7FEE' }, { '\u1478', '\uDA73' }, 
      { '\u1482', '\u24FF' }, { '\u148C', '\uCBC4' }, { '\u1494', '\uF75D' }, { '\u14A0', '\uCBF2' }, { '\u14AA', '\uECFD' }, { '\u14B6', '\uB4ED' }, { '\u14C0', '\uBFF9' }, { '\u14CD', '\u4DDD' }, { '\u14D7', '\u99DD' }, { '\u14E1', '\uFB8D' }, 
      { '\u14EC', '\uBB7F' }, { '\u14F9', '\uAF7B' }, { '\u1505', '\uDDFB' }, { '\u1512', '\uC959' }, { '\u151A', '\uFC4F' }, { '\u1525', '\uFAB5' }, { '\u1530', '\uAFE3' }, { '\u153B', '\u6D5F' }, { '\u1546', Character.MAX_VALUE }, { '\u1556', '\u3F7D' }, 
      { '\u1562', '\u7800' }, { '\u1566', '\uFFDB' }, { '\u1574', '\uB6FF' }, { '\u1581', '\u7EFF' }, { '\u158F', '\uFBAF' }, { '\u159C', '\u022F' }, { '\u15A2', '\uFF9B' }, { '\u15AF', '\uEFC7' }, { '\u15BB', '\uFFA5' }, { '\u15C7', Character.MAX_VALUE }, 
      { '\u15D7', '\007' }, { '\u15DA', '\uC700' }, { '\u15DF', '\uF7FF' }, { '\u15EE', '\uFFF1' }, { '\u15FB', '\u7FFD' }, { '\u1609', '\u01BF' }, { '\u1611', '\uDC00' }, { '\u1616', '\uFDBC' }, { '\u1622', '\uBFF5' }, { '\u162F', Character.MAX_VALUE }, 
      { '\u163F', '\uFF7F' }, { '\u164E', '\u3EFF' }, { '\u165B', ')' }, { '\u165E', '\uBE00' }, { '\u1664', '\uF9FF' }, { '\u1672', '\uFF7F' }, { '\u1681', '\u6EFB' }, { '\u168D', '\uFD7E' }, { '\u169A', '\uCBFF' }, { '\u16A7', '\u039E' }, 
      { '\u16AE', '\uE300' }, { '\u16B3', '\uFBDD' }, { '\u16C0', '\uCCFF' }, { '\u16CC', '\uF6DF' }, { '\u16D9', Character.MAX_VALUE }, { '\u16E9', '\u117F' }, { '\u16F2', '\uF800' }, { '\u16F7', '\uFBF6' }, { '\u1704', '\uE7EF' }, { '\u1711', '\uD73C' }, 
      { '\u171B', '\uFEEF' }, { '\u1729', '\uDFEF' }, { '\u1737', '\uC00B' }, { '\u173C', '\uEDBF' }, { '\u1749', '\uFEDF' }, { '\u1757', '\uFDCD' }, { '\u1763', '\u7BF5' }, { '\u176F', '\u40FD' }, { '\u1777', Character.MAX_VALUE }, { '\u1787', '\uB75F' }, 
      { '\u1793', '\uFFDF' }, { '\u17A2', '\uF930' }, { '\u17AA', '\uFBDF' }, { '\u17B8', '\uDC97' }, { '\u17C2', '\uFEF3' }, { '\u17CF', '\uBFF2' }, { '\u17DB', '\u8FDF' }, { '\u17E7', '\uDFBF' }, { '\u17F5', '\u177F' }, { '\u1800', '\uEDE6' }, 
      { '\u180B', '\u0F7F' }, { '\u1816', '\u3553' }, { '\u181E', '\u447C' }, { '\u1825', '\u877E' }, { '\u182F', '\uFA12' }, { '\u1837', '\u45BB' }, { '\u1840', '\uEDE0' }, { '\u1849', '\u779E' }, { '\u1854', '\u8017' }, { '\u1859', '\uBFD9' }, 
      { '\u1865', '\u7E55' }, { '\u186F', '\uDE89' }, { '\u1878', '\uC16F' }, { '\u1881', '\u0447' }, { '\u1886', '\u7ADE' }, { '\u1891', '\uF75D' }, { '\u189D', '\u57FF' }, { '\u18AA', '\u2905' }, { '\u18AF', '\u86F7' }, { '\u18B9', '\uFE95' }, 
      { '\u18C4', '\u97B3' }, { '\u18CE', '\uF32F' }, { '\u18D9', '\uCFFF' }, { '\u18E7', '\u9F75' }, { '\u18F2', '\u71F7' }, { '\u18FD', '\uFB17' }, { '\u1908', '\u34EE' }, { '\u1911', '\uEE19' }, { '\u191A', '\u37CC' }, { '\u1923', '\uEF61' }, 
      { '\u192D', '\u9FD6' }, { '\u1938', '\uEF4C' }, { '\u1942', '\uD68F' }, { '\u194C', '\uFBDD' }, { '\u1959', '\u7B73' }, { '\u1964', '\u6DEF' }, { '\u1970', '\uD7FE' }, { '\u197D', '\uA431' }, { '\u1983', '\u5E7F' }, { '\u198F', '\u97D7' }, 
      { '\u199A', '\u0F5B' }, { '\u19A3', '\uFFD8' }, { '\u19AF', '\u9D83' }, { '\u19B7', '\u7BCE' }, { '\u19C2', '\u22EC' }, { '\u19C9', '\uDCFF' }, { '\u19D6', '\u763D' }, { '\u19E0', '\uEF87' }, { '\u19EB', '\uDFE7' }, { '\u19F8', '\uFDED' }, 
      { '\u1A05', '\u4FFF' }, { '\u1A12', '\uA0FC' }, { '\u1A1A', '\u3B77' }, { '\u1A25', '\uDBFC' }, { '\u1A31', '\u3DED' }, { '\u1A3C', '\u7FDC' }, { '\u1A48', '\u6FA9' }, { '\u1A52', '\uF570' }, { '\u1A5B', '\u3FFB' }, { '\u1A68', '\u2C40' }, 
      { '\u1A6C', '\uFF7F' }, { '\u1A7B', '\u847F' }, { '\u1A84', '\uEC57' }, { '\u1A8E', '\uDEB7' }, { '\u1A9A', '\uE69C' }, { '\u1AA3', '\uF22F' }, { '\u1AAD', '\u0FEB' }, { '\u1AB7', '\uD5B5' }, { '\u1AC1', '\uAFEB' }, { '\u1ACD', '\uEDE7' }, 
      { '\u1AD9', '\u8C2F' }, { '\u1AE1', '\uFFF0' }, { '\u1AED', '\u537F' }, { '\u1AF8', '\uE8F0' }, { '\u1B00', '\uB99D' }, { '\u1B0A', '\uB5FF' }, { '\u1B17', '\uFF66' }, { '\u1B23', '\uE78F' }, { '\u1B2E', '\uD981' }, { '\u1B35', '\uBE10' }, 
      { '\u1B3C', '\u9C7C' }, { '\u1B45', '\uE3C1' }, { '\u1B4D', '\u9CD1' }, { '\u1B55', '\u2733' }, { '\u1B5D', '\u0CBC' }, { '\u1B64', '\uFF6D' }, { '\u1B71', '\uFCB7' }, { '\u1B7D', '\uEFB7' }, { '\u1B8A', '\uA0DF' }, { '\u1B93', Character.MAX_VALUE }, 
      { '\u1BA3', '\uBF0B' }, { '\u1BAD', '\uFE7B' }, { '\u1BBA', '\uA3FF' }, { '\u1BC6', '\u353F' }, { '\u1BD0', '\u13CC' }, { '\u1BD7', '\u97CD' }, { '\u1BE1', '\u7637' }, { '\u1BEB', '\uFB27' }, { '\u1BF6', '\uCFD6' }, { '\u1C01', '\u7E6C' }, 
      { '\u1C0B', '\uEC50' }, { '\u1C12', '\uED31' }, { '\u1C1B', '\u677C' }, { '\u1C25', '\uFC1C' }, { '\u1C2E', '\uF6FA' }, { '\u1C3A', '\u5FBF' }, { '\u1C47', '\u0FBA' }, { '\u1C50', '\uAE2F' }, { '\u1C5A', '\uA3AD' }, { '\u1C63', '\u7FFE' }, 
      { '\u1C71', '\uFCF0' }, { '\u1C7B', '\uDE74' }, { '\u1C85', '\uFFEF' }, { '\u1C94', '\uF200' }, { '\u1C99', '\uFBBF' }, { '\u1CA7', '\uFEA2' }, { '\u1CB1', '\u3DAF' }, { '\u1CBC', '\uBCFF' }, { '\u1CC9', '\uF694' }, { '\u1CD2', '\u5FB9' }, 
      { '\u1CDD', '\uF3AD' }, { '\u1CE8', '\u3F8F' }, { '\u1CF3', '\uF26C' }, { '\u1CFC', '\uA01F' }, { '\u1D03', '\uFFEF' }, { '\u1D12', '\u01BF' }, { '\u1D1A', '\u7728' }, { '\u1D22', '\u7005' }, { '\u1D27', '\uFF35' }, { '\u1D33', '\uDA03' }, 
      { '\u1D3A', '\uD2F9' }, { '\u1D44', '\uC7FA' }, { '\u1D4F', '\u3FBF' }, { '\u1D5C', '\u5C1D' }, { '\u1D64', '\uFF3A' }, { '\u1D70', '\uEC33' }, { '\u1D79', '\uB7AF' }, { '\u1D85', '\uFE9C' }, { '\u1D90', '\u5236' }, { '\u1D97', '\u7A9F' }, 
      { '\u1DA2', '\uBFFA' }, { '\u1DAF', '\uE722' }, { '\u1DB7', '\u9FF7' }, { '\u1DC4', '\uFCFF' }, { '\u1DD2', '\u2FBB' }, { '\u1DDD', '\uB61D' }, { '\u1DE6', '\uED06' }, { '\u1DEE', '\u1DFD' }, { '\u1DF9', '\u7DD7' }, { '\u1E05', '\uEFDF' }, 
      { '\u1E13', '\uEB23' }, { '\u1E1C', '\uF166' }, { '\u1E25', '\u7ED9' }, { '\u1E30', '\u0DC0' }, { '\u1E35', '\u3D3D' }, { '\u1E3F', '\uDFBF' }, { '\u1E4D', '\uC945' }, { '\u1E54', '\uBA83' }, { '\u1E5C', '\u7DD1' }, { '\u1E66', '\u9DD0' }, 
      { '\u1E6E', '\u7B87' }, { '\u1E78', '\uCF73' }, { '\u1E83', '\u9FF3' }, { '\u1E8F', '\uC3F5' }, { '\u1E99', '\uDF0D' }, { '\u1EA3', '\uC5FE' }, { '\u1EAE', '\u0CB3' }, { '\u1EB5', '\u8302' }, { '\u1EB9', '\uE879' }, { '\u1EC2', '\uAEC0' }, 
      { '\u1EC9', '\uC773' }, { '\u1ED3', '\u6F0F' }, { '\u1EDD', '\uFD7D' }, { '\u1EEA', '\u093F' }, { '\u1EF2', '\uFFF1' }, { '\u1EFF', '\u0157' }, { '\u1F05', '\u62FB' }, { '\u1F0F', '\u01FF' }, { '\u1F18', '\uFDB4' }, { '\u1F23', '\u3BF3' }, 
      { '\u1F2E', '\uB013' }, { '\u1F34', '\u43B2' }, { '\u1F3B', '\u5ED3' }, { '\u1F45', '\uFF30' }, { '\u1F4F', '\u0FFF' }, { '\u1F5B', '\uEB9F' }, { '\u1F67', '\uFEEF' }, { '\u1F75', '\uF203' }, { '\u1F7C', '\u3FEF' }, { '\u1F89', '\uFB89' }, 
      { '\u1F93', '\u37A9' }, { '\u1F9C', '\u9E99' }, { '\u1FA5', '\uDEF9' }, { '\u1FB1', '\uA72C' }, { '\u1FB9', '\u3733' }, { '\u1FC2', '\uC1F6' }, { '\u1FCB', '\u812E' }, { '\u1FD1', '\uFE3E' }, { '\u1FDD', '\u5D20' }, { '\u1FE3', '\uF2F7' }, 
      { '\u1FEF', '\uD585' }, { '\u1FF7', '\u69D7' }, { '\u2001', Character.MAX_VALUE }, { '\u2011', Character.MAX_VALUE }, { '\u2021', '\uDB07' }, { '\u202A', '\uFF6F' }, { '\u2038', '\uC4FF' }, { '\u2043', '\uD97F' }, { '\u204F', '\uEFCE' }, { '\u205B', '\uBE0F' }, 
      { '\u2065', '\uF17B' }, { '\u2070', '\uF05E' }, { '\u2079', '\uF6CF' }, { '\u2085', '\uFFB7' }, { '\u2093', '\u5EF7' }, { '\u209F', '\uEF84' }, { '\u20A8', '\uD7CB' }, { '\u20B3', '\u0EDF' }, { '\u20BD', '\uFF08' }, { '\u20C6', '\uFCFF' }, 
      { '\u20D4', '\uEE3F' }, { '\u20E0', Character.MAX_VALUE }, { '\u20F0', '\u13FF' }, { '\u20FB', '\uD7FF' }, { '\u2109', '\uAF0F' }, { '\u2113', '\u7FFD' }, { '\u2121', '\uBDC7' }, { '\u212C', '\u1FFA' }, { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, 
      { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, { '\u2137', Character.MIN_VALUE }, { '\u2137', '\uE740' }, { '\u213E', '\uBD38' }, { '\u2147', '\uF933' }, 
      { '\u2151', '\u7FEB' }, { '\u215E', '\uFEED' }, { '\u216B', '\u7FE8' }, { '\u2176', '\u7C76' }, { '\u2180', '\uB3F7' }, { '\u218C', '\uFFEF' }, { '\u219B', '\uFEAF' }, { '\u21A8', '\uD8B7' }, { '\u21B2', '\uFF6F' }, { '\u21C0', '\uFBBF' }, 
      { '\u21CE', '\uF8FB' }, { '\u21DA', '\uDBF7' }, { '\u21E7', '\u1752' }, { '\u21EE', '\uE2F9' }, { '\u21F8', '\u85C8' }, { '\u21FE', '\u7547' }, { '\u2207', '\u9090' }, { '\u220B', '\uE3EF' }, { '\u2217', '\u9EF4' }, { '\u2221', '\u3F6D' }, 
      { '\u222C', '\uEE2E' }, { '\u2236', '\u0536' }, { '\u223C', '\uF7BC' }, { '\u2248', '\u7FF3' }, { '\u2255', '\uA07B' }, { '\u225D', '\u7F3F' }, { '\u226A', '\u0567' }, { '\u2271', '\uEB60' }, { '\u2279', '\uBABE' }, { '\u2284', '\u6601' }, 
      { '\u2289', '\uFCD8' }, { '\u2293', '\u583F' }, { '\u229C', '\uCAF7' }, { '\u22A7', '\u87DF' }, { '\u22B2', '\uBFCD' }, { '\u22BE', '\uFFA0' }, { '\u22C8', '\u5BCD' }, { '\u22D2', '\uFEBF' }, { '\u22E0', '\uB6FD' }, { '\u22EC', '\uEFA7' }, 
      { '\u22F8', '\u77EF' }, { '\u2305', '\uDF9C' }, { '\u2310', '\u3FB7' }, { '\u231C', '\uF877' }, { '\u2327', '\u9D27' }, { '\u2330', '\uB7FC' }, { '\u233C', '\uCAB5' }, { '\u2345', '\uDFEF' }, { '\u2353', '\uFB5A' }, { '\u235E', '\uF1B6' }, 
      { '\u2368', '\uEC39' }, { '\u2371', '\uEF1F' }, { '\u237D', '\uFBBF' }, { '\u238B', '\u7FFB' }, { '\u2399', '\r' }, { '\u239C', '\uDAFE' }, { '\u23A8', '\uBDFB' }, { '\u23B5', '\u4E7F' }, { '\u23C0', '\u33FF' }, { '\u23CC', '\u5AC0' }, 
      { '\u23D2', '\uBFF5' }, { '\u23DF', '\u9FFE' }, { '\u23EC', '\uFFBF' }, { '\u23FB', '_' }, { '\u2401', Character.MIN_VALUE }, { '\u2401', '\uFDF8' }, { '\u240D', '\uFFCA' }, { '\u2419', '\u6FFD' }, { '\u2426', '\uCFFD' }, { '\u2433', '\uA001' }, 
      { '\u2436', '\uDFFF' }, { '\u2445', '\uFBF2' }, { '\u2451', '\uDFBF' }, { '\u245F', '\uFF7F' }, { '\u246E', '\uFEDA' }, { '\u247A', '\u080F' }, { '\u247F', '\uBA08' }, { '\u2485', '\uBFFF' }, { '\u2494', '\u7AFD' }, { '\u24A0', '\uEED7' }, 
      { '\u24AC', '\uFBEB' }, { '\u24B9', '\u67F9' }, { '\u24C4', '\uE044' }, { '\u24C9', '\uFF93' }, { '\u24D5', '\uDF97' }, { '\u24E1', '\u9F57' }, { '\u24EC', '\uFEF7' }, { '\u24FA', '\u08DF' }, { '\u2502', '\uDF80' }, { '\u250A', '\uFEDF' }, 
      { '\u2518', '\uFFC5' }, { '\u2524', '\uF7FE' }, { '\u2532', '\uFFFB' }, { '\u2541', '\u6803' }, { '\u2546', '\u67FB' }, { '\u2552', '\u6BFA' }, { '\u255D', '\u7FFF' }, { '\u256C', '\u5FE2' }, { '\u2576', Character.MAX_VALUE }, { '\u2586', '\uFF73' }, 
      { '\u2593', '\u87DF' }, { '\u259E', '\uE7FB' }, { '\u25AB', '\uEBFD' }, { '\u25B8', '\uF7A7' }, { '\u25C4', '\uBF7E' }, { '\u25D1', '\uEFC7' }, { '\u25DD', '\u1EF3' }, { '\u25E7', '\uDF82' }, { '\u25F0', '\u76FF' }, { '\u25FD', '\uDF7E' }, 
      { '\u260A', '\u79C9' }, { '\u2613', '\uDA7D' }, { '\u261E', '\uEFBE' }, { '\u262B', '\u1E9B' }, { '\u2634', '\u7CE0' }, { '\u263C', '\u77FB' }, { '\u2649', '\u87BE' }, { '\u2653', '\uFFFB' }, { '\u2662', '\u1BFF' }, { '\u266E', '\uFFDB' }, 
      { '\u267C', '\u3F5C' }, { '\u2686', '\u4FE0' }, { '\u268E', '\u7FFF' }, { '\u269D', '\u5F0E' }, { '\u26A6', '\u77FF' }, { '\u26B4', '\uDDBF' }, { '\u26C1', '\uF04F' }, { '\u26CA', Character.MAX_VALUE }, { '\u26DA', Character.MAX_VALUE }, { '\u26EA', '\u0FF8' }, 
      { '\u26F3', '\uA3BE' }, { '\u26FD', '\uFDDF' }, { '\u270B', '\uFC1C' }, { '\u2714', '\uFFFD' }, { '\u2723', '\u1F7D' }, { '\u272E', '\uFB9E' }, { '\u273A', '\uBDFF' }, { '\u2748', '\uDEDC' }, { '\u2753', '\u3F6F' }, { '\u275F', '\uBAFB' }, 
      { '\u276B', '\uDF7F' }, { '\u2779', '\uFBEF' }, { '\u2787', '\u7D1B' }, { '\u2791', '\u2EEC' }, { '\u279A', '\uAF8E' }, { '\u27A4', '\uF2F7' }, { '\u27B0', '\u7B0F' }, { '\u27BA', '\uCFEE' }, { '\u27C6', '\u1D96' }, { '\u27CE', '\u77C6' }, 
      { '\u27D8', '\u7E07' }, { '\u27E1', '\uFFF5' }, { '\u27EF', '\uD982' }, { '\u27F6', '\u7FDF' }, { '\u2804', '\u5EE6' }, { '\u280E', '\uC7FF' }, { '\u281B', '\uFEEE' }, { '\u2828', '\u79EF' }, { '\u2834', '\u9A56' }, { '\u283C', '\uFFCF' }, 
      { '\u284A', '\uFE5F' }, { '\u2857', '\uDE5E' }, { '\u2862', '\u896E' }, { '\u286A', '\uF9E8' }, { '\u2874', '\uF45E' }, { '\u287E', '\uE6C4' }, { '\u2886', '\001' }, { '\u2887', '\uBE7C' }, { '\u2892', '\u3B7F' }, { '\u289E', '\uDDDF' }, 
      { '\u28AB', '\uD59D' }, { '\u28B5', '\uE9EF' }, { '\u28C1', '\u34AC' }, { '\u28C8', '\uDE53' }, { '\u28D2', '\uF573' }, { '\u28DD', '\u4BF7' }, { '\u28E8', '\u7B4F' }, { '\u28F3', '\u9EFF' }, { '\u2900', '\uB8FE' }, { '\u290B', '\u476E' }, 
      { '\u2914', '\u0DFB' }, { '\u291E', '\uFF45' }, { '\u2929', '\uABFD' }, { '\u2935', '\uFBFE' }, { '\u2943', '\uE9D7' }, { '\u294E', '\uDDFF' }, { '\u295C', '\uEDF7' }, { '\u2969', '\u7FFF' }, { '\u2978', '\uDDFD' }, { '\u2985', '\u7EEB' }, 
      { '\u2991', '\uCFE7' }, { '\u299D', '\uB7FF' }, { '\u29AB', '\uBDE9' }, { '\u29B6', '\uEF91' }, { '\u29C0', '\u5D75' }, { '\u29CA', '\uD77C' }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, 
      { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', Character.MIN_VALUE }, { '\u29D5', '\uFA80' }, { '\u29DC', '\uFFEE' }, { '\u29EA', '\uB4F1' }, { '\u29F3', '\uBF76' }, { '\u29FF', '\u2FEF' }, 
      { '\u2A0B', '\uB677' }, { '\u2A16', '\u77BF' }, { '\u2A23', '\u9FBF' }, { '\u2A30', '\uFFFD' }, { '\u2A3F', '\u95BF' }, { '\u2A4A', '\uF6AE' }, { '\u2A55', '\u75FF' }, { '\u2A62', '\u7F3B' }, { '\u2A6E', '\uA7F5' }, { '\u2A79', '\u0AF9' }, 
      { '\u2A81', Character.MIN_VALUE }, { '\u2A81', Character.MIN_VALUE }, { '\u2A81', Character.MIN_VALUE }, { '\u2A81', Character.MIN_VALUE }, { '\u2A81', '\uFBD0' }, { '\u2A8B', '\u2BDD' }, { '\u2A95', '\uF633' }, { '\u2A9F', '\u9A7F' }, { '\u2AAA', '\uFDAB' }, { '\u2AB6', '\uD6FC' }, 
      { '\u2AC1', '\uF9E6' }, { '\u2ACC', '\uBFEB' }, { '\u2AD9', '\uDFDF' }, { '\u2AE7', '\uF41F' }, { '\u2AF1', '\uA6FD' }, { '\u2AFC', Character.MAX_VALUE }, { '\u2B0C', '\u4AFF' }, { '\u2B17', '\uF37B' }, { '\u2B23', '\u7FB7' }, { '\u2B30', '\uFEF9' }, 
      { '\u2B3D', '\uB6FF' }, { '\u2B4A', '\u1D5C' }, { '\u2B52', '\u7FF6' }, { '\u2B5F', '\uE5FF' }, { '\u2B6C', '\u1F7B' }, { '\u2B77', '\u2404' }, { '\u2B7A', '\uBE05' }, { '\u2B82', '\uF99E' }, { '\u2B8D', '\uDBE3' }, { '\u2B98', '\uDFF2' }, 
      { '\u2BA4', '\u6FEF' }, { '\u2BB1', '\uFDFF' }, { '\u2BC0', '\uD679' }, { '\u2BCA', '\uCBFC' }, { '\u2BD5', '\uEBFD' }, { '\u2BE2', '\uEFFF' }, { '\u2BF1', '\037' }, { '\u2BF6', Character.MIN_VALUE }, { '\u2BF6', Character.MIN_VALUE }, { '\u2BF6', '\u9800' }, 
      { '\u2BF9', '\uE148' }, { '\u2BFF', '\u8017' }, { '\u2C04', '\u6A74' }, { '\u2C0C', '\u00FE' }, { '\u2C13', '\u6D7F' }, { '\u2C1F', '\uFDF1' }, { '\u2C2B', '\uB87F' }, { '\u2C36', '\uFEF3' }, { '\u2C43', '\uE01F' }, { '\u2C4B', '\uF176' }, 
      { '\u2C55', '\uEE96' }, { '\u2C5F', '\u7B3F' }, { '\u2C6B', '\uEB8D' }, { '\u2C75', '\uFFFD' }, { '\u2C84', '\uADFF' }, { '\u2C91', '\uCBB3' }, { '\u2C9B', '\u84EF' }, { '\u2CA4', '\uE17F' }, { '\u2CAF', '\u4DAA' }, { '\u2CB7', '\uBFF0' }, 
      { '\u2CC2', '\uBF3F' }, { '\u2CCF', '\uFE3F' }, { '\u2CDC', '\uEBFF' }, { '\u2CEA', '\uFFD7' }, { '\u2CF8', '\uFFDF' }, { '\u2D07', '\uCF7F' }, { '\u2D14', '\uFFFB' }, { '\u2D23', '\u85ED' }, { '\u2D2C', '\uD73F' }, { '\u2D38', '\u07BC' }, 
      { '\u2D40', '\uAEFF' }, { '\u2D4D', '\uFE0F' }, { '\u2D58', '\uFDAF' }, { '\u2D65', '\u76BF' }, { '\u2D71', '\uFAEF' }, { '\u2D7E', '\u37BB' }, { '\u2D89', '\u7FDC' }, { '\u2D95', '\uA3BA' }, { '\u2D9E', '\uB6FF' }, { '\u2DAB', '\u56F7' }, 
      { '\u2DB6', '\u60F8' }, { '\u2DBD', '\uE7DF' }, { '\u2DCA', '\uFF61' }, { '\u2DD5', '\u4CDF' }, { '\u2DDF', '\uB0FB' }, { '\u2DE9', '\uFF45' }, { '\u2DF4', '\u7DED' }, { '\u2E00', '\u3FFA' }, { '\u2E0C', '\u1FFF' }, { '\u2E19', '\u18FC' }, 
      { '\u2E21', Character.MAX_VALUE }, { '\u2E31', '\uE3AF' }, { '\u2E3C', '\uC7D3' }, { '\u2E46', '\uDF83' }, { '\u2E50', '\uFB57' }, { '\u2E5C', '\uEF7D' }, { '\u2E69', '\uEFFF' }, { '\u2E78', '\u1378' }, { '\u2E7F', '\uFEC0' }, { '\u2E88', '\u5FF7' }, 
      { '\u2E95', '\u34BB' }, { '\u2E9E', '\u5EE3' }, { '\u2EA8', '\uF70D' }, { '\u2EB2', '\uEFF6' }, { '\u2EBF', '\uD7FE' }, { '\u2ECC', '\u00BF' }, { '\u2ED3', '\uF59D' }, { '\u2EDE', '\uF7F7' }, { '\u2EEC', '\u51DE' }, { '\u2EF5', '\uFFE0' }, 
      { '\u2F00', '\uFEC9' }, { '\u2F0B', '\u037F' }, { '\u2F14', '\u5F01' }, { '\u2F1B', '\uBFEF' }, { '\u2F29', '\u9FF1' }, { '\u2F34', '\u60A7' }, { '\u2F3B', '\uEF1D' }, { '\u2F46', '\uF1FF' }, { '\u2F53', '\017' }, { '\u2F57', Character.MIN_VALUE }, 
      { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, 
      { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', Character.MIN_VALUE }, { '\u2F57', '\u3C80' }, { '\u2F5C', '\uFB4D' }, { '\u2F67', '\uD91F' }, { '\u2F71', '\u7B3A' }, { '\u2F7B', '\uFEE3' }, { '\u2F87', '\u3FE9' }, { '\u2F92', '\uDC7F' }, 
      { '\u2F9E', '?' }, { '\u2FA4', Character.MIN_VALUE }, { '\u2FA4', Character.MIN_VALUE }, { '\u2FA4', '\u5000' }, { '\u2FA6', '\uF51F' }, { '\u2FB1', '\uBE07' }, { '\u2FBA', '\uFC1D' }, { '\u2FC4', '\uF91B' }, { '\u2FCE', '\uBC1E' }, { '\u2FD7', '\u71FF' }, 
      { '\u2FE3', '\u6FF9' }, { '\u2FEF', '\u5BBE' }, { '\u2FFA', '\u5796' }, { '\u3003', '\u9B1B' }, { '\u300C', '\u7FFF' }, { '\u301B', '\uFFFC' }, { '\u3029', '\u872E' }, { '\u3031', '\uAFE7' }, { '\u303D', '\uEBF5' }, { '\u3049', '\uF34F' }, 
      { '\u3054', '\uDFFD' }, { '\u3062', '\uE725' }, { '\u306B', '\u0BDC' }, { '\u3073', '\u5D44' }, { '\u307A', '\u5747' }, { '\u3083', '\uFDDD' }, { '\u3090', '\uED3F' }, { '\u309C', '\u7790' }, { '\u30A4', '\u7D7F' }, { '\u30B1', '\u8AC8' }, 
      { '\u30B7', '\uFAFA' }, { '\u30C3', '\uF3F9' }, { '\u30CF', '\u202A' }, { '\u30D3', '\uEF4B' }, { '\u30DE', '\uF5FF' }, { '\u30EC', '\u79CF' }, { '\u30F7', '\uABD3' }, { '\u3101', '\u0BA5' }, { '\u3108', '\uF77A' }, { '\u3114', '\uFB8F' }, 
      { '\u3120', '\u8EBD' }, { '\u312A', '\037' }, { '\u312F', Character.MIN_VALUE }, { '\u312F', Character.MIN_VALUE }, { '\u312F', '\uF300' }, { '\u3135', '\uFD4E' }, { '\u3140', '\u1A57' }, { '\u3148', '\u8800' }, { '\u314A', '\uAEAC' }, { '\u3153', '\u7654' }, 
      { '\u315B', '\u17AD' }, { '\u3164', '\uCDFF' }, { '\u3171', '\uFFB2' }, { '\u317D', '\uF42F' }, { '\u3187', '\u5BAA' }, { '\u3190', '\uDBFF' }, { '\u319E', '\002' }, { '\u319F', Character.MIN_VALUE }, { '\u319F', Character.MIN_VALUE }, { '\u319F', '\u73C0' }, 
      { '\u31A6', '\uF9EA' }, { '\u31B1', '\u2E3F' }, { '\u31BB', '\uFA8E' }, { '\u31C5', '\uBBFF' }, { '\u31D3', '\u76BC' }, { '\u31DD', '\uFFD3' }, { '\u31EA', '\uEEFE' }, { '\u31F7', '\u7E72' }, { '\u3201', '\u7EBD' }, { '\u320D', '\uE7F7' }, 
      { '\u321A', '\uF77F' }, { '\u3228', '\uCEFD' }, { '\u3234', '\u0FF5' }, { '\u323E', Character.MIN_VALUE }, { '\u323E', Character.MIN_VALUE }, { '\u323E', Character.MIN_VALUE }, { '\u323E', '\uA900' }, { '\u3242', '\uDB9B' }, { '\u324D', '\uA4C7' }, { '\u3255', '\u917F' }, 
      { '\u325F', '\uF8CA' }, { '\u3268', '\u7ECE' }, { '\u3273', '\u7D7A' }, { '\u327E', '\uC7E7' }, { '\u3289', '\uCBBD' }, { '\u3294', '\uDCAE' }, { '\u329E', '\uFD7E' }, { '\u32AB', '\u8F76' }, { '\u32B5', '\u91D3' }, { '\u32BD', '\u7CF3' }, 
      { '\u32C8', '\u01E5' }, { '\u32CE', '\u4C2F' }, { '\u32D6', '\uED77' }, { '\u32E2', '\uA360' }, { '\u32E8', '\u07DB' }, { '\u32F1', '\u5EF8' }, { '\u32FB', '\u1DF7' }, { '\u3306', '\u2181' }, { '\u330A', '\u6BE0' }, { '\u3312', '\u309C' }, 
      { '\u3318', '\u3B3A' }, { '\u3321', '\uFADE' }, { '\u332D', '\u7F53' }, { '\u3338', '\uC3F5' }, { '\u3342', '\u61CD' }, { '\u334A', '\u07BA' }, { '\u3352', Character.MIN_VALUE }, { '\u3352', Character.MIN_VALUE }, { '\u3352', Character.MIN_VALUE }, { '\u3352', Character.MIN_VALUE }, 
      { '\u3352', Character.MIN_VALUE }, { '\u3352', Character.MIN_VALUE }, { '\u3352', '\u26E0' }, { '\u3358', '\uBEFE' }, { '\u3365', '\u03F9' }, { '\u336D', '\uEBB5' }, { '\u3378', '\uE36D' }, { '\u3382', '\uE9CB' }, { '\u338C', '\u9C2F' }, { '\u3395', '\uBFDE' }, 
      { '\u33A2', '\u9F83' }, { '\u33AB', '\uABBF' }, { '\u33B7', '\u1FF7' }, { '\u33C3', '\uFFD5' }, { '\u33D0', '\uB7DF' }, { '\u33DD', '\uDFFE' }, { '\u33EB', '\uFDAE' }, { '\u33F7', '\uFFEF' }, { '\u3406', '\uFB7E' }, { '\u3413', '\uEFFD' }, 
      { '\u3421', '\uAAFF' }, { '\u342D', '\u6EBF' }, { '\u3439', Character.MIN_VALUE }, { '\u3439', Character.MIN_VALUE }, { '\u3439', Character.MIN_VALUE }, { '\u3439', Character.MIN_VALUE }, { '\u3439', Character.MIN_VALUE }, { '\u3439', '\uB620' }, { '\u343F', '\u7FCD' }, { '\u344B', '\uBE9E' }, 
      { '\u3456', '\u62B3' }, { '\u345E', '\u58F1' }, { '\u3466', '\uF10D' }, { '\u346E', '\uFD7B' }, { '\u347B', '\uE9F1' }, { '\u3485', '\uBEFD' }, { '\u3492', '\uC6C3' }, { '\u349A', '\u5F6D' }, { '\u34A5', '\uFF3D' }, { '\u34B2', '\u69FF' }, 
      { '\u34BE', '\uFFCF' }, { '\u34CC', '\uFBF4' }, { '\u34D8', '\uDCFB' }, { '\u34E4', '\u4FF7' }, { '\u34F0', '\u2000' }, { '\u34F1', '\u1137' }, { '\u34F8', '\025' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGEFA = new char[][] { { '\u34FB', '\u3000' } };
  
  private static final char[][] BIG5_TO_UNICODE_PAGEFE = new char[][] { 
      { '\u34FD', Character.MIN_VALUE }, { '\u34FD', Character.MIN_VALUE }, { '\u34FD', Character.MIN_VALUE }, { '\u34FD', '\uFFFB' }, { '\u350C', '\uFE1F' }, { '\u3518', '\uFEF5' }, { '\u3525', '\u0E7F' }, { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, 
      { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, { '\u352F', Character.MIN_VALUE }, { '\u352F', '\uFF7A' }, { '\u353C', Character.MAX_VALUE }, { '\u354C', Character.MAX_VALUE }, { '\u355C', '\u97FF' }, 
      { '\u3569', '\uFFFE' }, { '\u3578', '\u3FFF' }, { '\u3586', '\020' } };
  
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public int getMaxBytesPerChar() {
    return 2;
  }
  
  public int convert(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    char[] arrayOfChar = null;
    if (paramInt1 < 256) {
      arrayOfChar = BIG5_TO_UNICODE_PAGE00[paramInt1 >> 4];
    } else if (paramInt1 >= 512 && paramInt1 < 1120) {
      arrayOfChar = BIG5_TO_UNICODE_PAGE02[(paramInt1 >> 4) - 32];
    } else if (paramInt1 >= 8192 && paramInt1 < 8896) {
      arrayOfChar = BIG5_TO_UNICODE_PAGE20[(paramInt1 >> 4) - 512];
    } else if (paramInt1 >= 9216 && paramInt1 < 9808) {
      arrayOfChar = BIG5_TO_UNICODE_PAGE24[(paramInt1 >> 4) - 576];
    } else if (paramInt1 >= 12288 && paramInt1 < 13280) {
      arrayOfChar = BIG5_TO_UNICODE_PAGE30[(paramInt1 >> 4) - 768];
    } else if (paramInt1 >= 19968 && paramInt1 < 40880) {
      arrayOfChar = BIG5_TO_UNICODE_PAGE4E[(paramInt1 >> 4) - 1248];
    } else if (paramInt1 >= 64000 && paramInt1 < 64016) {
      arrayOfChar = BIG5_TO_UNICODE_PAGEFA[(paramInt1 >> 4) - 4000];
    } else if (paramInt1 >= 65024 && paramInt1 < 65392) {
      arrayOfChar = BIG5_TO_UNICODE_PAGEFE[(paramInt1 >> 4) - 4064];
    } 
    if (arrayOfChar != null) {
      char c = arrayOfChar[1];
      int i = paramInt1 & 0xF;
      if ((c & 1 << i) != 0) {
        char c1;
        int j = c & (1 << i) - 1;
        j = (j & 0x5555) + ((j & 0xAAAA) >> 1);
        j = (j & 0x3333) + ((j & 0xCCCC) >> 2);
        j = (j & 0xF0F) + ((j & 0xF0F0) >> 4);
        j = (j & 0xFF) + (j >> 8);
        int k = arrayOfChar[0] + j;
        if (k >= 0 && k < 800) {
          c1 = BIG5_TO_CHARSET00[arrayOfChar[0] + j];
        } else if (k >= 800 && k < 1600) {
          c1 = BIG5_TO_CHARSET01[arrayOfChar[0] + j - 800];
        } else if (k >= 1600 && k < 2400) {
          c1 = BIG5_TO_CHARSET02[arrayOfChar[0] + j - 1600];
        } else if (k >= 2400 && k < 3200) {
          c1 = BIG5_TO_CHARSET03[arrayOfChar[0] + j - 2400];
        } else if (k >= 3200 && k < 4000) {
          c1 = BIG5_TO_CHARSET04[arrayOfChar[0] + j - 3200];
        } else if (k >= 4000 && k < 4800) {
          c1 = BIG5_TO_CHARSET05[arrayOfChar[0] + j - 4000];
        } else if (k >= 4800 && k < 5600) {
          c1 = BIG5_TO_CHARSET06[arrayOfChar[0] + j - 4800];
        } else if (k >= 5600 && k < 6400) {
          c1 = BIG5_TO_CHARSET07[arrayOfChar[0] + j - 5600];
        } else if (k >= 6400 && k < 7200) {
          c1 = BIG5_TO_CHARSET08[arrayOfChar[0] + j - 6400];
        } else if (k >= 7200 && k < 8000) {
          c1 = BIG5_TO_CHARSET09[arrayOfChar[0] + j - 7200];
        } else if (k >= 8000 && k < 8800) {
          c1 = BIG5_TO_CHARSET10[arrayOfChar[0] + j - 8000];
        } else if (k >= 8800 && k < 9600) {
          c1 = BIG5_TO_CHARSET11[arrayOfChar[0] + j - 8800];
        } else if (k >= 9600 && k < 10400) {
          c1 = BIG5_TO_CHARSET12[arrayOfChar[0] + j - 9600];
        } else if (k >= 10400 && k < 11200) {
          c1 = BIG5_TO_CHARSET13[arrayOfChar[0] + j - 10400];
        } else if (k >= 11200 && k < 12000) {
          c1 = BIG5_TO_CHARSET14[arrayOfChar[0] + j - 11200];
        } else if (k >= 12000 && k < 12800) {
          c1 = BIG5_TO_CHARSET15[arrayOfChar[0] + j - 12000];
        } else {
          c1 = BIG5_TO_CHARSET16[arrayOfChar[0] + j - 12800];
        } 
        paramArrayOfbyte[paramInt2] = (byte)(c1 >> 8);
        paramArrayOfbyte[paramInt2 + 1] = (byte)(c1 & 0xFF);
        return 0;
      } 
    } 
    return -1;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\Big5CharToByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
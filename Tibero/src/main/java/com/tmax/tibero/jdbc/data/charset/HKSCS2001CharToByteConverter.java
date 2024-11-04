package com.tmax.tibero.jdbc.data.charset;

public class HKSCS2001CharToByteConverter {
  private static final char[] HKSCS2001_TO_CHARSET = new char[] { 
      '豋', '豖', '豄', '貪', '豝', '賃', '豠', '賋', '賄', '豘', 
      '豉', '賒', '豲', '豯', '豳', '豰', '豀', '豱', '豛', '豨', 
      '豵', '賌', '豷', '豸', '賅', '責', '賙', '豈', '賔', '豺', 
      '豻', '豼', '豍', '豾', '豒', '賊', '貢', '貣', '豞', '貥', 
      '豁', '豧', '豇', '豑', '貧', '販', '豓', '豚', '貭', '豫', 
      '豮', '豙', '豣', '貱', '貲', '貳', '豶', '賜', '貴', '賐', 
      '貵', '貽', '貶', '賎', '象', '豅', '貸', '貮', '貺', '豏', 
      '貼', '豐', '貿', '豪', '豦', '賉', '貾', '豃', '豭', '豴', 
      '買', '貹', '費', '賀', '賗', '賘', '賚', '賂', '豗', '豹', 
      '豩', '豽', '豔', '財', '貤', '豆', '貨', '賏', '貫', '豊', 
      '貰', '貯', '豌', '賕', '賓', '賖', '賑', '豜', '豬', '豎', 
      '豥', '賁', '豤', '豂', '豕', '豟' };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE35 = new char[][] { { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, 'Ȁ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE3C = new char[][] { 
      { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', Character.MIN_VALUE }, { '\001', 'ࠀ' }, { '\002', Character.MIN_VALUE }, 
      { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, 
      { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', Character.MIN_VALUE }, { '\002', 'Ȁ' }, { '\003', Character.MIN_VALUE }, 
      { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, 
      { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', Character.MIN_VALUE }, { '\003', 'ࠀ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE40 = new char[][] { { '\004', 'Ȁ' }, { '\005', Character.MIN_VALUE }, { '\005', Character.MIN_VALUE }, { '\005', Character.MIN_VALUE }, { '\005', Character.MIN_VALUE }, { '\005', Character.MIN_VALUE }, { '\005', Character.MIN_VALUE }, { '\005', '\002' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE42 = new char[][] { 
      { '\006', Character.MIN_VALUE }, { '\006', Character.MIN_VALUE }, { '\006', '\b' }, { '\007', Character.MIN_VALUE }, { '\007', Character.MIN_VALUE }, { '\007', Character.MIN_VALUE }, { '\007', Character.MIN_VALUE }, { '\007', '@' }, { '\b', Character.MIN_VALUE }, { '\b', Character.MIN_VALUE }, 
      { '\b', '\004' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE4B = new char[][] { 
      { '\t', Character.MIN_VALUE }, { '\t', '\001' }, { '\n', '\001' }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, 
      { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, 
      { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', Character.MIN_VALUE }, { '\013', '\002' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE4E = new char[][] { 
      { '\f', Character.MIN_VALUE }, { '\f', Character.MIN_VALUE }, { '\f', '耀' }, { '\r', Character.MIN_VALUE }, { '\r', Character.MIN_VALUE }, { '\r', Character.MIN_VALUE }, { '\r', Character.MIN_VALUE }, { '\r', Character.MIN_VALUE }, { '\r', Character.MIN_VALUE }, { '\r', Character.MIN_VALUE }, 
      { '\r', '\b' }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', Character.MIN_VALUE }, { '\016', '' }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, 
      { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, { '\017', Character.MIN_VALUE }, 
      { '\017', Character.MIN_VALUE }, { '\017', '\001' }, { '\020', Character.MIN_VALUE }, { '\020', Character.MIN_VALUE }, { '\020', Character.MIN_VALUE }, { '\020', 'ࠀ' }, { '\021', Character.MIN_VALUE }, { '\021', Character.MIN_VALUE }, { '\021', Character.MIN_VALUE }, { '\021', Character.MIN_VALUE }, 
      { '\021', Character.MIN_VALUE }, { '\021', Character.MIN_VALUE }, { '\021', Character.MIN_VALUE }, { '\021', 'က' }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, 
      { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', Character.MIN_VALUE }, { '\022', '䀀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE53 = new char[][] { 
      { '\023', Character.MIN_VALUE }, { '\023', Character.MIN_VALUE }, { '\023', '\020' }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, 
      { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', Character.MIN_VALUE }, { '\024', '\020' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE57 = new char[][] { 
      { '\025', Character.MIN_VALUE }, { '\025', Character.MIN_VALUE }, { '\025', Character.MIN_VALUE }, { '\025', ' ' }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, 
      { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, 
      { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', Character.MIN_VALUE }, { '\026', '\004' }, { '\027', Character.MIN_VALUE }, { '\027', Character.MIN_VALUE }, { '\027', Character.MIN_VALUE }, { '\027', '\001' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE5A = new char[][] { 
      { '\030', Character.MIN_VALUE }, { '\030', 'Ѐ' }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, 
      { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', Character.MIN_VALUE }, { '\031', ' ' }, { '\032', Character.MIN_VALUE }, { '\032', Character.MIN_VALUE }, 
      { '\032', Character.MIN_VALUE }, { '\032', Character.MIN_VALUE }, { '\032', Character.MIN_VALUE }, { '\032', Character.MIN_VALUE }, { '\032', Character.MIN_VALUE }, { '\032', '@' }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, 
      { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, 
      { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', Character.MIN_VALUE }, { '\033', ' ' }, { '\034', Character.MIN_VALUE }, { '\034', Character.MIN_VALUE }, 
      { '\034', Character.MIN_VALUE }, { '\034', Character.MIN_VALUE }, { '\034', Character.MIN_VALUE }, { '\034', Character.MIN_VALUE }, { '\034', Character.MIN_VALUE }, { '\034', 'ࠀ' }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, 
      { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, 
      { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', Character.MIN_VALUE }, { '\035', 'Ȅ' }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, 
      { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, 
      { '\037', Character.MIN_VALUE }, { '\037', Character.MIN_VALUE }, { '\037', '\004' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE61 = new char[][] { { ' ', Character.MIN_VALUE }, { ' ', Character.MIN_VALUE }, { ' ', '\004' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE65 = new char[][] { { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', Character.MIN_VALUE }, { '!', 'ࠀ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE67 = new char[][] { { '"', Character.MIN_VALUE }, { '"', 'Ȁ' }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', 'ࠀ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE69 = new char[][] { 
      { '$', Character.MIN_VALUE }, { '$', 'Ȁ' }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, 
      { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, 
      { '%', '\b' }, { '&', Character.MIN_VALUE }, { '&', '\b' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE6C = new char[][] { 
      { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, 
      { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', '耀' }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, 
      { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, 
      { '(', Character.MIN_VALUE }, { '(', '䀀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE70 = new char[][] { 
      { ')', Character.MIN_VALUE }, { ')', Character.MIN_VALUE }, { ')', Character.MIN_VALUE }, { ')', Character.MIN_VALUE }, { ')', ' ' }, { '*', Character.MIN_VALUE }, { '*', Character.MIN_VALUE }, { '*', '' }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, 
      { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, 
      { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', Character.MIN_VALUE }, { '+', '\002' }, { ',', Character.MIN_VALUE }, { ',', Character.MIN_VALUE }, { ',', Character.MIN_VALUE }, { ',', Character.MIN_VALUE }, 
      { ',', Character.MIN_VALUE }, { ',', Character.MIN_VALUE }, { ',', '\001' }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, 
      { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, 
      { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', Character.MIN_VALUE }, { '-', 'က' }, { '.', Character.MIN_VALUE }, { '.', Character.MIN_VALUE }, { '.', 'Ѐ' }, 
      { '/', Character.MIN_VALUE }, { '/', Character.MIN_VALUE }, { '/', Character.MIN_VALUE }, { '/', Character.MIN_VALUE }, { '/', '\004' }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, 
      { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', Character.MIN_VALUE }, { '0', 'Ȁ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE76 = new char[][] { 
      { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, 
      { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', Character.MIN_VALUE }, { '1', 'Ȁ' }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, 
      { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', Character.MIN_VALUE }, { '2', ' ' }, 
      { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, 
      { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', Character.MIN_VALUE }, { '3', '\020' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE7A = new char[][] { 
      { '4', Character.MIN_VALUE }, { '4', Character.MIN_VALUE }, { '4', 'Ѐ' }, { '5', Character.MIN_VALUE }, { '5', Character.MIN_VALUE }, { '5', Character.MIN_VALUE }, { '5', Character.MIN_VALUE }, { '5', Character.MIN_VALUE }, { '5', Character.MIN_VALUE }, { '5', '\b' }, 
      { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, 
      { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, { '6', Character.MIN_VALUE }, 
      { '6', 'က' }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, 
      { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', Character.MIN_VALUE }, { '7', '\b' }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, 
      { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', Character.MIN_VALUE }, { '8', 'ࠀ' }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, 
      { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', Character.MIN_VALUE }, { '9', 'ࠀ' }, 
      { ':', 'က' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE82 = new char[][] { 
      { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, { ';', Character.MIN_VALUE }, 
      { ';', Character.MIN_VALUE }, { ';', '耀' }, { '<', Character.MIN_VALUE }, { '<', Character.MIN_VALUE }, { '<', Character.MIN_VALUE }, { '<', 'Ā' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE85 = new char[][] { 
      { '=', Character.MIN_VALUE }, { '=', Character.MIN_VALUE }, { '=', Character.MIN_VALUE }, { '=', Character.MIN_VALUE }, { '=', Character.MIN_VALUE }, { '=', '耀' }, { '>', Character.MIN_VALUE }, { '>', Character.MIN_VALUE }, { '>', Character.MIN_VALUE }, { '>', '' }, 
      { '?', Character.MIN_VALUE }, { '?', Character.MIN_VALUE }, { '?', Character.MIN_VALUE }, { '?', '@' }, { '@', Character.MIN_VALUE }, { '@', Character.MIN_VALUE }, { '@', '耀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE88 = new char[][] { { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', Character.MIN_VALUE }, { 'A', '耀' }, { 'B', '\001' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE8B = new char[][] { { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', Character.MIN_VALUE }, { 'C', '\b' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE8E = new char[][] { 
      { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, { 'D', Character.MIN_VALUE }, 
      { 'D', Character.MIN_VALUE }, { 'D', 'Ȁ' }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, 
      { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', Character.MIN_VALUE }, { 'E', '' }, { 'F', Character.MIN_VALUE }, { 'F', '耀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE91 = new char[][] { 
      { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, 
      { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', Character.MIN_VALUE }, { 'G', '@' }, { 'H', Character.MIN_VALUE }, { 'H', Character.MIN_VALUE }, { 'H', '\002' }, { 'I', Character.MIN_VALUE }, 
      { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, 
      { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, 
      { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', Character.MIN_VALUE }, { 'I', '\020' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE97 = new char[][] { 
      { 'J', Character.MIN_VALUE }, { 'J', Character.MIN_VALUE }, { 'J', Character.MIN_VALUE }, { 'J', Character.MIN_VALUE }, { 'J', Character.MIN_VALUE }, { 'J', ' ' }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, 
      { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, 
      { 'K', Character.MIN_VALUE }, { 'K', Character.MIN_VALUE }, { 'K', 'Ā' }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, 
      { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, 
      { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', Character.MIN_VALUE }, { 'L', '\001' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE9F = new char[][] { 
      { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, { 'M', Character.MIN_VALUE }, 
      { 'M', '￀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE214 = new char[][] { { 'W', Character.MIN_VALUE }, { 'W', Character.MIN_VALUE }, { 'W', Character.MIN_VALUE }, { 'W', '耀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE219 = new char[][] { { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', Character.MIN_VALUE }, { 'X', '\001' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE21D = new char[][] { 
      { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, { 'Y', Character.MIN_VALUE }, 
      { 'Y', Character.MIN_VALUE }, { 'Y', '@' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE220 = new char[][] { { 'Z', Character.MIN_VALUE }, { 'Z', Character.MIN_VALUE }, { 'Z', Character.MIN_VALUE }, { 'Z', Character.MIN_VALUE }, { 'Z', Character.MIN_VALUE }, { 'Z', Character.MIN_VALUE }, { 'Z', Character.MIN_VALUE }, { 'Z', '䀀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE227 = new char[][] { { '[', Character.MIN_VALUE }, { '[', '\004' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE232 = new char[][] { 
      { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', Character.MIN_VALUE }, { '\\', '\002' }, { ']', Character.MIN_VALUE }, 
      { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, 
      { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, { ']', Character.MIN_VALUE }, 
      { ']', Character.MIN_VALUE }, { ']', 'Ȁ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE23C = new char[][] { { '^', Character.MIN_VALUE }, { '^', Character.MIN_VALUE }, { '^', Character.MIN_VALUE }, { '^', Character.MIN_VALUE }, { '^', Character.MIN_VALUE }, { '^', Character.MIN_VALUE }, { '^', '\b' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE241 = new char[][] { { '_', Character.MIN_VALUE }, { '_', Character.MIN_VALUE }, { '_', Character.MIN_VALUE }, { '_', Character.MIN_VALUE }, { '_', '䀀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE245 = new char[][] { { '`', ' ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE249 = new char[][] { 
      { 'a', Character.MIN_VALUE }, { 'a', Character.MIN_VALUE }, { 'a', Character.MIN_VALUE }, { 'a', Character.MIN_VALUE }, { 'a', Character.MIN_VALUE }, { 'a', Character.MIN_VALUE }, { 'a', Character.MIN_VALUE }, { 'a', 'ࠀ' }, { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, 
      { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, { 'b', Character.MIN_VALUE }, { 'b', '\b' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE251 = new char[][] { 
      { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, 
      { 'c', Character.MIN_VALUE }, { 'c', Character.MIN_VALUE }, { 'c', ' ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE256 = new char[][] { { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', Character.MIN_VALUE }, { 'd', ' ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE25C = new char[][] { 
      { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, { 'e', Character.MIN_VALUE }, 
      { 'e', '\020' }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', Character.MIN_VALUE }, { 'f', '\001' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE26B = new char[][] { { 'g', Character.MIN_VALUE }, { 'g', ' ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE26D = new char[][] { { 'h', Character.MIN_VALUE }, { 'h', Character.MIN_VALUE }, { 'h', Character.MIN_VALUE }, { 'h', Character.MIN_VALUE }, { 'h', Character.MIN_VALUE }, { 'h', Character.MIN_VALUE }, { 'h', Character.MIN_VALUE }, { 'h', '\020' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE26F = new char[][] { 
      { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, { 'i', Character.MIN_VALUE }, 
      { 'i', Character.MIN_VALUE }, { 'i', '䀀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE271 = new char[][] { { 'j', ' ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE287 = new char[][] { { 'k', '耀' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE289 = new char[][] { 
      { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, 
      { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', Character.MIN_VALUE }, { 'l', '\b' }, { 'm', Character.MIN_VALUE }, { 'm', Character.MIN_VALUE }, { 'm', Character.MIN_VALUE }, { 'm', Character.MIN_VALUE }, { 'm', '\004' }, 
      { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, { 'n', Character.MIN_VALUE }, 
      { 'n', 'Ѐ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE28D = new char[][] { 
      { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, { 'o', Character.MIN_VALUE }, 
      { 'o', Character.MIN_VALUE }, { 'o', 'Ȁ' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE299 = new char[][] { { 'p', Character.MIN_VALUE }, { 'p', Character.MIN_VALUE }, { 'p', Character.MIN_VALUE }, { 'p', Character.MIN_VALUE }, { 'p', '' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE29C = new char[][] { { 'q', Character.MIN_VALUE }, { 'q', Character.MIN_VALUE }, { 'q', Character.MIN_VALUE }, { 'q', Character.MIN_VALUE }, { 'q', Character.MIN_VALUE }, { 'q', Character.MIN_VALUE }, { 'q', Character.MIN_VALUE }, { 'q', '\b' } };
  
  private static final char[][] HKSCS2001_TO_UNICODE_PAGE2A1 = new char[][] { 
      { 'r', '' }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, 
      { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, 
      { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', Character.MIN_VALUE }, { 's', 'Ѐ' } };
  
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public int getMaxBytesPerChar() {
    return 2;
  }
  
  public int convert(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    char[] arrayOfChar = null;
    if (paramInt1 < 40704) {
      if (paramInt1 < 26880) {
        if (paramInt1 >= 13568 && paramInt1 < 13664) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE35[(paramInt1 >> 4) - 848];
        } else if (paramInt1 >= 15360 && paramInt1 < 16096) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE3C[(paramInt1 >> 4) - 960];
        } else if (paramInt1 >= 16384 && paramInt1 < 16512) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE40[(paramInt1 >> 4) - 1024];
        } else if (paramInt1 >= 16896 && paramInt1 < 17072) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE42[(paramInt1 >> 4) - 1056];
        } else if (paramInt1 >= 19200 && paramInt1 < 19600) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE4B[(paramInt1 >> 4) - 1200];
        } else if (paramInt1 >= 19968 && paramInt1 < 20912) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE4E[(paramInt1 >> 4) - 1248];
        } else if (paramInt1 >= 21248 && paramInt1 < 21568) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE53[(paramInt1 >> 4) - 1328];
        } else if (paramInt1 >= 22272 && paramInt1 < 22752) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE57[(paramInt1 >> 4) - 1392];
        } else if (paramInt1 >= 23040 && paramInt1 < 24528) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE5A[(paramInt1 >> 4) - 1440];
        } else if (paramInt1 >= 24832 && paramInt1 < 24880) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE61[(paramInt1 >> 4) - 1552];
        } else if (paramInt1 >= 25856 && paramInt1 < 26000) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE65[(paramInt1 >> 4) - 1616];
        } else if (paramInt1 >= 26368 && paramInt1 < 26480) {
          arrayOfChar = HKSCS2001_TO_UNICODE_PAGE67[(paramInt1 >> 4) - 1648];
        } 
      } else if (paramInt1 >= 26880 && paramInt1 < 27248) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE69[(paramInt1 >> 4) - 1680];
      } else if (paramInt1 >= 27648 && paramInt1 < 28160) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE6C[(paramInt1 >> 4) - 1728];
      } else if (paramInt1 >= 28672 && paramInt1 < 29888) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE70[(paramInt1 >> 4) - 1792];
      } else if (paramInt1 >= 30208 && paramInt1 < 30960) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE76[(paramInt1 >> 4) - 1888];
      } else if (paramInt1 >= 31232 && paramInt1 < 32368) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE7A[(paramInt1 >> 4) - 1952];
      } else if (paramInt1 >= 33280 && paramInt1 < 33536) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE82[(paramInt1 >> 4) - 2080];
      } else if (paramInt1 >= 34048 && paramInt1 < 34320) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE85[(paramInt1 >> 4) - 2128];
      } else if (paramInt1 >= 34816 && paramInt1 < 34976) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE88[(paramInt1 >> 4) - 2176];
      } else if (paramInt1 >= 35584 && paramInt1 < 35728) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE8B[(paramInt1 >> 4) - 2224];
      } else if (paramInt1 >= 36352 && paramInt1 < 36816) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE8E[(paramInt1 >> 4) - 2272];
      } else if (paramInt1 >= 37120 && paramInt1 < 37888) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE91[(paramInt1 >> 4) - 2320];
      } else if (paramInt1 >= 38656 && paramInt1 < 39408) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE97[(paramInt1 >> 4) - 2416];
      } 
    } else if (paramInt1 < 153088) {
      if (paramInt1 >= 40704 && paramInt1 < 40880) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE9F[(paramInt1 >> 4) - 2544];
      } else if (paramInt1 >= 136192 && paramInt1 < 136256) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE214[(paramInt1 >> 4) - 8512];
      } else if (paramInt1 >= 137472 && paramInt1 < 137616) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE219[(paramInt1 >> 4) - 8592];
      } else if (paramInt1 >= 138496 && paramInt1 < 138688) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE21D[(paramInt1 >> 4) - 8656];
      } else if (paramInt1 >= 139264 && paramInt1 < 139392) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE220[(paramInt1 >> 4) - 8704];
      } else if (paramInt1 >= 141056 && paramInt1 < 141088) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE227[(paramInt1 >> 4) - 8816];
      } else if (paramInt1 >= 143872 && paramInt1 < 144384) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE232[(paramInt1 >> 4) - 8992];
      } else if (paramInt1 >= 146432 && paramInt1 < 146544) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE23C[(paramInt1 >> 4) - 9152];
      } else if (paramInt1 >= 147712 && paramInt1 < 147792) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE241[(paramInt1 >> 4) - 9232];
      } else if (paramInt1 >= 148736 && paramInt1 < 148752) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE245[(paramInt1 >> 4) - 9296];
      } else if (paramInt1 >= 149760 && paramInt1 < 150048) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE249[(paramInt1 >> 4) - 9360];
      } else if (paramInt1 >= 151808 && paramInt1 < 152016) {
        arrayOfChar = HKSCS2001_TO_UNICODE_PAGE251[(paramInt1 >> 4) - 9488];
      } 
    } else if (paramInt1 >= 153088 && paramInt1 < 153248) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE256[(paramInt1 >> 4) - 9568];
    } else if (paramInt1 >= 154624 && paramInt1 < 154944) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE25C[(paramInt1 >> 4) - 9664];
    } else if (paramInt1 >= 158464 && paramInt1 < 158496) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE26B[(paramInt1 >> 4) - 9904];
    } else if (paramInt1 >= 158976 && paramInt1 < 159104) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE26D[(paramInt1 >> 4) - 9936];
    } else if (paramInt1 >= 159488 && paramInt1 < 159680) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE26F[(paramInt1 >> 4) - 9968];
    } else if (paramInt1 >= 160000 && paramInt1 < 160016) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE271[(paramInt1 >> 4) - 10000];
    } else if (paramInt1 >= 165632 && paramInt1 < 165648) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE287[(paramInt1 >> 4) - 10352];
    } else if (paramInt1 >= 166144 && paramInt1 < 166640) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE289[(paramInt1 >> 4) - 10384];
    } else if (paramInt1 >= 167168 && paramInt1 < 167360) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE28D[(paramInt1 >> 4) - 10448];
    } else if (paramInt1 >= 170240 && paramInt1 < 170320) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE299[(paramInt1 >> 4) - 10640];
    } else if (paramInt1 >= 171008 && paramInt1 < 171136) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE29C[(paramInt1 >> 4) - 10688];
    } else if (paramInt1 >= 172288 && paramInt1 < 172736) {
      arrayOfChar = HKSCS2001_TO_UNICODE_PAGE2A1[(paramInt1 >> 4) - 10768];
    } 
    if (arrayOfChar != null) {
      char c = arrayOfChar[1];
      int i = paramInt1 & 0xF;
      if ((c & 1 << i) != 0) {
        int j = c & (1 << i) - 1;
        j = (j & 0x5555) + ((j & 0xAAAA) >> 1);
        j = (j & 0x3333) + ((j & 0xCCCC) >> 2);
        j = (j & 0xF0F) + ((j & 0xF0F0) >> 4);
        j = (j & 0xFF) + (j >> 8);
        char c1 = HKSCS2001_TO_CHARSET[arrayOfChar[0] + j];
        paramArrayOfbyte[paramInt2] = (byte)(c1 >> 8);
        paramArrayOfbyte[paramInt2 + 1] = (byte)(c1 & 0xFF);
        return 0;
      } 
    } 
    return -1;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\HKSCS2001CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
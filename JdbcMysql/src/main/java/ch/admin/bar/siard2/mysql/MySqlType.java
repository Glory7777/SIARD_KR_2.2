package ch.admin.bar.siard2.mysql;




public enum MySqlType
{
  BIT("bit"),
  TINYINT("tinyint"),
  TINYINTU("tinyint unsigned"),
  BOOL("bool"),
  BOOLEAN("boolean"),
  SMALLINT("smallint"),
  SMALLINTU("smallint unsigned"),
  MEDIUMINT("mediumint"),
  MEDIUMINTU("mediumint unsigned"),
  INT("int"),
  INTU("int unsigned"),
  INTEGER("integer"),
  INTEGERU("integer unsigned"),
  BIGINT("bigint"),
  BIGINTU("bigint unsigned"),
  FLOAT("float"),
  FLOATU("float unsigned"),
  DOUBLE("double"),
  DOUBLEU("double unsigned"),
  DECIMAL("decimal"),
  DECIMALU("decimal unsigned"),
  DATE("date"),
  DATETIME("datetime"),
  TIMESTAMP("timestamp"),
  TIME("time"),
  YEAR("year"),
  CHAR("char"),
  VARCHAR("varchar"),
  BINARY("binary"),
  VARBINARY("varbinary"),
  TINYBLOB("tinyblob"),
  TINYTEXT("tinytext"),
  BLOB("blob"),
  TEXT("text"),
  MEDIUMBLOB("mediumblob"),
  MEDIUMTEXT("mediumtext"),
  LONGBLOB("longblob"),
  LONGTEXT("longtext"),
  ENUM("enum"),
  SET("set"),
  GEOMETRY("geometry"),
  POINT("point"),
  LINESTRING("linestring"),
  POLYGON("polygon"),
  MULTIPOINT("multipoint"),
  MULTILINESTRING("multilinestring"),
  MULTIPOLYGON("multipolygon"),
  GEOMETRYCOLLECTION("geometrycollection"),
  JSON("json");
  
  private String sTypeName = null;




  
  MySqlType(String _sTypeName) {
    this.sTypeName = _sTypeName;
  }






  
  public static MySqlType getByTypeName(String _sTypeName) {
    if (_sTypeName.equals("geomcollection")) {
      return GEOMETRYCOLLECTION;
    }
    MySqlType result = null;
    for (int i = 0; i < (values()).length; i++) {
      MySqlType t = values()[i];
      if (t.getTypeName().equals(_sTypeName)) {
        result = t;
        break;
      } 
    } 
    return result;
  }




  
  public String getTypeName() {
    return this.sTypeName;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\MySqlType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
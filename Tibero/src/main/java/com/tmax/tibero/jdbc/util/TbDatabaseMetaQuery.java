package com.tmax.tibero.jdbc.util;

public class TbDatabaseMetaQuery {
  public static String escapeChar = "/";
  
  private static String QUERY_ATTRIBUTES__SELECT_CLAUSE = " SELECT NULL AS TYPE_CAT,  T.OWNER AS TYPE_SCHEM,  T.TYPE_NAME AS TYPE_NAME,  A.ATTR_NAME AS ATTR_NAME,  DECODE(ST.TYPECODE, 'OBJECT', 2002, 'COLLECTION', 2003,  DECODE(A.ATTR_TYPE_NAME, 'CHAR', 1, 'VARCHAR', 12, 'VARCHAR2', 12, 'NUMBER', 2, 'LONG', -1, 'DATE', ?, 'TIME', 92, 'TIMESTAMP', 93, 'BLOB', 2004, 'CLOB', 2005, 'RAW', -3, 'LONG RAW', -4, 'INTERVAL YEAR TO MONTH', -103, 'INTERNAL DAY TO SECOND', -104, 'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101,1111)) AS DATA_TYPE,  NVL2(A.ATTR_TYPE_OWNER, A.ATTR_TYPE_OWNER || '.' , '') || A.ATTR_TYPE_NAME AS ATTR_TYPE_NAME,  DECODE(A.ATTR_TYPE_NAME, 'NUMBER', A.PRECISION, A.LENGTH) AS ATTR_SIZE,  A.SCALE AS DECIMAL_DIGITS,  10 AS NUM_PREC_RADIX, 1 AS NULLABLE,  NULL AS REMARKS,  NULL AS ATTR_DEF,  CAST(NULL AS NUMBER) AS SQL_DATA_TYPE,  CAST(NULL AS NUMBER) AS SQL_DATETIME_SUB,  DECODE(A.ATTR_TYPE_NAME, 'CHAR', A.LENGTH, 'VARCHAR', A.LENGTH, 'VARCHAR2', A.LENGTH, 'LONG', 0, 'CLOB', 4000,0) AS CHAR_OCTET_LENGTH,  A.ATTR_NO AS ORDINAL_POSITION,  CAST('YES' AS VARCHAR2(3)) AS IS_NULLABLE,  NULL AS SCOPE_CATALOG,  NULL AS SCOPE_SCHEMA,  NULL AS SCOPE_TABLE,  CAST(NULL AS NUMBER) AS SOURCE_DATA_TYPE ";
  
  private static String QUERY_ATTRIBUTES__OID_COLUMN = ", T.TYPE_OID, ST.TYPE_OID ";
  
  private static String QUERY_ATTRIBUTES__BY_TYPENAME = " FROM ALL_TYPES T  INNER JOIN ALL_TYPE_ATTRS A  ON T.OWNER = A.OWNER  AND T.TYPE_NAME = A.TYPE_NAME  LEFT OUTER JOIN ALL_TYPES ST  ON A.ATTR_TYPE_OWNER IS NOT NULL  AND A.ATTR_TYPE_OWNER = ST.OWNER  AND A.ATTR_TYPE_NAME = ST.TYPE_NAME  WHERE T.OWNER LIKE ? ESCAPE '" + escapeChar + "' " + " AND T.TYPE_NAME LIKE ? ESCAPE '" + escapeChar + "' " + " AND A.ATTR_NAME LIKE ? ESCAPE '" + escapeChar + "' " + " ORDER BY /* TYPE_CAT,*/ TYPE_SCHEM, TYPE_NAME, ORDINAL_POSITION ";
  
  private static String QUERY_ATTRIBUTES__BY_OID = " FROM ALL_TYPES T  INNER JOIN ALL_TYPE_ATTRS A  ON T.OWNER = A.OWNER  AND T.TYPE_NAME = A.TYPE_NAME  LEFT OUTER JOIN ALL_TYPES ST  ON A.ATTR_TYPE_OWNER IS NOT NULL  AND A.ATTR_TYPE_OWNER = ST.OWNER  AND A.ATTR_TYPE_NAME = ST.TYPE_NAME  WHERE T.TYPE_OID = ?  ORDER BY /* TYPE_CAT,*/ TYPE_SCHEM, TYPE_NAME, ORDINAL_POSITION ";
  
  public static String QUERY_CATALOGS = " SELECT 'nothing' as catalogs  FROM dual  WHERE 1 = 2";
  
  private static String QUERY_COLLECTION_TYPE__SELECT_CLAUSE = " SELECT T.OWNER AS TYPE_SCHEM,  T.TYPE_NAME AS TYPE_NAME,  CT.COLL_TYPE, CT.UPPER_BOUND,  DECODE(ST.TYPECODE, 'OBJECT', 2002, 'COLLECTION', 2003,  DECODE(CT.ELEM_TYPE_NAME, 'CHAR', 1, 'VARCHAR', 12, 'VARCHAR2', 12, 'NUMBER', 2, 'LONG', -1, 'DATE', ?, 'TIME', 92, 'TIMESTAMP', 93, 'BLOB', 2004, 'CLOB', 2005, 'RAW', -3, 'LONG RAW', -4, 'INTERVAL YEAR TO MONTH', -103, 'INTERNAL DAY TO SECOND', -104, 'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101,1111)) AS DATA_TYPE,  NVL2(CT.ELEM_TYPE_OWNER, CT.ELEM_TYPE_OWNER || '.' , '') || CT.ELEM_TYPE_NAME AS ELEM_TYPE_NAME,  CT.PRECISION, CT.SCALE, T.TYPE_OID ";
  
  private static String QUERY_COLLECTION_TYPE__BY_TYPENAME = " FROM ALL_TYPES T  INNER JOIN ALL_COLL_TYPES CT  ON T.OWNER = CT.OWNER  AND T.TYPE_NAME = CT.TYPE_NAME  LEFT OUTER JOIN ALL_TYPES ST  ON CT.ELEM_TYPE_OWNER IS NOT NULL  AND CT.ELEM_TYPE_OWNER = ST.OWNER  AND CT.ELEM_TYPE_NAME = ST.TYPE_NAME  WHERE T.OWNER = ?  AND T.TYPE_NAME = ?";
  
  private static String QUERY_COLLECTION_TYPE__BY_OID = " FROM ALL_TYPES T  INNER JOIN ALL_COLL_TYPES CT  ON T.OWNER = CT.OWNER  AND T.TYPE_NAME = CT.TYPE_NAME  LEFT OUTER JOIN ALL_TYPES ST  ON CT.ELEM_TYPE_OWNER IS NOT NULL  AND CT.ELEM_TYPE_OWNER = ST.OWNER  AND CT.ELEM_TYPE_NAME = ST.TYPE_NAME  WHERE T.TYPE_OID = ?";
  
  public static String QUERY_COLUMNPRIVILEGES = " SELECT NULL AS table_cat,  p.owner AS table_schem,  p.table_name,  p.column_name,  p.grantor,  p.grantee,  p.privilege,  p.grantable AS is_grantable  FROM all_col_privs p, all_objects o  WHERE p.owner LIKE ? ESCAPE '" + escapeChar + "' " + " AND p.table_name = ? " + " AND p.column_name LIKE ? ESCAPE '" + escapeChar + "' " + " AND p.owner = o.owner " + " AND p.table_name = o.object_name " + " AND o.status <> 'INVALID' " + " ORDER BY column_name, privilege ";
  
  public static String QUERY_RELATEDKEYS = " SELECT NULL AS pktable_cat,  p.owner AS pktable_schem,  p.table_name AS pktable_name,  pc.column_name AS pkcolumn_name,  NULL AS fktable_cat,  f.owner AS fktable_schem,  f.table_name AS fktable_name,  fc.column_name AS fkcolumn_name,  fc.position AS key_seq,  NULL AS update_rule,  decode (f.delete_rule, 'CASCADE', 0, 'SET NULL', 2, 1) AS delete_rule,  f.constraint_name AS fk_name,  p.constraint_name AS pk_name,  7 deferrability  FROM all_cons_columns pc,  all_constraints p,  all_cons_columns fc,  all_constraints f  WHERE 1 = 1 AND f.con_type         = 'REFERENTIAL' AND p.owner            = f.r_owner  AND p.constraint_name  = f.r_constraint_name  AND p.con_type         = 'PRIMARY KEY'  AND pc.owner           = p.owner AND pc.constraint_name = p.constraint_name  AND pc.table_name      = p.table_name  AND fc.owner           = f.owner  AND fc.constraint_name = f.constraint_name  AND fc.table_name      = f.table_name  AND fc.position        = pc.position ";
  
  public static String QUERY_GATHER_TABLE_STATS = " call dbms_stats.gather_table_stats(?, ?)";
  
  public static String QUERY_INDEXINFO01 = " select null as table_cat,  owner as table_schem,  table_name,  0 as NON_UNIQUE,  null as index_qualifier,  null as index_name,  0 as type,  0 as ordinal_position,  null as column_name,  null as asc_or_desc,  num_rows as cardinality,  blocks as pages,  null as filter_condition  from all_tables  where table_name = ? ";
  
  public static String QUERY_INDEXINFO02 = " select null as table_cat,  i.owner as table_schem,  i.table_name,  decode (i.uniqueness,'UNIQUE', 0, 1),  null as index_qualifier,  i.index_name,  1 as type,  c.column_position as ordinal_position,  c.column_name,  decode(c.descend, 'ASC', 'A', 'D') as asc_or_desc,  i.distinct_keys as cardinality,  i.leaf_blocks as pages,  null as filter_condition  from all_indexes i,  all_ind_columns c  where i.table_name = ? ";
  
  public static String QUERY_INDEXINFO03 = " and i.owner       = c.index_owner  and i.index_name  = c.index_name  and i.table_owner = c.table_owner  and i.table_name  = c.table_name order by non_unique, type, index_name, ordinal_position ";
  
  public static String QUERY_PRIMARYKEY = "SELECT NULL AS table_cat,  c.owner AS table_schem,  c.table_name,  c.column_name,  c.position AS key_seq,  c.constraint_name AS pk_name  FROM all_cons_columns c,  all_constraints k  WHERE k.con_type = 'PRIMARY KEY'  AND k.table_name = ?  AND k.owner like ? escape '" + escapeChar + "' " + " AND k.constraint_name = c.constraint_name " + " AND k.table_name = c.table_name " + " AND k.owner = c.owner " + " ORDER BY column_name ";
  
  public static String QUERY_PROCEDURE_COLUMNS_1 = "SELECT PACKAGE_NAME AS PROCEDURE_CAT,        OWNER AS PROCEDURE_SCHEM,        OBJECT_NAME AS PROCEDURE_NAME,        ARGUMENT_NAME AS COLUMN_NAME,        DECODE(POSITION, 0, 5, DECODE(IN_OUT, 'IN', 1, 'OUT', 4, 'IN/OUT', 2, 0)) AS COLUMN_TYPE,        DECODE(DATA_TYPE, 'CHAR', 1, 'VARCHAR', 12, 'VARCHAR2', 12, 'NUMBER', 2,               'LONG', -1, ";
  
  public static String QUERY_PROCEDURE_COLUMNS_2 = "'TIME', 92, 'TIMESTAMP', 93, 'BLOB', 2004,               'CLOB', 2005, 'RAW', -3, 'LONG RAW', -4, 'INTERVAL YEAR TO MONTH', -103,               'INTERNAL DAY TO SECOND', -104, 'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101, 1111) AS DATA_TYPE,        DATA_TYPE AS TYPE_NAME,        DECODE(DATA_PRECISION, NULL, DATA_LENGTH, DATA_PRECISION) AS PRECISION,        DATA_LENGTH AS LENGTH,        DATA_SCALE AS SCALE,        10 AS RADIX,        1 AS NULLABLE,        NULL AS REMARKS,        NULL AS COLUMN_DEF,        NULL AS SQL_DATA_TYPE,        NULL AS SQL_DATETIME_SUB,        DECODE(DATA_TYPE,               'CHAR', 32767,               'VARCHAR2', 32767,               'LONG', 32767,               'RAW', 32767,               'LONG RAW', 32767,               NULL)              AS CHAR_OCTET_LENGTH,        NULL AS ORDINAL_POSITION,        'YES' AS IS_NULLABLE,        NULL AS SPECIFIC_NAME  FROM ALL_ARGUMENTS  WHERE OWNER LIKE ? ESCAPE '" + escapeChar + "'";
  
  public static String QUERY_PROCEDURES = "SELECT DECODE(PROCEDURE_NAME, NULL, NULL, OBJECT_NAME) AS PROCEDURE_CAT,        OWNER AS PROCEDURE_SCHEM,        DECODE(PROCEDURE_NAME, NULL, OBJECT_NAME, PROCEDURE_NAME) AS PROCEDURE_NAME,        NULL, NULL, NULL,        NULL AS REMARKS,        DECODE(FUNCTIONABLE, 'YES', 2, 'NO', 1) AS PROCEDURE_TYPE,        NULL AS SPECIFIC_NAME   FROM ALL_PROCEDURES WHERE OWNER LIKE ? ESCAPE '" + escapeChar + "'";
  
  public static String QUERY_FUNCTIONS_STANDALONE = "SELECT\n  -- Standalone functions\n  NULL AS FUNCTION_CAT,\n  OWNER AS FUNCTION_SCHEM,\n  OBJECT_NAME AS FUNCTION_NAME,\n  'Standalone function' AS REMARKS,\n  NULL AS SPECIFIC_NAME\nFROM ALL_OBJECTS\nWHERE OBJECT_TYPE = 'FUNCTION'\n  AND OWNER LIKE ? ESCAPE '" + escapeChar + "'\n" + "  AND OBJECT_NAME LIKE ? ESCAPE '" + escapeChar + "'\n";
  
  public static String QUERY_FUNCTIONS_PKG = "SELECT\n  -- Packaged functions\n  PACKAGE_NAME AS FUNCTION_CAT,\n  OWNER AS FUNCTION_SCHEM,\n  OBJECT_NAME AS FUNCTION_NAME,\n  'Packaged function' AS REMARKS,\n  NULL AS SPECIFIC_NAME\nFROM ALL_ARGUMENTS\nWHERE ARGUMENT_NAME IS NULL\n  AND IN_OUT = 'OUT'\n";
  
  public static String QUERY_SCHEMAS_01 = " SELECT username AS table_schem,  null table_catalog  FROM all_users ";
  
  public static String QUERY_SCHEMAS_02 = " ORDER BY table_schem ";
  
  public static String QUERY_TABLEPRIVILEGES = " SELECT DISTINCT * FROM ( SELECT NULL AS table_cat,  p.owner AS table_schem,  p.table_name,  p.grantor, p.grantee,  p.privilege,  p.grantable AS is_grantable  FROM all_tab_privs p, all_objects o  WHERE p.owner LIKE ? ESCAPE '" + escapeChar + "' " + " AND p.table_name LIKE ? ESCAPE '" + escapeChar + "' " + " AND p.owner = o.owner " + " AND p.table_name = o.object_name " + " AND o.status <> 'INVALID' " + " UNION " + " SELECT NULL AS table_cat, " + " p.owner AS table_schem, " + " p.table_name, " + " p.grantor, " + " p.grantee, " + " p.privilege, " + " p.grantable AS is_grantable " + " FROM all_col_privs p, all_objects o" + " WHERE p.owner LIKE ? ESCAPE '" + escapeChar + "' " + " AND p.table_name LIKE ? ESCAPE '" + escapeChar + "' " + " AND p.owner = o.owner " + " AND p.table_name = o.object_name " + " AND o.status <> 'INVALID' )" + " ORDER BY table_schem, table_name, privilege ";
  
  public static String QUERY_TABLES = " SELECT NULL AS table_cat,  o.owner AS table_schem,  o.object_name AS table_name,  o.object_type AS table_type,  c.comments AS remarks,  null type_cat,  null type_schema,  null type_name,  null self_referencing_col_name,  null ref_generation   FROM all_objects o,  all_tab_comments c   WHERE o.owner LIKE ? ESCAPE '" + escapeChar + "' " + " AND o.object_name LIKE ? ESCAPE '" + escapeChar + "' " + " AND o.status <> 'INVALID' " + " AND o.owner        = c.owner (+) " + " AND o.object_name  = c.table_name (+) ";
  
  public static String QUERY_TABLETYPES = " select 'TABLE' as table_type  from dual  union select 'VIEW' as table_type  from dual  union select 'SYNONYM' as table_type  from dual ";
  
  public static String QUERY_TYPEINFO = " select 'NUMBER' as type_name, 2 as data_type,  38 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'NUMBER' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NUMBER' as type_name, -7 as data_type,  1 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  '(1)' as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'NUMBER' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NUMBER' as type_name, -6 as data_type,  3 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  '(3)' as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'NUMBER' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NUMBER' as type_name, 5 as data_type,  5 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  '(5)' as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'NUMBER' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NUMBER' as type_name, 4 as data_type,  10 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  '(10)' as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'NUMBER' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NUMBER' as type_name, -5 as data_type,  38 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'NUMBER' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'FLOAT' as type_name, 6 as data_type,  38 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'FLOAT' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'REAL' as type_name, 7 as data_type,  38 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  1 as fixed_prec_scale,  0 as auto_increment,  'REAL' as local_type_name,  -84 as minimum_scale,  127 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'CHAR' as type_name, 1 as data_type,  2000 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  1 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'CHAR' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NCHAR' as type_name, -15 as data_type,  2000 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  1 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'NCHAR' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'VARCHAR2' as type_name, 12 as data_type,  4000 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  1 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'VARCHAR2' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NVARCHAR' as type_name, -9 as data_type,  4000 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  1 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'NVARCHAR' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'LONG' as type_name, -1 as data_type,  2147483647 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'LONG' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'DATE' as type_name, 91 as data_type,  0 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'DATE' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,\n NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'TIME' as type_name, 92 as data_type,  9 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'TIME' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'TIMESTAMP' as type_name, 93 as data_type,  9 as precision,  NULL as literal_prefix,  NULL as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'TIMESTAMP' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'RAW' as type_name, -3 as data_type,  2000 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  3 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'RAW' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual\n union all  select 'LONG RAW' as type_name, -4 as data_type,  2147483647 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'LONG RAW' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'BLOB' as type_name, 2004 as data_type,  -1 as precision,  null as literal_prefix,  null as literal_suffix,  NULL as create_params,  1 as nullable,  0 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'BLOB' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'CLOB' as type_name, 2005 as data_type,  -1 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,\n 1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'CLOB' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'NCLOB' as type_name, 2011 as data_type,  -1 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,\n 1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'NCLOB' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'SQLXML' as type_name, 2009 as data_type,  -1 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,\n 1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'XMLTYPE' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'ROWID' as type_name, -8 as data_type,  12 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,\n 1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'ROWID' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  union all  select 'COLLECTION' as type_name, 2003 as data_type,  0 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,\n 1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'COLLECTION' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  where 1 = ?  union all  select 'OBJECT' as type_name, 2002 as data_type,  0 as precision,  '''' as literal_prefix,  '''' as literal_suffix,  NULL as create_params,\n 1 as nullable,  1 as case_sensitive,  0 as searchable,  0 as unsigned_attribute,  0 as fixed_prec_scale,  0 as auto_increment,  'OBJECT' as local_type_name,  0 as minimum_scale,  0 as maximum_scale,  NULL as sql_data_type,  NULL as sql_datetime_sub,  10 as num_prec_radix  from dual  where 1 = ?  order by data_type ";
  
  public static String QUERY_IPARAM = "select name, type, value from vt_parameter where";
  
  public static String QUERY_UDTMETA__BY_OID = "SELECT CLIENT_INTERNAL.GETUDTMETA(?) UDT_META FROM DUAL";
  
  public static String QUERY_UDTMETA__BY_TYPENAME = "SELECT CLIENT_INTERNAL.GETUDTMETABYNAME(?, ?) UDT_META FROM DUAL";
  
  public static String QUERY_OBJUDTMETA__BY_OBJTBLID = "SELECT CLIENT_INTERNAL.GETOBJUDTMETABYOBJTBL(UPPER(?)) FROM DUAL";
  
  public static String QUERY_UDT_PSMDEF = " SELECT DECODE(T.KIND, 31, 28, 32, 29, 33, 30, -1) AS KIND_AS_TBTYPE,  T.KIND AS PSM_MEM_KIND,  T.LIMIT, T.COUNT, T.TYPE_NO,  T.PRECISION, T.SCALE, T.MEMBER_NO,  O.OBJECT_ID AS PARENT_PKG_ID  FROM ALL_PACKAGE_MEMBER_TYPES T  LEFT OUTER JOIN ALL_OBJECTS O  ON T.PACKAGE_NAME = O.OBJECT_NAME  AND T.OWNER = O.OWNER  AND O.OBJECT_TYPE = 'PACKAGE' WHERE T.TYPE_NAME = ?  AND T.PACKAGE_NAME = ?  AND T.OWNER = ? ";
  
  public static String QUERY_UDTS = " SELECT * FROM ( SELECT NULL as TYPE_CAT,  OWNER as TYPE_SCHEM,  TYPE_NAME,  NULL as CLASS_NAME, 2002 as DATA_TYPE,  NULL as REMARKS,  CAST(NULL AS NUMBER) as BASE_TYPE  FROM ALL_TYPES  WHERE OWNER like ?  AND TYPE_NAME like ?  AND TYPECODE = 'OBJECT' ) ORDER BY DATA_TYPE, TYPE_SCHEM, TYPE_NAME";
  
  private static String QUERY_COLUMNS_1 = " DECODE (b.data_precision, 0, b.data_length, b.data_precision) AS column_size,  0 AS buffer_length,  b.data_scale AS decimal_digits,  10 AS num_prec_radix,  DECODE(b.nullable, 'Y', 1, 0) AS nullable,  c.comments AS remarks,  b.data_default AS column_def,  0 AS sql_data_type,  0 AS sql_datetime_sub,  DECODE (b.data_type, 'CHAR', b.data_length, 'VARCHAR', b.data_length, 'VARCHAR2', b.data_length,  'LONG', 0,  'CLOB', 4000, 0) AS char_octet_length,  b.column_id AS ordinal_position,  DECODE(b.nullable, 'Y', 'YES', 'NO') AS is_nullable,  null AS scope_catalog,  null AS scope_schema,  null AS scope_table,  null AS source_data_type,  'NO' AS is_autoIncrement  FROM ALL_TAB_COLUMNS b  INNER JOIN ALL_OBJECTS o  ON b.owner = o.owner  AND b.table_name = o.object_name  AND o.status <> 'INVALID'  LEFT OUTER JOIN ALL_COL_COMMENTS c  ON b.owner       = c.owner  AND b.table_name  = c.table_name  AND b.column_name = c.column_name ";
  
  private static String QUERY_COLUMNS_2 = " LEFT OUTER JOIN ALL_TYPES st  ON b.data_type_owner IS NOT NULL  AND b.data_type_owner = st.owner  AND b.data_type = st.type_name ";
  
  private static String QUERY_COLUMNS_WHERE = " WHERE b.owner LIKE ? ESCAPE '" + escapeChar + "' " + " AND b.table_name LIKE ? ESCAPE '" + escapeChar + "' " + " AND b.column_name LIKE ? ESCAPE '" + escapeChar + "' " + "AND o.object_type_no not in (10, 14)" + " ORDER BY b.owner, b.table_name, b.column_id ";
  
  private static String QUERY_COLUMNS_INCLUDE_SYNONYM_WHERE = " WHERE (    b.owner LIKE ? ESCAPE '" + escapeChar + "' " + "      or (s.owner LIKE ? ESCAPE '" + escapeChar + "' and s.org_object_owner is not null)" + "     ) " + " AND (    b.table_name LIKE ? ESCAPE '" + escapeChar + "' " + "      or s.synonym_name LIKE ? ESCAPE '" + escapeChar + "') " + " AND b.column_name LIKE ? ESCAPE '" + escapeChar + "' " + " and b.table_name = s.org_object_name (+) " + " and (" + "       (DECODE(s.owner, " + "               b.owner, 'true', " + "               'PUBLIC', 'true', " + "               NULL, 'true', " + "               'false') = 'true')" + "       or " + "       (b.owner LIKE ? and s.org_object_owner is not null) " + "       or " + "       (s.owner LIKE ? and s.org_object_owner is not null)" + "     )" + " ORDER BY table_schem, table_name, b.column_id ";
  
  private static String CLAUSE_COLUMN_TYPE_DECODE_1 = " DECODE (SUBSTRING(b.data_type, 0, DECODE(INSTR(b.data_type, '('), 0, LENGTH(b.data_type), INSTR(b.data_type, '(') - 1)),  'CHAR', 1, 'VARCHAR', 12, 'VARCHAR2', 12, 'NUMBER', 2,  'LONG', -1, 'DATE', ?, 'TIME', 92, 'TIMESTAMP', 93,  'BLOB', 2004, 'CLOB', 2005, 'RAW', -3, 'LONG RAW', -4,  'NCHAR', -15, 'NVARCHAR', -9, 'NCLOB', 2011,  'ROWID', -8, 'XMLTYPE', 2009, 'CURSOR', -17, 1111) ";
  
  private static String QUERY_BESTROWIDENTIFIER_1 = " SELECT 1 AS scope,  'ROWID' AS column_name,  1 AS data_type,  'ROWID' AS type_name,  0 AS column_size,  0 AS buffer_length,  0 AS decimal_digits,  2 AS pseudo_column  FROM DUAL  WHERE ? = 1  UNION  SELECT 2 AS scope,  t.column_name, ";
  
  private static String QUERY_BESTROWIDENTIFIER_2 = " DECODE (t.data_precision, 0, t.data_length, t.data_precision) AS column_size,  0 AS buffer_length, t.data_scale AS decimal_digits,  1 AS pseudo_column  FROM all_tab_columns t,  all_ind_columns i  WHERE ? = 1  AND t.table_name = ?  AND t.owner like ? escape '" + escapeChar + "/' " + " AND t.nullable != ? " + " AND t.owner = i.table_owner " + " AND t.table_name = i.table_name " + " AND t.column_name = i.column_name ";
  
  private static String QUERY_FUNCTION_COLUMNS_1 = " SELECT ARG.PACKAGE_NAME AS FUNCTION_CAT,  ARG.OWNER AS FUNCTION_SCHEM,  ARG.OBJECT_NAME AS FUNCTION_NAME,  ARG.ARGUMENT_NAME AS COLUMN_NAME,  DECODE(ARG.POSITION, 0, 5,  DECODE(ARG.IN_OUT, 'IN', 1,  'OUT', 4,  'IN/OUT', 2,   0)) AS COLUMN_TPYE,  DECODE(ARG.DATA_TYPE, 'CHAR', 1, 'VARCHAR', 12, 'VARCHAR2', 12, 'NUMBER', 2,  'LONG', -1,";
  
  private static String QUERY_FUNCTION_COLUMNS_2 = " 'TIME', 92,  'TIMESTAMP', 93,  'BLOB', 2004,  'CLOB', 2005,  'RAW', -3,  'LONG RAW', -4,  'INTERVAL YEAR TO MONTH', -103,  'INTERNAL DAY TO SECOND', -104,  'BINARY_FLOAT', 100,  'BINARY_DOUBLE', 101, 1111) AS DATA_TYPE,  DECODE(ARG.DATA_TYPE, 'OBJECT', ARG.TYPE_OWNER || '.' || ARG.TYPE_NAME,  ARG.DATA_TYPE) AS TYPE_NAME,  DECODE(ARG.DATA_PRECISION, NULL, ARG.DATA_LENGTH,  ARG.DATA_PRECISION) AS PRECISION,  ARG.DATA_LENGTH AS LENGTH,  ARG.DATA_SCALE AS SCALE,  10 AS RADIX,  1 AS NULLABLE,  NULL AS REMARKS,  NULL AS COLUMN_DEF,  NULL AS SQL_DATA_TYPE,  NULL AS SQL_DATETIME_SUB,  DECODE(ARG.DATA_TYPE,  'CHAR', 32767,  'VARCHAR2', 32767,  'LONG', 32767,  'RAW', 32767,  'LONG RAW', 32767,  NULL) AS CHAR_OCTET_LENGTH,  NULL AS ORDINAL_POSITION,  'YES' AS IS_NULLABLE,  NULL AS SPECIFIC_NAME  FROM ALL_ARGUMENTS ARG, ALL_PROCEDURE PROC  WHERE PROC.OBJECT_TYPE = 'FUNCTION'  AND PROC.OBJECT_ID = ARG.OBJECT_ID  AND ARG.OWNER LIKE ? ESCAPE '" + escapeChar + "' " + " AND ARG.OBJECT_NAME LIKE ? ESCAPE '" + escapeChar + "' ";
  
  private static String QUERY_VERSIONCOLUMNS_1 = " SELECT 0 AS scope,  t.column_name,  DECODE(c.data_type, 'CHAR', 1, 'VARCHAR', 12, 'VARCHAR2', 12, 'NUMBER', 2,  'LONG', -1,";
  
  private static String QUERY_VERSIONCOLUMNS_2 = " 'TIME', 92,  'TIMESTAMP', 93,  'BLOB', 2004,  'CLOB', 2005,  'RAW', -3,  'LONG RAW', -4,  'INTERVAL YEAR TO MONTH', -103,  'INTERNAL DAY TO SECOND', -104,  'BINARY_FLOAT', 100,  'BINARY_DOUBLE', 101, 1111) AS DATA_TYPE, ";
  
  private static String QUERY_VERSIONCOLUMNS_3 = " DECODE(c.data_precision,  null, c.data_length,  c.data_precision) AS column_size,  0 as buffer_length,  c.data_scale as decimal_digits,  0 as pseudo_column  FROM all_trigger_cols t, all_tab_columns c  WHERE t.table_name = ?  AND c.owner like ? escape '" + escapeChar + "' " + " AND t.table_owner = c.owner " + " AND t.table_name = c.table_name " + " AND t.column_name = c.column_name ";
  
  public static final String QUERY_STATIC_VIEW_CHECK = " SELECT OBJECT_NAME FROM ALL_OBJECTS  WHERE OWNER = 'SYSCAT' AND OBJECT_TYPE IN ('TABLE', 'VIEW') AND OBJECT_NAME = ?";
  
  public static final String QUERY_STATIC_VIEW_COLUMN_CHECK = " SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS  WHERE OWNER = 'SYSCAT' AND TABLE_NAME = ? AND COLUMN_NAME = ?";
  
  public static String getAttributesQuery(boolean paramBoolean1, boolean paramBoolean2) {
    StringBuffer stringBuffer = new StringBuffer(256);
    stringBuffer.append(QUERY_ATTRIBUTES__SELECT_CLAUSE);
    if (paramBoolean2)
      stringBuffer.append(QUERY_ATTRIBUTES__OID_COLUMN); 
    if (paramBoolean1) {
      stringBuffer.append(QUERY_ATTRIBUTES__BY_OID);
    } else {
      stringBuffer.append(QUERY_ATTRIBUTES__BY_TYPENAME);
    } 
    return stringBuffer.toString();
  }
  
  public static String getBestRowIdentifierQuery(boolean paramBoolean) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(QUERY_BESTROWIDENTIFIER_1);
    stringBuffer.append(CLAUSE_COLUMN_TYPE_DECODE_1).append(" AS data_type, ");
    if (paramBoolean)
      stringBuffer.append(" NVL2(t.data_type_owner, t.data_type_owner || '.', '') ||"); 
    stringBuffer.append(" t.data_type AS type_name, ");
    stringBuffer.append(QUERY_BESTROWIDENTIFIER_2);
    return stringBuffer.toString();
  }
  
  public static String getCollectionTypeQuery(boolean paramBoolean) {
    StringBuffer stringBuffer = new StringBuffer(128);
    stringBuffer.append(QUERY_COLLECTION_TYPE__SELECT_CLAUSE);
    if (paramBoolean) {
      stringBuffer.append(QUERY_COLLECTION_TYPE__BY_OID);
    } else {
      stringBuffer.append(QUERY_COLLECTION_TYPE__BY_TYPENAME);
    } 
    return stringBuffer.toString();
  }
  
  public static String getFunctionColumnsQuery(boolean paramBoolean) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(QUERY_FUNCTION_COLUMNS_1);
    stringBuffer.append("              'DATE', )").append(paramBoolean ? 93 : 91).append(", ");
    stringBuffer.append(QUERY_FUNCTION_COLUMNS_2);
    return stringBuffer.toString();
  }
  
  public static String getProcedureColumnsQuery(boolean paramBoolean) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(QUERY_PROCEDURE_COLUMNS_1);
    stringBuffer.append("'DATE', ").append(paramBoolean ? 93 : 91).append(", ");
    stringBuffer.append(QUERY_PROCEDURE_COLUMNS_2);
    return stringBuffer.toString();
  }
  
  public static String getTableColumnsQuery(boolean paramBoolean1, boolean paramBoolean2) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(" SELECT NULL AS table_cat, ");
    if (paramBoolean1) {
      stringBuffer.append(" NVL(s.owner, b.owner) AS table_schem, ");
      stringBuffer.append(" NVL(s.synonym_name, b.table_name) AS table_name, ");
    } else {
      stringBuffer.append(" b.owner AS table_schem, ");
      stringBuffer.append(" b.table_name, ");
    } 
    stringBuffer.append(" b.column_name, ");
    if (paramBoolean2) {
      stringBuffer.append(" DECODE(st.typecode, 'OBJECT', ").append(2002).append(", 'COLLECTION', ").append(2003).append(", ").append(CLAUSE_COLUMN_TYPE_DECODE_1).append(")");
    } else {
      stringBuffer.append(CLAUSE_COLUMN_TYPE_DECODE_1);
    } 
    stringBuffer.append(" AS data_type, ");
    if (paramBoolean2)
      stringBuffer.append(" NVL2(b.data_type_owner, b.data_type_owner || '.', '') ||"); 
    stringBuffer.append(" b.data_type AS type_name, ");
    stringBuffer.append(QUERY_COLUMNS_1);
    if (paramBoolean2)
      stringBuffer.append(QUERY_COLUMNS_2); 
    if (paramBoolean1) {
      stringBuffer.append(" , all_synonyms s");
      stringBuffer.append(QUERY_COLUMNS_INCLUDE_SYNONYM_WHERE);
    } else {
      stringBuffer.append(QUERY_COLUMNS_WHERE);
    } 
    return stringBuffer.toString();
  }
  
  public static String getVersionColumnsQuery(boolean paramBoolean1, boolean paramBoolean2) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(QUERY_VERSIONCOLUMNS_1);
    stringBuffer.append(" 'DATE',").append(paramBoolean1 ? 93 : 91).append(", ");
    stringBuffer.append(QUERY_VERSIONCOLUMNS_2);
    if (paramBoolean2)
      stringBuffer.append(" NVL2(c.data_type_owner, c.data_type_owner || '.', '') ||"); 
    stringBuffer.append(" c.data_type AS type_name, ");
    stringBuffer.append(QUERY_VERSIONCOLUMNS_3);
    return stringBuffer.toString();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbDatabaseMetaQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
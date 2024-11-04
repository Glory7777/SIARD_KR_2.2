package com.tmax.tibero.jdbc.err;

import com.tmax.tibero.jdbc.util.TbResourceBundle;
import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLNonTransientException;
import java.sql.SQLRecoverableException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTimeoutException;
import java.sql.SQLTransactionRollbackException;
import java.sql.SQLTransientConnectionException;
import java.sql.SQLTransientException;
import java.sql.SQLWarning;
import java.util.MissingResourceException;

public class TbError {
  private static final String JDBC_ERROR_FILE = "com.tmax.tibero.jdbc.err.Message_gen";
  
  private static final String SQL_STATE_FILE = "com.tmax.tibero.jdbc.err.SqlState_gen";
  
  private static TbResourceBundle _errorMsgBundle = new TbResourceBundle(getBundleFileName());
  
  private static TbResourceBundle _sqlStateBundle = new TbResourceBundle(getSqlStateFileName());
  
  private static final int ERROR_INTERNAL = 500000;
  
  public static final String SQL_STATE_SUCCESS = "00";
  
  public static final String SQL_STATE_WARNING = "01";
  
  public static final String SQL_STATE_NO_DATA = "02";
  
  public static final String SQL_STATE_ERROR_INCOMPLE = "03";
  
  public static final String SQL_STATE_ERROR_DYNAMIC = "07";
  
  public static final String SQL_STATE_ERROR_CONNECTION = "08";
  
  public static final String SQL_STATE_ERROR_FEATURE_NOT_SUPPORT = "0A";
  
  public static final String SQL_STATE_ERROR_INVALID_TRANSACTION_INIT = "0B";
  
  public static final String SQL_STATE_ERROR_INVALID_TARGET_TYPE = "0D";
  
  public static final String SQL_STATE_ERROR_IVALID_SCHEMA_NAME = "0E";
  
  public static final String SQL_STATE_ERROR_LOCATOR = "0F";
  
  public static final String SQL_STATE_ERROR_REFERENCE_TO_NULL_TABLE_VALUE = "0G";
  
  public static final String SQL_STATE_ERROR_INVALID_SQL_STATE = "0H";
  
  public static final String SQL_STATE_ERROR_RESIGNAL = "0K";
  
  public static final String SQL_STATE_ERROR_INVALID_GRANTOR = "0L";
  
  public static final String SQL_STATE_ERROR_TYPE_MISMATCH = "0N";
  
  public static final String SQL_STATE_ERROR_INVALID_ROLE = "0P";
  
  public static final String SQL_STATE_ERROR_RESULTSET_NOT_CREATED = "0Q";
  
  public static final String SQL_STATE_ERROR_CURSOR_ALLOCATED = "0R";
  
  public static final String SQL_STATE_ERROR_CASE_NOT_FOUND = "20";
  
  public static final String SQL_STATE_ERROR_CARDINALITY_VIOLATION = "21";
  
  public static final String SQL_STATE_ERROR_DATA = "22";
  
  public static final String SQL_STATE_ERROR_INTEGRITY_CONSTRAINT_VIOLATION = "23";
  
  public static final String SQL_STATE_ERROR_INVALID_CURSOR_STATE = "24";
  
  public static final String SQL_STATE_ERROR_INVALID_TRANSACTION_STATE = "25";
  
  public static final String SQL_STATE_ERROR_INVALID_SQL_STATEMENT_NAME = "26";
  
  public static final String SQL_STATE_ERROR_TRIGGERED_DATA_CHANGE_VIOLATION = "27";
  
  public static final String SQL_STATE_ERROR_INVALID_AUTH = "28";
  
  public static final String SQL_STATE_ERROR_DIRECT_SQL_SYNTAX = "2A";
  
  public static final String SQL_STATE_ERROR_DEPENDENT_PRIVILEGE = "2B";
  
  public static final String SQL_STATE_ERROR_INVALID_CHARSET = "2C";
  
  public static final String SQL_STATE_ERROR_INVALID_TRANSACTION_TERM = "2D";
  
  public static final String SQL_STATE_ERROR_INVALID_CONNECTION_NAME = "2E";
  
  public static final String SQL_STATE_ERROR_SQL_ROUTINE = "2F";
  
  public static final String SQL_STATE_ERROR_INVALID_SQL_STATEMENT = "30";
  
  public static final String SQL_STATE_ERROR_INVALID_TARGET_VALUE = "31";
  
  public static final String SQL_STATE_ERROR_INVALID_SQL_DESCRIPTOR_NAME = "33";
  
  public static final String SQL_STATE_ERROR_INVALID_CURSOR_NAME = "34";
  
  public static final String SQL_STATE_ERROR_INVALID_CONDITION_NUMBER = "35";
  
  public static final String SQL_STATE_ERROR_CURSOR_SENSITIVITY = "36";
  
  public static final String SQL_STATE_ERROR_DYNAMIC_SQL_SYNTAX = "37";
  
  public static final String SQL_STATE_ERROR_EXTERNAL_ROUTINE = "38";
  
  public static final String SQL_STATE_ERROR_EXTERNAL_ROUTINE_INVOCATION = "39";
  
  public static final String SQL_STATE_ERROR_SAVEPOINT = "3B";
  
  public static final String SQL_STATE_ERROR_AMBIGUOUS_CURSOR_NAME = "3C";
  
  public static final String SQL_STATE_ERROR_INVALID_CATALOG_NAME = "3D";
  
  public static final String SQL_STATE_ERROR_INVALID_SCHEMA_NAME = "3F";
  
  public static final String SQL_STATE_ERROR_INVALID_UDT_INSTANCE = "3G";
  
  public static final String SQL_STATE_ERROR_TRANSACTION_ROLLBACK = "40";
  
  public static final String SQL_STATE_ERROR_SYNTAX_OR_ACCESS_RULE = "42";
  
  public static final String SQL_STATE_ERROR_CHECK_OPTION_VIOLATION = "44";
  
  public static final String SQL_STATE_ERROR_UNHANDLED_USER_DEFINED = "45";
  
  public static final String SQL_STATE_ERROR_OPERATION_ABORT = "70";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P1 = "H1";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P2 = "H2";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P3 = "H3";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P4 = "H4";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P5 = "H5";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P6 = "H6";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P7 = "H7";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P8 = "H8";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P9 = "H9";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P10 = "HA";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P11 = "HB";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P12 = "HC";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P13 = "HD";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P14 = "HE";
  
  public static final String SQL_STATE_ERROR_SQL_MULTIMEDIA_P15 = "HF";
  
  public static final String SQL_STATE_ERROR_CLI_SPECIFIC_CONDITION = "HY";
  
  public static final String SQL_STATE_ERROR_REMOTE_DATABASE = "HZ";
  
  public static final String SQL_STATE_ERROR_ODBC = "IM";
  
  public static final int ERROR_JDBC_BASE = -90000;
  
  public static final int RUNTIME_ERROR = -90100;
  
  public static final int RESOURCE_LOAD_FAIL = -90200;
  
  public static final int UNSUPPORTED_OPERATION = -90201;
  
  public static final int READER_READ_ERROR = -90202;
  
  public static final int UNSUPPORTED_SVR_VERSION = -90203;
  
  public static final int COMM_ERROR = -90400;
  
  public static final int COMM_REFUSED = -90401;
  
  public static final int COMM_PROTOCOL_ERROR = -90402;
  
  public static final int COMM_CONNECTION_BROKEN = -90403;
  
  public static final int COMM_INVALID_INTERNAL_STATE = -90404;
  
  public static final int COMM_IO_ERROR_READ = -90405;
  
  public static final int COMM_IO_ERROR_WRITE = -90406;
  
  public static final int COMM_IO_ERROR_FLUSH = -90407;
  
  public static final int COMM_IO_ERROR_ENCODING = -90408;
  
  public static final int COMM_IO_ERROR_CLOSE = -90409;
  
  public static final int COMM_IO_CORRUPTED_MSG = -90410;
  
  public static final int COMM_FAIL_OVER_RECONNECTED = -90411;
  
  public static final int SVR_BACKEND_ERROR = -90500;
  
  public static final int SVR_STARTUP_FAIL = -90501;
  
  public static final int SVR_AUTHENTICATION_FAIL = -90502;
  
  public static final int SVR_CLOSE_SESSION_FAIL = -90503;
  
  public static final int SVR_OPEN_SESSION_FAIL = -90504;
  
  public static final int SVR_CLOSE_CONNECTION_FAIL = -90505;
  
  public static final int SVR_PREPARE_FAIL = -90506;
  
  public static final int SVR_CLOSE_CURSOR_FAIL = -90507;
  
  public static final int SVR_EXECUTE_FAIL = -90508;
  
  public static final int SVR_FETCH_FAIL = -90509;
  
  public static final int SVR_COMMIT_FAIL = -90510;
  
  public static final int SVR_ROLLBACK_FAIL = -90511;
  
  public static final int SVR_AUTOCOMMIT_FAIL = -90512;
  
  public static final int SVR_SET_ISOLATIONLEVEL_FAIL = -90513;
  
  public static final int SVR_SAVEPOINT_FAIL = -90514;
  
  public static final int SVR_BATCHUPDATE_FAIL = -90515;
  
  public static final int SVR_LOB_READ_FAIL = -90516;
  
  public static final int SVR_LOB_WRITE_FAIL = -90517;
  
  public static final int SVR_LOB_INSTR_FAIL = -90518;
  
  public static final int SVR_LOB_INLOB_FAIL = -90519;
  
  public static final int SVR_LOB_TRUNC_FAIL = -90520;
  
  public static final int SVR_LOB_OPEN_FAIL = -90521;
  
  public static final int SVR_LOB_CLOSE_FAIL = -90522;
  
  public static final int SVR_LOB_GET_LENGTH_FAIL = -90523;
  
  public static final int SVR_LOB_CREATE_TEMP_FAIL = -90524;
  
  public static final int SVR_LOB_DELETE_TEMP_FAIL = -90525;
  
  public static final int SVR_CANCEL_FAIL = -90526;
  
  public static final int SVR_XA_START_ERROR = -90527;
  
  public static final int SVR_XA_END_ERROR = -90528;
  
  public static final int SVR_XA_PREPARE_ERROR = -90529;
  
  public static final int SVR_XA_COMMIT_ERROR = -90530;
  
  public static final int SVR_XA_ROLLBACK_ERROR = -90531;
  
  public static final int SVR_XA_RECOVER_ERROR = -90532;
  
  public static final int SVR_PUTDATA_FAIL = -90533;
  
  public static final int SVR_LONG_READ_FAIL = -90534;
  
  public static final int SVR_GET_LAST_SQL_INFO = -90535;
  
  public static final int SVR_DPL_PREPARE = -90536;
  
  public static final int SVR_DPL_LOADSTREAM = -90537;
  
  public static final int SVR_DPL_DATASAVE = -90538;
  
  public static final int SVR_DPL_FINISH = -90539;
  
  public static final int SVR_DPL_FLUSH_ROW = -90540;
  
  public static final int SVR_DPL_ABORT = -90541;
  
  public static final int SVR_XA_FORGET_ERROR = -90542;
  
  public static final int SVR_RESET_SESS_FAIL = -90543;
  
  public static final int SVR_DPL_COLUMN_CNT_MISMATCH = -90544;
  
  public static final int SVR_SET_CLIENT_INFO_FAIL = -90545;
  
  public static final int SVR_GET_COLUMN_DESC_FAIL = -90546;
  
  public static final int SVR_PASSWORD_GRACE_EXPIRED = -90547;
  
  public static final int SVR_PASSWORD_GRACE_EXPIRED2 = -90548;
  
  public static final int MU_JDBC_SPEC = -90600;
  
  public static final int MU_ACTION_ON_AUTOCOMMIT = -90601;
  
  public static final int MU_ACTION_ON_XA = -90602;
  
  public static final int MU_ACTION_ON_CLOSED_CONNECTION = -90603;
  
  public static final int MU_INVALID_SYNTAX = -90604;
  
  public static final int MU_INVALID_URL = -90605;
  
  public static final int MU_NOT_EXECUTED_BATCH = -90606;
  
  public static final int MU_NO_RESULT_SET_RETURNED = -90607;
  
  public static final int MU_INVALID_PARAMETER = -90608;
  
  public static final int MU_INVALID_COLUMN_INDEX = -90609;
  
  public static final int MU_BAD_DATA_FORMAT = -90610;
  
  public static final int MU_COLUMN_NOT_EXIST = -90611;
  
  public static final int MU_COVERSION_RULE_VIOLATION = -90612;
  
  public static final int MU_OVER_MAX_ROW_CNT = -90613;
  
  public static final int MU_INVALID_SQL_SYNTAX = -90614;
  
  public static final int MU_CALL_ON_STATEMENT = -90615;
  
  public static final int MU_READ_FROM_INPUT_FAILED = -90616;
  
  public static final int MU_NO_OUT_PARAMETER = -90617;
  
  public static final int MU_OUT_PARAMETER_MISMATCH = -90618;
  
  public static final int MU_NO_BIND_DATA = -90619;
  
  public static final int MU_ACTION_ON_NOT_SCROLLABLE_RSET = -90620;
  
  public static final int MU_ACTION_ON_NOT_UPDATABLE_RSET = -90621;
  
  public static final int MU_ACTION_ON_NOT_FETCHED_RSET = -90622;
  
  public static final int MU_CANT_HANDLE_SQL_FOR_REQUESTED_RSET_TYPE = -90623;
  
  public static final int MU_INVALID_CURSOR_POSITION = -90624;
  
  public static final int MU_REQUESTED_RSET_TYPE_UNAVAILABLE = -90625;
  
  public static final int MU_FAILED_REFETCH = -90626;
  
  public static final int MU_ALL_PARAM_NOT_BOUND = -90627;
  
  public static final int MU_INVALID_STR_LENGTH = -90628;
  
  public static final int MU_ACTION_ON_EMPTY_LOB = -90629;
  
  public static final int MU_SELECT_BATCHUPDATE = -90630;
  
  public static final int MU_OUTPARAM_BATCHUPDATE = -90631;
  
  public static final int MU_ESCAPE_SYNTAX = -90632;
  
  public static final int MU_GET_SAVEPOINT_NAME = -90633;
  
  public static final int MU_GET_SAVEPOINT_ID = -90634;
  
  public static final int MU_CURSOR_NEXT_NOT_INVOKED = -90635;
  
  public static final int MU_BIGDATA_IN_BATCHUPDATE = -90636;
  
  public static final int MU_DPL_NO_SCHEMA = -90637;
  
  public static final int MU_DPL_NO_TABLE = -90638;
  
  public static final int MU_DPL_NO_COLUMN_COUNT = -90639;
  
  public static final int MU_DPL_NO_COLUMN_NAME = -90640;
  
  public static final int MU_DPL_NO_LOG_FLAG = -90641;
  
  public static final int MU_STRING_TO_ROWID = -90642;
  
  public static final int MU_CONNECTION_CLOSED = -90643;
  
  public static final int MU_NO_META_DATA = -90644;
  
  public static final int MU_CONN_RECOVERY_ALREADY_ENABLED = -90645;
  
  public static final int MU_RSET_ALREADY_CLOSED = -90646;
  
  public static final int MU_RSET_EXHAUSTED = -90647;
  
  public static final int MU_INVALID_SESS_ATTR_ARRAY = -90648;
  
  public static final int MU_INVALID_NAME_PATTERN = -90649;
  
  public static final int MU_DATA_NOT_FIT = -90650;
  
  public static final int MU_FAILED_TO_CONVERT = -90651;
  
  public static final int MU_NOT_ALLOWED_LITERAL_IN_BATCH = -90652;
  
  public static final int MU_NUMBER_OVERFLOW = -90653;
  
  public static final int MU_NUMBER_UNDERFLOW = -90654;
  
  public static final int MU_NOT_ALLOWED_MIXED_PARAM = -90655;
  
  public static final int MU_EXCEED_MAX_LENGTH_LIMIT = -90656;
  
  public static final int MU_FAILED_TO_CAST = -90657;
  
  public static final int MU_STATEMENT_CLOSED = -90658;
  
  public static final int MU_ACTION_ON_CLOSED_STATEMENT = -90659;
  
  public static final int MU_UNSUPPORTED_RSET_HOLDABILITY_TYPE = -90660;
  
  public static final int MU_DPL_NO_PARTITION = -90661;
  
  public static final int MU_UNSUPPORTED_OPERATION_PARALLEL_DPL = -90662;
  
  public static final int MU_EXCEEDS_DATA_SIZE = -90663;
  
  public static final int MU_UDT_META_DATA_LOAD_FAIL = -90664;
  
  public static final int MU_UDT_NOT_EXIST = -90665;
  
  public static final int MU_ANOTHER_KIND_UDT_EXIST = -90666;
  
  public static final int FO_RECONNECTED = -90700;
  
  public static final int FO_SELECT_FAIL = -90701;
  
  public static final int FO_UNABLE_TO_CONTINUE_FETCHES = -90702;
  
  public static final int INTERNAL_ERROR = -590700;
  
  public static final int INTERNAL_INVALID_INDEX = -590701;
  
  public static final int INTERNAL_UNEXPECTED_INPUT = -590702;
  
  public static final int INTERNAL_UNSUPPORTED_DATA_TYPE = -590703;
  
  public static final int INTERNAL_UNSUPPORTED_SQL_TYPE = -590704;
  
  public static final int INTERNAL_DATA_CONVERSION_FAIL = -590705;
  
  public static final int INTERNAL_EXECUTE_REPREPARE = -590706;
  
  public static final int INTERNAL_READ_STREAM_FAILED = -590707;
  
  public static final int INTERNAL_RESOURCE_RELEASE_FAIL = -590708;
  
  public static final int INTERNAL_NULL_PHYSICAL_CONNECTION = -590709;
  
  public static final int INTERNAL_UNSUPPORTED_NUMBER_RANGE = -590710;
  
  public static final int INTERNAL_ROWDATA_COPY_FAIL = -590711;
  
  public static final int INTERNAL_INVALID_HEADER_FIELD = -590712;
  
  public static final int INTERNAL_INVALID_ARGUMENT = -590713;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION = -590714;
  
  public static final int INTERNAL_INVALID_LOB_LOCATOR = -590715;
  
  public static final int INTERNAL_INVALID_COLUMN_COUNT = -590716;
  
  public static final int INTERNAL_INVALID_DEFERRED_PARAM = -590717;
  
  public static final int INTERNAL_UNSUPPORTED_CHARSET = -590718;
  
  public static final int INTERNAL_REQUESTED_DISCONNECT = -590719;
  
  public static final int INTERNAL_COMM_ERROR_INTERNAL_SOCKET = -590720;
  
  public static final int INTERNAL_COMM_ERROR_NODES_EMPTY = -590721;
  
  public static final int INTERNAL_UNSUPPORTED_OPERATION_TX_ISOLATION_LVL = -590722;
  
  public static final int INTERNAL_UNSUPPORTED_OPERATION_INVALID_DRIVER = -590723;
  
  public static final int INTERNAL_UNSUPPORTED_OPERATION_RESULT_SET_TYPE = -590724;
  
  public static final int INTERNAL_UNSUPPORTED_OPERATION_RESULT_SET_UPDATABLE_UPDATE_ARRAY = -590725;
  
  public static final int INTERNAL_UNSUPPORTED_OPERATION_RESULT_SET_UPDATABLE_GET_ARRAY = -590726;
  
  public static final int INTERNAL_COMM_PROTOCOL_ERROR_INVALID_MSG_TYPE = -590727;
  
  public static final int INTERNAL_COMM_PROTOCOL_ERROR_INVALID_PROTOCOL = -590728;
  
  public static final int INTERNAL_COMM_IO_ERROR_READ_PACKET = -590729;
  
  public static final int INTERNAL_COMM_IO_ERROR_READ_STREAM_BUFFER = -590730;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_COUNT = -590731;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_COLUMN_INDEX = -590732;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_AUTO_GENERATED_KEY = -590733;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_COLUMN_NAME = -590734;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_FETCH_SIZE = -590735;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_ROW_INDEX = -590736;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_EMPTY_SQL = -590737;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_FETCH_DIRECTION = -590738;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_MAX_COLUMN_SIZE = -590739;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_MAX_ROW_SIZE = -590740;
  
  public static final int INTERNAL_MU_INVALID_PARAMETER_TIMEOUT = -590741;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_UNKNOWN_CHAR = -590742;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_INVALID_INPUT = -590743;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_CONVERSION_OVERFLOW = -590744;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_MISSING_BYTEORDER_MARK = -590745;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_INCORRECT_BYTEORDER_MARK = -590746;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_REVERSED_BYTEORDER_MARK = -590747;
  
  public static final int INTERNAL_FAIL_CHARSET_CONVERSION_INVALID_UCS2_ARRAY = -590748;
  
  public static final int INTERNAL_MU_BAD_DATA_FORMAT_CORRUPTED = -590749;
  
  public static final int INTERNAL_DATA_CONVERSION_FAIL_TYPE_DATE = -590750;
  
  public static final int INTERNAL_DATA_CONVERSION_FAIL_TYPE_TIMESTAMP = -590751;
  
  public static final int INTERNAL_DATA_CONVERSION_FAIL_TYPE_TIME = -590752;
  
  public static final int INTERNAL_MU_STRING_TO_ROWID_INVALID_ROWID = -590753;
  
  public static final int INTERNAL_MU_STRING_TO_ROWID_INVALID_BLOCK_NUM = -590754;
  
  public static final int INTERNAL_MU_STRING_TO_ROWID_INVALID_FILE_NUM = -590755;
  
  public static final int INTERNAL_MU_STRING_TO_ROWID_INVALID_SEGMENT_NUM = -590756;
  
  public static final int INTERNAL_MU_STRING_TO_ROWID_INVALID_ROW_NUM = -590757;
  
  public static final int INTERNAL_ROWSET_INVALID_RSET_CACHED_RESULT_SET_TYPE = -590758;
  
  public static final int INTERNAL_ROWSET_INVALID_ROWSET_CACHED_ROW_SET_TYPE = -590759;
  
  public static final int INTERNAL_ROWSET_INVALID_ROWSET_CACHED_ROW_SET_TYPE_WITH_FETCH_DIRECTION = -590760;
  
  public static final int INTERNAL_ROWSET_INVALID_ROWSET_FILTERED_ROW_SET_TYPE = -590761;
  
  public static final int INTERNAL_ROWSET_INVALID_MATCH_INDEX_ARG_NULL = -590762;
  
  public static final int INTERNAL_ROWSET_INVALID_MATCH_NAME_ARG_NULL = -590763;
  
  public static final int INTERNAL_INVALID_ARGUMENT_POSITION = -590764;
  
  public static final int INTERNAL_INVALID_ARGUMENT_LENGTH = -590765;
  
  public static final int INTERNAL_INVALID_ARGUMENT_START_POSITION = -590766;
  
  public static final int INTERNAL_INVALID_ARGUMENT_LOB = -590767;
  
  public static final int INTERNAL_INVALID_ARGUMENT_STRING = -590768;
  
  public static final int INTERNAL_INVALID_ARGUMENT_LOB_MODE = -590769;
  
  public static final int INTERNAL_INVALID_ARGUMENT_INVALID_LOB = -590770;
  
  public static final int INTERNAL_INVALID_ARGUMENT_INVALID_ROWID = -590771;
  
  public static final int INTERNAL_MU_NO_OUT_PARAMETER_CALLABLE_STMT_IN_PARAMETER = -590772;
  
  public static final int INTERNAL_MU_INVALID_CURSOR_POSITION_CALL_DELETE_ROW = -590773;
  
  public static final int INTERNAL_MU_INVALID_CURSOR_POSITION_CALL_INSERT_ROW = -590774;
  
  public static final int INTERNAL_MU_INVALID_CURSOR_POSITION_CALL_REFRESH_ROW = -590775;
  
  public static final int INTERNAL_MU_INVALID_CURSOR_POSITION_CALL_UPDATE_ROW = -590776;
  
  public static final int INTERNAL_SVR_BACKEND_ERROR_RESULT_SET_NO_RECORD_DELETE = -590777;
  
  public static final int INTERNAL_SVR_BACKEND_ERROR_RESULT_SET_RECORD_DELETED = -590778;
  
  public static final int INTERNAL_SVR_BACKEND_ERROR_RESULT_SET_NO_RECORD_INSERT = -590779;
  
  public static final int INTERNAL_SVR_BACKEND_ERROR_RESULT_SET_RECORD_INSERTED = -590780;
  
  public static final int INTERNAL_SVR_BACKEND_ERROR_RESULT_SET_NO_RECORD_UPDATE = -590781;
  
  public static final int INTERNAL_SVR_BACKEND_ERROR_RESULT_SET_RECORD_UPDATED = -590782;
  
  public static final int INTERNAL_MU_RSET_EXHAUSTED_INVALID_PARAMETER_METADATA = -590783;
  
  public static final int INTERNAL_INVALID_OBJECT_RESULT_SET_METADATA = -590784;
  
  public static final int INTERNAL_INVALID_ARGUMENT_LOB_READ_OFFSET = -590785;
  
  public static final int INTERNAL_FO_RSET_LOST_ORIGINAL_CONNECTION = -590786;
  
  public static final int JNI_ERROR = -590800;
  
  public static final int JNI_INVALID_STR_LENGTH = -590801;
  
  public static final int JNI_MALLOC_ERROR = -590802;
  
  public static final int JNI_CLASS_NOT_FOUND = -590803;
  
  public static final int JNI_METHOD_NOT_FOUND = -590804;
  
  public static final int JNI_FIELD_NOT_FOUND = -590805;
  
  public static final int JNI_ARRAY_INDEX_OUT_OF_BOUND = -590806;
  
  public static final int JNI_INVALID_ENV = -590807;
  
  public static final int ROWSET_CANNOT_CREATE_PROVIDER = -90820;
  
  public static final int ROWSET_CANNOT_FIND_TABLE = -90821;
  
  public static final int ROWSET_CANNOT_INSERT = -90822;
  
  public static final int ROWSET_CANNOT_POPULATE_NULL_RSET = -90823;
  
  public static final int ROWSET_CANNOT_UPDATE = -90824;
  
  public static final int ROWSET_CANNOT_WRITE = -90825;
  
  public static final int ROWSET_COMMAND_IS_NULL = -90826;
  
  public static final int ROWSET_CONNECTION_CLOSED = -90827;
  
  public static final int ROWSET_FAILED_CONNECT = -90828;
  
  public static final int ROWSET_FAILED_CONVERSION = -90829;
  
  public static final int ROWSET_FAILED_COPY_OBJ = -90830;
  
  public static final int ROWSET_FAILED_MAPPING = -90831;
  
  public static final int ROWSET_FAILED_SET_MATCH_INDEX = -90832;
  
  public static final int ROWSET_FAILED_SET_MATCH_NAME = -90833;
  
  public static final int ROWSET_INVALID_COLUMN_INDEX = -90834;
  
  public static final int ROWSET_INVALID_COLUMN_NAME = -90835;
  
  public static final int ROWSET_INVALID_COLUMN_TYPE = -90836;
  
  public static final int ROWSET_INVALID_CURSOR = -90837;
  
  public static final int ROWSET_INVALID_FETCH_DIRECTION = -90838;
  
  public static final int ROWSET_INVALID_KEY_COLUMNS = -90839;
  
  public static final int ROWSET_INVALID_MATCH_INDEX = -90840;
  
  public static final int ROWSET_INVALID_MATCH_NAME = -90841;
  
  public static final int ROWSET_INVALID_OBJ_CLASS = -90842;
  
  public static final int ROWSET_INVALID_PAGE_SIZE = -90843;
  
  public static final int ROWSET_INVALID_PARAM_INDEX = -90844;
  
  public static final int ROWSET_INVALID_PARAM_NAME = -90845;
  
  public static final int ROWSET_INVALID_PARAM_TYPE = -90846;
  
  public static final int ROWSET_INVALID_ROW_INDEX = -90847;
  
  public static final int ROWSET_INVALID_ROWNUM = -90848;
  
  public static final int ROWSET_INVALID_ROWSET = -90849;
  
  public static final int ROWSET_INVALID_RSET = -90850;
  
  public static final int ROWSET_IS_INSERTING = -90851;
  
  public static final int ROWSET_MISSING_CONN_INFO = -90852;
  
  public static final int ROWSET_MISSING_PREV_PAGING = -90853;
  
  public static final int ROWSET_MISSING_USER_INFO = -90854;
  
  public static final int ROWSET_NOT_ALLOWED_UPDATE = -90855;
  
  public static final int ROWSET_NOT_ALLOWED_VISIBLE = -90856;
  
  public static final int ROWSET_NOT_CHANGED_ROWS = -90857;
  
  public static final int ROWSET_NOT_DELETED = -90858;
  
  public static final int ROWSET_NOT_EXIST_RSET = -90859;
  
  public static final int ROWSET_NOT_EXIST_MATCHED_INDEX = -90860;
  
  public static final int ROWSET_NOT_EXIST_MATCHED_NAME = -90861;
  
  public static final int ROWSET_NOT_INSERTED = -90862;
  
  public static final int ROWSET_NOT_UPDATED = -90863;
  
  public static final int ROWSET_NOT_SATISFIED_FILTER = -90864;
  
  public static final int ROWSET_NOT_SET_ALL_COLUMN = -90865;
  
  public static final int ROWSET_NOT_SET_COLUMN_INDEX = -90866;
  
  public static final int ROWSET_NOT_SET_COLUMN_NAME = -90867;
  
  public static final int ROWSET_TOO_FEW_ROWS = -90868;
  
  public static final int LOB_IS_CLOSED = -90900;
  
  public static final int LOB_READ_ERROR = -90901;
  
  public static final int LOB_CANT_READ_REMOTE = -90902;
  
  public static final int XML_ALREADY_BEEN_FREED = -90920;
  
  public static final int XML_WRITE_ONLY = -90921;
  
  public static final int XML_ALREADY_BEEN_READ = -90922;
  
  public static final int XML_READ_ONLY = -90923;
  
  public static final int XML_ALREADY_BEEN_SET = -90924;
  
  public static final int XML_FAILED_TO_FREE = -90925;
  
  public static final int XML_FAILED_TO_INIT = -90926;
  
  public static final int XML_FAILED_TO_READ = -90927;
  
  public static final int XML_UNSUPPORTED_CLASS = -90928;
  
  public static final int XML_HAS_NO_DATA = -90929;
  
  public static final int XML_CANT_SET_NULL = -90930;
  
  public static String getBundleFileName() {
    return "com.tmax.tibero.jdbc.err.Message_gen";
  }
  
  public static String getSqlStateFileName() {
    return "com.tmax.tibero.jdbc.err.SqlState_gen";
  }
  
  public static final String getMsg(int paramInt) {
    try {
      return TbResourceBundle.getKey(paramInt) + ":" + _errorMsgBundle.getValue(paramInt);
    } catch (MissingResourceException missingResourceException) {
      return "Message key[" + TbResourceBundle.getKey(paramInt) + "] in Message files or Message file[" + getBundleFileName() + "] is not found.";
    } 
  }
  
  public static final String getMsg(int paramInt, Object[] paramArrayOfObject) {
    try {
      return (paramArrayOfObject == null || paramArrayOfObject.length == 0) ? getMsg(paramInt) : (TbResourceBundle.getKey(paramInt) + ":" + String.format(_errorMsgBundle.getValue(paramInt), paramArrayOfObject));
    } catch (MissingResourceException missingResourceException) {
      return "Message key[" + TbResourceBundle.getKey(paramInt) + "] in Message files or Message file[" + getBundleFileName() + "] is not found.";
    } 
  }
  
  public static final String getSQLState(int paramInt) {
    try {
      return TbResourceBundle.getKey(paramInt) + ":" + _sqlStateBundle.getValue(paramInt);
    } catch (MissingResourceException missingResourceException) {
      return "Message key[" + TbResourceBundle.getKey(paramInt) + "] in Message files or Message file[" + getBundleFileName() + "] is not found.";
    } 
  }
  
  public static String trimVendorHeader(String paramString) {
    if (paramString == null)
      return "null"; 
    int i = TbResourceBundle.getKey(0).length() + 1;
    return paramString.substring(i);
  }
  
  private static SQLException makeSQLException(String paramString1, String paramString2, int paramInt) {
    switch (getSqlStateFromErrorCode(paramInt)) {
      case 0:
        return new SQLException(paramString1, paramString2, paramInt);
      case 1:
        return new SQLNonTransientException(paramString1, paramString2, paramInt);
      case 2:
        return new SQLTransientException(paramString1, paramString2, paramInt);
      case 3:
        return new SQLDataException(paramString1, paramString2, paramInt);
      case 5:
        return new SQLIntegrityConstraintViolationException(paramString1, paramString2, paramInt);
      case 6:
        return new SQLInvalidAuthorizationSpecException(paramString1, paramString2, paramInt);
      case 7:
        return new SQLNonTransientConnectionException(paramString1, paramString2, paramInt);
      case 8:
        return new SQLSyntaxErrorException(paramString1, paramString2, paramInt);
      case 9:
        return new SQLTimeoutException(paramString1, paramString2, paramInt);
      case 10:
        return new SQLTransactionRollbackException(paramString1, paramString2, paramInt);
      case 11:
        return new SQLTransientConnectionException(paramString1, paramString2, paramInt);
      case 12:
        return new SQLClientInfoException(paramString1, paramString2, paramInt, null);
      case 13:
        return new SQLRecoverableException(paramString1, paramString2, paramInt);
      case 4:
        return new SQLFeatureNotSupportedException(paramString1, paramString2, paramInt);
    } 
    return new SQLException(paramString1, paramString2, paramInt);
  }
  
  public static SQLException newSQLException(String paramString1, String paramString2, int paramInt) {
    paramString1 = TbResourceBundle.getKey(paramInt) + ":" + paramString1;
    return makeSQLException(paramString1, paramString2, paramInt);
  }
  
  public static SQLException newSQLException(String paramString, int paramInt) {
    String str = getSQLState(paramInt);
    paramString = TbResourceBundle.getKey(paramInt) + ":" + paramString;
    return makeSQLException(paramString, str, paramInt);
  }
  
  public static SQLException newSQLException(int paramInt, String paramString) {
    String str1 = getSQLState(paramInt);
    String str2 = getMsg(paramInt);
    if (null != paramString && !"".equals(paramString))
      str2 = str2 + " - " + paramString; 
    return makeSQLException(str2, str1, paramInt);
  }
  
  public static SQLException newSQLException(int paramInt, Object[] paramArrayOfObject) {
    String str1 = getSQLState(paramInt);
    String str2 = getMsg(paramInt, paramArrayOfObject);
    return makeSQLException(str2, str1, paramInt);
  }
  
  public static SQLException newSQLException(int paramInt) {
    return newSQLException(paramInt, (String)null);
  }
  
  public static SQLException newSQLException(int paramInt, long paramLong) {
    return newSQLException(paramInt, Long.toString(paramLong));
  }
  
  public static SQLException newSQLException(int paramInt1, int paramInt2) {
    return newSQLException(paramInt1, Integer.toString(paramInt2));
  }
  
  public static SQLException newSQLException(int paramInt, Throwable paramThrowable) {
    String str1 = getSQLState(paramInt);
    String str2 = getMsg(paramInt);
    switch (getSqlStateFromErrorCode(paramInt)) {
      case 0:
        return new SQLException(str2, str1, paramInt, paramThrowable);
      case 1:
        return new SQLNonTransientException(str2, str1, paramInt, paramThrowable);
      case 2:
        return new SQLTransientException(str2, str1, paramInt, paramThrowable);
      case 3:
        return new SQLDataException(str2, str1, paramInt, paramThrowable);
      case 5:
        return new SQLIntegrityConstraintViolationException(str2, str1, paramInt, paramThrowable);
      case 6:
        return new SQLInvalidAuthorizationSpecException(str2, str1, paramInt, paramThrowable);
      case 7:
        return new SQLNonTransientConnectionException(str2, str1, paramInt, paramThrowable);
      case 8:
        return new SQLSyntaxErrorException(str2, str1, paramInt, paramThrowable);
      case 9:
        return new SQLTimeoutException(str2, str1, paramInt, paramThrowable);
      case 10:
        return new SQLTransactionRollbackException(str2, str1, paramInt, paramThrowable);
      case 11:
        return new SQLTransientConnectionException(str2, str1, paramInt, paramThrowable);
      case 12:
        return new SQLRecoverableException(str2, str1, paramInt, paramThrowable);
      case 4:
        return new SQLFeatureNotSupportedException(str2, str1, paramInt);
    } 
    return new SQLException(str2, str1, paramInt, paramThrowable);
  }
  
  private static SQLWarning makeSQLWarning(String paramString1, String paramString2, int paramInt) {
    return new SQLWarning(paramString1, paramString2, paramInt);
  }
  
  public static SQLWarning newSQLWarning(String paramString, int paramInt) {
    String str = getSQLState(paramInt);
    paramString = TbResourceBundle.getKey(paramInt) + ":" + paramString;
    return makeSQLWarning(paramString, str, paramInt);
  }
  
  public static SQLWarning newSQLWarning(int paramInt, String paramString) {
    String str1 = getSQLState(paramInt);
    String str2 = getMsg(paramInt);
    if (null != paramString && !"".equals(paramString))
      str2 = str2 + " - " + paramString; 
    return makeSQLWarning(str2, str1, paramInt);
  }
  
  public static SQLWarning newSQLWarning(int paramInt, Object[] paramArrayOfObject) {
    String str1 = getSQLState(paramInt);
    String str2 = getMsg(paramInt, paramArrayOfObject);
    return makeSQLWarning(str2, str1, paramInt);
  }
  
  public static SQLWarning newSQLWarning(int paramInt) {
    return newSQLWarning(paramInt, (String)null);
  }
  
  public static SQLWarning newSQLWarning(int paramInt, long paramLong) {
    return newSQLWarning(paramInt, Long.toString(paramLong));
  }
  
  public static SQLWarning newSQLWarning(int paramInt1, int paramInt2) {
    return newSQLWarning(paramInt1, Integer.toString(paramInt2));
  }
  
  public static SQLWarning newSQLWarning(int paramInt, Throwable paramThrowable) {
    String str1 = getSQLState(paramInt);
    String str2 = getMsg(paramInt);
    return new SQLWarning(str2, str1, paramInt, paramThrowable);
  }
  
  private static int getSqlStateFromErrorCode(int paramInt) {
    String str1 = getSQLState(paramInt);
    String str2 = str1.substring(0, 2);
    String str3 = str1.substring(2, 5);
    byte b = 0;
    if (str2.equals("00")) {
      b = 0;
    } else if (str2.equals("22")) {
      b = 3;
    } else if (str2.equals("0A")) {
      b = 4;
    } else if (str2.equals("23")) {
      b = 5;
    } else if (str2.equals("08")) {
      if (str3.equals("000")) {
        b = 7;
      } else {
        b = 12;
      } 
    } else if (str2.equals("42")) {
      b = 8;
    } else if (str2.equals("40")) {
      b = 10;
    } else if (str2.equals("2B")) {
      b = 11;
    } 
    if (str2.equals("08"))
      b = 13; 
    return b;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\err\TbError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
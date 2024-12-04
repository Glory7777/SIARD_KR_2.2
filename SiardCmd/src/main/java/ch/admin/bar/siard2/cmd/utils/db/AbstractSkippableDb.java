package ch.admin.bar.siard2.cmd.utils.db;

import lombok.Getter;

import static ch.admin.bar.siard2.cmd.MetaDataBase.DataBase.isCubrid;
import static ch.admin.bar.siard2.cmd.MetaDataBase.DataBase.isTibero;
import static ch.admin.bar.siard2.cmd.utils.StringUtils.*;

public abstract class AbstractSkippableDb {

    @Getter
    private final String databaseProductName;

    public AbstractSkippableDb(String databaseProductName) {
        this.databaseProductName = databaseProductName;
    }

    protected boolean isTiberoSkippable(String schemaName) {
        return isTiberoDb() && isTiberoSysTable(schemaName);
    }

    protected boolean isCubridSkippable(String tableName) {
        return isCubridDb() && isCubridSystable(tableName);
    }

    protected String trimTableNameIfCubrid(String tableName) {
        return isCubridDb() ? trimTableName(tableName) : tableName;
    }

    private String trimTableName(String trailingTableName) {
        return removeFirstDot(trailingTableName);
    }

    protected boolean isTiberoDb() {
        return isTibero(this.databaseProductName);
    }

    protected boolean isCubridDb() {
        return isCubrid(this.databaseProductName);
    }

    private boolean isTiberoSysTable(String schemaName) {
        return schemaName.startsWith("SYS") || schemaName.equals("OUTLN"); // FIXME:: 추후 수정 필요
    }

    private boolean isCubridSystable(String tableName) {
        return tableName.toUpperCase().startsWith("DB_SERIAL");
    }
}

package ch.enterag.utils.jdbc;

import java.sql.SQLException;
import java.sql.Struct;
import java.util.Map;


public abstract class BaseStruct
        implements Struct {
    private Struct _structWrapped = null;


    public BaseStruct(Struct structWrapped) {
        this._structWrapped = structWrapped;
    }


    public String getSQLTypeName() throws SQLException {
        return this._structWrapped.getSQLTypeName();
    }


    public Object[] getAttributes() throws SQLException {
        return this._structWrapped.getAttributes();
    }


    public Object[] getAttributes(Map<String, Class<?>> map) throws SQLException {
        return this._structWrapped.getAttributes(map);
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcbase.jar!\ch\entera\\utils\jdbc\BaseStruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
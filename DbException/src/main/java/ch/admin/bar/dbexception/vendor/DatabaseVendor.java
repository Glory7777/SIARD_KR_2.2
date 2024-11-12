package ch.admin.bar.dbexception.vendor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum DatabaseVendor {

    MYSQL("mysql"),
    ORACLE("oracle"),
    POSTGRESQL("postgresql"),
    MSSQL("mssql"),
    CUBRID("cubrid"),
    TIBERO("tibero"),
    ;

    final String name;

    public static DatabaseVendor findByName(String name) {
        if (name.isEmpty()) throw new NoSuchElementException("No enum value is found");
        return Arrays.stream(DatabaseVendor.values())
                .filter(dataBase -> dataBase.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No value is found"));
    }

}

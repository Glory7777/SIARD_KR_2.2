package ch.admin.bar.siardsuite.service.database;

import ch.admin.bar.siardsuite.service.database.model.Dbms;
import ch.admin.bar.siardsuite.service.database.model.FileBasedDbms;
import ch.admin.bar.siardsuite.service.database.model.FileBasedDbmsConnectionProperties;
import ch.admin.bar.siardsuite.service.database.model.ServerBasedDbms;
import ch.admin.bar.siardsuite.service.database.model.ServerBasedDbmsConnectionProperties;
import lombok.val;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DbmsRegistry {

    public static final Collection<Dbms<?>> DBMS = Arrays.asList(
            FileBasedDbms.builder()
                    .name("MS Access")
                    .id("access")
                    .driverClassName("ch.admin.bar.siard2.jdbc.AccessDriver")
                    .jdbcConnectionStringEncoder(config -> String.format(
                            "jdbc:access:%s",
                            config.getFile().getAbsolutePath()))
                    .jdbcConnectionStringDecoder(encoded -> {
                        val file = new File(encoded.replace("jdbc:access:", ""));
                        return new FileBasedDbmsConnectionProperties(file);
                    })
                    .exampleFile(new File("D:\\Projekte\\SIARD2\\JdbcAccess\\testfiles\\dbfile.mdb"))
                    .build(),

            ServerBasedDbms.builder()
                    .name("DB/2")
                    .id("db2")
                    .driverClassName("ch.admin.bar.siard2.jdbc.Db2Driver")
                    .jdbcConnectionStringEncoder(config -> String.format(
                            "jdbc:db2://%s:%s/%s%s",
                            config.getHost(),
                            config.getPort(),
                            config.getDbName(),
                            config.getOptions()
                                    .map(optionsString -> "?" + optionsString)
                                    .orElse("")))
                    .jdbcConnectionStringDecoder(encoded -> {
                        val splitEncoded = encoded.split(":");
                        val splitPortAndDbNameWithOptions = splitEncoded[3].split("/", 2);
                        val splitDbNameAndOptions = splitPortAndDbNameWithOptions[1].split("\\?", 2);

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(splitEncoded[2].replace("//", ""))
                                .port(splitPortAndDbNameWithOptions[0])
                                .dbName(splitDbNameAndOptions[0])
                                .options(splitDbNameAndOptions.length > 1 ? Optional.of(splitDbNameAndOptions[1]) : Optional.empty())
                                .user("")
                                .password("")
                                .build();
                    })
                    .examplePort("50000")
                    .exampleHost("db2.exampleHost.org")
                    .exampleDbName("DB2-Database")
                    .build(),

            ServerBasedDbms.builder()
                    .name("MySQL")
                    .id("mysql")
                    .driverClassName("ch.admin.bar.siard2.jdbc.MySqlDriver")
                    .jdbcConnectionStringEncoder(config -> String.format(
                            "jdbc:mysql://%s:%s/%s%s",
                            config.getHost(),
                            config.getPort(),
                            config.getDbName(),
                            config.getOptions()
                                    .map(optionsString -> "?" + optionsString)
                                    .orElse("")))
                    .jdbcConnectionStringDecoder(encoded -> {
                        val splitEncoded = encoded.split(":");
                        val splitPortAndDbNameWithOptions = splitEncoded[3].split("/", 2);
                        val splitDbNameAndOptions = splitPortAndDbNameWithOptions[1].split("\\?", 2);

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(splitEncoded[2].replace("//", ""))
                                .port(splitPortAndDbNameWithOptions[0])
                                .dbName(splitDbNameAndOptions[0])
                                .options(splitDbNameAndOptions.length > 1 ? Optional.of(splitDbNameAndOptions[1]) : Optional.empty())
                                .user("")
                                .password("")
                                .build();
                    })
                    .examplePort("3306")
                    .exampleHost("mysql.exampleHost.org")
                    .exampleDbName("MySQL-Database")
                    .build(),

            ServerBasedDbms.builder()
                    .name("Oracle")
                    .id("oracle")
                    .driverClassName("ch.admin.bar.siard2.jdbc.OracleDriver")
                    .jdbcConnectionStringEncoder(config -> String.format(
                            "jdbc:oracle:thin:@%s:%s/%s%s",
                            config.getHost(),
                            config.getPort(),
                            config.getDbName(),
                            config.getOptions()
                                    .map(optionsString -> "?" + optionsString)
                                    .orElse("")))
                    .jdbcConnectionStringDecoder(encoded -> {
                        val splitEncoded = encoded.split(":");
                        val splitPortAndDbNameWithOptions = splitEncoded[4].split("/", 2);
                        val splitDbNameAndOptions = splitPortAndDbNameWithOptions[1].split("\\?", 2);

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(splitEncoded[3].replace("@", ""))
                                .port(splitPortAndDbNameWithOptions[0])
                                .dbName(splitDbNameAndOptions[0])
                                .options(splitDbNameAndOptions.length > 1 ? Optional.of(splitDbNameAndOptions[1]) : Optional.empty())
                                .user("")
                                .password("")
                                .build();
                    })
                    .examplePort("1521")
                    .exampleHost("oracle.exampleHost.org")
                    .exampleDbName("Oracle-Database")
                    .build(),

            ServerBasedDbms.builder()
                    .name("PostgreSQL")
                    .id("postgresql")
                    .driverClassName("ch.admin.bar.siard2.jdbc.PostgresDriver")
                    .jdbcConnectionStringEncoder(config -> String.format(
                            "jdbc:postgresql://%s:%s/%s%s", // example: "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true"
                            config.getHost(),
                            config.getPort(),
                            config.getDbName(),
                            config.getOptions()
                                    .map(optionsString -> "?" + optionsString)
                                    .orElse("")))
                    .jdbcConnectionStringDecoder(encoded -> {
                        val splitEncoded = encoded.split(":");
                        val splitPortAndDbNameWithOptions = splitEncoded[3].split("/", 2);
                        val host = splitEncoded[2].replace("//", "");
                        val splitDbNameAndOptions = splitPortAndDbNameWithOptions[1].split("\\?", 2);

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(host)
                                .port(splitPortAndDbNameWithOptions[0])
                                .dbName(splitDbNameAndOptions[0])
                                .options(Optional.of(splitDbNameAndOptions)
                                        .filter(strings -> strings.length > 1)
                                        .map(strings -> strings[1]))
                                .user("")
                                .password("")
                                .build();
                    })
                    .examplePort("5432")
                    .exampleHost("postgresql.exampleHost.org")
                    .exampleDbName("PostgreSQL-Database")
                    .build(),

            ServerBasedDbms.builder()
                    .name("Microsoft SQL Server")
                    .id("sqlserver")
                    .driverClassName("ch.admin.bar.siard2.jdbc.MsSqlDriver")
                    .jdbcConnectionStringEncoder(config -> String.format(
                            "jdbc:sqlserver://%s:%s;databaseName=%s%s",
                            config.getHost(),
                            config.getPort(),
                            config.getDbName(),
                            config.getOptions()
                                    .map(optionsString -> ";" + optionsString)
                                    .orElse("")))
                    .jdbcConnectionStringDecoder(encoded -> {
                        val splitEncoded = encoded.split(":");
                        val splitPortAndDbNameWithOptions = splitEncoded[3].split(";");

                        val dbName = Arrays.stream(splitPortAndDbNameWithOptions)
                                .skip(1) // contains port
                                .filter(s -> s.startsWith("databaseName="))
                                .map(s -> s.replace("databaseName=", ""))
                                .findAny()
                                .orElseThrow(() -> new IllegalArgumentException("Missing database name"));

                        val options = Arrays.stream(splitPortAndDbNameWithOptions)
                                .skip(1) // contains port
                                .filter(s -> !s.startsWith("databaseName="))
                                .collect(Collectors.joining(";"));

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(splitEncoded[2].replace("//", ""))
                                .port(splitPortAndDbNameWithOptions[0])
                                .dbName(dbName)
                                .options(Optional.of(options))
                                .user("")
                                .password("")
                                .build();
                    })
                    .examplePort("1433")
                    .exampleHost("mssql.exampleHost.org")
                    .exampleDbName("MS-SQL-Database")
                    .build(),

            ServerBasedDbms.builder()
                    .name("CUBRID")
                    .id("cubrid")
                    .driverClassName("ch.admin.bar.siard2.jdbc.CUBRIDDriver")
                    .jdbcConnectionStringEncoder(config -> {
                        String baseUrl = String.format(
                                "jdbc:cubrid:%s:%s:%s:%s::",
                                config.getHost(),
                                config.getPort(),
                                config.getDbName(),
                                config.getUser()
                        );

                        String options = config.getOptions()
                                .map(opts -> "?" + opts)
                                .orElse("");

                        return baseUrl + options;
                    })
                    .jdbcConnectionStringDecoder(encoded -> {
                        String[] splitEncoded = encoded.split(":");

                        if (splitEncoded.length < 5) {
                            throw new IllegalArgumentException("Invalid CUBRID JDBC URL format");
                        }

                        String host = splitEncoded[2];
                        String port = splitEncoded[3];
                        String dbName = splitEncoded[4];
                        String user = splitEncoded[5];

                        String password = ""; // 비밀번호가 없거나 필요 없는 경우

                        int length = splitEncoded.length;
                        String options;
                        if (length >= 7) {
                            String[] parts = splitEncoded[6].split("\\?", 2);
                            options = parts.length > 1 ? parts[1] : "";
                        } else {
                            options = "charset=utf8"; // cubrid 기본설정
                        }

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(host)
                                .port(port)
                                .dbName(dbName)
                                .user(user)
                                .password(password)
                                .options(Optional.of(options))
                                .build();
                    })
                    .examplePort("30000")
                    .exampleHost("cubrid.exampleHost.org")
                    .exampleDbName("testDB")
                    .build(),


            ServerBasedDbms.builder()
                    .name("TIBERO")
                    .id("tibero")
                    .driverClassName("com.tmax.tibero.jdbc.TbDriver")
                    .jdbcConnectionStringEncoder(config -> {
                        // JDBC URL 구성
                        String baseUrl = String.format(
                                "jdbc:tibero://%s:%s/%s",
                                config.getHost(),
                                config.getPort(),
                                config.getDbName()
                        );

                        // 사용자 이름과 비밀번호를 URL의 쿼리 파라미터로 추가
                        String options = String.format("?user=%s&password=%s",
                                config.getUser(),
                                config.getPassword());

                        return baseUrl + options;
                    })
                    .jdbcConnectionStringDecoder(encoded -> {
                        String[] splitEncoded = encoded.split(":");

                        // URL 형식 검증
                        if (splitEncoded.length < 4) {
                            throw new IllegalArgumentException("Invalid TIBERO JDBC URL format");
                        }

                        // 호스트, 포트, 데이터베이스 이름 추출
                        String host = splitEncoded[2].replace("//", "");
                        String[] portAndDbName = splitEncoded[3].split("/", 2);
                        String port = portAndDbName[0];
                        String dbName = portAndDbName[1].split("\\?")[0];

                        // 쿼리 파라미터 처리
                        String user = "";
                        String password = "";

                        if (encoded.contains("?")) {
                            String[] queryParams = encoded.split("\\?")[1].split("&");
                            for (String option : queryParams) {
                                if (option.startsWith("user=")) {
                                    user = option.substring("user=".length());
                                } else if (option.startsWith("password=")) {
                                    password = option.substring("password=".length());
                                }
                            }
                        }

                        return ServerBasedDbmsConnectionProperties.builder()
                                .host(host)
                                .port(port)
                                .dbName(dbName)
                                .user(user)
                                .password(password)
                                .options(Optional.empty()) // 필요 시 추가 옵션 처리
                                .build();
                    })
                    .examplePort("8629")
                    .exampleHost("tibero.exampleHost.org")
                    .exampleDbName("tibero")
                    .build()

    );

    public static Set<String> getSupportedDbms() {
        return DBMS.stream()
                .map(Dbms::getName)
                .collect(Collectors.toSet());
    }

    public static Dbms<?> findDbmsByName(final String name) {
        return DBMS.stream()
                .filter(dbms -> dbms.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No DBMS with name " + name + " available"));
    }

    public static Predicate<String> checkJdbcUrlValidity(final Dbms<?> serverBasedDbms) {
        return nullableValue -> Optional.ofNullable(nullableValue)
                .filter(value -> {
                    if (!value.startsWith("jdbc:" + serverBasedDbms.getId())) {
                        return false;
                    }

                    try {
                        serverBasedDbms.getJdbcConnectionStringDecoder().apply(value);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .isPresent();
    }
}

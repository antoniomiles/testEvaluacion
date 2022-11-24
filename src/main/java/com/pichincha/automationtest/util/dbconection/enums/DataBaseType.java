package com.pichincha.automationtest.util.dbconection.enums;

import lombok.Getter;

@Getter
public enum DataBaseType {
    POSTGRES("org.postgresql.Driver","jdbc:postgresql://<host>:<port>/<database>"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://<host>:<port>;databaseName=<database>;encrypt=false"),
    MYSQL("com.mysql.cj.jdbc.Driver","jdbc:mysql://<host>:<port>/<database>");

    private final String driver;
    private final String jdbcUrl;
    DataBaseType(String driver, String jdbcUrl) {
        this.driver = driver;
        this.jdbcUrl = jdbcUrl;
    }

}

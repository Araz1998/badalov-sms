package com.badalov.sms.badalovsms;

import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

@Sql(scripts = {"classpath:CleanUpDatabase.sql"})
public abstract class BaseTest {
    static MSSQLServerContainer DATABASE;

    static {
        startDatabase();
    }

    private static void startDatabase() {
        DockerImageName myImage = DockerImageName.parse("mcr.microsoft.com/mssql/server:2019-latest");
        DATABASE = (MSSQLServerContainer) new MSSQLServerContainer(myImage).withInitScript("schema-and-data.sql");

        DATABASE.start();

        System.setProperty("spring.datasource.url", DATABASE.getJdbcUrl());
        System.setProperty("spring.datasource.username", DATABASE.getUsername());
        System.setProperty("spring.datasource.password", DATABASE.getPassword());
    }
}

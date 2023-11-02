package com.apm.terminals.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NavisDatabaseConnectionService {
    private static final Logger logger = LogManager.getLogger(NavisDatabaseConnectionService.class);
    @Value("${n4.database.ip}")
    private String ip;
    @Value("${n4.database.username}")
    private String username;
    @Value("${n4.database.password}")
    private String password;
    @Value("${n4.database.port}")
    private String port;
    @Value("${n4.database.database}")
    private String database;

    public NavisDatabaseConnectionService() {
    }

    public Connection connectDatabase() {
        String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s", this.ip, this.port, this.database);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException var5) {
            logger.error(var5.getMessage());
        }

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, this.username, this.password);
        } catch (SQLException var4) {
            logger.error(var4.getMessage());
        }

        return conn;
    }
}
package br.com.stockMaster.database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        final String path = "src/resources/config.properties";
        try {
            InputStream input = new FileInputStream(path);
            properties.load(input);
            final String url = properties.getProperty("db.url");
            final String user = properties.getProperty("db.user");
            final String password = properties.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}

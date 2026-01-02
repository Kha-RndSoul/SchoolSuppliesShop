package com.shop.util;

import java.io.IOException;
import java.util.Properties;

public class DBProperties {
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(DBProperties.class.getClassLoader()
                    .getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String host() {
        return prop.getProperty("db.host", "localhost");
    }

    public static String port() {
        return prop.getProperty("db.port", "3307");
    }

    public static String username() {
        return prop.getProperty("db.username", "root");
    }

    public static String password() {
        return prop.getProperty("db.password", "");
    }

    public static String dbname() {
        return prop.getProperty("db.name", "test");
    }

    public static String option() {
        return prop.getProperty("db.option", "useUnicode=true&characterEncoding=utf-8");
    }
}
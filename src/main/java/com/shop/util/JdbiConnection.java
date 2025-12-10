package com.shop.util;

import org.jdbi.v3.core. Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class JdbiConnection {

    private static Jdbi jdbi;

    private static final String URL = "jdbc:mysql://localhost:3306/school_supplies_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc. Driver";

    static {
        try {
            Class.forName(DRIVER);
            jdbi = Jdbi.create(URL, USERNAME, PASSWORD);
            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.getConfig(org.jdbi.v3.core.statement.SqlStatements.class)
                    . setUnusedBindingAllowed(true);
            System.out.println(" JDBI initialized successfully");
        } catch (ClassNotFoundException e) {
            System. err.println(" MySQL JDBC Driver not found");
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver", e);
        } catch (Exception e) {
            System.err.println(" Failed to initialize JDBI");
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize JDBI", e);
        }
    }

    public static Jdbi getJdbi() {
        return jdbi;
    }

    public static boolean testConnection() {
        try {
            jdbi.withHandle(handle ->
                    handle.createQuery("SELECT 1")
                            .mapTo(Integer.class)
                            .one()
            );
            return true;
        } catch (Exception e) {
            System.err. println(" Connection test failed:  " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String getUrl() {
        return URL;
    }
}
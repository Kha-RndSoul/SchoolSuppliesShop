package com.shop.dao.support;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.shop.util.DBProperties;  // ← Import từ util
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLException;
public abstract class BaseDao {
    private static Jdbi jdbi;

    protected Jdbi get() {
        if (jdbi == null) {
            makeConnect();
        }
        return jdbi;
    }

    private void makeConnect() {
        MysqlDataSource src = new MysqlDataSource();

        String url = "jdbc:mysql://" + DBProperties.host() + ":" +
                DBProperties.port() + "/" + DBProperties.dbname() +
                "?" + DBProperties.option();

        src.setURL(url);
        src.setUser(DBProperties.username());
        src.setPassword(DBProperties.password());

        try {
            src.setUseCompression(true);
            src.setAutoReconnect(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        jdbi = Jdbi.create(src);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public static Jdbi getJdbi() {
        if (jdbi == null) {
            new BaseDao() {}.get();
        }
        return jdbi;
    }
}

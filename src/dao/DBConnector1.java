package dao;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector1 {
    private static Logger logger = LogManager.getLogger(DBConnector1.class);

    private static DataSource dataSource;
    static {
        try {
            Context init = new InitialContext();
            Context envContext = (Context) init.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/HorseRidesDB");
        } catch (NamingException e) {
            logger.error("Не удалось инициализировать DataSource", e);
        }
    }

    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            logger.error("Не удалось получить подключение к БД", e);
        }
        return null;
    }

}

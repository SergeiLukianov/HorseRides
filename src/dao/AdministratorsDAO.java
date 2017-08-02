package dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import users.Admin;

import java.sql.*;

public class AdministratorsDAO {

    private static Logger logger = LogManager.getRootLogger();

    public static boolean create(String nick, String pass){
        Connection connection = DBConnector1.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("insert into horse_rides.administrators (administrator_nick,administrator_pass) values (?,?)");
            synchronized (AdministratorsDAO.class) {
                st.setString(1, nick);
                st.setString(2, pass);
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось создать учетную запись администратора " + nick + ", " + pass);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Соединение не удалось закрыть");
            }
        }
        return false;
    }

    public static Admin getEntityByNick(String nick) {
        Connection connection = DBConnector1.getConnection();
        Admin admin = new Admin();
        admin.setNick(nick);
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.administrators where administrator_nick=?");
            synchronized (AdministratorsDAO.class) {
                st.setString(1, nick);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    admin.setPass(rs.getString("administrator_pass"));
                    return admin;
                } else {
                    rs.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Не удалось создать учетную запись администратора " + nick);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Соединение не удалось закрыть");
            }
        }
        return null;
    }
}

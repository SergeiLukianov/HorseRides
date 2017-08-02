package dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import entities.users.Client;

import java.sql.*;

public class ClientsDAO {
    private static Logger logger = LogManager.getRootLogger();

    public static boolean create(String nick, String pass){
        Connection connection = DBConnector1.getConnection();
        try{
            PreparedStatement st = connection.prepareStatement("insert into horse_rides.clients (client_nick,client_pass) values (?,?)");
            synchronized (ClientsDAO.class) {
                st.setString(1, nick);
                st.setString(2, pass);
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось создать учетную запись клиента " + nick, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return false;
    }


    public static Client getEntityByNick(String nick) {
        Connection connection = DBConnector1.getConnection();
        Client client = new Client();
        client.setNick(nick);
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.clients where client_nick=?");
            synchronized (ClientsDAO.class) {
                st.setString(1, nick);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    client.setPass(rs.getString("client_pass"));
                    return client;
                } else {
                    rs.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочитать запись клиента " + nick, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return null;
    }
}

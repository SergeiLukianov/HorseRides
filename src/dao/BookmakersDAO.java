package dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import entities.users.Bookmaker;

import java.sql.*;

public class BookmakersDAO {
    private static final Logger logger = LogManager.getLogger(BookmakersDAO.class);

    public static boolean create(String nick, String pass){
        Connection connection = DBConnector1.getConnection();
        try{
            PreparedStatement st = connection.prepareStatement("insert into horse_rides.bookmakers (bookmaker_nick,bookmaker_pass) values (?,?)");
            synchronized (BookmakersDAO.class) {
                st.setString(1, nick);
                st.setString(2, pass);
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось создать учетную запись букмекера " + nick, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return false;
    }

    public static Bookmaker getEntityByNick(String nick) {
        Connection connection = DBConnector1.getConnection();
        Bookmaker bookmaker = new Bookmaker();
        bookmaker.setNick(nick);
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.bookmakers where bookmaker_nick=?");
            synchronized (BookmakersDAO.class) {
                st.setString(1, nick);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    bookmaker.setPass(rs.getString("bookmaker_pass"));
                    return bookmaker;
                } else {
                    rs.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочитать запись букмекера " + nick, e);
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

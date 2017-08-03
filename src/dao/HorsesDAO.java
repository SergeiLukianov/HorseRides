package dao;

import entities.Horse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class HorsesDAO {
    private static final Logger logger = LogManager.getLogger(HorsesDAO.class);

    public static List<Horse> getAll() {
        List<Horse> horses = new ArrayList<>();
        Connection connection = DBConnector1.getConnection();
        try {
            Statement st = connection.createStatement();
            synchronized (HorsesDAO.class) {
                ResultSet rs = st.executeQuery("SELECT * FROM horse_rides.horses");
                while (rs.next()) {
                    Horse a = new Horse();
                    a.setId(rs.getInt("horse_id"));
                    a.setName(rs.getString("horse_name"));
                    horses.add(a);
                }
                rs.close();
            }
            return horses;
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все записи", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return null;
    }

    public static boolean create(String name) {
        Connection connection = DBConnector1.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("insert into horse_rides.horses (horse_name) value (?)");
            st.setString(1, name);
            synchronized (HorsesDAO.class) {
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось записать новую лошадь с именем" + name, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return false;
    }

    public static Horse update(int id, String name) {
        Horse oldHorse = new Horse();
        Connection connection = DBConnector1.getConnection();
        try {
            PreparedStatement st1 = connection.prepareStatement("select * from horse_rides.horses where horse_id=?");
            st1.setInt(1, id);
            synchronized (HorsesDAO.class) {

                ResultSet rs = st1.executeQuery();
                oldHorse.setId(id);
                oldHorse.setName(rs.getString("horse_name"));
                rs.close();

                PreparedStatement st = connection.prepareStatement("update horse_rides.horses set horse_name=? where horse_id=?");
                st.setString(1, name);
                st.setInt(2, id);
                int res = st.executeUpdate();
                return oldHorse;
            }
        } catch (SQLException e) {
            logger.error("Не удалось обновить запись с id=" + id + " на " + name, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return null;
    }

    public static Horse getEntityById(int id) {
        Connection connection = DBConnector1.getConnection();
        Horse horse = new Horse();
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.horses where horse_id=?");
            st.setInt(1, id);
            synchronized (HorsesDAO.class) {
                ResultSet rs = st.executeQuery();
                horse.setId(id);
                if (rs.next()) {
                    horse.setName(rs.getString("horse_name"));
                }
                rs.close();
            }
            return horse;
        } catch (SQLException e) {
            logger.error("Не удалось прочесть запись с id=" + id, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return null;
    }

    public static boolean delete(int id) {
        Connection connection = DBConnector1.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("delete from horse_rides.horses where horse_id=?");
            st.setInt(1, id);
            synchronized (HorsesDAO.class) {
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return false;
    }
}

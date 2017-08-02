package dao;

import entities.Result;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class ResultsDAO {
    private static Logger logger = LogManager.getLogger(ResultsDAO.class);

    public static List<Result> getAll() {
        List<Result> results = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try (Statement st = conn.createStatement()) {
            synchronized (ResultsDAO.class) {
                ResultSet rs = st.executeQuery("select * from horse_rides.results");
                while (rs.next()) {
                    Result res = new Result();
                    res.setId(rs.getInt("result_id"));
                    res.setDate(rs.getString("date"));
                    res.setWinner(rs.getInt("horse_winner_id"));
                    results.add(res);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все записи", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }

        return results;
    }

    public static List<Result> getAll(String date) {
        List<Result> results = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horse_rides.results where date=?");
            st.setString(1, date);
            synchronized (ResultsDAO.class) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Result res = new Result();
                    res.setId(rs.getInt("result_id"));
                    res.setDate(rs.getString("date"));
                    res.setWinner(rs.getInt("horse_winner_id"));
                    results.add(res);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все записи на " + date, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }

        return results;
    }

    public static boolean create(Result result) {
        Connection connection = DBConnector1.getConnection();
        try{
            PreparedStatement st = connection.prepareStatement("insert into horse_rides.results(date,horse_winner_id) values(?,?)");
            synchronized (ResultsDAO.class) {
                st.setString(1, result.getDate());
                st.setInt(2, result.getWinner());
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось записать результат " + result, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return false;
    }

    public static Result update(int id, Result result) {
        Result oldResult = new Result();
        Connection connection = DBConnector1.getConnection();
        try {
            synchronized (ResultsDAO.class) {
                PreparedStatement st = connection.prepareStatement("select * from horse_rides.results where result_id=?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    oldResult.setId(id);
                    oldResult.setDate(rs.getString("date"));
                    oldResult.setWinner(rs.getInt("horse_winner_id"));
                }
                rs.close();
                PreparedStatement st1 = connection.prepareStatement("update horse_rides.results set date=?, horse_winner_id=? where result_id=?");
                st1.setString(1, result.getDate());
                st1.setInt(2, result.getWinner());
                st1.setInt(3, id);
                st1.executeUpdate();
            }
            return oldResult;
        } catch (SQLException e) {
            logger.error("Не удалось обновить запись результата " + oldResult + " на " + result, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return null;
    }

    public static Result getEntityById(Integer id) {
        Connection connection = DBConnector1.getConnection();
        Result result = new Result();
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.results where result_id=?");
            st.setInt(1, id);
            synchronized (ResultsDAO.class) {
                ResultSet rs = st.executeQuery();
                result.setId(id);
                if (rs.next()) {
                    result.setDate(rs.getString("date"));
                    result.setWinner(rs.getInt("horse_winner_id"));
                }
                rs.close();
            }
            return result;
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

    public static Result getEntity(String date, int winner){
        Connection connection = DBConnector1.getConnection();
        Result result = new Result();
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.results where date=? and horse_winner_id=?");
            st.setString(1, date);
            st.setInt(2, winner);
            synchronized (ResultsDAO.class) {
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    result.setDate(rs.getString("date"));
                    result.setWinner(rs.getInt("horse_winner_id"));
                } else {
                    return null;
                }
                rs.close();
            }
            return result;
        } catch (SQLException e) {
            logger.error("Не удалось прочесть результат на " + date + ", где winner=" + winner, e);
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

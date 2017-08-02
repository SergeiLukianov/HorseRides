package dao;

import entities.Coefficient;
import entities.Result;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class CoefficientsDAO {
    private static Logger logger = LogManager.getRootLogger();

    public static List<Coefficient> getAll() {
        List<Coefficient> coefficients = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horse_rides.coefficients");
            synchronized (CoefficientsDAO.class) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Coefficient coeff = new Coefficient();
                    coeff.setId(rs.getInt("coefficient_id"));
                    coeff.setDate(rs.getString("date"));
                    coeff.setHorseId(rs.getInt("horse_id"));
                    coeff.setHorseName(rs.getString("horse_name"));
                    coeff.setCoefficient(rs.getDouble("coefficient"));

                    coefficients.add(coeff);
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

        return coefficients;
    }

    public static List<Coefficient> getAll(String date) {
        List<Coefficient> coefficients = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horse_rides.coefficients where date=?");
            synchronized (CoefficientsDAO.class) {
                st.setString(1, date);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Coefficient coeff = new Coefficient();
                    coeff.setId(rs.getInt("coefficient_id"));
                    coeff.setDate(rs.getString("date"));
                    coeff.setHorseId(rs.getInt("horse_id"));
                    coeff.setHorseName(rs.getString("horse_name"));
                    coeff.setCoefficient(rs.getDouble("coefficient"));

                    coefficients.add(coeff);
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
        return coefficients;
    }

    public static List<Coefficient> getAllSince(String date) {
        List<Coefficient> coefficients = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horse_rides.coefficients where date >=?");
            synchronized (CoefficientsDAO.class) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Coefficient coeff = new Coefficient();
                    coeff.setId(rs.getInt("coefficient_id"));
                    coeff.setDate(rs.getString("date"));
                    coeff.setHorseId(rs.getInt("horse_id"));
                    coeff.setHorseName(rs.getString("horse_name"));
                    coeff.setCoefficient(rs.getDouble("coefficient"));

                    coefficients.add(coeff);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все записи начиная с " + date + " включительно", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }

        return coefficients;
    }

    public static List<Coefficient> getAllUnregistered(String currentDate) {
        ResultSet rs = null;
        ResultSet rs1 = null;
        List<Coefficient> coefficients = new ArrayList<>();
        List<Result> results = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM horse_rides.coefficients where date>=?");
            synchronized (CoefficientsDAO.class) {
                st.setString(1, currentDate);
                rs = st.executeQuery();
                while (rs.next()) {
                    Coefficient coeff = new Coefficient();
                    coeff.setId(rs.getInt("coefficient_id"));
                    coeff.setDate(rs.getString("date"));
                    coeff.setHorseId(rs.getInt("horse_id"));
                    coeff.setHorseName(rs.getString("horse_name"));
                    coeff.setCoefficient(rs.getDouble("coefficient"));
                    coefficients.add(coeff);
                }
                rs.close();
                synchronized (ResultsDAO.class) {
                    Statement st1 = conn.createStatement();
                    rs1 = st1.executeQuery("SELECT * FROM horse_rides.results");
                    while (rs1.next()) {
                        Result result = new Result();
                        result.setId(rs1.getInt("result_id"));
                        result.setDate(rs1.getString("date"));
                        result.setWinner(rs1.getInt("horse_winner_id"));
                        results.add(result);
                    }
                    rs1.close();
                }
                for (Iterator<Coefficient> it = coefficients.iterator(); it.hasNext(); ) {
                    Coefficient c = it.next();
                    for (Result r : results) {
                        if (c.getDate().equals(r.getDate())) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
            Collections.sort(coefficients, new Comparator<Coefficient>() {
                @Override
                public int compare(Coefficient o1, Coefficient o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все записи незарегистрированных в результатах коэффициентов", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return coefficients;
    }


    public static boolean create(Coefficient coefficient) {
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("insert into horse_rides.coefficients(date,horse_id,horse_name,coefficient) values(?,?,?,?)");
            synchronized (CoefficientsDAO.class) {
                st.setString(1, coefficient.getDate());
                st.setInt(2, coefficient.getHorseId());
                st.setString(3, coefficient.getHorseName());
                st.setDouble(4, coefficient.getCoefficient());
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось записать коэффициент " + coefficient, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return false;
    }

    public static Coefficient update(int id, Coefficient coefficient) {
        Coefficient oldCoeff = new Coefficient();
        Connection connection = DBConnector1.getConnection();
        try {
            synchronized (CoefficientsDAO.class) {
                PreparedStatement st = connection.prepareStatement("select * from horse_rides.coefficients where coefficient_id=?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                PreparedStatement st1 = connection.prepareStatement("update horse_rides.coefficients set coefficient=? where coefficient_id=?");
                st1.setDouble(1, coefficient.getCoefficient());
                st1.setInt(2, id);
                if (rs.next()) {
                    oldCoeff.setId(id);
                    oldCoeff.setDate(rs.getString("date"));
                    oldCoeff.setHorseId(rs.getInt("horse_id"));
                    oldCoeff.setHorseName(rs.getString("horse_name"));
                    oldCoeff.setCoefficient(rs.getDouble("coefficient"));
                    st1.executeUpdate();
                }
                rs.close();
            }
            return oldCoeff;
        } catch (SQLException e) {
            logger.error("Не удалось обновить коэффициент с id=" + id
                    + " на " + coefficient, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение", e);
            }
        }
        return null;
    }

    public static Coefficient getEntityById(Integer id) {
        Connection connection = DBConnector1.getConnection();
        Coefficient coefficient = new Coefficient();
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.coefficients where coefficient_id=?");
            st.setInt(1, id);
            synchronized (CoefficientsDAO.class) {
                ResultSet rs = st.executeQuery();

                coefficient.setId(id);
                if (rs.next()) {
                    coefficient.setDate(rs.getString("date"));
                    coefficient.setHorseId(rs.getInt("horse_id"));
                    coefficient.setHorseName(rs.getString("horse_name"));
                    coefficient.setCoefficient(rs.getDouble("coefficient"));
                } else {
                    rs.close();
                    return null;
                }
            }
            return coefficient;
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

    public static Coefficient getEntityByDateHorse(String date, int horseId) {
        Connection connection = DBConnector1.getConnection();
        Coefficient coefficient = new Coefficient();
        try {
            PreparedStatement st = connection.prepareStatement(
                    "select * from horse_rides.coefficients where date=? and horse_id=?");
            st.setString(1, date);
            st.setInt(2, horseId);
            synchronized (CoefficientsDAO.class) {
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    coefficient.setId(rs.getInt("coefficient_id"));
                    coefficient.setDate(rs.getString("date"));
                    coefficient.setHorseId(rs.getInt("horse_id"));
                    coefficient.setHorseName(rs.getString("horse_name"));
                    coefficient.setCoefficient(rs.getDouble("coefficient"));
                } else {
                    rs.close();
                    return null;
                }
            }
            return coefficient;
        } catch (SQLException e) {
            logger.error("Не удалось прочесть коэффициент на " + date + " где horse_id=" + horseId, e);
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

package dao;

import entities.Bet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetsDAO {
    private static Logger logger = LogManager.getLogger(BetsDAO.class);

    public static List<Bet> getAll() {
        List<Bet> bets = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try{
            synchronized (BetsDAO.class) {
                PreparedStatement st = conn.prepareStatement("select * from horse_rides.bets");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Bet bet = new Bet();
                    bet.setId(rs.getInt("bet_id"));
                    bet.setDate(rs.getString("date"));
                    bet.setUserName(rs.getString("user_name"));
                    bet.setCoefficient(rs.getDouble("coefficient"));
                    bet.setAmount(rs.getDouble("amount"));
                    bet.setGuess(rs.getInt("horse_id"));
                    int stateId = rs.getInt("bet_state_id");
                    bet.setBetState(BetStatesDAO.getEntityById(stateId).getState());
                    bet.setCheckSum(rs.getInt("check_sum"));

                    bets.add(bet);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все ставки из БД");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение");
            }
        }
        return bets;
    }

    public static List<Bet> getAll(String date) {
        List<Bet> bets = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            Statement st = conn.createStatement();
            synchronized (BetsDAO.class) {
                ResultSet rs = st.executeQuery("select * from horse_rides.bets where date=\'" + date + "\'");
                while (rs.next()) {
                    Bet bet = new Bet();
                    bet.setId(rs.getInt("bet_id"));
                    bet.setDate(rs.getString("date"));
                    bet.setUserName(rs.getString("user_name"));
                    bet.setAmount(rs.getDouble("amount"));
                    bet.setCoefficient(rs.getDouble("coefficient"));
                    bet.setGuess(rs.getInt("horse_id"));
                    int stateId = rs.getInt("bet_state_id");
                    bet.setBetState(BetStatesDAO.getEntityById(stateId).getState());
                    bet.setCheckSum(rs.getInt("check_sum"));

                    bets.add(bet);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все ставки на" + date + " из БД");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение");
            }
        }
        return bets;
    }

    public static List<Bet> getAllForClient(String userName) {
        List<Bet> bets = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            synchronized (BetsDAO.class) {
                PreparedStatement st = conn.prepareStatement("select * from horse_rides.bets where user_name=?");
                st.setString(1, userName);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Bet bet = new Bet();
                    bet.setId(rs.getInt("bet_id"));
                    bet.setDate(rs.getString("date"));
                    bet.setUserName(rs.getString("user_name"));
                    bet.setAmount(rs.getDouble("amount"));
                    bet.setCoefficient(rs.getDouble("coefficient"));
                    bet.setGuess(rs.getInt("horse_id"));
                    int stateId = rs.getInt("bet_state_id");
                    bet.setBetState(BetStatesDAO.getEntityById(stateId).getState());
                    bet.setCheckSum(rs.getInt("check_sum"));

                    bets.add(bet);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все ставки клиента " + userName + " из БД");
        }
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("Не удалось закрыть соединение");
        }
        return bets;
    }

    public static List<Bet> getAllToBePayed(){
        List<Bet> bets = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            synchronized (BetsDAO.class) {
                PreparedStatement st = conn.prepareStatement("select * from horse_rides.bets where bet_state_id=2");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Bet bet = new Bet();
                    bet.setId(rs.getInt("bet_id"));
                    bet.setDate(rs.getString("date"));
                    bet.setUserName(rs.getString("user_name"));
                    bet.setAmount(rs.getDouble("amount"));
                    bet.setCoefficient(rs.getDouble("coefficient"));
                    bet.setGuess(rs.getInt("horse_id"));
                    int stateId = rs.getInt("bet_state_id");
                    bet.setBetState(BetStatesDAO.getEntityById(stateId).getState());
                    bet.setCheckSum(rs.getInt("check_sum"));

                    bets.add(bet);
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Не удалось прочесть все выигрышные ставки из БД");
        }
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("Не удалось закрыть соединение");
        }
        return bets;
    }

    public static boolean create(Bet bet) {
        Connection connection = DBConnector1.getConnection();
        try (PreparedStatement st =
                     connection.prepareStatement("insert into horse_rides.bets" +
                             "(date,user_name,horse_id,amount,coefficient,bet_state_id,check_sum) values(?,?,?,?,?,?,?)")) {
            synchronized (BetsDAO.class) {
                st.setString(1, bet.getDate());
                st.setString(2, bet.getUserName());
                st.setInt(3, bet.getGuess());
                st.setDouble(4, bet.getAmount());
                st.setDouble(5, bet.getCoefficient());
                st.setDouble(6, 1);
                st.setInt(7, bet.getCheckSum());
                st.execute();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Не удалось записать ставку " + bet);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение");
            }
        }
        return false;
    }

    public static Bet getEntityById(int id) {
        Connection connection = DBConnector1.getConnection();
        Bet bet = new Bet();
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.bets where bet_id=?");
            synchronized (BetsDAO.class) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                bet.setId(id);

                if (rs.next()) {
                    bet.setUserName(rs.getString("user_name"));
                    bet.setDate(rs.getString("date"));
                    bet.setGuess(rs.getInt("horse_id"));
                    bet.setCoefficient(rs.getDouble("coefficient"));
                    bet.setAmount(rs.getDouble("amount"));
                    int stateId = rs.getInt("bet_state_id");
                    bet.setBetState(BetStatesDAO.getEntityById(stateId).getState());
                    bet.setCheckSum(rs.getInt("check_sum"));
                }
                rs.close();
            }
            return bet;
        } catch (SQLException e) {
            logger.error("Не удалось прочесть ставку с id=" + id);
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение");
            }
        }
    }

    public static Bet update(int id, int newBetStateId) {
        Connection connection = DBConnector1.getConnection();
        Bet oldBet = null;
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE horse_rides.bets SET bet_state_id=? where bet_id=?");
            synchronized (BetsDAO.class) {
                st.setInt(1, newBetStateId);
                st.setInt(2, id);
                oldBet = BetsDAO.getEntityById(id);
                st.executeUpdate();
            }
            return oldBet;
        } catch (SQLException e) {
            logger.error("Не удалось обновить состояние ставки " + oldBet + " на " + newBetStateId);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Не удалось закрыть соединение");
            }
        }
        return null;
    }
}
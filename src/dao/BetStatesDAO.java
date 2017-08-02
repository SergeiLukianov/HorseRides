package dao;

import entities.BetState;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetStatesDAO {
    private static Logger logger = LogManager.getLogger(BetStatesDAO.class);

    public static List<BetState> getAll() {
        List<BetState> states = new ArrayList<>();
        Connection conn = DBConnector1.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horse_rides.bet_states");
            synchronized (BetStatesDAO.class) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    BetState state = new BetState();
                    state.setId(rs.getInt("state_id"));
                    state.setState(rs.getString("state"));
                    states.add(state);
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
        return states;
    }

    public static BetState getEntityById(Integer id) {
        Connection connection = DBConnector1.getConnection();
        BetState state = new BetState();
        try {
            PreparedStatement st = connection.prepareStatement("select * from horse_rides.bet_states where state_id=?");
            synchronized (BetStatesDAO.class) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery("select * from horse_rides.bet_states where state_id=" + id);
                state.setId(id);
                if (rs.next()) {
                    state.setState(rs.getString("state"));
                    return state;
                }
                rs.close();
            }
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
}

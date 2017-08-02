package entities.users;

import dao.ResultsDAO;
import entities.Result;

public class Admin {
    private String nick;
    private String pass;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void registerRideResult(String date, int winnerId){
        Result res = new Result();
        res.setDate(date);
        res.setWinner(winnerId);

        ResultsDAO.create(res);
    }

    public void editRideResult(int id, int winnerId){
        Result res = ResultsDAO.getEntityById(id);
        res.setWinner(winnerId);
        ResultsDAO.update(id, res);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "nick='" + nick + '\'' +
                '}';
    }
}

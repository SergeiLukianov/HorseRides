package users;

import entities.Bet;
import dao.BetsDAO;

public class Client {
    private String nick;
    private String pass;

    public Client() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String name) {
        nick = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public void makeABet(String date, int horse, double amount){
        Bet bet = new Bet();
        bet.setDate(date);
        bet.setGuess(horse);
        bet.setAmount(amount);
        bet.setUserName(nick);
        BetsDAO.create(bet);
    }

    @Override
    public String toString() {
        return "Client{" +
                "nick='" + nick + "\'}";
    }
}

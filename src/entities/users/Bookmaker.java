package entities.users;

import dao.CoefficientsDAO;
import entities.Coefficient;

public class Bookmaker {

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

    public static void setNewCoefficient(String date, int horseId, double coefficient){
        Coefficient coeff = new Coefficient();
        coeff.setDate(date);
        coeff.setHorseId(horseId);
        coeff.setCoefficient(coefficient);

        CoefficientsDAO.create(coeff);
    }

    public static void editCoefficient(int coefficientId, String date, int horseId, double coefficient){
        Coefficient coeff = new Coefficient();
        coeff.setDate(date);
        coeff.setHorseId(horseId);
        coeff.setCoefficient(coefficient);

        CoefficientsDAO.update(coefficientId, coeff);
    }

    @Override
    public String toString() {
        return "Bookmaker{" +
                "nick='" + nick + '\'' +
                '}';
    }
}

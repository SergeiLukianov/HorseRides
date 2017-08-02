package entities;

import java.util.Random;

public class Bet {
    private int id;
    private double amount;
    private int guess;
    private String date;
    private String userName;
    private double coefficient;
    private String betState;
    private int checkSum;

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBetState() {
        return betState;
    }

    public void setBetState(String betState) {
        this.betState = betState;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "ID=" + id +
                ", userName='" + userName +
                "', date='" + date +
                "', guess=" + guess +
                ", amount=" + amount +
                ", state='" + betState +
                "'}";
    }
}

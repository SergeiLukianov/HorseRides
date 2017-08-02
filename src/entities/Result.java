package entities;

public class Result {
    private int id;
    private String date;
    private int winner;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", winner=" + winner +
                '}';
    }
}

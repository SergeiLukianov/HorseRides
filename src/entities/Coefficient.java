package entities;

public class Coefficient {
    private int id;
    private String date;
    private int horseId;
    private String horseName;
    private double coefficient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horse_id) {
        this.horseId = horse_id;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return "Coefficient{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", horse_id=" + horseId +
                ", coefficient=" + coefficient +
                '}';
    }
}

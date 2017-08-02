package entities;

public class BetState {
    private int id;
    private String state;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BetState{" +
                "id=" + id +
                ", state='" + state + '\'' +
                '}';
    }
}

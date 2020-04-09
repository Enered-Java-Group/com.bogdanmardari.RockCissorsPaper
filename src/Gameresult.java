import java.io.Serializable;

public class Gameresult implements Serializable {
    String name;
    private int wins=0;
    private int draws=0;
    private int defeats=0;



    public void setName(String name) {
        this.name = name;
    }



    public Gameresult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getDefeat() {
        return defeats;
    }
    public void addVictory( int win){
        this.wins+=win;
    }
    public void addDraw(int draw){
        this.draws+=draw;
    }
    public void addDefeat(int defeat){
        this.defeats+=defeat;
    }
}

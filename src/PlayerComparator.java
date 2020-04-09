import java.util.Comparator;

public class PlayerComparator implements Comparator<Gameresult> {
    @Override
    public int compare(Gameresult o1, Gameresult o2) {
        return o2.getWins()-o1.getWins();
    }
}

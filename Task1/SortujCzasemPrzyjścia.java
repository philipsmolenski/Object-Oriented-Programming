import java.util.Comparator;

public class SortujCzasemPrzyjścia implements Comparator<Proces> {
    public int compare (Proces a, Proces b) {
        if (a.getCzasPrzyjścia() == b.getCzasPrzyjścia())
            return a.getId() - b.getId();

        if (a.getCzasPrzyjścia() > b.getCzasPrzyjścia())
            return 1;

        return -1;
    }
}

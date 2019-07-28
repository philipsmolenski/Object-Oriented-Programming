import java.util.Comparator;

public class SortujCzasemZakończenia implements Comparator<Proces> {
    public int compare (Proces a, Proces b) {
        if (a.getOstatnieOdebranieProcesora() == b.getOstatnieOdebranieProcesora())
            return a.getId() - b.getId();

        if (a.getOstatnieOdebranieProcesora() > b.getOstatnieOdebranieProcesora())
            return 1;

        return -1;
    }
}

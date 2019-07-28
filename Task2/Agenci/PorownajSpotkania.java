package Agenci;

import java.util.Comparator;

public class PorownajSpotkania implements Comparator<Spotkanie> {
    public int compare(Spotkanie a, Spotkanie b) {
        return a.getDzien() - b.getDzien();
    }
}

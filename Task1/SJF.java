import java.util.Arrays;

public class SJF extends StrategieNaZapotrzebowanie {
    public SJF() {
        super();
        this.name = "SJF";
    }

    @Override
    public Proces[] symulacjaStrategii(Proces[] procesy) {
        int n = procesy.length;
        Proces[] wynik = new Proces[n];
        Arrays.sort(procesy, new SortujCzasemPrzyjścia());
        int i = 0;

        while (i < n) {
            int bestidx = wybierzProces(procesy);
            wykonaj(procesy[bestidx]);
            wynik[i] = procesy[bestidx];
            i++;
        }

        return wynik;
    }
}

import java.util.Arrays;

public class FCFS extends Strategia {
    public FCFS () {
        super();
        this.name = "FCFS";
    }

    @Override
    public Proces[] symulacjaStrategii(Proces[] procesy) {
        int n = procesy.length;
        Proces[] wynik = new Proces[n];
        Arrays.sort(procesy, new SortujCzasemPrzyjścia());
        int i = 0;

        while (i < n) {
            if (czas < procesy[i].getCzasPrzyjścia())
                czas = procesy[i].getCzasPrzyjścia();

            wykonaj(procesy[i]);
            wynik[i] = procesy[i];
            i++;
        }

        return wynik;
    }
}

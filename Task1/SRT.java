import java.util.Arrays;

public class SRT extends StrategieNaZapotrzebowanie {
    public SRT() {
        super();
        this.name = "SRT";
    }

    @Override
    public Proces[] symulacjaStrategii(Proces[] procesy) {
        int n = procesy.length;
        Proces[] wynik = new Proces[n];
        Arrays.sort(procesy, new SortujCzasemPrzyjścia());
        int i = 0;
        int k = 0;

        while (i < n) {
            int bestidx = wybierzProces(procesy);
            podłącz(procesy[bestidx]);

            while (k < n) {
                if(procesy[k].getCzasPrzyjścia() < czas)
                    k++;

                else {
                    double t = procesy[k].getCzasPrzyjścia() - czas;

                    if (t >= procesy[bestidx].getZapotrzebowanie())
                        break;

                    wykonuj(procesy[bestidx], t);

                    if (procesy[k].getZapotrzebowanie() < procesy[bestidx].getZapotrzebowanie()) {
                        odłącz(procesy[bestidx]);
                        bestidx = k;
                        podłącz(procesy[bestidx]);
                    }

                    k++;
                }
            }

            wykonajPodłączony(procesy[bestidx]);
            wynik[i] = procesy[bestidx];
            i++;
        }

        return wynik;
    }
}

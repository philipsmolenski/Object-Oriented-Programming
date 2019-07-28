import java.util.ArrayDeque;
import java.util.Arrays;

public class RR extends Strategia {
    private double q;

    public RR (int q) {
        super();
        this.q = (double)q;
        this.name = "RR-" + q;
    }

    @Override
    public Proces[] symulacjaStrategii(Proces[] procesy) {
        int n = procesy.length;
        Proces[] wynik = new Proces[n];
        Arrays.sort(procesy, new SortujCzasemPrzyjścia());
        ArrayDeque<Proces> aq = new ArrayDeque<Proces>(n);
        int i = 0;
        int k = 0;

        while (i < n) {
            if (aq.size() == 0) {
                czas = procesy[k].getCzasPrzyjścia();
                aq.addLast(procesy[k]);
                k++;
            }

            else {
                Proces p = aq.pop();
                podłącz(p);

                if (p.getZapotrzebowanie() <= q) {
                    wykonajPodłączony(p);

                    while (k < n && procesy[k].getCzasPrzyjścia() <= czas) {
                        aq.addLast(procesy[k]);
                        k++;
                    }

                    wynik[i] = p;
                    i++;
                }

                else {
                    wykonuj(p, q);
                    odłącz(p);

                    while (k < n && procesy[k].getCzasPrzyjścia() < czas) {
                        aq.addLast(procesy[k]);
                        k++;
                    }

                    aq.addLast(p);

                    while (k < n && procesy[k].getCzasPrzyjścia() == czas) {
                        aq.addLast(procesy[k]);
                        k++;
                    }
                }
            }
        }
        return wynik;
    }
}

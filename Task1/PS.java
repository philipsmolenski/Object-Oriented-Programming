import java.util.Arrays;

public class PS extends Strategia {
    public PS () {
        super();
        this.name = "PS";
    }

    @Override
    public Proces[] symulacjaStrategii(Proces[] procesy) {
        int n = procesy.length;
        Proces[] wynik = new Proces[n];
        Arrays.sort(procesy, new SortujCzasemPrzyjścia());
        int i = 0;
        int k = 0;
        int wykonywane = 0;

        while (i < n) {
            if (wykonywane == 0) {
                czas = procesy[k].getCzasPrzyjścia();
                podłącz(procesy[k]);
                wykonywane ++;
                k++;
            }

            else {
                double minZapotrzebowanie = -1;

                for (int j = 0; j < n; j++) {
                    if (procesy[j].isPodłączony() && minZapotrzebowanie == -1)
                        minZapotrzebowanie = procesy[j].getZapotrzebowanie();

                    else if (procesy[j].isPodłączony() && procesy[j].getZapotrzebowanie() < minZapotrzebowanie)
                        minZapotrzebowanie = procesy[j].getZapotrzebowanie();
                }

                if (k < n && procesy[k].getCzasPrzyjścia() - czas < minZapotrzebowanie * wykonywane) {
                    double t = procesy[k].getCzasPrzyjścia() - czas;

                    for (int j = 0; j < n; j++) {
                        if (procesy[j].isPodłączony())
                            procesy[j].setZapotrzebowanie(procesy[j].getZapotrzebowanie() - t/wykonywane);
                    }

                    czas += t;
                    podłącz(procesy[k]);
                    wykonywane++;
                    k++;
                }

                else {
                    czas += minZapotrzebowanie * wykonywane;
                    for (int j = 0; j < n; j++) {
                        if (procesy[j].isPodłączony()) {
                            procesy[j].setZapotrzebowanie(procesy[j].getZapotrzebowanie() - minZapotrzebowanie);

                            if (procesy[j].getZapotrzebowanie() == 0) {
                                odłącz(procesy[j]);
                                wynik[i] = procesy[j];
                                i++;
                                wykonywane--;
                            }
                        }
                    }
                }
            }
        }
        Arrays.sort(wynik, new SortujCzasemZakończenia());
        return wynik;
    }
}

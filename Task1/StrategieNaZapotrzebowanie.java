public abstract class StrategieNaZapotrzebowanie extends Strategia {
    public StrategieNaZapotrzebowanie() {
        super();
    }

    public abstract Proces[] symulacjaStrategii(Proces[] procesy);

    protected int wybierzProces (Proces[] procesy) {
        int n = procesy.length;
        for (int j = 0; ; j++) {
            if (procesy[j].getZapotrzebowanie() != 0) {
                if (czas < procesy[j].getCzasPrzyjścia())
                    czas = procesy[j].getCzasPrzyjścia();
                break;
            }
        }

        int bestidx = 0;
        double bestZapotrzebowanie = 0;
        int j = 0;

        while (j < n && procesy[j].getCzasPrzyjścia() <= czas) {
            if (bestZapotrzebowanie == 0 && procesy[j].getZapotrzebowanie() != 0) {
                bestidx = j;
                bestZapotrzebowanie = procesy[j].getZapotrzebowanie();
            }

            else if (procesy[j].getZapotrzebowanie() != 0 && procesy[j].getZapotrzebowanie() < bestZapotrzebowanie) {
                bestidx = j;
                bestZapotrzebowanie = procesy[j].getZapotrzebowanie();
            }

            else if (procesy[j].getZapotrzebowanie() != 0 && procesy[j].getZapotrzebowanie() == bestZapotrzebowanie) {
                if (procesy[j].getId() < procesy[bestidx].getId())
                    bestidx = j;
            }

            j++;
        }

        return bestidx;
    }
}

public abstract class Strategia {
    protected double czas;

    protected String name;

    public String getName() {
        return name;
    }

    protected abstract Proces[] symulacjaStrategii(Proces[] procesy);

    public Strategia () {
        this.czas = 0;
    }

    public void podłącz (Proces p) {
        p.podłącz();
        double x = czas - p.getOstatnieOdebranieProcesora() + p.getCzasOczekiwania();
        p.setCzasOczekiwania(x);
    }

    public void wykonuj (Proces p, double t) {
        czas += t;
        p.setZapotrzebowanie(p.getZapotrzebowanie() - t);
    }


    public void odłącz (Proces p) {
        p.setOstatnieOdebranieProcesora(czas);
        p.odłącz();
    }

    public void wykonajPodłączony (Proces p) {
        wykonuj(p, p.getZapotrzebowanie());
        odłącz(p);
    }

    public void wykonaj (Proces p) {
        podłącz(p);
        wykonajPodłączony (p);
    }
}

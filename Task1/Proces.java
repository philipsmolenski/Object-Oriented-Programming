public class Proces {
    private int id;
    private double czasPrzyjścia;
    private double zapotrzebowanie;
    private double stareZapotrzebowanie;
    private double ostatnieOdebranieProcesora;
    private double stareOstatnieOdebranieProcesora;
    private double czasOczekiwania;
    private double staryCzasOczekiwania;
    private boolean podłączony;

    public Proces (int id, int czasPrzyjścia, int zapotrzebowanie) {
        this.id = id;
        this.czasPrzyjścia = (double)czasPrzyjścia;
        this.zapotrzebowanie = (double)zapotrzebowanie;
        this.stareZapotrzebowanie = (double)zapotrzebowanie;
        this.ostatnieOdebranieProcesora = czasPrzyjścia; //ułatwia to dalsze obliczenia
        this.stareOstatnieOdebranieProcesora = czasPrzyjścia;
        this.czasOczekiwania = 0;
        this.staryCzasOczekiwania = 0;
        this.podłączony = false;
    }

    public void przywróćWartości() {
        this.zapotrzebowanie = this.stareZapotrzebowanie;
        this.ostatnieOdebranieProcesora = this.stareOstatnieOdebranieProcesora;
        this.czasOczekiwania = this.staryCzasOczekiwania;
    }

    public int getId() {
        return this.id;
    }

    public double getCzasPrzyjścia() {
        return this.czasPrzyjścia;
    }

    public double getZapotrzebowanie() {
        return this.zapotrzebowanie;
    }

    public void setZapotrzebowanie(double x) {
        this.zapotrzebowanie = x;
    }

    public double getOstatnieOdebranieProcesora() {
        return this.ostatnieOdebranieProcesora;
    }

    public void setOstatnieOdebranieProcesora(double x) {
        this.ostatnieOdebranieProcesora = x;
    }

    public double getCzasOczekiwania() {
        return this.czasOczekiwania;
    }

    public void setCzasOczekiwania(double x) {
        this.czasOczekiwania = x;
    }

    public boolean isPodłączony() {
        return podłączony;
    }

    public void podłącz() {
        podłączony = true;
    }

    public void odłącz() {
        podłączony = false;
    }

}
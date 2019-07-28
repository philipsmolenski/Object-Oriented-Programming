package Agenci;

public class Spotkanie {
    private int dzien;
    private Agent agent;
    public Spotkanie (int dzien, Agent agent) {
        this.dzien = dzien;
        this.agent = agent;
    }

    public int getDzien() {
        return this.dzien;
    }

    public Agent getAgent() {
        return this.agent;
    }
}

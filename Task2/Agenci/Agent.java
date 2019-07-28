package Agenci;

import Choroba.Choroba;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent {
    protected ArrayList<Agent> znajomi;
    // Spotkania trzymamy na kolejce priorytetowej aby
    // mieć szybki dostęp do najbliższego spotkania.
    protected PriorityQueue<Spotkanie> spotkania;
    protected Stan stan;
    protected int id;

    public Agent (int id) {
        znajomi = new ArrayList<>();
        spotkania = new PriorityQueue<>(new PorownajSpotkania());
        stan = Stan.zdrowy;
        this.id = id;
    }

    public abstract Agent losujZnajomego(Random r);

    public abstract boolean losujCzySpotykasz(double prawdSpotkania, Random r);

    public Stan getStan () {
        return this.stan;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Agent> getZnajomi() {
        return this.znajomi;
    }

    public void dodajZnajomego(Agent agent) {
        znajomi.add(agent);
    }

    private void usuńZnajomego(Agent agent) {
        int n = znajomi.size();

        for (int i = n - 1; i >= 0; i--)
            if (znajomi.get(i) == agent)
                znajomi.remove(i);
    }

    // Szuka Agenta agent na liście znajomych i zwraca true,
    // gdy agent zostanie znaleziony oraz false w przeciwnym przypadku.
    public boolean szukajZnajomego (Agent agent) {
        int n = znajomi.size();

        for (int i = 0; i < n; i++)
            if (znajomi.get(i) == agent)
                return true;

        return false;
    }

    private int losujDzień (int dziś, int koniec, Random r) {
        int n = r.nextInt(koniec - dziś);
        return dziś + n + 1;
    }

    // Gdy agent umiera zmieniamy jego stan na martwy i
    // wyrzucamy go ze znajomych pozostałych agentów w populacji.
    public void umrzyj () {
        stan = Stan.martwy;
        int n = znajomi.size();

        for (int i = 0; i < n; i++) {
            Agent a = znajomi.get(i);
            a.usuńZnajomego(this);
        }
    }

    public void zachoruj () {
        stan = Stan.zarażony;
    }

    public void wyzdrowiej() {
        stan = Stan.odporny;
    }

    // Losuje spotkania tak długo, aż agent wylosuje, że nie chce się już spotykać
    public void losujSpotkania (int dziś, int koniec, Random r, double prawdSpotkania) {
        boolean b = losujCzySpotykasz(prawdSpotkania, r);

        while (b) {
            Agent agent = losujZnajomego(r);
            int dzień = losujDzień(dziś, koniec, r);
            Spotkanie s = new Spotkanie(dzień, agent);
            spotkania.add(s);
            b = losujCzySpotykasz(prawdSpotkania, r);
        }
    }

    // Przeprowadza pojedyncze spotkania
    private void spotkaj (Agent agent, Choroba choroba, Random r) {
        if (this.stan == Stan.zarażony)
            choroba.spróbujZarazić(agent, r);

        else if (agent.getStan() == Stan.zarażony)
            choroba.spróbujZarazić(this, r);
    }

    // Przeprowadza wszystkie spotkania zaplanowane na dany dzień.
    public void przeprowadźSpotkania(int dziś, Choroba choroba, Random r) {
        Spotkanie s = spotkania.peek();

        while (s != null && s.getDzien() == dziś) {
            spotkaj(s.getAgent(), choroba, r);
            spotkania.poll();
            s = spotkania.peek();
        }
    }
}

package Populacja;

import Agenci.Agent;
import Agenci.LosujTypAgenta;
import Agenci.Stan;
import Choroba.Choroba;
import java.util.Random;

public class Graf {
    private Random random;
    private int liczbaDni;
    private double prawdSpotkania;
    private int śrZnajomych;
    private Agent[] populacja;

    public Graf (int seed, int liczbaDni, double prawdSpotkania, int śrZnajomych, int liczbaAgentów, LosujTypAgenta los) {
        this.random = new Random(seed);
        this.liczbaDni = liczbaDni;
        this.prawdSpotkania = prawdSpotkania;
        this.śrZnajomych = śrZnajomych;
        populacja = new Agent[liczbaAgentów];
        for (int i = 0; i < liczbaAgentów; i++)
            populacja[i] = los.losujAgenta(random, i + 1);
    }

    public Agent[] getPopulacja() {
        return populacja;
    }

    public int getLiczbaDni() {
        return liczbaDni;
    }

    public void losujGraf() {
        int znajomości = 0;
        while (znajomości < (śrZnajomych * populacja.length)/2) {
            int a = random.nextInt(populacja.length);
            int b = random.nextInt(populacja.length);

            if (a != b && !populacja[a].szukajZnajomego(populacja[b])) {
                populacja[a].dodajZnajomego(populacja[b]);
                populacja[b].dodajZnajomego(populacja[a]);
                znajomości++;
            }
        }
    }

    public void losujZarażonego() {
        int a = random.nextInt(populacja.length);
        populacja[a].zachoruj();
    }

    private int policzLudziZeStanem (Stan x) {
        int wynik = 0;
        for (int i = 0; i < populacja.length; i++)
            if (populacja[i].getStan() == x)
                wynik++;

        return wynik;
    }

    public int policzZdrowych() {
        return policzLudziZeStanem(Stan.zdrowy);
    }

    public int policzZarażonych() {
        return policzLudziZeStanem(Stan.zarażony);
    }

    public int policzOdpornych() {
        return policzLudziZeStanem(Stan.odporny);
    }

    public void symulujDzień(Choroba choroba, int dziś) {
        for (int i = 0; i < populacja.length; i++) {
            choroba.spróbujZabić(populacja[i], random);
            choroba.spróbujWyzdrowieć(populacja[i], random);
        }

        // ostatniego dnia nie losujemy spotkań (bo nie ma na kiedy ich zaplanować)
        if (dziś != liczbaDni) {
            for (int i = 0; i < populacja.length; i++)
                populacja[i].losujSpotkania(dziś, liczbaDni, random, prawdSpotkania);

            for (int i = 0; i < populacja.length; i++)
                populacja[i].przeprowadźSpotkania(dziś, choroba, random);
        }
    }

}

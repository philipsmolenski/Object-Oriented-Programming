package Choroba;
import Agenci.Agent;
import Agenci.Stan;

import java.util.Random;

public class Choroba {
    private double prawdZarażenia;
    private double prawdWyzdrowienia;
    private double śmiertelność;

    public Choroba (double prawdZarażenia, double prawdWyzdrowienia, double śmiertelność) {
        this.prawdZarażenia = prawdZarażenia;
        this.prawdWyzdrowienia = prawdWyzdrowienia;
        this.śmiertelność = śmiertelność;
    }

    public void spróbujZarazić(Agent agent, Random r) {
        // Zarazić można tylko zdrowego agenta.
        if (agent.getStan() != Stan.zdrowy)
            return;

        double d = r.nextDouble();
        if (d < prawdZarażenia)
            agent.zachoruj();
    }

    public void spróbujWyzdrowieć(Agent agent, Random r) {
        if (agent.getStan() != Stan.zarażony)
            return;

        double d = r.nextDouble();
        if (d < prawdWyzdrowienia)
            agent.wyzdrowiej();
    }

    public void spróbujZabić(Agent agent, Random r) {
        if (agent.getStan() != Stan.zarażony)
            return;

        double d = r.nextDouble();
        if (d < śmiertelność)
            agent.umrzyj();
    }
}

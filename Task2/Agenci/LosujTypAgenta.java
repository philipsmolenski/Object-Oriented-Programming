package Agenci;
import java.util.Random;

public class LosujTypAgenta {
    private double prawdTowarzyski;
    public LosujTypAgenta (double prawdTowarzyski) {
        this.prawdTowarzyski = prawdTowarzyski;
    }

    public Agent losujAgenta (Random r, int id) {
        double d = r.nextDouble();

        if (d < prawdTowarzyski)
            return new AgentTowarzyski(id);

        else return new AgentZwykly(id);
    }
}

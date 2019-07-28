package Agenci;
import java.util.Random;

public class AgentZwykly extends Agent{

    public AgentZwykly(int id) {
        super(id);
    }

    @Override
    public boolean losujCzySpotykasz(double prawdSpotkania, Random r) {
        // Agent, który jest martwy lub nie ma znajomych nie chce sie spotykać
        if (this.stan == Stan.martwy || this.znajomi.size() == 0)
            return false;

        double d = r.nextDouble();
        // Agent zarażony spotyka się z 2 razy
        // mniejszym prawdopodobieństwem.
        if (this.stan == Stan.zarażony)
            d *= 2;

        return (d < prawdSpotkania);
    }

    @Override
    public Agent losujZnajomego(Random r) {
        int n = znajomi.size();
        int k = r.nextInt(n);

        return znajomi.get(k);
    }
}

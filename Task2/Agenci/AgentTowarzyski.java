package Agenci;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AgentTowarzyski extends Agent {

    public AgentTowarzyski (int id) {
        super(id);
    }

    @Override
    public boolean losujCzySpotykasz(double prawdSpotkania, Random r) {
        // Gdy agent nie ma znajomych lub jest martwy nie chce się spotykać
        if (this.stan == Stan.martwy || this.znajomi.size() == 0)
            return false;

        double d = r.nextDouble();

        return (d < prawdSpotkania);
    }

    @Override
    public Agent losujZnajomego(Random r) {
        int n = znajomi.size();

        // Gdy agent towarzyski jest zarażony wybieramy ze znajomych
        if (this.stan == Stan.zarażony) {
            int k = r.nextInt(n);
            return znajomi.get(k);
        }

        // W przeciwnym przypadku wybieramy ze znajomych i znajomych znajomych.
        ArrayList<Agent> lista = new ArrayList<>();

        // Zbieramy znajomych i znajomych znajomych do listy.
        for (int i = 0; i < n; i++) {
            Agent a = znajomi.get(i);
            lista.add(a);
            for (int j = 0; j < a.getZnajomi().size(); j++) {
                Agent b = a.getZnajomi().get(j);
                if (b != this)
                    lista.add(b);
            }
        }

        // Sortujemy listę...
        Collections.sort(lista, new PorownajAgentow());

        // ... i usuwamy powtórzenia.
        for (int i = lista.size() - 1; i > 0; i--)
            if (lista.get(i) == lista.get(i - 1))
                lista.remove(i);

        int s = lista.size();
        int k = r.nextInt(s);
        return lista.get(k);
    }
}
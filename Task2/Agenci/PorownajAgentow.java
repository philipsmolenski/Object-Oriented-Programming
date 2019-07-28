package Agenci;
import java.util.Comparator;

public class PorownajAgentow implements Comparator<Agent> {
    public int compare(Agent a, Agent b) {
        return a.getId() - b.getId();
    }
}

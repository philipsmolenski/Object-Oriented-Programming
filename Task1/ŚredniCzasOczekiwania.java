public class ŚredniCzasOczekiwania extends KryteriumPorównawcze {
    public double policz(Proces[] procesy) {
        int n = procesy.length;
        double suma = 0;

        for (int i = 0; i < n; i++)
            suma += procesy[i].getCzasOczekiwania();

        return suma/n;
    }

}

public class SredniCzasObrotu extends KryteriumPorównawcze {
    public double policz(Proces procesy[]) {
        int n = procesy.length;
        double suma = 0;

        for (int i = 0; i < n; i++) {
            double o = procesy[i].getOstatnieOdebranieProcesora();
            double cp = procesy[i].getCzasPrzyjścia();
            suma += o - cp;
        }

        return suma/n;
    }
}

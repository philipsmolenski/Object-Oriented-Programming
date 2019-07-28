import java.text.DecimalFormat;

public class Planista {
    public void przeprowadźSymulację(String args[]) {
        Input in = new Input();

        try {
            in.openfile(args);
            Proces[] procesy = in.readProcesy();
            Strategia[] strategie = in.readStrategie();
            in.closefile();
            KryteriumPorównawcze scob = new SredniCzasObrotu();
            KryteriumPorównawcze scocz = new ŚredniCzasOczekiwania();
            DecimalFormat df = new DecimalFormat("0.00");
            DecimalFormat df2 = new DecimalFormat("0");

            for (int i = 0; i < strategie.length; i++) {
                Proces[] wynik = strategie[i].symulacjaStrategii(procesy);
                double czasobrotu = scob.policz(wynik);
                double czasoczekiwania = scocz.policz(wynik);
                System.out.println("Strategia: " + strategie[i].getName());

                for (int j = 0; j < wynik.length; j++) {
                    System.out.print("[" + wynik[j].getId() + " ");
                    System.out.print(df2.format(wynik[j].getCzasPrzyjścia()) + " ");
                    System.out.print(df.format(wynik[j].getOstatnieOdebranieProcesora()) + "]");
                }

                System.out.println("");
                System.out.println("Średni czas obrotu: " + df.format(czasobrotu));
                System.out.println("Średni czas oczekiwania: " + df.format(czasoczekiwania));
                System.out.println("");

                for (int j = 0; j < procesy.length; j++)
                    procesy[j].przywróćWartości();
            }
        }catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        Planista planista = new Planista();
        planista.przeprowadźSymulację(args);
    }
}

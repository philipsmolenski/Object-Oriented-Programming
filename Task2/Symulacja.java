import Choroba.Choroba;
import Populacja.Graf;
import WejscieWyjscie.MojWyjatek;
import WejscieWyjscie.WejscieWyjscie;

public class Symulacja {

    public void symulacjaEpidemii() {
        try {
            WejscieWyjscie wej = new WejscieWyjscie();
            wej.załadujDefault();
            wej.załadujXML();
            wej.załadujPlikZRaportem();
            wej.wypiszParametry();
            Graf graf = wej.wczytajGraf();
            Choroba choroba = wej.wczytajChorobę();
            graf.losujGraf();
            graf.losujZarażonego();
            wej.wypiszGraf(graf);
            wej.wypiszNagłówek();
            int liczbaDni = graf.getLiczbaDni();
            // Wypisujemy raport sprzed rozpoczęcia symulacji.
            int zdrowi = graf.policzZdrowych();
            int zarażeni = graf.policzZarażonych();
            int odporni = graf.policzOdpornych();
            wej.wypiszRaportZDnia(zdrowi, zarażeni, odporni);

            // Wypisujemy raport z kolejnych dni symulacji.
            for (int dziś = 1; dziś <= liczbaDni; dziś++) {
                graf.symulujDzień(choroba, dziś);
                zdrowi = graf.policzZdrowych();
                zarażeni = graf.policzZarażonych();
                odporni = graf.policzOdpornych();
                wej.wypiszRaportZDnia(zdrowi, zarażeni, odporni);
            }

            wej.skończWpisywanie();

        }catch (MojWyjatek m) {
            System.out.println(m.getMessage());
        }

    }

    public static void main (String args[]) {
        Symulacja s = new Symulacja();
        s.symulacjaEpidemii();
    }
}

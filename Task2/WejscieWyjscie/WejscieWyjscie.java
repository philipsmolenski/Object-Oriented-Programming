package WejscieWyjscie;
import Agenci.Agent;
import Agenci.AgentZwykly;
import Agenci.LosujTypAgenta;
import Agenci.Stan;
import Populacja.Graf;
import Choroba.Choroba;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;


public class WejscieWyjscie {
    private Properties wartościDomyślne;
    private Properties plikXML;
    private PrintWriter writer;

    // Ustawia plik z wartościami domyślnymi jako odpowiedni atrybut klasy
    public void załadujDefault() throws MojWyjatek{
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String domyślne = rootPath + "default.properties";

        Properties wartościDomyślne = new Properties();
        try {
            FileInputStream input = new FileInputStream(new File(domyślne));
            wartościDomyślne.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        }catch (FileNotFoundException e) {
            throw new MojWyjatek ("Brak pliku default.properties");
        }catch (IOException e) {
            throw new MojWyjatek("default.properties nie jest tekstowy");
        }

        this.wartościDomyślne = wartościDomyślne;
    }

    // Ustawia plik XML jako odpowiedni atrybut klasy.
    public void załadujXML() throws MojWyjatek {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String xml = rootPath + "simulation-conf.xml";
        Properties plikXML = new Properties();
        try {
            plikXML.loadFromXML(new FileInputStream(xml));
        }catch (FileNotFoundException e) {
            throw new MojWyjatek ("Brak pliku simulation-conf.xml");
        }catch (IOException e) {
            throw new MojWyjatek("simulation-conf.xml jest XML");
        }

        this.plikXML = plikXML;
    }

    private int parsujInt (String wartość, String klucz, String plik) throws MojWyjatek {
        try {
            Integer.parseInt(wartość);
        }catch (NumberFormatException e) {
            throw new MojWyjatek("Niedozwolona wrtość " + wartość + " dla klucza " + klucz + " w pliku " + plik);
        }

        int n = Integer.parseInt(wartość);

        if (n < 0)
            throw new MojWyjatek("Niedozwolona wrtość " + wartość + " dla klucza " + klucz + " w pliku " + plik);

        return n;
    }

    private double parsujDouble (String wartość, String klucz, String plik) throws MojWyjatek {
        try {
            Double.parseDouble(wartość);
        }catch (NumberFormatException e) {
            throw new MojWyjatek("Niedozwolona wrtość " + wartość + " dla klucza " + klucz + " w pliku " + plik);
        }

        double d = Double.parseDouble(wartość);

        if (d < 0 || d > 1)
            throw new MojWyjatek("Niedozwolona wrtość " + wartość + " dla klucza " + klucz + " w pliku " + plik);

        return d;
    }

    // wczytuje domyślną wartość inta.
    private int domyślnyInt (String s) throws MojWyjatek {
        String liczba = wartościDomyślne.getProperty(s);
        if (liczba == null)
            return -1;

        return parsujInt(liczba, s, "default.properties");
    }

    // wczytuje domyślną wartość doublea.
    private double domyślnyDouble (String s) throws  MojWyjatek {
        String liczba = wartościDomyślne.getProperty(s);
        if (liczba == null)
            return -1;

        return parsujDouble(liczba, s , "default.properties");
    }

    // wczytuje wartość inta z pliku XML
    private int XMLint (String s) throws MojWyjatek {
        String liczba = plikXML.getProperty(s);
        if (liczba == null)
            return -1;

        return parsujInt(liczba, s, "simulation-conf.xml");
    }

    // wczytuje wartość doublea z pliku XML.
    private double XMLdouble (String s) throws MojWyjatek {
        String liczba = plikXML.getProperty(s);
        if (liczba == null)
            return -1;

        return parsujInt(liczba, s, "simulation-conf.xml");
    }

    // wczytuje wartość inta z XML lub ustawia wartość
    // domyślną, gdy w XML nie ma podanej wartości.
    // zgłasza wyjątek gdy wartość nie występuje w żadnym z plików.
    private int wczytajInt (String s) throws MojWyjatek {
        int a = domyślnyInt(s);
        int b = XMLint(s);

        if (b == -1 && a == -1)
            throw new MojWyjatek("Brak wartości dla klucza " + s);

        if (b != -1)
            return b;
        return a;
    }

    // wczytuje wartość doublea z XML lub ustawia wartość
    // domyślną, gdy w XML nie ma podanej wartości.
    private double wczytajDouble (String s) throws MojWyjatek {
        double a = domyślnyDouble(s);
        double b = XMLdouble(s);

        if (b == -1 && a == -1)
            throw new MojWyjatek("Brak wartości dla klucza " + s);

        if (b != -1)
            return b;
        return a;
    }

    private int wczytajLiczbęAgentów() throws MojWyjatek {
        int a = wczytajInt("liczbaAgentów");

        if (a > 1000000)
            throw new MojWyjatek("Liczba agentów nie może przekraczać 1000000");

        if (a == 0)
            throw new MojWyjatek("Liczba agentów nie może wynosić 0.");

        return a;
    }

    private int wczytajLiczbęDni() throws MojWyjatek {
        int a = wczytajInt("liczbaDni");

        if (a > 1000)
            throw new MojWyjatek("Liczba dni nie może przekroczyć 1000.");

        if (a == 0)
            throw new MojWyjatek("Liczba dni nie może wynosić 0.");

        return a;
    }

    private int wczytajŚrZnajomych () throws MojWyjatek {
        int a = wczytajInt("śrZnajomych");
        int b = wczytajLiczbęAgentów();

        if (a > b - 1)
            throw new MojWyjatek("Średnia liczba znajomych nie może przekraczać liczby agentów - 1");

        return a;
    }

    public Choroba wczytajChorobę() throws MojWyjatek {
        double prawdZarażenia = wczytajDouble("prawdZarażenia");
        double prawdWyzdrowienia = wczytajDouble("prawdWyzdrowienia");
        double śmiertelność = wczytajDouble("śmiertelność");
        return new Choroba(prawdZarażenia, prawdWyzdrowienia, śmiertelność);
    }

    private String wczytajPlikZRaportem() throws MojWyjatek {
        String plik = wartościDomyślne.getProperty("plikZRaportem");
        String plik2 = plikXML.getProperty("plikZRaportem");
        if (plik == null && plik2 == null) {
            throw new MojWyjatek("Brak wartości dla klucza plikZRaportem");
        }

        if (plik2 == null)
            return plik;

        return plik2;
    }

    private LosujTypAgenta wczytajLosowanieAgenta() throws MojWyjatek {
        double prawdTowarzyski = wczytajDouble("prawdTowarzyski");
        return new LosujTypAgenta(prawdTowarzyski);
    }

    public Graf wczytajGraf() throws MojWyjatek {
        int seed = wczytajInt("seed");
        int liczbaDni = wczytajLiczbęDni();
        double prawdSpotkania = wczytajDouble("prawdSpotkania");
        int śrZnajomych = wczytajŚrZnajomych();
        int liczbaAgentów = wczytajLiczbęAgentów();
        LosujTypAgenta los = wczytajLosowanieAgenta();

        return new Graf (seed, liczbaDni, prawdSpotkania, śrZnajomych, liczbaAgentów, los);
    }

    // Ustawia plik z raportem jako odpowiedni artybut klasy.
    public void załadujPlikZRaportem() throws MojWyjatek {
        String s = wczytajPlikZRaportem();
        try {
            FileWriter f = new FileWriter(s);
            PrintWriter writer = new PrintWriter(f);
            this.writer = writer;
        } catch (IOException e) {
            throw new MojWyjatek("Nie udało się załadować pliku z raportem");
        }
    }

    public void skończWpisywanie () {
        writer.close();
    }

    public void wypiszParametry() throws MojWyjatek {
        DecimalFormat df = new DecimalFormat("0.####");

        writer.print("# początkowe parametry:\n");
        writer.printf("seed=%d\n", wczytajInt("seed"));
        writer.printf("liczbaAgentów=%d\n", wczytajLiczbęAgentów());
        writer.print("prawdTowarzyski=" + df.format(wczytajDouble("prawdTowarzyski")) + "\n");
        writer.print("prawdSpotkania=" + df.format(wczytajDouble("prawdSpotkania")) + "\n");
        writer.print("prawdZarażenia=" + df.format(wczytajDouble("prawdZarażenia")) + "\n");
        writer.print("prawdWyzdrowienia=" + df.format(wczytajDouble("prawdWyzdrowienia")) +"\n");
        writer.print("śmiertelność=" + df.format(wczytajDouble("śmiertelność")) + "\n");
        writer.printf("liczbaDni=%d\n", wczytajLiczbęDni());
        writer.printf("śrZnajomych=%d\n", wczytajŚrZnajomych());
        writer.print("\n");
    }

    // Wypisuje każdego agenta wraz z jego typem, a następnie
    // wypisuje graf znajomości między agentami.
    public void wypiszGraf (Graf graf) {
        Agent[] agenci = graf.getPopulacja();
        writer.print("# agenci jako: id typ lub id* typ dla chorego\n");

        for (int i = 0; i < agenci.length; i++) {
            if (agenci[i].getStan() == Stan.zdrowy)
                writer.printf("%d ", agenci[i].getId());
            else
                writer.printf("%d* ", agenci[i].getId());

            if (agenci[i] instanceof AgentZwykly)
                writer.print("zwykły\n");
            else
                writer.print("towarzyski\n");
        }

        writer.print("\n");
        writer.print("# graf\n");

        for (int i = 0; i < agenci.length; i++) {
            writer.printf("%d", agenci[i].getId());
            ArrayList<Agent> znajomi = agenci[i].getZnajomi();

            for (int j = 0; j < znajomi.size(); j++) {
                Agent a = znajomi.get(j);
                writer.printf(" %d", a.getId());
            }

            writer.print("\n");
        }

        writer.printf("\n");
    }

    public void wypiszNagłówek() {
        writer.print("# liczebność w kolejnych dniach\n");
    }

    public void wypiszRaportZDnia(int zdrowi, int zarażeni, int uodpornieni) {
        writer.printf("%d %d %d\n", zdrowi, zarażeni, uodpornieni);
    }

}

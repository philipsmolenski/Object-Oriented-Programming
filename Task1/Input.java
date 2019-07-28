import java.io.File;
import java.util.Scanner;

public class Input {
    private Scanner scanner = new Scanner(System.in);
    private int wiersz;

    public Input () {
        this.wiersz = 1;
    }

    public void openfile(String args[]) throws MyException {
        if (args.length == 0)
            this.scanner = new Scanner(System.in);

        else {
            String s = args[0];
            File f = new File(s);

            if (!f.exists() || !f.isFile() || !f.canRead())
                throw new MyException ("Plik z danymi nie jest dostępny.");

            try {
                this.scanner = new Scanner(f);
            }catch (Exception e) {
                System.out.println("Plik z danymi nie jest dostępny.");
            }
        }
    }



    public boolean tylkoLiczby (String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ' && (c < '0' || c > '9'))
                return false;
        }

        return true;
    }

    public boolean sameSpacje(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c != ' ')
                return false;
        }

        return true;
    }

    public int[] wczytajLiczby (String s, int n, int w) throws MyException{
        int[] wynik = new int[n];
        if (sameSpacje(s))
            throw new MyException("Błąd w wierszu " + w + ": za mało danych.");

        if (!tylkoLiczby(s))
            throw new MyException("Błąd w wierszu " + w + ": podane wartości nie są liczbami całkowitymi.");

        if (s.charAt(0) == ' ' || s.charAt(s.length() - 1) == ' ')
            throw new MyException("Błąd w wierszu " + w + ": za dużo spacji.");

        for (int i = 0; i < s.length() - 1; i++)
            if (s.charAt(i) == ' ' && s.charAt(i + 1) == ' ')
                throw new MyException("Błąd w wierszu " + w + ": za dużo spacji.");

        int liczbaSpacji = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == ' ')
                liczbaSpacji ++;

        if (liczbaSpacji > n - 1)
            throw new MyException("Błąd w wierszu " + w + ": za dużo danych.");

        if (liczbaSpacji < n - 1)
            throw new MyException("Błąd w wierszu " + w + ": za mało danych.");

        int pocz = 0;
        int kon = 0;
        int i = 0;

        while (i < n - 1) {
            if (s.charAt(kon) != ' ')
                kon ++;

            else {
                String t = s.substring(pocz, kon);
                int a = Integer.parseInt(t);
                wynik[i] = a;
                pocz = kon + 1;
                kon ++;
                i ++;
            }
        }

        String t = s.substring(pocz, s.length());
        int a = Integer.parseInt(t);
        wynik[n - 1] = a;

        return wynik;
    }


    public Proces[] readProcesy() throws MyException{
        if (!scanner.hasNextLine())
            throw new MyException("Błąd w wierszu 1: za mało danych.");

        String s = scanner.nextLine();

        int[] tab = wczytajLiczby(s, 1, 1);

        int n = tab[0];

        Proces[] wynik = new Proces[n];
        wiersz ++;

        for ( ; wiersz < n + 2; wiersz ++) {
            if (!scanner.hasNextLine())
                throw new MyException("Błąd w wierszu " + wiersz + ": za mało danych.");

            s = scanner.nextLine();
            tab = wczytajLiczby(s, 2, wiersz);
            int a = tab[0];
            int b = tab[1];
            wynik[wiersz - 2] = new Proces(wiersz - 1, a, b);
        }

        return wynik;
    }

    public Strategia[] readStrategie() throws MyException {
        if (!scanner.hasNextLine())
            throw new MyException("Błąd w wierszu " + wiersz + ": za mało danych.");

        String s = scanner.nextLine();
        int tab[] = wczytajLiczby(s, 1, wiersz);
        int n = tab[0];
        Strategia[] wynik = new Strategia[n + 4];
        wynik[0] = new FCFS();
        wynik[1] = new SJF();
        wynik[2] = new SRT();
        wynik[3] = new PS();
        wiersz ++;

        if (n == 0) {
            if (scanner.hasNextLine())
                throw new MyException("Za dużo wierszy.");

            return wynik;
        }

        if (!scanner.hasNextLine())
            throw new MyException("Błąd w wierszu " + wiersz + ": za mało danych.");

        s = scanner.nextLine();
        tab = wczytajLiczby(s, n, wiersz);

        for (int i = 0; i < n; i++) {
            int q = tab[i];
            wynik[i + 4] = new RR(q);
        }

        if (scanner.hasNextLine())
            throw new MyException("Za dużo wierszy.");

        return wynik;
    }

    public void closefile() {
        scanner.close();
    }

}

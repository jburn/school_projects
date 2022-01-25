import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String args[])
    {
        // Alustetaan va-muuttujat ja Scanner-objekti käyttäjäinputtia varten
        String temp1, temp2, temp3;
        int tempint;
        float tempfloat;
        ArrayList<Asukas> asukaslista = new ArrayList<>();
        Scanner konsoli = new Scanner(System.in);

        // Otetaan käyttäjältä asukkaiden tiedot ja sijoitetaan muuttujiin
        System.out.print("Syota 1. asukkaan nimi: ");
        temp1 = konsoli.nextLine();
        System.out.print("Syota 1. asukkaan syntymaaika: ");
        temp2 = konsoli.nextLine();
        Asukas ekaAsukas = new Asukas(temp1, temp2);

        System.out.print("Syota 2. asukkaan nimi: ");
        temp1 = konsoli.nextLine();
        System.out.print("Syota 2. asukkaan syntymaaika: ");
        temp2 = konsoli.nextLine();
        Asukas tokaAsukas = new Asukas(temp1, temp2);

        // Lisätään asukkaat listaan
        asukaslista.add(ekaAsukas);
        asukaslista.add(tokaAsukas);
        
        // Otetaan pinta-ala käyttäjältä ja sijoitetaan muuttujaan
        System.out.print("Syota rakennuksen pinta-ala: ");
        tempfloat = konsoli.nextFloat();
        System.out.print("Syota rakennuksen huoneiden lukumaara: ");
        tempint = konsoli.nextInt();
        Rakennus testiRakennus = new Rakennus(tempfloat, tempint, asukaslista);

        konsoli.nextLine(); // Input bufferin tyhjennys

        // Otetaan tontin tiedot ja sijoitetaan muuttujaan
        System.out.print("Syota tontin nimi: ");
        temp1 = konsoli.nextLine();
        System.out.print("Syota tontin leveyskoordinaatti: ");
        temp2 = konsoli.nextLine();
        System.out.print("Syota tontin pituuskoordinaatti: ");
        temp3 = konsoli.nextLine();
        String[] koordinaattilista = {"leveys:"+temp2, "pituus:"+temp3};
        System.out.print("Syota tontin pinta-ala: ");
        tempfloat = konsoli.nextFloat();
        Tontti testiTontti = new Tontti(temp1, koordinaattilista, tempfloat, testiRakennus);

        konsoli.close(); // Suljetaan Scanner-objekti

        // Tulostetaan tiedot lopuksi
        System.out.println("\nTontin tiedot\n####################");
        System.out.println("Nimi: " + testiTontti.getNimi());
        System.out.println("Ala: " + testiTontti.getAla());
        System.out.println("Sijainti: " + testiTontti.getSijainti()[0]+ " " + testiTontti.getSijainti()[1]);

        System.out.print("\nRakennuksen tiedot\n####################\nHuoneet: " + testiTontti.getRakennus().getHuoneet());
        System.out.println("\nAla: " + testiTontti.getRakennus().getAla());
        System.out.print("\nAsukkaat\n####################\n");
        int pituus = testiTontti.getRakennus().getAsukkaat().size();
        for(int i=0;i<pituus;i++)
        {
            System.out.println("Nimi: " + testiTontti.getRakennus().getAsukkaat().get(i).getNimi());
            System.out.println("Syntymaaika: " + testiTontti.getRakennus().getAsukkaat().get(i).getSaika());
            System.out.println(" ");
        }
    }
}

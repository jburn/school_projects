import java.util.*;
public class Main {
    public static void main(String args[])
    {
        // Alustetaan va-muuttujat ja Scanner-objekti käyttäjäinputtia varten
        String temp1, temp2;
        int tempint, i, n, m, lenint;
        double tempdouble;
        Asukas tempAsukas = new Asukas("");
        ArrayList<Asukas> asukaslista = new ArrayList<Asukas>();
        ArrayList<Asunto> asuntolista = new ArrayList<Asunto>();
        ArrayList<Tontti> printList = new ArrayList<Tontti>();
        
        Scanner konsoli = new Scanner(System.in);

        // Otetaan käyttäjältä kerrostalon asukkaiden, sekä asuntojen tiedot ja sijoitetaan ne muuttujiin
        System.out.print("Syota kerrostalon asuntojen lukumaara: ");
        tempint = konsoli.nextInt();
        konsoli.nextLine(); // Tyhjennä bufferi

        System.out.println("Syota kerrostalon asukkaat asunnoittain");
        for(i=0; i<tempint; i++)
        {
            System.out.println("### Kerrostalon " + Integer.toString(i+1) + ". Asunto ###");
            for (n=0;n<4;n++)
            {
                System.out.print("Syota " + Integer.toString(n+1) +". asukkaan nimi: ");
                temp1 = konsoli.nextLine();
                tempAsukas = new Asukas(temp1);
                asukaslista.add(tempAsukas);
            }
            System.out.print("Asunnon pinta-ala: ");
            tempdouble = konsoli.nextDouble();
            konsoli.nextLine(); // Tyhjennä bufferi

            System.out.print("Asunnon huoneiden lukumaara: ");
            tempint = konsoli.nextInt();
            konsoli.nextLine(); // Tyhjennä bufferi

            asuntolista.add(new Asunto(asukaslista, tempdouble, tempint));
            asukaslista.clear();
        }
        System.out.print("Syota tontin nimi: ");
        temp1 = konsoli.nextLine();
        System.out.print("Syota tontin osoite: ");
        temp2 = konsoli.nextLine();
        System.out.print("Syota tontin pinta-ala: ");
        tempdouble = konsoli.nextDouble();
        konsoli.nextLine(); // Tyhjennä bufferi

        try
        {
            Tontti tempKerrostaloTontti = new Tontti(temp1, temp2, tempdouble, new Kerrostalo(asuntolista.size(), asuntolista));
            printList.add(tempKerrostaloTontti);
        }
        catch (Exception e)
        {
            System.out.println("Arvo ei voi olla negatiivinen");
        }
        asuntolista.clear();

        // Otetaan käyttäjältä rivitalon asukkaiden, sekä asuntojen tiedot ja sijoitetaan ne muuttujiin
        System.out.print("Syota rivitalon asuntojen lukumaara: ");
        tempint = konsoli.nextInt();
        konsoli.nextLine(); // Tyhjennä bufferi

        System.out.println("Syota rivitalon asukkaat asunnoittain");
        for(i=0; i<tempint; i++)
        {
            System.out.println("### Rivitalon " + Integer.toString(i+1) + ". Asunto ###");
            for (n=0;n<4;n++)
            {
                System.out.print("Syota " + Integer.toString(n+1) +". asukkaan nimi: ");
                temp1 = konsoli.nextLine();
                tempAsukas = new Asukas(temp1);
                asukaslista.add(tempAsukas);
            }
            System.out.print("Asunnon pinta-ala: ");
            tempdouble = konsoli.nextDouble();
            konsoli.nextLine(); // Tyhjennä bufferi

            System.out.print("Asunnon huoneiden lukumaara: ");
            tempint = konsoli.nextInt();
            konsoli.nextLine(); // Tyhjennä bufferi

            asuntolista.add(new Asunto(asukaslista, tempdouble, tempint));
            asukaslista.clear();
        }

        System.out.print("Syota tontin nimi: ");
        temp1 = konsoli.nextLine();
        System.out.print("Syota tontin osoite: ");
        temp2 = konsoli.nextLine();
        System.out.print("Syota tontin pinta-ala: ");
        tempdouble = konsoli.nextDouble();
        konsoli.nextLine(); // Tyhjennä bufferi
        try
        {
            Tontti tempRivitaloTontti = new Tontti(temp1, temp2, tempdouble, new Rivitalo(asuntolista.size(), asuntolista));
            printList.add(tempRivitaloTontti);
        }
        catch (Exception e)
        {
            System.out.println("Arvo ei voi olla negatiivinen");
        }
        
        asuntolista.clear();

        // Otetaan käyttäjältä Omakotitalon asukkaiden tiedot ja sijoitetaan ne muuttujiin
        System.out.println("Syota Omakotitalon asukkaat");
        for(i=0; i<1; i++)
        {
            for (n=0;n<4;n++)
            {
                System.out.print("Syota " + Integer.toString(n+1) +". asukkaan nimi: ");
                temp1 = konsoli.nextLine();
                tempAsukas = new Asukas(temp1);
                asukaslista.add(tempAsukas);
            }
            
            System.out.print("Asunnon pinta-ala: ");
            tempdouble = konsoli.nextDouble();
            konsoli.nextLine(); // Tyhjennä bufferi

            System.out.print("Asunnon huoneiden lukumaara: ");
            tempint = konsoli.nextInt();
            konsoli.nextLine(); // Tyhjennä bufferi

            asuntolista.add(new Asunto(asukaslista, tempdouble, tempint));
            asukaslista.clear();
        }

        System.out.print("Syota tontin nimi: ");
        temp1 = konsoli.nextLine();
        System.out.print("Syota tontin osoite: ");
        temp2 = konsoli.nextLine();
        System.out.print("Syota tontin pinta-ala: ");
        tempdouble = konsoli.nextDouble();
        konsoli.nextLine(); // Tyhjennä bufferi

        Tontti tempOmakotitaloTontti = new Tontti(temp1, temp2, tempdouble, new Omakotitalo(asuntolista.size(), asuntolista));
        printList.add(tempOmakotitaloTontti);

        
        asuntolista.clear();

        konsoli.close(); // Suljetaan Scanner-objekti

        // Tulostetaan lopuksi kaikkien tonttien tiedot
        lenint = printList.size();
        for(m=0;m<lenint;m++)
        {
            System.out.println("\nTontin tiedot\n####################");
            System.out.println("Nimi: " + printList.get(m).getNimi());
            System.out.println("Pinta-Ala: " + printList.get(m).getAla());
            System.out.println("Osoite: " + printList.get(m).getOsoite());
    
            System.out.print("\nRakennuksen tiedot\n####################\n");
            System.out.println("Tyyppi: " + printList.get(m).getRakennus().getTyyppi());
            System.out.println("Asuntoja: " + printList.get(m).getRakennus().getAsuntoLkm() + " Kpl");
            for (i=0;i<printList.get(m).getRakennus().getAsunnot().size();i++)
            {
                System.out.println("# Asunto " + Integer.toString(i+1) + " #");
                System.out.println("Pinta-Ala: " + printList.get(m).getRakennus().getAsunnot().get(i).getPintaAla());
                System.out.println("Huoneita: " + printList.get(m).getRakennus().getAsunnot().get(i).getHuoneet());
                System.out.println("Asukkaat: ");
                tempint = printList.get(m).getRakennus().getAsunnot().get(i).getAsukkaat().size();
                for (n=0;n<tempint;n++)
                {
                    System.out.println(printList.get(m).getRakennus().getAsunnot().get(i).getAsukkaat().get(n).getNimi());
                }
            }
        }
    }
}

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // Pääohjelman va-muuttujat ja muu alustus
        int tempint;
        String temp;
        Character tempchar;
        Scanner konsoli = new Scanner(System.in);

        // Otetaan käyttäjältä tiedostopolku ja luodaan sillä sanalista
        System.out.print("Syota tiedostopolku: ");
        String polku = konsoli.nextLine();
        Sanalista sanalista = new Sanalista(polku);
        System.out.println("Luettu tiedostosta Sanalistaan: " + sanalista.annaSanat());

        // Demonstroidaan bonusmetodin toimivuus. kommentoi piiloon jos ei huvita testata
        System.out.print("Anna sanatJoidenPituusOn() metodin testipituus: ");
        tempint = konsoli.nextInt();
        konsoli.nextLine(); // Tyhjennä bufferi
        Sanalista bonusDemoSanalista = sanalista.sanatJoidenPituusOn(tempint);
        System.out.println("Palautti: " + bonusDemoSanalista.annaSanat());

        // Demonstroidaan toisen bonusmetodin toimivuus. kommentoi piiloon jos ei huvita testata
        System.out.print("Anna sanatJoissaMerkit() metodin testiparametri: ");
        temp = konsoli.nextLine();
        Sanalista bonusToinenSanalista = sanalista.sanatJoissaMerkit(temp);
        System.out.println("Palautti: " + bonusToinenSanalista.annaSanat());

        // Otetaan kayttajalta arvausten maara peliin
        System.out.print("Syota pelin arvausten maara: ");
        tempint = konsoli.nextInt();
        konsoli.nextLine(); // Tyhjennä input bufferi

        Hirsipuu peli = new Hirsipuu(sanalista, tempint); // Luodaan peli

        System.out.println("### HIRSIPUU ALKAA ###");
        // Loopataan kunnes arvaukset loppuvat ja pelin loppuehto ei täyty
        while (peli.onLoppu() == false)
        {
            // Pelin tilanne tulostetaan pelaajalle
            System.out.println("\nArvauksia jaljella: " + peli.arvauksiaOnJaljella());
            System.out.println("Arvatut kirjaimet: " + peli.arvaukset());
            System.out.println("# "+ peli.annaNayttoSana() +" #");
            // Otetaan arvaus pelaajalta
            System.out.print("Arvaa kirjain: ");
            temp = konsoli.nextLine();

            
            if (temp.length() > 1) // Tarkistetaan onko syote yksittainen kirjain
            {
                System.out.println("Arvauksen pitaa olla yksittainen kirjain!");
                continue;
            }
            tempchar = temp.charAt(0);

            if (Character.isAlphabetic(tempchar)) // Tarkistetaan kuuluuko syote aakkostoon
            {
                if (peli.arvaukset().contains(Character.toUpperCase(tempchar))) // Tarkistetaan onko kirjain jo arvattu
                {
                    System.out.println("Kirjain on jo arvattu!");
                    continue;
                }
                if (peli.arvaa(tempchar) == true) // Tarkistetaan oliko arvaus oikein
                {
                    System.out.println("Oikein!");
                }
                else // Jos arvaus ei ollut oikein, se oli väärin (:D)
                {
                    System.out.println("Vaarin!");
                }
            }
            else // Jos syöte ei kuulu aakkostoon, kerrotaan se käyttäjälle
            {
                System.out.println("Syote ei kuulu aakkostoon!");
            }
        }
        konsoli.close(); // Suljetaan Scanner, koska sitä ei enää tarvita
        // Tarkistetaan tuliko pelaaja ulos loopista voittajana vai häviäjänä
        if (peli.annaNayttoSana().equals(peli.sana())) 
        {
            System.out.println("\n### VOITTO ###");
        }
        else
        {
            System.out.println("\n### HAVIO ###");
        }
        // Pelin lopetus
        System.out.println("Peli ohi. Oikea sana: " + peli.sana());
    }
}
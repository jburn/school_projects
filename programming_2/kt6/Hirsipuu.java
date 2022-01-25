import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Hirsipuu 
{
    // Luokkamuuttujat
    private int arvausMaara = 8; // Alustetaan kahdeksaan. Ei muutu jos ei saada >0 parametria muodostimeen
    private String arvattavaSana;
    private String naytettavaSana = "";
    private List<Character> arvausLista = new ArrayList<Character>();

    // Muodostin otta parametreina listan sanoista ja arvausten määrän positiivisena >0 kokonaislukuna
    public Hirsipuu(Sanalista sLista, int aMaara)
    {
        Random rand = new Random(); // Alustetaan satunnaisobjekti
        this.arvattavaSana = sLista.annaSanat().get(rand.nextInt(sLista.annaSanat().size())); // Arvotaan satunnainen sana listalta
        // Lisätään arvattavaan sanaan tyhjiä kohtia
        for(int i=0;i<arvattavaSana.length();i++)
        {
            naytettavaSana += '_';
        }
        // Asetetaan arvausten määrä
        if(aMaara > 0)
        {
            arvausMaara = aMaara;
        }
    }

    // Toteuttaa arvaamisen yhteydessä tehtävät toiminnot
    // Tarkistaa onko arvaus oikein vai väärin
    public boolean arvaa(Character merkki)
    {
        merkki = Character.toUpperCase(merkki);
        if ((this.arvattavaSana.contains(Character.toString(merkki)) && (!this.arvausLista.contains(merkki))))
        {
            this.arvausLista.add(merkki);
            for (int i=0;i<this.arvattavaSana.length();i++)
            {
                if (this.arvattavaSana.charAt(i) == merkki.charValue())
                {
                    this.naytettavaSana = this.naytettavaSana.substring(0, i) + merkki + this.naytettavaSana.substring(i+1);
                }
            }
            return true;
        }
        else
        {
            this.arvausMaara--;
            this.arvausLista.add(merkki);
            return false;
        }
    }

    // Metodi joka palauttaa arvatut kirjaimet
    public List<Character> arvaukset()
    {
        return this.arvausLista;
    }

    // Metodi joka palauttaa jäljellä olevien arvausten määrän
    public int arvauksiaOnJaljella()
    {
        return this.arvausMaara;
    }

    // Metodi palauttaa arvattavan sanan piilottamattomana
    public String sana()
    {
        return this.arvattavaSana;
    }

    // Metodi palauttaa pelaajalle näytettävän sanan alaviivoineen
    public String annaNayttoSana()
    {
        return this.naytettavaSana;
    }

    // Metodi palauttaa totuusarvon, joka kuvastaa onko peli loppu
    public boolean onLoppu()
    {
        if (naytettavaSana.contains("_") && (this.arvauksiaOnJaljella() > 0))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}

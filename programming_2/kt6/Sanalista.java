import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Sanalista 
{
    // Luokkamuuttujat
    private List<String> sanaLista = new ArrayList<String>();
    private String tiedPolku;
    
    // Muodostin ottaa parametrina tiedoston polun, avaa polussa sijaitsevan tiedoston, ja
    // lukee tiedostosta kaikki rivit sanalistaan.
    public Sanalista(String polku)
    {
        Scanner lukija;
        String temp = "";
        tiedPolku = polku;
        try
        {
            File tiedosto = new File(polku);
            lukija = new Scanner(tiedosto);
            while(lukija.hasNextLine())
            {
                temp = lukija.nextLine();
                temp = temp.toUpperCase();
                this.sanaLista.add(temp);
            }
            lukija.close();
        }
        catch (Exception e)
        {
            System.out.println("Tiedostoa "+ polku +" ei voitu avata!");
        }
    }

    // Metodi joka palauttaa listan sanalistan sanoista
    public List<String> annaSanat()
    {
        return this.sanaLista;
    }

    // Palauttaa Sanalistan sanoista joiden pituus on parametrinä annetun kokonaisluvun mukainen
    public Sanalista sanatJoidenPituusOn(int pituus)
    {
        Sanalista uusiLista = new Sanalista(tiedPolku);
        uusiLista.sanaLista.clear();
        for (int i=0;i<this.sanaLista.size();i++)
        {
            if (this.sanaLista.get(i).length() == pituus)
            {
                uusiLista.sanaLista.add(this.sanaLista.get(i));
            }
        }
        return uusiLista;
    }

    // Palauttaa sanalistan sanoista joissa on samat merkit samoilla paikoilla kuin annetussa merkkijonossa
    public Sanalista sanatJoissaMerkit(String mjono)
    {
        mjono = mjono.toUpperCase();
        Sanalista uusiLista = new Sanalista(tiedPolku);
        uusiLista.sanaLista.clear();
        boolean same;
        for (int i=0;i<this.sanaLista.size();i++)
        {   
            if(mjono.length() != this.sanaLista.get(i).length())
            {
                continue;
            }
            same = true;
            for (int n=0;n<mjono.length();n++)
            {
                if (mjono.charAt(n) == '_')
                {
                    continue;
                }
                else if (this.sanaLista.get(i).charAt(n) != mjono.charAt(n))
                {
                    same = false;
                    continue;
                }
            }
            if (same == true)
            {
                uusiLista.sanaLista.add(this.sanaLista.get(i));
            }
        }
        return uusiLista;
    }
}

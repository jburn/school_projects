import java.util.ArrayList;
public class Rakennus
{
    // Luokkamuuttujat
    private float pintaAla;
    private int huoneet;
    private ArrayList<Asukas> asukkaat;

    // Muodostin ottaa parametreinä: 
    // Pinta-alan positiivisena liukulukuna
    // Huoneiden lukumäärän positiivisena kokonaislukuna
    // Listan asukkaista listana Asukas-luokan olioita
    public Rakennus(float ala, int huoneLkm, ArrayList<Asukas> asukasNimet)
    {
        if ((ala < 0) || (huoneLkm < 0)) 
        {
            System.out.println("Pinta-ala tai huoneiden lkm ei voi olla negatiivinen!");
        }
        else 
        {
            pintaAla = ala;
            huoneet = huoneLkm;
        }
        asukkaat = asukasNimet;
    }

    // Asetinmetodi pinta-alalle
    public void setAla(float uusiAla)
    {
        if (uusiAla >= 0) 
        {
            pintaAla = uusiAla;
        }
        else 
        {
            System.out.println("Pinta-ala ei voi olla negatiivinen!");
        }
    }

    // Saantimetodi pinta-alalle
    public float getAla()
    {
        return pintaAla;
    }

    // Asetinmetodi huoneiden lukumäärälle
    public void setHuoneet(int uusiLkm)
    {
        if(uusiLkm < 0)
        {
            System.out.println("Huoneiden lukumaara ei voi olla negatiivinen!");
        }
        else
        {
            huoneet = uusiLkm;
        }
    }

    // Saantimetodi huoneiden lukumäärälle
    public int getHuoneet()
    {
        return huoneet;
    }

    // Asetinmetodi asukaslistalle
    public void setAsukkaat(ArrayList<Asukas> uusiAsukasLista)
    {
        asukkaat = uusiAsukasLista;
    }

    // Saantimetodi asukaslistalle
    public ArrayList<Asukas> getAsukkaat()
    {
        return asukkaat;
    }
}
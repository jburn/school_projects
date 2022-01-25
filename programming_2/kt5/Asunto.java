import java.util.ArrayList;

public class Asunto
{
    // Luokkamuuttujat
    private ArrayList<Asukas> asukkaat = new ArrayList<Asukas>();
    private double pintaAla;
    private int huoneet;

    // Muodostimen parametrit:
    // Merkkijonoista koostuva ArrayList-objekti
    // Asunnon pinta-ala positiivisena liukulukuna
    // Huoneiden lukumäärä positiivisena kokonaislukuna
    public Asunto(ArrayList<Asukas> asukasLista, double asuntoAla, int huoneLkm)
    {
        if ((asuntoAla < 0) || (huoneLkm < 0))
        {
            System.out.println("Pinta-ala tai huoneiden lukumaara ei voi olla negatiivinen!");
        }
        else
        {
            pintaAla = asuntoAla;
            huoneet = huoneLkm;
        }
        
        for (int i=0;i<asukasLista.size();i++)
        {
            this.addAsukas(asukasLista.get(i));
        }
        
    }

    // Asetinmetodi asukkaille
    public void setAsukkaat(ArrayList<Asukas> uusiLista)
    {
        this.asukkaat = uusiLista;
    }

    // Saantimetodi asukkaille
    public ArrayList<Asukas> getAsukkaat()
    {
        return this.asukkaat;
    }

    // Asetinmetodi pinta-alalle
    public void setPintaAla(double uusiAla)
    {
        if (uusiAla < 0)
        {
            System.out.println("Pinta-ala ei voi olla negatiivinen!");
        }
        else
        {
            this.pintaAla = uusiAla;
        }
    }

    // Saantimetodi pinta-alalle
    public double getPintaAla()
    {
        return this.pintaAla;
    }

    // Asetinmetodi huoneiden lukumäärälle
    public void setHuoneet(int uusiLkm)
    {
        if (uusiLkm < 0)
        {
            System.out.println("Huoneiden lukumaara ei voi olla negatiivinen!");
        }
        else
        {
            this.huoneet = uusiLkm;
        }
    }

    // Saantimetodi huoneiden lukumäärälle
    public int getHuoneet()
    {
        return this.huoneet;
    }

    // Asukkaan lisäysmetodi
    public void addAsukas(Asukas uusiAsukas)
    {
        this.asukkaat.add(uusiAsukas);
    }

    // Asukkaiden vaihtometodi
    public void newAsukas(ArrayList<Asukas> uusiLista)
    {
        this.asukkaat.clear();
        for (int i=0;i<uusiLista.size();i++)
        {
            this.asukkaat.add(uusiLista.get(i));
        }
    }
}
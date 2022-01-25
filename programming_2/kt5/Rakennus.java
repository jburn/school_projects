import java.util.ArrayList;

public class Rakennus
{
    // Luokkamuuttujat
    private int asuntoLkm;
    private ArrayList<Asunto> asunnot = new ArrayList<Asunto>();
    private String tyyppi;
    // Muodostin ottaa parametreinä: 
    // Asuntojen lukumäärän positiivisena kokonaislukuna
    // Listan asunnoista ArrayListana Asunto-luokan olioita
    public Rakennus(int asuntoLukumaara, ArrayList<Asunto> asuntoLista) 
    {
        if (asuntoLkm < 0)
        {
            System.out.println("Asuntojen lukumaara ei voi olla negatiivinen!");
        }
        else 
        {
            asuntoLkm = asuntoLukumaara;
        }

        for (int i=0;i<asuntoLista.size();i++)
        {
            addAsunto(asuntoLista.get(i));
        }
    }

    // Asetinmetodi huoneiden lukumäärälle
    public void setAsuntoLkm(int uusiLkm)
    {
        if (uusiLkm < 0)
        {
            System.out.println("Asuntojen lukumaara ei voi olla negatiivinen!");
        }
        else
        {
            this.asuntoLkm = uusiLkm;
        }
    }

    // Saantimetodi huoneiden lukumäärälle
    public int getAsuntoLkm()
    {
        return this.asuntoLkm;
    }

    // Asetinmetodi asuntolistalle
    public void setAsunnot(ArrayList<Asunto> uusiAsuntoLista)
    {
        this.asunnot = uusiAsuntoLista;
    }

    // Saantimetodi asuntolistalle
    public ArrayList<Asunto> getAsunnot()
    {
        return this.asunnot;
    }

    // Asetinmetodi rakennustyypille
    public void setTyyppi(String rakennusTyyppi)
    {
        this.tyyppi = rakennusTyyppi;
    }

    // Saantimetodi rakennustyypille
    public String getTyyppi()
    {
        return tyyppi;
    }

    // Asunnon lisäysmetodi
    public void addAsunto(Asunto uusiAsunto)
    {
        asunnot.add(uusiAsunto);
    }

    // Asuntojen vaihtometodi
    public void newAsunto(ArrayList<Asunto> uusiLista)
    {
        this.asunnot.clear();
        for (int i=0;i<uusiLista.size();i++)
        {
            this.asunnot.add(uusiLista.get(i));
        }
    }


}
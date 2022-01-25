public class Tontti
{
    // Luokkamuuttujat
    private String nimi;
    private String[] sijainti; // Sijainti tulisi antaa muodossa {"leveys, pituus"}
    private float pintaAla;
    private Rakennus rakennus;

    // Muodostin ottaa parametreinä:
    // Tontin nimen merkkijonona
    // Tontin koordinaatit merkkijonolistana
    // Pinta-alan positiivisena liukulukuna
    // Tontilla olevan rakennuksen Rakennus-luokan oliona
    public Tontti(String tonttiNimi, String[] koordinaatit, float tonttiPintaAla, Rakennus tonttiRakennus)
    {
        if (tonttiPintaAla >= 0) 
        {
            pintaAla = tonttiPintaAla;
        }
        else
        {
            System.out.println("Pinta-ala ei voi olla negatiivinen!");        
        }
        nimi = tonttiNimi;
        sijainti = koordinaatit;
        rakennus = tonttiRakennus;
    }

    // Asetinmetodi tontin nimelle
    public void setNimi(String uusiNimi)
    {
        nimi = uusiNimi;
    }

    // Saantimetodi tontin nimelle
    public String getNimi()
    {
        return nimi;
    }

    // Asetinmetodi tontin sijainnille
    public void setSijainti(String[] uusiSijainti)
    {
        sijainti = uusiSijainti;
    }

    // Saantimetodi tontin sijainnille
    public String[] getSijainti()
    {
        return sijainti;
    }

    // Saantimetodi tontin pinta-alalle
    public float getAla()
    {
        return pintaAla;
    }

    // Asetinmetodi tontin pinta-alalle
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

    // Asetinmetodi tontin rakennukselle
    public void setRakennus(Rakennus uusiRakennus)
    {
        rakennus = uusiRakennus;
    }

    // Saantimetodi tontin rakennukselle
    public Rakennus getRakennus()
    {
        return rakennus;
    }
}
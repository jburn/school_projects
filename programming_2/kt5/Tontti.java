public class Tontti
{
    // Luokkamuuttujat
    private String nimi;
    private String osoite; // Sijainti tulisi antaa muodossa {"leveys, pituus"}
    private double pintaAla;
    private Rakennus rakennus;

    // Muodostin ottaa parametreinä:
    // Tontin nimen merkkijonona
    // Tontin koordinaatit merkkijonolistana
    // Pinta-alan positiivisena liukulukuna
    // Tontilla olevan rakennuksen Rakennus-luokan oliona
    public Tontti(String tonttiNimi, String tonttiOsoite, double tonttiPintaAla, Rakennus tonttiRakennus)
    {
        if (tonttiPintaAla >= 0) 
        {
            pintaAla = tonttiPintaAla;
        }
        else
        {
            System.out.println("Pinta-ala ei voi olla negatiivinen!");        
        }
        rakennus = tonttiRakennus;
        nimi = tonttiNimi;
        osoite = tonttiOsoite;
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

    // Asetinmetodi tontin osoitteelle
    public void setOsoite(String uusiOsoite)
    {
        osoite = uusiOsoite;
    }

    // Saantimetodi tontin osoitteelle
    public String getOsoite()
    {
        return osoite;
    }

    // Saantimetodi tontin pinta-alalle
    public double getAla()
    {
        return pintaAla;
    }

    // Asetinmetodi tontin pinta-alalle
    public void setAla(double uusiAla)
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
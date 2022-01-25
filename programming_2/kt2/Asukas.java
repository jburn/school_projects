public class Asukas
{
    // Luokkamuuttujat
    private String nimi;
    private String syntymaAika;

    // Luokan muodostin ottaa parametreinä:
    // nimen ja syntymäajan merkkijonoina
    public Asukas(String aNimi, String aSyntymaAika)
    {
        nimi = aNimi;
        syntymaAika = aSyntymaAika;
    }

    // Asetinmetodi nimelle
    public void setNimi(String uusiNimi)
    {
        nimi = uusiNimi;
    }

    // Saantimetodi nimelle
    public String getNimi()
    {
        return nimi;
    }

    // Asetinmetodi syntymäajalle
    public void setSaika(String uusiSaika)
    {
        syntymaAika = uusiSaika;
    }

    // Saantimetodi syntymäajalle
    public String getSaika()
    {
        return syntymaAika;
    }
}
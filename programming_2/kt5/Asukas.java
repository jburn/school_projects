public class Asukas
{
    // Luokkamuuttujat
    private String nimi;

    // Luokan muodostin ottaa parametreinä:
    // nimen merkkijonona
    public Asukas(String aNimi)
    {
        nimi = aNimi;
    }

    // Asetinmetodi nimelle
    public void setNimi(String uusiNimi)
    {
        this.nimi = uusiNimi;
    }

    // Saantimetodi nimelle
    public String getNimi()
    {
        return this.nimi;
    }
}
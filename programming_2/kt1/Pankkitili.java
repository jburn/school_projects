public class Pankkitili
{
    //Luokkamuuttujat
    private String tilinro;
    private String omistaja;
    private double saldo;

    //Muodostin ottaa parametreinä tilinumeron, omistajan ja saldon
    public Pankkitili(String tiliNumero, String tiliOmistaja, double tiliSaldo)
    {
        tilinro = tiliNumero;
        omistaja = tiliOmistaja;
        saldo = tiliSaldo;
    }
    //Tilinumeron asetusmetodi
    public void setTilinro(String uusiNro) 
    {
        tilinro = uusiNro;
    }
    //Tilinumeron saantimetodi
    public String getTilinro() 
    {
        return tilinro;
    }
    //Omistajan asetusmetodi
    public void setOmistaja(String uusiOmistaja)
    {
        omistaja = uusiOmistaja;
    }
    //Omistajan saantimetodi
    public String getOmistaja() 
    {
        return omistaja;
    }
    //Saldon asetusmetodi
    public double getSaldo()
    {
        return saldo;
    }
    //Ottometodi, hyväksyy parametrinä positiivisen summan.
    public void otto(double ottoSumma)
    {
        if ((ottoSumma>0) && (ottoSumma<=saldo))
        {
            saldo -= ottoSumma;
        } else {
            System.out.println("VIRHE: Nostettava summa liian suuri tai negatiivinen!");
        }
    }
    //Talletusmetodi, hyväksyy parametrinä positiivisen summan.
    public void talletus(double summa)
    {
        if (summa>0)
        {
            saldo += summa;
        } else {
            System.out.println("VIRHE: Talletettava summa ei voi olla negatiivinen!");
        }
    }
    //Metodi tilitietojen tulostukseen
    public void tilitiedot()
    {
        System.out.println("Tilinumero: " + tilinro);
        System.out.println("Tilin omistaja: " + omistaja);
        System.out.println("Tilin saldo: " + saldo + " euroa\n");
    }
}
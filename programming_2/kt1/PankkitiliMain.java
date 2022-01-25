import java.util.Scanner;

class PankkitiliMain 
{
    public static void main(String args[])
    {
        String temp1, temp2;
        double tempd;

        //Luodaan Scanner objekti käyttäjäinputtia varten
        Scanner konsoli = new Scanner(System.in);

        //Otetaan käyttäjältä tilinumero syötteenä ja tallennetaan va-muuttujaan
        System.out.print("Syota tilinumero: ");
        temp1 = konsoli.nextLine();

        //Otetaan käyttäjältä omistajan nimi syötteenä ja tallennetaan va-muuttujaan
        System.out.print("Syota omistajan nimi: ");
        temp2 = konsoli.nextLine();

        //Otetaan käyttäjältä saldo syötteenä ja tallennetaan va-muuttujaan
        System.out.print("Syota saldo: ");
        tempd = konsoli.nextDouble();

        //Luodaan käyttäjän syötteiden perusteella pankkitiliobjekti
        Pankkitili ptili = new Pankkitili(temp1, temp2, tempd);
        ptili.tilitiedot(); //Tulostetaan tilitiedot

        //Testataan talletusmetodia käyttäjän antamalla syötteellä
        System.out.print("Syota talletuksen maara: ");
        tempd = konsoli.nextDouble();
        ptili.talletus(tempd);
        ptili.tilitiedot();

        //Testataan ottometodia käyttäjän antamalla syötteellä
        System.out.print("Syota oton maara: ");
        tempd = konsoli.nextDouble();
        ptili.otto(tempd);
        ptili.tilitiedot();

        //Suljetaan lopuksi Scanner objekti
        konsoli.close();
    }
}
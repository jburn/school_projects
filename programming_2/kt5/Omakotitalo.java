import java.util.ArrayList;

public class Omakotitalo extends Rakennus
{
    // Luokkamuuttujat
    private final String tyyppi = "Omakotitalo";

    // Muodostin ottaa superluokan parametrit
    public Omakotitalo(int asuntoLkm, ArrayList<Asunto> asuntoLista)
    {
        super(asuntoLkm, asuntoLista);
        super.setTyyppi(tyyppi);
    }
}

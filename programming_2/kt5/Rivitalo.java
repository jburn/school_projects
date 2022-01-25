import java.util.ArrayList;

public class Rivitalo extends Rakennus
{
    // Luokkamuuttujat
    private final String tyyppi = "Rivitalo";

    // Muodostin ottaa superluokan parametrit
    public Rivitalo(int asuntoLkm, ArrayList<Asunto> asuntoLista)
    {
        super(asuntoLkm, asuntoLista);
        super.setTyyppi(tyyppi);
    }
}

import java.util.ArrayList;

public class Kerrostalo extends Rakennus
{
    // Luokkamuuttujat
    private final String tyyppi = "Kerrostalo";

    // Muodostin ottaa superluokan parametrit
    public Kerrostalo(int asuntoLkm, ArrayList<Asunto> asuntoLista)
    {
        super(asuntoLkm, asuntoLista);
        super.setTyyppi(tyyppi);
    }
}

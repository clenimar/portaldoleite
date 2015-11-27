package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExibicaoMaisNegativos implements MetodoDeExibicao {

    public ExibicaoMaisNegativos() {

    }

    @Override
    public List<Dica> ordenaLinhaDoTempo(List l) {
        Collections.sort(l, new Comparator<Dica>() {
            @Override
            public int compare(Dica dica, Dica outraDica) {
                return Integer.compare(outraDica.getDiscordancias(), dica.getDiscordancias());
            }
        });

        return l;
    }
}

package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExibicaoMaisPositivos implements MetodoDeExibicao {

    public ExibicaoMaisPositivos() {

    }

    @Override
    public List<Dica> ordenaLinhaDoTempo(List l) {
        Collections.sort(l, new Comparator<Dica>() {
            @Override
            public int compare(Dica dica, Dica outraDica) {
                return Integer.compare(outraDica.getConcordancias(), dica.getConcordancias());
            }
        });

        return l;
    }
}

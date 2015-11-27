package models;

import com.google.common.collect.Lists;

import java.util.List;

public class ExibicaoMaisRecentes implements MetodoDeExibicao {

    public ExibicaoMaisRecentes() {

    }

    @Override
    public List<Dica> ordenaLinhaDoTempo(List l) {
        return Lists.reverse(l);
    }
}

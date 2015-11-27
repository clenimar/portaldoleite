package models;


import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import java.util.List;

public class LinhaDoTempo {
    private static GenericDAOImpl dao = new GenericDAOImpl();
    private List<Dica> dicas;
    private MetodoDeExibicao metodoDeExibicao;

    public LinhaDoTempo() {
        this.metodoDeExibicao = new ExibicaoMaisRecentes();
    }

    public LinhaDoTempo(MetodoDeExibicao m) {
        this.metodoDeExibicao = m;
    }

    @play.db.jpa.Transactional
    public List<Dica> getLinhaDoTempo() {
        return metodoDeExibicao.ordenaLinhaDoTempo(
                dao.findAllByClassName("Dica")
        );
    }

    public List<Dica> getDicas() {
        return dicas;
    }

    public void setDicas(List<Dica> dicas) {
        this.dicas = dicas;
    }

    public MetodoDeExibicao getMetodoDeExibicao() {
        return metodoDeExibicao;
    }

    public void setMetodoDeExibicao(MetodoDeExibicao metodoDeExibicao) {
        this.metodoDeExibicao = metodoDeExibicao;
    }
}

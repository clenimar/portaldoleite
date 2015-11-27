package unidade;
        import static org.fest.assertions.Assertions.assertThat;

        import models.*;
        import org.junit.Before;
        import org.junit.Test;
        import java.util.ArrayList;
        import java.util.List;

public class LinhaDoTempoTeste {

    LinhaDoTempo linhaDoTempo;


    @Before
    public void before() {
        this.linhaDoTempo = new LinhaDoTempo();

    }

    @Test
    private void adicionaDicasTimeLine() {
        linhaDoTempo.getDicas().add( new DicaAssunto("Padrões de projeto são muitos, estude bastante.", new Tema("Padrões")));
        linhaDoTempo.getDicas().add( new DicaConselho("configure o Play antes, dá muito trabalho", new Tema("Play")));
        linhaDoTempo.getDicas().add( new DicaMaterial("https://www.codecademy.com", new Tema("Material")));
        linhaDoTempo.getDicas().add( new DicaDisciplina("Java - P2", "Nao e obrigado, mas ajuda muito saber", new Tema("Requisitos")));

        assertThat(linhaDoTempo.getLinhaDoTempo().get(0).getTexto()).isEqualTo("Padrões de projeto são muitos, estude bastante.");

    }

    @Test
    private void testaExibicaoPorMaisRecentes(){

        linhaDoTempo.setMetodoDeExibicao(new ExibicaoMaisRecentes());

        assertThat(linhaDoTempo.getLinhaDoTempo().get(0).getTexto()).isEqualTo("Padrões de projeto são muitos, estude bastante.");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(1).getTexto()).isEqualTo("configure o Play antes, dá muito trabalho");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(2).getTexto()).isEqualTo("https://www.codecademy.com");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(3).getTexto()).isEqualTo("Java - P2");

    }

    @Test
    private void testaExibicaoMaisPositivos(){

        //alterando as concordancias para mudar a ordem de exibicao
        linhaDoTempo.getLinhaDoTempo().get(0).setConcordancias(0);
        linhaDoTempo.getLinhaDoTempo().get(1).setConcordancias(5);
        linhaDoTempo.getLinhaDoTempo().get(2).setConcordancias(10);
        linhaDoTempo.getLinhaDoTempo().get(3).setConcordancias(15);

        //muda estrategia de exibicao para mais positivos
        linhaDoTempo.setMetodoDeExibicao(new ExibicaoMaisPositivos());

        assertThat(linhaDoTempo.getLinhaDoTempo().get(0).getTexto()).isEqualTo("Java - P2");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(1).getTexto()).isEqualTo("https://www.codecademy.com");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(2).getTexto()).isEqualTo("configure o Play antes, dá muito trabalho");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(3).getTexto()).isEqualTo("Padrões de projeto são muitos, estude bastante.");
    }

    @Test
    private void testaExibicaoMaisNegativos(){

        //alterando as concordancias para mudar a ordem de exibicao
        linhaDoTempo.getLinhaDoTempo().get(0).setDiscordancias(0);
        linhaDoTempo.getLinhaDoTempo().get(1).setDiscordancias(5);
        linhaDoTempo.getLinhaDoTempo().get(2).setDiscordancias(10);
        linhaDoTempo.getLinhaDoTempo().get(3).setDiscordancias(15);


        //muda estrategia de exibicao para mais negativos
        linhaDoTempo.setMetodoDeExibicao(new ExibicaoMaisNegativos());

        assertThat(linhaDoTempo.getLinhaDoTempo().get(0).getTexto()).isEqualTo("Java - P2");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(1).getTexto()).isEqualTo("https://www.codecademy.com");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(2).getTexto()).isEqualTo("configure o Play antes, dá muito trabalho");
        assertThat(linhaDoTempo.getLinhaDoTempo().get(3).getTexto()).isEqualTo("Padrões de projeto são muitos, estude bastante.");

    }
}
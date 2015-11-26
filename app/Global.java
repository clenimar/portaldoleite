import java.util.ArrayList;
import java.util.List;

import models.*;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();

	private Tema temaMinitestesSi, temaPlaySi, temaOacRevisaoIC, temaOacExercicios, temaListasLogica, temaProjetoLogica;
	private Dica dicaLabSi, dicaPlaySi, dicaRevisaoIcOac, dicaExerciciosOac, dicaListasLogica, dicaProjetoLogica;

	//Disciplinas defaut
	private Disciplina si1, oac, logica;
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
					addUsers();
					addDicas();
				}
			}
		});
	}
	
	@Override
	public void onStop(Application app){
	    JPA.withTransaction(new play.libs.F.Callback0() {
	    @Override
	    public void invoke() throws Throwable {
	        Logger.info("Aplicação finalizando...");
	        disciplinas = dao.findAllByClassName("Disciplina");

	        for (Disciplina disciplina: disciplinas) {
	        dao.removeById(Disciplina.class, disciplina.getId());
	       } 
	    }}); 
	}
	
	private void criaDisciplinaTemas(){
		this.si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(temaPlaySi = new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(temaMinitestesSi = new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));
		dao.persist(si1);

		this.oac = new Disciplina("OAC");
		oac.addTema(temaOacRevisaoIC = new Tema("Conceitos básicos (Revisão IC)"));
		oac.addTema(new Tema("Organização Básica de Computadores"));
		oac.addTema(new Tema("Linguagem de Descrição de Hardware"));
		oac.addTema(new Tema("Circuitos Combinacionais"));
		oac.addTema(new Tema("Circuitos Sequenciais"));
		oac.addTema(new Tema("Arquitetura do Conjunto de Instruções"));
		oac.addTema(new Tema("Organização e Arquitetura Básicas de Computadores"));
		oac.addTema(temaOacExercicios = new Tema("Exercícios"));
		dao.persist(oac);

		this.logica = new Disciplina("Lógica Matemática");
		logica.addTema(new Tema("Lógica Proposicional"));
		logica.addTema(new Tema("Lógica de Predicados"));
		logica.addTema(new Tema("Verificação através de Modelos"));
		logica.addTema(new Tema("Alloy"));
		logica.addTema(temaListasLogica = new Tema("Listas"));
		logica.addTema(temaProjetoLogica = new Tema("Projeto"));
		dao.persist(logica);

		dao.flush();
	}


	public void addUsers(){

		for (int id = 1; id <= 10; id++){
			User user = new User();
			user.setNome("user"+id);
			user.setLogin("user"+id);
			user.setPass("senha"+id);
			user.setEmail("user"+id+"@email.com");
		
			dao.persist(user);
		}
		dao.flush();

	}

	public void addDicas(){
		//cria meta dica oac
		MetaDica metaDicaOac = new MetaDica(oac, "user1", "Não falte as aulas, toda aula tem ponto extra!");
		metaDicaOac.setConcordancias(5);
		dao.persist(metaDicaOac);

		dicaExerciciosOac = new DicaConselho("Os exercicios extras valem muitos pontos, nao deixe de fazer");
		dicaExerciciosOac.setTema(temaOacExercicios);
		temaOacExercicios.setDisciplina(oac);
		dicaExerciciosOac.setUser("user5");
		dicaExerciciosOac.addUsuarioQueVotou("user1");
		dicaExerciciosOac.addUsuarioQueVotou("user2");

		//adiciona pontos a dica
		for (int i = 0; i < 20;i++){
			dicaExerciciosOac.incrementaConcordancias();
		}
		for (int i = 0; i < 5;i++){
			dicaExerciciosOac.incrementaDiscordancias();
		}
		dao.persist(dicaExerciciosOac);

		dicaRevisaoIcOac = new DicaAssunto("Antes das aulas faça uma boa revisao de IC");
		temaOacRevisaoIC.setDisciplina(oac);
		dicaRevisaoIcOac.setTema(temaOacRevisaoIC);
		dicaRevisaoIcOac.setUser("user4");
		dicaRevisaoIcOac.addUsuarioQueVotou("user5");
		dicaRevisaoIcOac.addUsuarioQueVotou("user1");
		dicaRevisaoIcOac.incrementaConcordancias();
		dicaRevisaoIcOac.incrementaConcordancias();

		//cria meta dica em si
		MetaDica metaDicaSi1 = new MetaDica(si1, "user2", "Seja autodidata! Procure por cursos online");
		dao.persist(metaDicaSi1);

		dicaLabSi = new DicaConselho("Faça todo os labs, não deixe acumular");
		temaMinitestesSi.setDisciplina(si1);
		dicaLabSi.setTema(temaMinitestesSi);
		dicaLabSi.setTema(temaMinitestesSi);
		dicaLabSi.setUser("user1");
		dicaLabSi.addUsuarioQueVotou("user2");
		dicaLabSi.addUsuarioQueVotou("user3");
		dicaLabSi.incrementaConcordancias();
		dicaLabSi.incrementaConcordancias();
		dao.persist(dicaLabSi);

		dicaPlaySi = new DicaConselho("Comece a configurar o Play no primeiro dia de aula, pois dá muuuito trabalho");
		temaPlaySi.setDisciplina(si1);
		dicaPlaySi.setTema(temaPlaySi);
		dicaPlaySi.setTema(temaPlaySi);
		dicaPlaySi.setUser("user2");
		dicaPlaySi.addUsuarioQueVotou("user5");
		dicaPlaySi.addUsuarioQueVotou("user4");
		dicaPlaySi.incrementaConcordancias();
		dicaPlaySi.incrementaConcordancias();
		dao.persist(dicaPlaySi);


		//cria meta dica logica
		MetaDica metaDicaLogica = new MetaDica(logica, "user3", "Copie para o seu caderno tudo que o professor copiar no quadro, TUDO!");
		dao.persist(metaDicaLogica);

		dicaListasLogica = new DicaConselho("Faça todas as listas possíveis");
		temaListasLogica.setDisciplina(logica);
		dicaListasLogica.setTema(temaListasLogica);
		dicaListasLogica.setTema(temaListasLogica);
		dicaListasLogica.setUser("user6");
		dicaListasLogica.addUsuarioQueVotou("user3");
		dicaListasLogica.addUsuarioQueVotou("user5");
		dicaListasLogica.incrementaConcordancias();
		dicaListasLogica.incrementaConcordancias();
		dao.persist(dicaListasLogica);

		dicaProjetoLogica = new DicaAssunto("Peça ajuda ao monitor responsável por seu grupo, começe o projeto assim que for lançado!");
		temaProjetoLogica.setDisciplina(logica);
		dicaProjetoLogica.setTema(temaProjetoLogica);
		dicaProjetoLogica.setTema(temaProjetoLogica);
		dicaProjetoLogica.setUser("user4");
		dicaProjetoLogica.addUsuarioQueVotou("user1");
		dicaProjetoLogica.addUsuarioQueVotou("user2");
		dicaProjetoLogica.incrementaConcordancias();
		dicaProjetoLogica.incrementaConcordancias();
		dao.persist(dicaProjetoLogica);

		dao.flush();

	}
}

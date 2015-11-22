import java.util.ArrayList;
import java.util.List;

import models.Disciplina;
import models.Tema;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
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
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));
		dao.persist(si1);

		Disciplina oac = new Disciplina("OAC - Organização e Arquitetura de Computadores");
		oac.addTema(new Tema("Conceitos básicos (Revisão IC)"));
		oac.addTema(new Tema("Organização Básica de Computadores"));
		oac.addTema(new Tema("Linguagem de Descrição de Hardware"));
		oac.addTema(new Tema("Circuitos Combinacionais"));
		oac.addTema(new Tema("Circuitos Sequenciais"));
		oac.addTema(new Tema("Arquitetura do Conjunto de Instruções"));
		oac.addTema(new Tema("Organização e Arquitetura Básicas de Computadores"));
		oac.addTema(new Tema("Exercícios"));
		dao.persist(oac);

		Disciplina logica = new Disciplina("Lógica Matemática");
		logica.addTema(new Tema("Lógica Proposicional"));
		logica.addTema(new Tema("Lógica de Predicados"));
		logica.addTema(new Tema("Verificação através de Modelos"));
		logica.addTema(new Tema("Alloy"));
		logica.addTema(new Tema("Listas"));
		logica.addTema(new Tema("Projeto"));
		dao.persist(logica);
		
		dao.flush();
	}
}

package persistencia;

import java.util.ArrayList;
import dominio.Producao;

public abstract class ProducaoDAO { //calsse abstrata "Pai" de Jatai e Americana

	public ProducaoDAO() {
		new conexaoDAO("jdbc:postgresql://localhost:5432/BDAGENDA","postgres","123");
	};
	
	public abstract void excluir(int IDaux);
	
	public abstract void incluir(Producao pro);
	
	public abstract void alterar(Producao pro);
	
	public abstract Producao buscar(int dAux);
	
	public abstract Producao buscarTipo(int idAux);
	
	public abstract ArrayList<Producao> buscaGeral();
}

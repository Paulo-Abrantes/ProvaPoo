package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

	public class conexaoDAO {
	
	private String usuario;
	private String senha;
	private String caminho;
	private Connection minhaConexao;
	
	public conexaoDAO(String caminho, String usuario, String senha) {
		this.caminho = caminho;
		this.usuario = usuario;
		this.senha = senha;
	}
	


	public void conectar() { // Cria a conexao com o Banco de dados
		try {
			Class.forName("org.postgresql.Driver"); 
			minhaConexao = DriverManager.getConnection(caminho, usuario, senha);
		}catch(Exception e) {
			System.out.println("Erro na conexao");
		}
	}
	
	public void desconectar() { // Remove a conexao
		try {
			minhaConexao.close();
		}catch(Exception e) {
			System.out.println("Erro na desconex�o...");
		}
	}

	public Connection getConexao() {  // "status da conexao, caso de algum erro
		return minhaConexao;
	}
}

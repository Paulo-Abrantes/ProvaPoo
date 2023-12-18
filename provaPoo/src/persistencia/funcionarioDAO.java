package persistencia;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import dominio.Funcionario;

public class funcionarioDAO  { // Implementacao da Interface
	
	private conexaoDAO c;
	private String INC = "INSERT INTO \"funcionario\" (\"matricula\", \"nome\", \"email\") VALUES (?, ?, ?)";
	private String BUS = "select * from \"funcionario\" where \"matricula\"=?";
	private String BUSGERAL = "select * from \"funcionario\"";
	private String DEL = "delete from \"funcionario\" where \"matricula\" = ?";
	private String ALT = "update \"funcionario\" set \"nome\" = ?, \"email\" = ? where \"matricula\" = ? ";	
	
	public funcionarioDAO() {
		c = new conexaoDAO("jdbc:postgresql://localhost:5432/BDAGENDA","postgres","123");
	}
	
		public void excluir(String aux ) { //Metodo da Interface. Exclusao de funcionario
			try {
				c.conectar();
				
				PreparedStatement inst = c.getConexao().prepareStatement(DEL);
				inst.setString(1, aux);
				inst.executeUpdate();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na exclusao" + e);
			}
	}
	
	public void incluir(Funcionario fun) { //Inclusao de funcionario, caso nao exista
		try {
			
			c.conectar();
			PreparedStatement inst = c.getConexao().prepareStatement(INC);
			inst.setString(1, fun.getMatricula());
			inst.setString(2, fun.getNome());
			inst.setString(3, fun.getEmail());
			inst.executeUpdate();
			c.desconectar();
			
		}catch(Exception e) {
			System.out.println("Erro na inclusao" + e.getMessage());
			e.printStackTrace();
			System.out.println("Instrução SQL: " + INC);

			
		}
	}
	
	public void alterar(Funcionario fun) {// Alterar/Atualizar os dados de um funcionario cadastrado
		try {
			
			c.conectar();
			PreparedStatement inst = c.getConexao().prepareStatement(ALT);
			inst.setString(1, fun.getMatricula());
			inst.setString(2, fun.getNome());
			inst.setString(3, fun.getEmail());
			inst.executeUpdate();
			c.desconectar();
			System.out.println("Tentando realizar a alteracao");
		}catch(Exception e) {
			System.out.println("Erro na alteracao" + e);
			e.printStackTrace();
		}
	} 
	
	
	public Funcionario buscar(String aux) { //Buscar funcionario pela Matricula. Se ele existir (alterar ou deletar) se nao (incluir)
		Funcionario fun = null;
		try {
			
			c.conectar();
			PreparedStatement inst = c.getConexao().prepareStatement(BUS);
			inst.setString(1, aux);
			ResultSet rs = inst.executeQuery();
			if(rs.next()) { 
				fun = new Funcionario (rs.getString("matricula"), rs.getString("nome"), rs.getString("email"));
			}
			
			c.desconectar();
		}catch(Exception e) {
			System.out.println("Erro na busca" + e);
		}
		return fun;
	}
	
	
	public ArrayList<Funcionario> buscaGeral() { //Relatorio de todos os funcionarios
		Funcionario fun;
		ArrayList<Funcionario> lista = new ArrayList<Funcionario>();
		try {
			c.conectar();
			Statement inst = c.getConexao().createStatement(); 
			ResultSet rs = inst.executeQuery(BUSGERAL);
			while(rs.next()) { 
				fun = new Funcionario(rs.getString("Matricula"), rs.getString("nome"), rs.getString("email"));
				lista.add(fun);
			}
			c.desconectar();
		}catch(Exception e) {
			System.out.println("Erro na busca" + e);
		}
		return lista;
	}
	
}

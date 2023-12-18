package persistencia;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import dominio.Colmeia;


public class ColmeiaDAO implements ControleDAO{

	

	private conexaoDAO c;
	private String INC = "INSERT INTO \"colmeia\" (\"id_cm\",\"tipo_cm\",\"status\") VALUES (?,?,?)";
	private String BUS = "SELECT * FROM \"colmeia\" WHERE \"id_cm\"=?";
	private String BUSTP = "SELECT * FROM \"colmeia\" WHERE \"tipo_cm\"=?";
	private String BUSGERAL = "SELECT * FROM \"colmeia\"";
	private String DEL = "DELETE FROM \"colmeia\" WHERE \"id_cm\" = ? OR \"status\" = ?";
	private String ALT = "UPDATE \"colmeia\" set \"tipo_cm\" = ?, \"status\" = ? WHERE \"id_cm\" = ? ";
		
		public ColmeiaDAO() {
			c = new conexaoDAO("jdbc:postgresql://localhost:5432/BDAGENDA","postgres","123");
		}

		
		public void excluir(int ID) { //Metodo excluir vindo da interface
			try {
								
				c.conectar();
				
				PreparedStatement inst = c.getConexao().prepareStatement(DEL);
				inst.setInt(1, ID);
				inst.execute();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na exclusao" + e);
			}
		}
		
		
		public void excluir(String status, int id) {//Sobrecarga do metodo excluir vindo da interface
			try {
				c.conectar();
				
				PreparedStatement inst = c.getConexao().prepareStatement(DEL);
				inst.setString(2, status);
				inst.setInt(1, id);
				inst.execute();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na exclusao" + e);
			}
		}
		
		
		public void incluir(Colmeia cm) {// Incluir uma nova Colmeia, caso nao exista
			try {
				
				c.conectar();
				PreparedStatement inst = c.getConexao().prepareStatement(INC);
				inst.setInt(1, cm.getId());
				inst.setInt(2, cm.getTipo());
				inst.setString(3, cm.getStatus());
				inst.execute();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na inclusao" + e);
			}
		}
		
		
		public void alterar(Colmeia cm) { // Alterar/Atualizar, caso ela exista
			try {
				
				c.conectar();
				PreparedStatement inst = c.getConexao().prepareStatement(ALT);
				inst.setInt(3, cm.getId());
				inst.setInt(1, cm.getTipo());
				inst.setString(2, cm.getStatus());
				inst.executeUpdate();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na alteracao" + e);
			}
		} 
		
		
		public Colmeia buscar(int idAux) {//Buscar Colmeia pelo id int. Se ele existir (alterar ou deletar) se nao (incluir)
			Colmeia cm = null;
			try {
				
				c.conectar();
				PreparedStatement inst = c.getConexao().prepareStatement(BUS);
				inst.setInt(1,idAux);
				ResultSet rs = inst.executeQuery();
				if(rs.next()) { 
					cm = new Colmeia(rs.getInt("id"), rs.getInt("tipo"), rs.getString("status"));
				}
				
				c.desconectar();
			}catch(Exception e) {
				System.out.println("Erro na busca"+ e);
			}
			return cm;
		}
		
		public ArrayList<Colmeia> buscaTipo() { //busca todas as colmeias por tipo
			Colmeia cm;
			ArrayList<Colmeia> listaTp = new ArrayList<Colmeia>();
			try {
				c.conectar();
				Statement inst = c.getConexao().createStatement(); 
				ResultSet rs = inst.executeQuery(BUSTP);
				while(rs.next()) { 
					cm = new Colmeia(rs.getInt("id"), rs.getInt("tipo"), rs.getString("status"));
					listaTp.add(cm);
				}
				c.desconectar();
			}catch(Exception e) {
				System.out.println("Erro na busca" + e);
			}
			return listaTp;
		}
		
		public ArrayList<Colmeia> buscaGeral() { //Relatoria de todas as Colmeias
			Colmeia cm;
			ArrayList<Colmeia> lista = new ArrayList<Colmeia>();
			try {
				c.conectar();
				Statement inst = c.getConexao().createStatement(); 
				ResultSet rs = inst.executeQuery(BUSGERAL);
				while(rs.next()) { 
					cm = new Colmeia(rs.getInt("id"), rs.getInt("tipo"), rs.getString("status"));
					lista.add(cm);
				}
				c.desconectar();
			}catch(Exception e) {
				System.out.println("Erro na busca" + e);
			}
			return lista;
		}




}
package persistencia;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import dominio.Producao;

	public class AmericanaDAO  extends ProducaoDAO { //Herança da classe Pai
		
		private conexaoDAO c;
		private String INC = "INSERT INTO \"producao\" (\"quantidade\",\"tipo_pd\",\"dia_pd\",\"id_pd\") VALUES (?,?,?,?)";
		private String BUS = "SELECT quantidade,tipo_pd, dia_pd, id_pd FROM \"producao\" WHERE \"id_pd\"=?";
		private String BUSTP = "SELECT * FROM \"producao\" WHERE \"tipo_pd\"=?";
		private String BUSGERAL = "SELECT * FROM \"producao\"";
		private String DEL = "DELETE FROM \"producao\" WHERE \"id_pd\" = ?";
		private String ALT = "UPDATE \"producao\" SET \"quantidade\" = ?, \"tipo_pd\" = ? WHERE \"dia_pd\" = ? ";	
		
		public AmericanaDAO() {
			c = new conexaoDAO("jdbc:postgresql://localhost:5432/BDAGENDA","postgres","123");
		}
		

		public void excluir(int ID) { // Exclusao por data (date vindo do SQL), a exclusao so vai ocorrer caso a data exista no banco
			try {
				c.conectar();
				
				PreparedStatement inst = c.getConexao().prepareStatement(DEL);
				inst.setInt(1, ID);
				inst.execute();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na exclusao" + e);
			}
		};
		
		public void incluir(Producao pro) { // Incluir uma producao, mas so pode incluir se informar o tipo

			try {
			c.conectar();
			PreparedStatement inst = c.getConexao().prepareStatement(INC);
			inst.setFloat(1, pro.getProducaoT());
			inst.setInt(2, pro.getTipo());
			inst.setDate(3, pro.getDia());
			inst.setInt(4, pro.getID());
			inst.execute();
			System.out.println("Quase inserindo americana");
			c.desconectar();
			
			}catch(Exception e) {
			System.out.println("Erro na inclusao" + e);
			}
		};
		
		public void alterar(Producao pro) { // Aleterar apenas se a protucao ja existir
			try {
				
				c.conectar();
				PreparedStatement inst = c.getConexao().prepareStatement(ALT);
				inst.setFloat(1, pro.getProducaoT());
				inst.setInt(2, pro.getTipo());
				inst.setDate(3, pro.getDia());
				inst.execute();
				c.desconectar();
				
			}catch(Exception e) {
				System.out.println("Erro na alteracao"+ e);
			}
		};
		
		public Producao buscar(int ID) {
		    Producao producao = null;

		    try {
		        c.conectar();
		        PreparedStatement inst = c.getConexao().prepareStatement(BUS);
		        inst.setInt(1, ID);
		        ResultSet rs = inst.executeQuery();

		        if (rs.next()) {
		            // Recupere os valores diretamente do ResultSet sem se preocupar com o nome da coluna
		            producao = new Producao(
		                rs.getFloat("quantidade"),
		                rs.getInt("tipo_pd"),
		                rs.getDate("dia_pd"),
		                rs.getInt("id_pd") // Certifique-se de usar o nome correto da coluna ID
		            );
		        }

		    } catch (Exception e) {
		        System.out.println("Erro na busca" + e);
		    }
		    System.out.println("Tentando recuperar as informacoes");
		    return producao;
	        

		    
		}


		
		
		
		public Producao buscarTipo(int idAux) { // Relatorio geral com filtro e de tipo
			
			Producao pro = null;
			try {
				
				c.conectar();
				PreparedStatement inst = c.getConexao().prepareStatement(BUSTP);
				inst.setInt(1,idAux);
				ResultSet rs = inst.executeQuery();
				if(rs.next()) { 
					pro = new Producao (rs.getFloat("quantidade"), rs.getInt("tipo"), rs.getDate("dia"), rs.getInt("ID"));
				}
				
				c.desconectar();
			}catch(Exception e) {
				System.out.println("Erro na busca"+ e);
			}
			return pro;
		};
		
		public ArrayList<Producao> buscaGeral(){ // Relatorio Geral
			
			Producao pro;
			ArrayList<Producao> lista = new ArrayList<Producao>();
			try {
				c.conectar();
				Statement inst = c.getConexao().createStatement(); 
				ResultSet rs = inst.executeQuery(BUSGERAL);
				while(rs.next()) { 
					pro = new Producao (rs.getFloat("quantidade"), rs.getInt("tipo"), rs.getDate("dia"), rs.getInt("ID"));
					lista.add(pro);
				}
				c.desconectar();
			}catch(Exception e) {
				System.out.println("Erro na busca"+ e);
			}
			return lista;
		}
		
}

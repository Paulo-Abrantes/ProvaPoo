package dominio;


public class Colmeia {

	private int id;
	private int tipo;
	private String status;
	

	public Colmeia() {
		
	}

	public Colmeia(int id, int tipo, String status) {
		this.id = id;
		this.tipo = tipo;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
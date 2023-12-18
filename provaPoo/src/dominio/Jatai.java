package dominio;

import java.sql.Date;

public class Jatai extends Producao {

	private float producaoJ;
	private int tipo;
	private Date dia;
	private int ID;
	
	public Jatai() {
		
	};
	public Jatai(float producaoJ, int tipo, Date dia, int ID) {
		this.producaoJ = producaoJ;
		this.tipo = tipo;
		this.dia = dia;
		this.ID = ID;
	}
	public float getProducaoJ() {
		return producaoJ;
	}
	public void setProducaoJ(float producaoJ) {
		this.producaoJ = producaoJ;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Date getDia() {
		return dia;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
}

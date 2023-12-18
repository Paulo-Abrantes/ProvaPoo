package dominio;

import java.sql.Date;

public class Producao {

	private float producaoT;
	private int tipo;
	private Date dia;
	private int ID;
	
	public Producao() {
		
	};
	public Producao(float producaoT, int tipo, Date dia, int ID) {
		this.producaoT = producaoT;
		this.tipo = tipo;
		this.dia = dia; 
		this.ID = ID;
	}
	public float getProducaoT() {
		return producaoT;
	}
	public void setProducaoT(float producaoT) {
		this.producaoT = producaoT;
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

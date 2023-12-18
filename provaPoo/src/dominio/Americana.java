package dominio;
import java.sql.Date;

public class Americana extends Producao{
	
	private float producaoA;
	private int tipo;
	private Date dia;
	private int ID;
	
	public Americana() {
		
	};
	public Americana(float producaoA, int tipo, Date dia, int ID) {
		this.producaoA = producaoA;
		this.tipo = tipo;
		this.dia = dia;
		this.ID = ID;
	}
	public float getProducaoA() {
		return producaoA;
	}
	public void setProducaoA(float producaoA) {
		this.producaoA = producaoA;
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

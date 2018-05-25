package gt.edu.umg.compiladores.imt.dto;

public class Produccion {
	
	private String izquierda;
	private String derecha;
	
	public Produccion() {
		super();
	}
	
	public Produccion(String izquierda) {
		this.izquierda = izquierda;
	}
	
	public Produccion(String izquierda, String derecha) {
		this.izquierda = izquierda;
		this.derecha = derecha;
	}
	
	public String getIzquierda() {
		return izquierda;
	}
	public void setIzquierda(String izquierda) {
		this.izquierda = izquierda;
	}
	public String getDerecha() {
		return derecha;
	}
	public void setDerecha(String derecha) {
		this.derecha = derecha;
	}
}

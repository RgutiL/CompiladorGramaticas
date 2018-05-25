package gt.edu.umg.compiladores.imt.dto;

public class Token {
	
	private Tipos tipo;
    private String valor;
    
    public Token() {
    	super();
    }
    
    public Token(Tipos tipo, String valor) {
    	this.tipo = tipo;
    	this.valor = valor;
    }
    
	public Tipos getTipo() {
		return tipo;
	}
	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Token [tipo=" + tipo + ", valor=" + valor + "]";
	}
}

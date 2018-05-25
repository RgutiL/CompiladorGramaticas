package gt.edu.umg.compiladores.imt.dto;

public enum Tipos {
	
	VARIABLE("[.]+"),
	TERMINAL("[.]+"),
	EPSILON("e"),
	ASIGNADOR(":"),
	OPCIONALIDAD("\\|"),
	LINEANUEVA("\\n"),
	APOSTROFE("'");

	public final String patron;

	Tipos(String s) {
		this.patron = s;
	}
}

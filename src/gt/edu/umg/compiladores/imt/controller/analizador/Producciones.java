package gt.edu.umg.compiladores.imt.controller.analizador;

import java.util.ArrayList;
import java.util.List;

import gt.edu.umg.compiladores.imt.dto.Produccion;
import gt.edu.umg.compiladores.imt.dto.Tipos;
import gt.edu.umg.compiladores.imt.dto.Token;

public class Producciones {
	
	public static List<Produccion> convertir(List<Token> tokens) {
		List<Produccion> resultado = new ArrayList<>();
		
		boolean derecha = false;
		Produccion prod = null;
		StringBuilder derechaStr = null;
		for(Token token: tokens) {
			
			if(Tipos.VARIABLE.equals(token.getTipo()) && !derecha) {
				prod = new Produccion();
				prod.setIzquierda(token.getValor());
				derechaStr = new StringBuilder();
				derecha = true;
			} else if(Tipos.OPCIONALIDAD.equals(token.getTipo())) {
				prod.setDerecha(derechaStr.toString());
				resultado.add(prod);
				prod = new Produccion(prod.getIzquierda());
				derechaStr = new StringBuilder();
			} else if(Tipos.LINEANUEVA.equals(token.getTipo())) {
				prod.setDerecha(derechaStr.toString());
				resultado.add(prod);
				derecha = false;
			} else if(!Tipos.ASIGNADOR.equals(token.getTipo())) {
				derechaStr.append(token.getValor());
			}
		}
		
		return resultado;
	}

}

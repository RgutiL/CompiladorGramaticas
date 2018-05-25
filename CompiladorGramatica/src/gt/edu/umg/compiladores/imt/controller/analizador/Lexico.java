package gt.edu.umg.compiladores.imt.controller.analizador;

import java.util.ArrayList;
import java.util.List;

import gt.edu.umg.compiladores.imt.dto.Tipos;
import gt.edu.umg.compiladores.imt.dto.Token;

public class Lexico {
	
	private Lexico() {
	}

	public static List<Token> analizar(String cadena) {
		List<Token> tokens = new ArrayList<>();
		
		System.out.println("Analisis Lexico: ");
		
		String[] lineas = cadena.split("\\n");
		
		List<String> variableDefinida = new ArrayList<>();
		List<String> variableDerecha = new ArrayList<>();
		
		//Capturando las variables definidas
		for(int i = 0; i < lineas.length; i++) {
			String[] partesLinea = lineas[i].split(":");
			variableDefinida.add(partesLinea[0]);
		}
		
		for(int i = 0; i < lineas.length; i++) {
			String[] partesLinea = lineas[i].split(":");
			if(partesLinea.length == 2) {
				tokens.add(new Token(Tipos.VARIABLE, partesLinea[0]));
				tokens.add(new Token(Tipos.ASIGNADOR, ":"));
				boolean terminal = false;
				StringBuilder terminalTxt = null;
				StringBuilder variable = null;
				for(int j = 0; j < partesLinea[1].length(); j++) {
					char c = partesLinea[1].charAt(j);
					
					switch (c) {
					case '\'':
						terminal = !terminal;
						if(terminal) {
							//Valida si existe una variable en buffer entonces se agrega a la lista de tokens
							if(variable != null && variable.length() > 0) {
								tokens.add(new Token(Tipos.VARIABLE, variable.toString()));
								variableDerecha.add(variable.toString());
								variable = null;
							}
							terminalTxt = new StringBuilder();
						} else {
							if(terminalTxt != null && terminalTxt.length() > 0) {
								System.out.println("Terminal: " + terminalTxt.toString());
								tokens.add(new Token(Tipos.TERMINAL, terminalTxt.toString()));
							}
							terminalTxt = null;
						}
						tokens.add(new Token(Tipos.APOSTROFE, Character.toString(c)));
						break;
					case '|':
						//Valida si existe una variable en buffer entonces se agrega a la lista de tokens
						if(variable != null && variable.length() > 0) {
							tokens.add(new Token(Tipos.VARIABLE, variable.toString()));
							variableDerecha.add(variable.toString());
							variable = null;
						}
						tokens.add(new Token(Tipos.OPCIONALIDAD, Character.toString(c)));
						break;
					case 'e':
						if(!terminal) {
							//Valida si existe una variable en buffer entonces se agrega a la lista de tokens
							if(variable != null && variable.length() > 0) {
								tokens.add(new Token(Tipos.VARIABLE, variable.toString()));
								variableDerecha.add(variable.toString());
								variable = null;
							}
							tokens.add(new Token(Tipos.EPSILON, Character.toString(c)));
						} else {
							terminalTxt.append(c);
						}
						break;
					default:
						if(terminal) {
							//La bandera de terminal esta activa por lo que se agrega el caracter al buffer de terminal
							terminalTxt.append(c);
						} else {
							//Si el buffer de variables no esta iniciado lo activa
							if(variable == null) {
								variable = new StringBuilder();
							}
							variable.append(c);
							if((j+1) < partesLinea[1].length() && !Character.isDigit(partesLinea[1].charAt(j+1))) {
								String tempVar = variable.toString();
								for(String varDef: variableDefinida) {
									if(varDef.equals(tempVar)) {
										tokens.add(new Token(Tipos.VARIABLE, tempVar));
										variableDerecha.add(tempVar);
										variable = null;
										break;
									}
								}
							}
							
						}
						break;
					}
				}
				
				//Valida si existe una variable en buffer entonces se agrega a la lista de tokens
				if(variable != null && variable.length() > 0) {
					tokens.add(new Token(Tipos.VARIABLE, variable.toString()));
					variableDerecha.add(variable.toString());
					variable = null;
				}
				
				tokens.add(new Token(Tipos.LINEANUEVA, "\\n"));
				
			} else {
				tokens.add(new Token(Tipos.VARIABLE, partesLinea[0]));
				tokens.add(new Token(Tipos.LINEANUEVA, "\\n"));
			}
		}
		
		/*
		for(int i = 0; i < cadena.length(); i++) {
			String token = Character.toString(cadena.charAt(i));
			Tipos tokent = null;
			boolean matched = false;
			boolean space = false;
			
			for(Tipos tokenTipo : Tipos.values()) {
				Pattern patron = Pattern.compile(tokenTipo.patron);
				Matcher matcher = patron.matcher(token);
				if(matcher.matches()) {
					tokens.add(new Token(tokenTipo, token));
					matched = true;
					space = true;
					tokent = tokenTipo;
					break;
				} else if(token.equals(" ")) {
					space = true;
				}
			}
			
			if(!matched || !space) {
				throw new RuntimeException("Se encontro un token no valido");
			} else {
				System.out.println("Tipo: " + tokent + " valor: " + token);
			}
		}*/
		
		return tokens;
	}
}

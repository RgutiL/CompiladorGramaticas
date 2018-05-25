package gt.edu.umg.compiladores.imt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import gt.edu.umg.compiladores.imt.controller.analizador.Lexico;
import gt.edu.umg.compiladores.imt.controller.analizador.Producciones;
import gt.edu.umg.compiladores.imt.controller.analizador.Sintactico;
import gt.edu.umg.compiladores.imt.dto.Produccion;
import gt.edu.umg.compiladores.imt.dto.Tipos;
import gt.edu.umg.compiladores.imt.dto.Token;

@WebServlet("/procesar")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 5
)
public class Procesar extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public Procesar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Token> tokens = null;
		String cadena = null;
		Set<String> variables = new HashSet<>();
		Set<String> terminales = new HashSet<>();
		List<Produccion> producciones = null;
		
		String mensajeError = null;
		
		try {
			InputStream contenido;
			
			Part archivo = request.getPart("file");
			String nombreArchivo = getFileName(archivo);
			contenido = archivo.getInputStream();
			System.out.println(nombreArchivo);
			
			StringWriter writer = new StringWriter();
			IOUtils.copy(contenido, writer);
			cadena = writer.toString();
			
			cadena = cadena.replaceAll("\\n{2,}", "".trim());
			
			tokens = Lexico.analizar(cadena);
			
			Sintactico.analizar(tokens);
			
			producciones = Producciones.convertir(tokens);
			
			for(Token token: tokens) {
				if(token.getTipo() == Tipos.VARIABLE) {
					variables.add(token.getValor());
				}else if(token.getTipo() == Tipos.TERMINAL) {
					terminales.add(token.getValor());
				}
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			mensajeError = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError = "Ocurrio un error interno.";
		}
		
		request.setAttribute("cadena", cadena);
		request.setAttribute("terminales", terminales);
		request.setAttribute("variables", variables);
		request.setAttribute("producciones", producciones);
		request.setAttribute("mensajeError", mensajeError);
		request.setAttribute("tokens", tokens);
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
	
	public static String getValue(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}
	
	public static String getFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}

}

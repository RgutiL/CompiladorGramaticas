<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Compilador Gramatica</title>
<link href="resources/css/template.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="resources/js/template.js"></script>
</head>
<body>
<div class="container">
<div class="forma row">
<div class="cell cell100">
<form action="procesar" class="form-signin" method="post"
	enctype="multipart/form-data">
<div class="row">
<div class="cell cell100 align-center">
<h3>Seleccione un archivo y luego click en el botón <b>Analizar:</b></h3>
<br />
</div>
<div class="cell cell100 align-center">
<div class="upload-btn-wrapper">
<button class="btn">Seleccionar un archivo</button>
<input class="file-chooser" type="file" name="file" /></div>
<button type="submit">Analizar</button>
</div>
<div class="cell cell50 align-left"></div>
</div>
</form>
</div>
<div class="row">
<div class="cell cell30 cell-box cs-right-10">
<h3><b>Contenido archivo </b></h3>
</div>
<div class="cell cell20 cell-box cs-right-10">
<h3><b>Variables <c:if
	test="${not empty requestScope.variables}">
	<c:out value="(${fn:length(requestScope.variables)})" />
</c:if> </b></h3>
</div>
<div class="cell cell20 cell-box cs-right-10">
<h3><b>Terminales <c:if
	test="${not empty requestScope.terminales}">
	<c:out value="(${fn:length(requestScope.terminales)})" />
</c:if> </b></h3>
</div>
<div class="cell cell30 cell-box cs-right-10">
<h3><b>Producciones <c:if
	test="${not empty requestScope.producciones}">
	<c:out value="(${fn:length(requestScope.producciones)})" />
</c:if> </b></h3>
</div>
</div>
</div>
<div class="resultado">
<div class="row h100">
<div class="cell cell30 cell-box cs-right-10 h100"><textarea><c:out
	value="${requestScope.cadena}" /></textarea></div>
<div class="cell cell20 cell-box cs-right-10 h100">
<div class="result-box"><c:forEach
	items="${requestScope.variables}" var="variable">
	<c:out value="${variable}" />
	<br />
</c:forEach></div>
</div>
<div class="cell cell20 cell-box cs-right-10 h100">
<div class="result-box"><c:forEach
	items="${requestScope.terminales}" var="terminal">
	<c:out value="${terminal}" />
	<br />
</c:forEach></div>
</div>
<div class="cell cell30 cell-box cs-right-10 h100">
<div class="result-box no-padding"><c:forEach
	items="${requestScope.producciones}" var="produccion">
	<div class="row">
	<div class="cell cell20"><c:out value="${produccion.izquierda}" /></div>
	<div class="cell cell80"><c:out value="${produccion.derecha}" /></div>
	</div>
</c:forEach></div>
</div>
</div>
<%-- 
		  	<c:if test="${not empty requestScope.tokens}">
		  	<div class="row">
		  		<div class="cell cell100 cell-box cs-right-10"><h3><b>Tabla de Simbolos</b></h3></div>
		  		<div class="cell cell100">
		  		<table>
		  			<thead>
		  				<tr>
	  						<th>No.</th>
		  					<th>Token</th>
		  					<th>Tipo</th>
		  				</tr>
		  			</thead>
		  			<tbody>
		  				<c:forEach items="${requestScope.tokens}" var="token" varStatus="status">
		  				<tr>
		  					<td><c:out value="${status.count}"/> </td>
		  					<td><c:out value="${token.valor}"/> </td>
		  					<td><c:out value="${token.tipo}"/> </td>
		  				</tr>
		  				</c:forEach>
		  			</tbody>
		  		</table>
		  		</div>
		  	</div>
		  	</c:if>
		  	--%></div>
</div>
</body>
</html>
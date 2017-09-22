<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:base title="New Restaurant">
	<jsp:attribute name="head">
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDm-mzYm2bUccqo7GRnvauQw0yXeVz5mYA&v=3.exp&sensor=false" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/newRestaurantMap.js" type="text/javascript"></script>
	</jsp:attribute>

<jsp:attribute name="errorInContent">
 <c:if test="${not empty error }">
  <c:if test="${not empty error['ERROR IN CONTENT'] }">
<label>${error['ERROR IN CONTENT'] }</label>
</c:if>
</c:if>
</jsp:attribute>

<jsp:attribute name="messageInContent">
 <c:if test="${not empty message }">
<label>${message}</label>
</c:if>
</jsp:attribute> 

	<jsp:attribute name="content">
		<div class="col-md-8">
			<h3>Nuevo Restaurant</h3>
			
			
			<form:form action="../SaveNewRestaurant" method="POST" modelAttribute="newRestaurant" id="restaurantForm" class="form-horizontal formulario">
	  			
	  			
	  			<div class="mapa">
			       	<h2>Seleccione la ubicacion en el mapa</h2>
			    	<div id="map-canvas"></div>
			    </div>
			    
			    <div class="col-md-12">
					<div class="form-group">
						<form:label class="control-label" path="name">Nombre:</form:label>
						<form:input type="text" class="form-control" path="name" id="name" placeholder="Nombre" required="true" minlength="2" maxlength="100" />
					</div>
			  
					<div class="form-group">
						<label class="control-label">Direccion:</label>
						<form:input type="text" class="form-control" path="ubication.direction" id="addres" placeholder="Direccion" readonly="true" required="true" title="Debe marcar una direccion en el mapa (Si no puede ver el mapa asegurese de tener activado javascript en su navegador)"/>
					</div> 
					
					<div class="form-group">
						<input type="submit" class="btn btn-danger form-control" value="Registrar"/>
					</div>
					
					<form:hidden  path="ubication.longitude" id="ubicationlongitude" />
				    <form:hidden  path="ubication.latitude" id="ubicationlatitude" />
	 			</div>
			</form:form>
	  	</div>
	</jsp:attribute>



</mt:base>
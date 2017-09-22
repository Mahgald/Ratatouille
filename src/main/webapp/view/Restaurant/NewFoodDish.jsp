<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:base title="New FoodDish">

	<jsp:attribute name="content">
			



 <div class="col-md-8 col-md-offset-2">
 <h3>Nuevo plato del menu para ${newFoodDish.restaurant.name}</h3>
  <form:form action="SaveNewFoodDish" method="POST" modelAttribute="newFoodDish" id="foodDishForm" class="form-horizontal formulario">
  
  <div class="form-group">
   <form:label class="control-label col-sm-2" path="name">Nombre: </form:label>
   <div class="col-sm-6">
    <form:input class="form-control" path="name" id="name" placeholder="Nombre"/>
   <br>
   </div>
  <div class="form-group">
   <form:label class="control-label col-sm-2" path="price">Precio: </form:label>
   <div class="col-sm-6">
    <form:input class="form-control" path="price" id="price" placeholder="Nombre"/>
   <br>
   </div>
     <div class="form-group">
   <form:label class="control-label col-sm-2" path="description">Descripcion: </form:label>
   <div class="col-sm-6">
    <form:input class="form-control" path="description" id="description" placeholder="Nombre"/>
   <br>
   </div>

   
    <div class="form-group">
   <form:label class="control-label col-sm-2" path="name">restaurant: </form:label>
   <div class="col-sm-6">
    <form:hidden path="restaurant.idDomainObject" id="restaurant" />
   <br>
   </div> 
   
    <div class="form-group">
   <form:label class="control-label col-sm-2" path="description">help: </form:label>
   <div class="col-sm-6">
    <input class="form-control" name="help" value= "${idvalor }" id="description" placeholder="Nombre"/>
   <br>
   </div>
   
  </div>
  

  


  



  

<div class="form-group">
<div class="col-sm-6 col-sm-offset-2">
 <div class="error" id="error"></div>
</div>
 </div> 
  <div class="form-group">
    <div class="col-sm-6 col-sm-offset-2">
     <input type="submit" class="btn btn-danger form-control" value="Registrar" />
    </div>
   </div>
 </form:form>
  <br><br><br>
 </div>
 
 
 
 
	</jsp:attribute>



</mt:base>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:base title="SignUp">



<jsp:attribute name="errorInContent">
 <c:if test="${not empty error }">
  <c:if test="${not empty error['ERROR IN CONTENT'] }">
<label>${error['ERROR IN CONTENT'] }</label>
</c:if>
</c:if>
</jsp:attribute>

<jsp:attribute name="content">
 <div class="col-md-8 col-md-offset-2">
 <h3>Nuevo Usuario</h3>
 <form:form action="CrearUser" method="POST" modelAttribute="newUser" id="userForm" class="form-horizontal formulario">
  
  <div class="form-group">
   <form:label class="control-label col-sm-2" path="name">Nombre: </form:label>
   <div class="col-sm-6">
    <form:input minlength="3" class="form-control" path="name" id="name" placeholder="Nombre" required="true" />
   <br>
   </div>
  </div>
  

  <div class="form-group">
   <form:label class="control-label col-sm-2" path="lastName">Apellido: </form:label>
   <div class="col-sm-6">
    <form:input minlength="3" class="form-control" path="lastName" id="lastName" placeholder="Apellido" required="true"/>
    <br>
   </div> 
  </div>
  
   <div class="form-group">
   <form:label class="control-label col-sm-2" path="email">Email: </form:label>
   <div class="col-sm-6">
    <form:input type="email" class="form-control" path="email" id="email" placeholder="Email" required="true"/>
    <br>
   </div> 
  </div>
  
   <div class="form-group">
   <form:label class="control-label col-sm-2" path="password">Contrase&ntilde;a </form:label>
   <div class="col-sm-6">
    <form:password minlength="6" maxlength="20" class="form-control" path="password" id="password" placeholder="Contraseña" required="true"/>
    <br>
   </div> 
  </div>
  
  <div class="form-group">
   <form:label class="control-label col-sm-2" path="birthdate">Fecha de Nacimiento (dd/mm/aa): </form:label>
   <div class="col-sm-6">
    <form:input class="form-control" path="birthdate" id="birthdate" placeholder="Fecha de Nacimiento:" required="true"/>
    <br>
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:base title="Perfil">
<jsp:attribute name="head">

</jsp:attribute>


<jsp:attribute name="contentOfResponsible">
<c:catch var="exception">
<c:if test="${not empty user.configurations }">
			<div class="wrap">
			<aside id="side-menu" class="aside" role="navigation">   
	         
			<ul class="nav nav-list accordion">                    
				<li class="nav-header pos">
					<div class="link">
						<i class="fa fa-lg fa-users"></i>Restaurantes a cargo<i class="fa fa-chevron-down"></i>
					</div>
					<ul class="submenu restaurantsResponsibleScroll">
						<c:forEach items="${user.configurations}" var="configuration">
								<!-- CAMBIAR LI -->
							<li><a href="${pageContext.request.contextPath}/Restaurant/ShowRestaurant?idRestaurant=${configuration.restaurant.idDomainObject}">${configuration.restaurant.name} ${configuration.restaurant.ubication.direction}</a></li> 
						</c:forEach>	
				</ul>
			</li>
	
			
	
		</ul>
	</aside>
		</div>
	</c:if>
</c:catch>
</jsp:attribute>

<jsp:attribute name="content">
<div class="col-md-9 bot dataPerfil">

 <div >
   <label class=" " >Nombre: </label>
 <label class=" pull-right"  >${user.name} </label>
   </div>
 <div >
   <label class=""  >Apellido: </label>
 <label  class="pull-right">${user.lastName} </label>
   </div>
   <div >
   <label class=""  >Email: </label>
 <label  class="pull-right">${user.email} </label>
   </div>
   <div >
   <label class=""  >Fecha de nacimiento: </label>
 <label class="pull-right">${user.birthdate} </label>
   </div>


</div>
</jsp:attribute>

<jsp:attribute name="messageInContent">
 <c:if test="${not empty message }">
<label>${message}</label>
</c:if>
</jsp:attribute>

<jsp:attribute name="errorInContent">
 <c:if test="${not empty error }">
  <c:if test="${not empty error['ERROR IN CONTENT'] }">
<label>${error['ERROR IN CONTENT'] }</label>
</c:if>
</c:if>
</jsp:attribute>

<jsp:attribute name="contentOfUser">

				<ul class="nav nav-list accordion">                    
		          
		            
		          <li class="nav-header">
		            <div class="link"><i class="fa fa-lg fa-cogs"></i>Informacion personal<i class="fa fa-chevron-down"></i></div>
		            <ul class="submenu">
		              
		              <li><a href="#"><span class="fa fa-md fa-pencil-square-o" data-toggle="modal" data-target="#ChangeInformationOfUser"> Modificar</span></a></li>
		              <c:if test="${  user.lowLogic }">
		              <li><a href="#"><span class="fa fa-md fa-plus-square" data-toggle="modal" data-target="#ReactivateUser"> Reactivar</span></a></li>
		              </c:if>
		              <c:if test="${not  user.lowLogic }">
		              <li><a href="#"><span class="fa fa-md fa-minus-square" data-toggle="modal" data-target="#DisableUser"> Dar de baja</span></a></li>
		              </c:if>
		            </ul>
		          </li>  
		          
		           <li class="nav-header">
		            <div class="link"><i class="fa fa-lg fa-book"></i>Contrase&ntilde;a<i class="fa fa-chevron-down"></i></div>
		            <ul class="submenu">
		              <li><a href="#"><span class="fa fa-md fa-plus-square" data-toggle="modal" data-target="#ChangePassword"> Cambiar contrase&ntilde;a</span></a></li>
		              <li><a href="#"><span class="fa fa-md fa-pencil-square-o" data-toggle="modal" data-target="#RecoverPassword"> Recuperar contrase&ntilde;a</span></a></li>
		            </ul>
		          </li>
		          
		         
		          
		      </ul>
		      
		      
		      
		      
		      

	
	
</jsp:attribute>

<jsp:attribute name="modalsOfUser">
<!-- DESABILITAR usuario -->
	<div class="modal fade" id="DisableUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header headers">
					<h5 class="modal-title" id="exampleModalLabel">DARSE DE BAJA</h5>

				</div>
				<div class="modal-body">
					<div>	
						<form:form action="/Ratatouille/User/DeleteUser" method="POST" modelAttribute="user" id="" class="">
	
						<label>Esta seguro que desea desactivar su cuenta ${user.name }  ${user.lastName }</label>
						<form:hidden path="idUser"/>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default"> Desactivar cuenta</button> 
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</form:form>	
			</div>
		</div>
	</div>
	</div>
	
	<!-- REACTIVAR usuario -->
	<div class="modal fade" id="ReactivateUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header headers">
					<h5 class="modal-title" id="exampleModalLabel">REACTIVAR CUENTA</h5>
					
				</div>
				<div class="modal-body">
					<div>	
						<form:form action="/Ratatouille/User/ReactiveUser" method="POST" modelAttribute="user" id="" class="">
	
						<label>Esta seguro que desea reactivar su cuenta ${user.name }  ${user.lastName }</label>
						<form:hidden path="idUser"/>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default"> Reactivar</button> 
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</form:form>	
			</div>
		</div>
	</div>
	</div>
	
	<!-- MODIFICAR INFORMACION DEL USER -->
	<div class="modal fade" id="ChangeInformationOfUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header headers">
					<h5 class="modal-title" id="exampleModalLabel">MODIFICAR DATOS PERSONALES</h5>
					
				</div>
				<div class="modal-body">
						<c:if test="${not empty error}">
							<c:if test="${not empty error['ERROR MODAL CHANGE INFORMATION OF USER']}">
				    			<div>
				    				<label>${error['ERROR MODAL CHANGE INFORMATION OF USER']}</label>
				   				</div>
				    		</c:if>
				    	</c:if>
				
					<div>	
						<form:form action="/Ratatouille/User/ChangeInformationOfUser" method="POST" modelAttribute="user" id="" class="">
						
						<label> Nombre: ${user.name }</label>
						 <label>Apellido: ${user.lastName }</label>
						<label>Email: ${user.email }</label>
						 <label>Fecha de cumpleanos: ${user.birthdate }</label>
						<form:hidden path="idUser"/>
						
						<div class="form-group">
					<label class="control-label"> Nombre: </label><form:input path="name" class="form-control" required="true" minlength="3"/>
					</div>
					<div class="form-group">
					<label class="control-label">Apellido: </label><form:input path="lastName" class="form-control" required="true" minlength="3"/>
					</div>
					<div class="form-group">
					<label class="control-label">Email: </label><form:input type="email" path="email" class="form-control" required="true" />
					</div>
					<div class="form-group">
					<label class="control-label">Fecha de nacimiento: </label><form:input type="datetime-local" path="birthdate" class="form-control" required="true"/>
					</div>
					</div>
					
					
					
					
					<div class="modal-footer">
						<button type="submit" class="btn btn-default">Modificar</button> 
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</form:form>	
			</div>
		</div>
	</div>
	</div>
	
		<!-- MODIFICAR PASSWORD -->
	<div class="modal fade" id="ChangePassword" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header headers">
					<h5 class="modal-title" id="exampleModalLabel">MODIFICAR CONTRASE&Ntilde;A</h5>
					
				</div>
				<div class="modal-body">
						<c:if test="${not empty error}">
							<c:if test="${not empty error['ERROR MODAL CHANGE PASSWORD OF USER']}">
				    			<div>
				    				<label>${error['ERROR MODAL CHANGE PASSWORD OF USER']}</label>
				   				</div>
				    		</c:if>
				    	</c:if>
				
					<div>	
						<form:form action="/Ratatouille/User/ChangePassword" method="POST" modelAttribute="user" id="" class="">
						
						<label>Complete los datos para cambiar su contrase&ntilde;a</label>
						<form:hidden path="idUser"/>
						<div class="form-group">
						<label class="control-label">Contrase&ntilde;a actual: </label><input type="password"name="oldPassword" id="oldPassword" class="form-control"/>
						</div>
						<div class="form-group">
						<label class="control-label">Nueva contrase&ntilde;a: </label><input type="password"name="newPassword" id="newPassword" class="form-control"/>
						</div>
						<div class="form-group">
						<label class="control-label">Confirmar nueva contrase&ntilde;a: </label><input type="password"name="reNewPassword" id="reNewPassword" class="form-control"/>
						</div>
					</div>
					
					
					
					
					<div class="modal-footer">
						<button type="submit" class="btn btn-default"> Modificar</button> 
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</form:form>	
			</div>
		</div>
	</div>
	</div>
	
		<!-- RECUPERAR PASSWORD -->
	<div class="modal fade" id="recoverPassword" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header headers">
					<h5 class="modal-title" id="exampleModalLabel">RECUPERAR CONTRASE&Ntilde;A</h5>
					
				</div>
				<div class="modal-body">
					<div>	
					<label>esta funcionalidad aun no esta disponible</label>
						<form:form action="/Ratatouille/User/RecovePassword" method="POST" modelAttribute="user" id="" class="">
						<form:hidden path="idUser"/>
						
					</div>
					
					
					
					
					<div class="modal-footer">
						<button type="submit" class="btn btn-default"> Solicitar </button> 
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</form:form>	
			</div>
		</div>
	</div>
	</div>
	</jsp:attribute>
	<jsp:attribute name="scriptsForShowModalWhenHaveErrors">

	<c:if test="${not empty error}">
		<c:if test="${not empty error['ERROR MODAL CHANGE INFORMATION OF USER']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
        		$('#ChangeInformationOfUser').modal('show');
    		});
		</script> 
		</c:if>
	</c:if>
	<c:if test="${not empty error}">
		<c:if test="${not empty error['ERROR MODAL CHANGE PASSWORD OF USER']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
        		$('#ChangePassword').modal('show');
    		});
		</script> 
		</c:if>
	</c:if>
</jsp:attribute>

</mt:base>
<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<%@ attribute name="content" fragment="true" %>
<%@ attribute name="contentOfResponsible" fragment="true" %>
<%@ attribute name="contentOfUser" fragment="true" %>
<%@ attribute name="modalsOfUser" fragment="true" %>
<%@ attribute name="modalsOfResponsible" fragment="true" %>
<%@ attribute name="modalsOfResponsibleOf" fragment="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="errorInContent" fragment="true" %>
<%@ attribute name="messageInContent" fragment="true" %>
<%@ attribute name="scriptsForShowModalWhenHaveErrors" fragment="true" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
<head>
	
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<!-- Fonts CSS -->
	<link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fjalla+One" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Arvo" rel="stylesheet">
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap-social.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font-awesome.min.css">
	<!-- Our CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/simple-sidebar.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/googlemapStyle.css">
	<link rel="icon" href="<%=request.getContextPath()%>/resources/img/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/base.css">
	
	
	<!-- Scripts -->
	<script src="<%=request.getContextPath()%>/resources/js/jquery224.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/custom.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/angularApp.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/app.js"></script>
	
	<jsp:invoke fragment="head"></jsp:invoke>
	<title>${title}</title>

</head>

<body ng-app="app">
			  
	<nav class="navbar navbar-default navbar-fixed-top navbar-background">
	
	
	
	
		<div class="col-md-offset-1">
			<div class="navbar-header" ng-controller="SearchRestaurantController">
			 	<!-- SEARCH MODAL -->
			<div class="modal " id="searchModal" tabindex="-1" role="dialog"  data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <div class="modal-header headers">
				    <h5 class="modal-title" id="exampleModalLabel">BUSCANDO RESTAURANTS POR CERCANIA</h5>
				    </div>
				    <div class="modal-body"  >
				    
				    	<%-- <c:if test="${not empty error}">
							<c:if test="${not empty error['ERROR LOGIN DATOS NO VALIDOS']}">
				    			<div>
				    				<label>${error['ERROR LOGIN DATOS NO VALIDOS']}</label>
				   				</div>
				    		</c:if>
				    	</c:if> --%>
					 
						<div class="form-group">
						
						<label class="control-label">Parte del nombre:</label><input type="search" class="form-control input-sm" aria-describedby="basic-addon1" placeholder="Buscar" ng-keyup="beginSearch()" ng-model="texto1" >
				       <label class="control-label">Rango de distancia: </label><input type="number" min="0" class="form-control input-sm" ng-show="searchByDistance" ng-model="distanceToSearch" ng-keyup="beginSearch()"/>
						</div>
						
						
						<div class="form-group" ng-show="latitude != 0  ">
							<label >{{latitude}}</label>
							<lable >{{longitude}}</lable>
						</div>
						<div>resultados
						 <ul ng-show="resultsByDistance.length > 0 " class="resultSearcherRestaurants searchScroll">
				    <li ng-show="resultsByDistance.length > 0" ng-repeat="result in resultsByDistance | limitTo:7" >
				  
				    <a class="pull-left" href="<%=request.getContextPath()%>/Restaurant/ShowRestaurant?idRestaurant={{ result.hasOwnProperty('restaurant') ? (result.restaurant.idDomainObject)+'&idFoodDish=':''}}{{result.idDomainObject}}">
							   <span ng-show="!result.hasOwnProperty('restaurant')" class="fa fa-cutlery redcolor"></span>
							 {{result.name}} </a> 
							<span>{{result.ubication.direction}}</span>
				    </li>
				    </ul>
						</div>
						
					</div>	
					
				    <div class="modal-footer">
				    	<!-- <button type="button" class="btn btn-default"> Ingresar</button>  -->
				    	<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="dontSearchByDistance()">Cancelar</button>
				    </div>
				    	
				    </div>
			 	</div>
			</div>
	
				<a class="navbar-brand ratatouille ">Ratatouille</a>
				
				<span class="navbar-form " role="search" >
			
				
				    <div class="form-group input-group">
				        <input type="search" class="form-control input-sm" aria-describedby="basic-addon1" placeholder="Buscar" ng-keyup="beginSearch()" ng-model="texto1" >
				       	<span class="input-group-addon input-sm dropdown-toggle" id="basic-addon1"><span class="fa fa-search" aria-hidden="true"></span></span>
				    </div>
				    <div class="form-group input-group">
				    	<label class="checkbox-inline"><input type="checkBox"  ng-model="searchByDistance"  ng-change="changeCriterionOfSearch()"/> Distancia</label>
					</div>
				    <div class="form-group input-group distanceInput">
				    	<input type="number" min="0" class="form-control input-sm" ng-show="searchByDistance" ng-model="distanceToSearch"/>
				   </div>
				    
				    <ul ng-show="results.length >0 && texto1.length >=3" class="resultSearcherRestaurants searchScroll">
				    <li ng-show="texto1.length >=3" ng-repeat="result in results | limitTo:7" >
				  
				    <a class="pull-left" href="<%=request.getContextPath()%>/Restaurant/ShowRestaurant?idRestaurant={{ result.hasOwnProperty('restaurant') ? (result.restaurant.idDomainObject)+'&idFoodDish=':''}}{{result.idDomainObject}}">
							   <span ng-show="!result.hasOwnProperty('restaurant')" class="fa fa-cutlery redcolor"></span>
							 {{result.name}} <label ng-show="result.hasOwnProperty('restaurant')"> ({{result.restaurant.name}})</label></a> 
							<span>{{result.hasOwnProperty('description') ? (result.description)+' ':(result.ubication.direction)+''}}</span>
				    </li>
				    </ul>
				    
				    
				    
				    
				   <%--  <div id="" ng-show="texto1.length >=3" class="resultSearcherRestaurants">
				    
				    
				    	<span ng-show="texto1.length >=3" ng-repeat="result in results | limitTo:7" >
								<!-- <a class="dropdown-item" href=""><label>{{user.name}}</label></a> -->
						 <a class="" href="<%=request.getContextPath()%>/Restaurant/ShowRestaurant?idRestaurant={{ result.hasOwnProperty('restaurant') ? (result.restaurant.idDomainObject)+'&idFoodDish=':''}}{{result.idDomainObject}}">
							 <span ng-show="!result.hasOwnProperty('restaurant')" class="fa fa-cutlery redcolor"></span>
							 <label>{{result.name}} </label><label ng-show="result.hasOwnProperty('restaurant')"> ({{result.restaurant.name}})</label></a> 
							 <label>{{result.description}}</label>
					    	
				    	
						</span>
				    </div> --%>
			
				</span>
								
				<%-- 
				 		VIEJO BUSCAR
				 
				 	<span class="input-group navbar-form" ng-controller="SearchRestaurantController">
					<input type="search" ng-keyup="beginSearch()" ng-model="texto1" class="form-control input-sm" aria-haspopup="true" aria-expanded="false" aria-describedby="basic-addon1" placeholder="Buscar">
					<span class="input-group-addon input-sm " id="basic-addon1"><span class="fa fa-search" aria-hidden="true"></span></span>
					
					<label><input type="checkBox" class="form-control input-sm" ng-model="searchByDistance"  ng-change="changeCriterionOfSearch()"/> Distancia</label>
					<input type="number" min="0" class="form-control input-sm" ng-show="searchByDistance" ng-model="distanceToSearch"/>
					<div id="sekkerDropdown" ng-show="texto1.length >=3">
				        	
						<span ng-show="texto1.length >=3" ng-repeat="result in results | limitTo:7">
						
						<!-- <a class="dropdown-item" href=""><label>{{user.name}}</label></a> -->
						 <a class="" href="<%=request.getContextPath()%>/Restaurant/ShowRestaurant?idRestaurant={{ result.hasOwnProperty('restaurant') ? (result.restaurant.idDomainObject)+'&idFoodDish=':''}}{{result.idDomainObject}}">
						 <span ng-show="!result.hasOwnProperty('restaurant')" class="fa fa-cutlery glycolor"></span>
						 <label>{{result.name}} </label><label ng-show="result.hasOwnProperty('restaurant')"> ({{result.restaurant.name}})</label></a> 
						 <label>{{result.description}}</label>
				    	<div role="separator" class="dropdown-divider"></div>
				    	
				    	</span>
				    </div>
				</span> --%> 
				
				
				
				
				
				
				
				
				
				
				
				<!-- <span class="input-group" ng-controller="SearchController">
					<label>begin search</label>
					<input type="text" ng-keyup="beginSearch()" ng-model="texto1" class="form-control input-sm" aria-describedby="basic-addon1" placeholder="Buscar">
					<span class="input-group-addon input-sm" id="basic-addon1"><span class="fa fa-search" aria-hidden="true"></span></span>
					<label ng-bind="texto2"></label>
					<label>end search</label>							
					<span ng-show="texto1.length >=3" ng-repeat="user in users | limitTo:7">
						<label>probando</label>
						<span>
							<label>{{user.name}}</label>
						</span>
					</span>
				</span>	 -->
				
				
				
			</div>
		
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-buttons-position ">
					<li><a href="<%=request.getContextPath()%>/" class="glycolor"><b>Inicio</b></a></li>
					<c:if test="${empty userLoged }">
						<li data-toggle="modal" data-target="#logInModal"><a href="#"><span class="glyphicon fa-md glyphicon-log-in"></span>LogIn</a></li>
						<li><a href="<%=request.getContextPath()%>/User/"><span class="glyphicon fa-md glyphicon-user"></span>Registro</a></li>
						<li><a href="<%=request.getContextPath()%>/Restaurant/NewRestaurant/"><span class="glyphicon fa-md glyphicon-cutlery"></span>Registrar</a></li>
					</c:if>
					<c:if test="${not empty userLoged }">
						<li><a class="glycolor" href="<%=request.getContextPath()%>/User/Perfil?idUser=${idUser}"><span class="glyphicon fa-md glyphicon-user"></span>Perfil</a></li>
						<li class="glycolor"><a class="glycolor" href="<%=request.getContextPath()%>/Restaurant/NewRestaurant/" ><span class="glyphicon fa-md glyphicon-cutlery"></span>Registrar</a></li>
												
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown">
								<span class="fa fa-lg fa-users glycolor"></span>
							</a>
							
							
							<span class="badge badge-notify">${fn:length(user.received)}</span>
							<ul class="dropdown-menu notifications notificationScroll">
								<c:forEach items="${user.received}" var="friendRequest">
								<li><label>${friendRequest.sender.name} ${friendRequest.sender.lastName}</label>
								<c:if test="${not friendRequest.requestEvaluated}">
								<form:form action="/Ratatouille/User/AcceptFriendRequest" method="POST" modelAttribute="user" id="" class="">
									<input type="hidden" name="idOfFriendRequest" id="" value="${friendRequest.idDomainEvent }"/>
									<button type="submit" > Aceptar solicitud</button> 
								</form:form>	
								<form:form action="/Ratatouille/User/RejectFriendRequest" method="POST" modelAttribute="user" id="" class="">
									<input type="hidden" name="idOfFriendRequest" id="" value="${friendRequest.idDomainEvent }"/>
									<button type="submit" > Rechazar solicitud</button> 
								</form:form>	
								</c:if>
								<c:if test="${ friendRequest.requestEvaluated }">
								<c:if test="${ friendRequest.requestStatus }">
								<label>Ya aceptaste esta solicitud de amistad</label>
								</c:if>
								<c:if test="${ not friendRequest.requestStatus }">
								<label>Ya rechazaste esta solicitud de amistad</label>
								</c:if>
								
								</c:if>
						</li>
								<li class="divider"></li> 
								</c:forEach>

							</ul>
						</li>
						
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown">
								<span class="glyphicon fa-lg glyphicon-globe glycolor"></span>
							</a>
							<span class="badge badge-notify">${fn:length(user.notifications)}</span>
							<ul class="dropdown-menu notifications notificationScroll">
								<c:forEach items="${user.notifications}" var="notification">
								<li>
								<c:set var = "noShowNotification" value = ""/>
								<c:catch>
								<label>${notification.content.user.name } ${notification.content.user.lastName } ha dicho ${notification.content.description } sobre ${  notification.content.content.getIdRestaurant()== notification.content.content.getIdDomainObject() ? 'tu restaurant':'tu plato'} ${notification.content.content.name }</label>
								</c:catch>
								<c:catch>
								<label>${notification.content.userShare.name } ${notification.content.userShare.lastName } ha compartido este plato contigo ${notification.content.content.name }  ${notification.content.message }</label>
								</c:catch>
								<c:catch>
								<c:if test="${notification.content.sender.email == userLoged}">
								<label>${notification.content.recepter.name } ${notification.content.recepter.lastName } ha  ${notification.content.requestStatus ? 'aceptado':'rechazado'} tu solicitud de amistad</label>
								
								</c:if>
								<c:if test="${notification.content.recepter.email == userLoged}">
								<label>${notification.content.sender.name } ${notification.content.sender.lastName } te ha enviado una solicitud de amistad ${notification.content.requestEvaluated ? 'y ya la respondiste':'y esta esperando tu respuesta'}</label>
								
								<c:set var = "noShowNotification" value = "false"/>
								</c:if>
								</c:catch>
								 
								<c:if test="${empty  noShowNotification}">
								<form:form action="/Ratatouille/User/ViewNotification" method="POST" modelAttribute="user" id="" class="">
									<input type="hidden" name="idNotification" id="" value="${notification.idNotification }"/>
									<button type="submit" > Ir al contenido</button> 
								</form:form>	
								</c:if>
						</li>
								<li class="divider"></li> 
								</c:forEach>
							</ul>
						</li>
												
						<li class="dropdown">
							<a class="dropdown-toggle " data-toggle="dropdown">
								<span class="fa-lg fa fa-caret-down glycolor"></span></a>
							<ul class="dropdown-menu context">
								<li><a href="<%=request.getContextPath()%>/User/LogOut"><span class="glyphicon fa-md glyphicon-log-out"></span>Salir</a></li>
							</ul>
						</li>
					</c:if>
				</ul>
			</div>
		</div>

	</nav>


	<jsp:invoke fragment="contentOfResponsible"></jsp:invoke>
	
	<!--  col-md-6 col-md-offset-2-->
	<div class="content">
		<div>
			<jsp:invoke fragment="errorInContent"></jsp:invoke>
		</div>
		
		<div>
			<jsp:invoke fragment="messageInContent"></jsp:invoke>
		</div>

		<jsp:invoke fragment="content"></jsp:invoke>
	</div>
	
	

		
	<div class="wrap2">
				<aside id="side-menu" class="aside asideFull" role="navigation">  
					<c:if test="${not empty userLoged }">
			 <jsp:invoke fragment="contentOfUser"></jsp:invoke>
			 </c:if>          
		        
		        
		        
		        <div class="link">
		            <div class="link"><i class="fa fa-star fa-lg "></i>RECOMENDACIONES</div>
		            <ul class="">
		              <li><a href="#"><span class=""  > Reactivar</span></a></li>
		              </ul>
</div>
		        
		        
		    <%--  <c:if test="${not empty userLoged }">
			 <jsp:invoke fragment="friendsOfUser"></jsp:invoke>
			 </c:if> --%>
			 <c:if test="${not empty userLoged }">
				<div ng-controller="SearchUserController">
		
					<span class="input-group" >
					
					<input type="search" ng-keyup="beginSearch()" ng-model="texto1"  class="form-control input-sm " aria-haspopup="true" aria-expanded="false" aria-describedby="basic-addon2" placeholder="Buscar">
					
					<span class="input-group-addon input-sm " id="basic-addon2">
						<i class="fa fa-search" aria-hidden="true"></i>
					</span>

				</span>
				<div  ng-show="texto1.length >=3" class="resultSearcherUsers">
				        <ul class="searchUsersScroll">	
						<li ng-show="texto1.length >=3" ng-repeat="user in users | limitTo:7">
						
						<label>{{user.name}} {{user.lastName}}</label><!-- <a class="dropdown-item" href=""><label>{{user.name}}</label></a> -->
			        	<form:form action="/Ratatouille/User/SendFriendRequest" method="POST" modelAttribute="user" id="" class="">
						<input type="hidden" name="idOfUserToSendFriendRequest" id="" value="{{user.idUser}}"/>
						<button type="submit" class="btn btn-default btn-sm" > Enviar solicitud de amistad</button> 
						</form:form>	
			        
			        	
			        	</li>
			        	</ul>
			        </div>
				
				</div>
		         <div class="listOfFriends"><i class="fa fa-odnoklassniki glycolor"></i><label>Amigos</label>
		           	<ul class="submenu friendsScroll" >
								<c:forEach items="${user.friends}" var="friend">
								<li><label>${friend.getName()} ${friend.getLastName()}</label></li>
								</c:forEach>
								
							</ul>
					</div>
					
		      </c:if>
		  </aside>
		</div>
		






<c:if test="${not empty userLoged }">
			 <jsp:invoke fragment="modalsOfUser"></jsp:invoke>
			 </c:if>
			 <jsp:invoke fragment="modalsOfResponsible"></jsp:invoke>
			 <jsp:invoke fragment="modalsOfResponsibleOf"></jsp:invoke>
			 









	 












<c:if test="${empty userLoged }">
		<!-- LOGIN MODAL -->
			<div class="modal fade" id="logInModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <div class="modal-header headers">
				    <h5 class="modal-title" id="exampleModalLabel">INICIO DE SESION</h5>
				    </div>
				    <div class="modal-body">
				    
				    	<c:if test="${not empty error}">
							<c:if test="${not empty error['ERROR LOGIN DATOS NO VALIDOS']}">
				    			<div>
				    				<label>${error['ERROR LOGIN DATOS NO VALIDOS']}</label>
				   				</div>
				    		</c:if>
				    	</c:if>
					    <form:form action="/Ratatouille/User/LogIn/" method="POST" modelAttribute="UserToLog" id="" class="">
						<div class="form-group">
							<form:label class="" path="email">Email: </form:label>
							<form:input type="email" class="form-control" path="email" id="name"	placeholder="Email" required="true"/>
						</div>
						<div class="form-group">
							<form:label class="" path="password">Contrase&ntilde;a: </form:label>
							<form:password minlength="6" maxlength="20" class="form-control" path="password" id="name" placeholder="Password" required="true"/>
						</div>
						<input type="hidden" name="uri" value="${redirectUrl}">
					</div>	
				    <div class="modal-footer">
				    	<button type="submit" class="btn btn-default"> Ingresar</button> 
				    	<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				    </div>
				    	</form:form>
				    </div>
			 	</div>
			</div>
		</c:if>
	
		<!-- <nav class="navbar navbar-default navbar-bottom footer-margin">
		  <div class="container">
		    <div class="navbar-header footerPosition">
		      <a class="navbar-brand" href="#">Proyecto Final - GRUPO 7</a>
		    </div>
		    
		  </div>
		</nav> --> 



<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>



<c:if test="${empty userLoged}">
	<c:if test="${not empty error}">
		<c:if test="${not empty error['ERROR LOGIN DATOS NO VALIDOS']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
        		$('#logInModal').modal('show');
    		});
		</script> 
		</c:if>
	</c:if>
</c:if>

<jsp:invoke fragment="scriptsForShowModalWhenHaveErrors"></jsp:invoke>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<mt:base title="Index">

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
		<div class="col-md-9 bot">
			<c:out value="${userLoged}"></c:out>
			<c:out value="${typeUserLoged}"></c:out>
			
			<h2 class="ratatouille">Bienvenido a Ratatouille</h2>
			<h3>La red social gastronomica mas grande del mundo</h3>
			
			<div ng-controller="QueryController" class="Querys">
			
			<div name="q1">
			<h3>
			Buscar los resturantes que tuvieron mas comentarios entre las siguientes fechas
			</h3>
			
		<div class="form-group"><label class="control-label">Desde: (dd-mm-aaaa)</label><input class="form-control" type="text" ng-model="dateFrom"  pattern="\d{1,2}-\d{1,2}-\d{4}" /></div>
		<div class="form-group"> <label class="control-label">Hasta: (dd-mm-aaaa)</label><input class="form-control" type="text" ng-model="dateTo" pattern="\d{1,2}-\d{1,2}-\d{4}"/></div>
			<input type="button" ng-click="RestaurantsWithMoreCommentariesInRangeOfDate()" value="Listar restaurants con mas comentarios">
			
			<div id="sekkerDropdown" ng-show="q1Ready" >
			
						<table ng-show="q1Ready" class="table table-bordered">
			<tr class="text-center">
			<th class="text-center">Restaurant</th>
			<th class="text-center">Cantidad de comentarios</th>
			</tr>
			
			<tr ng-repeat="result in restaurantsWithmoreCommentaries | limitTo:10" class="text-center">
			<td>
		 <a class="" href="${pageContext.request.contextPath}/Restaurant/ShowRestaurant?idRestaurant={{ result.restaurant.hasOwnProperty('restaurant') ? (result.restaurant.restaurant.idDomainObject)+'&idFoodDish=':''}}{{result.restaurant.idDomainObject}}">
							 <span ng-show="!result.restaurant.hasOwnProperty('restaurant')" class="fa fa-cutlery glycolor"></span>
							 <label>{{result.restaurant.name}} </label><label ng-show="result.restaurant.hasOwnProperty('restaurant')"> ({{result.restaurant.restaurant.name}})</label></a> 
			</td>
			<td>
			 <label>{{result.restaurant.description}}</label><label>{{result.counter}}</label>
			</td>
			</tr>
			
			</table>
			

				    </div>
			</div>
			<div name="q2">
						<h3>
			Buscar los usuarios con mas comentarios
			</h3>
			

			<input type="button" ng-click="UsersWithMoreCommentaries()" value="Listar ranking de usuarios con mas comentarios">
			
			<div id="sekkerDropdown" ng-show="q2Ready" >
			<table ng-show="q2Ready" class="table table-bordered">
			<tr class="text-center">
			<th class="text-center">Usuario</th>
			<th class="text-center">Cantidad de comentarios</th>
			</tr>
			
			<tr ng-repeat="result in usersWithMoreCommentaries | limitTo:10" class="text-center">
			<td >
			<a class="" href="${pageContext.request.contextPath}/Restaurant/ShowRestaurant?idRestaurant={{ result.restaurant.hasOwnProperty('restaurant') ? (result.restaurant.restaurant.idDomainObject)+'&idFoodDish=':''}}{{result.restaurant.idDomainObject}}">
							 <span ng-show="!result.restaurant.hasOwnProperty('restaurant')" ></span>
							 <label>{{result.user.name}} </label><label> {{result.user.lastName}}</label></a> 
			</td>
			<td>
			<label>{{result.counter}} </label>
			</td>
			</tr>
			
			</table>
				    	
				    </div>
			</div>
			
			
			<div name="q3">
					<h3>
			Estadisticas de usuaios
			</h3>
			

			<input type="button" ng-click="TotalOfUserInTheSystem()" value="Solicitar estadisticas de usuarios">
			
			<div id="sekkerDropdown" ng-show="q3Ready" >
				    	
				    	<table class="table table-bordered">
				    	<tr>
				    	<th>Estado</th>
				    	<th class="text-center">Visitantes</th>
				    	<th class="text-center">Comensales</th>
				    	<th class="text-center">Gourmets</th>
				    	</tr>
				    	
				    	<tr>
				    	<td >Activos	</td>
				    	<td class="text-center">{{userQuantityStatistics.activeVisitor}}</td>
				    	<td class="text-center">{{userQuantityStatistics.activeCommensal}} </td>
				    	<td class="text-center">{{userQuantityStatistics.activeGourmet}}</td>
				    	</tr>
				    	
				    	<tr>
				    	<td>No activos</td>
				    	<td class="text-center">{{userQuantityStatistics.noActiveVisitor}}</td>
				    	<td class="text-center">{{userQuantityStatistics.noActiveCommensal}}</td>
				    	<td class="text-center">{{userQuantityStatistics.noActiveGourmet}} </td>
				    	</tr>
				    	
				    	<tr>
				    	<td>Total por clase</td>
				    	<td class="text-center">{{userQuantityStatistics.totalVisitor}}</td>
				    	<td class="text-center">{{userQuantityStatistics.totalCommensal}}</td>
				    	<td class="text-center">{{userQuantityStatistics.totalGourmet}}</td>
				    	</tr>
				    	
				    	<tr>
				    	<td>Total general</td>
				    	<td  colspan="3" class="text-center">{{userQuantityStatistics.total}}</td>
				    	</tr>
				    	</table>
	
				    </div>
			
			</div>
		</div>
	
	</div>
	</jsp:attribute>

</mt:base>


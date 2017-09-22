<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:base title="ShowRestaurant">
	<jsp:attribute name="head">
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDm-mzYm2bUccqo7GRnvauQw0yXeVz5mYA&v=3.exp&sensor=false" type="text/javascript"></script>
		
		<script src="${pageContext.request.contextPath}/resources/js/showRestaurantMap.js" type="text/javascript"></script>
		
		<script src="${pageContext.request.contextPath}/resources/js/editRestaurant.js" type="text/javascript"></script>
		
		<script>
			$(document).ready(function () {
		        $("#checkall").change(function () {
		        	$('.check1').prop('checked', true);
		        	$('.check2').prop('checked', true);
		        	$('.check3').prop('checked', true);
		        })
		    });
			$(document).ready(function () {
		        $("#uncheckall").change(function () {
		        	$('.check1').prop('checked', false);
		        	$('.check2').prop('checked', false);
		        	$('.check3').prop('checked', false);
		        })
		    });
	 
		</script>
		
		<script>
			function renderModalMap(){
				//$('#changeInformationOfRestaurant').modal('show');
				loadScriptModal();
				
				
				document.getElementById("newNameRestaurant").value=document.getElementById("nameRestaurant").innerHTML;
				document.getElementById("addresActual").innerHTML=document.getElementById("addres").innerHTML;
				
				
				var y=document.getElementById("ubicationlongitude").value;
				var x=document.getElementById("ubicationlatitude").value;
				var position= new google.maps.LatLng(x, y);
				placeMarker(position,mapModal);
				
				return true;
			
			}
		</script>
		
		<script>
			function moveDataToModalConfirmationShareFoodDish(element) {
				element.id
				var id=element.id
				id=id.replace("shareFoodDish","");
				var name =document.getElementById("FoodDish"+id+"name").innerHTML;
				var price= document.getElementById("FoodDish"+id+"price").innerHTML ;
				var description= document.getElementById("FoodDish"+id+"description").innerHTML;
				$('#shareFoodDish').modal('show'); 
				document.getElementById("shareFoodDishModalName").innerHTML=name;
				document.getElementById("shareFoodDishModalPrice").innerHTML=price ;
				document.getElementById("shareFoodDishModalDescription").innerHTML=description;
				document.getElementById("shareFoodDishModalId").value=id;
			}
		
		</script>
		
		<script>
			function moveDataToModalConfirmationDeleteFoodDish(element) {
				element.id
				var id=element.id
				id=id.replace("removeFoodDish","");
				var name =document.getElementById("FoodDish"+id+"name").innerHTML;
				var price= document.getElementById("FoodDish"+id+"price").innerHTML ;
				var description= document.getElementById("FoodDish"+id+"description").innerHTML;
				$('#deleteFoodDish').modal('show'); 
				document.getElementById("removeFoodDishModalName").innerHTML=name;
				document.getElementById("removeFoodDishModalPrice").innerHTML=price ;
				document.getElementById("removeFoodDishModalDescription").innerHTML=description;
				document.getElementById("removeFoodDishModalId").value=id;
			}
		
		</script>
		
		<script>
			function moveDataToModalConfirmationReactiveFoodDish(element) {
				element.id
				var id=element.id
				id=id.replace("reactiveFoodDish","");
				var name =document.getElementById("FoodDish"+id+"name").innerHTML;
				var price= document.getElementById("FoodDish"+id+"price").innerHTML ;
				var description= document.getElementById("FoodDish"+id+"description").innerHTML;
				$('#reactiveFoodDish').modal('show'); 
				document.getElementById("reactiveFoodDishModalName").innerHTML=name;
				document.getElementById("reactiveFoodDishModalPrice").innerHTML=price ;
				document.getElementById("reactiveFoodDishModalDescription").innerHTML=description;
				document.getElementById("reactiveFoodDishModalId").value=id;
			}
		
		</script>
		
		<script>
			function moveDataToModalConfirmationChangeFoodDish(element) {
				element.id
				var id=element.id
				id=id.replace("changeFoodDish","");
				var name =document.getElementById("FoodDish"+id+"name").innerHTML;
				var price= document.getElementById("FoodDish"+id+"price").innerHTML ;
				var description= document.getElementById("FoodDish"+id+"description").innerHTML;
				$('#changeFoodDish').modal('show'); 
				document.getElementById("changeFoodDishModalName").innerHTML=name;
				document.getElementById("changeFoodDishModalPrice").innerHTML=price ;
				document.getElementById("changeFoodDishModalDescription").innerHTML=description;
				document.getElementById("changeFoodDishModalId").value=id;
				document.getElementById("changeFoodDishModalNameOldInput").value=name;
				document.getElementById("changeFoodDishModalPriceOldInput").value=price ;
				document.getElementById("changeFoodDishModalDescriptionOldInput").value=description;
				document.getElementById("changeFoodDishModalNameNewInput").value=name;
				document.getElementById("changeFoodDishModalPriceNewInput").value=price;
				
				document.getElementById("changeFoodDishModalDescriptionNewInput").value=description;
		
			}
		
		</script>
			
		<script>
			function moveDataToModalConfirmationDeleteCommentaryOfFoodDish(element) {
				element.id;
				var id=element.id;
				id=id.replace("removeCommentary","");
				id=id.split("Of");
				var name =document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"name").innerHTML;
				var lastName= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"lastName").innerHTML ;
				var description= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"description").innerHTML;
				var date= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"date").innerHTML;
				var valoration= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"valoration").innerHTML;
				$('#deleteCommentaryOfFoodDish').modal('show'); 
				document.getElementById("removeCommentaryOfFoodDishModalName").innerHTML=name;
				document.getElementById("removeCommentaryOfFoodDishModalDate").innerHTML=date;
				document.getElementById("removeCommentaryOfFoodDishModalLastName").innerHTML=lastName ;
				document.getElementById("removeCommentaryOfFoodDishModalDescription").innerHTML=description;
				document.getElementById("removeCommentaryOfFoodDishModalId").value=id[0];
				document.getElementById("removeCommentaryOfFoodDishModalValoration").innerHTML=valoration ;
		
			}
		
		</script>
			
		<script>
			function moveDataToModalConfirmationChangeCommentaryOfFoodDish(element) {
				element.id
				var id=element.id
				id=id.replace("changeCommentary","");
				id=id.split("Of");
				var name =document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"name").innerHTML;
				var lastName= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"lastName").innerHTML ;
				var description= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"description").innerHTML;
				var date= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"date").innerHTML;
				var valoration= document.getElementById("Commentary"+id[0]+"OfFoodDish"+id[1]+"valoration").innerHTML;
				$('#changeCommentaryOfFoodDish').modal('show'); 
		
		
				document.getElementById("changeCommentaryOfFoodDishModalName").innerHTML=name;
				document.getElementById("changeCommentaryOfFoodDishModalLastName").innerHTML=lastName ;
				document.getElementById("changeCommentaryOfFoodDishModalDescription").innerHTML=description;
				document.getElementById("changeCommentaryOfFoodDishModalDate").innerHTML=date;
				document.getElementById("changeCommentaryOfFoodDishModalValoration").innerHTML=valoration;
		
				document.getElementById("changeCommentaryOfFoodDishModalId").value=id[0];
				document.getElementById("changeCommentaryOfFoodDishModalNameOldInput").value=name;
				document.getElementById("changeCommentaryOfFoodDishModalLastNameOldInput").value=lastName ;
				document.getElementById("changeCommentaryOfFoodDishModalDescriptionOldInput").value=description;
		
				
				document.getElementById("changeCommentaryOfFoodDishModalDescriptionNewInput").value=description;
				
				
				switch(valoration) {
				case "NEGATIVE":
				document.getElementById("changeCommentaryOfFoodDishModalValorationNegativeNewInput").checked = true;
				break;
				case "NEUTRAL":
				document.getElementById("changeCommentaryOfFoodDishModalValorationNeutralNewInput").checked = true;
				break;
				case "POSITIVE":
				document.getElementById("changeCommentaryOfFoodDishModalValorationPositiveNewInput").checked = true;
				break;
				default:
				document.getElementById("changeCommentaryOfFoodDishModalValorationNeutralNewInput").checked = true;
			} 
		
			}
		
		</script>
		
		<script>
			function moveDataToModalConfirmationDeleteCommentaryOfRestaurant(element) {
				element.id;
				var id=element.id;
				id=id.replace("removeCommentary","");
				id=id.split("Of");
				var name =document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"name").innerHTML;
				var lastName= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"lastName").innerHTML ;
				var description= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"description").innerHTML;
				var date= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"date").innerHTML;
				var valoration= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"valoration").innerHTML;
		
				$('#deleteCommentaryOfRestaurant').modal('show'); 
		
				document.getElementById("removeCommentaryOfRestaurantModalName").innerHTML=name;
				document.getElementById("removeCommentaryOfRestaurantModalDate").innerHTML=date;
				document.getElementById("removeCommentaryOfRestaurantModalLastName").innerHTML=lastName ;
				document.getElementById("removeCommentaryOfRestaurantModalDescription").innerHTML=description;
				document.getElementById("removeCommentaryOfRestaurantModalValoration").innerHTML=valoration;
				document.getElementById("removeCommentaryOfRestaurantModalId").value=id[0];
		
			}
		
		</script>
		
		<script>
			function moveDataToModalConfirmationChangeCommentaryOfRestaurant(element) {
				element.id
				var id=element.id
				id=id.replace("changeCommentary","");
				id=id.split("Of");
				var name =document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"name").innerHTML;
				alert();
				var lastName= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"lastName").innerHTML ;
				alert();
				var description= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"description").innerHTML;
				alert();
				var date= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"date").innerHTML;
				alert();
				var valoration= document.getElementById("Commentary"+id[0]+"OfRestaurant"+id[1]+"valoration").innerHTML;
				$('#changeCommentaryOfRestaurant').modal('show'); 
				
		
				document.getElementById("changeCommentaryOfRestaurantModalName").innerHTML=name;
				document.getElementById("changeCommentaryOfRestaurantModalLastName").innerHTML=lastName ;
				document.getElementById("changeCommentaryOfRestaurantModalDescription").innerHTML=description;
				document.getElementById("changeCommentaryOfRestaurantModalDate").innerHTML=date;
		
				document.getElementById("changeCommentaryOfRestaurantModalValoration").innerHTML=valoration;
				alert();
		
				document.getElementById("changeCommentaryOfRestaurantModalId").value=id[0];
		
				document.getElementById("changeCommentaryOfRestaurantModalNameOldInput").value=name;
				document.getElementById("changeCommentaryOfRestaurantModalLastNameOldInput").value=lastName ;
				document.getElementById("changeCommentaryOfRestaurantModalDescriptionOldInput").value=description;
		
				
				document.getElementById("changeCommentaryOfRestaurantModalDescriptionNewInput").value=description;
				alert();
				switch(valoration) {
					case "NEGATIVE":
					document.getElementById("changeCommentaryOfRestaurantModalValorationNegativeNewInput").checked = true;
					break;
					case "NEUTRAL":
					document.getElementById("changeCommentaryOfRestaurantModalValorationNeutralNewInput").checked = true;
					break;
					case "POSITIVE":
					document.getElementById("changeCommentaryOfRestaurantModalValorationPositiveNewInput").checked = true;
					break;
					default:
					document.getElementById("changeCommentaryOfRestaurantModalValorationNeutralNewInput").checked = true;
				} 
		
		
			}
		
		</script>
	
	</jsp:attribute>

	<jsp:attribute name="contentOfResponsible">
		<c:if test="${not empty responsibleOf}">	
			
			<!-- RESPONSIBLE SIDEBAR -->
			<div class="wrap">
				<aside id="side-menu" class="aside" role="navigation">            
					<ul class="nav nav-list accordion">                    
						<li class="nav-header pos">
							<div class="link">
								<i class="fa fa-lg fa-users"></i>Responsables<i class="fa fa-chevron-down"></i>
							</div>
							<ul class="submenu">
								<li ><a href="#"><span class="fa fa-md fa-pencil-square-o" data-toggle="modal" data-target="#modResponsibles"> Modificar Responsables</span></a></li>
								<li>
									<ul>
										<c:forEach items="${restaurant.configuration}" var="configuration">
										<!-- CAMBIAR LI -->
										<li><a href="#">${configuration.responsible.name} ${configuration.responsible.lastName}</a></li> 
			
									</c:forEach>	
								</ul>
							</li>
							</ul>
						</li>
			
						<li class="nav-header">
							<div class="link"><i class="fa fa-lg fa-bell"></i>Filtro de Notifiaciones<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li><a href="#"><span class="fa fa-md fa-pencil-square-o" data-toggle="modal" data-target="#modFilter"> Modificar filtro</span></a></li>
								<li>
									<ul class="">
										<c:if test="${configurationOf.filter.combination[0]}">
											<li><a>Visitante - Mostrar</a></li>	
										</c:if>
										<c:if test="${not configurationOf.filter.combination[0]}">
											<li><a>Visitante - No Mostrar</a></li>	
										</c:if>
										<c:if test="${configurationOf.filter.combination[1]}">
											<li><a>Comensal - Mostrar</a></li>	
										</c:if>
										<c:if test="${not configurationOf.filter.combination[1]}">
											<li><a>Comensal - No Mostrar</a></li>	
										</c:if>
										<c:if test="${configurationOf.filter.combination[2]}">
											<li><a>Gourmet - Mostrar</a></li>	
										</c:if>
										<c:if test="${not configurationOf.filter.combination[2]}">
											<li><a>Gourmet - No Mostrar</a></li>	
										</c:if>
									</ul>
								</li>
							</ul>
						</li>
				
						<li class="nav-header">
							<div class="link"><i class="fa fa-lg fa-cogs"></i>Administrar Restaurant<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<c:if test="${ restaurant.lowlogic}"><li><a href="#"><span class="fa fa-md fa-plus-square" data-toggle="modal" data-target="#reactivateRestaurant"> Reactivar</span></a></li></c:if>
								<c:if test="${not restaurant.lowlogic}"><li><a href="#"><span class="fa fa-md fa-minus-square" data-toggle="modal" data-target="#disableRestaurant"> Dar de baja</span></a></li></c:if>
								<li><a href="#" onclick="return renderModalMap();"><span class="fa fa-md fa-pencil-square-o" data-toggle="modal" data-target="#changeInformationOfRestaurant"> Modificar</span></a></li>
								
							</ul>
						</li>  
				
						<li class="nav-header">
						<div class="link"><i class="fa fa-lg fa-book"></i>Menu<i class="fa fa-chevron-down"></i></div>
						<ul class="submenu">
							<li><a href="#"><span class="fa fa-md fa-plus-square" data-toggle="modal" data-target="#modNewFoodDish"> Agregar</span></a></li>
							<!-- <li><a href="#"><span class="fa fa-md fa-pencil-square-o" data-toggle="modal" data-target=""> Modificar</span></a></li>
							<li><a href="#"><span class="fa fa-md fa-minus-square" data-toggle="modal" data-target=""> Eliminar</span></a></li> -->
						</ul>
					</li>
			
					</ul>
				</aside>
			</div>
			
			<!-- 									FOODDISH									 -->
			
			
			
			<!-- AGREGAR FOODDISH -->
			<div class="modal fade" id="modNewFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">NUEVO PLATO</h5>
							<h6 class="modal-subtitle">${restaurant.name}, ${restaurant.ubication.direction}</h6>
						</div>
						<div class="modal-body">
							<c:if test="${not empty error}">
								<c:if test="${not empty error['ERROR NEW FOODDISH']}">
				    				<div>
				    					<label>${error['ERROR NEW FOODDISH']}</label>
				   					</div>
				    			</c:if>
				    		</c:if>
							<form:form action="SaveNewFoodDish/" method="POST" modelAttribute="newFoodDish" id="" class="">
							<div class="form-group">
								<form:label class="" path="name">Nombre: </form:label>
								<form:input class="form-control" path="name" id="name"	placeholder="Nombre" required="true" minlength="2"/>
							</div>
							<div class="form-group">
								<form:label class="" path="price">Precio: </form:label>
								<form:input class="form-control" path="price" id="price" placeholder="precio" required="true" type="number" min="0"/>
							</div>
							<div class="form-group">
								<form:label class="" path="description">Descripcion: </form:label>
								<form:input class="form-control" path="description" id="description" placeholder="Descripcion" required="true" minlength="5" maxlength="500"/>
							</div>
							<form:hidden path="restaurant.idDomainObject" id="restaurant" />
			
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Agregar</button> 
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			</div>
			
			<!-- EDITAR FOODDISH -->
			<div class="modal fade" id="changeFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">MODIFICAR PLATO</h5>
						</div>
						
						<div class="modal-body">
							<c:if test="${not empty error}">
								<c:if test="${not empty error['ERROR IN MODAL CHANGE FOODDISH']}">
				    				<div>
				    					<label>${error['ERROR IN MODAL CHANGE FOODDISH']}</label>
				   					</div>
				    			</c:if>
				    		</c:if>
								<form:form action="/Ratatouille/Restaurant/ChangeFoodDish" method="POST" modelAttribute="restaurant" id="" class="">
							<div>	
								<input type="hidden" name="idFoodDishToChange" id="changeFoodDishModalId"/>
								 <p>Esta por modificar el siguiente plato:</p>
								 <div>
									<label>Nombre: </label><label id="changeFoodDishModalName"></label><br>
									<label>Descripcion: </label><label id="changeFoodDishModalDescription"></label><br>
									<label>Precio &dollar;: </label> <label id="changeFoodDishModalPrice"></label>
									
								</div>
								<input type="hidden" name="changeFoodDishModalNameOldInput" id="changeFoodDishModalNameOldInput"/>
								<input type="hidden" name="changeFoodDishModalPriceOldInput" id="changeFoodDishModalPriceOldInput"/>
								<input type="hidden" name="changeFoodDishModalDescriptionOldInput" id="changeFoodDishModalDescriptionOldInput"/>
								<div>
									<div class="form-group">
										<label>Nombre: </label><input type="text" class="form-control" name="changeFoodDishModalNameNewInput" id="changeFoodDishModalNameNewInput" required="tue" minlength="2" />
									</div>
									<div class="form-group">
										<label>Precio &dollar;: </label><input type="number" step="0.01" min="0" class="form-control" name="changeFoodDishModalPriceNewInput" id="changeFoodDishModalPriceNewInput" required="tue" min="0"/>
									</div>
									<div class="form-group">
										<label>Descripcion: </label><input type="text" class="form-control" name="changeFoodDishModalDescriptionNewInput" id="changeFoodDishModalDescriptionNewInput" required="tue" minlength="5" maxlength="500"/>
									</div>
								</div>
								<form:hidden path="idDomainObject"/>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default"> Modificar </button> 
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
									
						</div>
							</form:form>
						
							
					</div>
				</div>
			</div>
			
			<!-- ELIMINAR FOODDISH -->
			<div class="modal fade" id="deleteFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">ELIMINAR PLATO</h5>
							
						</div>
						<div class="modal-body">
							<div>	
								<form:form action="/Ratatouille/Restaurant/DeleteFoodDish" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idFoodDishToDelete" id="removeFoodDishModalId"/>
								<p>Esta por eliminar la siguiente comida:</p>
								<label id="removeFoodDishModalName"></label><br>
								<label id="removeFoodDishModalDescription"> </label><br>
								<label>Precio &dollar;:</label><label id="removeFoodDishModalPrice"></label>
								
								<form:hidden path="idDomainObject"/>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default"> Eliminar  </button> 
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						</div>
							</form:form>
					</div>
				</div>
			</div>
			
			<!-- REACTIVAR FOODDISH -->
			<div class="modal fade" id="reactiveFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">REACTIVAR PLATO</h5>
						</div>
						<div class="modal-body">
							<div>	
								<form:form action="/Ratatouille/Restaurant/ReactiveFoodDish" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idFoodDishToReactive" id="reactiveFoodDishModalId"/>
								<p>Esta por reactivar la siguiente comida:</p>
								<label id="reactiveFoodDishModalName"></label><br>
								<label id="reactiveFoodDishModalDescription"></label><br>
								<label>Precio &dollar;: </label><label id="reactiveFoodDishModalPrice"></label>
								<form:hidden path="idDomainObject"/>
			
			
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Reactivar </button> 
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							</div>
						</form:form>	
					</div>
				</div>
			</div>
			</div>
			
			<!-- MODIFICAR FILTRO DE NOTIFICACIONES -->
			<div class="modal fade" id="modFilter" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">ADMINISTRAR FILTROS</h5>
						</div>
						<div class="modal-body">
							<form:form action="/Ratatouille/Restaurant/ChangeConfiguration" method="POST" modelAttribute="restaurant" id="" class="">
								<form:hidden path="idDomainObject"/>
								<h6 class="checkbox-title-position"><label>FILTRO PERSONALIZADO</label></h6>	
								<div class="form-group checkbox-position">
									<label class="checkbox-inline"><input class="check1" type="checkbox" name="visitor" <c:if test="${configurationOf.filter.combination[0]}"> checked="checked" </c:if> >Visitante</label>
									<label class="checkbox-inline"><input class="check2" type="checkbox" name="commensal" <c:if test="${configurationOf.filter.combination[1]}"> checked="checked" </c:if>>Comensal</label>
									<label class="checkbox-inline"><input class="check3" type="checkbox" name="gourmet" <c:if test="${configurationOf.filter.combination[2]}"> checked="checked" </c:if>>Gourmet</label>
								</div>
								<div class="divider"></div>
								<h6 class="radio-title-position"><label>FILTRO GENERAL</label></h6>
								<div class="form-group radio-position">
									<label class="radio-inline"><input type="radio" name="filter" id="checkall">Todos</label>
									<label class="radio-inline"><input type="radio" name="filter" id="uncheckall">Ninguno</label>
								</div>	
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default"> Aceptar</button> 
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						</div>
							</form:form>
					</div>
				</div>
			</div>
					
			<!-- 									RESTAURANT									 -->
			
			<!-- DESABILITAR RESTAURANT -->
			<div class="modal fade" id="disableRestaurant" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">DAR DE BAJA RESTAURANT</h5>
						</div>
						<div class="modal-body">
							<form:form action="/Ratatouille/Restaurant/DeleteRestaurant" method="POST" modelAttribute="restaurant" id="" class="">
								<p>Esta por dar de baja el restaurant <label>${restaurant.name}</label></p>
								<p>Ubicado en <label>${restaurant.ubication.direction}</label></p>
								<form:hidden path="idDomainObject"/>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default">Aceptar</button> 
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						</div>
							</form:form>	
					</div>
				</div>
			</div>
			
			<!-- REACTIVAR RESTAURANT -->
			<div class="modal fade" id="reactivateRestaurant" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">REACTIVAR RESTAURANT</h5>
						</div>
						<div class="modal-body">
							<form:form action="/Ratatouille/Restaurant/ReactiveRestaurant" method="POST" modelAttribute="restaurant" id="" class="">
								<p>Esta por reactivar el restaurant <label>${restaurant.name}</label></p>
								<p>Ubicado en <label>${restaurant.ubication.direction}</label></p>
								<form:hidden path="idDomainObject"/>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default"> Reactivar</button> 
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						</div>
							</form:form>
					</div>
				</div>
			</div>
			
			<!-- MODIFICAR RESTAURANT -->
			<div class="modal fade" id="changeInformationOfRestaurant" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">MODIFICAR RESTAURANT</h5>
						</div>
						<div class="modal-body">
						<div class="form-group">
						<label class="control-label">Restaurant  </label><label id="nameRestaurant"> ${restaurant.name }</label>
						<label class="control-label">Direccion </label><label id="addresActual"></label>
						</div>
							<form:form action="/Ratatouille/Restaurant/ChangeInformationOfRestaurant" method="POST" modelAttribute="restaurant" id="" class="">
								<form:hidden path="idDomainObject"/>
								<form:hidden  path="ubication.longitude" id="ubicationlongitudeToChange" />
					    		<form:hidden  path="ubication.latitude" id="ubicationlatitudeToChange" />
							<div class="map">
					       		<h2>Seleccione la ubicacion en el mapa</h2>
					    		<div id="modal-map-canvas"></div>
					   		</div>
					   		<div class="form-group">
								<form:label class="control-label" path="name">Nombre:</form:label>
								<form:input class="form-control" path="name" id="newNameRestaurant" placeholder="Nombre"/>
							</div>
				  			<div class="form-group">
								<label class="control-label">Direccion:</label>
								<input type="text" class="form-control" name="addresToChange" id="addresToChange" placeholder="direccion" value="" readonly="true"/>
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
			
			<!-- 									RESPONSIBLE									 -->
			
			<!-- ELIMINAR RESPONSABLES -->
			<div class="modal fade" id="modResponsibles" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">ADMINISTRAR RESPONSABLES</h5>
						</div>
						<div class="modal-body">
						
							<c:if test="${not empty error}">
								<c:if test="${not empty error['ERROR IN MODAL CHANGE RESPONSIBLES']}">
				    				<div>
				    					<label>${error['ERROR IN MODAL CHANGE RESPONSIBLES']}</label>
				   					</div>
				    			</c:if>
				    		</c:if>
							<c:forEach items="${restaurant.configuration}" var="configuration">
								<div>	
									<form:form action="/Ratatouille/Restaurant/DeleteResponsibleOf" method="POST" modelAttribute="restaurant" id="" class="">
										<input type="hidden" name="idResponsible" value="${configuration.responsible.idUser}">
										<ul class="list-group">
									   		<li class="list-group-item"><label>${configuration.responsible.name} ${configuration.responsible.lastName}</label><button type="submit" class="btn btn-xs btn-ratatouille pull-right glyphicon glyphicon-remove"></button></li>
										</ul>
										<form:hidden path="idDomainObject"/>
									</form:form>
								</div>
							</c:forEach>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</c:if>
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
		<div class="col-md-5 bot">	
			<%-- <h3 >${restaurant.name}</h3>
					${restaurant.category}
				${restaurant.category.benefit} --%>
				
			
			<c:if test="${restaurant.category.benefit == 'NOTHING'}">
				<h3>${restaurant.name}</h3>
				<h4 id="addres"></h4>
			</c:if> 
			
			<c:if test="${restaurant.category.benefit == 'CURSIVA'}">
				<h3 class="cursive ratatouille">${restaurant.name}</h3>
				<h4 id="addres" class="cursiveAdress"></h4>
			</c:if> 
			
			<c:if test="${restaurant.category.benefit == 'HIGHLIGHT'}">
				<h3 class="ratatouille highlight"><i class="fa fa-star" aria-hidden="true"></i>${restaurant.name}<i class="fa fa-star" aria-hidden="true"></i></h3>
				<h4 id="addres" class="highlightAdress"></h4>
			</c:if> 
		
			
				
			<input type="hidden" value="${ restaurant.ubication.longitude}" id="ubicationlongitude" />
			<input type="hidden" value="${ restaurant.ubication.latitude}" id="ubicationlatitude" />
		
			<div class="mapa">
				<div id="map-canvas"></div> 
			</div>
		
			<a href="#"><label>Comentarios: ${fn:length(restaurant.commentaries)}</label></a>
		 	<c:if test="${fn:length(restaurant.commentaries)>0 }">
				<div name="comentarios del restaurant" class="commentaryScroll bot">
					
					<c:forEach items="${restaurant.commentaries}" var="commentary">
						
							<div class="panel panel-default">
								<div class="panel-heading">  
								
									<c:if test="${commentary.valoration =='NEGATIVE'}"><span class="fa fa-2x fa-frown-o"></span></c:if>
									<c:if test="${commentary.valoration =='NEUTRAL'}"><span class="fa fa-2x fa-meh-o"></span></c:if>
									<c:if test="${commentary.valoration =='POSITIVE'}"><span class="fa fa-2x  fa-smile-o"></span></c:if>           		   	
			                		<label id="Commentary${commentary.idDomainEvent}OfRestaurant${restaurant.idDomainObject}name">${commentary.user.name}</label>
									<label id="Commentary${commentary.idDomainEvent}OfRestaurant${restaurant.idDomainObject}lastName">${commentary.user.lastName}</label>
									
									<label id="Commentary${commentary.idDomainEvent}OfRestaurant${restaurant.idDomainObject}valoration" hidden>${commentary.valoration}</label>
									<c:if test="${commentary.user.email == userLoged}">
										<span class="pull-right">
											<a class="glyphicon glyphicon-edit fa-lg restComment glycolor" id="changeCommentary${commentary.idDomainEvent}Of${restaurant.idDomainObject }" name="changeCommentaryOfRestaurant" 
													onclick="moveDataToModalConfirmationChangeCommentaryOfRestaurant(this)"></a>
											
											<a class="glyphicon glyphicon-remove-circle fa-lg restComment glycolor" id="removeCommentary${commentary.idDomainEvent}Of${restaurant.idDomainObject }" name="removeCommentaryOfRestaurant" 
													onclick="moveDataToModalConfirmationDeleteCommentaryOfRestaurant(this)"></a>
										</span>
									</c:if>
								</div>
								<div class="panel-body">
									<p id="Commentary${commentary.idDomainEvent}OfRestaurant${restaurant.idDomainObject}date" class="pull-right">${commentary.date}</p><br>
									<p id="Commentary${commentary.idDomainEvent}OfRestaurant${restaurant.idDomainObject}description">${commentary.description}</p>
								</div>		
							</div>
						
					</c:forEach>
				</div>
			</c:if>
			
			<div class="col-md-12">	
				<form:form action="/Ratatouille/Restaurant/CommentRestaurant/" method="POST" modelAttribute="comentary" class="form-horizontal" role="form">
					<div class="form-group">
						<form:textarea  class="form-control" path="description" name="description" required="true" placeholder="Ingrese aquí su comentario" minlength="5" maxlength="500"/>
						<input type="hidden" name="idRestaurant" value="${restaurant.idDomainObject} "/>
					</div>
					<div class="form-group">
						<label class="radio-inline"><input type="radio" name="valorationOfNewCommentaryOfRestaurant" value="POSITIVE"/> <span class="fa fa-2x  fa-smile-o"></span></label>
						<label class="radio-inline"><input type="radio" name="valorationOfNewCommentaryOfRestaurant" value="NEUTRAL" checked="checked" required/><span class="fa fa-2x fa-meh-o"></span></label>
						<label class="radio-inline"><input type="radio" name="valorationOfNewCommentaryOfRestaurant" value="NEGATIVE"/> <span class="fa fa-2x fa-frown-o"></span></label>
						<button type="submit" class="btn btn-default pull-right"> Comentar</button>
					</div>
					
					
				</form:form>	
			</div>
		</div> 
		
		<div class="col-md-3 foodMarg">
			<c:if test="${restaurant.category.benefit == 'NOTHING'}">
				<h3 class="menuPos">MENÚ</h3>
			</c:if> 
			<c:if test="${restaurant.category.benefit == 'CURSIVA'}">	
				<h3 class="menuPos cursive ratatouille">MENÚ</h3>
			</c:if> 
			<c:if test="${restaurant.category.benefit == 'HIGHLIGHT'}">
				<h3 class="ratatouille menuPos highlight"><i class="fa fa-star" aria-hidden="true"></i>Menú<i class="fa fa-star" aria-hidden="true"></i></h3>
			</c:if> 
				<c:if test="${!empty restaurant.menu}">
					<div class="fooddishMenu fooddishScroll">
					<c:forEach items="${restaurant.menu}" var="foodDish">
						
						<c:if test="${not foodDish.lowlogic || not empty responsibleOf}">
							<div class="media-position">
										
											<%--<label id="FoodDish${foodDish.idDomainObject}name">${foodDish.name}</label> <label> Precio &dollar;: </label> <label id="FoodDish${foodDish.idDomainObject}price">${foodDish.price}</label> --%>
											<%-- <div class="botones">
												<c:if test="${not empty responsibleOf}">
													<c:if test="${foodDish.lowlogic}">
														<input class="btn btn-default" type="button"id="reactiveFoodDish${foodDish.idDomainObject}" value="Reactivar" name="reactiveFoodDish" onclick="moveDataToModalConfirmationReactiveFoodDish(this)">
													</c:if>
													
													<input class="btn btn-default"type="button" id="changeFoodDish${foodDish.idDomainObject}" value="Modificar" name="changeFoodDish" onclick="moveDataToModalConfirmationChangeFoodDish(this)">
													
													<c:if test="${not foodDish.lowlogic}">
														<input class="btn btn-default" type="button" id="removeFoodDish${foodDish.idDomainObject}" value="Eliminar" name="removeFoodDish" onclick="moveDataToModalConfirmationDeleteFoodDish(this)">
													</c:if>
												</c:if>
											</div> --%>
										
										<div class="media ">
											<div class="media-left">
												<span class="glyphicon fa-3x glyphicon-cutlery"></span>
											</div>
											<div class="media-body">
												<h4 class="media-heading" ><span id="FoodDish${foodDish.idDomainObject}name">${foodDish.name}</span><span  class="pull-right">Precio &dollar;: <span id="FoodDish${foodDish.idDomainObject}price">${foodDish.price}</span></span></h4>
												<p id="FoodDish${foodDish.idDomainObject }description">${foodDish.description}</p>
											</div>
										</div>
										
											<c:if test="${not empty userLoged }">
												<span  class="pull-left">
													<c:if test="${not empty responsibleOf}">
														<a class="glyphicon glyphicon-edit fa-lg restComment glycolor" title="Modificar Plato" id="changeFoodDish${foodDish.idDomainObject}" value="Modificar" name="changeFoodDish" onclick="moveDataToModalConfirmationChangeFoodDish(this)"></a>
														
														<c:if test="${foodDish.lowlogic}">
															<a class="glyphicon glyphicon-ok fa-lg restComment glycolor" title="Reactivar Plato" id="reactiveFoodDish${foodDish.idDomainObject}" value="Reactivar" name="reactiveFoodDish" onclick="moveDataToModalConfirmationReactiveFoodDish(this)"></a>
														</c:if>
														
														<c:if test="${not foodDish.lowlogic}">
															<a class="glyphicon glyphicon-remove fa-lg restComment glycolor" title="Desactivar Plato" id="removeFoodDish${foodDish.idDomainObject}" value="Eliminar" name="removeFoodDish" onclick="moveDataToModalConfirmationDeleteFoodDish(this)"></a>
														</c:if>
													</c:if>
												</span>
												<a class="glyphicon glyphicon-share fa-lg restComment glycolor pull-right" title="Compartir Plato" id="shareFoodDish${foodDish.idDomainObject }"name="shareFoodDish" onclick="moveDataToModalConfirmationShareFoodDish(this)"></a>
											</c:if>
										
										<a href="#" data-toggle="collapse" data-target="#collapse${foodDish.idDomainObject}y${commentary.idDomainEvent}"><label>Comentarios: ${fn:length(foodDish.commentaries)}</label></a>
										
										<c:if test="${fn:length(foodDish.commentaries)>0 }">
											<div name="comentarios de cada plato" class="commentaryScroll bot collapse" id="collapse${foodDish.idDomainObject}y${commentary.idDomainEvent}">
												<c:forEach items="${foodDish.commentaries}" var="commentary">
													<div class="panel panel-default">
														<div class="panel-heading">  
															<c:if test="${commentary.valoration =='NEGATIVE'}"><span class="fa fa-lg fa-frown-o"></span></c:if>
															<c:if test="${commentary.valoration =='NEUTRAL'}"><span class="fa fa-lg fa-meh-o"></span></c:if>
															<c:if test="${commentary.valoration =='POSITIVE'}"><span class="fa fa-lg  fa-smile-o"></span></c:if>         		   	
										               		<label id="Commentary${commentary.idDomainEvent}OfFoodDish${foodDish.idDomainObject}name">${commentary.user.name}</label>
															<label id="Commentary${commentary.idDomainEvent}OfFoodDish${foodDish.idDomainObject}lastName">${commentary.user.lastName}</label>
															<label id="Commentary${commentary.idDomainEvent}OfFoodDish${foodDish.idDomainObject}valoration" hidden>${commentary.valoration}</label>
															<c:if test="${commentary.user.email == userLoged}">
																
																<span class="pull-right">
																	<a class="glyphicon glyphicon-edit fa-lg restComment glycolor" id="changeCommentary${commentary.idDomainEvent}Of${foodDish.idDomainObject }" name="changeCommentaryOfFoodDish" 
																			onclick="moveDataToModalConfirmationChangeCommentaryOfFoodDish(this)"></a>
																
																	<a class="glyphicon glyphicon-remove-circle fa-lg restComment glycolor" id="removeCommentary${commentary.idDomainEvent}Of${foodDish.idDomainObject }" name="removeCommentaryOfFoodDish" 
																			onclick="moveDataToModalConfirmationDeleteCommentaryOfFoodDish(this)"></a>
																</span>
															</c:if>
														</div>
														<div class="panel-body">
															<p id="Commentary${commentary.idDomainEvent}OfFoodDish${foodDish.idDomainObject}date" class="pull-right">${commentary.date}</p><br>
															<p id="Commentary${commentary.idDomainEvent}OfFoodDish${foodDish.idDomainObject}description">${commentary.description}</p>
														</div>
																	
													</div>
												</c:forEach>
											</div>
										</c:if>
										
											<div class="col-md-12">	
												<form:form action="/Ratatouille/Restaurant/CommentFoodDish/" method="POST" modelAttribute="comentary" id="" class="form-horizontal" role="form">
													<div class="form-group">
														<form:textarea class="form-control" path="description" name="description" required="true" placeholder="Ingrese aquí su comentario" minlength="5" maxlength="500"/>
														<input type="hidden" name="idFoodDish" value="${foodDish.idDomainObject} "/>
													</div>
													<div class="form-group">
														<label class="radio-inline"><input type="radio" name="valorationOfNewCommentaryOfFoodDish" value="POSITIVE"/> <span class="fa fa-2x  fa-smile-o"></span> </label>
														<label class="radio-inline"><input type="radio" name="valorationOfNewCommentaryOfFoodDish" value="NEUTRAL" checked="checked" required/><span class="fa fa-2x fa-meh-o"></span></label>
														<label class="radio-inline"><input type="radio" name="valorationOfNewCommentaryOfFoodDish" value="NEGATIVE"/> <span class="fa fa-2x fa-frown-o"></span></label>
														<button type="submit" class="btn btn-default pull-right"> Comentar</button>
														
													</div>
													
													
													
												</form:form>	
											</div>
							</div>				
										</c:if>	
							
						
					</c:forEach>
					</div>
				</c:if> 
				
				
				
				<c:if test="${empty restaurant.menu}">
				<div class="fooddishMenu ">
					<h4>Menu Vacio</h4>
				</div>
				</c:if> 
			
		</div>
	</jsp:attribute>


	<jsp:attribute name="contentOfUser"></jsp:attribute>



	<jsp:attribute name="modalsOfUser">
	
	
	
		<!-- 									COMENTARIO									 -->
			
			<!-- EDITAR COMENTARIO DE RESTAURANT -->
			<div class="modal fade" id="changeCommentaryOfRestaurant" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">MODIFICAR COMENTARIO</h5>
						</div>
						<div class="modal-body">
							<div>	
								<form:form action="/Ratatouille/Restaurant/ChangeCommentaryOfRestaurant" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idCommentaryOfRestaurantToChange" id="changeCommentaryOfRestaurantModalId"/>
								<p>Esta por modificar el siguiente comentario:</p>
								<div>
									<label>Usuario: </label><label id="changeCommentaryOfRestaurantModalName"></label>
									<label id="changeCommentaryOfRestaurantModalLastName"></label><br>
									<label>Fecha: </label><label id="changeCommentaryOfRestaurantModalDate"></label><br>
									<label>Valoracion: </label><label id="changeCommentaryOfRestaurantModalValoration"></label>
								</div>
								<div>
									<label>Descripcion: </label><p id="changeCommentaryOfRestaurantModalDescription"></p>
								</div>
								<input type="hidden" name="changeCommentaryOfRestaurantModalNameOldInput" id="changeCommentaryOfRestaurantModalNameOldInput"/>
								<input type="hidden" name="changeCommentaryOfRestaurantModalLastNameOldInput" id="changeCommentaryOfRestaurantModalLastNameOldInput"/>
								<input type="hidden" name="changeCommentaryOfRestaurantModalDescriptionOldInput" id="changeCommentaryOfRestaurantModalDescriptionOldInput"/>
								<input type="hidden" name="changeCommentaryOfRestaurantModalDateOldInput" id="changeCommentaryOfRestaurantModalDateOldInput"/>
								<div>
									
									<div>
										<label> Valoracion:
											<input type="radio" name="changeCommentaryOfRestaurantModalValorationNewInput" id="changeCommentaryOfRestaurantModalValorationNegativeNewInput" value="NEGATIVE"><span class="fa fa-2x fa-frown-o"></span>
											<input type="radio" name="changeCommentaryOfRestaurantModalValorationNewInput" id="changeCommentaryOfRestaurantModalValorationNeutralNewInput" value="NEUTRAL"><span class="fa fa-2x fa-meh-o"></span>
											<input type="radio" name="changeCommentaryOfRestaurantModalValorationNewInput" id="changeCommentaryOfRestaurantModalValorationPositiveNewInput" value="POSITIVE"><span class="fa fa-2x  fa-smile-o"></span> 
										</label>
									</div>
									
									<div class="form-group">
										<label>Nueva descripcion: </label><input type="text" class="form-control" name="changeCommentaryOfRestaurantModalDescriptionNewInput" id="changeCommentaryOfRestaurantModalDescriptionNewInput"/>
									</div>
								</div>
								<form:hidden path="idDomainObject"/>
			
			
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Modificar </button> 
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							</div>
						</form:form>	
					</div>
				</div>
			</div>
			</div>
			
			<!-- ELIMINAR COMENTARIO DEL RESTAURANT -->
			<div class="modal fade" id="deleteCommentaryOfRestaurant" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">ELIMINAR COMENTARIO</h5>
						</div>
						<div class="modal-body">
							<div>	
								<form:form action="/Ratatouille/Restaurant/DeleteCommentaryOfRestaurant" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idCommentaryOfRestaurantToDelete" id="removeCommentaryOfRestaurantModalId"/>
								<P>Esta segura que desea eliminar el siguiente comentario:</P>
			
								<label>Usuario: </label><label id="removeCommentaryOfRestaurantModalName"></label>
								<label id="removeCommentaryOfRestaurantModalLastName"></label><br>
								<label>Fecha: </label><label id="removeCommentaryOfRestaurantModalDate"></label><br>
								<label>Valoracion: </label><label id="removeCommentaryOfRestaurantModalValoration"></label><br>
								<label>Descripcion: </label><p id="removeCommentaryOfRestaurantModalDescription"></p>
								<form:hidden path="idDomainObject"/>
			
							</div>
						</div>
						<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Eliminar </button> 
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			
			<!-- CAMBIAR COMENTARIO AL FOODDISH -->
			<div class="modal fade" id="changeCommentaryOfFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">MODIFICAR COMENTARIO</h5>
						</div>
						<div class="modal-body">
							<div>	
								<form:form action="/Ratatouille/Restaurant/ChangeCommentaryOfFoodDish" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idCommentaryOfFoodDishToChange" id="changeCommentaryOfFoodDishModalId"/>
								<p>Esta por modificar el siguiente comentario:</p>
								<div>
									<label id="changeCommentaryOfFoodDishModalName"></label>
									<label id="changeCommentaryOfFoodDishModalLastName"></label>
									<label id="changeCommentaryOfFoodDishModalDate"></label>
									<label id="changeCommentaryOfFoodDishModalValoration"></label>
								</div>
								<div>
									<label>Descripcion: </label><p id="changeCommentaryOfFoodDishModalDescription"></p>
								</div>
								<input type="hidden" name="changeCommentaryOfFoodDishModalNameOldInput" id="changeCommentaryOfFoodDishModalNameOldInput"/>
								<input type="hidden" name="changeCommentaryOfFoodDishModalLastNameOldInput" id="changeCommentaryOfFoodDishModalLastNameOldInput"/>
								<input type="hidden" name="changeCommentaryOfFoodDishModalDescriptionOldInput" id="changeCommentaryOfFoodDishModalDescriptionOldInput"/>
								<input type="hidden" name="changeCommentaryOfFoodDishModalDateOldInput" id="changeCommentaryOfFoodDishModalDateOldInput"/>
								<div>
									
									<div class="form-group">
										<label>Descripcion: </label><input type="text" class="form-control" name="changeCommentaryOfFoodDishModalDescriptionNewInput" id="changeCommentaryOfFoodDishModalDescriptionNewInput" required="true" minlength="5" maxlength="500"/>
									</div>
									
									<div>
										<label> Valoracion:
											<input type="radio" name="changeCommentaryOfFoodDishModalValorationNewInput" id="changeCommentaryOfFoodDishModalValorationPositiveNewInput" value="POSITIVE"><span class="fa fa-2x  fa-smile-o"></span> 
											<input type="radio" name="changeCommentaryOfFoodDishModalValorationNewInput" id="changeCommentaryOfFoodDishModalValorationNeutralNewInput" value="NEUTRAL"><span class="fa fa-2x fa-meh-o"></span>
											<input type="radio" name="changeCommentaryOfFoodDishModalValorationNewInput" id="changeCommentaryOfFoodDishModalValorationNegativeNewInput" value="NEGATIVE"><span class="fa fa-2x fa-frown-o"></span>
											
										</label>
									</div>
									
								</div>
								<form:hidden path="idDomainObject"/>
			
			
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Modificar </button> 
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							</div>
						</form:form>	
					</div>
				</div>
			</div>
			</div>
			
			<!-- ELIMINAR COMENTARIO DEL FOODDISH -->
			<div class="modal fade" id="deleteCommentaryOfFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">ELIMINAR COMENTARIO</h5>
						</div>
						<div class="modal-body">
							<div>	
								<form:form action="/Ratatouille/Restaurant/DeleteCommentaryOfFoodDish" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idCommentaryOfFoodDishToDelete" id="removeCommentaryOfFoodDishModalId"/>
								<label>Esta segura que desea eliminar el siguiente comentario:</label>
			
								<label id="removeCommentaryOfFoodDishModalName"></label>
								<label id="removeCommentaryOfFoodDishModalLastName"></label>
								<label id="removeCommentaryOfFoodDishModalDate"></label>
								<label id="removeCommentaryOfFoodDishModalValoration"></label>
								<p id="removeCommentaryOfFoodDishModalDescription"></p>
								<form:hidden path="idDomainObject"/>
			
			
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Eliminar </button> 
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							</div>
						</form:form>	
					</div>
				</div>
			</div>
			</div>
			
			<!-- SHARE FOODDISH -->
			<div class="modal fade" id="shareFoodDish" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header headers">
							<h5 class="modal-title" id="exampleModalLabel">COMPARTIR PLATO</h5>
						</div>
						<div class="modal-body">
						
						<c:if test="${not empty error}">
								<c:if test="${not empty error['ERROR IN MODAL SHARE FOODDISH']}">
				    				<div>
				    					<label>${error['ERROR IN MODAL SHARE FOODDISH']}</label>
				   					</div>
				    			</c:if>
				    		</c:if>
							<div>	
								<form:form action="/Ratatouille/Restaurant/ShareFoodDish" method="POST" modelAttribute="restaurant" id="" class="">
								<input type="hidden" name="idFoodDishToShare" id="shareFoodDishModalId"/>
								<label>Seleccione los amigos a los que ompartira el siguiente plato y un mensaje para ellos</label>
								
								<label id="shareFoodDishModalName"></label>
								<label>Precio &dollar;: </label><label id="shareFoodDishModalPrice"></label>
								<p id="shareFoodDishModalDescription"></p>
								<form:hidden path="idDomainObject"/>
								
			
							</div>
							<div class="form-group">
							<label>Mensaje para amigos: </label>
								<input class="form-control" type="text" id="descriptionToShareFoodDish" name="descriptionToShareFoodDish" value=""/>
							</div>
							<div class="form-group">
							<c:forEach items="${user.friends}" var="friend">
							<label class="checkbox"><input class="" type="checkbox" name="friendsToShareFoodDish" value="${friend.idUser }">${friend.name } ${friend.lastName }</label>
							
							
							</c:forEach>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"> Compartir </button> 
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
		<c:if test="${not empty error['ERROR NEW FOODDISH']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
        		$('#modNewFoodDish').modal('show');
    		});
		</script> 
		</c:if>
	</c:if>



	<c:if test="${not empty error}">
		<c:if test="${not empty error['ERROR IN MODAL CHANGE RESPONSIBLES']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
        		$('#modResponsibles').modal('show');
    		});
		</script> 
		</c:if>
	</c:if>
	
	
	<c:if test="${not empty error}">
		<c:if test="${not empty error['ERROR IN MODAL CHANGE FOODDISH']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
   	 			//element=document.getElementById("changeFoodDish"+${pageContext.request.getParameter("idFoodDish")});
   	 			element=document.getElementById("changeFoodDish"+${error['idFoodDish']});
   	 			moveDataToModalConfirmationChangeFoodDish(element);
        		/* $('#changeFoodDish').modal('show'); */
   	 		});
		</script> 
		</c:if>
	</c:if>
	
	<c:if test="${not empty error}">
		<c:if test="${not empty error['ERROR IN MODAL SHARE FOODDISH']}">
		<script type="text/javascript">
   	 		$(window).on('load',function(){
   	 			//element=document.getElementById("changeFoodDish"+${pageContext.request.getParameter("idFoodDish")});
   	 			element=document.getElementById("shareFoodDish"+${error['idFoodDish']});
   	 			moveDataToModalConfirmationShareFoodDish(element);
        		/* $('#changeFoodDish').modal('show'); */
   	 		});
		</script> 
		</c:if>
	</c:if>
	

</jsp:attribute>
</mt:base>
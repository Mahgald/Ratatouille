var app=angular.module('app',[]);

function RemoteResource($http,$q,baseUrl) {
	
    this.list = function() {
        var defered = $q.defer();
        var promise = defered.promise;

};
} 


function RemoteResourceProvider() {
	var _baseUrl;
	this.setBaseUrl=function(baseUrl) {
		_baseUrl=baseUrl;
	};
	this.$get=['$http','$q',function($http,$q) {
		return new RemoteResource($http,$q,_baseUrl);
	}];
};
	 
app.provider("remoteResource",RemoteResourceProvider);

app.constant("baseUrl", "http://localhost:8080/Ratatouille");
app.config(['baseUrl', 'remoteResourceProvider',function(baseUrl, remoteResourceProvider) {
	   remoteResourceProvider.setBaseUrl(baseUrl);
}
]);




app.controller('SearchUserController',['remoteResource','$scope','$http',function(remoteResource,$scope,$http){
	$scope.pruebas='/Ratatouille/view/AngularFragments/SearchFragment.jsp';
	
	
	$scope.texto1="";
	$scope.texto2="angularJS";
	$scope.users= [{name:"martin"},{name:"dale loco"}];
	$scope.bussinessMessages="";
		
	
	$scope.searchUsers = function() {
	        $http({method: 'GET', url: 'http://localhost:8080/Ratatouille/User/users/'+$scope.texto1}).then(function(response) {
	          $scope.users = response.data;
	        }, function(response) {
	        	$scope.users = response.data || 'Request failed';
	      });
	};
	
	
	$scope.beginSearch=function(){
		if($scope.texto1.length >= 3){
			$scope.searchUsers();
			$scope.texto2=$scope.texto1;
		}else if($scope.texto1.length == 0){
			$scope.texto2="";
			
		}
		
	}
	
	
}])

app.controller('SearchRestaurantController',['remoteResource','$scope','$http',function(remoteResource,$scope,$http){
	$scope.pruebas='/Ratatouille/view/AngularFragments/SearchFragment.jsp';
	
	
	$scope.texto1="";
	$scope.texto2="angularJS";
	$scope.results= [];
	$scope.resultsByDistance= [];
	$scope.bussinessMessages="";
	$scope.distanceToSearch=0;
	$scope.latitude=0;
	$scope.longitude=0;
	$scope.searchByDistance=false;
	
	
	
	$scope.searchRestaurantByDistance = function() {
		if($scope.texto1==""){
			$scope.texto1="---";
		}
		
		
		if($scope.distanceToSearch==null){
			$scope.distanceToSearch=00;
		}
		
	
	        $http({method: 'GET', url: 'http://localhost:8080/Ratatouille/Restaurant/Search/'+$scope.texto1+"/"+$scope.distanceToSearch+"/"+$scope.latitude+"/"+$scope.longitude+"/"}).then(function(response) {
	          $scope.resultsByDistance = response.data;
	        }, function(response) {
	        	$scope.resultsByDistance = response.data || 'Request failed';
	      });
	    if($scope.texto1=="---"){
	    	$scope.texto1="";
		}
	    if($scope.distanceToSearch==00){
			$scope.distanceToSearch=null;
		}
	    
	};
	$scope.searchRestaurantsAndFoodDish = function() {
        	$http({method: 'GET', url: 'http://localhost:8080/Ratatouille/Restaurant/Search/'+$scope.texto1}).then(function(response) {
          $scope.results = response.data;
        }, function(response) {
        	$scope.results = response.data || 'Request failed';
      });
};
	
	$scope.beginSearch=function(){
		if($scope.searchByDistance){
			
			$scope.searchRestaurantByDistance();
		}else{
			$scope.searchRestaurantsAndFoodDish();
		}
		

		
	}
	
	$scope.dontSearchByDistance=function(){
		$scope.searchByDistance=false;
		$scope.distanceToSearch=0;
		$scope.resultsByDistance= [];
		$scope.beginSearch();
	}
	
	
	$scope.changeCriterionOfSearch=function(){
		
		$('#searchModal').modal('show'); 
		$('#searchModal').modal({backdrop: 'static', keyboard: false})
		$('.modal-backdrop').remove();
		if (!$scope.searchByDistance){
			$scope.distanceToSearch=0;
			$scope.beginSearch();
		}else{
			$scope.results= [];
			
			if (navigator.geolocation) {//navegador lo soporta
				
				navigator.geolocation.getCurrentPosition($scope.acquiredGeolocation,$scope.errorAtAcquiredgeolocation);
				$scope.beginSearch();
			}else{
				alert("no soportado");
			}
		}

	}
	
	$scope.acquiredGeolocation=function(position){
		$scope.latitude=position.coords.latitude;
		$scope.longitude=position.coords.longitude;
		
		
		
		alert("coordenadas"+$scope.latitude+" "+$scope.longitude);
		
	}
	$scope.errorAtAcquiredgeolocation=function(error){
		 switch(error.code) {
		    case error.PERMISSION_DENIED:
		    	alert("1");
		      break;
		    case error.POSITION_UNAVAILABLE:
		    	alert("2");
		      break;
		    case error.TIMEOUT:
		    	alert("n3");
		      break;
		    case error.UNKNOWN_ERROR:
		    	alert("n4");
		      break;
		    }
		alert("no hay coordenadas");
	}
	
	
	
}])




app.controller('QueryController',['remoteResource','$scope','$http',function(remoteResource,$scope,$http){
	$scope.pruebas='/Ratatouille/view/AngularFragments/SearchFragment.jsp';
	
	
	$scope.dateFrom="";
	$scope.dateTo="";
	$scope.restaurantsWithmoreCommentaries=[];
	$scope.q1Searching=false;
	$scope.q1Ready=false;
	$scope.q2Searching=false;
	$scope.q2Ready=false;
	$scope.q3Searching=false;
	$scope.q3Ready=false;
	
	
	$scope.usersWithMoreCommentaries=[];
	
	$scope.userQuantityStatistics={};
	
	$scope.ListOfRestaurantsProximity=[];
	$scope.latitude;
	$scope.longitude;
	
	
	
	$scope.RestaurantsWithMoreCommentariesInRangeOfDate = function() {
		$scope.q1Searching=true;
		$scope.q1Ready=false;
		
	        $http({method: 'GET', url: 'http://localhost:8080/Ratatouille/Query/RestaurantsWithMoreCommentariesInRangeOfDate/'+$scope.dateFrom+"/"+$scope.dateTo}).then(function(response) {
	          $scope.restaurantsWithmoreCommentaries = response.data;
	          $scope.q1Searching=false;
	          $scope.q1Ready=true;
	          
	        }, function(response) {
	        	$scope.restaurantsWithmoreCommentaries = response.data || 'Request failed';
	      });
	};
	
	$scope.UsersWithMoreCommentaries = function() {
		$scope.q2Searching=true;
		$scope.q2Ready=false;
		
        $http({method: 'GET', url: 'http://localhost:8080/Ratatouille/Query/UsersWithMoreCommentaries/'}).then(function(response) {
          $scope.usersWithMoreCommentaries = response.data;
          
          $scope.q2Searching=false;
          $scope.q2Ready=true;
        }, function(response) {
        	$scope.usersWithMoreCommentaries = response.data || 'Request failed';
      });
	};
	
	$scope.TotalOfUserInTheSystem = function() {
		
		$scope.q3Searching=true;
		$scope.q3Ready=false;
		
		$http({method: 'GET', url: 'http://localhost:8080/Ratatouille/Query/TotalOfUserInTheSystem/'}).then(function(response) {
			$scope.userQuantityStatistics = response.data;
			
			 $scope.q3Searching=false;
	          $scope.q3Ready=true;
		}, function(response) {
			$scope.userQuantityStatistics = response.data || 'Request failed';
		});
	};
	
	$scope.RestaurantsProximity = function() {
		$http({method: 'GET', url: 'http://localhost:8080/Ratatouille/Query/RestaurantsProximity/'+$scope.latitude+"/"+$scope.longitude}).then(function(response) {
			$scope.ListOfRestaurantsProximity = response.data;
		}, function(response) {
			$scope.ListOfRestaurantsProximity = response.data || 'Request failed';
		});
	};
	
	
	
	$scope.beginSearch=function(){
		if($scope.texto1.length >= 3){
			$scope.searchUsers();
			$scope.texto2=$scope.texto1;
		}else if($scope.texto1.length == 0){
			$scope.texto2="";
			
		}
		
	}
	
	
}])












/*app.directive('showIfAngular',function(){
	return{
		restrict: 'E',
		template: '<label ng-if="search.texto == \'prueba\'" >esto es una locura</label>'
	};
})

app.directive('showIfAngular2',function(){
	return{
		restrict: 'E',
		template: '<div ng-if="search.texto == \'prueba2\'" ng-repeat="result in users" > ontenido a iterar <div>{{result.name}}</div></div>'
	};
}

)*/




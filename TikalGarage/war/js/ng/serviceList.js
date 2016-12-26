app.service('listService',['$http','$q','$location',function($http,$q,$location){
	this.getLista=function(){
		var d= $q.defer();
		$http.get("/servicio/status/activos").then(function(response){
			console.log(response);
				d.resolve(response.data);
		},function(response){
			console.log(response);
			if(response.status == 403){
				$location.path("/login");
			}
		});
		return d.promise;
	}
}]);


app.controller('serviceListController',['$rootScope','$scope','$location','listService','sessionService',function($rootScope,$scope,$location,listService,sessionService){
	if(!$rootScope.authenticated){
		$location.path("/login");
	}	
	$scope.addAbierto=function(ser){
		$rootScope.abiertos.push(ser);
	}
	$rootScope.detallesView=false;
	listService.getLista().then(function(data){
			$scope.listaServicios=data;
		},function(data){
			console.log(data);
			if(data.status == 403){
				$location.path("/login");
			}
		});
}]);
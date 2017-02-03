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


app.controller('serviceListController',['$rootScope','$scope','$location','listService','sessionService','$http',function($rootScope,$scope,$location,listService,sessionService,$http){
	sessionService.isAuthenticated().then(function(){
	$scope.busca="";
	$scope.entontrados=[]
	$scope.$watch('busca',function(){
		if($scope.busca.length>3){
			$scope.buscar();
		}
	},true);
	$scope.buscar=function(){
		$http.get("/search/general/"+$scope.busca).success(function(data){
			$scope.encontrados=data.nombres;
			$scope.tipos=data.tipos;
			
			$('#searchBox').typeahead({

			    source: $scope.encontrados,

			    updater:function (item) {
			    	var ind=$scope.encontrados.indexOf(item);

			    	$http.get("/search/filtra/"+item+"/"+$scope.tipos[ind]).success(function(data){
			    		$scope.listaServicios=data;
			    		$scope.busca="";
			    	});
			        return item;
			    }
			});
			$('#searchBox').data('typeahead').source=$scope.encontrados;
		});
	}
	$scope.onSelect=function($item,$model,$label){
		alert("entra");
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
	

	});	
	
}]);
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
	if(!$rootScope.authenticated){
		$location.path("/login");
	}
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
			        alert($scope.tipos[ind]);
			        return item;
			    }
			});
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
	
	$scope.states = [{postcode:'B1',address:'Bull ring'},{postcode:'M1',address:'Manchester'}];
	$scope.encontrados= ["Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Dakota","North Carolina","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"];
	
	
}]);
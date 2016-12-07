app.service('listService',['$http','$q',function($http,$q){
	this.getLista=function(){
		var d= $q.defer();
		$http.get("/servicio/status/activos").then(function(response){
			console.log(response);
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	}
}]);


app.controller('serviceListController',['$scope','$location','listService',function($scope,$location,listService){
		listService.getLista().then(function(data){
			$scope.listaServicios=data;
		})
}]);
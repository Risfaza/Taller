app.controller('empresasListController',['$scope','$location','$http','empresasService',function($scope,$location,$http,empresasService){
	$http.get("/empresas/findAll").then(function(response){
		$scope.empresas=response.data;
	},function(response){
		alert("something went wrong");
		console.log(response);
	});
	
	$scope.add=function(){
		$location.path("/empresas");
	}
	
	$scope.ver=function(rfc){
		console.log(rfc);
		$location.path("/empresas/details/"+rfc.RFC);
	};
	$scope.editar=function(rfc){
		$location.path("/empresas/edit/"+rfc.RFC);
	};
	$scope.eliminar=function(rfc){
		alert(rfc);
	};
}]);
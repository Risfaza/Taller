app.service('usuarioService', [ '$http', '$q', function($http, $q) {

	this.crearUsuario = function(usuario) {
		var d = $q.defer();
		$http.post("/usuario/registro",usuario).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
} ]);

app.controller('usuarioController',['$scope','$location','usuarioService',function($scope,$location,usuarioService){
	$scope.EnviarFormulario = function(){
		console.log($scope.usuario);
		usuarioService.crearUsuario($scope.usuario).then(
				function(data) {
					$scope.usuario = data;
					alert("Usuario creado correctamente");
				});
	}
}]);
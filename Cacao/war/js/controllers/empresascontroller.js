app.service('empresasService', [ '$http', '$q', function($http, $q) {
	this.add = function(newEmp) {/* Agrega elementos a arreglo Empresa */

	};
	this.find = function(rfc) {
		var d= $q.defer();
		$http.get("/empresas/find/"+rfc).then(function(response){
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	};
	this.clean = function() {/* Limpia o Elimina todos los elementos */
	};
	this.getAll = function() {/* Muestra todos los Elementos */
	};
	this.removeItem = function(item) {/* Elimina elemento por elemeto */
	}
} ]);
app.controller("empresasController", [
		'$scope',
		'$http',
		'$location',
		'empresasService',
		function($scope, $http, $location, empresasSevice) {
			$scope.addEmp = function() {
				$http.post("/empresas/add", $scope.newEmp).then(
						function(response) {
							alert("Empresa Guardada");
							$location.path("/empresas/list");
						}, function(response) {
							alert("Something went wrong!");
							console.log(response);
						});
			}
			$scope.removeEmp = function(item) {
			}

			$scope.clean = function() {
			}

			$scope.clean = function() {
			}

		} ]);

app.controller("empresasEditController", [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'empresasService',
		function($scope, $http, $location, $routeParams, empresasService) {
			console.log($routeParams.rfc);
			$http.get("empresas/find/" + $routeParams.rfc).then(
					function(response) {
						$scope.newEmp = response.data;
					}, function(response) {
						alert("something went wrong");
						console.log(response);
					});

			$scope.addEmp = function() {
				$http.post("/empresas/add", $scope.newEmp).then(
						function(response) {
							alert("Empresa Guardada");
							$location.path("/empresas/list");
						}, function(response) {
							alert("Something went wrong!");
							console.log(response);
						});
			}

			$scope.ver = function(item) {
				$location.path("/empresas/details/" + item.RFC);
			}

			$scope.clean = function() {
			}

		} ]);

app.controller("empresasDetailsController", [ '$scope', '$http', '$location',
		'$routeParams', 'empresasService',
		function($scope, $http, $location, $routeParams, empresasService) {
			empresasService.find($routeParams.rfc).then(function(data){
				$scope.empresa=data[0];
				$scope.regimenes=data[1];
				console.log($scope.regimenes);
			});
			
			$scope.addEsquema=function(){
				$location.path("/esquemas/agregar/"+$scope.empresa.RFC);
			}
			$scope.verRegimen=function(id){
				$location.path("/esquemas/details/"+id);
			}			
			$scope.editarRegimen=function(id){
				$location.path("/esquemas/edit/"+id);
			}				
			$scope.irEmpleados=function(){
				$location.path("/empleados/list/"+$scope.empresa.RFC);
			}		
		} ]);
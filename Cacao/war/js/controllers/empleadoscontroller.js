app.service('empleadosService', function() {
	this.add = function(newEmp) {
	};
	this.updaLocalStorage = function() {/* Actualiza Storage */
	};
	this.clean = function() {/* Limpia o Elimina todos los elementos */
	};
	this.getAll = function(rfc) {/* Muestra todos los Elementos */
	};
	this.removeItem = function(item) {/* Elimina elemento por elemeto */
	}
})
app.controller("empleadosController", [
		'$scope',
		'$http',
		'empleadosService',
		function($scope, $http, empleadosService) {
			$scope.addEmp = function() {
				$http.post("/employee/add", $scope.newEmp).then(
						function(response) {
							alert("Empleado Guardado");
							console.log(response.data);
						}, function(response) {
							alert("Something went wrong!");
							console.log(response);
						});
			}
		} ]);
app.controller("empleadosDetailsController", [ '$scope', '$http', '$location',
		'$routeParams', 'empleadosService',
		function($scope, $http, $location, $routeParams, empresasService) {

			$scope.regresaEmpresa = function() {
				$location.path("/empresas/details/" + $routeParams.rfc);
			}
		} ]);

app.controller("empleadosListController", [ '$scope', '$http', '$location',
		'$routeParams', 'empleadosService',
		function($scope, $http, $location, $routeParams, empresasService) {

			
			$scope.regresaEmpresa = function() {
				$location.path("/empresas/details/" + $routeParams.rfc);
			}
		} ]);
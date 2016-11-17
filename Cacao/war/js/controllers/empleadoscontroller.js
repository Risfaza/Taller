app.service('empleadosService',['$http','$q', function($http,$q) {
	this.add = function(newEmp) {
	};
	this.updaLocalStorage = function() {/* Actualiza Storage */
	};
	this.clean = function() {/* Limpia o Elimina todos los elementos */
	};
	this.getAll = function(id) {/* Muestra todos los Elementos */
	};
	this.removeItem = function(item) {/* Elimina elemento por elemeto */
	}

	this.findByRegimen = function(id) {
		var d= $q.defer();
		$http.get("/employee/getByRegimen/"+id).then(function(response){
			console.log(response);
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	}

}]);
app.controller("empleadosController", [ '$scope', '$http', 'empleadosService',
		'$routeParams',
		function($scope, $http, empleadosService, $routeParams) {
			$scope.addEmp = function() {
				var send = {
					empleado : $scope.newEmp,
					idEmpresa : $routeParams.id
				}
				$http.post("/employee/add", send).then(function(response) {
					alert("Empleado Guardado");
					console.log(response.data);
				}, function(response) {
					alert("Something went wrong!");
					console.log(response);
				});
			}
		} ]);
app.controller("empleadosDetailsController", [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'empleadosService',
		'$rootScope',
		function($scope, $http, $location, $routeParams, empresasService,
				$rootScope) {

			$scope.regresaEmpresa = function() {
				$location.path("/empresas/details/" + $rootScope.rfc);
			}
		} ]);

app.controller("empleadosListController", [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'empleadosService',
		'$rootScope',
		function($scope, $http, $location, $routeParams, empleadosService,
				$rootScope) {

			empleadosService.findByRegimen($routeParams.id).then(
					function(datos) {
						$scope.empleados = datos;
						console.log(datos);
					});

			$scope.add = function() {
				$location.path("/empleados/add/" + $routeParams.id);
			}
			$scope.regresaEmpresa = function() {
				$location.path("/empresas/details/" + $rootScope.id);
			}
		} ]);
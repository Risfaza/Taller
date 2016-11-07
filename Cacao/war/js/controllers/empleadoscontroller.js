app.service('empleadosService', function() {/*
											 * Servicio e inyeccion de servicio
											 * de localStorage
											 */
	this.add = function(newEmp) {
	};
	this.updaLocalStorage = function() {/* Actualiza Storage */
	};
	this.clean = function() {/* Limpia o Elimina todos los elementos */
	};
	this.getAll = function() {/* Muestra todos los Elementos */
	};
	this.removeItem = function(item) {/* Elimina elemento por elemeto */
	}
})
app.controller("empleadosController", [ '$scope', '$http', 'empleadosService',
		function($scope, $http, empleadosService) {

			$scope.addEmp = function() {
				$http.post("/employee/add", $scope.newEmp).then(function(response) {
					alert("Empleado Guardado");
					console.log(response.data);
				}, function(response) {
					alert("Something went wrong!");
					console.log(response);
				});
			}

		

		} ]);
app.service('empresasService', function() {
	this.add = function(newEmp) {/* Agrega elementos a arreglo Empresa */
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
					console.log(response.data);
				}, function(response) {
					alert("Something went wrong!");
					console.log(response);
				});
		}

		$scope.removeEmp = function(item) {
		}

		$scope.clean = function() {
		}

	} 
	]);
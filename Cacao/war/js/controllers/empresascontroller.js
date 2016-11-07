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

<<<<<<< HEAD
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
=======
		$scope.removeEmp = function(item) {
		}
>>>>>>> branch 'master' of https://github.com/isrVigueras/cacao.git

		$scope.clean = function() {
		}

<<<<<<< HEAD
			$scope.clean = function() {
			}

		} ]);

app.controller("empresasEditController", [
                              		'$scope',
                              		'$http',
                              		'$location','$routeParams',
                              		'empresasService',
                              		function($scope, $http, $location,$routeParams, empresasSevice) {
                              			console.log($routeParams.rfc);
                              			$http.get("empresas/find/"+$routeParams.rfc).then(function(response){
                              				$scope.newEmp=response.data;
                              			},function(response){
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

                              			$scope.removeEmp = function(item) {
                              			}

                              			$scope.clean = function() {
                              			}

                              		} ]);
=======
	} 
	]);
>>>>>>> branch 'master' of https://github.com/isrVigueras/cacao.git

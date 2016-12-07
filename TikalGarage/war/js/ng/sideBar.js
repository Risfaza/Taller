app.service('sideBarService', [ '$http', '$q', function($http, $q) {
	this.getServicios = function() {
		$http.get("/servicio/getServiciosHoy").then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;

	}
} ]);

app.controller('sideBarController', [
		'$scope',
		'sideBarService','$rootScope','$location',
		function($scope, sideBarService,$rootScope,$location) {
			
			$scope.serviciosActivos=$rootScope.serviciosHoy;
			$scope.goToView = function(id) {
				alert(id);
				$location.path("/servicio/view/"+id);
			}
		} ]);
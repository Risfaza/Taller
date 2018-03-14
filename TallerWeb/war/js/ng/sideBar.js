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

app.controller('sideBarController', [ '$scope', 'sideBarService', '$rootScope',
		'$location', '$timeout',
		function($scope, sideBarService, $rootScope, $location, $timeout) {

			$scope.serviciosActivos = $rootScope.serviciosHoy;
			$scope.goToView = function(id) {
				$location.path("/servicio/view/" + id);
			}

			$rootScope.relojito=function() {
				if($rootScope.actual){
					if ($rootScope.actual.servicio.fechaInicio) {
						var hoy = new Date();
						var inicio = new Date($rootScope.actual.servicio.fechaInicio)
						if($rootScope.actual.servicio.fechafin){
							hoy = new Date($rootScope.actual.servicio.fechafin);
							var dif = hoy - inicio;
							$scope.counter = dif;
							$scope.dias= Math.floor($scope.counter / (1000 * 60 * 60 * 24));
							$scope.horas= Math.floor($scope.counter / (1000 * 60 * 60))%24; 
							$scope.minutos= Math.floor($scope.counter / (1000 * 60)) % (60); 
							$scope.segundos= Math.floor($scope.counter / (1000))%(60); 
						}else{
							var dif = hoy - inicio;
//						alert(dif);
							$scope.counter = dif;
						}
					}
					}	
			}
			$scope.$watch('actual', $rootScope.relojito()
			, true);

			$scope.$watch('counter', function() {
//				if ($rootScope.actual.servicio.metadata.fechaInicio) {
//					var hoy = new Date();
//					var inicio = new Date($rootScope.actual.servicio.metadata.fechaInicio)
//					var dif = hoy - inicio;
//					alert(dif);
//					$scope.counter = dif;
					$scope.dias= Math.floor($scope.counter / (1000 * 60 * 60 * 24));
					$scope.horas= Math.floor($scope.counter / (1000 * 60 * 60))%24; 
					$scope.minutos= Math.floor($scope.counter / (1000 * 60)) % (60); 
					$scope.segundos= Math.floor($scope.counter / (1000))%(60); 
					
					document.getElementById("semaforo").className = "alert-success semGreen";
					document.getElementById("semaforo2").className = "alert-success semGreen";
					if($scope.dias>6 && $scope.dias<15){
						document.getElementById("semaforo").className = "alert-warning";
						document.getElementById("semaforo2").className = "alert-warning";
						
					}
					if($scope.dias>=15){
						document.getElementById("semaforo").className = "alert-danger";
						document.getElementById("semaforo2").className = "alert-danger";
					}
//				}
			}, true);
			
			$scope.onTimeout = function() {
				if(! $rootScope.actual.servicio.fechafin){
				$scope.counter+=1000;
				mytimeout = $timeout($scope.onTimeout, 1000);
				}
			}
			var mytimeout = $timeout($scope.onTimeout, 1000);

			$scope.stop = function() {
				$timeout.cancel(mytimeout);
			}
			
			$scope.getFecha= function(fecha){
				if(fecha instanceof Date){
				}else{
					fecha= new Date(fecha);
				}
				var day=fecha.getDay();
				var mes=fecha.getMonth()+1;
				var anio=fecha.getFullYear();
				var hora=fecha.getHours()+6;
				var minutos=fecha.getMinutes();
				var seg= fecha.getSeconds();
				if(day<10){
					day='0'+day;
				}
				if(mes<10){
					mes='0'+mes;
				}
				if(hora<10){
					hora='0'+hora;
				}
				if(minutos<10){
					minutos='0'+minutos
				}
				if(seg<10){
					seg='0'+seg;
				}
			
				return day+"-"+mes+"-"+anio+" "+hora+":"+minutos+":"+seg;
			}
		} ]);
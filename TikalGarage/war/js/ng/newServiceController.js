app.service('eventoService',['$http','$q',function($http,$q){
	this.addEvento=function(evento){
		$http.post("",evento).then(function(response){},function(response){});
	}
}]);

app
		.controller(
				"newServiceController",
				[
						"$scope",
						'$http','$location','eventoService','$rootScope',
						function($scope, $http,$location,eventoService,$rootScope) {
							$scope.mensaje = "Texto cargado desde el controlador Pagina1Controller";
							$scope.ids = [ "cliente", "auto", "bitacora",
									"presupuesto", "damage", "cobranza" ];
							$scope.servicio = {
								servicio : {},
								cliente : {
									nombre : "",
									rfc : "",
									domicilio : {
										calle : "",
										numero : "",
										cp : "",
										colonia : "",
										ciudad : ""
									},
									contacto : "",
									telefonoContacto : []

								},
								auto : {
									equipamiento : {
										equipoAdicional : []
									}
								},
								eventos : []
							}

							$scope.guardar = function() {
								console.log($scope.servicio);
								var send={servicio:$scope.servicio};
								$http.post('/servicio/add', send)
										.then(function(response) {
											alert("Servicio Guardado");
											$rootScope.serviciosHoy.push(response.data);
											$location.path("servicio/view/"+response.data.servicio.idServicio);
											console.log(response);
										}, function(response) {
											console.log(response);
											alert("Something went wrong");
										})
							}
							$scope.showTab = function(id) {
								$scope.ids.forEach(function(i) {
									// console.log(i);
									var myEl = angular.element("#" + i);
									// console.log(myEl.hasClass());
									myEl.removeClass('active');
									myEl.removeClass('in');
								});
								var myEl = angular.element("#" + id);
								myEl.addClass('active in');
							};
							$rootScope.detallesView=false;
							$scope.addCaract = function() {
								var caract = $scope.caracteristicaAuto;
								if ($scope.servicio.auto.equipamiento.equipoAdicional
										.indexOf(caract) < 0) {
									$scope.servicio.auto.equipamiento.equipoAdicional
											.push(caract);
								}
								$scope.caracteristicaAuto = "";
							}
							$scope.fecha = function() {
								var f = new Date();
								// $scope.evento.fecha=
								// f.getDay()+"-"+f.getMonth()+"-"+f.getFullYear()+"T"+f.getHours()+":"+f.getMinutes();
							}
							$scope.findCliente = function() {
								// implementar servicio de búsqueda
							}
							$scope.mayusculas=function(value){
								value=value.toUpperCase();
								return value;
							}

						} ]);
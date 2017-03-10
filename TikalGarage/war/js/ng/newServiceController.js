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
						'$http','$location','eventoService','$rootScope','sessionService',
						function($scope, $http,$location,eventoService,$rootScope,sessionService) {
							var slider = new Slider("#ex6");
							slider.on("change", function(sliderValue) {
								document.getElementById("ex6SliderVal").textContent = sliderValue.newValue;
								$scope.servicio.servicio.datosAuto.combustible=sliderValue.newValue;
							});
							sessionService.isAuthenticated().then(function(){
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
//								console.log($scope.servicio);
								var send={servicio:$scope.servicio};
								$http.post('/servicio/add', send)
										.then(function(response) {
											alert("Servicio Guardado");
											if(!$rootScope.serviciosHoy){
												$rootScope.serviciosHoy=[]
											}
//											$rootScope.serviciosHoy.push(response.data);
//											console.log(response);
											window.scrollTo(0, 0);
											$location.path("servicio/view/"+response.data.servicio.servicio.idServicio);
										}, function(response) {
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
								if($scope.servicio.cliente.nombre.length>3){
								$http.get("/search/cliente/"+$scope.servicio.cliente.nombre).success(function(data){
									$scope.clientesEncontrados=data;
									console.log(data);
									$('#searchCliente').typeahead({

									    source: $scope.clientesEncontrados,

									    updater:function (item) {
									    	var ind=$scope.clientesEncontrados.indexOf(item);

									    	$http.get("/search/filtra/"+item+"/"+$scope.tipos[ind]).success(function(data){
									    		$scope.listaServicios=data;
									    		$scope.busca="";
									    	});
									        return item;
									    }
									});
									$('#searchCliente').data('typeahead').source=$scope.clientesEncontrados;
								});
								}
							}
							$scope.mayusculas=function(value){
								value=value.toUpperCase();
								return value;
							}
							$scope.siguienteBtn=function(){
								$scope.showTab('auto');
								window.scrollTo(0, 0);
							}
							});
						} ]);
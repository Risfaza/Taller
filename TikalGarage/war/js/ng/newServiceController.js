app
		.controller(
				"newServiceController",
				[
						"$scope",'$http',
						function($scope,$http) {
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
									telefonoContacto:[]
									

								},
								auto : {
									equipamiento : {
										equipoAdicional : []
									}
								},
								eventos:[]
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
							$scope.guardar = function() {
								console.log($scope.servico);
								$http.post('/servicio/add',$scope.servicio).then(function(response){
									alert("Servicio Guardado");
									console.log(response);
								},function(response){
									console.log(response); 
									alert("Something went wrong");
								})
							}

							$scope.addCaract = function() {
								var caract = $scope.caracteristicaAuto;
								if ($scope.servicio.auto.equipamiento.equipoAdicional
										.indexOf(caract) < 0) {
									$scope.servicio.auto.equipamiento.equipoAdicional
											.push(caract);
								}
								$scope.caracteristicaAuto = "";
							}
							$scope.evento={};
							$scope.fecha=function(){
								var f= new Date();
//								$scope.evento.fecha= f.getDay()+"-"+f.getMonth()+"-"+f.getFullYear()+"T"+f.getHours()+":"+f.getMinutes();
							}
							
							$scope.findCliente = function() {
								// implementar servicio de búsqueda
							}

						} ]);
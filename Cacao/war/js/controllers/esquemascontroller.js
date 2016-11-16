app.service('esquemasService', [ '$http', '$q', function($http, $q) {
	this.getEmpresa = function(rfc) {
		var d = $q.defer();
		$http.get("/empresas/find/" + rfc).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}

	this.get= function(id){
		var d = $q.defer();
		$http.get("/esquemas/find/" + id).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
	
	this.saveEmpresa = function(empr) {
		var d = $q.defer();
		$http.post("/esquemas/add", empr).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
	
	this.save = function(empr) {
		var d = $q.defer();
		$http.post("/esquemas/update", empr).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
} ]);
app.controller("esquemasDetailsController", [ '$scope', '$routeParams', '$location',
'esquemasService', function($scope, $routeParams, $location, esquemasService) {
	esquemasService.get($routeParams.id).then(function(data){
		$scope.regimen=data;
		console.log(data);
	})
	
} ]);



app
		.controller(
				"esquemasEditController",
				[
						'$scope',
						'$routeParams',
						'$location',

						'esquemasService',
						function($scope, $routeParams, $location,
								esquemasService) {
							$scope.listpercecpciones = [
									{
										"clave" : "001",
										"descripcion" : "Sueldos, Salarios Rayas y Jornales",
									},
									{
										"clave" : "002",
										"descripcion" : "Gratificación Anual",
									},
									{
										"clave" : "003",
										"descripcion" : "Participación de los trabajadores en las Utilidades PTU",
									},
									{
										"clave" : "004",
										"descripcion" : "Fondo de Ahorro",
									},
									{
										"clave" : "005",
										"descripcion" : "Caja de Ahorro",
									},
									{
										"clave" : "006",
										"descripcion" : "Caja de Ahorro",
									},
									{
										"clave" : "009",
										"descripcion" : "Contribuciones a Cargo del Trabajador Pagadas por el Patrón",
									},
									{
										"clave" : "010",
										"descripcion" : "Premios por puntualidad",
									},
									{
										"clave" : "011",
										"descripcion" : "Prima de Seguro de vida",
									},
									{
										"clave" : "012",
										"descripcion" : "Seguro de Gastos Médicos Mayores",
									},
									{
										"clave" : "013",
										"descripcion" : "Cuotas Sindicales Pagadas por el Patrón",
									},
									{
										"clave" : "014",
										"descripcion" : "Subsidios por incapacidad",
									},
									{
										"clave" : "015",
										"descripcion" : "Becas para Trabajadore y/o hijos",
									},
									{
										"clave" : "016",
										"descripcion" : "Otros",
									},
									{
										"clave" : "017",
										"descripcion" : "Subsidios para el empleo",
									},
									{
										"clave" : "019",
										"descripcion" : "Horas extra",
									},
									{
										"clave" : "020",
										"descripcion" : "Prima Dominical",
									},
									{
										"clave" : "021",
										"descripcion" : "Prima Vacacional",
									},
									{
										"clave" : "022",
										"descripcion" : "Prima por antigüedad",
									},
									{
										"clave" : "023",
										"descripcion" : "Pagos por Separación",
									},
									{
										"clave" : "024",
										"descripcion" : "Seguro de Retiro",
									},
									{
										"clave" : "025",
										"descripcion" : "Indemnizaciones",
									},
									{
										"clave" : "026",
										"descripcion" : "Reembolso por funeral",
									},
									{
										"clave" : "027",
										"descripcion" : "Cuotas de seguridad social pagadas por el patrón",
									},
									{
										"clave" : "028",
										"descripcion" : "Comisiones",
									},
									{
										"clave" : "029",
										"descripcion" : "Vales de despensa",
									},
									{
										"clave" : "030",
										"descripcion" : "Vales de restaurante",
									},
									{
										"clave" : "031",
										"descripcion" : "Vales de gasolina",
									},
									{
										"clave" : "032",
										"descripcion" : "Vales de Ropa",
									},
									{
										"clave" : "033",
										"descripcion" : "Ayuda para renta",
									},
									{
										"clave" : "034",
										"descripcion" : "Ayuda para artículos escolares",
									},
									{
										"clave" : "035",
										"descripcion" : "Ayuda para anteojos",
									},
									{
										"clave" : "036",
										"descripcion" : "Ayuda para transporte",
									},
									{
										"clave" : "037",
										"descripcion" : "Ayuda para gastos de funeral",
									},
									{
										"clave" : "038",
										"descripcion" : "Otros ingresos por salarios",
									},
									{
										"clave" : "039",
										"descripcion" : "Jubilaciones, pensiones o haberes de retiro",
									},
									{
										"clave" : "040",
										"descripcion" : "Ingreso pagado por Entidades federativas, municipios, o demarcaciones territoriales del Distrito Federal, organismos autónomos y entidades paraestatales y paramunicipales con ingresos propios",
									},
									{
										"clave" : "041",
										"descripcion" : "Ingreso por Entidades federativas, municipios o demarcaciones territoriales del Distrito Federal, organismos autónomos y entidades paraestatales y paramunicipales con ingresos federales",
									},
									{
										"clave" : "042",
										"descripcion" : "Ingreso pagado por Entidades federativas, municipios, o demarcaciones territoriales del Distrito Federal, organismos autónomos y entidades paraestatales y paramunicipales con ingresos propios y federales",
									}, ]
							$scope.listdeducciones = [
									{
										"clave" : "001",
										"descripcion" : "Seguridad social",
									},
									{
										"clave" : "002",
										"descripcion" : "ISR",
									},
									{
										"clave" : "003",
										"descripcion" : "Aportaciones a retiro, cesantía en edad avanzada y vejez",
									},
									{
										"clave" : "004",
										"descripcion" : "Otros",
									},
									{
										"clave" : "005",
										"descripcion" : "Aportaciones a Fondo de vivienda",
									},
									{
										"clave" : "006",
										"descripcion" : "Descuento por incapacidad",
									},
									{
										"clave" : "007",
										"descripcion" : "Pension alimenticia",
									},
									{
										"clave" : "008",
										"descripcion" : "Renta",
									},
									{
										"clave" : "009",
										"descripcion" : "Préstamos a provenientes del Fondo Nacional de la Vivienda para losm Trabajadores",
									},
									{
										"clave" : "010",
										"descripcion" : "Pago por crédito vivienda",
									},
									{
										"clave" : "011",
										"descripcion" : "Pago de abonos INFONACOT",
									},
									{
										"clave" : "012",
										"descripcion" : "Anticipo de salarios",
									},
									{
										"clave" : "013",
										"descripcion" : "Pagos hechos con exceso al trabajador",
									},
									{
										"clave" : "014",
										"descripcion" : "Errores",
									},
									{
										"clave" : "015",
										"descripcion" : "Pérdidas",
									},
									{
										"clave" : "016",
										"descripcion" : "Averías",
									},
									{
										"clave" : "017",
										"descripcion" : "Aquisición de artículos producidos por la empresa o establecimiento",
									},
									{
										"clave" : "018",
										"descripcion" : "Cuotas para la constitución y fomento de sociedades cooperativas y de cajas de ahorro",
									},
									{
										"clave" : "019",
										"descripcion" : "Cuotas sindicales",
									},
									{
										"clave" : "020",
										"descripcion" : "Ausencia (Ausentismo)",
									},
									{
										"clave" : "021",
										"descripcion" : "Cuotas obrero patronales",
									}, ]
							$scope.listtipos = [
									{
										"clave" : "2",
										"descripcion" : "Sueldos y salarios",
									},
									{
										"clave" : "3",
										"descripcion" : "Jubilados",
									},
									{
										"clave" : "4",
										"descripcion" : "Pensionados",
									},
									{
										"clave" : "5",
										"descripcion" : "Asimilados a salarios, Miembros de las Sociedades Cooperativas de Producción",
									},
									{
										"clave" : "6",
										"descripcion" : "Asimilados a salarios, Integreantes de Sociedades y Asociaciones Civiles",
									},
									{
										"clave" : "7",
										"descripcion" : "Asimilados a salarios, Miembros de consejos directivos, de vigilancia, consultivos, honorarios a administradores, comisarios y gerentes generales",
									},
									{
										"clave" : "8",
										"descripcion" : "Asimilado a salarios, Actividad empresarial (comisionistas)",
									},
									{
										"clave" : "9",
										"descripcion" : "Asimilados a salarios, Actividad empresarial (comisionistas)",
									},
									{
										"clave" : "10",
										"descripcion" : "Asimilados a salarios, Ingresos acciones o títulos valor",
									}, ]
							$scope.regimen = {
								nombre : "",
								percepciones : [],
								deducciones : [],
							};

//							esquemasService.getEmpresa($routeParams.rfc).then(
//									function(data) {
//										$scope.empresa = data[0];
//									});
							$scope.newEsq = {};
							$scope.newDed = {};
							$scope.listaEsq = [];
							$scope.addPer = function(listpercecpciones) {
								// ToDoService.add($scope.newEsq);
								var esq = $scope.inputPerc;
								for (var i = 0; i < $scope.listpercecpciones.length; i++) {
									if ($scope.listpercecpciones[i].clave == esq.clave) {
										esq.descripcion = $scope.listpercecpciones[i].descripcion
									}
								}
								$scope.regimen.percepciones.push(esq);
								$scope.inputPerc = {};
							}
							$scope.addDed = function(listdeducciones) {

								var ded = $scope.inputDeduc;
								for (var i = 0; i < $scope.listdeducciones.length; i++) {
									if ($scope.listdeducciones[i].clave == ded.clave) {
										ded.descripcion = $scope.listdeducciones[i].descripcion
									}
								}
								$scope.regimen.deducciones.push(ded);
								$scope.inputDeduc = {};
							}

							esquemasService.get($routeParams.id).then(function(data){
								$scope.regimen=data;
								if(!$scope.regimen.percepciones){
									$scope.regimen.percepciones = [];
								}
								
								if(!$scope.regimen.deducciones){
									$scope.regimen.deducciones = [];
								}
							});
							$scope.showFormPer=false;
							$scope.showPer=function(){
								$scope.showFormPer=true;
							}
							$scope.showDed=function(){
								$scope.showFormPer=false;
							}
							
							$scope.save = function(reg) {
//								if (!empr.regimenes) {
//									empr.regimenes = []
//								}
//								var send = {
//									empresa : empr.RFC,
//									regimen : $scope.regimen,
//									tipo : $scope.regimen.tipoRegimen
//								}

								esquemasService.save(reg).then(
										function(data) {
											console.log(data);
											alert("Guardado con éxito");
											$location.path("/empresas/list")
										})

							};
						} ]);

app
.controller(
		"esquemasController",
		[
				'$scope',
				'$routeParams',
				'$location',

				'esquemasService',
				function($scope, $routeParams, $location,
						esquemasService) {
					$scope.listpercecpciones = [
							{
								"clave" : "001",
								"descripcion" : "Sueldos, Salarios Rayas y Jornales",
							},
							{
								"clave" : "002",
								"descripcion" : "Gratificación Anual",
							},
							{
								"clave" : "003",
								"descripcion" : "Participación de los trabajadores en las Utilidades PTU",
							},
							{
								"clave" : "004",
								"descripcion" : "Fondo de Ahorro",
							},
							{
								"clave" : "005",
								"descripcion" : "Caja de Ahorro",
							},
							{
								"clave" : "006",
								"descripcion" : "Caja de Ahorro",
							},
							{
								"clave" : "009",
								"descripcion" : "Contribuciones a Cargo del Trabajador Pagadas por el Patrón",
							},
							{
								"clave" : "010",
								"descripcion" : "Premios por puntualidad",
							},
							{
								"clave" : "011",
								"descripcion" : "Prima de Seguro de vida",
							},
							{
								"clave" : "012",
								"descripcion" : "Seguro de Gastos Médicos Mayores",
							},
							{
								"clave" : "013",
								"descripcion" : "Cuotas Sindicales Pagadas por el Patrón",
							},
							{
								"clave" : "014",
								"descripcion" : "Subsidios por incapacidad",
							},
							{
								"clave" : "015",
								"descripcion" : "Becas para Trabajadore y/o hijos",
							},
							{
								"clave" : "016",
								"descripcion" : "Otros",
							},
							{
								"clave" : "017",
								"descripcion" : "Subsidios para el empleo",
							},
							{
								"clave" : "019",
								"descripcion" : "Horas extra",
							},
							{
								"clave" : "020",
								"descripcion" : "Prima Dominical",
							},
							{
								"clave" : "021",
								"descripcion" : "Prima Vacacional",
							},
							{
								"clave" : "022",
								"descripcion" : "Prima por antigüedad",
							},
							{
								"clave" : "023",
								"descripcion" : "Pagos por Separación",
							},
							{
								"clave" : "024",
								"descripcion" : "Seguro de Retiro",
							},
							{
								"clave" : "025",
								"descripcion" : "Indemnizaciones",
							},
							{
								"clave" : "026",
								"descripcion" : "Reembolso por funeral",
							},
							{
								"clave" : "027",
								"descripcion" : "Cuotas de seguridad social pagadas por el patrón",
							},
							{
								"clave" : "028",
								"descripcion" : "Comisiones",
							},
							{
								"clave" : "029",
								"descripcion" : "Vales de despensa",
							},
							{
								"clave" : "030",
								"descripcion" : "Vales de restaurante",
							},
							{
								"clave" : "031",
								"descripcion" : "Vales de gasolina",
							},
							{
								"clave" : "032",
								"descripcion" : "Vales de Ropa",
							},
							{
								"clave" : "033",
								"descripcion" : "Ayuda para renta",
							},
							{
								"clave" : "034",
								"descripcion" : "Ayuda para artículos escolares",
							},
							{
								"clave" : "035",
								"descripcion" : "Ayuda para anteojos",
							},
							{
								"clave" : "036",
								"descripcion" : "Ayuda para transporte",
							},
							{
								"clave" : "037",
								"descripcion" : "Ayuda para gastos de funeral",
							},
							{
								"clave" : "038",
								"descripcion" : "Otros ingresos por salarios",
							},
							{
								"clave" : "039",
								"descripcion" : "Jubilaciones, pensiones o haberes de retiro",
							},
							{
								"clave" : "040",
								"descripcion" : "Ingreso pagado por Entidades federativas, municipios, o demarcaciones territoriales del Distrito Federal, organismos autónomos y entidades paraestatales y paramunicipales con ingresos propios",
							},
							{
								"clave" : "041",
								"descripcion" : "Ingreso por Entidades federativas, municipios o demarcaciones territoriales del Distrito Federal, organismos autónomos y entidades paraestatales y paramunicipales con ingresos federales",
							},
							{
								"clave" : "042",
								"descripcion" : "Ingreso pagado por Entidades federativas, municipios, o demarcaciones territoriales del Distrito Federal, organismos autónomos y entidades paraestatales y paramunicipales con ingresos propios y federales",
							}, ]
					$scope.listdeducciones = [
							{
								"clave" : "001",
								"descripcion" : "Seguridad social",
							},
							{
								"clave" : "002",
								"descripcion" : "ISR",
							},
							{
								"clave" : "003",
								"descripcion" : "Aportaciones a retiro, cesantía en edad avanzada y vejez",
							},
							{
								"clave" : "004",
								"descripcion" : "Otros",
							},
							{
								"clave" : "005",
								"descripcion" : "Aportaciones a Fondo de vivienda",
							},
							{
								"clave" : "006",
								"descripcion" : "Descuento por incapacidad",
							},
							{
								"clave" : "007",
								"descripcion" : "Pension alimenticia",
							},
							{
								"clave" : "008",
								"descripcion" : "Renta",
							},
							{
								"clave" : "009",
								"descripcion" : "Préstamos a provenientes del Fondo Nacional de la Vivienda para losm Trabajadores",
							},
							{
								"clave" : "010",
								"descripcion" : "Pago por crédito vivienda",
							},
							{
								"clave" : "011",
								"descripcion" : "Pago de abonos INFONACOT",
							},
							{
								"clave" : "012",
								"descripcion" : "Anticipo de salarios",
							},
							{
								"clave" : "013",
								"descripcion" : "Pagos hechos con exceso al trabajador",
							},
							{
								"clave" : "014",
								"descripcion" : "Errores",
							},
							{
								"clave" : "015",
								"descripcion" : "Pérdidas",
							},
							{
								"clave" : "016",
								"descripcion" : "Averías",
							},
							{
								"clave" : "017",
								"descripcion" : "Aquisición de artículos producidos por la empresa o establecimiento",
							},
							{
								"clave" : "018",
								"descripcion" : "Cuotas para la constitución y fomento de sociedades cooperativas y de cajas de ahorro",
							},
							{
								"clave" : "019",
								"descripcion" : "Cuotas sindicales",
							},
							{
								"clave" : "020",
								"descripcion" : "Ausencia (Ausentismo)",
							},
							{
								"clave" : "021",
								"descripcion" : "Cuotas obrero patronales",
							}, ]
					$scope.listtipos = [
							{
								"clave" : "2",
								"descripcion" : "Sueldos y salarios",
							},
							{
								"clave" : "3",
								"descripcion" : "Jubilados",
							},
							{
								"clave" : "4",
								"descripcion" : "Pensionados",
							},
							{
								"clave" : "5",
								"descripcion" : "Asimilados a salarios, Miembros de las Sociedades Cooperativas de Producción",
							},
							{
								"clave" : "6",
								"descripcion" : "Asimilados a salarios, Integreantes de Sociedades y Asociaciones Civiles",
							},
							{
								"clave" : "7",
								"descripcion" : "Asimilados a salarios, Miembros de consejos directivos, de vigilancia, consultivos, honorarios a administradores, comisarios y gerentes generales",
							},
							{
								"clave" : "8",
								"descripcion" : "Asimilado a salarios, Actividad empresarial (comisionistas)",
							},
							{
								"clave" : "9",
								"descripcion" : "Asimilados a salarios, Actividad empresarial (comisionistas)",
							},
							{
								"clave" : "10",
								"descripcion" : "Asimilados a salarios, Ingresos acciones o títulos valor",
							}, ]
					$scope.regimen = {
						nombre : "",
						percepciones : [],
						deducciones : [],
					};

					esquemasService.getEmpresa($routeParams.rfc).then(
							function(data) {
								$scope.empresa = data[0];
							});
					$scope.newEsq = {};
					$scope.newDed = {};
					$scope.listaEsq = [];
					$scope.addPer = function(listpercecpciones) {
						// ToDoService.add($scope.newEsq);
						var esq = $scope.inputPerc;
						for (var i = 0; i < $scope.listpercecpciones.length; i++) {
							if ($scope.listpercecpciones[i].clave == esq.clave) {
								esq.descripcion = $scope.listpercecpciones[i].descripcion
							}
						}
						$scope.regimen.percepciones.push(esq);
						$scope.inputPerc = {};
					}
					$scope.addDed = function(listdeducciones) {

						var ded = $scope.inputDeduc;
						for (var i = 0; i < $scope.listdeducciones.length; i++) {
							if ($scope.listdeducciones[i].clave == ded.clave) {
								ded.descripcion = $scope.listdeducciones[i].descripcion
							}
						}
						$scope.regimen.deducciones.push(ded);
						$scope.inputDeduc = {};
					}
					
					$scope.save = function(empr) {
						if (!empr.regimenes) {
							empr.regimenes = []
						}
						var send = {
							empresa : empr.RFC,
							regimen : $scope.regimen,
							tipo : $scope.regimen.tipoRegimen
						}
						
						empr.regimenes.push($scope.regimen);
						console.log(send); 
						esquemasService.saveEmpresa(send).then(
								function(data) {
									console.log(data);
									alert("Guardado con éxito");
									$location.path("/empresas/list")
								})

					};
				} ]);


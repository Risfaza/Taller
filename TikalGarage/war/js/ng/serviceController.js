app.service('eventoService',['$http','$q',function($http,$q){
	this.addEvento=function(evento){
		var d= $q.defer();
		var fecha=""+ evento.fecha.toString();
		var mes= fecha.substring(4,7);
		if(mes=="Jan"){
			mes="01";
		}
		if(mes=="Feb"){
			mes="02";
		}
		if(mes=="Mar"){
			mes="03";
		}
		if(mes=="Apr"){
			mes="04";
		}
		if(mes=="May"){
			mes="05";
		}
		if(mes=="Jun"){
			mes="06";
		}
		if(mes=="Jul"){
			mes="07";
		}
		if(mes=="Aug"){
			mes="08";
		}
		if(mes=="Sep"){
			mes="09";
		}
		if(mes=="Oct"){
			mes="10";
		}
		if(mes=="Nov"){
			mes="11";
		}
		if(mes=="Dec"){
			mes="12";
		}
		evento.fecha=null;
		send={
				evento:evento,
				fecha:fecha.substring(8,10)+"/"+mes+"/"+fecha.substring(11,24)
		}
		$http.post("/eventos/add/",send).then(function(response){
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	}
	this.getServicio=function(id){
		var d= $q.defer();
		$http.get("/servicio/findServicio/"+id).then(function(response){
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	}
	this.getBitacora=function(id){
		var d= $q.defer();
		$http.get("/eventos/getBitacora/"+id).then(function(response){
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	}
}]);

app
		.controller(
				"serviceController",
				[
						"$scope",
						'$http','fileService','$sce','fileUpload','eventoService','$routeParams','modalService','$rootScope',
						function($scope, $http,fileService,$sce,fileUpload,eventoService,$routeParams,modalService,$rootScope) {
							$scope.ids = [ "cliente", "auto", "bitacora",
									"presupuesto", "damage", "cobranza" ];
							$scope.uri={
									url:""
							};
							$scope.presupuesto={
									
							}
							$scope.urlpost = $sce.trustAsResourceUrl($scope.uri.url);
						    $http.get("/servicio/getUpldUrl").then(function(response){
						    	$scope.uri=response.data;
						    });
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
							}
							eventoService.getServicio($routeParams.id).then(function(data){
								$scope.servicio.servicio=data.servicio;
								$scope.servicio.auto=data.auto;
								$scope.servicio.cliente=data.cliente;
							});

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
								var send={
										servicio:$scope.servicio,
										presupuesto:$scope.gruposCosto
								}
//								console.log(send);
								$http.post('/servicio/save', send)
										.then(function(response) {
											alert("Servicio Guardado");
										}, function(response) {
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
							$scope.evento = {id:$scope.servicio.servicio.id};
							$scope.eventos = [];
							eventoService.getBitacora($routeParams.id).then(function(data){
								$scope.eventos=data;
							});
							$scope.fecha = function() {
								var f = new Date();
								modalService.aver('showModalEvento')
								
							}

							$scope.sendImages = function(id) {
								if($scope.images.length){
									var file = $scope.images;
									fileUpload.uploadFileToUrl(file, $scope.uri.url,id).then(function(data){
										$scope.eventos.push(data);
										// recursivo
									});
								}
							}

							$scope.addEvento = function() {
// var e = $scope.evento;
// $scope.eventos.push(e);
// $scope.evento = {
// evidencia:[]
// };
								$scope.evento.id=$routeParams.id;
								eventoService.addEvento($scope.evento).then(function(data){
//									console.log(data);
									$scope.images= fileService.getFile("b_pics");
									$scope.indice=0;
									$scope.sendImages(data.idEvento);
								})
								
							}
							$scope.results=['Alabama','Alaska','Arizona','Arkansas','California','Colorado','Connecticut','Delaware','Florida','Georgia','Hawaii','Idaho','Illinois','Indiana','Iowa','Kansas','Kentucky','Louisiana','Maine','Maryland','Massachusetts','Michigan','Minnesota','Mississippi','Missouri','Montana','Nebraska','Nevada','New Hampshire','New Jersey','New Mexico','New York','North Dakota','North Carolina','Ohio','Oklahoma','Oregon','Pennsylvania','Rhode Island','South Carolina','South Dakota','Tennessee','Texas','Utah','Vermont','Virginia','Washington','West Virginia','Wisconsin','Wyoming'];
							$scope.findCliente = function() {
								// implementar servicio de búsqueda
							}
							$scope.gruposCosto=[];
							$scope.addGrupo=function(){
								var nombregrupo= $scope.nombreGrupo;
								$scope.nombreGrupo="";
								$scope.servicio.servicio.gruposCosto.push(nombregrupo);
								var tipo=$scope.filtro.tipo;
								$scope.gruposCosto.push({
									nombre:nombregrupo,
									presupuestos:[],
									tipo:tipo
								});
								
							}
							$scope.modalEvento=true;
							$scope.verModal=function(modal){
								$scope.modals=modalService.aver(modal);
							}
							$scope.addPresupuesto=function(gru){
								if(!gru.presupuestos){
									gru.presupuestos=[];
								}
								var tipo=$scope.filtro.tipo;
								gru.presupuestos.push({tipo:tipo,id:$routeParams.id,concepto:""});
							}
							$scope.filtro={tipo:"ME"};
							$scope.showPresupuesto= function(ver){
								$scope.filtro.tipo=ver;
							}
							
							$scope.cotizaciones=function(e){
								console.log(e);
								if(e.subtipo){
									if(e.subtipo=="RE" && e.concepto!=""){
										alert("cotizaa");
									}
								}
								
							}
							
						} ]);



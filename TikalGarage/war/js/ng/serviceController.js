app.service('eventoService', [
		'$http',
		'$q',
		function($http, $q) {
			this.addEvento = function(evento) {
				var d = $q.defer();
				var fecha = "" + evento.fecha.toString();
				var mes = fecha.substring(4, 7);
				if (mes == "Jan") {
					mes = "01";
				}
				if (mes == "Feb") {
					mes = "02";
				}
				if (mes == "Mar") {
					mes = "03";
				}
				if (mes == "Apr") {
					mes = "04";
				}
				if (mes == "May") {
					mes = "05";
				}
				if (mes == "Jun") {
					mes = "06";
				}
				if (mes == "Jul") {
					mes = "07";
				}
				if (mes == "Aug") {
					mes = "08";
				}
				if (mes == "Sep") {
					mes = "09";
				}
				if (mes == "Oct") {
					mes = "10";
				}
				if (mes == "Nov") {
					mes = "11";
				}
				if (mes == "Dec") {
					mes = "12";
				}
				evento.fecha = null;
				send = {
					evento : evento,
					fecha : fecha.substring(8, 10) + "/" + mes + "/"
							+ fecha.substring(11, 24)
				}
				$http.post("/eventos/add/", send).then(function(response) {
					d.resolve(response.data);
				}, function(response) {
				});
				return d.promise;
			}
			this.getServicio = function(id) {
				var d = $q.defer();
				$http.get("/servicio/findServicio/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			
			this.updateBitacora=function(eventos,id){
				var d = $q.defer();
				$http.post("/eventos/update/",{id:id,eventos:eventos}).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			
			this.getBitacora = function(id) {
				var d = $q.defer();
				$http.get("/eventos/getBitacora/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			
			this.removeEvento=function(id){
				var d = $q.defer();
				$http.post("/eventos/remove/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
		} ]);

app.controller("serviceController", [
		"$scope",
		'$http',
		'fileService',
		'$sce',
		'fileUpload',
		'eventoService',
		'$routeParams',
		'modalService',
		'$rootScope',
		'$window',
		function($scope, $http, fileService, $sce, fileUpload, eventoService,
				$routeParams, modalService, $rootScope,$window) {
			$scope.ids = [ "cliente", "auto", "bitacora", "presupuesto",
					"damage", "cobranza" ];
			$scope.uri = {
				url : ""
			};
			$scope.presupuesto = {

			}
			$scope.gruposCosto = [];
			$scope.urlpost = $sce.trustAsResourceUrl($scope.uri.url);
			$http.get("/servicio/getUpldUrl").then(function(response) {
				$scope.uri = response.data;
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
			eventoService.getServicio($routeParams.id).then(function(data) {
				$scope.servicio.servicio = data.servicio.servicio;
				if($scope.servicio.servicio.datosAuto.combustible){
					var com=$scope.servicio.servicio.datosAuto.combustible;
					com = parseInt(com); 
					$scope.servicio.servicio.datosAuto.combustible=com;
				}
				$scope.servicio.auto = data.servicio.auto;
				$scope.servicio.cliente = data.servicio.cliente;
				$scope.servicio.gruposCosto = data.presupuesto;
				$rootScope.actual=$scope.servicio;
				$rootScope.detallesView=true;
				console.log($rootScope.actual);
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
				var send = {
					servicio : $scope.servicio,
					presupuesto : $scope.servicio.gruposCosto
				}
				// console.log(send);
				$http.post('/servicio/save', send).then(function(response) {
					alert("Servicio Guardado");
				}, function(response) {
					alert("Something went wrong");
				})
				var lista=$scope.listcotizaciones;
				var nc=$scope.newCot;
				lista.push(nc)
				$http.post('/cotizacion/save', {listcotizaciones:lista,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo}).then(function(response) {
					$scope.listcotizaciones=[];
				}, function(response) {
				})
			}
			
			$scope.guardar = function() {
				var send = {
					servicio : $scope.servicio,
					presupuesto : $scope.servicio.gruposCosto
				}
				// console.log(send);
				$http.post('/servicio/save', send).then(function(response) {
					alert("Servicio Guardado");
				}, function(response) {
					alert("Something went wrong");
				})
				var lista=$scope.listcotizaciones;
				var nc=$scope.newCot;
				lista.push(nc)
				eventoService.getBitacora($routeParams.id).then(function(data) {
				$scope.eventos = data;
				console.log(data);
			});

				$http.post('/cotizacion/save', {listcotizaciones:lista,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo}).then(function(response) {
					$scope.listcotizaciones=[];
				}, function(response) {
				})
			}
			
			
			$scope.guardar = function() {
				var send = {
					servicio : $scope.servicio,
					presupuesto : $scope.servicio.gruposCosto
				}
				// console.log(send);
				$http.post('/servicio/save', send).then(function(response) {
					alert("Servicio Guardado");
				}, function(response) {
					alert("Something went wrong");
				})
				var lista=$scope.listcotizaciones;
				var nc=$scope.newCot;
				lista.push(nc)
				$http.post('/cotizacion/save', {listcotizaciones:lista,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo}).then(function(response) {
					$scope.listcotizaciones=[];
				}, function(response) {
				})
			}
			
			$scope.guardar2 = function() {
				var send = {
					servicio : $scope.servicio,
					presupuesto : $scope.servicio.gruposCosto
				}
				// console.log(send);
				$http.post('/servicio/save', send).then(function(response) {
//					alert("Servicio Guardado");
				}, function(response) {
					alert("Something went wrong");
				})
				var lista=$scope.listcotizaciones;
				var nc=$scope.newCot;
				lista.push(nc)
				eventoService.getBitacora($routeParams.id).then(function(data) {
				$scope.eventos = data;
				console.log(data);
			});

				$http.post('/cotizacion/save', {listcotizaciones:lista,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo}).then(function(response) {
					$scope.listcotizaciones=[];
				}, function(response) {
				})
				eventoService.updateBitacora($scope.eventos,$routeParams.id).then(function(data) {
					$scope.eventos = data;
//					$window.location.href = '/reporte/presupuestoPDF/'+$routeParams.id;
				});
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
			$scope.evento = {
				id : $scope.servicio.servicio.id
			};
			$scope.eventos = [];
			eventoService.getBitacora($routeParams.id).then(function(data) {
				$scope.eventos = data;
				console.log(data);
			});
			$scope.fecha = function() {
				var f = new Date();
				modalService.aver('showModalEvento');

			}

			$scope.sendImages = function(id) {
				if ($scope.images.length) {
					var file = $scope.images;
					fileUpload.uploadFileToUrl(file, $scope.uri.url, id).then(
							function(data) {
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
				$scope.evento.id = $routeParams.id;
				eventoService.addEvento($scope.evento).then(function(data) {
					// console.log(data);
					$scope.images = fileService.getFile("b_pics");
					$scope.indice = 0;
					$scope.sendImages(data.idEvento);
				})
				

			}
			$scope.results = [ 'Alabama', 'Alaska', 'Arizona', 'Arkansas',
					'California', 'Colorado', 'Connecticut', 'Delaware',
					'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois',
					'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
					'Maine', 'Maryland', 'Massachusetts', 'Michigan',
					'Minnesota', 'Mississippi', 'Missouri', 'Montana',
					'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey',
					'New Mexico', 'New York', 'North Dakota', 'North Carolina',
					'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania',
					'Rhode Island', 'South Carolina', 'South Dakota',
					'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia',
					'Washington', 'West Virginia', 'Wisconsin', 'Wyoming' ];
			$scope.findCliente = function() {
				// implementar servicio de búsqueda
			}

			$scope.addGrupo = function() {
				var nombregrupo = $scope.nombreGrupo;
				$scope.nombreGrupo = "";
				$scope.servicio.servicio.gruposCosto.push(nombregrupo);
				var tipo = $scope.filtro.tipo;
				$scope.servicio.gruposCosto.push({
					nombre : nombregrupo,
					presupuestos : [],
					tipo : tipo
				});

			}
			$scope.modalEvento = true;
			$scope.verModal = function(modal) {
				$scope.modals = modalService.aver(modal);
			}
			$scope.addPresupuesto = function(gru,tipo) {
				if (!gru.presupuestos) {
					gru.presupuestos = [];
				}
//				var tipo = $scope.filtro.tipo;
				gru.presupuestos.push({
					tipo : tipo,
					id : $routeParams.id,
					concepto : "",
					precioUnitario:{value:""}
				});
			}
			$scope.filtro = {
				tipo : "HP"
			};
			$scope.filtroCot = {
				concepto : ""
			};
			$scope.showPresupuesto = function(ver) {
				$scope.filtro.tipo = ver;
			}
			$scope.showCotizaciones = false;
			$scope.addCot = function() {
				var cot = $scope.newCot;
				var concepto = $scope.cotizando.concepto;
				$scope.listcotizaciones.push(cot);
				$scope.newCot = {
					proveedor : "",
					precio : "",
					tiempo : "",
					concepto : concepto,
					selected:false
				};
			}
			$scope.newCot = {selected:false};
			$scope.setPrecio = function(precio) {
				alert(precio);
				$scope.cotizando.precioUnitario.value = precio;
			}
			$scope.listcotizaciones=[];
			
			$scope.borrarEvento=function(e){
				var index=$scope.eventos.indexOf(e);
				$scope.eventos.splice(index,1);
				eventoService.removeEvento(e.idEvento).then(function(data){
					console.log(data);
				});
			};
			$scope.verEvidencias=function(e){
				
				$scope.modals = modalService.aver('showModalEvidencias');
//				console.log($scope.modals);
				$scope.evidenciasVer=e.evidencia;
				$scope.evidenciasAdd=e.idEvento;
			};
			
			$scope.appendImages=function(){
				$scope.images = fileService.getFile("b_pics");
				$scope.indice = 0;
				var idEvento=$scope.evidenciasAdd
				$scope.sendImages(idEvento);
			}
			$scope.newDatoCobranza={monto:{value:""}};
			$scope.appendPago=function(){
				var f = new Date();
				var fecha= f.getDate()+'/'+(f.getMonth()+1)+'/'+f.getFullYear();
				var pago= {fecha:fecha};
				$scope.servicio.servicio.cobranza.pagos.push(pago);
//				$scope.newDatoCobranza={monto:{value:""}};
//				console.log($scope.servicio.servicio);
			}
			
			$scope.cotizaciones = function(e) {
				console.log(e);
				if ($scope.newCot.proveedor && $scope.newCot.precio
						&& $scope.newCot.tiempo) {
					if ($scope.newCot.proveedor != ""
							&& $scope.newCot.precio != ""
							&& $scope.newCot.tiempo != "") {
						var nc = $scope.newCot;
						$scope.listcotizaciones.push(nc);
						$scope.newCot = {
							proveedor : "",
							precio : "",
							tiempo : "",
							concepto : "",
							selected:false
						};
					}
				}
				if (e.subtipo) {
					if (e.subtipo == "RE" && e.concepto != "") {
						$scope.cotizando = e;
						$scope.filtroCot.concepto = e.concepto;
						$scope.newCot.concepto = e.concepto;
						$scope.showCotizaciones = true;
						$http.get("/cotizacion/get", {
							params : {
								concepto : e.concepto,
								tipo : $scope.servicio.auto.tipo,
								modelo : $scope.servicio.auto.modelo
							}
						}).then(function(data) {
							$scope.listcotizaciones=data.data;
						}, function(data) {

						});
					} else {
						$scope.showCotizaciones = false;
					}
				}

			}
			
			$scope.selectImage=function(img){
				if(img.appended == true){
					img.appended=false;
				}else{img.appended=true;}
				$('#'+img.image).toggleClass("selected");
			}
			
			$scope.imprimir=function(){
				$scope.guardar2();
				$scope.modals=modalService.aver('modalImprimir');
			}
			
			$scope.verDocumento=function(tipo){
				$window.location.href = '/reporte/presupuestoPDF'+tipo+'/'+$routeParams.id;
			}
			
			function currency(value, decimals, separators) {
			    decimals = decimals >= 0 ? parseInt(decimals, 0) : 2;
			    separators = separators || ['.', "'", ','];
			    var number = (parseFloat(value) || 0).toFixed(decimals);
			    if (number.length <= (4 + decimals))
			        return number.replace('.', separators[separators.length - 1]);
			    var parts = number.split(/[-.]/);
			    value = parts[parts.length > 1 ? parts.length - 2 : 0];
			    var result = value.substr(value.length - 3, 3) + (parts.length > 1 ?
			        separators[separators.length - 1] + parts[parts.length - 1] : '');
			    var start = value.length - 6;
			    var idx = 0;
			    while (start > -3) {
			        result = (start > 0 ? value.substr(start, 3) : value.substr(0, 3 + start))
			            + separators[idx] + result;
			        idx = (++idx) % 2;
			        start -= 3;
			    }
			    return (parts.length == 3 ? '-' : '') + result;
			}
			
			$scope.$watch('servicio.servicio.cobranza',function(){
				$scope.aCuenta=0.0;
				var cobranza= $scope.servicio.servicio.cobranza;
				for(var i=0; i<cobranza.pagos.length;i++){
					$scope.aCuenta += parseFloat(cobranza.pagos[i].monto.value);
				} 
				$rootScope.saldo='$'+currency($scope.servicio.servicio.metadata.costoTotal.value - $scope.aCuenta, 2, [',', "'", '.']);
			},true);

			
		} ]);
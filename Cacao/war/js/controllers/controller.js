var app = angular.module("app", ['ngRoute']);
app.config(['$routeProvider',function($routeProvider) {

	$routeProvider.when('/empresas', {
		templateUrl: "pages/empresas.html",
		controller: "empresasController"
	});
	
	$routeProvider.when('/esquemas/agregar/:rfc', {
		templateUrl: "pages/esquemas.html",
		controller: "esquemasController"
	});

	$routeProvider.when('/empleados', {
		templateUrl: "pages/empleadosList.html",
		controller: "empleadosController"
	});

	$routeProvider.when('/altausuarios', {
		templateUrl: "pages/altausuarios.html",
		/*controller: "usuariosController"*/
	});

	$routeProvider.when('/empresas/list', {
		templateUrl: "pages/empresasList.html",
		controller: "empresasListController"
	});

	$routeProvider.when('/cfdi', {
		templateUrl: "pages/cfdi.html",
		controller: "cfdiController"
	});

	$routeProvider.when('/cfdisinqr', {
		templateUrl: "pages/cfdisinqr.html",
		controller: "cfdiController"
	});

	$routeProvider.when('/empresas/edit/:rfc', {
		templateUrl: "pages/empresas.html",
		controller: "empresasEditController"
	});
	
	$routeProvider.when('/empresas/details/:rfc', {
		templateUrl: "pages/empresasDetails.html",
		controller: "empresasDetailsController"
	});
	
	$routeProvider.when('/esquemas/details/:id', {
		templateUrl: "pages/esquemasDetails.html",
		controller: "esquemasDetailsController"
	});
	
	$routeProvider.when('/esquemas/edit/:id', {
		templateUrl: "pages/esquemasEdit.html",
		controller: "esquemasEditController"
	});	
	
	$routeProvider.when('/empleados/list/:id', {
		templateUrl: "pages/empleadosList.html",
		controller: "empleadosListController"
	});
	
	$routeProvider.when('/empleados/add/:id', {
		templateUrl: "pages/empleados.html",
		controller: "empleadosController"
	});
	
	$routeProvider.otherwise({
		redirectTo: 'index.html'
	});   

}]);
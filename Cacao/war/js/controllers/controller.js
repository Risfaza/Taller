var app = angular.module("app", ['ngRoute']);
app.config(['$routeProvider',function($routeProvider) {

 $routeProvider.when('/empresas', {
  templateUrl: "pages/empresas.html",
  controller: "empresasController"
});

 $routeProvider.when('/esquemas', {
  templateUrl: "pages/esquemas.html",
  controller: "esquemasController"
});

 $routeProvider.when('/empleados', {
  templateUrl: "pages/empleados.html",
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

 $routeProvider.when('/empresas/edit/:rfc', {
	  templateUrl: "pages/empresas.html",
	  controller: "empresasEditController"
	});
 $routeProvider.otherwise({
  redirectTo: 'index.html'
});   

}]);
var app = angular.module("app", ['ngRoute']);
app.config(['$routeProvider',function($routeProvider) {
 
 
  $routeProvider.when('', {
    templateUrl: "",
    controller: ""
  });
  
  
  $routeProvider.otherwise({
    redirectTo: '/pagina1'
  });   
  
}]);
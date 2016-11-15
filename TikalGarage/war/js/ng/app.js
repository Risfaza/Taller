var app= angular.module('app',['ngRoute']);

app.config(['$routeProvider',function($routeProvider) {
	   
	 
	  $routeProvider.when('/servicio/create', {
	    templateUrl: "pages/newService.html",
	    controller: "newServiceController"
	  });
	  $routeProvider.when('/', {
		    templateUrl: "pages/mainpage.html",
		    controller: "mainPageController"
		  });
	  
	   
	  $routeProvider.otherwise({
		  
	        redirectTo: '/'
	  });   
	 
	}]);


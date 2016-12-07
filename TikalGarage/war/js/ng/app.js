var app= angular.module('app',['ngRoute']);

app.config(['$routeProvider',function($routeProvider) {
	  $routeProvider.when('/servicio/create', {
	    templateUrl: "pages/newService.html",
	    controller: "newServiceController"
	  });
	  $routeProvider.when('/servicio/view/:id', {
		    templateUrl: "pages/service.html",
		    controller: "serviceController"
		  });
	  $routeProvider.when('/', {
//		    templateUrl: "pages/mainpage.html",
//		    controller: "mainPageController"
		  redirectTo: '/listServicios'
		  });
	  $routeProvider.when('/listServicios', {
		    templateUrl: "pages/serviceList.html",
		    controller: "serviceListController"
		  });
	   
	  $routeProvider.otherwise({
	        redirectTo: '/listServicios'
	  });   
	 
	}]);

app.run(['$rootScope','$http',function($rootScope,$http) {
	$rootScope.serviciosHoy=[];
	$http.get("/servicio/serviciosHoy").then(function(response){
		$rootScope.serviciosHoy=response.data;
		console.log(response.data);
	})
	
}]);


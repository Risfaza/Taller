var app = angular.module('app', [ 'ngRoute' ]);

app
		.config([
				'$routeProvider',
				'$httpProvider',
				function($routeProvider, $httpProvider) {
					$routeProvider.when('/servicio/create', {
						templateUrl : "pages/newService.html",
						controller : "newServiceController"
					});
					$routeProvider.when('/servicio/view/:id', {
						templateUrl : "pages/service.html",
						controller : "serviceController"
					});
					$routeProvider.when('/', {
						// templateUrl: "pages/mainpage.html",
						// controller: "mainPageController"
						redirectTo : '/listServicios'
					});
					$routeProvider.when('/listServicios', {
						templateUrl : "pages/serviceList.html",
						controller : "serviceListController"
					});

					$routeProvider.when('/login', {
						templateUrl : 'pages/login.html',
						controller : 'navigation'
					});
					
					$routeProvider.when('/registro', {
						templateUrl : 'pages/registro.html',
						controller : 'usuarioController'
					})
					
					$routeProvider.when('/reporte', {
						
					});

					$routeProvider.otherwise({
						redirectTo : '/listServicios'
					});

					$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
					$httpProvider.defaults.headers.common["Access-Control-Allow-Methods"] = "POST";
					$httpProvider.defaults.headers.common["Access-Control-Allow-Origin"] = '*';

				} ]);

app.run([ '$rootScope', '$http', function($rootScope, $http) {
	$rootScope.serviciosHoy = [];
	$http.get("/servicio/serviciosHoy").then(function(response) {
		$rootScope.serviciosHoy = response.data;
		console.log(response.data);
	})

} ]);

app.controller('navigation',['sessionService','$rootScope', '$scope', '$http', '$location',

function(sessionService,$rootScope, $scope, $http, $location) {
	//
//	var authenticate = function(credentials, callback) {
//
//		var headers = credentials ? {
//			authorization : "Basic "
//					+ btoa(credentials.username + ":" + credentials.password)
//		} : {};
//
//		$http.get('user', {
//			headers : headers
//		}).success(function(data) {
//			if (data.name) {
//				$rootScope.authenticated = true;
//			} else {
//				$rootScope.authenticated = false;
//			}
//			callback && callback();
//		}).error(function() {
//			$rootScope.authenticated = false;
//			callback && callback();
//		});
//
//	}

//	authenticate();
	$scope.credentials = {};
	$scope.login = function() {
		sessionService.authenticate($scope.credentials, function() {
			if ($rootScope.authenticated) {
				$location.path("/");
				$scope.error = false;
			} else {
				$location.path("/login");
				$scope.error = true;
			}
		});
	};
}]);

app.service('sessionService',['$rootScope','$http','$location',function($rootScope,$http,$location){
	this.authenticate = function(credentials, callback) {

		var headers = credentials ? {
			authorization : "Basic "
					+ btoa(credentials.username + ":" + credentials.password)
		} : {};
		$http.get('user',{headers:headers}).success(function(data) {
			if (data.principal.usuario) {
				$rootScope.authenticated = true;
				$location.path("/listServicios");
			} else {
				$rootScope.authenticated = false;
			}
		}).error(function(data) {
			//$rootScope.authenticated = false;
				$location.path("/login");
		});

	}
}]);

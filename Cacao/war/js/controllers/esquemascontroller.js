
app.service('EsquemasController',function(){/*Servicio e inyeccion de servicio de localStorage*/
	
})

app.controller("EsquemasController",function($scope,ToDoService){	
	
});
app.controller("SeguroController",['$scope','$log','$http',function($scope,$log,$http){	
	$scope.listpercecpciones=
	[
	{
		"clave": "001",
		"descripcion": "Sueldos, Salarios Rayas y Jornales",        
	},
	{
		"clave": "002",
		"descripcion": "Gratificación Anual",        
	},
	{
		"clave": "003",
		"descripcion": "Participación de los trabajadores en las Utilidades PTU",        
	},
	{
		"clave": "004",
		"descripcion": "Fondo de Ahorro",        
	},
	{
		"clave": "005",
		"descripcion": "Caja de Ahorro",        
	},
	]
	$scope.listdeducciones=[
	{
		"clave": "001",
		"descripcion": "Deducccion1",
	},
	{
		"clave": "002",
		"descripcion": "Deducccion2",
	},
	]
}]);
var app=angular.module("ToDoList",["LocalStorageModule",])/*Libreria LocalStorage Module*/
app.service('ToDoService',function(localStorageService){/*Servicio e inyeccion de servicio de localStorage*/

	this.getAll=function(){/*Muestra todos los Elementos*/
		return this.esquemas;
	};
	
})

app.controller("EsquemasController",function($scope,ToDoService){	
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
		"descripcion": "Deducccion1 fdsf fdf fds",
	},
	{
		"clave": "002",
		"descripcion": "Deducccion2",
	},
	]
	$scope.regimen={
		nombre:"",
		percepciones:[],
		deducciones:[]
	};
	$scope.todo =ToDoService.getAll();
	$scope.newEsq = {};
	$scope.newDed ={};
	$scope.listaEsq=[];
	$scope.addPer=function(){
		//ToDoService.add($scope.newEsq);
		var esq=$scope.inputPerc;
		$scope.regimen.percepciones.push(esq);
		$scope.inputPerc ={};		
	}
	$scope.addDed=function(){
		var ded=$scope.inputDeduc;
		$scope.regimen.deducciones.push(ded);
		$scope.inputDeduc ={};	
	}

	$scope.removeEmp = function(item){
		//$scope.todo = ToDoService.removeItem(item);
		$scope.listaEsq.removeItem(item);		
	}
	this.getAll=function(){/*Muestra todos los Elementos*/
		return this.esquemas;
	}
	$scope.clean =function(){
		ToDoService.clean();
	}
	this.removeItem=function(item){/*Elimina elemento por elemeto*/
		this.esquemas = this.esquemas.filter(function(activity){
			return activity !=item;
		});
		this.updaLocalStorage();	
		return this.getAll();
	}
	this.getAll=function(){/*Muestra todos los Elementos*/
		return this.esquemas;
	};
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
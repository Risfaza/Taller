var app=angular.module("ToDoList",["LocalStorageModule",])/*Libreria LocalStorage Module*/
app.service('ToDoService',function(localStorageService){/*Servicio e inyeccion de servicio de localStorage*/
	
	this.key="angular-todolist";/*String o clave representa lista*/
	if(localStorageService.get(this.key)){/*Si hay cosas en la lista las muestra*/
		this.empresas=localStorageService.get(this.key);
	}
	else{
		this.empresas=[];/*Si no inicializa como lista vacia*/
	}

	this.add = function(newEmp){/*Agrega elementos a arreglo Empresa*/
		this.empresas.push(newEmp);
		this.updaLocalStorage();
	};
	this.updaLocalStorage = function(){/*Actualiza Storage*/
		localStorageService.set(this.key, this.empresas);
	};
	this.clean=function(){/*Limpia o Elimina todos los elementos*/
		this.empresas=[];
		this.updaLocalStorage();	
		return this.getAll();
	};
	this.getAll=function(){/*Muestra todos los Elementos*/
		return this.empresas;
	};
	this.removeItem=function(item){/*Elimina elemento por elemeto*/
		this.empresas = this.empresas.filter(function(activity){
			return activity !=item;
		});
		this.updaLocalStorage();	
		return this.getAll();
	}
})
app.controller("ToDoController",function($scope,ToDoService){	
	$scope.todo =ToDoService.getAll();
	$scope.newEmp = {};
	$scope.addEmp=function(){
		ToDoService.add($scope.newEmp);
		$scope.newEmp ={};			
	}
	$scope.removeEmp = function(item){
		$scope.todo = ToDoService.removeItem(item);			
	}
	$scope.clean =function(){
		ToDoService.clean();
	}
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
}]);
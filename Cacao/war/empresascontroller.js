/*Libreria LocalStorage Module*/
//app.service('ToDoService',function(localStorageService){/*Servicio e inyeccion de servicio de localStorage*/
//	
//	this.key="angular-todolist";/*String o clave representa lista*/
//	if(localStorageService.get(this.key)){/*Si hay cosas en la lista las muestra*/
//		this.empresas=localStorageService.get(this.key);
//	}
//	else{
//		this.empresas=[];/*Si no inicializa como lista vacia*/
//	}
//
//	this.add = function(newEmp){/*Agrega elementos a arreglo Empresa*/
//		this.empresas.push(newEmp);
//		this.updaLocalStorage();
//	};
//	this.updaLocalStorage = function(){/*Actualiza Storage*/
//		localStorageService.set(this.key, this.empresas);
//	};
//	this.clean=function(){/*Limpia o Elimina todos los elementos*/
//		this.empresas=[];
//		this.updaLocalStorage();	
//		return this.getAll();
//	};
//	this.getAll=function(){/*Muestra todos los Elementos*/
//		return this.empresas;
//	};
//	this.removeItem=function(item){/*Elimina elemento por elemeto*/
//		this.empresas = this.empresas.filter(function(activity){
//			return activity !=item;
//		});
//		this.updaLocalStorage();	
//		return this.getAll();
//	}
//})

app.controller("EmpresasController",function($scope,EmpresasService){
	$scope.todo =EmpresasService.getAll();
	$scope.newEmp = {};
	$scope.addEmp=function(){
		console.log($scope.newEmp);
		EmpresasService.add($scope.newEmp);
	}
	$scope.removeEmp = function(item){
		$scope.todo = EmpresasService.removeItem(item);			
	}
	
});
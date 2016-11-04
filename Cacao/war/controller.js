var app=angular.module("ToDoList",[]);
app.service('EmpresasService', [ '$http', function($http) {
	this.add = function(newEmp) {/* Agrega elementos a arreglo Empresa */
		$http.post("/empresas/add", newEmp).then(function(response) {
			alert("Empresa Guardada");
			console.log(response)
		}, function(response) {
			console.log(response)
		});
	};
	
	this.find= function(rfc){
		$http.get("/empresas/find/"+rfc).then(function(response){
			console.log(response)
			return response.data;
		},function(response){
			console.log(response)
		});
			
		
	};
	this.getAll = function() {/* Muestra todos los Elementos */
		$http.get("/empresas/findAll").then(function(response){
			console.log(response);
			return response.data; 	
		},function(response){})
	};
	this.removeItem = function(item) {/* Elimina elemento por elemeto */
		this.empresas = this.empresas.filter(function(activity) {
			return activity != item;
		});
		this.updaLocalStorage();
		return this.getAll();
	}
} ]);

angular.module('Booking')
.factory('propertiesFactory', ['$http', function($http){

    var url='http://localhost:8080/BookingDAO/rest/properties/';
	var propertiesInterface = {
    	
    	getListaProperties : function(){
    		return $http.get(url)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	getProperty : function(propertyid){
    		var urlid = url + 'property/' + propertyid;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	getProperties : function(userid){
    		var urlid = url + userid;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	putProperty : function(property){
    		var urlid = url + property.id;
    		return $http.put(urlid, property)
    			.then(function(response){
					return response.status;
				});
    	},
    	
    	deleteProperty : function(id){
    		var urlid = url + id;
    		return $http.delete(urlid)
    			.then(function(response){
					return response.status;
				});
    	},
    	
    	postProperty : function(property){
    		var url2 = url + 'created';
    		return $http.post(url2, property)
    			.then(function(response){
					return response.status;
				});
    	},
    	
    	verDisponibles : function(){
    		var url2 = url + 'disponibles';
    		return $http.put(url2)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	verNoDisponibles : function(){
    		var url2 = url + 'nodisponibles';
    		return $http.put(url2)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	ordenar : function(listProperties){
    		var url2 = url + 'ordenar';
    		return $http.put(url2, listProperties)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	noOrdenar : function(listProperties){
    		var url2 = url + 'noOrdenar';
    		return $http.put(url2, listProperties)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	similaresCategoria : function(id){
    		var url2 = url + 'similaresCategoria/' + id;
    		return $http.get(url2)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	similaresLocalidad : function(id){
    		var url2 = url + 'similaresLocalidad/' + id;
    		return $http.get(url2)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    
    	similaresValoracion : function(id){
    		var url2 = url + 'similaresValoracion/' + id;
    		return $http.get(url2)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	 getAccommodations : function(propertyid){
            var url2 = url + 'accommodations/' + propertyid;
            return $http.get(url2)
                .then(function(response){
                    return response.data;
                });
        },
        
        getPropertiesByCity : function(city){
            var url2 = url + 'byCity/' + city;  // Suponiendo que el endpoint en el backend es 'byCity'
            return $http.get(url2)
                .then(function(response){
                    return response.data;
                });
        },
        
        getPropertyOwner: function(propertyid) {
            var urlid = url + 'owner/' + propertyid;
            return $http.get(urlid)
                .then(function(response){
                    return response.data;
                });
        },
        
        getAllOrderedByRating : function() {
            var url2 = url + 'orderedByRating';
            return $http.get(url2)
                .then(function(response){
                    return response.data;
                });
        }
        
    }
    
    
    return propertiesInterface;
}])
angular.module('Booking')
.factory('accommodationsFactory', ['$http', function($http){

    var url='http://localhost:8080/BookingDAO/rest/accommodations/';
    var accommodationsInterface = {
    	
    	getAccommodations : function(propertyid){
    		var urlid = url + propertyid;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	getAccommodation : function(accommodationid){
    		var urlid = url + 'accommodation/' + accommodationid;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	getAllAccommodations : function(){
    		return $http.get(url)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	putAccommodation : function(accommodation){
    		var urlid = url + accommodation.id;
    		return $http.put(urlid, accommodation)
    			.then(function(response){
					return response.status;
				});
    	},
    	
    	postAccommodation: function(accommodation, property) {
		    var urlid = url + property.id;
		    return $http.post(urlid, accommodation)
		        .then(function(response) {
		            return response.status;;
		        });
		},
    	
    	deleteAccommodation : function(accommodationid){
    		var urlid = url + accommodationid;
    		return $http.delete(urlid)
    			.then(function(response){
					return response.status;
				});
    	},
    	
    	sumarPrecio : function(accommodationid, precio){
			var urlid = url + 'sumarPrecio/' + accommodationid;
    		return $http.put(urlid, precio)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	restarPrecio : function(accommodationid, precio){
			var urlid = url + 'restarPrecio/' + accommodationid;
    		return $http.put(urlid, precio)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	anadirAlojamiento : function(accommodationid, accommodations){
			var urlid = url + 'anadir/' + accommodationid;
    		return $http.put(urlid, accommodations)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	quitarAlojamiento : function(accommodationid, accommodations){
			var urlid = url + 'quitar/' + accommodationid;
    		return $http.put(urlid, accommodations)
    			.then(function(response){
					return response.data;
				});
    	}
    };
    
    return accommodationsInterface;
}]);
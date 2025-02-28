angular.module('Booking')
.factory('usersFactory', ['$http', function($http){

    var url='http://localhost:8080/BookingDAO/rest/users';
	var usersInterface = {
    	
    	getUser: function() {
            console.log("Making GET request to:", url);
            return $http.get(url)
                .then(function(response) {
                    return response.data;
                });
        },
        
    	getAllUsers: function(){
    		return $http.get(url + 'allUsers')
    			.then(function(response){
					return response.data;
				});
    	},
    	getUserById: function(id) {
            var urlid = url + '/' + id;
            return $http.get(urlid)
                .then(function(response) {
                    return response.data;
                });
        },
        getUserFavorites: function(userId) {
                return $http.get(url + '/' + userId + '/favorites')
                    .then(function(response) {
                        return response.data;
                    });
        },
    	putUser: function(user) {
		    var urlid = url + '/' + user.id;  // Aseguramos el separador "/"
		    console.log("Making PUT request to:", urlid);
		    return $http.put(urlid, user)
		        .then(function(response) {
		            console.log("PUT request successful");
		            return response.status;
		        }, function(error) {
		            console.error("PUT request failed:", error);
		        });
		},	
    	userCorrecto : function(id){
			
			console.log("LOG USER CORRECTO");
    		var urlid = url + '/'+ 'userCorrecto/' + id;
    		return $http.put(urlid)
    			.then(function(response){
					return response.data;
				});
    	},

		userCorrectoAccomodation : function(id){
			
			console.log("LOG USER CORRECTO");
    		var urlid = url + '/'+ 'userCorrectoAccomodation/' + id;
    		return $http.put(urlid)
    			.then(function(response){
					return response.data;userCorrectoAccomodation
				});
    	},
    	postUser: function(user){
    		var url2 = url + 'created';
    		return $http.post(url2, user)
    			.then(function(response){
					return response.status;
				});
    	},
    	postUser2Session: function(user){
    		var url2 = url + 'login';
    		return $http.post(url2, user)
    			.then(function(response){
					return response.data;
				});
    	},
    	quitUser2Session: function(user){
    		return $http.post(url, user)
    			.then(function(response){
					return response.status;
				});
    	},
    	deleteUser: function(id){
		    var urlid = url + '/' + id; // Agregar un separador "/"
		    return $http.delete(urlid)
		        .then(function(response){
		            return response.status;
		        }, function(error) {
		            console.error("Error in deleteUser:", error); // Agregar log de error
		        });
		},
    	uExist: function(user){
    		var url2 = url + 'uExit';
    		return $http.put(url2, user)
    			.then(function(response){
					return response.data;
				});
    	},
    };
    
    return usersInterface;
}]);
    
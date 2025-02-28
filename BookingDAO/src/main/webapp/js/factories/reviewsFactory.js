angular.module('Booking')
.factory('reviewsFactory', ['$http', function($http){

	var url = 'http://localhost:8080/BookingDAO/rest/reviews/';
	var reviewsInterface = {    	
    	
    	getReviews : function(id){
    		var urlid = url + id;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},

    	getReview : function(propertyId, userId){
    		var urlid = url + 'getReview/' + propertyId + '/' + userId;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},

    	addReview : function(propertyId, review){
    		var urlid = url + propertyId;
    		return $http.post(urlid, review)
    			.then(function(response){
					return response.status;
				});
    	},

    	updateReview : function(propertyId, review){
    		var urlid = url + propertyId;
    		return $http.put(urlid, review)
    			.then(function(response){
					return response.status;
				});
    	},

    	verificarExistenciaReview : function(propertyId, userId){
    		var urlid = url + 'yaExiste/' + propertyId + '/' + userId;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	}
    }
    
    return reviewsInterface;
}]);
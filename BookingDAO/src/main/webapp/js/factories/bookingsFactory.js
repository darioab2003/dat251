angular.module('Booking')
.factory('bookingsFactory', ['$http', function($http){

    var url='http://localhost:8080/BookingDAO/rest/bookings/';
	var bookingsInterface = {    	
    	
    	getBookings: function(userid){
    		var urlid = url + userid;
    		return $http.get(urlid)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	addBooking: function(booking) {
		    return $http.post(url, booking)
		        .then(function(response) {
		            return response.data;
		        });
		}
    };
    
    return bookingsInterface;
}]);

angular.module('Booking')
.factory('bookingsAccommodationsFactory', ['$http', function($http){

    var url = 'http://localhost:8080/BookingDAO/rest/bookingsAccommodations/';

    var bookingsAccommodationsInterface = {    	
    	
    	getAllBookingAccommodations: function(){
    		return $http.get(url)
    			.then(function(response){
					return response.data;
				});
    	},
    	
    	addBookingAccommodation: function(bookingAccommodation) {
            return $http.post(url, bookingAccommodation)
                .then(function(response) {
                    return response.data;
                });
        },
        
        getBookingAccommodations: function(bookingId) {
            var urlid = url + 'byBooking/' + bookingId; // Asumiendo que este es el endpoint correcto
            return $http.get(urlid)
                .then(function(response){
                    return response.data;
                });
        },
    };
    
    return bookingsAccommodationsInterface;
}]);

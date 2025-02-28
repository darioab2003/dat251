angular.module('Booking')
.factory('favoritosFactory', ['$http', function($http){

    var url = 'http://localhost:8080/BookingDAO/rest/favoritos'; // URL de la API

    var favoritosInterface = {
        
        addFavorito: function(favorito) {
            console.log("Making POST request to:", url);
            return $http.post(url + '/add', favorito)
                .then(function(response) {
                    return response.data;
                });
        },
        
        deleteFavorito: function(idu, idp) {
            console.log("Making DELETE request to:", url);
            return $http.delete(url + '/delete/' + idu + '/' + idp)
                .then(function(response) {
                    return response.data;
                });
        },
       
        isFavorite: function(idu, idp) {
            console.log("Making GET request to:", url);
            return $http.get(url + '/isFavorite/' + idu + '/' + idp)
                .then(function(response) {
                    return response.data;
                });
        },
        
        getFavoritos: function(userid) {
            console.log("Making GET request to:", url);
            return $http.get(url + '/' + userid)
                .then(function(response) {
                    return response.data;
                });
        }
    };
    
    return favoritosInterface;
}]);

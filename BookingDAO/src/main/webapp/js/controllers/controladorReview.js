angular.module('Booking')
.controller('controladorReview', ['reviewsFactory', 'usersFactory', '$location', '$routeParams', function(reviewsFactory, usersFactory, $location, $routeParams) {
    
    var ViewModel = this;
    ViewModel.review = {};
    ViewModel.user = {};

    ViewModel.functions = {
        
        // Función para obtener los datos del usuario
        readUser : function() {
            usersFactory.getUser()
                .then(function(response){
                    ViewModel.user = response;
                    // Llamar a verificarExistenciaReview después de obtener el usuario
                    ViewModel.functions.verificarExistenciaReview(ViewModel.user.id); // Pasar el ID del usuario
                })
                .catch(function(error){
                    console.log("Error leyendo datos del usuario", error);
                });
        },
        
        verificarExistenciaReview : function(userID) {
            console.log("$routeParams.ID:", $routeParams.ID);
            console.log("userID:", userID);
            if ($routeParams.ID && userID) {
                reviewsFactory.verificarExistenciaReview($routeParams.ID, userID)
                    .then(function(response){
                        if (response) {
                            // Si hay una review existente, redirigir a EditarReview
                            $location.path("/EditarReview/" + $routeParams.ID);
                        } else {
                            // Si no hay review existente, permitir crear una nueva
                            ViewModel.review.propertyID = $routeParams.ID;
                        }
                    })
                    .catch(function(error){
                        console.error("Error verificando existencia de la review", error);
                    });
            } else {
                console.error("propertyID o userID están indefinidos");
            }
        },

         crearReview: function() {
            if (ViewModel.review.valoracion < 1 || ViewModel.review.valoracion > 5) {
                console.error("La valoración debe estar entre 1 y 5");
                return;
            }

            reviewsFactory.addReview(ViewModel.review.propertyID, ViewModel.review)
                .then(function(response) {
                    $location.path("/PaginaPrincipal");
                })
                .catch(function(error) {
                    console.error("Error creando la review", error);
                });
        },

        editarReview : function() {
            reviewsFactory.updateReview(ViewModel.review.propertyID, ViewModel.review)
                .then(function(response){
                    $location.path("/PaginaPrincipal");
                })
                .catch(function(error){
                    console.error("Error editando la review", error);
                });
        },

        cancelar : function() {
            $location.path("/ListaProperties");
        }
        
    };

    // Llamar a readUser para iniciar el proceso
    ViewModel.functions.readUser();

}]);

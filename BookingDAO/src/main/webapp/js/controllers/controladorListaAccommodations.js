angular.module('Booking')
.controller('controladorListaAccommodations', ['$scope', '$routeParams', '$location', 'accommodationsFactory', 'reviewsFactory', 'usersFactory', 'propertiesFactory', '$window', function($scope, $routeParams, $location, accommodationsFactory, reviewsFactory, usersFactory, propertiesFactory, $window) {
        
        $scope.accommodations = [];
        $scope.reviews = [];
        $scope.users = [];
        $scope.propertyId = $routeParams.ID;
        $scope.isLoggedIn = false; // Variable para controlar el estado de sesión

        // Función para cargar la lista de accommodations
        function cargarListaAccommodations() {
	        accommodationsFactory.getAccommodations($scope.propertyId)
	            .then(function(data) {
	                $scope.accommodations = data;
	                // Inicializar numSelected a 1 para cada alojamiento
	                $scope.accommodations.forEach(function(accommodation) {
	                    accommodation.numSelected = 1;
	                });
	            })
	            .catch(function(error) {
	                console.error('Error al cargar la lista de accommodations', error);
	            });
	    }
        // Función para cargar la lista de reviews
        
        function cargarListaReviews() {
            reviewsFactory.getReviews($scope.propertyId)
                .then(function(data) {
                    $scope.reviews = data;
                    // Cargar detalles de los usuarios para cada review
                    $scope.reviews.forEach(function(review) {
						console.log('ID del usuario en la review:', review.idu);
                        usersFactory.getUserById(review.idu)
                            .then(function(userData) {
                                $scope.users[review.idu] = userData;
                            })
                            .catch(function(error) {
                                console.error('Error al cargar la información del usuario', error);
                            });
                    });
                })
                .catch(function(error) {
                    console.error('Error al cargar la lista de reviews', error);
                });
        }
        
        // Función para verificar si el usuario está logueado
        function checkUserLoggedIn() {
            usersFactory.getUser()
                .then(function(user) {
                    // Verificar si el usuario está logueado
                    if (user.email !== undefined) {
                        $scope.isLoggedIn = true;
                    } else {
                        $scope.isLoggedIn = false;
                    }
                })
                .catch(function(error) {
                    console.error('Error al verificar el estado de sesión del usuario', error);
                });
        }
		
		$scope.redirectDejarValoracion = function() {
	        usersFactory.getUser().then(function(user) {
	            propertiesFactory.getProperty($scope.propertyId).then(function(property) {
	                if (property.idu === user.id) {
	                    // Usuario es propietario, redirigir a dejarValoracionPropietario
	                    $location.path('/dejarValoracionPropietario/' + $scope.propertyId);
	                } else {
	                    // Usuario no es propietario, redirigir a dejarValoracion
	                    $location.path('/dejarValoracion/' + $scope.propertyId);
	                }
	            });
	        });
	    };
	    
	   $scope.redirectPagoReserva = function(accommodation) {
	        // Guardar el alojamiento seleccionado en sessionStorage
	        $window.sessionStorage.setItem('selectedAccommodation', JSON.stringify(accommodation));
	        $location.path('/PagoReserva');
	    };
	    
        // Inicialización
        cargarListaAccommodations();
        cargarListaReviews();
        checkUserLoggedIn(); // Verificar estado de sesión al cargar la página
    }]);
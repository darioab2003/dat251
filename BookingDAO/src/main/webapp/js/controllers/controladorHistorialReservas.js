angular.module('Booking')
.controller('controladorHistorialReservas', ['$scope', 'bookingsFactory', 'usersFactory', 'bookingsAccommodationsFactory', 'accommodationsFactory', 'propertiesFactory', function($scope, bookingsFactory, usersFactory, bookingsAccommodationsFactory, accommodationsFactory, propertiesFactory) {


    // Inicializamos la lista de reservas vacía
    $scope.reservas = [];

    function cargarReservasUsuario(userId) {
        bookingsFactory.getBookings(userId)
            .then(function(reservas) {
                $scope.reservas = reservas;
                console.log('Reservas cargadas:', $scope.reservas);
                
                // Obtener detalles de propiedades para cada reserva
                $scope.reservas.forEach(function(reserva) {
                    bookingsAccommodationsFactory.getBookingAccommodations(reserva.id)
                        .then(function(bookingAccommodations) {
                            bookingAccommodations.forEach(function(ba) {
                                accommodationsFactory.getAccommodation(ba.idacc)
                                    .then(function(accommodation) {
                                        propertiesFactory.getProperty(accommodation.idp)
                                            .then(function(property) {
                                                reserva.property = property; // Asignar propiedad a la reserva
                                            })
                                            .catch(function(error) {
                                                console.error('Error al obtener la propiedad:', error);
                                            });
                                    })
                                    .catch(function(error) {
                                        console.error('Error al obtener el alojamiento:', error);
                                    });
                            });
                        })
                        .catch(function(error) {
                            console.error('Error al obtener booking accommodations:', error);
                        });
                });
            })
            .catch(function(error) {
                console.error('Error al cargar las reservas:', error);
            });
    }

    // Función para obtener el ID del usuario actual
    function obtenerIdUsuarioActual() {
        usersFactory.getUser()
            .then(function(user) {
                console.log('ID del usuario obtenido:', user.id);
                cargarReservasUsuario(user.id); // Suponiendo que user.id es el ID del usuario
            })
            .catch(function(error) {
                console.error('Error al obtener el usuario actual:', error);
            });
    }

    // Llamamos a la función para obtener el ID del usuario y luego cargar sus reservas
    obtenerIdUsuarioActual();

}]);

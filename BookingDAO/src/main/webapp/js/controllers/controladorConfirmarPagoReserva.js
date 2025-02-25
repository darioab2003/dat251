angular.module('Booking')
.controller('controladorConfirmarPagoReserva', ['$scope', 'bookingsFactory', 'bookingsAccommodationsFactory', '$location', '$window', function($scope, bookingsFactory, bookingsAccommodationsFactory, $location, $window) {

    var selectedAccommodation = $window.sessionStorage.getItem('selectedAccommodation');
    if (!selectedAccommodation) {
        console.error('No accommodation selected');
        return;
    }

    $scope.accommodation = JSON.parse(selectedAccommodation);
    $scope.totalPrice = $scope.accommodation.price * $scope.accommodation.numSelected;

    console.log('Detalles de reserva:', $scope.accommodation, $scope.totalPrice);

    $scope.confirmReservation = function() {
        var booking = {
            idu: 1,  // Suponiendo que se obtiene el ID del usuario actual correctamente
            totalPrice: $scope.totalPrice
        };

        console.log('Añadiendo reserva:', booking);  

        // Añadir la reserva (booking)
        bookingsFactory.addBooking(booking)
            .then(function(response) {
                console.log('Reserva añadida:', response);

                // Después de añadir la reserva, añadir la relación bookingAccommodation
                var bookingAccommodation = {
                    idb: response.id,  // ID de la reserva recién creada
                    idacc: $scope.accommodation.id,  // ID del alojamiento seleccionado
                    numAccommodations: $scope.accommodation.numSelected
                };

                // Añadir la bookingAccommodation
                return bookingsAccommodationsFactory.addBookingAccommodation(bookingAccommodation);
            })
            .then(function(response) {
                console.log('BookingAccommodation añadida:', response);

                // Redirigir al usuario a la siguiente página después de la reserva
                $location.path('/TerminarPagoReserva');
            })
            .catch(function(error) {
                console.error('Error al añadir la reserva:', error);
            });
    };

    $scope.terminarPago = function() {
        $location.path('/TerminarPagoReserva');
    };
}]);

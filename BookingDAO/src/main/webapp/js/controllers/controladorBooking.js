angular.module('Booking')
.controller('controladorBooking', ['$scope', 'usersFactory', 'propertiesFactory', '$location', '$window', function($scope, usersFactory, propertiesFactory, $location, $window) {
    
    // Recuperar el alojamiento seleccionado desde sessionStorage
    var selectedAccommodation = $window.sessionStorage.getItem('selectedAccommodation');
    if (!selectedAccommodation) {
        console.error('No accommodation selected');
        return;
    }

    $scope.accommodation = JSON.parse(selectedAccommodation);
    $scope.totalPrice = $scope.accommodation.price * $scope.accommodation.numSelected;
	$scope.reserva = {
        property: {},
        user: {}
    };
	
    // Cargar datos del usuario
    usersFactory.getUser().then(function(response) {
        $scope.reserva.user = response;
    }, function(response) {
        console.log("Error al leer los datos del usuario");
    });

    // Cargar datos de la propiedad
    propertiesFactory.getProperty($scope.accommodation.idp).then(function(response) {
        $scope.reserva.property = response;
    }, function(response) {
        console.log("Error al leer los datos de la propiedad");
    });

    $scope.confirmReservation = function() {
       $location.path('/ConfirmarPagoReserva');
       alert('Reserva confirmada!');
    };
    
    
    $scope.confirmarPago = function() {
        $location.path('/ConfirmarPagoReserva');
    };
}]);
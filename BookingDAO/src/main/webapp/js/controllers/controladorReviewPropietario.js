angular.module('Booking')
.controller('controladorReviewPropietario', ['$location', function($location) {
    
    var ViewModel = this;

    ViewModel.functions = {
        cancelar: function() {
            $location.path("/ListaProperties");
        }
    };

}]);

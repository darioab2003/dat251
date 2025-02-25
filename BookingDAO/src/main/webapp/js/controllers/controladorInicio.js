angular.module('Booking')
.controller('controladorInicio', ['usersFactory', function(usersFactory){
    var ViewModel = this;
    ViewModel.user = {};

    ViewModel.functions = {
        readUser: function() {
            console.log("Sending GET request to fetch user data");
            usersFactory.getUser()
                .then(function(response) {
                    ViewModel.user = response;
                    console.log("User data loaded successfully", response);
                }, function(response) {
                    console.log("Error reading user data", response);
                });
        }
    };
    ViewModel.functions.readUser();
}]);
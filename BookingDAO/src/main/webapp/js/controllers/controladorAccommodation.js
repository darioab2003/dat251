angular.module('Booking')
.controller('controladorAccommodation', ['usersFactory', 'propertiesFactory', 'accommodationsFactory', '$location', '$routeParams', function(usersFactory, propertiesFactory, accommodationsFactory, $location, $routeParams){

    var ViewModel = this;
    ViewModel.user={};
    ViewModel.property={};
    ViewModel.accommodation={};

    ViewModel.functions = {
        
        readUser : function() {
            usersFactory.getUser()
                .then(function(response){
                   ViewModel.user = response;
                }, function(response){
                    console.log("Error reading user data");
                })
        },
        
        readProperty : function() {
            propertiesFactory.getProperty($routeParams.ID)
                .then(function(response){
                   ViewModel.property = response;
                }, function(response){
                    console.log("Error reading property data");
                })
        },
        
		readAccommodation : function() {
            accommodationsFactory.getAccommodation($routeParams.ID)
                .then(function(response){
                   ViewModel.accommodation = response;
                }, function(response){
                    console.log("Error reading user data");
                })
        },
        
        userCorrectoAccomodation : function() {
            usersFactory.userCorrectoAccomodation($routeParams.ID)
                .then(function(response){
                       if(response === false){
						   $location.path("/PaginaPrincipal");
					   } 
                }, function(response){
                    console.log("Error checking if correct user");
                })
        },
		
       crearAccommodation : function() {
			console.log("Accommodation to be created:", ViewModel.accommodation);
            accommodationsFactory.postAccommodation(ViewModel.accommodation, ViewModel.property)
                 .then(function(response) {
                   $location.path("/PaginaPrincipal"); 
                })
                .catch(function(error) {
                    console.error("Error creating accommodation", error);
                });
        },
        
        borrarAccommodation : function() {
            accommodationsFactory.deleteAccommodation($routeParams.ID)
                .then(function(response){
                   $location.path("/PaginaPrincipal"); 
                }, function(response){
                    console.log("Error reading user data");
                })
        },
        
        cancelar: function(){
			$location.path("/PaginaPrincipal"); 
		},
		
		
         editarAccommodation : function() {
	        accommodationsFactory.putAccommodation(ViewModel.accommodation)
	            .then(function(response){
	               $location.path("/PaginaPrincipal");
	            }, function(response){
	                console.log("Error reading user data");
	            })
        },
    
     init: function() {
            ViewModel.functions.readUser();
            if ($location.path().includes("NuevaAccommodation")) {
                ViewModel.functions.readProperty();
            } else if ($location.path().includes("EditarAccommodation")) {
                ViewModel.functions.readAccommodation();
            }
        }
    };

    ViewModel.functions.init();
    ViewModel.functions.userCorrectoAccomodation();
}]);
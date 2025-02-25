angular.module('Booking')
.controller('controladorProperty', ['propertiesFactory', 'accommodationsFactory', 'usersFactory', 'reviewsFactory', '$location', '$routeParams', function(propertiesFactory, accommodationsFactory, usersFactory, reviewsFactory, $location, $routeParams){

    var ViewModel = this;
    ViewModel.reviewYaExiste = {};
    ViewModel.property = {};
    ViewModel.reviews = [];
    ViewModel.accommodations = [];
    ViewModel.users = [];
    ViewModel.user = {};

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
                    console.log("Error reading user data");
                })
        },
        
        readAccommodation : function() {
			accommodationsFactory.getAccommodations($routeParams.ID)
				.then(function(response){
					ViewModel.accommodations = response;
				}, function(response) {
					console.log("Error reading accommodations data");
				})
		},

		verProperty: function() {
            propertiesFactory.getProperty($routeParams.ID)
                .then(function(response){
                   ViewModel.property = response;
                })
                .catch(function(error){
                    console.error("Error reading property data", error);
                });
        },
        
        verUsers: function() {
            usersFactory.getAllUsers()
                .then(function(response){
                   ViewModel.users = response;
                })
                .catch(function(error){
                    console.error("Error reading users data", error);
                });
        },

        verReviews: function() {
            reviewsFactory.getReviews($routeParams.ID)
                .then(function(response){
                   ViewModel.reviews = response;
                })
                .catch(function(error){
                    console.error("Error reading reviews data", error);
                });
        },

        verAccommodations: function() {
            propertiesFactory.getAccommodations($routeParams.ID)
                .then(function(response){
                   ViewModel.accommodations = response;
                })
                .catch(function(error){
                    console.error("Error reading accommodations data", error);
                });
        },

        editarProperty : function() {
            console.log("Editing property with data:", ViewModel.property);
            propertiesFactory.putProperty(ViewModel.property)
                .then(function(response){
					ViewModel.property = response;
                    $location.path("/PaginaPrincipal"); 
                })
        .catch(function(error){
            console.log("Error editing property data", error);
        });
},
    
		userCorrecto : function() {
            usersFactory.userCorrecto($routeParams.ID)
                .then(function(response){
                       if(response === false){
						   $location.path("/PaginaPrincipal");
					   } 
                }, function(response){
                    console.log("Error checking if correct user");
                })
        },
		
		
        borrarProperty: function() {
            propertiesFactory.deleteProperty($routeParams.ID)
                .then(function(response){
                   $location.path("/PaginaPrincipal"); 
                })
                .catch(function(error){
                    console.error("Error deleting property", error);
                });
        },

        crearProperty: function() {
            propertiesFactory.postProperty(ViewModel.property)
                .then(function(response) {
                    $location.path("/PaginaPrincipal");
                })
                .catch(function(error) {
                    console.error("Error creating property", error);
                });
        },

        propertySwitcher: function() {
            if ($location.path() == '/EdicionProperty/' + ViewModel.property.id){
                ViewModel.functions.editarProperty(ViewModel.property);
            } else if ($location.path() == '/AnadirProperty'){
                ViewModel.functions.crearProperty(ViewModel.property);
            }
        },

        reviewYaExiste: function() { 
            reviewsFactory.reviewYaExiste($routeParams.ID)
                .then(function(response){
                    ViewModel.reviewYaExiste = response;
                })
                .catch(function(error){
                    console.error("Error checking existing reviews", error);
                })
        },
        cancelar: function() { 
            $location.path("/PaginaPrincipal");
        }
    };

    ViewModel.functions.readUser();

    if ($location.path().includes("NuevaProperty")) {
        // No hacer nada aquí, ya que estamos creando una nueva propiedad
    } else if ($location.path().includes("EditarAlojamiento")) {
        ViewModel.functions.readProperty();
        ViewModel.functions.readAccommodation();
    }
    ViewModel.functions.userCorrecto();
}]);
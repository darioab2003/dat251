angular.module('Booking')
.controller('controladorUsuario', ['usersFactory', 'propertiesFactory', 'favoritosFactory', '$location', '$routeParams', function(usersFactory, propertiesFactory, favoritosFactory, $location, $routeParams){

    var ViewModel = this;

    ViewModel.user={};
    ViewModel.userEnc={};
    ViewModel.userExist=false;
	ViewModel.properties=[];
	ViewModel.favoritos = [];

    ViewModel.functions = {
        readUser: function() { 
            console.log("Sending GET request to fetch user data");
            usersFactory.getUser()
                .then(function(response) {
                    ViewModel.user = response;
                    console.log("User data loaded successfully", response);
                }, function(response) {
                    console.error("Error reading user data", response);
                });
        },
        
         readProperties : function() {
			propertiesFactory.getProperties($routeParams.ID)
				.then(function(response){
					ViewModel.properties = response;
					console.log("ViewModel.properties size: " + ViewModel.properties.length);
				}, function(response) {
					console.log("Error reading properties data");
				})
		},
		
		readFavoritos: function() {
            favoritosFactory.getFavoritos($routeParams.ID)
                .then(function(response) {
                    ViewModel.favoritos = response;
                    console.log("ViewModel.favoritos size: " + ViewModel.favoritos.length);
                    
                    // Load details for each favorite property
                    ViewModel.favoritos.forEach(function(favorito) {
                        propertiesFactory.getProperty(favorito.idp)
                            .then(function(propertyResponse) {
                                favorito.propertyDetails = propertyResponse;
                            }, function(propertyResponse) {
                                console.error("Error reading property data", propertyResponse);
                            });
                    });
                }, function(response) {
                    console.log("Error reading favoritos data");
                });
        },
        
        editarCuenta: function() {
            console.log("Sending PUT request to update user data", ViewModel.user);
            usersFactory.putUser(ViewModel.user)
                .then(function(response) {
                    console.log("User data updated successfully");
                    ViewModel.functions.readUser();
                    $location.path("/PaginaPrincipal"); 
                }, function(response) {
                    console.error("Error updating user data", response);
                });
        },
       
        cerrarSesion : function() {
		    usersFactory.quitUser2Session()
		        .then(function(response){
		            console.log("Sesion cerrada correctamente");
		            $location.path("/PaginaPrincipal"); 
		        }, function(response){
		            console.log("Error al cerrar sesión");
		        });
		},
        
        borrarCuenta : function() {
            usersFactory.deleteUser(ViewModel.user.id)
                .then(function(response){
                       ViewModel.functions.readUser();
                       $location.path("/PaginaPrincipal"); 
                }, function(response){
                    console.log("Error reading user data");
                })
        },
        
        crearCuenta : function() {
            usersFactory.uExist(ViewModel.user)
                .then(function(response){
                   	ViewModel.userExist = response;
                   	if(ViewModel.userExist){
						$location.path("/CrearCuenta");
		            } else {
						usersFactory.postUser(ViewModel.user)
			                .then(function(response){
									$location.path("/ListadoRestaurantes");
			                }, function(response){
			                    console.log("Error reading user data");
			                })
					}
                }, function(response){
                    console.log("Error reading user data");
                })
        },
       
       userCorrecto : function() {
            usersFactory.userCorrecto($routeParams.ID)
                .then(function(response){
                       if(response === false){
						   $location.path("/ListadoRestaurantes");
					   } 
                }, function(response){
                    console.log("Error reading user data");
                })
        }
    }
    
   ViewModel.functions.readUser();
   ViewModel.functions.readProperties();
   ViewModel.functions.readFavoritos();
    
}])
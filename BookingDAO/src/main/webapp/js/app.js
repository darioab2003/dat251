angular.module('Booking', ['ngRoute'])
.config(function($routeProvider) {
    $routeProvider
        .when("/", {
            controller: "controladorInicio",
            controllerAs: "inicio",
            templateUrl: "paginaPrincipal.html",
            resolve: {
                delay: function($q, $timeout) {
                    var delay = $q.defer();
                    $timeout(delay.resolve, 500);
                    return delay.promise;
                }
            }
        })
        .when("/PaginaPrincipal", {
            controller: "controladorInicio",
            controllerAs: "inicio",
            templateUrl: "paginaPrincipal.html",
        })
        .when("/Perfil/:ID", {
            controller: "controladorUsuario",
            controllerAs: "perfil",
            templateUrl: "perfilUsuario.html",
        })
        .when("/Registro", {
            controller: "controladorUsuario",
            controllerAs: "registro",
            templateUrl: "registroUsuario.html",
        })
        .when("/NuevaProperty/:ID", {
            controller: "controladorProperty",
            controllerAs: "alojamiento",
            templateUrl: "nuevoAlojamiento.html",
        })
        .when("/EliminarProperty/:ID", {
            controller: "controladorProperty",
            controllerAs: "alojamiento",
            templateUrl: "borrarAlojamiento.html",
        })
        .when("/EditarAlojamiento/:ID", {
    		controller: "controladorProperty",
    		controllerAs: "alojamiento",
    		templateUrl: "editarAlojamiento.html",
    	})
    	.when("/EliminarAccommodation/:ID", {
            controller: "controladorAccommodation",
            controllerAs: "habitacion",
            templateUrl: "borrarHabitacion.html",
        })
        .when("/EditarAccommodation/:ID", {
    		controller: "controladorAccommodation",
    		controllerAs: "habitacion",
    		templateUrl: "editarHabitacion.html",
    	})
    	.when("/NuevaAccommodation/:ID", {
            controller: "controladorAccommodation",
            controllerAs: "habitacion",
            templateUrl: "crearHabitacion.html",
        })
        .when("/ListaProperties", {
            controller: "controladorListaProperties",
            controllerAs: "listProperties",
            templateUrl: "index3.html",
        })
        .when("/VerProperty/:ID", {
                controller: "controladorListaAccommodations",
                controllerAs: "listAccommodations",
                templateUrl: "index4.html"
        })
        .when("/dejarValoracionPropietario/:ID", {
		    controller: "controladorReviewPropietario",
		    controllerAs: "reviewPropietario",
		    templateUrl: "dejarValoracionPropietario.html",
		})
		.when("/dejarValoracion/:ID", {
		    controller: "controladorReview",
		    controllerAs: "review",
		    templateUrl: "dejarValoracion.html",
		})
        .when("/NuevaReview/:ID", {
		    controller: "controladorReview",
		    controllerAs: "review",
		    templateUrl: "dejarValoracion.html",
		})
		.when("/EditarReview/:ID", {
		    controller: "controladorReview",
		    controllerAs: "review",
		    templateUrl: "dejarValoracionExiste.html",
		})
		.when("/PagoReserva", {
		    controller: "controladorBooking",
		    controllerAs: "reserva",
		    templateUrl: "pagoReserva.html",
		})
		.when("/ConfirmarPagoReserva", {
		    controller: "controladorConfirmarPagoReserva",
		    controllerAs: "reserva",
		    templateUrl: "confirmarPagoReserva.html",
		})
		.when("/TerminarPagoReserva", {
		    controller: "controladorHistorialReservas",
		    controllerAs: "reserva",
		    templateUrl: "historialReservas.html",
		})
        .otherwise({
            redirectTo: "/"
        });
});
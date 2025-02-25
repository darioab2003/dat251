angular.module('Booking')
    .controller('controladorListaProperties', ['$scope', '$location', 'propertiesFactory', 'favoritosFactory', 'usersFactory', function($scope, $location, propertiesFactory, favoritosFactory, usersFactory) {

        $scope.properties = [];
        $scope.filtroSeleccionado = 'todos';
        $scope.destino = '';
        $scope.userId = null;

        function cargarListaProperties() {
            propertiesFactory.getListaProperties()
                .then(function(data) {
                    $scope.properties = data;

                    if ($scope.userId) {
                        usersFactory.getUserFavorites($scope.userId)
                            .then(function(favorites) {
                                $scope.properties.forEach(function(property) {
                                    if (favorites.includes(property.id)) {
                                        property.isFavorite = true;
                                    }
                                });
                            })
                            .catch(function(error) {
                                console.error('Error al obtener favoritos del usuario', error);
                            });
                    }
                })
                .catch(function(error) {
                    console.error('Error al cargar la lista de propiedades', error);
                });
        }

        $scope.buscar = function() {
            if ($scope.destino) {
                $location.search('destino', $scope.destino);
                filtrarPorDestino($scope.destino);
            } else {
                cargarListaProperties();
            }
        };

        function filtrarPorDestino(destino) {
            if (destino) {
                propertiesFactory.getPropertiesByCity(destino)
                    .then(function(data) {
                        $scope.properties = data;
                        if ($scope.userId) {
                            usersFactory.getUserFavorites($scope.userId)
                                .then(function(favorites) {
                                    $scope.properties.forEach(function(property) {
                                        if (favorites.includes(property.id)) {
                                            property.isFavorite = true;
                                        }
                                    });
                                })
                                .catch(function(error) {
                                    console.error('Error al obtener favoritos del usuario', error);
                                });
                        }
                    })
                    .catch(function(error) {
                        console.error('Error al filtrar por destino', error);
                        cargarListaProperties();
                    });
            } else {
                cargarListaProperties();
            }
        }

        var params = $location.search();
        if (params.destino) {
            $scope.destino = params.destino;
            $scope.buscar();
        } else {
            cargarListaProperties();
        }

        $scope.filtrarPorDisponibilidad = function(opcion) {
            switch (opcion) {
                case 'todos':
                    cargarListaProperties();
                    break;
                case 'disponible':
                    propertiesFactory.verDisponibles()
                        .then(function(data) {
                            $scope.properties = data;
                        })
                        .catch(function(error) {
                            console.error('Error al filtrar por disponibles', error);
                        });
                    break;
                case 'no_disponible':
                    propertiesFactory.verNoDisponibles()
                        .then(function(data) {
                            $scope.properties = data;
                        })
                        .catch(function(error) {
                            console.error('Error al filtrar por no disponibles', error);
                        });
                    break;
                case 'valoracion':
                    propertiesFactory.getAllOrderedByRating()
                        .then(function(data) {
                            $scope.properties = data;
                        })
                        .catch(function(error) {
                            console.error('Error al filtrar por valoración', error);
                        });
                    break;
                default:
                    console.error('Opción de filtro no válida');
            }
        };

        $scope.verDisponibilidad = function(id) {
            $location.path('/VerProperty/' + id);
        };

        $scope.toggleFavorite = function(property) {
            if (!$scope.userId) {
                console.error('Usuario no autenticado.');
                return;
            }

            if (property.isFavorite) {
                favoritosFactory.deleteFavorito($scope.userId, property.id)
                    .then(function(response) {
                        property.isFavorite = false;
                    })
                    .catch(function(error) {
                        console.error('Error al eliminar de favoritos', error);
                    });
            } else {
                var favorito = {
                    idu: $scope.userId,
                    idp: property.id
                };

                favoritosFactory.addFavorito(favorito)
                    .then(function(response) {
                        property.isFavorite = true;
                    })
                    .catch(function(error) {
                        console.error('Error al agregar a favoritos', error);
                    });
            }
        };

        usersFactory.getUser()
            .then(function(user) {
                $scope.userId = user.id;
                cargarListaProperties();
            })
            .catch(function(error) {
                console.error('Error al obtener el usuario autenticado', error);
                cargarListaProperties();
            });
    }]);

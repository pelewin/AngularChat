angular.module('customerApp', ['ngRoute'])

    .config(function ($routeProvider) {

        $routeProvider
            .when('/', {
                controller: 'customerList',
                templateUrl: 'list.html'
            })
            .when('/edit/:customerId', {
                controller: 'editCustomer',
                templateUrl: 'detail.html'
            })
            .when('/new', {
                controller: 'newCustomer',
                templateUrl: 'detail.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    })

    .controller('customerList',
    function Customers($scope, $http) {
        $http.get('/customers').
            success(function (data) {
                $scope.customersList = data;
            }
        );
    })

    .controller('editCustomer',
    function ($location, $routeParams, $scope, $http) {

        $http.get('/customers/' + $routeParams.customerId).
            success(function (data) {
                $scope.customer = data;
            }
        );

        $scope.save = function () {
            $http.put('/customers/' + $routeParams.customerId, $scope.customer).
                success(function () {
                    $location.path('/');
                }
            );
        };

        $scope.destroy = function () {
            $http.delete('/customers/' + $routeParams.customerId).
                success(function () {
                    $location.path('/');
                }
            );
        };
    })

    .controller('newCustomer',
    function ($location, $routeParams, $scope, $http) {

        $scope.save = function () {
            $http.post('/customers/', $scope.customer).
                success(function () {
                    $location.path('/');
                }
            );
        };

    });

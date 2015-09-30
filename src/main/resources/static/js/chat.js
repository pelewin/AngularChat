angular.module('angularChat', ['ngRoute'])

    .config(function ($routeProvider, $httpProvider) {

        $routeProvider
            .when('/', {
                controller: 'login',
                templateUrl: 'login.html'
            })
            .when('/chat', {
                controller: 'messages',
                templateUrl: 'chat.html'
            })
            .otherwise({
                redirectTo: '/'
            });

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    })

    .controller('login',
    function ($rootScope, $scope, $http, $location) {
        var authenticate = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('user', {headers: headers}).success(function (data) {
                if (data.name) {
                    $rootScope.authenticated = true;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

        }

        authenticate();
        $scope.credentials = {};
        $scope.login = function () {
            authenticate($scope.credentials, function () {
                if ($rootScope.authenticated) {
                    $location.path("/chat");
                    $scope.error = false;
                } else {
                    $location.path("/login");
                    $scope.error = true;
                }
            });
        };

    })

    .controller('messages',
    function ($rootScope, $scope, $http, $location) {

        var stompClient = null;
        $scope.messages = [];

        $http.get('/api/messages').
            success(function (data) {
                $scope.messages = data;
            }
        );

        //connect();

        $scope.sendMessage = function () {
            stompClient.send("/app/message", {}, $scope.messageText);
            $scope.messageText = "";
        };

        $scope.redraw = function () {
            gotoBottom();
        }

        function connect() {
            var socket = new SockJS('/message');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function (message) {
                    $scope.messages.push(JSON.parse(message.body));
                    $scope.$apply();
                    gotoBottom();

                });
            });
            gotoBottom();
        }

        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            console.log("Disconnected");
        }

        $scope.logout = function () {
            $http.post('/logout', {}).success(function () {
                $rootScope.authenticated = false;
                $location.path("/");
            }).error(function (data) {
                $rootScope.authenticated = false;
                $location.path("/");
            })
        }

    }
);

function gotoBottom() {
    // Animate
    $("#viewport-content").animate({
        bottom: $("#viewport-content").height() - $("#viewport").height()
    }, 250);
};


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

        $http.get('user').success(function (data) {
                $scope.userName = data.name;
        });

        var stompClient = null;
        $scope.messages = [];

        $http.get('/api/messages').
            success(function (data) {
                $scope.messages = data;
                gotoBottom();
            }
        );

        connect();

        $scope.sendMessage = function () {
            stompClient.send("/app/message", {}, $scope.messageText);
            $scope.messageText = "";
            gotoBottom();
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
                    newMessage = JSON.parse(message.body);
                    $scope.messages.push(newMessage);
                    $scope.$apply();
                    gotoBottom();
                    if (newMessage.user.name != $scope.userName) {
                        notifyMe(newMessage.user.name, newMessage.text);
                    }

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
    $("#viewport-content").animate({ scrollTop: $('#viewport-content')[0].scrollHeight}, 1000);
};

function notifyMe(author, message) {

    if (isFocused == "focus") return;

    var options = {
        body: message,
        //icon: theIcon
    }

    var myNotification = new Notify(author, {
        body: message,
        timeout: 3
    });

    myNotification.show();
}

var isFocused;


$(window).on("blur focus", function(e) {
    isFocused = e.type;
})


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
                    notifyMe(newMessage.user.name, newMessage.text);

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

function notifyMe(author, message) {

    var options = {
      body: message,
      //icon: theIcon
    }

  // Let's check if the browser supports notifications
  if (!("Notification" in window)) {
    alert("This browser does not support desktop notification");
  }

  // Let's check whether notification permissions have already been granted
  else if (Notification.permission === "granted") {
    // If it's okay let's create a notification
    var notification = new Notification(author, options);
  }

  // Otherwise, we need to ask the user for permission
  else if (Notification.permission !== 'denied') {
    Notification.requestPermission(function (permission) {
      // If the user accepts, let's create a notification
      if (permission === "granted") {
        var notification = new Notification(author, options);
      }
    });
  }

  // At last, if the user has denied notifications, and you
  // want to be respectful there is no need to bother them any more.
}


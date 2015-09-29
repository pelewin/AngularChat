angular.module('angularChat', [])

    .controller('messages',
    function ($scope, $http) {

        var stompClient = null;
        $scope.messages = [];

        $http.get('/api/messages').
            success(function (data) {
                $scope.messages = data;
            }
        );

        connect();

        function gotoBottom() {
            // Animate
            $("#viewport-content").animate({
                bottom: $("#viewport-content").height() - $("#viewport").height()
            }, 250);
        };


        $scope.sendMessage = function() {
            stompClient.send("/app/message", {}, $scope.messageText);
            $scope.messageText = "";
        };

        function connect() {
            var socket = new SockJS('/message');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function(message){
                    $scope.messages.push(JSON.parse(message.body));
                    $scope.$apply();
                    gotoBottom();

                });
            });
        }

        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
             console.log("Disconnected");
        }

    }
);

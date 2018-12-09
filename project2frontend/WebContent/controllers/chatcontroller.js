app.controller('ChatCtrl',  function($rootScope ,$scope, ChatService) {
   // alert('entering chat controller')
    $scope.chats = [];
    $scope.stompClient = ChatService.stompClient;
    $scope.users=[]
    $scope.$on('sockConnected', function(event, frame) {
    	/*alert('connected successfully..')*/
    	//alert('=1')
        $scope.userName=$rootScope.user.firstname;
        alert($scope.userName + ' connects with websocket');
        $scope.stompClient.subscribe("/topic/join", function(message) {
        	//alert('=2')
            user = JSON.parse(message.body);
            console.log(user)
            alert(message.body)
            if(user != $scope.userName && $.inArray(user, $scope.users) == -1) {
                $scope.addUser(user);
                $scope.latestUser = user;
                $scope.$apply();
 		/*alert($scope.latestUser + ' has joined the chat ')*/
                //alert('=3')
                $('#joinedChat').fadeIn(500).delay(10000).fadeOut(500);
            }
            
        });
        
  
        $scope.stompClient.subscribe('/app/join/'+$scope.userName, function(message) {
           /* alert(message)
	    alert(message.body)*/
        	//alert('=4')
            $scope.users = JSON.parse(message.body);
        	
            $scope.$apply();
        });
        
    });

    $scope.sendMessage = function(chat) {
        chat.from = $scope.userName;
      //alert('=5')
        $scope.stompClient.send("/app/chat", {}, JSON.stringify(chat));
        $rootScope.$broadcast('sendingChat', chat);
        $scope.chat.message = '';

    };

    $scope.capitalize = function(str) {
    	//alert('=6')
        return str.charAt(0).toUpperCase() + str.slice(1);
    };
 
    $scope.addUser = function(user) {
    	//alert('=7')
        $scope.users.push(user);
        $scope.$apply();
    };
    
    
    $scope.$on('sockConnected', function(event, frame) {
        
    	//alert('=8')
        $scope.user=$rootScope.user.firstname;
       
        $scope.stompClient.subscribe( "/queue/chats/"+$scope.userName, function(message) {
        	//alert("bd")
            $scope.processIncomingMessage(message, false);
        });
        
        
        $scope.stompClient.subscribe("/queue/chats", function(message) {
        	//alert('=9')
            $scope.processIncomingMessage(message, true);
        });
        
        
    });

    $scope.$on('sendingChat', function(event, sentChat) {
    	//alert('=10')
        chat = angular.copy(sentChat);
        chat.from = 'Me';
        chat.direction = 'outgoing';
        $scope.addChat(chat);
    });

    $scope.processIncomingMessage = function(message, isBroadcast) {
    	//alert('=11')
        message = JSON.parse(message.body);
        message.direction = 'incoming';
	message.broadcast=isBroadcast
        if(message.from != $scope.userName) {
        	alert('=12')
        	$scope.addChat(message);
            $scope.$apply(); // since inside subscribe closure
        }
    };
    
    ChatService.users.then(function(response) {
    	//alert('=13')
		$scope.users = response.data;
		console.log("friend status " + JSON.stringify($scope.users));

		for (var i = 0; i < $scope.users.length; i++) {
			console.log($scope.users[i].firstname);
		}

	}, function(response) {
		$rootScope.error = response.data
		if (response.status == 401)
			$location.path('/login')

	});
 
    $scope.addChat = function(chat) {
    	//alert('=14')
        $scope.chats.push(chat);
    };
 
 
});
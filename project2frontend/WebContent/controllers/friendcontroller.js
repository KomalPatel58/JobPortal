/**
 * FriendCtrl
 */
app.controller('FriendCtrl',function($scope,$location,FriendService){
	function getAllSuggestedUsers()
	{
		FriendService.getAllSuggestedUsers().then(function(response){
			
			$scope.suggestedUsers=response.data
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.sendFriendRequest=function(user){
		FriendService.sendFriendRequest(user).then(function(response){
			alert('Friend request has been sent successfully')
			getAllSuggestedUsers()
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	function getPendingRequests(){
		FriendService.getPendingRequests().then(function(response){
			$scope.pendingRequests=response.data
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.acceptRequest=function(friendRequest){
		FriendService.acceptRequest(friendRequest).then(
		function(response){
			getPendingRequests()
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.deleteRequest=function(friendRequest){
		FriendService.deleteRequest(friendRequest).then(
		function(response){
			getPendingRequests()
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	getPendingRequests()
	getAllSuggestedUsers()
	
	FriendService.getAllFriends().then(
			function(response){
				$scope.friends=response.data
			},function(response){
				$scope.error=response.data
				if(response.status==401)
					$location.path('/login')
			})
})
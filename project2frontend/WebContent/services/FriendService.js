/**
 * FriendService
 */
app.factory('FriendService',function($http){
	var friendService={}
	
	friendService.getAllSuggestedUsers=function(){
		return $http.get("http://localhost:8085/project2middleware/suggestedusers")
	}
	
	friendService.sendFriendRequest=function(user){
		return $http.post("http://localhost:8085/project2middleware/friendrequest",user)
	}
	
	friendService.getPendingRequests=function(){
		return $http.get("http://localhost:8085/project2middleware/pendingrequests")
	}
	
	friendService.acceptRequest=function(friendRequest){
		return $http.put("http://localhost:8085/project2middleware/acceptrequest",friendRequest)
	}
	
	friendService.deleteRequest=function(friendRequest){
		return $http.put("http://localhost:8085/project2middleware/deleterequest",friendRequest)
	}
	
	friendService.getAllFriends=function(){
		return $http.get("http://localhost:8085/project2middleware/friends")
	}
	return friendService;
})
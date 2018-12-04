/**
 * JobApplicationCtrl
 */
app.controller('JobApplicationCtrl',function($rootScope,$scope,$location,$routeParams,JobApplicationService){
	
	var jobNotificationId=$routeParams.id
	function getAllJobNotifications(){
		JobApplicationService.getAllJobNotifications().then(function(response){
			$rootScope.jobNotifications=response.data
			
			$rootScope.jobNotificationCount=$rootScope.jobNotifications.length
		},function(response){
			if(response.status=401)
					$location.path('/login')
		})		
	}
	
	if(jobNotificationId!=undefined){
		JobApplicationService.getJobNotification(jobNotificationId).then(function(response){
			$scope.jobNotification=response.data
			
		},function(response){
			if(response.status=401)
				$location.path('/login')
		})
	
	
	JobApplicationService.updateJobNotification(jobNotificationId).then(function(response){
		getAllJobNotifications()
		
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	}
	getAllJobNotifications();
})
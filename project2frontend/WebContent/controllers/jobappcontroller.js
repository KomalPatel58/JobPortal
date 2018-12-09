/**
 * JobAppCtrl
 */

app.controller('JobAppCtrl',function($scope,JobApplicationService,$location,$rootScope){
	$scope.isRejected=false
	
	if($rootScope.user!=undefined){
		if($rootScope.user.role=='ADMIN')
			{
			JobApplicationService.getAllJobApplications().then(function(response){
					$scope.jobApplications=response.data
				},function(response){
					$scope.error=response.data
					console.log($scope.error)
					if(response.status==401 && $scope.error.errorCode==5)
						$location.path('/login')
				})
			}
	}
	else
		{
		$location.path('/login')
		}
	
	function getAllJobApp()
	{
		if($rootScope.user!=undefined){
			if($rootScope.user.role=='ADMIN')
				{
				JobApplicationService.getAllJobApplications().then(function(response){
						$scope.jobApplications=response.data
					},function(response){
						$scope.error=response.data
						console.log($scope.error)
						if(response.status==401 && $scope.error.errorCode==5)
							$location.path('/login')
					})
				}
		}
		else
			{
			$location.path('/login')
			}
	}
	
	$scope.approve=function(jobApplicationId){
		JobApplicationService.updateJobStatus(jobApplicationId).then(function(response){
			getAllJobApp();
		},function(response){
			$scope.error=response.data
			if(response.status==401 && response.data.errorCode==5)
				$location.path('/login')
		})
	}
	
	$scope.reject=function(jobApplication,rejectionReason){
		if(rejectionReason==undefined)
			rejectionReason='Not Mentioned by Admin'
				JobApplicationService.rejectJobApplication(jobApplication,rejectionReason).then(function(response){
					getAllJobApp();
		},function(response){
			$scope.error=response.data
			if(response.status==401 && response.data.errorCode==5)
				$location.path('/login')
		})
	}
	
	$scope.showTxtForRejectionReason=function(){
		$scope.isRejected=!$scope.isRejected
	}
})
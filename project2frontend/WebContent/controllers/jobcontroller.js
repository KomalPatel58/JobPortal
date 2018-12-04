/**
 * JobCtrl
 */
app.controller('JobCtrl',function($scope,JobService,$location){
	$scope.isClicked=false;
	$scope.addJob=function(job)
	{
		
		JobService.addJob(job).then(
				function(response){
					alert('Job Details PostedSuccessfully..')
					$location.path('/getalljobs')
					
				},function(response){
					$scope.error=response.data
					if($scope.error.errorcode==5)
						$location.path('/login')
				})
	}
	
	/*$scope.getAllJobs=function(){
		
		
	}*/
	JobService.getAllJobs().then(function(response){
		$scope.jobs=response.data
	},function(response){
		if(response.status=401){
			$location.path('/login')
		}
		
	})
	
	$scope.showDetails=function(JobId)
	{
		$scope.isClicked=!$scope.isClicked;
		$scope.jobId=JobId;
	}
	
	$scope.applyJob=function(jobId)
	{
		
		JobService.addJobApplication(jobId).then(
				function(response){
					alert('Job Details PostedSuccessfully..')
					$location.path('/getalljobs')
					
				},function(response){
					$scope.error=response.data
					if($scope.error.errorcode==5)
						$location.path('/login')
				})
	}
	
})
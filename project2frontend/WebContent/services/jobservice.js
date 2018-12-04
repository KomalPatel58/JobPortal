/**
 * jobService 
 */
app.factory('JobService',function($http){
	var jobService={}
	
	jobService.addJob=function(job){
		return $http.post("http://localhost:8085/project2middleware/addjob",job)
	}
	
	jobService.getAllJobs=function(){
		return $http.get("http://localhost:8085/project2middleware/getalljobs")
	}
	jobService.addJobApplication=function(jobId){
		return $http.post("http://localhost:8085/project2middleware/addjobapplication/"+jobId)
	}
	return jobService;
})
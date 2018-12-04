/**
 * JobApplicationService
 */
app.factory('JobApplicationService',function($http){
	
	var jobApplicationService={}
	jobApplicationService.getJobNotification=function(jobNotificationId){
		return $http.get("http://localhost:8085/project2middleware/getjobnotification/"+jobNotificationId)
	}
	jobApplicationService.getAllJobNotifications=function(){
		return $http.get("http://localhost:8085/project2middleware/getalljobnotifications")
	}
	jobApplicationService.updateJobNotification=function(jobNotificationId){
		return $http.put("http://localhost:8085/project2middleware/updatejobnotification/"+jobNotificationId)
	}
	jobApplicationService.getAllJobApplications=function(){
		return $http.get("http://localhost:8085/project2middleware/getalljobapplications")
	}
	jobApplicationService.rejectJobApplication=function(jobApplication,rejectionReason){
		return $http.put("http://localhost:8085/project2middleware/rejectjobapplication/"+rejectionReason,jobApplication)
	}
	jobApplicationService.updateJobStatus=function(jobApplicationId){
		return $http.put("http://localhost:8085/project2middleware/updatejobstatus/"+jobApplicationId)
	}
	return jobApplicationService
})
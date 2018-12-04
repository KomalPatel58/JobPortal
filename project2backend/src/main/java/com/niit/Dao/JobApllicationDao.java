package com.niit.Dao;


import java.util.List;


import com.niit.Models.JobApplication;



public interface JobApllicationDao {

	void addJobApplication(JobApplication jobApplication);
	List<JobApplication> getAllJobNotifications(String email);
	List<JobApplication> getAllJobApplications(String email);
	JobApplication getJobNotification(int id);
	void updateJobNotification(int Id);
	void updateJobStatus(int id);
	void updateJobApplication(JobApplication jobApplication);
}

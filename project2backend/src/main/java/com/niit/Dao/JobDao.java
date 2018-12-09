package com.niit.Dao;

import java.util.List;

import com.niit.Models.Job;

public interface JobDao {
	void addJob(Job job);
	List<Job> getAllJobs(String email);
	Job getJob(int id);
}

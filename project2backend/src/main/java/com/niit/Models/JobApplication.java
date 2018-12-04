package com.niit.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="jobapplication_s190038")
public class JobApplication {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private User jobApplicant;
	@ManyToOne
	private Job jobAppliedFor;
	private char jobStatus;
	private char jobNotified;
	private String rejectionReason;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getJobApplicant() {
		return jobApplicant;
	}
	public void setJobApplicant(User jobApplicant) {
		this.jobApplicant = jobApplicant;
	}
	public Job getJobAppliedFor() {
		return jobAppliedFor;
	}
	public void setJobAppliedFor(Job jobAppliedFor) {
		this.jobAppliedFor = jobAppliedFor;
	}
		
	
	public char getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(char jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public char getJobNotified() {
		return jobNotified;
	}
	public void setJobNotified(char jobNotified) {
		this.jobNotified = jobNotified;
	}
}

package com.niit.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.JobApllicationDao;
import com.niit.Dao.JobDao;
import com.niit.Dao.UserDao;
import com.niit.Models.ErrorClazz;
import com.niit.Models.Job;
import com.niit.Models.JobApplication;
import com.niit.Models.User;

@Controller
public class JobApplicationController {

	@Autowired
	private JobApllicationDao jobApplicationDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JobDao jobDao;
	@RequestMapping(value="/addjobapplication/{jobId}",method=RequestMethod.POST)
	public ResponseEntity<?>addJobApplication(@PathVariable int jobId,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		JobApplication jobApplication=new JobApplication();
		Job jobAppliedFor=jobDao.getJob(jobId);
		jobApplication.setJobAppliedFor(jobAppliedFor);
		User jobApplicant=userDao.getUser(email);
		jobApplication.setJobApplicant(jobApplicant);
		jobApplication.setJobNotified('P');
		jobApplication.setJobStatus('P');
		jobApplication.setRejectionReason(null);
		try
		{
			jobApplicationDao.addJobApplication(jobApplication);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Unable to post blogpost details.."+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getjobnotification/{jobNotificationId}",method=RequestMethod.GET)
	public ResponseEntity<?>getJobNotification(@PathVariable int jobNotificationId,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		JobApplication jobNotification=jobApplicationDao.getJobNotification(jobNotificationId);
		return new ResponseEntity<JobApplication>(jobNotification,HttpStatus.OK);
		
	}
	@RequestMapping(value="/getalljobnotifications",method=RequestMethod.GET)
	public ResponseEntity<?>getAllJobNotifications(HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<JobApplication> jobNotifications=jobApplicationDao.getAllJobNotifications(email);
		return new ResponseEntity<List<JobApplication>>(jobNotifications,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatejobnotification/{jobNotificationId}")
	public ResponseEntity<?>updateJobNotification(@PathVariable int jobNotificationId,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			jobApplicationDao.updateJobNotification(jobNotificationId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Unable to post blogpost details.."+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/updatejobstatus/{jobApplicationId}")
	public ResponseEntity<?>updateJobStatus(@PathVariable int jobApplicationId,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			jobApplicationDao.updateJobStatus(jobApplicationId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Unable to post blogpost details.."+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getalljobapplications",method=RequestMethod.GET)
	public ResponseEntity<?>getAllJobApplications(HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<JobApplication> jobApplications=jobApplicationDao.getAllJobApplications(email);
		return new ResponseEntity<List<JobApplication>>(jobApplications,HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectjobapplication/{rejectionReason}",method=RequestMethod.PUT)
	public ResponseEntity<?>rejectJobApplication(@PathVariable String rejectionReason,@RequestBody JobApplication jobApplication,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized Access..Please Login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			jobApplication.setJobStatus('N');
			jobApplication.setJobNotified('N');
			jobApplication.setRejectionReason(rejectionReason);
			jobApplicationDao.updateJobApplication(jobApplication);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Unable to post blogpost details.."+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}

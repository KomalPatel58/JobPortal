package com.niit.Controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.JobDao;
import com.niit.Dao.UserDao;
import com.niit.Models.ErrorClazz;
import com.niit.Models.Job;
import com.niit.Models.User;

@Controller
public class JobController {

	@Autowired
	private JobDao jobDao;
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/addjob",method=RequestMethod.POST)
	public ResponseEntity<?> addJob(@RequestBody Job job,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		System.out.println("SESSION ID IN addJob()" + session.getId());
		System.out.println("Session Attribute email in addJob()" + session.getAttribute("email"));
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access..please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			System.out.println(new Date());
			job.setPostedOn(new Date());
			jobDao.addJob(job);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Unable to insert job..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getalljobs",method=RequestMethod.GET)
	public ResponseEntity<?>getAllJobs(HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<Job> jobs=jobDao.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
	
}

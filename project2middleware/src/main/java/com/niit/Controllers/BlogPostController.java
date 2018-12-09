package com.niit.Controllers;

import java.util.Date;
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

import com.niit.Dao.BlogPostDao;
import com.niit.Dao.NotificationDao;
import com.niit.Dao.UserDao;
import com.niit.Models.BlogPost;
import com.niit.Models.ErrorClazz;
import com.niit.Models.Notification;
import com.niit.Models.User;

@Controller
public class BlogPostController {

	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotificationDao notificationDao;
	
	@RequestMapping(value="/addblogpost",method=RequestMethod.POST)
	public ResponseEntity<?>addBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorised access..please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPost.setPostedOn(new Date());
		User postedBy=userDao.getUser(email);
		blogPost.setPostedBy(postedBy);
		try
		{
			blogPostDao.addBlogPost(blogPost);
			return new ResponseEntity<Void>(HttpStatus.OK);
			
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Unable to post blogpost details.."+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/blogswaitingforapproval",method=RequestMethod.GET)
	public ResponseEntity<?> blogsWaitingForApproval(HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access..please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost>blogs=blogPostDao.blogsWaitingForApproval();
		return new ResponseEntity<List<BlogPost>>(blogs,HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogsapproved",method=RequestMethod.GET)
	public ResponseEntity<?> blogsApproved(HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access..please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			
		}
		
		List<BlogPost>blogs=blogPostDao.blogsApproved();
		return new ResponseEntity<List<BlogPost>>(blogs,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblog/{blogPostId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlog(@PathVariable int blogPostId,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access..please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			
		}
		BlogPost blogPost=blogPostDao.getBlog(blogPostId);
		if(!blogPost.isApproved())
		{
			User user=userDao.getUser(email);
			if(email==null)
			{
				ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access..please login..");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
				
			}
		}
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/approve",method=RequestMethod.PUT)
	public ResponseEntity<?>approveBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			
		}
		
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPost.setApproved(true);
		try
		{
			blogPostDao.updateBlogPost(blogPost);
			Notification notification=new Notification();
			notification.setApprovalStatus("Approved");
			notification.setBlogTitle(blogPost.getBlogTitle());
			notification.setUserToBeNotified(blogPost.getPostedBy());
			notificationDao.addNotification(notification);
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Unable to approve the blogpost" + e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reject/{rejectionReason}",method=RequestMethod.PUT)
	public ResponseEntity<?>rejectBlogPost(@PathVariable String rejectionReason,@RequestBody BlogPost blogPost,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Unauthorized access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")) {
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		try
		{
			Notification notification=new Notification();
			notification.setApprovalStatus("Rejected");
			notification.setBlogTitle(blogPost.getBlogTitle());
			notification.setUserToBeNotified(blogPost.getPostedBy());
			notification.setRejectionReason(rejectionReason);
			notificationDao.addNotification(notification);
			blogPostDao.deleteBlogPost(blogPost);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			ErrorClazz errorClazz=new ErrorClazz(7,"Unable to approve the blogpost" + e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

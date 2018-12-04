package com.niit.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.niit.Dao.ProfilePictureDao;
import com.niit.Models.ErrorClazz;
import com.niit.Models.ProfilePicture;

@Controller
public class ProfilePictureController {
	@Autowired
	private ProfilePictureDao profilePictureDao;
	@RequestMapping(value="/uploadprofilepic")
	public ResponseEntity<?> saveOrUpdateProfilePicture(@RequestParam CommonsMultipartFile image,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5, "Unauthorized access..please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		ProfilePicture profilePicture=new ProfilePicture();
		profilePicture.setEmail(email);
		profilePicture.setImage(image.getBytes());
		profilePictureDao.saveOrUpdateProfilePic(profilePicture);
		return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getimage",method=RequestMethod.GET)
	public @ResponseBody byte[] getImage(@RequestParam String email,HttpSession session)
	{
		String auth=(String)session.getAttribute("email");
		if(auth==null)
		{
			return null;
		}
		ProfilePicture profilePicture=profilePictureDao.getImage(email);
		if(profilePicture==null)
			return null;
		else
			return profilePicture.getImage();
	}
	
}

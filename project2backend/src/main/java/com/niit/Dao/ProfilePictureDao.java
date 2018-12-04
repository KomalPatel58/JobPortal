package com.niit.Dao;

import com.niit.Models.ProfilePicture;

public interface ProfilePictureDao {
	
	ProfilePicture saveOrUpdateProfilePic(ProfilePicture profilePicture);
	ProfilePicture getImage(String email);
}

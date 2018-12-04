package com.niit.Dao;

import com.niit.Models.User;

public interface UserDao {
	void registerUser(User user);
	boolean isEmailUnique(String email);
	User login(User user);
	void updateUser(User user);
	User getUser(String email);

}

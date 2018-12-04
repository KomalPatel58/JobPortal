package com.niit.DaoImpl;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.Dao.UserDao;
import com.niit.Models.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public UserDaoImpl() {
		System.out.println("UserDaoImpl is instantiated");
		// TODO Auto-generated constructor stub
	}

	public void registerUser(User user) {
		Session session=sessionFactory.getCurrentSession();
	    session.save(user);
		// TODO Auto-generated method stub

	}

	public boolean isEmailUnique(String email) {
		Session session=sessionFactory.getCurrentSession();
		//select * from user_s190038 where email='john@niit.com'
		User user=(User)session.get(User.class, email);
		if(user==null)
			return true; //entered email is unique
		else
			return false;//entered email is duplicate,another user has already registered with same email id 
		// TODO Auto-generated method stub
		
	}

	public User login(User user) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where email=? and password=?");
		query.setString(0, user.getEmail());
		query.setString(1, user.getPassword());
		
		return (User)query.uniqueResult();
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.update(user);
	}

	public User getUser(String email) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, email);
		return user;
		
	}

}

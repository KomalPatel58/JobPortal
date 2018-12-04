package com.niit.DaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Dao.NotificationDao;
import com.niit.Models.Notification;

@Repository
@Transactional
public class NotificationDaoImpl implements NotificationDao {

	@Autowired
	private SessionFactory sessionFactory;
	public void addNotification(Notification notification) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(notification);
	}

	public List<Notification> getAllNotifications(String email) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Notification where viewed=0 and userToBeNotified.email=?");
		query.setString(0,email);
		List<Notification> notifications=query.list();
		return notifications;		
	}

	public Notification getNotification(int notificationId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Notification notification=(Notification)session.get(Notification.class, notificationId);
		return notification;
	}

	public void updateNotification(int notificationId) {
		// TODO Auto-generated method stub
			Session session=sessionFactory.getCurrentSession();
			Notification notification = (Notification)session.get(Notification.class,notificationId);
			notification.setViewed(true);
			session.update(notification);
	}

}

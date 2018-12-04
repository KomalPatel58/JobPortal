package com.niit.DaoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.Dao.JobApllicationDao;
import com.niit.Models.JobApplication;

@Repository
@Transactional
public class JobApplicationDaoImpl implements JobApllicationDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	public void addJobApplication(JobApplication jobApplication) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(jobApplication);
	}

	public List<JobApplication> getAllJobNotifications(String email) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from JobApplication where jobNotified='N' and jobApplicant.email=?");
		query.setString(0, email);
		List<JobApplication> jobNotifications=query.list();
		return jobNotifications;
	}

	public JobApplication getJobNotification(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		JobApplication jobApplication=(JobApplication)session.get(JobApplication.class, id);
		return jobApplication;
		
	}

	public void updateJobNotification(int Id) {
		// TODO Auto-generated method stub
			Session session=sessionFactory.getCurrentSession();
			JobApplication jobApplication=(JobApplication)session.get(JobApplication.class, Id);
			jobApplication.setJobNotified('V');
			session.update(jobApplication);
	}

	public void updateJobStatus(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		JobApplication jobApplication=(JobApplication)session.get(JobApplication.class, id);
		jobApplication.setJobStatus('A');
		jobApplication.setJobNotified('N');
		session.update(jobApplication);
	}

	public List<JobApplication> getAllJobApplications(String email) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from JobApplication where jobStatus='P'");
		
		List<JobApplication> jobApplications=query.list();
		return jobApplications;
	}
	public void updateJobApplication(JobApplication jobApplication)
	{
		Session session=sessionFactory.getCurrentSession();
		session.update(jobApplication);
	}

}

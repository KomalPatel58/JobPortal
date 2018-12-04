package com.niit.DaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Dao.JobDao;
import com.niit.Models.Job;

@Repository
@Transactional
public class JobDaoImpl implements JobDao {

	@Autowired
	private SessionFactory sessionFactory;
	public void addJob(Job job) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(job);
	}

	public List<Job> getAllJobs() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Job");
		List<Job> jobs=query.list();
		// TODO Auto-generated method stub
		return jobs;
	}

	public Job getJob(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Job job=(Job)session.get(Job.class, id);
		return job;
	}

}

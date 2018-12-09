package com.niit.DaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Dao.JobDao;
import com.niit.Models.Job;
import com.niit.Models.User;

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

	public List<Job> getAllJobs(String email ) {
		String queryString="select * from job_s190038 where id in"
				+ "(select id from job_s190038"
				+" minus "
				+"select jobAppliedFor_id from jobapplication_s190038 where jobApplicant_email=?)";
		
		Session session=sessionFactory.getCurrentSession();
		SQLQuery sqlquery=session.createSQLQuery(queryString);
		sqlquery.addEntity(Job.class);
		sqlquery.setString(0, email);
		List<Job> jobs=sqlquery.list();
			
		/*Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Job");
		List<Job> jobs=query.list();*/
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

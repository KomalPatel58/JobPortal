package com.niit.DaoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.Dao.BlogCommentDao;
import com.niit.Models.BlogComment;

@Repository
@Transactional
public class BlogCommentDaoImpl implements BlogCommentDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	public void addBlogComment(BlogComment blogComment) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(blogComment);
		
	}

	public List<BlogComment> getAllBlogComments(int blogPostId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogComment where blogPost.id=?");
		query.setInteger(0, blogPostId);
		List<BlogComment> blogComments=query.list();
		return blogComments;
	}

}

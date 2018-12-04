package com.niit.DaoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Dao.BlogPostLikesDao;
import com.niit.Models.BlogPost;
import com.niit.Models.BlogPostLikes;
import com.niit.Models.User;

@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see com.niit.Dao.BlogPostLikesDao#hasUserLikedBlogPost(int, java.lang.String)
	 */
	public BlogPostLikes hasUserLikedBlogPost(int blogPostId, String email) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("From BlogPostLikes where blogPost.id=? and user.email=?");
		query.setInteger(0, blogPostId);
		query.setString(1, email);
		BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
		return blogPostLikes;
	}

	public BlogPost updateLikes(int blogPostId,String email) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		BlogPostLikes blogPostLikes=hasUserLikedBlogPost(blogPostId, email);
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogPostId);
		User user=(User)session.get(User.class, email);
		if(blogPostLikes==null)
		{
			blogPostLikes=new BlogPostLikes();
			blogPostLikes.setBlogPost(blogPost);
			blogPostLikes.setUser(user);
			session.save(blogPostLikes);
			blogPost.setLikes(blogPost.getLikes()+1);
			session.update(blogPost);
		}
		else
		{
			session.delete(blogPostLikes);
			blogPost.setLikes(blogPost.getLikes()-1);
			session.update(blogPost);
		}
		return blogPost;
	}

}

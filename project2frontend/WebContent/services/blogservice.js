/**
 * BlogService
 */
app.factory('BlogService',function($http){
	var blogService={}
	blogService.addBlog=function(blog){
		return $http.post("http://localhost:8085/project2middleware/addblogpost",blog)
	}
	
	blogService.getBlogsWaitingForApproval=function(){
		return $http.get("http://localhost:8085/project2middleware/blogswaitingforapproval")
	}
	
	blogService.getBlogsApproved=function(){
		return $http.get("http://localhost:8085/project2middleware/blogsapproved")
	}
	
	blogService.getBlog=function(blogPostId){
		return $http.get("http://localhost:8085/project2middleware/getblog/"+blogPostId)
	}
	
	blogService.approve=function(blogPost){
		return $http.put("http://localhost:8085/project2middleware/approve",blogPost)
	}
	
	blogService.reject=function(blogPost,rejectionReason){
		return $http.put("http://localhost:8085/project2middleware/reject/"+rejectionReason,blogPost)
	}
	
	blogService.hasUserLikedBlogPost=function(blogPostId){
			return $http.get("http://localhost:8085/project2middleware/hasuserlikedblogpost/"+blogPostId)
		}
		
	blogService.updateLikes=function(blogPostId){
		return $http.put("http://localhost:8085/project2middleware/updatelikes/"+blogPostId)
	}
	
	blogService.addBlogComment=function(blogComment){
		return $http.post("http://localhost:8085/project2middleware/addblogcomment",blogComment)
	}
	
	blogService.getAllBlogComments=function(blogPostId){
		return $http.get("http://localhost:8085/project2middleware/getblogcomments/"+blogPostId)
	}
		return blogService
})
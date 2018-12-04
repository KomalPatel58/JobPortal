/**
 * BlogInDetailCtrl
 */
app.controller('BlogInDetailCtrl',function($scope,BlogService,$rootScope,$routeParams,$location,$sce){
	
			var blogPostId=$routeParams.id
			$scope.isRejected=false
			if(blogPostId!=undefined){
			BlogService.getBlog(blogPostId).then(function(response){
				$scope.blogPost=response.data
				$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
				/*console.log(blogPost)*/
			},function(response){
				/*console.log(response.data)*/
				$scope.error=response.data
				if(response.status==401 && response.data.errorCode==5)
					$location.path('/login')
				alert(response.status)
			})
			BlogService.hasUserLikedBlogPost(blogPostId).then(
					function(response){
						//response.data = '' or 1 blobpostlikes object
						if(response.data=='')
							$scope.isLiked=false
							else
								$scope.isLiked=true
					},
					function(response){
						$scope.error=response.data 
						if(response.status==401 && response.data.errorCode==5)
							$location.path('/login')
					})
					
					}
					
		
			
			$scope.approve=function(blogPost){
				BlogService.approve(blogPost).then(function(response){
					$location.path('/blogswaitingforapproval/1')
				},function(response){
					$scope.error=response.data
					if(response.status==401 && response.data.errorCode==5)
						$location.path('/login')
				})
			}
			
			$scope.reject=function(blogPost,rejectionReason){
				if(rejectionReason==undefined)
					rejectionReason='Not Mentioned by Admin'
				BlogService.reject(blogPost,rejectionReason).then(function(response){
					$location.path('/blogswaitingforapproval/1')
				},function(response){
					$scope.error=response.data
					if(response.status==401 && response.data.errorCode==5)
						$location.path('/login')
				})
			}
			
			$scope.showTxtForRejectionReason=function(){
				$scope.isRejected=!$scope.isRejected
			}
			
			$scope.updateLikes=function(blogPostId){
				BlogService.updateLikes(blogPostId).then(function(response){
					$scope.blogPost=response.data
					$scope.isLiked=!$scope.isLiked
					
				},function(response){
					$scope.error=response.data
					if(response.status==401 && response.data.errorcode==5)
						$location.path('/login')
				})
				}
			
			$scope.addBlogComment=function(blogPost,commentTxt){
				var blogComment={}
				blogComment.blogPost=blogPost
				blogComment.commentTxt=commentTxt
				BlogService.addBlogComment(blogComment).then(function(response){
					$scope.blogComment=response.data
					$scope.commentTxt=""
				},function(response){
					$scope.error=response.data
					if(response.status==401 && response.data.errorCode==5)
						$location.path('/login')
				})
			}
			 $scope.getAllBlogComments=function(){
					if(blogPostId!=undefined){
					$scope.showComments=!$scope.showComments
					BlogService.getAllBlogComments(blogPostId).then(
							function(response){
								//response.data -> List<BlogComment>
								//response.data -> select  * from blogcomment where blogpost_id=?
								$scope.blogComments=response.data
							},function(response){
								$scope.error=response.data 
								if(response.status==401 && response.data.errorCode==5)//Not loggedin, login.html
									$location.path('/login')
							})
					}
			 }
			})
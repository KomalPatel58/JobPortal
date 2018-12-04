/**
 * BlogCtrl
 */
app.controller('BlogCtrl',function($scope,BlogService,$location,$rootScope,$routeParams){
	$scope.addBlog=function(blog){
		BlogService.addBlog(blog).then(function(response){
			alert('Blog details inserted successfully and it is waiting for approval')
			$location.path('/home')
		},function(){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	if($rootScope.user!=undefined){
		if($rootScope.user.role=='ADMIN' && routeParams.id==1)
			{
				BlogService.getBlogWaitingApproval().then(function(response){
					$scope.blogWaitingForApproval=response.data
				},function(response){
					$scope.error=response.data
					console.log($scope.error)
					if(response.status==401 && $scope.error.errorCode==5)
						$location.path('/login')
				})
			}
	}
	else
		{
		$location.path('/login')
		}
	
		if($rootScope.user!=undefined){
			if($routeParams.id==2)
				{
				BlogService.getBlogApproved().then(function(response){
					$scope.blogs=response.data
				},function(response){
					if(response.status==401)
						$location.path('/login')
				})
				}
		}
		else
			{
				$location.path('/login')
			}

	
})
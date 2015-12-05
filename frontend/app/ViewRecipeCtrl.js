myApp.controller('ViewRecipeCtrl', function($scope, $rootScope, $state, $stateParams, userService, recipeService, communityService) {
		$scope.max = 5;
		$scope.rate = 3;
		$scope.avgRate = 3;
		$scope.recipeId = parseInt($stateParams.recipeID);
		$scope.recipe =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		console.log("Recipe view: " + JSON.stringify($scope.recipe));

		$scope.comments=[{"id" : 1, "body":"Deneme", "owner" : "Jane Doe", "date" : "12/2/2009"},
						{"id" : 7, "body":"Uzuncana bir text Uzuncana Çok Uzun Çok Çok", "owner" : "Jane Doe", "date" : "12/2/2009"},
						{"id" : 1, "body":"Deneme", "owner" : "Jane Doe", "date" : "12/2/2009"}];

		$scope.getComments = function() {
			communityService.getComments("recipe",$scope.recipeId).then(function(response) {
				comments = response.data;
				comments.sort(function(a,b) {
					if(a.createdAt < b.createdAt) return -1;
					if(a.createdAt > b.createdAt) return 1;
					else return 0;
				});
				$scope.comments = comments;
			});
		};

		$scope.getAvgRating = function() {
			communityService.getAvgRating("recipe", $scope.recipeId).then(function(response) {
				$scope.avgRate = response.data.rating;
			});
		};

		$scope.getRatingByCurrentUser = function() {
			communityService.getRatingByCurrentUser("recipe", $scope.recipeId).then(function(response) {
				$scope.rate = response.data.rating;
			});
		}
		// CALL INIT WHEN API IS READY
		var init = function() {
			recipeService.getRecipeWithIDNew($scope.recipeId).then(
				function(response){
					$scope.recipe = response.data;
				}
			);
			/*
			$scope.getComments();
			$scope.getAvgRating();
			$scope.getRatingByCurrentUser();
			*/

		};

		$scope.addRating = function() {
			communityService.rate("recipe", $scope.recipeId, $scope.rate).then(function(response) {
				$scope.avgRating = response.data.rating;
				$scope.getAvgRating();
			});
		}

		$scope.addComment = function() {
			communityService.makeComment("recipe",  $scope.recipeId, $scope.commentText).then(function(response) {
				$scope.commentText = "";
				$scope.getComments();
			});
		}

		$scope.deleteComment = function(id) {
			communityService.deleteComment(id, "recipe",  $scope.recipeId).then(function(response) {
				$scope.getComments();
			});
		}

		$scope.isOwner = function(id) {
			return id == userService.getUser().id;
		}

		$scope.back = function() {
			$state.go($rootScope.previousState, $rootScope.previousParams);
		};
	});
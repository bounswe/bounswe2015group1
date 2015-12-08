myApp.controller('ViewRecipeCtrl', function($scope, $rootScope, $state, $stateParams, $http, userService, recipeService, communityService) {
		$scope.max = 5;
		$scope.rate = 3;
		$scope.avgRate = 3;
		$scope.recipeId = parseInt($stateParams.recipeID);
		//$scope.recipe =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		$scope.editAllowed = false;
		console.log("Recipe view: " + JSON.stringify($scope.recipe));
		$scope.recommendedRecipes = [];
		/*
		$scope.comments=[{"id" : 1, "body":"Deneme", "owner" : "Jane Doe", "date" : "12/2/2009"},
						{"id" : 7, "body":"Uzuncana bir text Uzuncana Çok Uzun Çok Çok", "owner" : "Jane Doe", "date" : "12/2/2009"},
						{"id" : 1, "body":"Deneme", "owner" : "Jane Doe", "date" : "12/2/2009"}];*/

		$scope.comments = []
		$scope.nutrCollapsed =true;

		$scope.nutritionLiterals = ['calories', 'carbohydrate', 'fats', 'proteins', 'sodium', 'fiber', 'cholesterol', 'sugars', 'iron'];
		$scope.nutritionUnits = ['cal', 'g', 'g', 'g', 'mg', 'g', 'mg', 'g', '%'];
		$scope.getComments = function() {
			communityService.getComments("recipe",$scope.recipeId).then(function(response) {
				comments = response.data;
				console.log("COMMENTS :" + JSON.stringify(comments));
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
				console.log("Average Rating " + response.data.rating);
				$scope.avgRate = response.data.rating;
			});
		};

		$scope.getRatingByCurrentUser = function() {
			communityService.getRatingByCurrentUser("recipe", $scope.recipeId).then(function(response) {
				if(response.data !== null) $scope.rate = response.data.rating;
			});
		}

		$scope.getRecommendedRecipes = function() {
			recipeService.getRecommendedRecipes($scope.recipeId).then(function(response) {
				$scope.recommendedRecipes = response.data;
			})
		}

		// CALL INIT WHEN API IS READY
		var init = function() {
			recipeService.getRecipeWithID($scope.recipeId).then(
				function(response){
					$scope.recipe = response.data;
					console.log("View Recipe recipe data: " + JSON.stringify($scope.recipe))
					console.log("User data : " + JSON.stringify(userService.getUser()));
					if(response.data.userId == userService.getUser().id) {
						$scope.editAllowed = true;
					}
					for(var i=0; i < $scope.recipe.ingredients.length; i++) {
  							$http.get($rootScope.baseUrl + '/api/ingredient/item/' + $scope.recipe.ingredients[i].ingredientId).then(function(response){
	    						if(response.data.status_code == 404) {
	    							console.log("Ingredient not found");
	    						}
	    						else {
	    							console.log("Ingredient Unit Fetched")
	    							for(var ind=0; ind < $scope.recipe.ingredients.length; ind++) {
	    								if($scope.recipe.ingredients[ind].ingredientId == response.data.item_id) {
	    									$scope.recipe.ingredients[ind].unit = response.data.nf_serving_size_unit;
	    								}
	    							}
	    						}
    						});
  					}
				}
			);
			
			$scope.getRecommendedRecipes();
			$scope.getComments();
			$scope.getAvgRating();
			$scope.getRatingByCurrentUser();
			

		};

		$scope.addRating = function() {
			communityService.rate("recipe", $scope.recipeId, $scope.rate).then(function(response) {
				//$scope.avgRating = response.data.rating;
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
			//console.log("BACK TO: " + $rootScope.previousState + " WITH PARAMS " + $rootScope.previousParams);
			//$state.go($rootScope.previousState, $rootScope.previousParams);
			//$rootScope.back();
			window.history.back();
		};

		init();
	});
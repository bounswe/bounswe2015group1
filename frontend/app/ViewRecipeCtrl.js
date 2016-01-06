myApp.controller('ViewRecipeCtrl', function($scope, $rootScope, $state, $stateParams, $http, userService, recipeService, communityService) {
		
		$scope.max = 5;
		$scope.rate = 3;
		$scope.avgRate = 3;
		$scope.recipeId = parseInt($stateParams.recipeID);
		//$scope.recipe =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		$scope.editAllowed = false;
		$scope.commentAllowed = false;
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
				comments.forEach( (comm) => { comm.createdAt = timestampToDate(comm.createdAt) })
				console.log("COMMENTS :" + JSON.stringify(comments));
				comments.sort(function(a,b) {
					if(a.createdAt < b.createdAt) return -1;
					if(a.createdAt > b.createdAt) return 1;
					else return 0;
				});
				$scope.comments = comments;
			});
		};



		var timestampToDate = function(timestamp){
		  var d = new Date(timestamp);
		  var year = d.getFullYear();
		  var month = d.getMonth() + 1;
		  var date = d.getDate();
		  return year + "-" + month + "-" + date;
		}



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
			$scope.loggedIn = userService.getLoggedIn();
			recipeService.getRecipeWithID($scope.recipeId).then(
				function(response){
					$scope.recipe = response.data;
					console.log("View Recipe recipe data: " + JSON.stringify($scope.recipe))
					console.log("User data : " + JSON.stringify(userService.getUser()));
					if(response.data.userId == userService.getUser().id) {
						$scope.editAllowed = true;
					}
					if(userService.getUser().id >= 0){
						$scope.commentAllowed = true;
					}
					userService.getUserWithId($scope.recipe.userId).then(
						function(response) {
							$scope.owner = response.data;
							$scope.ownerName=$scope.owner.fullName;
						}
					);
					userService.getAllergens().then(function(response){
						$scope.allergens = response.data;
						console.log("Allergens " + JSON.stringify($scope.allergens))
						for(var i=0; i < $scope.recipe.ingredients.length; i++) {
							$scope.recipe.ingredients[i].allergenic = false;
							for(var j=0; j < $scope.allergens.length; j++) {
								console.log("Comparing  :" + $scope.recipe.ingredients[i].ingredientId + " with " +  $scope.allergens[j].ingredientId)

								if($scope.recipe.ingredients[i].ingredientId == $scope.allergens[j].ingredientId) {
									$scope.recipe.ingredients[i].allergenic = true;
								}
							}
						}
					});
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

		$scope.consume = function() {
			var rawRecipe = $scope.recipe;
			delete rawRecipe.userId;
			delete rawRecipe.createdAt;
			delete rawRecipe.rating;
			delete rawRecipe.nutritions.id;
			for(var i=0; i < rawRecipe.ingredients.length; i++) {
				delete rawRecipe.ingredients[i].unit;
				console.log("Deleted Unit")
			}
			recipeService.consumeRecipe(JSON.stringify(rawRecipe))
		}

		init();
	});
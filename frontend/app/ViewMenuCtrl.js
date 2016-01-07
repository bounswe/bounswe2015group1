myApp.controller('ViewMenuCtrl', function($scope, $rootScope, $state, $stateParams, userService, recipeService, communityService, menuService) {
		$scope.max = 5;
		$scope.rate = 3;
		$scope.avgRate = 3;
		$scope.menuId = parseInt($stateParams.menuID);
		$scope.menu =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		$scope.commentAllowed = false;
		console.log("Menu view: " + JSON.stringify($scope.menu));


		$scope.comments = []

		$scope.getComments = function() {
			communityService.getComments("menu",$scope.menuId).then(function(response) {
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
			communityService.getAvgRating("menu", $scope.menuId).then(function(response) {
				$scope.avgRate = response.data.rating;
			});
		};

		$scope.getRatingByCurrentUser = function() {
			communityService.getRatingByCurrentUser("menu", $scope.menuId).then(function(response) {
				if(response.data !== null) $scope.rate = response.data.rating;
			});
		}
		// CALL INIT WHEN API IS READY
		var init = function() {
			menuService.getMenuWithID($scope.menuId).then(
				function(response){
					console.log("Menu View : " + JSON.stringify(response.data) );
					if(userService.getUser().id >= 0){
						$scope.commentAllowed = true;
					}
					$scope.menu = response.data;
				}
			);
			
			$scope.getComments();
			$scope.getAvgRating();
			$scope.getRatingByCurrentUser();

		};

		$scope.addRating = function() {
			communityService.rate("menu", $scope.menuId, $scope.rate).then(function(response) {
				$scope.getAvgRating();
			});
		}

		$scope.addComment = function() {
			communityService.makeComment("menu",  $scope.menuId, $scope.commentText).then(function(response) {
				$scope.commentText = "";
				$scope.getComments();
			});
		}

		$scope.deleteComment = function(id) {
			communityService.deleteComment(id, "menu",  $scope.menuId).then(function(response) {
				$scope.getComments();
			});
		}

		$scope.isOwner = function(id) {
			return id == userService.getUser().id;
		}

		$scope.back = function() {
			window.history.back();
		};

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		init();
	});
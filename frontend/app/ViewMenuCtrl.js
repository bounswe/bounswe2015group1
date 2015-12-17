myApp.controller('ViewMenuCtrl', function($scope, $rootScope, $state, $stateParams, userService, recipeService, communityService, menuService) {
		$scope.max = 5;
		$scope.rate = 3;
		$scope.avgRate = 3;
		$scope.menuId = parseInt($stateParams.menuID);
		$scope.menu =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		console.log("Menu view: " + JSON.stringify($scope.menu));

		/*$scope.comments=[{"id" : 1,"body":"Deneme", "owner" : "Jane Doe", "date" : "12/2/2009"},
						{"id" : 7,"body":"Uzuncana bir text Uzuncana Çok Uzun Çok Çok", "owner" : "Jane Doe", "date" : "12/2/2009"},
						{"id" : 1,"body":"Deneme", "owner" : "Jane Doe", "date" : "12/2/2009"}];*/

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
				$scope.avgRate = response.data;
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
					$scope.menu = response.data;
				}
			);
			
			$scope.getComments();
			$scope.getAvgRating();
			$scope.getRatingByCurrentUser();

		};

		$scope.addRating = function() {
			communityService.rate("menu", $scope.menuId, $scope.rate).then(function(response) {
				//$scope.avgRating = response.data.rating;
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
			//console.log("BACK TO: " + $rootScope.previousState + " WITH PARAMS " + $rootScope.previousParams);
			//$state.go($rootScope.previousState, $rootScope.previousParams);
			//$rootScope.back();
			window.history.back();
		};

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		init();
	});
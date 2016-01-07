myApp.controller('NavBarCtrl', function($scope, $http, $window, $state, userService, searchService) {
		$scope.loggedIn = false;
		$scope.isCollapsed = true;

		$scope.nutritionList = ['Calories', 'Carbohydrate', 'Fats', 'Proteins', 'Sodium', 'Fiber', 'Cholesterol', 'Sugars', 'Iron'];
		$scope.nutritionUnits = ['cal', 'g', 'g', 'g', 'mg', 'g', 'mg', 'g', '%'];

		$scope.includedIngredients = [];
		$scope.excludedIngredients = [];

		$scope.mins = {};
		$scope.maxs = {};
		$scope.login= function() {
				userService.login($scope.emailLogin, $scope.passLogin);
		};

		$scope.logout= function() {
				userService.logout();
		};

		$scope.$watch(userService.getLoggedIn, function() {
			$scope.loggedIn = userService.getLoggedIn();
			$scope.user = userService.getUser();
			$scope.token = userService.getToken();
		});

		$scope.search = function() {
			var params = {};
			var paramExists = false;
			$scope.nutritionList.forEach(function(nutrition) {
				if($scope.mins[nutrition] !== undefined && $scope.mins[nutrition] >= 0) {
					params["min" + nutrition] = $scope.mins[nutrition];
					paramExists = true;
				}
				if($scope.maxs[nutrition] !== undefined && $scope.maxs[nutrition] >= 0) {
					params["max" + nutrition] = $scope.maxs[nutrition];
					paramExists = true;
				}
			});
			if($scope.includedIngredients.length > 0) {
				params['wantedIngredients'] = $scope.includedIngredients.join(',');
				paramExists = true;
			}
			if($scope.excludedIngredients.length > 0) {
				params['notWantedIngredients'] = $scope.excludedIngredients.join(',');
				paramExists = true;
			}
			if($scope.maxRating !== undefined) {
				params['maxRating'] = $scope.maxRating;
				paramExists = true;
			}
			if($scope.minRating !== undefined) {
				params['minRating'] = $scope.minRating;
				paramExists = true;
			}
			if($scope.period !== undefined && $scope.period != "none") {
				params['period'] = $scope.period;
				paramExists = true;
			}
			var stateParams = {query : $scope.searchText};
			if(paramExists) { 
				stateParams.params = JSON.stringify(params);
			}
			console.log("Search params " + JSON.stringify(params));
			$state.go('search', stateParams);
			$scope.isCollapsed = true;
		};

	});
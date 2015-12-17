myApp.controller('NavBarCtrl', function($scope, $http, $window, $state, userService, searchService) {
		$scope.loggedIn = false;
		$scope.isCollapsed = true;
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
			//searchService.search($scope.searchText,0,5);
			$state.go('search', {query : $scope.searchText});
		};

	});
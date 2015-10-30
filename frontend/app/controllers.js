angular.module('FoodApp.Controllers', []).controller('MainCtrl', function($scope, $http, $rootScope) {



	}).controller('RegisterCtrl', function($scope) {

	}).controller('NavBarCtrl', function($scope, $http, $window, $state, userService) {
		$scope.loggedIn = false;
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

	}).controller('ProfileCtrl', function() {

	}).controller('LoginCtrl', function($scope, $state, $stateParams, userService) {
		console.log("Incorrect : " + $stateParams.incorrect);
		$scope.incorrect = ($stateParams.incorrect == "incorrect");
		$scope.login= function() {
				userService.login($scope.emailLogin, $scope.passLogin);
		};
		$scope.$watch(userService.getLoggedIn, function() {
			if(userService.getLoggedIn()) {
				$state.go('main')
			}
		});
	});;
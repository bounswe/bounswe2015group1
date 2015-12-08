myApp.controller('LoginCtrl', function($scope, $state, $stateParams, userService) {
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
	});
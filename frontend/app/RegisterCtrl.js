myApp.controller('RegisterCtrl', function($scope, userService) {
		$scope.register= function(){
			userService.register($scope.nameRegister, $scope.emailRegister,
				$scope.passRegister, $scope.birthDateRegister, $scope.locationRegister, false);
		}
		$scope.registerAsRestaurant= function(){
			// 4102358400000 is like a default date for Restaurants as not to disturb user object.
			userService.register($scope.restNameRegister, $scope.restEmailRegister,
				$scope.resPassRegister, "4102358400000", $scope.resLocationRegister, true);
		}
	});
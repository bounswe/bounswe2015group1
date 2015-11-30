myApp.controller('RegisterCtrl', function($scope, userService) {
		$scope.register= function(){
			userService.register($scope.nameRegister, $scope.emailRegister,
				$scope.passRegister, $scope.birthDateRegister, $scope.locationRegister);
		}
		$scope.registerAsRestaurant= function(){
			userService.register($scope.restNameRegister, $scope.restEmailRegister,
				$scope.resPassRegister, "31.12.2099", $scope.resLocationRegister);
		}
	});
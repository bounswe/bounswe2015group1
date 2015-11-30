myApp.controller('RegisterCtrl', function($scope, userService) {
		$scope.register= function(){
			userService.register($scope.nameRegister, $scope.emailRegister,
				$scope.passRegister, $scope.birthDateRegister, $scope.locationRegister);
		}

	});
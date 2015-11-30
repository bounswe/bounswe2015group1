myApp.controller('ProfileCtrl', function($scope,userService) {
		var init = function() {
			console.log(JSON.stringify(userService.getUser()));
			$scope.userName = userService.getUser().fullName;
			$scope.address = userService.getUser().location;
			$scope.email = userService.getUser().email;
			$scope.birthDate=userService.getUser.dateOfBirth;

		}

		$scope.tabs= [{ title : "", }]

		init();

	});
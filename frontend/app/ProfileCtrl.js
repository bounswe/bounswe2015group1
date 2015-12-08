myApp.controller('ProfileCtrl', function($scope,userService,recipeService) {
		var init = function() {
			console.log(JSON.stringify(userService.getUser()));
			$scope.userName = userService.getUser().fullName;
			$scope.address = userService.getUser().location;
			$scope.email = userService.getUser().email;
			$scope.birthDate=userService.getUser.dateOfBirth;

			var usrId = userService.getUser().id;
			
			recipeService.getUserRecipes(usrId).then(function(response) {
				$scope.userRecipes = response.data;
				console.log(JSON.stringify($scope.userRecipes));
			});

		}

		$scope.tabs= [{ title : "", }]

		init();




	});
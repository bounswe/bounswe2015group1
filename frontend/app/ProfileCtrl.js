myApp.controller('ProfileCtrl', function($scope,userService,recipeService) {
		var init = function() {
			console.log(JSON.stringify(userService.getUser()));
			$scope.userName = userService.getUser().fullName;
			$scope.address = userService.getUser().location;
			$scope.email = userService.getUser().email;
			$scope.birthDate=userService.getUser().dateOfBirth;

			var usrId = userService.getUser().id;
			
			recipeService.getUserRecipes(usrId).then(function(response) {
				$scope.userRecipes = response.data;
				console.log(JSON.stringify($scope.userRecipes));
			});

			getAllergens();
		}

		$scope.onAllergenSelect = function($item, $model, $label) {
  			console.log("Current Allergen is " + JSON.stringify($item))
  			if(typeof $scope.newAllergen.fields != undefined) {
  			$http.get($rootScope.baseUrl + '/api/ingredient/item/' + $scope.newAllergen.fields.item_id).then(function(response){
	    			if(response.data.status_code == 404) {
	    				console.log("Ingredient not found");
	    			}
	    			else {
	    				$scope.newAllergenID = response.data;
	    			}
    			});
      		}
  		}

  		$scope.getCompletions = function(val) {
    		return $http.get($rootScope.baseUrl + '/api/ingredient/search/' + val + '*').then(function(response){
    			console.log("Autocomplete data: " + JSON.stringify(response.data.hits));
      			return response.data.hits;
      		});
  		};

		$scope.addAllergen = function() {
			userService.addAllergen($scope.newAllergenID).then(function(response) {
				getAllergens();
			});
		}

		var getAllergens = function () {
			userService.getAllergens().then(function(response) {
					var allergenIds = response.data;
					console.log("USER ALLERGENS: " + JSON.stringify(allergenIds));
				});
		}

		init();




	});
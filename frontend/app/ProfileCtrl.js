myApp.controller('ProfileCtrl', function($rootScope,$scope,$http,userService,recipeService) {
		var timestampToDate = function(timestamp){
		  var d = new Date(timestamp);
		  var year = d.getFullYear();
		  var month = d.getMonth() + 1;
		  var date = d.getDate();
		  return year + "-" + month + "-" + date;
		}

		var init = function() {
			console.log(JSON.stringify(userService.getUser()));
			$scope.nameProfile = userService.getUser().fullName;
			$scope.emailProfile = userService.getUser().email;
			$scope.birthDateProfile = timestampToDate(userService.getUser().dateOfBirth);
			$scope.locationProfile = userService.getUser().location;
			$scope.isRestaurantProfile = userService.getUser().isRestaurant;

			var usrId = userService.getUser().id;
			
			recipeService.getUserRecipes(usrId).then(function(response) {
				$scope.userRecipes = response.data;
				console.log(JSON.stringify($scope.userRecipes));
			});

			recipeService.getConsumedRecipes(usrId).then(function(response) {
				$scope.consumedRecipes = response.data;
			});

			recipeService.getAvgConsumption(usrId).then(function(response) {
				$scope.avgNutrition = response.data;
				console.log("AVG Consumption " +JSON.stringify($scope.avgNutrition));
			});

			getAllergens();
		}

		$scope.nutritionLiterals = ['calories', 'carbohydrate', 'fats', 'proteins', 'sodium', 'fiber', 'cholesterol', 'sugars', 'iron'];
		$scope.nutritionUnits = ['cal', 'g', 'g', 'g', 'mg', 'g', 'mg', 'g', '%'];

		$scope.onAllergenSelect = function($item, $model, $label) {
  			console.log("Current Allergen is " + JSON.stringify($item))
  			$http.get($rootScope.baseUrl + '/api/ingredient/item/' + $item.fields.item_id).then(function(response){
	    			if(response.data.status_code == 404) {
	    				console.log("Ingredient not found");
	    			}
	    			else {
	    				console.log("Allergen Data : " + JSON.stringify(response.data));
	    				$scope.newAllergenData = response.data;
	    			}
    			});
  		}

  		$scope.getCompletions = function(val) {
    		return $http.get($rootScope.baseUrl + '/api/ingredient/search/' + val + '*').then(function(response){
    			console.log("Autocomplete data: " + JSON.stringify(response.data.hits));
      			return response.data.hits;
      		});
  		};

		$scope.addAllergen = function() {
			userService.addAllergen($scope.newAllergenData.item_id).then(function(response) {
				getAllergens();
			});
		}

		var getAllergens = function () {
			userService.getAllergens().then(function(response) {
					$scope.allergens = [];
					var allergenIds = response.data;
					for(var i=0; i < allergenIds.length; i++) {
						$http.get($rootScope.baseUrl + '/api/ingredient/item/' + allergenIds[i].ingredientId).then(function(response){
	    					if(response.data.status_code == 404) {
	    						console.log("Ingredient not found");
	    					}
	    					else {
	    						console.log("Allergen Data : " + JSON.stringify(response.data));
	    						$scope.allergens.push(response.data);
	    					}
    					});
					}
					console.log("USER ALLERGENS: " + JSON.stringify(allergenIds));
				});
		}

		$scope.updateProfile = function(){
			console.log($scope.nameProfile);
			console.log($scope.emailProfile);
			console.log($scope.passProfile);
			console.log($scope.birthDateProfile);
			console.log($scope.locationProfile);
			
			userService.update(userService.getUser().id, $scope.nameProfile, $scope.emailProfile, 
				$scope.passProfile, $scope.birthDateProfile,
				$scope.locationProfile, userService.getUser().isRestaurant);
		}

		init();




	});
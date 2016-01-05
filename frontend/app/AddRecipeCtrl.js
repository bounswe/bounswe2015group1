myApp.controller('AddRecipeCtrl', function($rootScope, $scope, $http, $stateParams, recipeService) {
			$scope.ingredients=[];
			$scope.ingredientUnits = [];
			$scope.tags = [];
			$scope.isEmpty = true;
			$scope.editMode = false;
			var makeid =function() {
    			var text = "";
    			var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

			    for( var i=0; i < 8; i++ )
        			text += possible.charAt(Math.floor(Math.random() * possible.length));

    			return text;	
			};

			$scope.addRecipe = function() {
				var nutrition = { "calories" : 0, "carbohydrate" : 0, "fats" : 0, "proteins" : 0, "sodium" : 0, "fiber" : 0, "cholesterol" : 0, "sugars" : 0, "iron" : 0};
				for(var i=0 ; i<$scope.ingredients.length; i++) {
					var ing = $scope.ingredients[i];
					//var multiplier = ing.amount / ing.ingredient.nf_serving_size_qty;
					// $scope.tags = $scope.tags.concat(ing.tags);

					//DEBUG
					/*console.log("Cal: " +ing.ingredient.nf_calories );
					console.log("Carbo" +  ing.ingredient.nf_total_carbohydrate);
					console.log("Fat " + ing.ingredient.nf_total_fat) ;
					console.log("Protein " +  ing.ingredient.nf_protein );
					console.log("Sodium " +  ing.ingredient.nf_sodium) ;
					console.log("Fiber " + ing.ingredient.nf_dietary_fiber);
					console.log("Cholest " + ing.ingredient.nf_cholesterol);
					console.log("Sugars " +  ing.ingredient.nf_sugars);
					console.log("Iron " + ing.ingredient.nf_iron_dv);*/
					//NODEBUG

					/*nutrition.calories += ing.ingredient.nf_calories  * multiplier;
					nutrition.carbohydrate = ing.ingredient.nf_total_carbohydrate * multiplier;
					nutrition.fats = ing.ingredient.nf_total_fat * multiplier;
					nutrition.proteins = ing.ingredient.nf_protein * multiplier;
					nutrition.sodium = ing.ingredient.nf_sodium * multiplier;
					nutrition.fiber = ing.ingredient.nf_dietary_fiber * multiplier;
					nutrition.cholesterol = ing.ingredient.nf_cholesterol * multiplier;
					nutrition.sugars = ing.ingredient.nf_sugars * multiplier;
					nutrition.iron = ing.ingredient.nf_iron_dv * multiplier;*/
					nutrition.calories += ing.nutritions.calories * ing.amount;
					nutrition.carbohydrate += ing.nutritions.carbohydrate * ing.amount;
					nutrition.fats += ing.nutritions.fats * ing.amount;
					nutrition.proteins += ing.nutritions.proteins * ing.amount;
					nutrition.sodium += ing.nutritions.sodium * ing.amount;
					nutrition.fiber += ing.nutritions.fiber * ing.amount;
					nutrition.cholesterol += ing.nutritions.cholesterol * ing.amount;
					nutrition.sugars += ing.nutritions.sugars * ing.amount;
					nutrition.iron += ing.nutritions.iron * ing.amount;

					// Removing nutritions attribute of each ingredient since it is not required on API.
					delete ing.nutritions;
				}

				console.log("TAGS: " + JSON.stringify($scope.tags));
				console.log("NUTRITIONS: " + JSON.stringify(nutrition));
				if($scope.editMode) {
					recipeService.updateRecipe($scope.recipeId,$scope.recipeName, $scope.ingredients, $scope.recipeDesc, $scope.tags, nutrition);
				}
				else {
					 recipeService.addRecipe($scope.recipeName, $scope.ingredients, $scope.recipeDesc, $scope.tags, nutrition);
				}
				$scope.tags = "";
				$scope.ingredients=[];
				$scope.newIngredientName = "";
				$scope.newIngredientAmount = "";
				$scope.newIngredientFullData = null;
				$scope.recipeName = "";
				$scope.recipeDesc = "";
				$scope.userTags = "";
			};
			$scope.addIngredient = function() {
				console.log("CURR INGREDIENT: " + JSON.stringify($scope.newIngredientFullData));
				var nutrition = { "calories" : 0, "carbohydrate" : 0, "fats" : 0, "proteins" : 0, "sodium" : 0, "fiber" : 0, "cholesterol" : 0, "sugars" : 0, "iron" : 0};
				var ing = $scope.newIngredientFullData;
				var amount = parseInt($scope.newIngredientAmount);
				var multiplier = amount ; // / ing.nf_serving_size_qty;

				nutrition.calories = ing.nf_calories;
				nutrition.carbohydrate = ing.nf_total_carbohydrate;
				nutrition.fats = ing.nf_total_fat;
				nutrition.proteins = ing.nf_protein;
				nutrition.sodium = ing.nf_sodium;
				nutrition.fiber = ing.nf_dietary_fiber;
				nutrition.cholesterol = ing.nf_cholesterol;
				nutrition.sugars = ing.nf_sugars;
				nutrition.iron = ing.nf_iron_dv;

				var ingredient = {"ingredientId" : $scope.newIngredientFullData.item_id , "name" : $scope.newIngredientFullData.item_name, "amount": amount, "nutritions" : nutrition }
				//var ing = { "ingredient" : $scope.newIngredientFullData, "amount": parseInt($scope.newIngredientAmount), "tags": $scope.newIngredientFullData.item_name.split()};
				$scope.ingredients.push(ingredient);
				$scope.ingredientUnits.push(ing.nf_serving_size_unit);
				//$scope.taglist.concat();
				console.log("INGREDIENTS: " + JSON.stringify($scope.ingredients));
				$scope.newIngredientName = "";
				$scope.newIngredientAmount = "";
				$scope.newIngredientFullData = null;
			};

			$scope.removeIngredient = function(pos) {
				$scope.ingredients.splice(pos,1);
				$scope.ingredientUnits.splice(pos,1);
			};

			$scope.$watch(recipeService.getAddStatus, function() {
				$scope.ingredients = [];
				console.log('Add Recipe Successful');
			});

			$scope.getCompletions = function(val) {
    			return $http.get($rootScope.baseUrl + '/api/ingredient/search/' + val + '*').then(function(response){
    				console.log("Autocomplete data: " + JSON.stringify(response.data.hits));
      				return response.data.hits;
      			});
  			};

  			$scope.onIngredientSelect = function($item, $model, $label) {
  				console.log("Current Ingredient is " + JSON.stringify($item))
  				if(typeof $scope.newIngredientName.fields != undefined) {
  				$http.get($rootScope.baseUrl + '/api/ingredient/item/' + $scope.newIngredientName.fields.item_id).then(function(response){
	    				if(response.data.status_code == 404) {
	    					console.log("Ingredient not found");
	    				}
	    				else {
	    					$scope.newIngredientFullData = response.data;
	    				}
    				});
      			}
  			}
  			

  			var init = function() {
  				console.log("STATE PARAMS ADD RECIPE " + $stateParams.editID)
  				if($stateParams.editID !== null && $stateParams.editID != '' && typeof $stateParams.editID != 'undefined' ) {
  					$scope.recipeId = parseInt($stateParams.editID);
  					$scope.editMode = true;
  					console.log("RECIPE EDIT MODE id: " + $scope.recipeId);
  					recipeService.getRecipeWithID($scope.recipeId).then(function(response) {
  						$scope.ingredients = response.data.ingredients;
  						$scope.recipeName = response.data.name;
  						$scope.recipeDesc = response.data.description
  						$scope.recipeId = response.data.id;
  						$scope.tags = response.data.tags;
  						for(var i=0; i < $scope.ingredients.length; i++) {
  							$http.get($rootScope.baseUrl + '/api/ingredient/item/' + $scope.ingredients[i].ingredientId).then(function(response){
	    						if(response.data.status_code == 404) {
	    							console.log("Ingredient not found");
	    						}
	    						else {
	    							console.log("Ingredient Unit Fetched")
	    							for(var ind=0; ind < $scope.ingredients.length; ind++) {
	    								if($scope.ingredients[ind].ingredientId == response.data.item_id) {
	    									var ing = $scope.ingredients[ind];
	    									$scope.ingredientUnits[ind] = response.data.nf_serving_size_unit; 
    										var nutrition = { "calories" : 0, "carbohydrate" : 0, "fats" : 0, "proteins" : 0, "sodium" : 0, "fiber" : 0, "cholesterol" : 0, "sugars" : 0, "iron" : 0};
											nutrition.calories = ing.nf_calories;
											nutrition.carbohydrate = ing.nf_total_carbohydrate;
											nutrition.fats = ing.nf_total_fat;
											nutrition.proteins = ing.nf_protein;
											nutrition.sodium = ing.nf_sodium;
											nutrition.fiber = ing.nf_dietary_fiber;
											nutrition.cholesterol = ing.nf_cholesterol;
											nutrition.sugars = ing.nf_sugars;
											nutrition.iron = ing.nf_iron_dv;
											$scope.ingredients[ind].nutrition = nutrition;
	    								}
	    							}
	    							console.log("Ingredient Units " + JSON.stringify($scope.ingredientUnits))
	    						}
    						});
  						}
      				});

  				}
  			}

  			init();
	});
myApp.controller('AddRecipeCtrl', function($rootScope, $scope, $http, recipeService) {
			$scope.ingredients=[];
			$scope.taglist = [];
			$scope.userTags = ""
			$scope.isEmpty = true;
			var makeid =function() {
    			var text = "";
    			var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

			    for( var i=0; i < 8; i++ )
        			text += possible.charAt(Math.floor(Math.random() * possible.length));

    			return text;	
			};

			$scope.addRecipe = function() {
				var nutrition = { "calories" : 0, "carbohydrate" : 0, "fats" : 0, "proteins" : 0, "sodium" : 0, "fiber" : 0, "cholesterol" : 0, "sugars" : 0, "iron" : 0};
				$scope.taglist = [];
				for(var i=0 ; i<$scope.ingredients.length; i++) {
					var ing = $scope.ingredients[i];
					var multiplier = ing.amount / ing.ingredient.nf_serving_size_qty;
					$scope.taglist = $scope.taglist.concat(ing.tags);

					//DEBUG
					console.log("Cal: " +ing.ingredient.nf_calories );
					console.log("Carbo" +  ing.ingredient.nf_total_carbohydrate);
					console.log("Fat " + ing.ingredient.nf_total_fat) ;
					console.log("Protein " +  ing.ingredient.nf_protein );
					console.log("Sodium " +  ing.ingredient.nf_sodium) ;
					console.log("Fiber " + ing.ingredient.nf_dietary_fiber);
					console.log("Cholest " + ing.ingredient.nf_cholesterol);
					console.log("Sugars " +  ing.ingredient.nf_sugars);
					console.log("Iron " + ing.ingredient.nf_iron_dv);
					//NODEBUG

					nutrition.calories += ing.ingredient.nf_calories  * multiplier;
					nutrition.carbohydrate = ing.ingredient.nf_total_carbohydrate * multiplier;
					nutrition.fats = ing.ingredient.nf_total_fat * multiplier;
					nutrition.proteins = ing.ingredient.nf_protein * multiplier;
					nutrition.sodium = ing.ingredient.nf_sodium * multiplier;
					nutrition.fiber = ing.ingredient.nf_dietary_fiber * multiplier;
					nutrition.cholesterol = ing.ingredient.nf_cholesterol * multiplier;
					nutrition.sugars = ing.ingredient.nf_sugars * multiplier;
					nutrition.iron = ing.ingredient.nf_iron_dv * multiplier;
				}
				if($scope.userTags != "")
					$scope.taglist = $scope.taglist.concat($scope.userTags.split());
				console.log("TAGS: " + JSON.stringify($scope.taglist));
				console.log("NUTRITIONS: " + JSON.stringify(nutrition));
				recipeService.addRecipe($scope.recipeName, $scope.ingredients, $scope.recipeDesc, $scope.taglist, nutrition);
				$scope.tags = "";
				$scope.ingredients=[];
				$scope.newIngredientName = "";
				$scope.newIngredientAmount = "";
				$scope.newIngredientFullData = null;
			};
			$scope.addIngredient = function() {
				var ing = { "ingredient" : $scope.newIngredientFullData, "amount": parseInt($scope.newIngredientAmount), "tags": $scope.newIngredientFullData.item_name.split()};
				$scope.ingredients.push(ing);
				//$scope.taglist.concat();
				console.log(JSON.stringify($scope.ingredients));
				$scope.newIngredientName = "";
				$scope.newIngredientAmount = "";
				$scope.newIngredientFullData = null;
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

	});
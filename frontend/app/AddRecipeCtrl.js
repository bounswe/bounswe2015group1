myApp.controller('AddRecipeCtrl', function($scope, $http, recipeService) {
			$scope.ingredients=[];
			$scope.taglist = [];
			$scope.isEmpty = true;
			var makeid =function() {
    			var text = "";
    			var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

			    for( var i=0; i < 8; i++ )
        			text += possible.charAt(Math.floor(Math.random() * possible.length));

    			return text;	
			};

			$scope.addRecipe = function() {
				$scope.taglist = $scope.taglist.concat($scope.userTags.split());
				console.log("TAGS: " + JSON.stringify($scope.taglist));
				recipeService.addRecipe($scope.recipeName, $scope.ingredients, $scope.recipeDesc, taglist);
			};
			$scope.addIngredient = function() {
				var ing = { "ingredientId": makeid(), "name": $scope.newIngredientName, "amount": parseInt($scope.newIngredientAmount), "unit": $scope.newIngredientUnit};
				$scope.ingredients.push(ing);
				$scope.taglist.push($scope.newIngredientName);
				console.log(JSON.stringify($scope.ingredients));
			};

			$scope.$watch(recipeService.getAddStatus, function() {
				$scope.ingredients = [];
				console.log('Add Recipe Successful');
			});

			$scope.getLocation = function(val) {
    			return $http.get('http://ec2-52-89-168-70.us-west-2.compute.amazonaws.com:8080/api/ingredient/autocomplete/' + val + '*').then(function(response){
      				return response.data;
      			});
  			};

	});
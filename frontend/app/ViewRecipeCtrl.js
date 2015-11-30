myApp.controller('ViewRecipeCtrl', function($scope, $rootScope, $state, $stateParams, recipeService) {
		$scope.recipe =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		console.log("Recipe view: " + JSON.stringify($scope.recipe));

		$scope.back = function() {
			$state.go($rootScope.previousState, $rootScope.previousParams);
		}
	});
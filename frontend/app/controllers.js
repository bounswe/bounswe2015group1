var myApp = angular.module('FoodApp.Controllers', []);

myApp.controller('MainCtrl', function($scope, $state, $http, $rootScope, recipeService) {
				$scope.recipes = [];

		var init = function() {
			recipeService.fetchAllRecipes().then(function(response) {
				$scope.recipes = response.data;
				console.log(JSON.stringify($scope.recipes));
			});
		};

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};



		/*$scope.$watch(recipeService.getRecipes, function() {
				$scope.recipes = recipeService.getRecipes();
				console.log('Recipes Fetched');
		});*/

		init();

	});
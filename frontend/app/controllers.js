var myApp = angular.module('FoodApp.Controllers', []);

myApp.controller('MainCtrl', function($scope, $state, $http, $rootScope, recipeService) {
		var init = function() {
			recipeService.fetchAllRecipes();
		};

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.recipes = [];

		$scope.$watch(recipeService.getRecipes, function() {
				$scope.recipes = recipeService.getRecipes();
				console.log('Recipes Fetched');
		});

		init();

	});
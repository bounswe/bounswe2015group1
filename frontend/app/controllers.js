var myApp = angular.module('FoodApp.Controllers', []);

myApp.controller('MainCtrl', function($scope, $state, $http, $rootScope, recipeService, menuService) {
				$scope.recipes = [];

		var init = function() {
			recipeService.getAllRecipes().then(function(response) {
				$scope.recipes = response.data;
				//console.log(JSON.stringify($scope.recipes));
			});
			menuService.fetchAllMenus().then(function(response) {
				$scope.menus = response.data;
				//console.log(JSON.stringify($scope.recipes));
			});
		};

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.viewMenu = function(id) {
			$state.go('viewMenu', { menuID : id});
		};


		/*$scope.$watch(recipeService.getRecipes, function() {
				$scope.recipes = recipeService.getRecipes();
				console.log('Recipes Fetched');
		});*/

		init();

	});
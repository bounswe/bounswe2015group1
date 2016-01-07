var myApp = angular.module('FoodApp.Controllers', []);

myApp.controller('MainCtrl', function($scope, $state, $http, $rootScope, recipeService, menuService) {
				$scope.recipes = [];
				$scope.menus = [];
		var init = function() {
			recipeService.getAllRecipes().then(function(response) {
				var allRecipes = response.data;
				allRecipes.sort( function() { return 0.5 - Math.random() } );
				var recipeLength = allRecipes.length < 9 ? allRecipes.length : 9;
				for(var i=0; i <recipeLength; i++) {
					$scope.recipes.push(allRecipes[i])
				}
			});
			menuService.fetchAllMenus().then(function(response) {
				var allMenus = response.data;
				allMenus.sort( function() { return 0.5 - Math.random() } );
				var menuLength = allMenus.length < 9 ? allMenus.length : 9;
				for(var i=0; i < menuLength; i++) {
					$scope.menus.push(allMenus[i])
				}
			});
		};

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.viewMenu = function(id) {
			$state.go('viewMenu', { menuID : id});
		};

		init();

	});
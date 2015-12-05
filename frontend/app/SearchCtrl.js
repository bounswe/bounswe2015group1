myApp.controller('SearchCtrl', function($scope, $rootScope, $state, $stateParams, searchService) {
		$scope.recipeResults = [];
		$scope.menuResults = [];
		$scope.maxSize = 10;
		$scope.recipesCurrentPage = 1; 
		$scope.menusCurrentPage = 1;
		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.search = function(query) {
			searchService.recipeSearch(query).then(function(response) {
				$scope.recipeResults = response.data;
			});
			searchService.menuSearch(query).then(function(response) {
				$scope.menuResults = response.data;
			});
		}

		$scope.search($stateParams.query);
		/*$scope.$watch(searchService.getResults, function() {
				$scope.results = searchService.getResults();
				console.log('Results Fetched');
		});*/
	});
myApp.controller('SearchCtrl', function($scope, $rootScope, $state, $stateParams, searchService) {
		$scope.results = searchService.getResults();

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.$watch(searchService.getResults, function() {
				$scope.results = searchService.getResults();
				console.log('Results Fetched');
		});
	});
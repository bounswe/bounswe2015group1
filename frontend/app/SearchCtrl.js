myApp.controller('SearchCtrl', function($scope, $rootScope, $state, $stateParams, searchService) {
		$scope.recipeResults = [];
		$scope.menuResults = [];
		$scope.maxSize = 10;
		$scope.recipesCurrentPage = 1; 
		$scope.menusCurrentPage = 1;
		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.viewMenu = function(id) {
			$state.go('viewMenu', { menuID : id});
		};

		$scope.search = function(query) {
			$scope.recipeResults = [];
			$scope.menuResults = [];
			searchService.recipeSearch(query).then(function(response) {
				if(response.status == 200)
					$scope.recipeResults = response.data;
			});
			searchService.menuSearch(query).then(function(response) {
				console.log("SearchCtrl Menu Results: " + JSON.stringify(response.data));
				if(response.status == 200)
					$scope.menuResults = response.data;
			});
		}

		$scope.advancedSearch = function(query, params) {
			$scope.recipeResults = [];
			$scope.menuResults = [];
			searchService.recipeAdvancedSearch(query, params).then(function(response) {
				if(response.status == 200)
					$scope.recipeResults = response.data;
			});
			searchService.menuAdvancedSearch(query, params).then(function(response) {
				console.log("SearchCtrl Menu Results: " + JSON.stringify(response.data));
				if(response.status == 200)
					$scope.menuResults = response.data;
			});
		}
		
		/*$scope.$watch(searchService.getResults, function() {
				$scope.results = searchService.getResults();
				console.log('Results Fetched');
		});*/
		var init = function() {
			if($stateParams.params !== null && $stateParams.params != '' && typeof $stateParams.params != 'undefined' ) {
				$scope.advancedSearch($stateParams.query, JSON.parse($stateParams.params));
			}
			else {
				$scope.search($stateParams.query);
			}
		}

		init();
	});
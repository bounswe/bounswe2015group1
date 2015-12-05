myApp.controller('AddMenuCtrl', function($rootScope, $scope, $http, userService, recipeService, menuService) {
			$scope.recipes=[];
			$scope.selectedRecipes =[];
			$scope.taglist = [];
			$scope.isEmpty = true;
			$scope.period = "";
			$scope.recipesToBeAdded = [];
			$scope.recipesToBeRemoved = [];
			$scope.addMenu = function() {
				var recipeIds = [];
				for(var i=0; i < $scope.selectedRecipes.length; i++) {
					recipeIds.push($scope.selectedRecipes[i].id);
				}
				console.log("Selected recipe ids: " + JSON.stringify(recipeIds));
				menuService.addMenu($scope.menuName, recipeIds, $scope.period, $scope.menuDesc);
			};



			$scope.addRecipes = function() {
				$scope.recipes = $scope.recipes.filter(function(current){
    				return $scope.recipesToBeAdded.filter(function(current_b){
        				return current_b.id == current.id
    				}).length == 0
				});

				$scope.selectedRecipes = $scope.selectedRecipes.concat($scope.recipesToBeAdded);
			}

			$scope.removeRecipes = function() {
				$scope.selectedRecipes = $scope.selectedRecipes.filter(function(current){
    				return $scope.recipesToBeRemoved.filter(function(current_b){
        				return current_b.id == current.id
    				}).length == 0
				});

				$scope.recipes = $scope.recipes.concat($scope.recipesToBeRemoved);
			}
			$scope.getRecipes = function(val) {
				// API CALL
    			/*return $http.get($rootScope.baseUrl + '/api/recipe/user/' + userService.getUser().id).then(function(response){
      				$scope.userRecipes = response.data;
      			});*/
				recipeService.fetchAllRecipes().then(function(response){
					$scope.recipes = response.data;
				})
  			};

  			$scope.getRecipes();
	});
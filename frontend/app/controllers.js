angular.module('FoodApp.Controllers', []).controller('MainCtrl', function($scope, $state, $http, $rootScope, recipeService) {
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

	}).controller('RegisterCtrl', function($scope, userService) {
		$scope.register= function(){
			userService.register($scope.nameRegister, $scope.emailRegister,
				$scope.passRegister, $scope.birthDateRegister, $scope.locationRegister);
		}

	}).controller('NavBarCtrl', function($scope, $http, $window, $state, userService, searchService) {
		$scope.loggedIn = false;
		$scope.login= function() {
				userService.login($scope.emailLogin, $scope.passLogin);
		};

		$scope.logout= function() {
				userService.logout();
		};

		$scope.$watch(userService.getLoggedIn, function() {
			$scope.loggedIn = userService.getLoggedIn();
			$scope.user = userService.getUser();
			$scope.token = userService.getToken();
		});

		$scope.search = function() {
			searchService.search($scope.searchText,0,5);
			$state.go('search');
		};

	}).controller('ProfileCtrl', function($scope,userService) {
		var init = function() {
			console.log(JSON.stringify(userService.getUser()));
			$scope.userName = userService.getUser().fullName;
			$scope.address = userService.getUser().location;
			$scope.email = userService.getUser().email;
			$scope.birthDate=userService.getUser.dateOfBirth;

		}

		$scope.tabs= [{ title : "", }]

		init();

	}).controller('LoginCtrl', function($scope, $state, $stateParams, userService) {
		console.log("Incorrect : " + $stateParams.incorrect);
		$scope.incorrect = ($stateParams.incorrect == "incorrect");
		$scope.login= function() {
				userService.login($scope.emailLogin, $scope.passLogin);
		};
		$scope.$watch(userService.getLoggedIn, function() {
			if(userService.getLoggedIn()) {
				$state.go('main')
			}
		});
	}).controller('AddRecipeCtrl', function($scope, recipeService) {
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

	}).controller('ViewRecipeCtrl', function($scope, $rootScope, $state, $stateParams, recipeService) {
		$scope.recipe =  recipeService.getRecipeWithID(parseInt($stateParams.recipeID));
		console.log("Recipe view: " + JSON.stringify($scope.recipe));

		$scope.back = function() {
			$state.go($rootScope.previousState, $rootScope.previousParams);
		}
	}).controller('SearchCtrl', function($scope, $rootScope, $state, $stateParams, searchService) {
		$scope.results = searchService.getResults();

		$scope.viewRecipe = function(id) {
			$state.go('viewRecipe', { recipeID : id});
		};

		$scope.$watch(searchService.getResults, function() {
				$scope.results = searchService.getResults();
				console.log('Results Fetched');
		});
	});
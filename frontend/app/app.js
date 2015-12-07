angular.module('FoodApp', ['ui.bootstrap', 'ui.router', 'FoodApp.Controllers']);

angular.module('FoodApp').config(function($stateProvider, $urlRouterProvider, $locationProvider) {
	$stateProvider.state('main', {
		url : '/',
		controller : 'MainCtrl',
		templateUrl : '/views/main.html'
	}).state('register', {
		url : '/register',
		controller : 'RegisterCtrl',
		templateUrl : '/views/register.html'
	}).state('profile', {
		url : '/profile',
		controller : 'ProfileCtrl',
		templateUrl : '/views/profile.html'
	}).state('login', {
		url : '/login/:incorrect',
		controller : 'LoginCtrl',
		templateUrl : '/views/login.html'
	}).state('addRecipe', {
		url : '/addRecipe',
		controller : 'AddRecipeCtrl',
		templateUrl : '/views/addRecipe.html'
	}).state('addMenu', {
		url : '/addMenu',
		controller : 'AddMenuCtrl',
		templateUrl : '/views/addMenu.html'
	}).state('viewRecipe', {
		url : '/viewRecipe/:recipeID',
		controller : 'ViewRecipeCtrl',
		templateUrl : '/views/viewRecipe.html'
	}).state('viewMenu', {
		url : '/viewMenu/:menuID',
		controller : 'ViewMenuCtrl',
		templateUrl : '/views/viewMenu.html'
	}).state('search', {
		url : '/search/:query',
		controller : 'SearchCtrl',
		templateUrl : '/views/search.html'
	});
	$urlRouterProvider.otherwise('/');
});

angular.module('FoodApp').run(function($rootScope) {
		$rootScope.saveTheDay = false;
		$rootScope.saveTheDelay = 10;
		$rootScope.baseUrl = "http://ec2-52-89-168-70.us-west-2.compute.amazonaws.com:8080";
		$rootScope.$on('$stateChangeSuccess', function(ev, to, toParams, from, fromParams) {
   			$rootScope.previousState = from.name;
    		$rootScope.previousParams = fromParams;
    		console.log('Previous state:'+$rootScope.previousState);
    		console.log('Previous Params state:'+ JSON.stringify($rootScope.previousParams));
		});
});

angular.module('FoodApp').factory('userService', function($http, $window, $state, $rootScope) {
	var user = null;
	var token = null;
	var loggedIn = false;
	var logout = function() {
			var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/session/logout',
			 headers: {
			   'Authorization': 'Bearer ' + token.accessToken 
			 }
			}
			console.log('auth: ' + token.accessToken )
			$http(req).then(function(response){
				unauth();
			}, function(response){
				console.log('RESPONSE STATUS: ' + response.status);
				if(response.status==401) {
					unauth();
				}
			});

		};
	var login = function(email, pass) {
			var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/session/login',
			 headers: {
			   'Content-Type': 'application/json'
			 },
			 data: { "email" :  email,
			 		 "password" : pass }
			};
			$http(req).then(function(response){
				console.log("Status: " + response.status);
					token = response.data;
					console.log("Serialized Token: " + JSON.stringify(token) );
					$window.sessionStorage.token = JSON.stringify(token);
					$http.get($rootScope.baseUrl + '/api/user/'+ token.userId).then(function(response) {
						user = response.data;
						loggedIn = true;
					});
			}, function(){
				delete $window.sessionStorage.token;
				loggedIn = false;
				$state.go('login', { incorrect : "incorrect"});
			});
		};

	var unauth = function() {
			user = null;
			token = null;
			delete $window.sessionStorage.token;
			loggedIn = false;
			$window.location.href = "/";
	};
	var register = function (fullName, email, password, dateOfBirth, location, isRestaurant) {
		var req = {
		 method : 'POST',
		 url : $rootScope.baseUrl + '/api/user',
		 headers: {
		    'Content-Type' : 'application/json'	
		 },
		 data: {
		 	"email" : email,
		 	"password" : password,
		 	"fullName" : fullName,
		 	"location" : location,
		 	"dateOfBirth" : dateOfBirth,
		 	"isRestaurant" : isRestaurant
		 }
		};
		console.log(email + " " + password + " " + fullName + " " + location + " " + dateOfBirth);
		$http(req).then(function(response){
			console.log("Register Success");
			console.log(JSON.stringify(response.data));
			alert("You have registered successfully...");
			$window.location.href = "/";

		}, function(){
			console.log("Register Error");
			alert("An error occured while registering. Try again...");
			$window.location.href = "/register";
		});

	};

	if($window.sessionStorage.token) {
		console.log("Session Token: " + $window.sessionStorage.token);
		token = JSON.parse($window.sessionStorage.token);
		$http.get($rootScope.baseUrl + '/api/user/'+ token.userId).then(function(response) {
			user = response.data;
			loggedIn = true;
		});
	}

	return {
		login : login,
		logout : logout,
		register: register,
		getUser : function() {
			return user;
		},
		getToken : function() {
			return token;
		},
		getLoggedIn : function() {
			return loggedIn;
		}
	}
});

angular.module('FoodApp').factory('recipeService', function($http, $rootScope, $q, userService, standinDB) {
	var recipes = [];
	var recipeAddStatus = 0;
	var recipe = null;
	var addRecipe = function(name,ingredients,desc,tags, nutrition) {
			if($rootScope.saveTheDay) {
				return $q(function(resolve,reject) {
					setTimeout(function() {
						resolve({"data" : standinDB.addRecipe(userService.getUser().id,name,ingredients,desc,tags,nutrition)});
					},$rootScope.saveTheDelay)
				});
			}
			var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/recipe',
			 headers: {
			   'Authorization': 'Bearer ' + userService.getToken().accessToken,
			   'Content-Type': 'application/json'
			 },
			 data : { "name" : name, "ingredients" : ingredients, "description": desc, "tags" : tags , "nutritions" : nutrition}
			};
			console.log("Recipe Obj: " + JSON.stringify({ "name" : name, "ingredients" : ingredients, "description": desc, "tags" : tags, "nutritions" : nutrition }));
			console.log('auth: ' + userService.getToken().accessToken );
			$http(req).then(function(response){
				recipeAddStatus = 200;
				console.log("Recipe Added");
			}, function(response){
				console.log('RESPONSE STATUS: ' + response.status);
				if(response.status==401) {
					unauth();
				}
			});

		};
	var fetchAllRecipes = function() {
		// DELETE WHEN API IS READY
		$http.get($rootScope.baseUrl + '/api/recipe/all').then(function(response) {
				recipes = response.data;
				//console.log(JSON.stringify(recipes));
		});
		return $http.get($rootScope.baseUrl + '/api/recipe/all');
	};
	var getUserRecipes = function(id) {
		if($rootScope.saveTheDay) {
				return $q(function(resolve,reject) {
					setTimeout(function() {
						resolve({"data" : standinDB.getUserRecipes(id)});
					},$rootScope.saveTheDelay)
				});
		}
		else return $http.get($rootScope.baseUrl + '/api/recipe/user/' + id);
	}
	return {
		addRecipe : addRecipe,
		fetchAllRecipes : fetchAllRecipes,
		getUserRecipes : getUserRecipes,
		getRecipes : function() {
			return recipes;
		},
		getAddStatus : function() {
			if(recipeAddStatus != 0) {
				var temp = recipeAddStatus;
				recipeAddStatus = 0;
				return temp;
			} else return 0;
		},
		getRecipeWithID : function(id) {
			if($rootScope.saveTheDay) {
				return $q(function(resolve,reject) {
					setTimeout(function() {
						resolve({"data" : standinDB.getRecipeWithID(id)});
					},$rootScope.saveTheDelay)
				});
			}
			else return $http.get($rootScope.baseUrl + '/api/recipe/view/' + id);
		},
		getRecipeWithIDNew : function(id) {
			return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
	};
});


angular.module('FoodApp').factory('menuService', function($http, $rootScope, $q,  userService, standinDB) {
	var addMenu = function(name,recipeIds,period,desc) {
			if($rootScope.saveTheDay) {
				return $q(function(resolve,reject) {
					setTimeout(function() {
						resolve({"data" : standinDB.addMenu(userService.getUser().id,name,recipeIds,period,desc)});
					},$rootScope.saveTheDelay)
				});
			}
			var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/menu',
			 headers: {
			   'Authorization': 'Bearer ' + userService.getToken().accessToken,
			   'Content-Type': 'application/json'
			 },
			 data : { "name" : name, "recipeIds" : recipeIds, "period" : period , "description": desc }
			};
			return $http(req);

		};
	var fetchAllMenus = function() {
		if($rootScope.saveTheDay) {
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : standinDB.getMenus()});
				},$rootScope.saveTheDelay)
			});
		}
		else return $http.get($rootScope.baseUrl + '/api/menu');
	};

	var	getMenuWithID = function(id) {
		if($rootScope.saveTheDay) {
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : standinDB.getMenuWithID(id)});
				},$rootScope.saveTheDelay)
			});
		}
		else return $http.get($rootScope.baseUrl + '/api/menu/' + id);
	}
	return {
		addMenu : addMenu,
		fetchAllMenus : fetchAllMenus,
		getMenuWithID : getMenuWithID
	};
});


angular.module('FoodApp').factory('searchService', function($http, $rootScope, $q, userService, standinDB) {
	/*var recipeResults = [];
	var menuResults = [];
	var lastRecipeQuery = '';
	var lastMenuQuery = '';
	var tags = [];*/
	var recipeSearch = function(query) {
		if($rootScope.saveTheDay) {
			var recipes = standinDB.searchRecipes(query);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : recipes});
				},$rootScope.saveTheDelay)
			});
			//return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
		else return $http.get($rootScope.baseUrl + '/api/search/recipe/' + query);
		//return $http.get($rootScope.baseUrl + '/api/recipe/all');
	};

	var menuSearch = function(query) {
		if($rootScope.saveTheDay) {
			var menus = standinDB.searchMenus(query);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : menus});
				},$rootScope.saveTheDelay)
			});
			//return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
		else return $http.get($rootScope.baseUrl + '/api/search/menu/' + query);
		/*return $http.get($rootScope.baseUrl + '/api/recipe/all').then(function(){
			return response.data;
		});*/
	};
	return {
		recipeSearch : recipeSearch,
		menuSearch : menuSearch,
		/*getResults : function() {
			return results;
		},
		getResultWithID : function(id) {
			var arrLen = results.length;
			for (var i = 0; i < arrLen; i++) {
    			if(results[i].id == id) {
    				return results[i];
    			}
			}
		}*/
	};
});

angular.module('FoodApp').factory('communityService', function($http, $rootScope, $q, userService, standinDB) {
	var getComments = function(type, parentId) {
		//return $http.get($rootScope.baseUrl + '/api/search/recipe/' + query);
		if($rootScope.saveTheDay) {
			var comments = standinDB.getComments(type, parentId);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : comments});
				},$rootScope.saveTheDelay)
			});
			//return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
		return $http.get($rootScope.baseUrl + '/api/comment/' + type + "/" + parentId);
	};

	var makeComment = function(type,parentId,body) {
		if($rootScope.saveTheDay) {
			var rating = standinDB.makeComment(userService.getUser().id, type, parentId, body);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : rating});
				},$rootScope.saveTheDelay)
			});
		}
		var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/comment',
			 headers: {
			   'Authorization': 'Bearer ' + userService.getToken().accessToken,
			   'Content-Type': 'application/json'
			 },
			 data : { "type" : type, "parentId" : parentId, "body": body }
		};
		return $http(req);
	};

	var deleteComment = function(id,type,parentId) {
		if($rootScope.saveTheDay) {
			var rating = standinDB.deleteComment(id, type, parentId);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : rating});
				},$rootScope.saveTheDelay)
			});
		}
		var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/comment/delete',
			 headers: {
			   'Authorization': 'Bearer ' + userService.getToken().accessToken,
			   'Content-Type': 'application/json'
			 },
			 data : { "id" : id, "type" : type, "parentId" : parentId}
		};
		return $http(req);
	};

	var getAvgRating = function(type,parentId) {
		console.log("Requesting avg rating of" + type + " " + parentId)
		if($rootScope.saveTheDay) {
			var rating = standinDB.getAvgRate(type, parentId);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : rating});
				},$rootScope.saveTheDelay)
			});
			//return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
		else return $http.get($rootScope.baseUrl + '/api/rate/' + type + "/" + parentId);
	};

	var rate = function(type,parentId, rating) {
		if($rootScope.saveTheDay) {
			var rating = standinDB.rate(userService.getUser().id, type, parentId,rating);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : rating});
				},$rootScope.saveTheDelay)
			});
			//return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
		var req = {
			 method: 'POST',
			 url: $rootScope.baseUrl + '/api/rate',
			 headers: {
			   'Authorization': 'Bearer ' + userService.getToken().accessToken,
			   'Content-Type': 'application/json'
			 },
			 data : { "type" : type, "parentId" : parentId, "rating": rating }
		};
		return $http(req);
	};

	var getRatingByCurrentUser = function(type,parentId) {
		if($rootScope.saveTheDay) {
			var rating = standinDB.getRateByUser(type, parentId,userService.getUser().id);
			return $q(function(resolve,reject) {
				setTimeout(function() {
					resolve({"data" : rating});
				},$rootScope.saveTheDelay)
			});
			//return $http.get($rootScope.baseUrl + '/api/recipe/all');
		}
		else return $http.get($rootScope.baseUrl + '/api/rate/' + type + "/" + parentId + "/" + userService.getUser().id);
	}

	return {
		getComments : getComments,
		makeComment : makeComment,
		getAvgRating : getAvgRating,
		rate : rate,
		getRatingByCurrentUser : getRatingByCurrentUser,
		deleteComment : deleteComment
	};
});
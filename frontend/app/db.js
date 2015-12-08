angular.module('FoodApp').factory('standinDB', function($window, $rootScope) { 
	var recipes = [{"id" : 1 , "userId" : "2" ,"name":"Tomato Soup","ingredients":
						[{"ingredientId":"529e8039f9655f6d3500d506",
							"name":"Tomatoes",
							"amount":100,
							"nutritions":{"calories":14.285714285714286,
										"carbohydrate":2.857142857142857,
										"fats":0,
										"proteins":0,
										"sodium":0,
										"fiber":0,
										"cholesterol":0,
										"sugars":2.857142857142857,
										"iron":5.714285714285714}},
						{"ingredientId":"54de40287ea1cda21acc5594",
							"name":"Water Beverage",
							"amount":100,
							"nutritions":{"calories":0,
										"carbohydrate":6.25,
										"fats":0,
										"proteins":0,
										"sodium":0,
										"fiber":0,
										"cholesterol":0,
										"sugars":0,
										"iron":0}}],
						"description":"Cook it.",
						"tags":["tomato","soup"],
						"nutritions":{"calories":14.285714285714286,
									"carbohydrate":9.107142857142858,
									"fats":0,
									"proteins":0,
									"sodium":0,
									"fiber":0,
									"cholesterol":0,
									"sugars":2.857142857142857,
									"iron":5.714285714285714}},
					{"id" : 2 , "userId" : "7" ,"name":"Omelette",
						"ingredients":[{"ingredientId":"550e6e73ca57a1da796cce37",
										"name":"Eggs, Large",
										"amount":2,
										"nutritions":{"calories":140,"carbohydrate":1,"fats":9,"proteins":12,"sodium":110,"fiber":0,"cholesterol":430,"sugars":0,"iron":8}}],
							"description":"Cook it.",
							"tags":["Omelette","egg"],
							"nutritions":{"calories":140,"carbohydrate":1,"fats":9,"proteins":12,"sodium":110,"fiber":0,"cholesterol":430,"sugars":0,"iron":8}}
					];

	var menus = [{"id": 1, "name" : "Omelette Menu", "userId" : 7, "period" : "weekly", "recipeIds": [2], "recipeNames" : ["Omelette"], "description": "Free 2 Eat"}];

	var comments = [{"id": 198, "userId": 1 , "userFullName": "Jane Doe" , "type" : "recipe"  , "parentId" : 1, "body" : "Nice recipe", "createdAt" : "2011-11-05"},
					{"id": 153, "userId": 7 , "userFullName": "John Doe" , "type" : "recipe"  , "parentId" : 2, "body" : "City food", "createdAt" : "2011-12-04" }
					];

	var rates = [{"id": 198, "userId": 1 , "userFullName": "Jane Doe" , "type" : "recipe"  , "parentId" : 1, "rating" : 3, "createdAt" : "2011-12-05"},
				 {"id": 153, "userId": 7 , "userFullName": "John Doe" , "type" : "recipe"  , "parentId" : 1, "rating" : 4, "createdAt" : "2011-10-06"}];

	var users = [{"id" : 7 , "fullName" : "John Doe"}];

	var init = function() {
		if($rootScope.saveTheStorage) {
			if($window.sessionStorage.recipes) { 
				console.log("Browser Data available");
				console.log("SESSION RECIPES: "  + JSON.stringify($window.sessionStorage.recipes));
				recipes = JSON.parse($window.sessionStorage.recipes);
			}
			if($window.sessionStorage.menus) menus = JSON.parse($window.sessionStorage.menus);
			if($window.sessionStorage.comments) comments = JSON.parse($window.sessionStorage.comments);
			if($window.sessionStorage.rates) rates = JSON.parse($window.sessionStorage.rates);
			if($window.sessionStorage.users) users = JSON.parse($window.sessionStorage.users);
		}
	}

	init();

	var saveDB = function() {
		console.log("PRESAVE: "  + JSON.stringify($window.sessionStorage.recipes));
		$window.sessionStorage.recipes = JSON.stringify(recipes);
		$window.sessionStorage.menus = JSON.stringify(menus);
		$window.sessionStorage.comments = JSON.stringify(comments);
		$window.sessionStorage.rates = JSON.stringify(rates);
		$window.sessionStorage.users = JSON.stringify(users);
		console.log("AFTERSAVE: "  + JSON.stringify($window.sessionStorage.recipes));
	}

	var searchRecipes = function (query) {
		var queryTags = query.split(" ");
		queryTags = queryTags.filter(function(val) { return val !== null && val != "" } );
		recipesLikelinesses = [];
		for(var i = 0; i < recipes.length; i++) {
			var recipe = recipes[i];
			var recipeLikeliness = 0;
			for(var k = 0; k < queryTags.length; k++) {
					console.log("Recipe Query Tag:" + queryTags[k] + " Recipe Tags: " + JSON.stringify(recipe.tags));
					if(recipe.tags.indexOf(queryTags[k]) >= 0)
						recipeLikeliness += 1;
			}
			recipesLikelinesses.push({ "pos": i , "recipeLikeliness" : recipeLikeliness});
		}
		resultPos = recipesLikelinesses.filter(function(val) {
						return val.recipeLikeliness > 0;
					});
		resultRecipes = []
		for(var i = 0; i < resultPos.length; i++) {
			resultRecipes.push(recipes[resultPos[i].pos]);
		}
		console.log("Recipe Search results: " + JSON.stringify(resultRecipes));
		return resultRecipes;
	}

	var getRecommendedRecipes = function(id) {
		var recipe = getRecipeWithID(id);
		if(recipe !== null) {
			var result = searchRecipes(recipe.tags.join(' '));
			console.log("RECOMMENDED RECIPES db return " + JSON.stringify(result));
			result = result.filter((v) => {return v.id != id} );
			return result;
		}
	}

	var searchMenus = function (query) {
		var queryTags = query.split(" ");
		queryTags = queryTags.filter(function(val) { return val !== null && val != "" } );
		menusLikelinesses = [];
		for(var i = 0; i < menus.length; i++) {
			var menu = menus[i];
			var menuLikeliness = 0;
			for(var j = 0; j < menu.recipeIds.length; j++) {
				var recipe = null;
				for(var k = 0; k < recipes.length; k++) {
					if(recipes[k].id == menu.recipeIds[j]) {
						recipe = recipes[k];
						break;
					}
				}
				if(recipe != null) {
					for(var k = 0; k < queryTags.length; k++) {
						console.log("Menu Query Tag:" + queryTags[k] + " Recipe Tags: " + JSON.stringify(recipe.tags) + "Exists: " + JSON.stringify(recipe.tags.indexOf(queryTags[k]) >= 0) );
						if(recipe.tags.indexOf(queryTags[k]) >= 0)
							menuLikeliness += 1;
					}
				}
			}
			menusLikelinesses.push({ "pos": i , "menuLikeliness" : menuLikeliness});
		}
		resultPos = menusLikelinesses.filter(function(val) {
						return val.menuLikeliness > 0;
					});
		resultMenus = []
		for(var i = 0; i < resultPos.length; i++) {
			resultMenus.push(menus[resultPos[i].pos]);
		}
		console.log("Menu Search results: " + JSON.stringify(resultMenus));
		return resultMenus;
	}

	var getComments = function(type, parentId) {
		return comments.filter(function(comment) {
				return comment.type == type && comment.parentId == parentId;
			});
	}

	var getCommentsByUser = function(type,parentId,userId) {
		return comments.filter(function(comment) {
				return comment.type == type && comment.parentId == parentId && comment.userId == userId;
			});
	}

	var getAvgRate = function(type, parentId) {
		console.log("Requested avg rate of " + type + " " + parentId)
		var ratesOfElem = rates.filter(function(r) {
				console.log("Rate " + r.type + " " + r.parentId +  " Is belong to " + JSON.stringify(r.type == type && r.parentId == parentId));
				return r.type == type && r.parentId == parentId;
			});
		console.log("After avg rate request size of the rates : " + rates.length);
		if(ratesOfElem.length == 0)
			return {"type" : type, "parentId" : parentId, "rating" : 0};
		var rate = 0;
		for(var i=0; i<ratesOfElem.length; i++) {
			rate += ratesOfElem[i].rating;
			console.log("DB Elem Rate: " + ratesOfElem[i].rating);
		}
		rate = Math.round(rate / ratesOfElem.length);
		console.log("DB Avg Rate: " + rate);
		return {"type" : type, "parentId" : parentId, "rating" : rate};
	}

	var getRateByUser = function(type,parentId,userId) {
		for(var i=0; i<rates.length; i++) {
			if(rates[i].parentId == parentId && rates[i].type == type && rates[i].userId == userId) {
				return rates[i];
			}
		}
		return null;
	}

	var getMenuWithID = function(id) {
		for(var i=0; i<menus.length; i++) {
			if(menus[i].id == id) {
				return menus[i];
			}
		}
		return null
	}

	var getRecipeWithID = function(id) {
		for(var i=0; i<recipes.length; i++) {
			if(recipes[i].id == id) {
				console.log("DB Returned recipe " + JSON.stringify(recipes[i]));
				return recipes[i];
			}
		}
		return null
	}

	var getUserRecipes = function(id) {
		return recipes.filter(function(r) {
			return r.userId == id;
		});
	}

	var addRecipe = function(userId,name,ingredients,desc,tags, nutrition) {
		recipes.push({"name" : name, "userId" : userId, "id" : recipes.length+1, "ingredients" : ingredients, "nutritions" : nutrition, "description" : desc, "tags" : tags});
	}

	var addMenu = function(userId,name,recipeIds,period,desc) {
		var recipeNames = [];
		for(var i=0; i < recipeIds.length; i++) {
			console.log("STDAY Add menu recipe id: " + recipeIds[i]);
			for(var j=0; j<recipes.length; j++) {
				console.log("STDAY Add menu recipe vs recipe id: " + (recipes[j].id == recipeIds[i]));
				if(recipes[j].id == recipeIds[i]) {
					recipeNames.push(recipes[j].name);
					break;
				}
			}
		}
		console.log("STDAY ADD MENU recipe names : " + recipeNames );
		menus.push({"id" : menus.length+1, "name" : name, "recipeIds" : recipeIds, "userId" : userId,
					"recipeNames" : recipeNames, "description" : desc, "period" : period})
	}

	var makeComment = function(userId, type, parentId, body) {
		var userFullName = "";
		for(var i=0; i<users.length; i++) {
			if(users[i].id == userId)
				userFullName = users[i].fullName;
		}
		comments.push({"id" : comments.length+1, "userId" : userId, "userFullName" : userFullName, "type" : type, "parentId" : parentId, "body" : body, "createdAt" : getDate()});
	}

	var deleteComment = function(id, type, parentId) {
		comments = comments.filter(function(v) {
						return (v.type != type || v.parentId != parentId || v.id != id);
					});
	}

	var rate = function(userId, type, parentId, rating) {
		for(var i=0; i<rates.length; i++) {
			if(rates[i].userId == userId && rates[i].type == type && rates[i].parentId == parentId) {
				rates[i].rating = rating;
				console.log("Rate exists, modifying");
				return;
			}
		}
		var userFullName = "";
		for(var i=0; i<users.length; i++) {
			if(users[i].id == userId)
				userFullName = users[i].fullName;
		}
		rates.push({"id" : rates.length+1, "userId" : userId, "userFullName" : userFullName, "type" : type, "parentId" : parentId, "rating" : rating, "createdAt" : getDate()});
		console.log("STDAY DB rates after add: " + JSON.stringify(rates));
	};

	var getDate = function() {
		var today = new Date();
    	var dd = today.getDate();
    	var mm = today.getMonth()+1;
	    var yyyy = today.getFullYear();
    	if(dd<10){
       		dd='0'+dd
    	} 
    	if(mm<10){
        	mm='0'+mm
    	} 
    	return yyyy + '-' + mm + '-' + dd;
	}

	return {
		getMenus : function(){
			return menus;
		},
		getRecipes : function(){
			return recipes;
		},
		getRecommendedRecipes : getRecommendedRecipes,
		getComments : getComments,
		getCommentsByUser : getCommentsByUser,
		getAvgRate : getAvgRate,
		getRateByUser : getRateByUser,
		searchMenus : searchMenus,
		searchRecipes : searchRecipes,
		getMenuWithID : getMenuWithID,
		getRecipeWithID : getRecipeWithID,
		addMenu : addMenu,
		addRecipe : addRecipe,
		makeComment : makeComment,
		deleteComment : deleteComment,
		getUserRecipes : getUserRecipes,
		rate : rate,
		saveDB : saveDB
	};

});
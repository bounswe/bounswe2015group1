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
	});
	$urlRouterProvider.otherwise('/');

});

angular.module('FoodApp').run(function($rootScope) {
		$rootScope.baseUrl = "http://localhost:8080";
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
	var register = function (fullName, email, password, dateOfBirth, location) {
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
		 	"dateOfBirth" : dateOfBirth
		 }
		};
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
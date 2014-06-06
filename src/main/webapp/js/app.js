angular.module('mturk', ['ngRoute', 'ngSanitize', 'ui.bootstrap', 'googlechart', 'ngTable'])
.config(['$routeProvider', 'views', function($routeProvider, views) {
    $routeProvider
    .when('/api', {templateUrl: views.api, controller: 'ApiController'})
    .when('/arrivals', {templateUrl: views.arrivals, controller: 'ArrivalsController'})
    .when('/completions', {templateUrl: views.completions, controller: 'CompletionsController'})
    .when('/general', {templateUrl: views.general, controller: 'GeneralController'})
    .when('/toprequesters', {templateUrl: views.toprequesters, controller: 'TopRequestersController'})
    .when('/search', {templateUrl: views.search, controller: 'SearchController'})
    .otherwise({redirectTo: '/general'});
}])

.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('interceptor');
}]);
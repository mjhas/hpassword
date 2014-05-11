(function(angular, jQuery, undefined) {
	var passwordApp = angular.module('passwordApp', [ 'ngGrid',
			'ui.bootstrap.modal', 'ngRoute' ]);

	passwordApp.config(function($routeProvider) {
		$routeProvider

		.when('/', {
			redirectTo : '/change'
		}).when('/resetlink', {
			templateUrl : 'api/resetlink.html',
			controller : 'resetlinkController'
		}).when('/reset', {
			templateUrl : 'api/reset.html',
			controller : 'resetController'
		}).when('/change', {
			templateUrl : 'api/change.html',
			controller : 'changeController'
		}).otherwise({
			redirectTo : '/change'
		});
	});
	passwordApp.directive('passwordMatch', [ function() {
		return {
			restrict : 'A',
			scope : true,
			require : 'ngModel',
			link : function(scope, elem, attrs, ctrl) {
				var initialvalid = scope.$eval(attrs.ngModel) === scope
						.$eval(attrs.passwordMatch)
				ctrl.$setValidity('passwordMatch', initialvalid);
				ctrl.$parsers.unshift(function(value) {
					var valid = value === scope.$eval(attrs.passwordMatch);
					ctrl.$setValidity('passwordMatch', valid);
					return valid ? value : undefined;
				});
				ctrl.$formatters.unshift(function(value) {
					var valid = scope.$eval(attrs.passwordMatch) === value;
					ctrl.$setValidity('passwordMatch', valid);
					return value;
				});
			}
		};
	} ]);
	passwordApp.controller('changeController', function($scope,
			passwordService, $modal) {
		$scope.submit = function(pcr, newPasswordRepeat, captcha) {
			passwordService.changePassword(pcr, captcha).success(
					function(data) {
						var modalInstance = $modal.open({
							templateUrl : 'api/dialog.html?msg=change.ok',
							controller : 'dialogController',
							size : 'sm',
						});
						$scope.reset();
					}).error(function(data) {
				var modalInstance = $modal.open({
					templateUrl : 'api/dialog.html?msg=error',
					controller : 'dialogController',
					size : 'sm',
				});
			});
		};
		$scope.reset = function() {
			$scope.pcr = {};
			$scope.newPasswordRepeat = '';
		};
	});
	passwordApp.controller('dialogController',
			function($scope, $modalInstance) {
				$scope.cancel = function() {
					$modalInstance.close();
				};
			});
	passwordApp.controller('resetController',
			function($scope, passwordService, $modal) {
				$scope.submit = function(pcr, newPasswordRepeat, captcha) {
					passwordService.resetPassword(pcr, captcha).success(
							function(data) {
								var modalInstance = $modal.open({
									templateUrl : 'api/dialog.html?msg=reset.ok',
									controller : 'dialogController',
									size : 'sm',
								});
								$scope.reset();
							}).error(function(data) {
						var modalInstance = $modal.open({
							templateUrl : 'api/dialog.html?msg=error',
							controller : 'dialogController',
							size : 'sm',
						});
					});
				};
				$scope.reset = function() {
					$scope.pcr = {};
					$scope.newPasswordRepeat = '';
				};
			});
	passwordApp.controller('resetlinkController', function($scope,
			passwordService, $modal) {
		$scope.submit = function(prr, captcha) {
			passwordService.resetrequest(prr, captcha).success(
					function(data) {
						var modalInstance = $modal.open({
							templateUrl : 'api/dialog.html?msg=token.ok',
							controller : 'dialogController',
							size : 'sm',
						});
						$scope.reset();
					}).error(function(data) {
				var modalInstance = $modal.open({
					templateUrl : 'api/dialog.html?msg=error',
					controller : 'dialogController',
					size : 'sm',
				});
			});
		};
		$scope.reset = function() {
			$scope.pcr = {};
			$scope.newPasswordRepeat = '';
		};
	});

	passwordApp.factory('passwordService',
			function($http) {
				return {
					changePassword : function(pcr, captcha) {
						return $http.post("api/changepassword?captcha="
								+ captcha, pcr);
					},
					resetPassword : function(pcr, captcha) {
						return $http.post("api/tokenreset?captcha=" + captcha,
								pcr);
					},
					resetrequest : function(prr, captcha) {
						return $http.post(
								"api/resetrequest?captcha=" + captcha, prr);
					},
				};
			});

}(angular, jQuery));
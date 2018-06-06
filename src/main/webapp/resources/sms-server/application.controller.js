(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('applicationCtrl', applicationCtrl);

    applicationCtrl.$inject = ['applicationService', '$scope', 'loginService', '$location', 'confirmService'];
    function applicationCtrl(applicationService, $scope, loginService, $location, confirmService) {
        $scope.$watch(loginService.isAuthenticated, function () {
            $scope.authenticated = loginService.isAuthenticated();
        });

        $scope.versionLoaded = false;

        var confirmLogoutModalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Log Out',
            headerText: 'Confirm logout?',
            bodyText: 'Are you sure you want to log out?'
        };

        $scope.logout = function () {
            confirmService.showModal({}, confirmLogoutModalOptions).then(function (result) {
                loginService.logout();
            });
        };

        $scope.getCurrentUserName = function () {
            return loginService.getCurrentUserName();
        };

        $scope.getVersionInfo = function () {
            applicationService.getVersionInfo()
                .then(function (response) {
                    $scope.version = response.data;
                    $scope.versionLoaded = true;
                })
        };
    }
})();
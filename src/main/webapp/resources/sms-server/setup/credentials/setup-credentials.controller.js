(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('setupCredentialsCtrl', setupCredentialsCtrl);

    setupCredentialsCtrl.$inject = ['setupCredentialsService', '$scope', '$uibModal', '$rootScope', 'confirmService'];
    function setupCredentialsCtrl(setupCredentialsService, $scope, $uibModal, $rootScope, confirmService) {
        var getUserCredentials = function () {
            setupCredentialsService.getUserCredentials().then(function (data) {
                $scope.userCredentials = data.data;
            });
        };

        getUserCredentials();

        $scope.addCredentialsModal = function () {
            $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: 'resources/sms-server/modal/add-credentials/',
                controller: 'addCredentialsCtrl',
                size: 'lg'
            }).closed.then(function () {
                getUserCredentials();
            });
        };

        var confirmDeleteModalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete',
            headerText: 'Delete Credentials?',
            bodyText: 'Are you sure you want to delete this credentials?'
        };

        $scope.removeCredentials = function (id) {
            confirmService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                setupCredentialsService.removeCredentials(id).then(function (data) {
                    getUserCredentials();
                })
            });
        };

        $scope.shareCredentials = function (id) {
            $rootScope.shareCredentialsId = id;
            $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: 'resources/templates/modal/shareCredentials-modal.html',
                controller: 'ShareCredentialsModalController',
                size: 'lg'
            }).closed.then(function () {
                getUserCredentials();
            });
        };
    }
})();
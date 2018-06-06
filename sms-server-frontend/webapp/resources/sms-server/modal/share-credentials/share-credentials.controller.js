(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('shareCredentialsCtrl', shareCredentialsController);

  shareCredentialsController.$inject = ['shareCredentialsService', '$scope',
    '$uibModalInstance', '$rootScope'];

  function shareCredentialsController(shareCredentialsService, $scope,
      $uibModalInstance, $rootScope) {
    $scope.cancel = function () {
      $uibModalInstance.dismiss();
    };

    shareCredentialsService.getUsersWithoutCredentials(
        $rootScope.shareCredentialsId).then(function (response) {
      $scope.users = response.data;
    });

    $scope.selected = {};
    $scope.selected.user = {};

    $scope.share = function () {
      var credentialsId = $rootScope.shareCredentialsId;

      var request = {
        credentialsId: credentialsId,
        userId: $scope.selected.user.id
      };

      shareCredentialsService.shareCredentials(request).then(function (data) {
        $uibModalInstance.dismiss();
      })
    };
  }
})();
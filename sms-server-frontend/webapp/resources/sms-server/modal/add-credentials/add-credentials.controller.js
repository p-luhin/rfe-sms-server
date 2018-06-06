(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('addCredentialsCtrl', addCredentialsCtrl);

  addCredentialsCtrl.$inject = ['addCredentialsService', '$scope',
    '$uibModalInstance', 'toaster'];

  function addCredentialsCtrl(addCredentialsService, $scope, $uibModalInstance,
      toaster) {
    $scope.addCredentials = function (credentials) {
      if (!credentials || !credentials.apiKey || !credentials.sender
          || !credentials.username) {

        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'Please, fill all required fields with correct data.',
          timeout: 0
        });

        return;
      }

      addCredentialsService.addCredentials(credentials).then(function (data) {
        $uibModalInstance.dismiss();
      });
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss();
    }
  }
})();
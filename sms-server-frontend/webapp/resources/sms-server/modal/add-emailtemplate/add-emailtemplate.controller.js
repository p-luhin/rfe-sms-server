(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('addEmailTemplateCtrl', addEmailTemplateCtrl);

  addEmailTemplateCtrl.$inject = ['addEmailTemplateService', '$scope',
    '$uibModalInstance', 'toaster'];

  function addEmailTemplateCtrl(addEmailTemplateService, $scope,
      $uibModalInstance, toaster) {
    addEmailTemplateService.getAvailableSMSTemplates().then(
        function (response) {
          $scope.smsTypes = response.data;
        });

    $scope.template = {};

    $scope.addTemplate = function (template) {
      if (!template || !template.subject || !template.content
          || !template.smsType) {

        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'Please, fill all required fields with correct data.',
          timeout: 0
        });

        return;
      }

      addEmailTemplateService.saveEmailTemplate(template).then(function (data) {
        $uibModalInstance.dismiss();
      });
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss();
    }
  }
})();
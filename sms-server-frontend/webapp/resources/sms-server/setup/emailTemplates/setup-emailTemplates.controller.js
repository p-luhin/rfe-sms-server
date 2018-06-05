(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('setupEmailTemplatesCtrl', setupEmailTemplatesCtrl);

  setupEmailTemplatesCtrl.$inject = ['setupEmailTemplatesService', '$scope',
    '$uibModal', 'confirmService'];

  function setupEmailTemplatesCtrl(setupEmailTemplatesService, $scope,
      $uibModal, confirmService) {
    var getEmailTemplates = function () {
      setupEmailTemplatesService.getEmailTemplates().then(function (data) {
        $scope.emailTemplates = data.data;
      });
    };

    getEmailTemplates();

    $scope.addEmailTemplateModal = function () {
      $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'modal/add-emailtemplate/add-emailtemplate.tmpl.html',
        controller: 'addEmailTemplateCtrl',
        size: 'lg'
      }).closed.then(function () {
        getEmailTemplates();
      });
    };

    var confirmDeleteModalOptions = {
      closeButtonText: 'Cancel',
      actionButtonText: 'Delete',
      headerText: 'Delete Email Template?',
      bodyText: 'Are you sure you want to delete this email template?'
    };

    $scope.removeEmailTemplate = function (id) {
      confirmService.showModal({}, confirmDeleteModalOptions).then(
          function (result) {
            setupEmailTemplatesService.removeEmailTemplate(id).then(
                function (data) {
                  getEmailTemplates();
                })
          });
    };
  }
})();
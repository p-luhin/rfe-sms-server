(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('setupTemplatesCtrl', setupTemplatesCtrl);

  setupTemplatesCtrl.$inject = ['setupTemplatesService', '$scope', '$uibModal',
    'confirmService'];

  function setupTemplatesCtrl(setupTemplatesService, $scope, $uibModal,
      confirmService) {
    var getSMSTemplates = function () {
      setupTemplatesService.getAllTemplates().then(function (data) {
        $scope.smsTemplates = data.data;
      });
    };

    getSMSTemplates();

    $scope.addSMSTemplateModal = function () {
      $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'resources/sms-server/modal/add-template/add-template.tmpl.html',
        controller: 'addTemplateCtrl',
        size: 'lg'
      }).closed.then(function () {
        getSMSTemplates();
      });
    };

    var confirmDeleteModalOptions = {
      closeButtonText: 'Cancel',
      actionButtonText: 'Delete',
      headerText: 'Delete SMS Template?',
      bodyText: 'Are you sure you want to delete this sms template?'
    };

    var confirmDeleteEmailTemplateModalOptions = {
      closeButtonText: 'Cancel',
      actionButtonText: 'Delete',
      headerText: 'Delete Email Template?',
      bodyText: 'If you will remove this SMS template, email template will be removed too. Are you wish to continue?'
    };

    $scope.removeSMSTemplate = function (id) {
      confirmService.showModal({}, confirmDeleteModalOptions).then(
          function (result) {
            confirmService.showModal({},
                confirmDeleteEmailTemplateModalOptions).then(function (result) {
              setupTemplatesService.removeTemplate(id).then(function (data) {
                getSMSTemplates();
              })
            });
          });
    };
  }
})();
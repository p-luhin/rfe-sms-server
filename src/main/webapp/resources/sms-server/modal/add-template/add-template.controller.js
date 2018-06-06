(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('addTemplateCtrl', addTemplateCtrl);

    addTemplateCtrl.$inject = ['addTemplateService', '$scope', '$uibModalInstance', 'toaster'];
    function addTemplateCtrl(addTemplateService, $scope, $uibModalInstance, toaster) {
        $scope.addTemplate = function (template) {
            if (!template || !template.smsType) {

                toaster.pop({
                    type: 'error',
                    title: 'Error',
                    body: 'Please, fill all required fields with correct data.',
                    timeout: 0
                });

                return;
            }
            template.enabled = true;

            addTemplateService.addTemplate(template).then(function (data) {
                $uibModalInstance.dismiss();
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        }
    }
})();
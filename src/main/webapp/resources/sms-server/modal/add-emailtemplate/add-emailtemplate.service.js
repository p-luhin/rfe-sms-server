(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('addEmailTemplateService', addEmailTemplateService);

    addEmailTemplateService.$inject = ['$http', 'RestURLFactory'];
    function addEmailTemplateService($http, RestURLFactory) {
        return {
            getAvailableSMSTemplates: getAvailableSMSTemplates,
            saveEmailTemplate: saveEmailTemplate
        };

        function getAvailableSMSTemplates() {
            return $http.get(RestURLFactory.AVAILABLE_SMS_TEMPLATES);
        }

        function saveEmailTemplate(emailTemplate) {
            return $http.post(RestURLFactory.EMAIL_TEMPLATE, emailTemplate);
        }
    }
})();
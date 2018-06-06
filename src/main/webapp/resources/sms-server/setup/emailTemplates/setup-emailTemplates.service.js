(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('setupEmailTemplatesService', setupEmailTemplatesService);

    setupEmailTemplatesService.$inject = ['$http', 'RestURLFactory'];
    function setupEmailTemplatesService($http, RestURLFactory) {
        return {
            getEmailTemplates: getEmailTemplates,
            removeEmailTemplate: removeEmailTemplate
        };

        function getEmailTemplates() {
            return $http.get(RestURLFactory.EMAIL_TEMPLATE);
        }

        function removeEmailTemplate(id) {
            return $http.delete(RestURLFactory.EMAIL_TEMPLATE + '/' + id);
        }
    }
})();
(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('addTemplateService', addTemplateService);

    addTemplateService.$inject = ['$http', 'RestURLFactory'];
    function addTemplateService($http, RestURLFactory) {
        return {
            addTemplate: addTemplate
        };

        function addTemplate(template) {
            return $http.post(RestURLFactory.SMS_TEMPLATE, template);
        }
    }
})();
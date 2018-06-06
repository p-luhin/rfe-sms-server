(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('setupPropertiesService', setupPropertiesService);

    setupPropertiesService.$inject = ['$http', 'RestURLFactory'];
    function setupPropertiesService($http, RestURLFactory) {
        return {
            saveProperties: saveProperties,
            getProperties: getProperties
        };

        function saveProperties(properties) {
            return $http.post(RestURLFactory.SMS_SERVER_PROPERTIES, properties);
        }

        function getProperties() {
            return $http.get(RestURLFactory.SMS_SERVER_PROPERTIES);
        }
    }
})();
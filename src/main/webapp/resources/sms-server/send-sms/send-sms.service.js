(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('sendSmsService', sendSmsService);

    sendSmsService.$inject = ['RestURLFactory', '$http'];
    function sendSmsService(RestURLFactory, $http) {
        return {
            getSenderNames: getSenderNames,
            sendSMS: sendSMS,
            sendBulkSMS: sendBulkSMS,
            loadRecipients: loadRecipients
        };

        function getSenderNames() {
            return $http.get(RestURLFactory.GET_SENDER_NAMES);
        }

        function sendSMS(sms) {
            return $http.post(RestURLFactory.SEND_CUSTOM_SMS, sms);
        }

        function sendBulkSMS(formData) {
            return $http.post(RestURLFactory.SEND_BULK_SMS, formData,
                {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                });
        }

        function loadRecipients(query) {
            return $http.get(RestURLFactory.GET_ALL_RECIPIENTS + "?query=" + query);
        }
    }

})();
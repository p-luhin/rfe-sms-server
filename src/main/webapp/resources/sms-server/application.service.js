(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('applicationService', applicationService);

    applicationService.$inject = ['RestURLFactory', '$http'];
    function applicationService(RestURLFactory, $http) {
        return {
            getVersionInfo: getVersionInfo
        };

        function getVersionInfo() {
            return $http.get(RestURLFactory.VERSION);
        }
    }
})();
(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('changePasswordService', changePasswordService);

    changePasswordService.$inject = ['$http', 'RestURLFactory'];
    function changePasswordService($http, RestURLFactory) {
        return {
            changePassword: changePassword
        };

        function changePassword(password) {
            return $http.post(RestURLFactory.CHANGE_PASSWORD, password);
        }
    }
})();
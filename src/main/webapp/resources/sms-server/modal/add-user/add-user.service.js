(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('addUserService', addUserService);

    addUserService.$inject = ['$http', 'RestURLFactory'];
    function addUserService($http, RestURLFactory) {
        return {
            addNewUser: addNewUser
        };

        function addNewUser(user) {
            return $http.post(RestURLFactory.USERS, user);
        }
    }
})();
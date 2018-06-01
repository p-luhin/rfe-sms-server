(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('shareCredentialsService', shareCredentialsService);

  shareCredentialsService.$inject = ['$http', 'RestURLFactory'];

  function shareCredentialsService($http, RestURLFactory) {
    return {
      shareCredentials: shareCredentials,
      getUsersWithoutCredentials: getUsersWithoutCredentials
    };

    function shareCredentials(request) {
      return $http.post(RestURLFactory.SHARE_CREDENTIALS, request);
    }

    function getUsersWithoutCredentials(credentials) {
      return $http.get(RestURLFactory.USERS + '?credentialsId=' + credentials);
    }
  }
})();
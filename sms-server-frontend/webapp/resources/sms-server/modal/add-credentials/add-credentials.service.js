(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('addCredentialsService', addCredentialsService);

  addCredentialsService.$inject = ['$http', 'RestURLFactory'];

  function addCredentialsService($http, RestURLFactory) {
    return {
      addCredentials: addCredentials
    };

    function addCredentials(credentials) {
      return $http.post(RestURLFactory.CREDENTIALS, credentials);
    }
  }
})();
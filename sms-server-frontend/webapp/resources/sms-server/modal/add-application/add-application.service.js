(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('addApplicationService', addApplicationService);

  addApplicationService.$inject = ['RestURLFactory', '$http'];

  function addApplicationService(RestURLFactory, $http) {
    return {
      getUserCredentials: getUserCredentials,
      addNewApplication: addNewApplication
    };

    function getUserCredentials() {
      return $http.get(RestURLFactory.CREDENTIALS);
    }

    function addNewApplication(application) {
      return $http.post(RestURLFactory.APPLICATION, application);
    }
  }
})();
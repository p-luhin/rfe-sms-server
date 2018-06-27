(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('personsService', personsService);

  /** @ngInject */
  function personsService(RestURLFactory, $http) {
    return {
      removePerson: removePerson,
      getPersons: getPersons,
      addPersons: addPersons,
      getPersonsByQuery: getPersonsByQuery
    };

    function removePerson(id) {
      return $http.delete(RestURLFactory.PERSONS + '/' + id);
    }

    function getPersons(skip, currentPageSize, sortField, sortDirection) {
      return $http.get(RestURLFactory.PERSONS +
          '?skip=' + skip + '&offset=' + currentPageSize + '&sortField='
          + sortField + '&sortDirection=' + sortDirection);
    }

    function getPersonsByQuery(skip, currentPageSize, sortField, sortDirection,
        query) {
      return $http.get(RestURLFactory.PERSONS +
          '?skip=' + skip +
          '&offset=' + currentPageSize +
          '&sortField=' + sortField +
          '&sortDirection=' + sortDirection +
          '&query=' + query
      );
    }

    function addPersons(persons) {
      return $http.post(RestURLFactory.PERSONS, recipients);
    }
  }
})();
(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('addPersonsService', addPersonsService);

    addPersonsService.$inject = ['RestURLFactory', '$http'];
    function addPersonsService(RestURLFactory, $http) {
        return {
            savePersons: savePersons,
            deletePerson: deletePerson,
            getPersons: getPersons
        };

        function savePersons(persons) {
            return $http.post(RestURLFactory.PERSONS, persons);
        }

        function deletePerson(id) {
            return $http.delete(RestURLFactory.PERSONS + '?personId=' + id);
        }

        function getPersons(skip, currentPageSize, sortField, sortDirection) {
            return $http.get(RestURLFactory.PERSONS +
                '?skip=' + skip + '&offset=' + currentPageSize.value + '&sortField=' + sortField + '&sortDirection=' + sortDirection
            );
        }
    }
})();
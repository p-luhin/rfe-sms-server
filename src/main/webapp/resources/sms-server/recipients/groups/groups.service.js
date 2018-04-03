(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('groupsService', groupsService);

    groupsService.$inject = ['RestURLFactory', '$http'];
    function groupsService(RestURLFactory, $http) {
        return {
            getGroup: getGroup,
            getPersonsWithoutGroup: getPersonsWithoutGroup,
            addGroup: addGroup,
            getAllGroups: getAllGroups,
            removeGroup: removeGroup,
            getGroupsByQuery: getGroupsByQuery
        };

        function getGroup(groupId) {
            return $http.get(RestURLFactory.GROUP + '/' + groupId);
        }

        function getPersonsWithoutGroup(groupId) {
            return $http.get(RestURLFactory.PERSONS_WITHOUT_GROUP + groupId);
        }

        function addGroup(group) {
            return  $http.post(RestURLFactory.GROUP, group);
        }

        function getAllGroups(skip, currentPageSize, sortField, sortDirection) {
            return $http.get(RestURLFactory.GROUP +
                '?skip=' + skip + '&offset=' + currentPageSize + '&sortField=' + sortField + '&sortDirection=' + sortDirection
            );
        }

        function getGroupsByQuery(skip, currentPageSize, sortField, sortDirection, query) {
          return $http.get(RestURLFactory.GROUP +
              '?skip=' + skip +
              '&offset=' + currentPageSize +
              '&sortField=' + sortField +
              '&sortDirection=' + sortDirection +
              '&query=' + query
          );
        }

        function removeGroup(id) {
            return $http.delete(RestURLFactory.GROUP + '?groupId=' + id)
        }
    }
})();
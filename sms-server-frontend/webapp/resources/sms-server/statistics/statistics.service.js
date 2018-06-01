(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('statisticsService', statisticsService);

  statisticsService.$inject = ['RestURLFactory', '$http'];

  function statisticsService(RestURLFactory, $http) {
    return {
      loadStatistics: loadStatistics
    };

    function loadStatistics(skip, currentPageSize, sortField, sortDirection) {
      return $http.get(RestURLFactory.GET_FULL_STATISTICS +
          '/page?skip=' + skip + '&offset=' + currentPageSize.value
          + '&sortField=' + sortField + '&sortDirection=' + sortDirection);
    }
  }
})();
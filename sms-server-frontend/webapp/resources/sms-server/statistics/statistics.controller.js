(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('statisticsCtrl', statisticsCtrl);

  statisticsCtrl.$inject = ['$scope', '$routeParams', 'statisticsService'];

  function statisticsCtrl($scope, $routeParams, statisticsService) {
    var sortConstants = {
      notSorted: {
        elementClass: "fa fa-sort"
      },
      asc: {
        elementClass: "fa fa-sort-asc",
        sortString: 'asc'
      },
      desc: {
        elementClass: "fa fa-sort-desc",
        sortString: 'desc'
      }
    };

    var openFirst = $routeParams.openFirst;

    var sortOrder = {
      error: sortConstants.notSorted,
      smsType: sortConstants.notSorted,
      senderName: sortConstants.notSorted,
      number: sortConstants.notSorted,
      recipientType: sortConstants.notSorted,
      sentDate: sortConstants.desc,
      initiatedBy: sortConstants.notSorted
    };

    var sortField = 'sentDate';
    var sortDirection = sortConstants.desc.sortString;

    var sortBy = function (field) {
      if (sortOrder[field] === sortConstants.notSorted) {
        sortOrder[field] = sortConstants.asc;
      } else if (sortOrder[field] === sortConstants.asc) {
        sortOrder[field] = sortConstants.desc;
      } else if (sortOrder[field] === sortConstants.desc) {
        sortOrder[field] = sortConstants.asc;
      }

      sortDirection = sortOrder[field].sortString;
      sortField = field;

      for (var sortOrderField in sortOrder) {
        if (sortOrderField != field) {
          sortOrder[sortOrderField] = sortConstants.notSorted;
        }
      }
    };

    $scope.sortConstants = sortConstants;
    $scope.sortOrder = sortOrder;

    $scope.sortBy = function (field) {
      sortBy(field);
      $scope.loadStatistics();
    };

    var pageSize = {
      ten: {
        display: "10",
        value: 10,
        active: true
      },
      fifteen: {
        display: "15",
        value: 15,
        active: false
      },
      fifty: {
        display: "50",
        value: 50,
        active: false
      }
    };

    var currentPage = 1;
    var currentPageSize = pageSize.ten;
    var showPages = [];
    var skip = 0;
    var count = 0;

    var recalculatePages = function () {
      pushPages();
      $scope.showPages = showPages;
      $scope.pageCount = count;
      $scope.currentPage = currentPage;
    };

    var pushPages = function () {
      showPages = [];
      if (currentPage === 1) {
        showPages.push(createPage(currentPage, true));
        showPages.push(createPage(currentPage + 1, false));
        showPages.push(createPage(currentPage + 2, false));
      } else if (currentPage === count) {
        showPages.push(createPage(currentPage - 2, false));
        showPages.push(createPage(currentPage - 1, false));
        showPages.push(createPage(currentPage, true));
      } else {
        showPages.push(createPage(currentPage - 1, false));
        showPages.push(createPage(currentPage, true));
        showPages.push(createPage(currentPage + 1, false));
      }
    };

    var createPage = function (pageNum, active) {
      return {
        pageNumber: pageNum,
        active: active
      }
    };

    var changePage = function (targetPage) {
      skip = (targetPage - 1) * currentPageSize.value;

      currentPage = targetPage;
    };

    var changePageSize = function (targetPageSize) {
      currentPageSize.active = false;
      currentPageSize = targetPageSize;
      currentPageSize.active = true;
    };

    $scope.pageSize = pageSize;

    $scope.getPage = function (targetPage) {
      changePage(targetPage);
      $scope.loadStatistics();
    };

    $scope.changePageSize = function (targetPageSize) {
      changePageSize(targetPageSize);
      $scope.loadStatistics();
    };

    $scope.statistics = [];

    $scope.loadStatistics = function () {
      statisticsService.loadStatistics(skip, currentPageSize, sortField,
          sortDirection).then(function (response) {
        $scope.statistics = response.data.items;

        count = Math.ceil(response.data.count / currentPageSize.value);
        recalculatePages();

        if ($scope.statistics && openFirst) {
          $scope.statistics[0].expanded = true;
        }
      });
    };

    $scope.loadStatistics();
  }
})();
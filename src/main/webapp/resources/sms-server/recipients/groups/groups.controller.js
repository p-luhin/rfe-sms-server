(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('groupsCtrl', groupsCtrl);

    groupsCtrl.$inject = ['groupsService', '$scope', '$location', '$routeParams', 'toaster', 'confirmService'];
    function groupsCtrl(groupsService, $scope, $location, $routeParams, toaster, confirmService) {
        var allGroups = [];
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

        var sortOrder = {
            name: sortConstants.notSorted
        };

        var sortField = 'createdDate';
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
            $scope.getAllGroups();
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
        var pages = [];
        var skip = 0;
        var count = 0;

        var recalculatePages = function () {
            pages = [];
            for (var i = 0; i < count; i++) {
                var page = {
                    pageNumber: i + 1,
                    active: currentPage - 1 === i
                };
                pages.push(page);
                $scope.pages = pages;
            }
        };

        var changePage = function (targetPage) {
            skip = (targetPage - 1) * currentPageSize.value;

            currentPage = targetPage;
            recalculatePages();
        };

        var changePageSize = function (targetPageSize) {
            currentPageSize.active = false;
            currentPageSize = targetPageSize;
            currentPageSize.active = true;
        };

        $scope.pageSize = pageSize;
        $scope.pages = pages;

        $scope.getPage = function (targetPage) {
            changePage(targetPage);
            $scope.getAllGroups();
        };

        $scope.changePageSize = function (targetPageSize) {
            changePageSize(targetPageSize);
            $scope.getAllGroups();
        };

        $scope.editGroup = function (id) {
            $location.path("recipients/edit-group/" + id);
        };

        $scope.getAllGroups = function () {
            groupsService.getAllGroups(skip, currentPageSize.value, sortField, sortDirection).then(function (response) {
                $scope.receivedGroups = response.data.items;
                allGroups = response.data.items;

                $scope.count = response.data.count;
                count = Math.ceil(response.data.count / currentPageSize.value);
                recalculatePages();
            });
        };

        var confirmDeleteModalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete',
            headerText: 'Delete Group?',
            bodyText: 'Are you sure you want to delete this group?'
        };

        $scope.removeGroup = function (id) {
            confirmService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                groupsService.removeGroup(id).then(function () {
                    $scope.getAllGroups();
                });
            });
        };

        $scope.getAllGroups();

        $scope.$watch('groupFilter', function (newVlaue) {
            if (newVlaue != undefined) {
                groupsService.getGroupsByQuery(
                    skip,
                    currentPageSize.value,
                    sortField,
                    sortDirection,
                    newVlaue)
                .then(function (response) {
                    $scope.receivedGroups = response.data.items;
                    allGroups = response.data.items;

                    $scope.count = response.data.count;
                    count = Math.ceil(response.data.count / currentPageSize.value);
                    recalculatePages();
                });
            }
        });

    }
})();
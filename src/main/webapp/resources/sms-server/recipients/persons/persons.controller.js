(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('personsCtrl', personsCtrl);

    personsCtrl.$inject = ['$scope', '$location', 'confirmService', '$routeParams', 'personsService'];
    function personsCtrl($scope, $location, confirmService, $routeParams, personsService) {
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
            firstName: sortConstants.notSorted,
            lastName: sortConstants.notSorted,
            phoneNumber: sortConstants.notSorted,
            email: sortConstants.notSorted
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
            $scope.getAllPersons();
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
            $scope.getAllPersons();
        };

        $scope.changePageSize = function (targetPageSize) {
            console.log(targetPageSize);
            changePageSize(targetPageSize);
            $scope.getAllPersons();
        };

        var personsCount = 0;
        $scope.persons = [];
        $scope.selectedPersons = [];

        var defaultPersonForm = {
            name: null,
            email: null,
            phoneNumber: null,
            personNumber: personsCount
        };

        var allPersons = [];

        $scope.addNewPersonForm = function () {
            $scope.persons.push(angular.copy(defaultPersonForm));
            personsCount++;
        };

        $scope.removePersonForm = function (index) {
            $scope.persons.splice(index, 1);
        };

        $scope.addPersons = function (recipients) {
            personsService.addPersons(recipients)
                .then(function (data) {
                    $location.path('/recipients');
                });
            $scope.getAllPersons();
        };

        var confirmDeleteModalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete',
            headerText: 'Delete Person?',
            bodyText: 'Are you sure you want to delete this person?'
        };

        $scope.removePerson = function (id) {
            confirmService.showModal({}, confirmDeleteModalOptions).then(function (result) {
                personsService.removePerson(id).then(function () {
                    $scope.getAllPersons();
                });
            });
        };

        $scope.getAllPersons = function () {
            personsService.getPersons(skip, currentPageSize, sortField, sortDirection).then(function (response) {
                $scope.receivedPersons = response.data.items;
                allPersons = response.data.items;

                $scope.count = response.data.count;
                count = Math.ceil(response.data.count / currentPageSize.value);
                recalculatePages();
            });
        };

        $scope.getAllPersons();

        $scope.addNewPersonForm();

        $scope.$watch('personsFilter', function (newVlaue) {

            if (newVlaue != undefined) {
                personsService.getPersonsByQuery(
                    skip,
                    currentPageSize.value,
                    sortField,
                    sortDirection,
                    newVlaue)
                .then(function (response) {
                    $scope.receivedPersons = response.data.items;
                    allPersons = response.data.items;

                    $scope.count = response.data.count;
                    count = Math.ceil(response.data.count / currentPageSize.value);
                    recalculatePages();
                });
            }
        });
    }
})();
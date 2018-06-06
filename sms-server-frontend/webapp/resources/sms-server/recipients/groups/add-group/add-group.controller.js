(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('addGroupCtrl', addGroupCtrl);

  addGroupCtrl.$inject = ['addGroupService', '$scope', '$location',
    '$routeParams', 'toaster'];

  function addGroupCtrl(addGroupService, $scope, $location, $routeParams,
      toaster) {
    $scope.receivedPersons = [];
    $scope.selectedPersons = [];

    $scope.getAllPersons = function () {
      addGroupService.getAllPersons().then(function (response) {
        $scope.receivedPersons = response.data;
      });
    };

    $scope.getGroup = function (groupId) {
      addGroupService.getGroup(id)
      .then(function (response) {
        $scope.selectedPersons = response.data.persons;
        $scope.groupName = response.data.name;
      });
    };

    $scope.getPersonsWithoutGroup = function (groupId) {
      addGroupService.getPersonsWithoutGroup(groupId)
      .then(function (response) {
        $scope.receivedPersons = response.data;
      });
    };

    $scope.selectPerson = function (index) {
      $scope.selectedPersons.push($scope.receivedPersons[index]);
      $scope.receivedPersons.splice(index, 1);
    };

    $scope.unselectPerson = function (index) {
      $scope.receivedPersons.push($scope.selectedPersons[index]);
      $scope.selectedPersons.splice(index, 1);
    };

    if ($routeParams.groupId === undefined) {
      $scope.getAllPersons();
      $scope.title = "Add new recipients group";
    } else {
      $scope.title = "Edit recipients group";
      var groupId = $routeParams.groupId;
      $scope.getGroup(groupId);
      $scope.getPersonsWithoutGroup(groupId);
    }

    $scope.saveGroup = function () {
      var groupName = $scope.groupName;

      if (groupName.indexOf(' ') >= 0) {
        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'Group name could not contains spaces',
          timeout: 0
        });

        return;
      }

      var groupToSave = {};

      if ($routeParams.groupId != undefined) {
        groupToSave.id = $routeParams.groupId;
      }

      groupToSave.persons = $scope.selectedPersons;
      groupToSave.name = $scope.groupName;

      addGroupService.saveGroup(groupToSave).then(function () {
        $scope.selectedPersons = [];
        $location.path('/recipients');
        $scope.getAllPersons();
      })
    };
  }
})();
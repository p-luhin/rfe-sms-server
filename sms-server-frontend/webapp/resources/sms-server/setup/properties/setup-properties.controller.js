(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('setupPropertiesCtrl', setupPropertiesCtrl);

  setupPropertiesCtrl.$inject = ['setupPropertiesService', '$scope',
    '$location', 'toaster'];

  function setupPropertiesCtrl(setupPropertiesService, $scope, $location,
      toaster) {
    var allProperties = [];

    setupPropertiesService.getProperties().then(function (response) {
      $scope.propertyGroups = [];

      for (var key in response.data) {
        $scope.propertyGroups.push(key);
      }

      allProperties = response.data;

      $scope.selectedPropertyGroup = $scope.propertyGroups[0];
      $scope.selectGroup();
    });

    $scope.saveProperties = function (properties) {
      allProperties[$scope.selectedPropertyGroup] = properties;
      setupPropertiesService.saveProperties(allProperties).then(
          function (response) {
            toaster.pop({
              type: 'success',
              title: 'Success',
              body: 'Properties Saved',
              timeout: 0
            });
          });
    };

    $scope.selectGroup = function () {
      var selectedGroup = $scope.selectedPropertyGroup;
      $scope.properties = allProperties[selectedGroup];
    }
  }
})();
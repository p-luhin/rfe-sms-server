(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('setupCtrl', setupCtrl);

  setupCtrl.$inject = ['$scope', '$location'];

  function setupCtrl($scope, $location) {
    $scope.openSetup = function (uri) {
      $location.path(uri);
    }
  }

})();
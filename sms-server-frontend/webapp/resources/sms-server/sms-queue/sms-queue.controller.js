(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('smsQueueCtrl', smsQueueCtrl);

  smsQueueCtrl.$inject = ['smsQueueService', '$scope', 'ConfirmModalService'];

  function smsQueueCtrl(smsQueueService, $scope, ConfirmModalService) {
    var getAllMessagesFromQueue = function () {
      smsQueueService.getAllMessagesInQueue().then(function (response) {
        $scope.queue = response.data;
      });
    };

    getAllMessagesFromQueue();

    var confirmDeleteModalOptions = {
      closeButtonText: 'Cancel',
      actionButtonText: 'Delete',
      headerText: 'Delete Sms From Queue?',
      bodyText: 'Are you sure you want to delete this sms from queue?'
    };

    $scope.removeFromQueue = function (id) {
      ConfirmModalService.showModal({}, confirmDeleteModalOptions).then(
          function (result) {
            smsQueueService.removeFromQueue(id).then(function (response) {
              getAllMessagesFromQueue();
            })
          });
    }
  }
})();
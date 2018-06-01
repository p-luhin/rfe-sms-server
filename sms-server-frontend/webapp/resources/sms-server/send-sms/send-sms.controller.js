(function () {
  'use strict';

  angular
  .module('sms-server')
  .controller('sendSmsCtrl', sendSmsCtrl)
  .directive('fileModel', fileModel);

  sendSmsCtrl.$inject = ['sendSmsService', '$scope', 'toaster', '$location'];

  function sendSmsCtrl(sendSmsService, $scope, toaster, $location) {
    var defaultSymbolsLeft = 160;
    $scope.symbolsLeft = defaultSymbolsLeft;
    $scope.smsCount = 1;

    $scope.sendSmsScope = $scope;

    $scope.messages = [];

    $scope.loading = true;

    $scope.activeTab = 1;

    sendSmsService.getSenderNames().then(function (response) {
      $scope.senderNames = response.data;

      if ($scope.senderNames.length != 0) {
        $scope.requestSenderName = $scope.senderNames[0];
      }
    });

    $scope.countSymbols = function () {
      recalculateSymbols();
      if ($scope.symbolsLeft <= 0) {
        $scope.smsCount++;
        recalculateSymbols();
      }

      if ($scope.symbolsLeft > defaultSymbolsLeft) {
        $scope.smsCount--;
        recalculateSymbols();
      }
    };

    $scope.sendSMS = function (smsObject) {
      $scope.loading = true;
      if (isSMSValid(smsObject)) {
        $scope.error = undefined;
      }

      console.log(smsObject);
      var sms = createBaseSMS();

      for (var obj in smsObject.recipient) {
        if (smsObject.recipient[obj].recipientType === undefined) {
          var receiver = {
            name: smsObject.recipient[obj].name,
            recipientType: 'NUMBER'
          };
          sms.recipients.push(receiver);
        } else {
          var receiver = {
            name: smsObject.recipient[obj].name,
            recipientType: smsObject.recipient[obj].recipientType
          };
          sms.recipients.push(receiver);
        }
      }

      sms.content = smsObject.content;

      sms.senderName = $scope.requestSenderName;

      sendSmsService.sendSMS(sms)
      .then(function (data) {
        if (data.data.error) {
          toaster.pop({
            type: 'error',
            title: 'SMS was not sent',
            body: data.data.lastError,
            timeout: 0
          });
          $location.path('/statistics').search({openFirst: true});
        } else if (data.data.inQueue == true) {
          toaster.pop({
            type: 'warning',
            title: 'Warning',
            body: 'Mute mode is enabled. Sms placed in queue',
            timeout: 0
          });
        } else {
          toaster.pop({
            type: 'success',
            title: 'Success',
            body: 'SMS sent successfully',
            timeout: 0
          });
          $location.path('/statistics').search({openFirst: true});
        }

        $scope.messages = [];
        smsObject.recipient = '';
        smsObject.content = '';
        sms = null;
        $scope.loading = false;
      });
    };

    $scope.showTab = function (tabIndex) {
      $scope.activeTab = tabIndex;
    };

    $scope.sendBulkSMS = function () {
      $scope.loading = true;
      var file = $scope.myFile;

      console.log(file);

      var fd = new FormData();
      fd.append('file', file);
      fd.append('senderName', $scope.requestSenderName);

      sendSmsService.sendBulkSMS(fd).then(function (data) {
        $scope.loading = false;
        toaster.pop({
          type: 'success',
          title: 'Success',
          body: 'SMS sent successfully',
          timeout: 0
        });
        $location.path('/statistics').search({openFirst: true});
      });
    };

    function isSMSValid(sms) {
      if (!sms || !sms.recipient) {

        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'You must select at least one recipient or recipients group',
          timeout: 0
        });

        return false;
      }
      if (!sms.content) {

        toaster.pop({
          type: 'error',
          title: 'Error',
          body: 'You must enter message',
          timeout: 0
        });

        return false;
      }
      return true;
    }

    function recalculateSymbols() {
      $scope.symbolsLeft = defaultSymbolsLeft * $scope.smsCount
          - $scope.sms.content.length;
    }

    function createBaseSMS() {
      var sms = {};
      sms.recipients = [];
      sms.smsParameters = {};
      sms.duplicateEmail = false;
      sms.smsContent = "";
      return sms;
    }

    $scope.loadRecipients = function (query) {
      return sendSmsService.loadRecipients(query)
      .then(function (response) {
        var recipients = response.data;
        return recipients;
      })
    };
  }

  fileModel.$inject = ['$parse'];

  function fileModel($parse) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind('change', function () {
          scope.$apply(function () {
            if (attrs.multiple) {
              modelSetter(scope, element[0].files);
            }
            else {
              modelSetter(scope, element[0].files[0]);
            }
          });
        });
      }
    };
  }

})();
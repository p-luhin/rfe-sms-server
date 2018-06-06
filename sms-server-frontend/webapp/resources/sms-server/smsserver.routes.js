(function () {
  'use strict';

  angular
  .module('sms-server')
  .config(routes);

  routes.$inject = ['$routeProvider'];

  function routes($routeProvider) {
    var SMS_SERVER_DIR = '';
    var TEMPLATE_POSTFIX = '.tmpl.html';
    $routeProvider
    .when('/statistics', {
      templateUrl: SMS_SERVER_DIR + 'statistics/statistics' + TEMPLATE_POSTFIX,
      controller: 'statisticsCtrl'
    })
    .when('/send-sms', {
      templateUrl: SMS_SERVER_DIR + 'send-sms/send-sms' + TEMPLATE_POSTFIX,
      controller: 'sendSmsCtrl'
    })
    .when('/login', {
      templateUrl: SMS_SERVER_DIR + 'login/login' + TEMPLATE_POSTFIX,
      controller: 'loginCtrl'
    })
    .when('/recipients', {
      templateUrl: SMS_SERVER_DIR + 'recipients/recipients' + TEMPLATE_POSTFIX
    })
    .when('/recipients/add', {
      templateUrl: SMS_SERVER_DIR + 'recipients/persons/add-persons/add-persons'
      + TEMPLATE_POSTFIX,
      controller: 'addPersonsCtrl'
    })
    .when('/recipients/add-group', {
      templateUrl: SMS_SERVER_DIR + 'recipients/groups/add-group/add-group'
      + TEMPLATE_POSTFIX,
      controller: 'addGroupCtrl'
    })
    .when('/recipients/edit-group/:groupId', {
      templateUrl: SMS_SERVER_DIR + 'recipients/groups/add-group/add-group'
      + TEMPLATE_POSTFIX,
      controller: 'addGroupCtrl'
    })
    .when('/setup', {
      templateUrl: SMS_SERVER_DIR + 'setup/setup' + TEMPLATE_POSTFIX,
      controller: 'setupCtrl'
    })
    .when('/setup/templates', {
      templateUrl: SMS_SERVER_DIR + 'setup/templates/setup-templates'
      + TEMPLATE_POSTFIX,
      controller: 'setupTemplatesCtrl'
    })
    .when('/setup/credentials', {
      templateUrl: SMS_SERVER_DIR + 'setup/credentials/setup-credentials'
      + TEMPLATE_POSTFIX,
      controller: 'setupCredentialsCtrl'
    })
    .when('/setup/users', {
      templateUrl: SMS_SERVER_DIR + 'setup/users/setup-users'
      + TEMPLATE_POSTFIX,
      controller: 'setupUsersCtrl'
    })
    .when('/setup/applications', {
      templateUrl: SMS_SERVER_DIR + 'setup/applications/setup-applications'
      + TEMPLATE_POSTFIX,
      controller: 'setupApplicationsCtrl'
    })
    .when('/setup/emailTemplates', {
      templateUrl: SMS_SERVER_DIR + 'setup/emailTemplates/setup-emailTemplates'
      + TEMPLATE_POSTFIX,
      controller: 'setupEmailTemplatesCtrl'
    })
    .when('/setup/properties', {
      templateUrl: SMS_SERVER_DIR + 'setup/properties/setup-properties'
      + TEMPLATE_POSTFIX,
      controller: 'setupPropertiesCtrl'
    })
    .when('/sms-queue', {
      templateUrl: SMS_SERVER_DIR + 'sms-queue/sms-queue' + TEMPLATE_POSTFIX,
      controller: 'smsQueueCtrl'
    })
    .otherwise({
      redirectTo: '/statistics'
    });
  }
})();
(function () {
  'use strict';

  angular
  .module('sms-server', ['ngRoute',
    'ngCookies',
    'ngProgress',
    'ngStorage',
    'ngTagsInput',
    'ui.bootstrap',
    'ui.bootstrap.tpls',
    'ui.bootstrap.tooltip',
    'ngSanitize',
    'ui.select',
    'angular-md5',
    'toaster'
  ])
  .constant('localStorageAuthName', 'authentication')
  .constant('cookieAuthName', 'auth_token');
})();
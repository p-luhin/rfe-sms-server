(function () {
  'use strict';

  angular
  .module('sms-server')
  .config(config);

  config.$inject = ['$httpProvider'];

  function config($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
  }
})();
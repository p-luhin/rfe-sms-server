(function () {
  'use strict';

  angular
  .module('sms-server')
  .run(run);

  run.$inject = ['loginService'];

  function run(loginService) {
    loginService.account();
  }
})();
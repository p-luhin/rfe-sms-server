(function () {
  'use strict';

  angular
  .module('sms-server')
  .run(run);

  run.$inject = ['$cookies'];

  function run($cookies) {
    if (!$cookies.get('auth_token')) {
      if (localStorage.getItem('authentication')) {
        var auth = JSON.parse(localStorage.getItem('authentication'));
        $cookies.put('auth_token', auth.token);
      }
    }
  }
})();
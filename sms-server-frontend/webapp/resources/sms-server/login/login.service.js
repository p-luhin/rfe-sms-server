(function () {
  'use strict';

  angular
  .module('sms-server')
  .factory('loginService', loginService);

  loginService.$inject = ['RestURLFactory', '$http', '$rootScope', '$cookies',
    '$location', 'localStorageAuthName', 'cookieAuthName'];

  function loginService(RestURLFactory, $http, $rootScope, $cookies, $location,
      localStorageAuthName, cookieName) {

    return {
      logIn: logIn,
      setAuthentication: setAuthentication,
      logout: logout,
      isAuthenticated: isAuthenticated,
      getCurrentUserName: getCurrentUserName
    };

    function logIn(user) {
      var data = 'j_username=' + encodeURIComponent(user.username) +
          '&j_password=' + encodeURIComponent(user.password);

      return $http.post(RestURLFactory.AUTHENTICATE, data, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      });
    }

    function setAuthentication() {
      $rootScope.authenticated = true;
    }

    function logout() {
      $http.post(RestURLFactory.LOGOUT, '').then(function (promise) {
        $cookies.remove(cookieName);
        $rootScope.authenticated = false;
        localStorage.removeItem(localStorageAuthName);
        $location.path('/login');
      });
    }

    function isAuthenticated() {
      return $rootScope.authenticated;
    }

    function getCurrentUserName() {
      var auth = JSON.parse(localStorage.getItem(localStorageAuthName));
      return auth.username;
    }
  }

})();
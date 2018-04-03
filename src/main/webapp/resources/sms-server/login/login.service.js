(function () {
    'use strict';

    angular
        .module('sms-server')
        .factory('loginService', loginService);

    loginService.$inject = ['RestURLFactory', '$http', '$rootScope', '$cookies', '$location', 'localStorageAuthName', 'cookieAuthName'];

    function loginService(RestURLFactory, $http, $rootScope, $cookies, $location, localStorageAuthName, cookieName) {

        return {
            logIn: logIn,
            setAuthentication: setAuthentication,
            logout: logout,
            isAuthenticated: isAuthenticated,
            getCurrentUserName: getCurrentUserName
        };

        function logIn(user) {
            return $http.post(RestURLFactory.AUTHENTICATE, user);
        }

        function setAuthentication(authentication) {
            $cookies.put(cookieName, authentication.token);
            $rootScope.authenticated = true;
            authentication.authenticated = true;
            localStorage.setItem(localStorageAuthName, JSON.stringify(authentication));
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
            if ($rootScope.authenticated != undefined) {
                return $rootScope.authenticated;
            } else {
                var auth = JSON.parse(localStorage.getItem(localStorageAuthName));
                if (auth != undefined) {
                    return auth.authenticated;
                } else {
                    return false;
                }
            }
        }

        function getCurrentUserName() {
            var auth = JSON.parse(localStorage.getItem(localStorageAuthName));
            return auth.username;
        }
    }

})();
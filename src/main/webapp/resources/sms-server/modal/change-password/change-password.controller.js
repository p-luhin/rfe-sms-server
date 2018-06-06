(function () {
    'use strict';

    angular
        .module('sms-server')
        .controller('changePasswordCtrl', changePasswordCtrl);

    changePasswordCtrl.$inject = ['changePasswordService', '$scope', '$uibModalInstance', 'md5', 'toaster'];
    function changePasswordCtrl(changePasswordService, $scope, $uibModalInstance, md5, toaster) {
        $scope.selected = {};
        $scope.selected.credentials = {};

        $scope.changePassword = function (oldPassword, newPassword, anotherNewPassword) {
            if (!oldPassword ||
                !newPassword ||
                !anotherNewPassword ||
                newPassword !== anotherNewPassword ||
                newPassword === oldPassword
            ) {
                toaster.pop({
                    type: 'error',
                    title: 'Error',
                    body: 'Please, fill all required fields with correct data.',
                    timeout: 0
                });

                return;
            }

            oldPassword = md5.createHash(oldPassword);
            newPassword = md5.createHash(newPassword);

            var passObj = {
                oldPassword: oldPassword,
                newPassword: newPassword
            };

            changePasswordService.changePassword(passObj)
                .success(function (data) {
                    $uibModalInstance.dismiss();
                    toaster.pop({
                        type: 'success',
                        title: 'Success',
                        body: 'Password changed!',
                        timeout: 0
                    });
                })
                .error(function (data) {
                    toaster.pop({
                        type: 'error',
                        title: 'Error',
                        body: 'Old password is invalid',
                        timeout: 0
                    });
                });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
    }
})();
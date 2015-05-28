'use strict';

angular.module('oculuserviceApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });

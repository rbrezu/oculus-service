'use strict';

angular.module('oculuserviceApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



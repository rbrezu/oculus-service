'use strict';

angular.module('oculuserviceApp')
    .factory('OculusUser', function ($resource) {
        return $resource('api/oculusUsers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

'use strict';

angular.module('oculuserviceApp')
    .factory('OculusEvent', function ($resource) {
        return $resource('api/oculusEvents/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.entered_time != null) data.entered_time = new Date(data.entered_time);
                    if (data.exit_time != null) data.exit_time = new Date(data.exit_time);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

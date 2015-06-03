'use strict';

angular.module('oculuserviceApp')
    .controller('OculusEventDetailController', function ($scope, $stateParams, OculusEvent, OculusUser) {
        $scope.oculusEvent = {};
        $scope.load = function (id) {
            OculusEvent.get({id: id}, function(result) {
              $scope.oculusEvent = result;
            });
        };
        $scope.load($stateParams.id);
    });

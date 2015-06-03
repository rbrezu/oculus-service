'use strict';

angular.module('oculuserviceApp')
    .controller('OculusUserDetailController', function ($scope, $stateParams, OculusUser, OculusEvent) {
        $scope.oculusUser = {};
        $scope.load = function (id) {
            OculusUser.get({id: id}, function(result) {
              $scope.oculusUser = result;
            });
        };
        $scope.load($stateParams.id);
    });

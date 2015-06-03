'use strict';

angular.module('oculuserviceApp')
    .controller('OculusUserController', function ($scope, OculusUser, OculusEvent, ParseLinks) {
        $scope.oculusUsers = [];
        $scope.oculusevents = OculusEvent.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OculusUser.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.oculusUsers.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.oculusUsers = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            OculusUser.get({id: id}, function(result) {
                $scope.oculusUser = result;
                $('#saveOculusUserModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.oculusUser.id != null) {
                OculusUser.update($scope.oculusUser,
                    function () {
                        $scope.refresh();
                    });
            } else {
                OculusUser.save($scope.oculusUser,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            OculusUser.get({id: id}, function(result) {
                $scope.oculusUser = result;
                $('#deleteOculusUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OculusUser.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteOculusUserConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveOculusUserModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oculusUser = {first_name: null, family_name: null, sex: null, age: null, smoking_time: null, time_since_last_cigarette: null, form_score: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

'use strict';

angular.module('oculuserviceApp')
    .controller('OculusEventController', function ($scope, OculusEvent, OculusUser, ParseLinks) {
        $scope.oculusEvents = [];
        $scope.oculususers = OculusUser.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OculusEvent.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.oculusEvents.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.oculusEvents = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            OculusEvent.get({id: id}, function(result) {
                $scope.oculusEvent = result;
                $('#saveOculusEventModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.oculusEvent.id != null) {
                OculusEvent.update($scope.oculusEvent,
                    function () {
                        $scope.refresh();
                    });
            } else {
                OculusEvent.save($scope.oculusEvent,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            OculusEvent.get({id: id}, function(result) {
                $scope.oculusEvent = result;
                $('#deleteOculusEventConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OculusEvent.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteOculusEventConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveOculusEventModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oculusEvent = {name: null, type: null, location: null, entered_time: null, exit_time: null, duration: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

'use strict';

angular.module('oculuserviceApp')
    .controller('AuthorController', function ($scope, Author, Book, ParseLinks) {
        $scope.authors = [];
        $scope.books = Book.query();
        $scope.loadAll = function() {
            Author.query(function(result) {
                /*$scope.links = ParseLinks.parse(headers('link'));

                for (var i = 0; i < result.length; i++) {
                    $scope.authors.push(result[i]);
                }*/
                $scope.authors = result;
            });
        };
        /*$scope.reset = function() {
            $scope.page = 1;
            $scope.authors = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };*/
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
                $('#saveAuthorModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.author.id != null) {
                Author.update($scope.author,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Author.save($scope.author,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
                $('#deleteAuthorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAuthorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveAuthorModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.author = {name: null, birthday: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

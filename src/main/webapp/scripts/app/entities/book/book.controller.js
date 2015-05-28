'use strict';

angular.module('oculuserviceApp')
    .controller('BookController', function ($scope, Book, Author) {
        $scope.books = [];
        $scope.authors = Author.query();
        $scope.loadAll = function() {
            Book.query(function(result) {
               $scope.books = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Book.get({id: id}, function(result) {
                $scope.book = result;
                $('#saveBookModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.book.id != null) {
                Book.update($scope.book,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Book.save($scope.book,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Book.get({id: id}, function(result) {
                $scope.book = result;
                $('#deleteBookConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Book.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBookConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBookModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.book = {title: null, price: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

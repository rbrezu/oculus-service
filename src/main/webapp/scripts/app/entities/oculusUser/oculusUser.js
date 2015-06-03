'use strict';

angular.module('oculuserviceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oculusUser', {
                parent: 'entity',
                url: '/oculusUser',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'oculuserviceApp.oculusUser.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oculusUser/oculusUsers.html',
                        controller: 'OculusUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oculusUser');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oculusUserDetail', {
                parent: 'entity',
                url: '/oculusUser/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'oculuserviceApp.oculusUser.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oculusUser/oculusUser-detail.html',
                        controller: 'OculusUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oculusUser');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('oculuserviceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oculusEvent', {
                parent: 'entity',
                url: '/oculusEvent',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'oculuserviceApp.oculusEvent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oculusEvent/oculusEvents.html',
                        controller: 'OculusEventController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oculusEvent');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oculusEventDetail', {
                parent: 'entity',
                url: '/oculusEvent/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'oculuserviceApp.oculusEvent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oculusEvent/oculusEvent-detail.html',
                        controller: 'OculusEventDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oculusEvent');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('bankApp')
    .controller('ClientsCtrl', function ($scope, $http) {
        $scope.newClient = {username: ''};

        $scope.listClients = function () {
            $http.get('/api/client')
                .success(function (clients) {
                    $scope.clients = clients;
                });
        };
        $scope.listClients();

        $scope.addClient = function () {
            var username = $scope.newClient.username;
            if (username) {
                var client = $scope.newClient;
                $http.put('/api/client/' + username, client).success(function () {
                    $scope.listClients();
                    $http.put('/api/account', {client: client});
                    $scope.newClient = {username: ''};
                });
            }
        };
    })
    .controller('AccountsCtrl', function ($scope, $http, $routeParams) {
        $scope.transactions = [{amount: null, type: null, error: null}];
        $scope.alerts = {
            negative: {
                key: 'NEGATIVE',
                msg: 'les valeurs négatives ne sont pas autorisées',
                show: false
            },
            zero: {
                key: 'ZERO',
                msg: 'zéro n\'est pas autorisé',
                show: false
            }
        };

        function updateGlobalError() {
            var cases = $scope.transactions.map(item => item.error);
            $scope.alerts.negative.show = cases && cases.some(item => item === $scope.alerts.negative.key);
            $scope.alerts.zero.show = cases && cases.some(item => item === $scope.alerts.zero.key);
        }

        $scope.$watch('transactions', function (newValue) {
            for (var item of newValue) {
                if (item.amount < 0) {
                    item.error = $scope.alerts.negative.key;
                } else if (item.amount === 0) {
                    item.error = $scope.alerts.zero.key;
                } else {
                    item.error = null;
                }
            }
            updateGlobalError();
        }, true);

        $scope.getAccountBy = function (username) {
            $http.get('/api/account/' + username)
                .success(function (account) {
                    $scope.account = account;
                    $scope.transactions = [{amount: null, type: 'DEPOSIT', error: null}]
                });
        };

        $scope.updateAccount = function () {
            for (var transaction of $scope.transactions) {
                delete transaction.error;
            }
            $scope.account.transactions.transactions = $scope.account.transactions.transactions.concat($scope.transactions);
            $http.put('/api/account/', $scope.account).success(function () {
                $scope.getAccountBy($routeParams.username);
            });
        };

        if ($routeParams.username) {
            $scope.getAccountBy($routeParams.username);
        }

        $scope.newTransaction = function () {
            $scope.addTransaction({amount: null, type: 'DEPOSIT', error: null});
        };

        $scope.addTransaction = function (transaction) {
            $scope.transactions.push(transaction);
        };
    });

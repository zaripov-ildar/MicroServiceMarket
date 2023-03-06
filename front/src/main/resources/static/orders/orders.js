angular.module('market').controller('ordersController', function ($scope, $http, $localStorage) {
    $scope.loadOrders = function () {
        $http({
            method:"GET",
            url:'http://localhost:5555/core/api/v1/orders',
            headers: $localStorage.headers
        }).then(function (response){
            $scope.orders = response.data;
        })
    };

    $scope.loadOrders();
});
angular.module('market').controller('productsController', function ($scope, $http) {
    $scope.filter = {
        page: 1,
        pageSize: 5,
        minPrice: null,
        maxPrice: null,
        titlePart: null
    };

    $scope.loadProducts = function () {
        console.log($scope.filter);
        $http({
            method: 'GET',
            url: 'http://localhost:5555/core/api/v1/products',
            params: $scope.filter
        }).then(function (response) {
            console.log(response);
            $scope.products = response.data.content;
        });
    };

    $scope.addToCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId + 'add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }


    $scope.loadProducts();
});
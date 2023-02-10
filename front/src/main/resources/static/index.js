angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    if ($localStorage.marchMarketUser) {
        try {
            let jwt = $localStorage.marchMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.marchMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        if ($localStorage.marchMarketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
        }
    }

    $scope.tryToAuth = function () {
        console.log($scope.user)
        $http.post('http://localhost:5555/auth/api/v1/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.marchMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        return !!$localStorage.marchMarketUser;
    };

    $scope.loadProducts = function () {
        $http.get('http://localhost:5555/core/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
                console.log($scope.cart);
            });
    };

    $scope.addToCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createOrder = function () {
        console.log("trying to send order..."+ $localStorage.marchMarketUser)
        $http.post('http://localhost:5555/core/api/v1/orders', {}, {headers: {'username': $localStorage.marchMarketUser.username}})
            .then(function (response) {
                alert("Order was created");
                $scope.loadCart();
            });
    }

    // $scope.deleteProduct = function (id) {
    //     $http.delete('http://localhost:5555/core/api/v1/products/' + id)
    //         .then(function (response) {
    //             $scope.loadProducts();
    //         });
    // }

    // $scope.createNewProduct = function () {
    //     $http.post('http://localhost:5555/core/api/v1/products', $scope.newProduct)
    //         .then(function (response) {
    //             $scope.newProduct = null;
    //             $scope.loadProducts();
    //         });
    // }

    $scope.loadProducts();
    $scope.loadCart();
});
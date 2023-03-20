(function () {
        angular.module('market', ['ngStorage', 'ngRoute'])
            .config(config)
            .run(run);


        function config($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'welcome/welcome.html',
                    controller: 'welcomeController'
                })
                .when('/products', {
                    templateUrl: 'products/products.html',
                    controller: 'productsController'
                })
                .when('/cart', {
                    templateUrl: 'cart/cart.html',
                    controller: 'cartController'
                })
                .when('/orders', {
                    templateUrl: 'orders/orders.html',
                    controller: 'ordersController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        }

        function getUrlVars() {
            const vars = {};
            window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
                vars[key] = value;
            });
            return vars;
        }

        function parseJwt(token) {
            try {
                return JSON.parse(atob(token.split('.')[1]));
            } catch (e) {
                return null;
            }
        }

        function isTokenExpired(jwt) {
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            console.log('curr time:' + currentTime);
            console.log('exp time:' + payload.exp);
            return currentTime > payload.exp
        }


        function run($rootScope, $http, $localStorage) {
            let code = getUrlVars()['code'];
            if (code) {
                const params = new URLSearchParams({
                    grant_type: 'authorization_code',
                    client_id: 'marketUnsafe',
                    code: code,
                    redirect_url: 'http://localhost:8019/front'

                });
                $http({
                    method: "POST",
                    url: 'http://localhost:8150/realms/master/protocol/openid-connect/token',
                    data: params.toString(),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function (response) {
                    if (response.data.access_token) {

                        $localStorage.headers = {
                            Authorization: 'Bearer ' + response.data.access_token,
                            refresh_token: response.data.refresh_token,
                            username: parseJwt(response.data.access_token).sub,
                            'Content-type': 'application/x-www-form-urlencoded'
                        }
                        window.location.href = '/front';
                    }
                })
            }
            if ($localStorage.headers) {
                let token = $localStorage.headers.Authorization.substring(7);
                if (isTokenExpired(token)) {
                    delete $localStorage.headers;
                    $http.defaults.headers.common.Authorization = '';
                    window.location.href = '/front';
                    alert("Token expired!")
                }
                if ($localStorage.headers) {
                    $http.defaults.headers.common = $localStorage.headers
                }
            }

            if (!$localStorage.guestCartId) {
                $http.get('http://localhost:5555/cart/api/v1/cart/generateId')
                    .then(function (response) {
                        $localStorage.guestCartId = response.data.value;
                    });
            }
        }
    }
)
();

angular.module('market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {

        $scope.tryToAuth = function () {
            window.location.href = 'http://localhost:8150/realms/master/protocol/openid-connect/auth?response_type=code&client_id=marketUnsafe&redirect_url=http://localhost:8019/front';
        };

        $scope.tryToLogout = function () {
            delete $localStorage.headers;
            window.location.href = 'http://localhost:8150/realms/master/protocol/openid-connect/logout?client_id=marketUnsafe&post_logout_redirect_uri=http://localhost:8019/front';
        };

        $scope.isUserLoggedIn = function () {
            return $localStorage.headers;
        };
    }
)
;
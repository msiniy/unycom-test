angular.module("orders", [])

.component("orderList", {
    templateUrl: "orders/list.template.html",
    controller: ["$http", function OrderListController($http) {
        var self = this;
        $http.get("/api/v1/orders").then(function(response) {
            self.orders = response.data;
        });
    }]
}).component("orderDetails", {
    templateUrl: "orders/details.template.html",
    controller: ["$routeParams", "$http", function OrderDetailsController($routeParams, $http) {
        var self = this;
        $http.get("/api/v1/orders/" + $routeParams.orderId).then(function(response) {
            self.order = response.data;
        });
    }]
});
var customers = require("./customers/customers.module.js");
var orders = require("./orders/orders.module.js");

angular.module('unycomApp', ["ngRoute", "customers", "orders"])
.config(["$routeProvider", function config($routeProvider) {
    $routeProvider.when("/customers", {
        template: "<customer-list></customer-list>"
    }).when("/customers/:code", {
        template: "<customer-details></customer-details>"
    }).when("/orders", {
        template: "<order-list></order-list>"
    }).when("/orders/:orderId", {
        template: "<order-details></order-details>"
    }).otherwise("/customers");
}]);
angular.module("customers", [])
.component("customerList", {
    templateUrl: "customers/list.template.html",
    controller: ["$http", function CustomerListController($http) {
        // For the real app it would be better to implement custom REST service.
        var self = this;
        $http.get("/api/v1/customers").then(function(response) {
            self.customers = response.data;
        });
    }]
}).component("customerDetails", {
    templateUrl: "customers/details.template.html",
    controller: ["$routeParams", "$http", function CustomerDetailsController($routeParams, $http) {
        var self = this;
        $http.get("/api/v1/customers/" + $routeParams.code).then(function(response) {
            self.customer = response.data;
        });
    }]
});
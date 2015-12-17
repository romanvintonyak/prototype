var epamcscockpit = angular.module("epamcscockpit", ["ngRoute", "checklist-model", "epamcscockpitResource", "epamcscockpitFilters"]);
var defaultErrrMsg = "An error occurred while loading the data";

function fillConstants($scope) { // todo quick and dirty hack, waiting for config service
    $scope.ticketPriorities = ['Low', 'Medium', 'High'];
    $scope.ticketStates = ['New', 'Open', 'Closed'];
    $scope.ticketCategories = [null, 'Problem', 'Incident', 'Complaint', 'Fraud', 'Note'];
    $scope.ticketLevels = ['All', 'Sales', 'Service', 'Automated', 'Interactive', 'Physical store CS transfer'];
    $scope.ticketGroup = ['My Group', 'All Groups', 'Unassigned'];
    $scope.ticketAgent = ['Assigned to me', 'All Group Users', 'Unassigned'];
    $scope.ticketEventReason = ["Complaint", "Update", "FirstContact"];
    $scope.ticketInterventions = ['IM', 'E-mail', 'Call'];
    $scope.ticketSorts = {
        'ticketId': 'Ticket ID',
        'creationTime': 'Date Created',
        /*'customerDisplayName': 'Customer Name',*/
        'modifyTime': "Time Modified"
    };
}


epamcscockpit.config(["$routeProvider", "$httpProvider", function ($routeProvider, $httpProvider) {
    $routeProvider
        .when("/", {
            templateUrl: 'template/ticket_pool.html',
            controller: 'TicketPoolCtrl'
        })
        .when("/advanced_search", {
            templateUrl: "template/advanced_search.html"
        })
        .when("/ticket/:ticketId", {
            templateUrl: "template/ticket_details.html",
            controller: 'TicketDetailsCtrl'
        })
        .when("/customer/:customerId", {
            templateUrl: "template/customer_details.html",
            controller: 'CustomerDetailsCtrl'
        })
        .when("/order/:orderCode", {
            templateUrl: "template/order_details.html",
            controller: 'OrderDetailsCtrl'
        })
        .when("/ticket_create", {
            templateUrl: "template/ticket_create.html"
        })
        .when("/order_create", {
            templateUrl: "template/order_create.html"
        })
        .when("/customer_create", {
            templateUrl: "template/customer_create.html"
        })
        .otherwise({
            redirectTo: "/"
        });
}]);

epamcscockpit.controller("TicketPoolCtrl", function ($scope, $http, $interval, $filter,
                                                     TicketsResource, TicketCountResource, ConfigResource) {
    fillConstants($scope);
    $scope.errorMsg = "";

    $scope.ticketStore = [];
    $scope.ticketSearchCriteria = {};

    $scope.clearTicketSearchCriteria = function () {
        var sc = $scope.ticketSearchCriteria;
        sc.categories = [];
        sc.agent = [];
        sc.group = [];
        sc.state = [];
        sc.levels = [];
        sc.priority = [];
        sc.sortName = [];
        sc.sortReverse = false;
    };
    
    $scope.clearTicketSearchCriteria();

    $scope.updateTicketStore = function () {
        $scope.ticketCount = TicketCountResource.get();
        
        ConfigResource.get( 
            function (data, status, headers, config) {
                $scope.ticketConfig = data;
            },
            function () {
                $scope.errorMsg = defaultErrrMsg;
            }
        );
        
        
        TicketsResource.query(
            $scope.ticketSearchCriteria,
            function (data, status, headers, config) {
                var curDate = new Date();
                angular.forEach(data, function (ticket) {
                    ticket.creationTime = new Date(ticket.creationTime);
                });
                $scope.ticketStore = data;
            },
            function () {
                $scope.errorMsg = defaultErrrMsg;
            }
        )
    };

    $scope.updateTicketStore();

    // --- sort-related functions
    $scope.sortField = 'ticketId'; // user requested sort
    $scope.sortFilterField = 'ticketId'; // real sorter for angular, sort field for server in SC
    $scope.sortReverse = false;

    $scope.isServerSort = function () { // todo total cannot be correct criteria to distinguish
        return true;//$scope.ticketCount.total > 1;
    };

    $scope.order = function () {
        if ($scope.isServerSort()) {
            $scope.ticketSearchCriteria.sortName = $scope.sortField;
            $scope.sortFilterField = [];
            $scope.updateTicketStore();
        } else {
            $scope.ticketSearchCriteria.sortName = [];
            $scope.sortFilterField = $scope.sortField;
        }
    };

    $scope.flipSort = function () {
        $scope.sortReverse = !$scope.sortReverse;
        $scope.ticketSearchCriteria.sortReverse = $scope.sortReverse;
        if ($scope.isServerSort())
            $scope.updateTicketStore();

    };
});

epamcscockpit.controller("TicketDetailsCtrl", function ($scope, $http, $routeParams, TicketsResource,
                                                        TicketChangeStateResource) {
    fillConstants($scope);
    TicketsResource.get({
            ticketId: $routeParams.ticketId
        }, function (data, status, headers, config) {
            $scope.ticket = data;
            if (data.state == "Open") {
                $scope.hideCloseButton = false;
            }
        },
        function () {
            $scope.errorMsg = defaultErrrMsg
        });

    $scope.showCloseForm = function () {
        $scope.showForm = !$scope.showForm;
    };

    $scope.closeTicket = function () {
        $scope.ticketStateHolder = {newState: "Closed", comment: $scope.comment};
        $scope.ticket = TicketChangeStateResource.update({ticketId: $scope.ticket.ticketId}, $scope.ticketStateHolder);
        $scope.showForm = false;
        $scope.hideCloseButton = true;
    }

    $scope.showForm = false;
    $scope.hideCloseButton = true;
});


epamcscockpit.controller("TicketCreateCtrl", function ($scope, $location, $http, TicketCreateResource) {
    fillConstants($scope);
    $scope.newTicket = {
        category: $scope.ticketCategories[1],
        priority: $scope.ticketPriorities[1]
    };
    $scope.newEvent = {
        interventionType: $scope.ticketInterventions[1],
        reason: $scope.ticketEventReason[1]
    };


    $scope.addTicket = function () {
        TicketCreateResource.save(
            {newTicket: $scope.newTicket, creationEvent: $scope.newEvent},
            function (data, status, headers, config) {
                console.log(data);
                $location.path("/ticket/" + data.ticketId);
            },
            function () {
                $scope.errorMsg = defaultErrrMsg
            }
        )
    }
});

epamcscockpit.controller("OrderDetailsCtrl", function ($scope, $http, $routeParams, OrdersResource) {
    OrdersResource.get({
            orderCode: $routeParams.orderCode
        },
        function (data, status, headers, config) {
            $scope.order = data;
        },
        function () {
            $scope.errorMsg = defaultErrrMsg
        });

});

epamcscockpit.controller("CustomerDetailsCtrl", function (CustomersResource, $http, $scope, $routeParams) {
    $scope.customer = undefined;

    CustomersResource.get({
        id: $routeParams.customerId
    }, function (data) {
        $scope.customer = data
    }, function (err) {
        $scope.errorMsg = defaultErrrMsg
    });
});




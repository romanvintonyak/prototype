var epamcscockpit = angular.module("epamcscockpit", ["ngRoute","checklist-model","epamCustomersResource","epamcscockpitFilters"]);

epamcscockpit.config(["$routeProvider","$httpProvider",function($routeProvider,$httpProvider){
	$routeProvider
		.when("/",{
			 templateUrl: 'template/ticket_pool.html',
		     controller: 'TicketPoolCtrl',
		})
		.when("/advanced_search",{
			templateUrl: "template/advanced_search.html",
		})
		.when("/ticket/:ticketId",{
			templateUrl: "template/ticket_details.html",
		    controller: 'TicketDetailsCtrl'
		})
		.when("/customer/:customerId",{
			templateUrl: "template/customer_details.html",
		    controller: 'CustomerDetailsCtrl'
		})
		.when("/order/:orderCode",{
			templateUrl: "template/order_details.html",
			controller: 'OrderDetailsCtrl'
		})
		.when("/ticket_create",{
			templateUrl: "template/ticket_create.html",
		    
		})
		.when("/order_create",{
			templateUrl: "template/order_create.html",
		})
		.when("/customer_create",{
			templateUrl: "template/customer_create.html",
		})
		.otherwise({
	        redirectTo: "/"
	    });
}]);

epamcscockpit.controller("TicketPoolCtrl", function($scope, $http,$interval,TicketsResource) {
	$scope.ticketStore = [];
	$scope.ticketSearchCriteria={
			priorities:[]
	}
	$scope.ticketPriorities = ['Low','Medium','High']; 
	
	$scope.updateTicketStore = function(){
		TicketsResource.query(
				$scope.ticketSearchCriteria,		
				function(data, status, headers, config){
					var curDate = new Date()
					angular.forEach(data, function(ticket) {
						ticket.creationTime = new Date(ticket.creationTime)
					})
					$scope.ticketStore = data
				},
				function(){
					// Handle error here
				}
		)
	};
	$scope.updateTicketStore()
	
	$scope.clearTicketSearchCriteria = function() {
	    $scope.ticketSearchCriteria.priorities = [];
	  };
});

epamcscockpit.controller("TicketDetailsCtrl", function($scope, $http, $routeParams,TicketsResource) {
	TicketsResource.get(
			{
				ticketId: $routeParams.ticketId
			},
			function(data, status, headers, config){
				$scope.ticket = data;
			},
			function(){
				// Handle error here
			}
	);
	
});


epamcscockpit.controller("OrderDetailsCtrl", function($scope, $http, $routeParams,OrdersResource) {
	OrdersResource.get(
			{
				orderCode: $routeParams.orderCode
			},
			function(data, status, headers, config){
				$scope.order = data;
			},
			function(){
				// Handle error here
			}
	);
	
});



epamcscockpit.controller("CustomerDetailsCtrl", function(CustomersResource,$http, $scope, $routeParams) {
	$scope.customer = undefined;
	$scope.customerAddress = undefined;
	
	CustomersResource.get({
		 id: $routeParams.customerId,
	 }, function(data) { //successful response here
		 $scope.customer=data
		 console.log($scope.customer)
	 }, function(err) {
		 // Handle error here
	 });
});




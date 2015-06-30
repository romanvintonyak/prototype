var epamcscockpit = angular.module("epamcscockpit", ["ngRoute","checklist-model","epamcscockpitResource","epamcscockpitFilters"]);
var defaultErrrMsg = "An error occurred while loading the data";
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
	$scope.ticketPriorities = ['Low','Medium','High']; 
	$scope.ticketStates = ['New','Open','Closed'];
	$scope.ticketCategories = [null,'Problem','Incident','Complaint','Fraud','Note'];
	$scope.ticketLevels = ['All','Sales','Service','Automated','Interactive','Physical store CS transfer'];
	$scope.ticketGroup = ['My Group','All Groups','Unassigned'];
	$scope.ticketAgent = ['Assigned to me','All Group Users','Unassigned'];
	
	$scope.errorMsg="";
	
	$scope.ticketStore = [];
	$scope.ticketSearchCriteria={
			agent:[],
			group:[],
			levels:[],
			priorities:[],
			states:[],
			categories:[]
	}
	
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
					$scope.errorMsg=defaultErrrMsg
				}
		)
	};
	$scope.updateTicketStore()
	
	$scope.clearTicketSearchCriteria = function() {
	    $scope.ticketSearchCriteria.priorities = [];
	  };
});

epamcscockpit.controller("TicketDetailsCtrl", function($scope, $http, $routeParams,TicketsResource) {
	TicketsResource.get({
			ticketId: $routeParams.ticketId
		},
		function(data, status, headers, config){
			$scope.ticket = data;
		},
		function(){
			$scope.errorMsg=defaultErrrMsg
		});
	
});


epamcscockpit.controller("OrderDetailsCtrl", function($scope, $http, $routeParams,OrdersResource) {
	OrdersResource.get({
			orderCode: $routeParams.orderCode
		},
		function(data, status, headers, config){
			$scope.order = data;
		},
		function(){
			$scope.errorMsg=defaultErrrMsg
		});
	
});



epamcscockpit.controller("CustomerDetailsCtrl", function(CustomersResource,$http, $scope, $routeParams) {
	$scope.customer = undefined;
	
	CustomersResource.get({
		 id: $routeParams.customerId,
	 }, function(data) { 
		 $scope.customer=data
	 }, function(err) {
		 $scope.errorMsg=defaultErrrMsg
	 });
});




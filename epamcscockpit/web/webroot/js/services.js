angular.module("epamcscockpitResource", [ "ngResource" ])
	.factory("CustomersResource", ["$resource", function($resource) {
		var resourseUrl = "/epamcustomer/v1/customers/";
		return $resource(resourseUrl+':id',
			{
				id: '@id'
			},
			{
				getAddress: {
					method: 'GET',
					isArray: true,
					url:resourseUrl+':id/address'
				},
				
			});
	}])
	.factory("TicketsResource", ["$resource", function($resource) {
		var resourseUrl = "/epamticket/v1/tickets";
		return $resource(resourseUrl+'/:ticketId',
			{
				id: '@ticketId'
			});
	}])
	.factory("OrdersResource", ["$resource", function($resource) {
		var resourseUrl = "/epamorder/v1/orders";
		return $resource(resourseUrl+'/:orderCode',
			{
				id: '@orderCode'
			});
	}]);
    



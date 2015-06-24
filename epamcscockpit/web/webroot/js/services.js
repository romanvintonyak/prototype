angular.module("epamCustomersResource", [ "ngResource" ])
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
		var resourseUrl = "/epamticket/tickets";
		return $resource(resourseUrl+':id',
			{
				id: '@id'
			});
	}]);
    



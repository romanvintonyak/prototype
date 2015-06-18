angular.module("epamCustomersResource", [ "ngResource" ])
	.factory("CustomersResource", ["$resource", function($resource) {
		var resourseUrl = "/epamcustomer/v1/customers/";
		console.log("initCustomersResource");
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
	}]);
    



angular.module("epamcscockpitResource", ["ngResource"])
    .factory("CustomersResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamcustomer/v1/customers/";
        return $resource(resourseUrl + ':id',
            {
                id: '@id'
            },
            {
                getAddress: {
                    method: 'GET',
                    isArray: true,
                    url: resourseUrl + ':id/address'
                }

            });
    }])
    .factory("TicketsResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamcscockpit/rest/tickets";
        return $resource(resourseUrl + '/:ticketId',
            {
                id: '@ticketId'
            });
    }])
    .factory("TicketCreateResource", ["$resource", function ($resource) {
        return $resource("/epamcscockpit/rest/tickets", null, {})
    }])
    .factory("TicketChangeStateResource", ["$resource", function ($resource) {
        return $resource("/epamcscockpit/rest/tickets/" + ':ticketId' + "/changestate",
            null,
            {'update': {method: 'PUT'}});
    }])

    .factory("TicketCountResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamcscockpit/rest/tickets/ticketCount";
        return $resource(resourseUrl, {}, {
            get: {
                method: 'GET',
                responseType: 'text'
            }
        });
    }])
    .factory("ConfigResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamcscockpit/rest/tickets/config";
        return $resource(resourseUrl);
    }])
    .factory("OrdersResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamorder/v1/orders";
        return $resource(resourseUrl + '/:orderCode',
            {
                id: '@orderCode'
            });
    }])

    



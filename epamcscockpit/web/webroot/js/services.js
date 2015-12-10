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
        var resourseUrl = "/epamticket/v1/tickets";
        return $resource(resourseUrl + '/:ticketId',
            {
                id: '@ticketId'
            });
    }])
    .factory("TicketCreateResource", ["$resource", function ($resource) {
        return $resource("/epamticket/v1/tickets", null, {
            //post: {
            //    method: 'POST'
            //
            //},
            save: {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (data, headersGetter) {
                    var str = [];
                    for (var d in data)
                        str.push(encodeURIComponent(d) + "=" + encodeURIComponent(
                                angular.toJson(data[d],false)));
                    return str.join("&");
                }
            }
        })
    }])
    .factory("TicketCountResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamticket/v1/tickets/ticketCount";
        return $resource(resourseUrl,{}, { get: {
                 method: 'GET',
                responseType: 'text'
        }});
    }])

    .factory("OrdersResource", ["$resource", function ($resource) {
        var resourseUrl = "/epamorder/v1/orders";
        return $resource(resourseUrl + '/:orderCode',
            {
                id: '@orderCode'
            });
    }]);
    



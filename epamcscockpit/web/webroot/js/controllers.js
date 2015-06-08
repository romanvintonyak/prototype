	var epamcscockpit = angular.module('epamcscockpit', []);
	epamcscockpit.controller("TicketPoolCtrl", function($scope, $http) {
		$scope.ticketPool = [];
		$scope.ticketFilter ={
				
		}
		$http.get('data/ticketStore.json').
			success(function(data,status,headers,config){
				$scope.ticketPool=data
			}).
			error(function(){
				
			});
		
	});

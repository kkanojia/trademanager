function TransactionsIndexController($scope, TradeManagerService, $rootScope,  $modal) {
	$scope.loading = true;

	$scope.transactionEntries = TradeManagerService.getAll();

	$scope.loading = false;

	$scope.deleteAllEntries = function() {
		$scope.loading = true;
		TradeManagerService.deleteAll();
		$scope.transactionEntries = TradeManagerService.getAll();
		$scope.loading = false;
	};

	$scope.openInformation = function(){
		 var modalInstance = $modal.open({
		      animation: true,
		      templateUrl: 'partials/transactions/infoContent.htm',
		    });
	};

};

function CreateTransactionController($scope, $location, TradeManagerService, $rootScope) {
	$rootScope.infoMessage = "";
	$rootScope.error = "";
	$scope.minDate = new Date("August 01, 2015 00:00:00");
	$scope.maxDate = new Date("August 31, 2015 00:00:00");
	$scope.tradeEntry = new TradeManagerService();
	$scope.tradeEntry.tradeDate = $scope.minDate;

	$scope.buysell = {
		types : [ "Buy", "Sell" ]
	}

	// Ideally Get it from DB
	$scope.assetIds = [ {
		"id" : 1,
		"name" : "ABC"
	}, {
		"id" : 2,
		"name" : "DEF"
	}, {
		"id" : 3,
		"name" : "XYZ"
	}, ]

	$scope.save = function() {
		$rootScope.infoMessage = "";
		$rootScope.error = "";
		$scope.tradeEntry.assetId = $scope.tradeEntry.assetinfo.id;
		$scope.tradeEntry.tradeDate = $scope.tradeEntry.tradeDate.toISOString().substring(0, 10);

		$scope.tradeEntry.$save(function(responseEntry) {
			if (responseEntry.id == undefined) {
				$rootScope.infoMessage = "Short selling is not allowed. Kunal was too sleepy to fix it. Hire him, he will fix it :)"
			}
			$location.path('/transactions/index');
		});
	};

};
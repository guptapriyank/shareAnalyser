var shareAnalyserServiceModule = angular.module('shareAnalyserService', [ 'ngResource' ]);

shareAnalyserServiceModule.factory('shareAnalyserService', function($resource) {
    return $resource("getAllStocks/:stockId", {
        stockId : '@stockId'
    }, {
        getHighest : {
            url : "stocks/:stockId/highest",
            method : "GET"
        }
    });
});

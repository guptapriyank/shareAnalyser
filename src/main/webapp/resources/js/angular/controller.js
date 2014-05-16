var shareAnalyserControllerModule = angular.module('shareAnalyserControllerModule', [ 'shareAnalyserService' ]);

var shareAnalyserController = function(shareAnalyserService) {
    that = this;
    that.shares = shareAnalyserService.query();
    that.orderProp = 'id';

    that.getHighest = function(stockId) {
        that.highestValueResults = shareAnalyserService.getHighest({
            stockId : stockId
        });
    };
};

shareAnalyserControllerModule.controller("shareAnalyserController", shareAnalyserController);
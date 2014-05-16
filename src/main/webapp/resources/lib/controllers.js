var phonecatApp = angular.module('phonecatApp', [ 'pascalprecht.translate', 'ngCookies', 'ngRoute' ]);

phonecatApp.controller('PhoneListCtrl', function($translate, $scope, $http, $location, $cookieStore, $compile) {
    $scope.changeLanguage = function() {
        $translate.use($scope.lang);
        var headerObject = $(".resultTableP > thead > tr");
        localizeHeaders($scope.metadataP, $scope.lang, $compile, $scope, headerObject);
        headerObject = $(".resultTableO > thead > tr");
        localizeHeaders($scope.metadataO, $scope.lang, $compile, $scope, headerObject);
    };
    $scope.recordTypeAll = ["person","organization"];//value of all radio
    $scope.recordTypePerson = ["person"];//value of person radio
    $scope.recordTypeOrganization = ["organization"];//value of organization radio
    $scope.recordType =  $scope.recordTypeAll;// Default recordType selected
    $scope.orderProp = '-score';// Default ordering
    $scope.persons = [];// record list after executing
    $scope.organizations = [];// record list after executing
    $scope.displayedSearchResult = [];// columns to be shown from search result search
    $scope.pageSize = 5;// no of record to be shown, default value
    $scope.filterCriteria = "";// Filter criteria

    // function to fetch metadata init function

    $scope.init = function() {
        var mdmSession = $cookieStore.get("MDM-session");
        var mdmSessionArr = mdmSession.split("::");

        var mdmTicket = mdmSessionArr[0];
        var serviceUrl = mdmSessionArr[1];
        var userId = mdmSessionArr[2];

        $scope.header = {
            'Content-Type' : 'application/json;charset=UTF-8',
            'Accept' : 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
            'CDI-serviceUrl' : serviceUrl,
            'CDI-ticket' : mdmTicket,
            'CDI-userId' : userId
        };
        var context = location.pathname.split("/")[1];
        $http({
            method : 'GET',
            url : '/' + context + '/goldenrecords/metadata/pgupta/person.json',// for testing
//            url : '/service/cdi/v1/goldenrecords/metadata/pgupta/person',
            headers : $scope.header
        }).success(function(data) {
            $scope.metadataP = data;
            var headerObject = $(".resultTableP > thead > tr");
            $scope.displayedSearchResultPerson = fillSearchHeader(data, $scope.lang, $compile, $scope, headerObject);
        });
        
        $http({
            method : 'GET',
            url : '/' + context + '/goldenrecords/metadata/pgupta/organization.json',// for testing
//            url : '/service/cdi/v1/goldenrecords/metadata/pgupta/person',
            headers : $scope.header
        }).success(function(data) {
            $scope.metadataO = data;
            var headerObject = $(".resultTableO > thead > tr");
            $scope.displayedSearchResultOrganization = fillSearchHeader(data, $scope.lang, $compile, $scope, headerObject);
        });

    };

    // Function for searching
    $scope.searchName = function() {
        $("#noOfRecords").addClass("hide");
        $scope.persons = [];
        $(".loading").removeClass('hide');
        var context = location.pathname.split("/")[1];
        $http({
            method : 'POST',
            url : '/' + context + '/goldenrecords/person.json',// for testing
//            url : '/service/cdi/v1/search/goldenrecords/pgupta',
            data : {"searchParam":{"name":$scope.query }, "recordTypeList":$scope.recordType},
            headers : $scope.header
        }).success(function(data) {
            $("#noOfRecords").removeClass("hide");
            $scope.pageSize = 5;
            $(".loading").addClass('hide');
            getUIPersonModel(data.person.goldenRecords, $scope);
            getUIOrganizationModel(data.organization.goldenRecords, $scope);
            // $scope.persons = data.person.goldenRecords;
        });
    };

    // Function for ordering
    $scope.orderBy = function(field) {
        if ($scope.orderProp == field) {
            $scope.orderProp = "-" + field;
        } else {
            $scope.orderProp = field;
        }
    };

    $scope.init();
});
// We already have a limitTo filter built-in to angular,
// let's make a startFrom filter
phonecatApp.filter('startFrom', function() {
    return function(input, start) {
        start = +start; // parse to int
        return input.slice(start);
    };
});

phonecatApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/dashboard', {
		templateUrl : 'dashboard.html',
		controller : 'PhoneListCtrl'
	}).when('/search', {
		templateUrl : 'search.html',
		controller : 'PhoneListCtrl'
	}).otherwise({
		redirectTo : '/dashboard'
	});
} ]);

phonecatApp.config([ '$translateProvider', function($translateProvider) {
    $translateProvider.translations('en_US', translationsEN);
    $translateProvider.translations('de', translationsDE);
    $translateProvider.translations('nl', translationsNL);
    $translateProvider.preferredLanguage('en_US');
    $translateProvider.fallbackLanguage('en_US');
} ]);

var translationsEN = {
    LANG_DE : 'German',
    LANG_EN : 'English',
    LANG_NL : 'Dutch',
    GO : 'Go',
    FULL_NAME : 'Full Name',
    STREET : 'Street',
    CITY : 'City',
    COUNTRY : 'Country',
    EMAIL_ID : 'Email',
    SEARCH_RESULTS_FOR : 'Search Result For',
    REFINED_BY : 'Refine by',
    SEARCH_NAME_HERE : 'Search Name here',
    SHOW_MORE_RECORDS : 'Show More Records',
    LOADING : 'Loading'
};

var translationsDE = {
    LANG_DE : 'Deutsch',
    LANG_EN : 'Englisch',
    LANG_NL : 'Holländisch',
    GO : 'gehen',
    FULL_NAME : 'Vollständiger Name',
    STREET : 'Straße',
    CITY : 'City',
    COUNTRY : 'Land',
    EAMIL_ID : 'E-mail',
    SEARCH_RESULTS_FOR : 'Suchergebnis für',
    REFINED_BY : 'Filtern nach',
    SEARCH_NAME_HERE : 'Suchen nach Name hier',
    SHOW_MORE_RECORDS : 'Weitere Aufzeichnungen zeigen',
    LOADING : 'Verladung'
};

var translationsNL = {
    LANG_DE : 'Duits',
    LANG_EN : 'Engels',
    LAN_NL : 'Nederlands',
    GO : 'gaan',
    FULL_NAME : 'volledige Naam',
    STREET : 'Straat',
    CITY : 'City',
    COUNTRY : 'Land',
    EAMIL_ID : 'e-mail',
    SEARCH_RESULTS_FOR : 'Zoekopdracht',
    REFINED_BY : 'Verfijn op',
    SEARCH_NAME_HERE : 'Zoeken Naam hier',
    SHOW_MORE_RECORDS : 'Toon Meer Records',
    LOADING : 'het laden'
};
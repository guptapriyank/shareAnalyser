<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html ng-app="shareAnalyser">
<head>
<title>Home</title>
<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet"
	media="screen">
<link href="resources/css/style.css" rel="stylesheet" media="screen">

<script src="resources/lib/jquery-2.0.3.js"></script>
<script src="resources/lib/angular.js"></script>
<script src="resources/lib/angular-resource.min.js"></script>

<script src="resources/bootstrap/js/bootstrap.min.js"></script>

<script src="resources/js/angular/app.js"></script>
<script src="resources/js/angular/controller.js"></script>
<script src="resources/js/angular/service.js"></script>
</head>
<body ng-controller="shareAnalyserController as saCtrl">
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Share Analyser</a>
			</div>
			<div class="navbar-collapse collapse"></div>
		</div>
	</div>
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="gap15"></div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="stock">
						<ul class="stock-list">
							<li ng-repeat="share in saCtrl.shares | orderBy:saCtrl.orderProp">
								<div class="share-top">{{share.name}}</div>
								<div class="share-body">
									<a href="#" ng-click="saCtrl.getHighest(share.id)">Show
										Highest Price, Year and Month </a>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-md-4">
					<div class="preview">
						<div class="preview-head">Stock Details</div>
						<div class="preview-body">
							<ul ng-show="saCtrl.highestValueResults">
								<li><strong>Highest price :</strong> <span>{{saCtrl.highestValueResults.highestValue}}</span></li>
								<li></li>
								<li
									ng-repeat="yearMonth in saCtrl.highestValueResults.yearMonths"><strong>Year
										:</strong> <span>{{yearMonth.year}}</span> <strong>Month : </strong> <span>{{yearMonth.month}}</span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

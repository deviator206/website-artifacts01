//artifactSite
var app = angular.module("artifactSite", []);

app.webservice = {
	"FETCH_CATEGORY_DETAILS" : "SiteInterface",
	"FETCH_IMAGE_LIST" : "SiteInterface"
};
app.controller("HeaderController", function($scope) {
	$scope.setUp = function() {
		var str = (window.location.href).split("#")[1];
		if (str !== undefined) {
			$("#li" + str).addClass("active");
		} else {
			$($("#ulHeaderElement").children()[0]).addClass("active");
		}
	}, $scope.setHighlighter = function(evt) {
		// remove previuosly added class
		var i, len, ele = $("#ulHeaderElement").children();
		len = ele.length;
		for (i = 0; i < len; i++) {
			$(ele[i]).removeClass("active");
		}

		$(event.target.parentElement).addClass("active");

	};
});

app
		.controller(
				"GalleryController",
				function($scope, $http) {
					$scope.categoryImageList = {};
					$scope.categoryDetails = [];
					$scope.currentImageList = [];
					$scope.selectedCatId = -1;
					$scope.categoryDefaultMessage = "Loading Categories";

					$scope.setUp = function() {
						// fetch category details
						$http
								.get(
										app.webservice.FETCH_CATEGORY_DETAILS
												+ "?apimode=category")
								.success(function(resp) {
									// will return array
										$scope.categoryDetails = resp;
										// $scope.addCategories(arrT)
										if ($scope.categoryDetails.length > 0) {
											$scope.categoryDefaultMessage = $scope.categoryDetails[0].catDescription;
											$scope.fetchImageList($scope.categoryDetails[0].catId)
											
										}
									}

								);
					};
					$scope.fetchImageList = function(selectedId) {
						$scope.categoryDefaultMessage ="Fetching Images...";
						$scope.currentImageList = [];
						
						$http.get(
								app.webservice.FETCH_CATEGORY_DETAILS
										+ "?apimode=image&catId="+selectedId).success(
								function(resp) {
									 $scope.categoryImageList[resp.catId] =resp.imgList;
									 $scope.renderImages(resp.catId);
								});
					}, $scope.categoryClicked = function(obj) {
						$scope.fetchImageList(obj.catId);
					};
					$scope.renderImages = function(selectId)
					{
						if($scope.categoryImageList.hasOwnProperty(selectId))
						{
							// EXIST SO SHOW OLD DATA
							$scope.selectedCatId = selectId;
							$scope.currentImageList = $scope.categoryImageList[$scope.selectedCatId];
							for(var i=0;i < $scope.categoryDetails.length;i++)
							{
								if($scope.categoryDetails[i].catId === Number(selectId)) 
								{
									$scope.categoryDefaultMessage = $scope.categoryDetails[i].catDescription;
									break;
								}
							}
								
						}
						else
						{
							 //SHOW NEW DATA
							//so fetch it
							$scope.fetchImageList(selectId);
						}
					}

				});

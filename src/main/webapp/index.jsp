<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sf Movies App</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<style>
html,body,#map-canvas {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#panel {
	position: absolute;
	top: 5px;
	left: 50%;
	margin-left: -180px;
	z-index: 5;
}

#address {
	width: 35em;
}

.ui-autocomplete {
	position: absolute;
	top: 100%;
	left: 0;
	z-index: 1000;
	float: left;
	display: none;
	min-width: 160px;
	_width: 160px;
	padding: 4px 2px;
	margin: 2px 2px 2px 2px;
	list-style: none;
	background-color: #ffffff;
	border-color: #ccc;
	border-color: rgba(0, 0, 0, 0.2);
	border-style: solid;
	border-width: 1px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	-webkit-background-clip: padding-box;
	-moz-background-clip: padding;
	background-clip: padding-box;
	*border-right-width: 2px;
	*border-bottom-width: 2px;
	height: 200px;
	overflow-y: scroll;
	overflow-x: hidden; . ui-menu-item > a.ui-corner-all { display : block;
	padding: 3px 15px;
	clear: both;
	font-weight: normal;
	line-height: 18px;
	color: #555555;
	white-space: nowrap; &. ui-state-hover , &.ui-state-active { color :
	#ffffff;
	text-decoration: none;
	background-color: #0088cc;
	border-radius: 0px;
	-webkit-border-radius: 0px;
	-moz-border-radius: 0px;
	background-image: none;
}
}
}
</style>
</head>
<body>

	<div id="panel">
		<input id="address" type="text" class="form-control"
			placeholder="Type in your movie">
	</div>
	<div id="map-canvas"></div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script>
		var geocoder;
		var map;
		var markers = [];
		function setAllMap(map) {
			for (var i = 0; i < markers.length; i++) {
				markers[i].setMap(map);
			}
		}
		function clearMarkers() {
			setAllMap(null);
		}
		function deleteMarkers() {
			clearMarkers();
			markers = [];
		}
		function initialize() {
			geocoder = new google.maps.Geocoder();
			var latlng = new google.maps.LatLng(37.7833, -122.4167);
			var mapOptions = {
				zoom : 10,
				center : latlng
			}
			map = new google.maps.Map(document.getElementById('map-canvas'),
					mapOptions);
		}

		function codeAddress(address) {

			geocoder.geocode({
				'address' : address
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					map.setCenter(results[0].geometry.location);
					var marker = new google.maps.Marker({
						map : map,
						position : results[0].geometry.location,
						title : address
					});
					markers.push(marker);
					var infowindow = new google.maps.InfoWindow();
					google.maps.event.addListener(marker, 'click', (function(
							marker, infowindow) {
						return function() {
							infowindow.setContent(address);
							infowindow.open(map, marker);
						};
					})(marker, infowindow));
				} else {

				}
			});
		}

		google.maps.event.addDomListener(window, 'load', initialize);
	</script>
	<script type="text/javascript">
		$(function() {

			$("#address").autocomplete(
					{
						source : function(request, response) {
							$.ajax({
								url : "webresources/myresource",
								dataType : "json",
								type : "GET",
								data : {
									term : request.term
								},
								success : function(data) {
									response(data);
								}
							});
						},
						minLength : 3,
						select : function(event, ui) {
							deleteMarkers();
							var addresses = ui.item.locations;
							for (var x = 0; x < addresses.length; x++) {
								codeAddress(addresses[x]
										+ ", San Francisco, CA");
							}

						},
						open : function() {
							$(this).removeClass("ui-corner-all").addClass(
									"ui-corner-top");
						},
						close : function() {
							$(this).removeClass("ui-corner-top").addClass(
									"ui-corner-all");
						}
					});
		});
	</script>
</body>
</html>
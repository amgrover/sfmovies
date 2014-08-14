<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Heatmaps</title>
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
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
}
</style>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js">
	
</script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.10.3/jquery-ui.js">
	
</script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />


	
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=visualization"></script>
<script>
	// Adding 500 Data Points
	var map, pointarray, heatmap;

	var taxiData = [];
	var mapOptions = {
			zoom : 13,
			center : new google.maps.LatLng(41.5665851, -81.58638604),
			mapTypeId : google.maps.MapTypeId.SATELLITE
		};
	function initialize() {
		

		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);

		var pointArray = new google.maps.MVCArray(taxiData);

		heatmap = new google.maps.visualization.HeatmapLayer({
			data : pointArray
		});

		heatmap.setMap(map);
	}

	function toggleHeatmap() {
		heatmap.setMap(heatmap.getMap() ? null : map);
	}

	function changeGradient() {
		var gradient = [ 'rgba(0, 255, 255, 0)', 'rgba(0, 255, 255, 1)',
				'rgba(0, 191, 255, 1)', 'rgba(0, 127, 255, 1)',
				'rgba(0, 63, 255, 1)', 'rgba(0, 0, 255, 1)',
				'rgba(0, 0, 223, 1)', 'rgba(0, 0, 191, 1)',
				'rgba(0, 0, 159, 1)', 'rgba(0, 0, 127, 1)',
				'rgba(63, 0, 91, 1)', 'rgba(127, 0, 63, 1)',
				'rgba(191, 0, 31, 1)', 'rgba(255, 0, 0, 1)' ]
		heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
	}

	function changeRadius() {
		heatmap.set('radius', heatmap.get('radius') ? null : 20);
	}

	function changeOpacity() {
		heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
	}

	google.maps.event.addDomListener(window, 'load', initialize);
	$(document).ready(function() {
		$(function() {
			$("#country").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : "/autocomplete",
						type : "GET",
						data : {
							term : request.term
						},

						dataType : "json",

						success : function(data) {
							response(data);
						}
					});
				}
			});
		});
	});
	

	function getSensors() {
		var url = "/autocomplete?id=" + $("#country").val();
		$.ajax({
			url : url,
			type : "POST",
			success : function(smartphone) {
				for ( var key in smartphone) {
					time = key;
					value = smartphone[key];
					var b=new google.maps.LatLng(time, value);
					taxiData.push(b);
				}
				initialize();

			}
		});
	}
</script>
</head>

<body>
	<div id="panel">
		<label for="country">Country: </label> <input id="country" /> <input
			type="button" onclick="getSensors()" value="Submit"></input>
		<button onclick="toggleHeatmap()">Toggle Heatmap</button>
		<button onclick="changeGradient()">Change gradient</button>
		<button onclick="changeRadius()">Change radius</button>
		<button onclick="changeOpacity()">Change opacity</button>
	</div>
	<div id="map-canvas"></div>
</body>
</html>
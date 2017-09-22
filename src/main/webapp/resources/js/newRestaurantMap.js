
var markerarray = [];
var geocoder = new google.maps.Geocoder();
var infowindow = new google.maps.InfoWindow();
function initialize() {

	var mapOptions = {
		zoom : 14,
		center : new google.maps.LatLng(-34.920, -57.955),
		mapTypeId : 'roadmap'
	};

	var map = new google.maps.Map(document.getElementById('map-canvas'),
			mapOptions);
	google.maps.event.addListener(map, 'click', function(e) {
		placeMarker(e.latLng, map)
	});
}

function placeMarker(location, map) {
	borrarMarcas();
	var marker = new google.maps.Marker({
		position : location,
		map : map

	});
	codeLatLng(marker);
	markerarray.push(marker);
	document.getElementById("ubicationlongitude").value = marker.getPosition()
			.lng();
	document.getElementById("ubicationlatitude").value = marker.getPosition()
			.lat();

	map.setCenter(position);

}

function codeLatLng(marker) {
	var latlng = marker.getPosition();

	geocoder
			.geocode(
					{
						'latLng' : latlng
					},
					function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							if (results[0]) {
								document.getElementById("addres").value = results[0].formatted_address;
								infowindow
										.setContent(results[0].formatted_address);
								infowindow.open(map, marker);
							}
							;
						}
						;
					});
}

function borrarMarcas() {
	for (var i = 0; i < markerarray.length; i++) {
		markerarray[i].setMap(null);
		bounds = null;
		bounds = new google.maps.LatLngBounds();
	}
	;

	markerArray = [];
}

google.maps.event.addDomListener(window, 'load', initialize);

window.onload = loadScript;
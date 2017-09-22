var map;
var markerarray = [];
var geocoder = new google.maps.Geocoder();
var infowindow = new google.maps.InfoWindow();
function initialize() {

	var y = document.getElementById("ubicationlongitude").value;
	var x = document.getElementById("ubicationlatitude").value;

	//var x=-34.920;
	//var y=-57.955;
	var mapOptions = {
		zoom : 14,
		center : new google.maps.LatLng(x, y)
	};

	map = new google.maps.Map(document.getElementById('map-canvas'),
			mapOptions);

	var marker = new google.maps.Marker({
		position : new google.maps.LatLng(x, y),
		map : map
	});
	markerarray.push(marker);

	var latlng = marker.getPosition();

	geocoder
			.geocode(
					{
						'latLng' : latlng
					},
					function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							if (results[0]) {
								//document.getElementById("addresToChange").value=results[0].formatted_address;
								document.getElementById("addres").innerHTML = results[0].formatted_address;
								//document.getElementById("addresActual").innerHTML = results[0].formatted_address;
								infowindow
										.setContent(results[0].formatted_address);
								infowindow.open(map, marker);
								
							}
							;
						}
						;
					});

}

google.maps.event.addDomListener(window, 'load', initialize);

function loadScript() {
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDm-mzYm2bUccqo7GRnvauQw0yXeVz5mYA&v=3.exp&sensor=false&'
			+ 'callback=initialize';
	document.body.appendChild(script);
}

window.onload = loadScript;

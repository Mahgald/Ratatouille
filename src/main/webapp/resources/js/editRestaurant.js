/*var map;
var markerarray = [];
var geocoder = new google.maps.Geocoder();
var infowindow = new google.maps.InfoWindow();


function initialize() {
	// $('#changeInformationOfRestaurant').modal('show');
	var y = document.getElementById("ubicationlongitude").value;
	var x = document.getElementById("ubicationlatitude").value;

	// var x=-34.920;
	// var y=-57.955;
	var mapOptions = {
		zoom : 14,
		center : new google.maps.LatLng(x, y)
	};

	var map = new google.maps.Map(document.getElementById('map-canvas'),
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
								// document.getElementById("addres").value=results[0].formatted_address;
								document.getElementById("addres").innerHTML = results[0].formatted_address;
								infowindow
										.setContent(results[0].formatted_address);
								infowindow.open(map, marker);
							}
							;
						}
						;
					});


	// $('#changeInformationOfRestaurant').modal('hide');
}

// google.maps.event.addDomListener(window, 'load', initialize);

function loadScript() {
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDm-mzYm2bUccqo7GRnvauQw0yXeVz5mYA&v=3.exp&sensor=false&'
			+ 'callback=initialize';
	document.body.appendChild(script);
}
*/

var mapModal;
var markerarrayModal = [];
var aux=false;


function initializeModal() {

var y=document.getElementById("ubicationlongitude").value;
var x=document.getElementById("ubicationlatitude").value;
// var x=-34.920;
// var y=-57.955;
var mapOptions = {
zoom: 14,
center: new google.maps.LatLng(x,y)
};

var map = new google.maps.Map(document.getElementById('modal-map-canvas'),
  mapOptions);
mapModal=map;
google.maps.event.addListener(map, 'click', function(e) {
placeMarker(e.latLng, map)
});
var marker = new google.maps.Marker({
position: new google.maps.LatLng(x, y),
map:map
});
markerarrayModal.push(marker);


codeLatLng(marker);
}


function placeMarker(location, map) {
borrarMarcas();
var marker = new google.maps.Marker({
  position: location,
  map: map

});
codeLatLng(marker);
markerarrayModal.push(marker);
document.getElementById("ubicationlongitudeToChange").value=marker.getPosition().lng();
document.getElementById("ubicationlatitudeToChange").value=marker.getPosition().lat();





map.setCenter(position);

}
function codeLatLng(marker) {
	var latlng = marker.getPosition();
	
	var geo =new google.maps.Geocoder();
	geo.geocode({	'latLng' : latlng		
					},	function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							if (results[0]) {
								document.getElementById("addresToChange").value = results[0].formatted_address;
								
							};
						};
					});
					//document.getElementById("addresToChange").value = "fff";
					
}




function borrarMarcas(){
   for (var i = 0; i < markerarrayModal.length; i++) {
    markerarrayModal[i].setMap(null);
bounds = null;
bounds = new google.maps.LatLngBounds();
 };

markerArrayModal= [];
}

function loadScriptModal() {

var script = document.createElement('script');
script.type = 'text/javascript';
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDm-mzYm2bUccqo7GRnvauQw0yXeVz5mYA&v=3.exp&sensor=false&'
	+ 'callback=initializeModal';
	if(!aux){
		document.body.appendChild(script);
		aux=true;
		
	}

}




function mapView() {
    var element = document.getElementById('osm-map');
// Create Leaflet map on map element.
    var map = L.map(element);
    var filter = document.getElementById('filter');
    var ourRequest = new XMLHttpRequest();

    var redIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    var blackIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-black.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    var greenIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });


    if(filter.value.length !== 0)
    {
        ourRequest.open('GET','/api/anti-police?filter=mostSubmitted');
        ourRequest.onload = function (){
            var ourData = JSON.parse(ourRequest.responseText);
            var marker;
            for(let i = 0; i<ourData.length; i++){
                marker = L.marker(ourData[i].latlng,{icon: blackIcon});
                map.addLayer(marker);
                if(ourData[i].user != null && ourData[i].state != null) {
                    marker.bindPopup('<p>Type of police: ' + ourData[i].state + ' </p>' +
                        '<p>From user: ' + ourData[i].user.username + ' </p>' +
                        '<p>DateTime: ' + ourData[i].formattedDateTime);
                }
            }

        };
    }
    else {
        ourRequest.open('GET', '/api/anti-police');
        ourRequest.onload = function (){
            var ourData = JSON.parse(ourRequest.responseText);
            var marker;
            for(let i = 0; i<ourData.length; i++){
                if(ourData[i].state === "PATROLA" && ourData[i].state!=null)
                {
                    marker = L.marker(ourData[i].latlng);
                }
                if(ourData[i].state === "RADAR" && ourData[i].state!=null)
                {
                    marker = L.marker(ourData[i].latlng,{icon: greenIcon});
                }
                if(ourData[i].state === "ALKOTEST" && ourData[i].state!=null)
                {
                    marker = L.marker(ourData[i].latlng,{icon: redIcon});
                }
                map.addLayer(marker);
                if(ourData[i].user != null && ourData[i].state != null) {
                    marker.bindPopup('<p>Type of police: ' + ourData[i].state + ' </p>' +
                        '<p>From user: ' + ourData[i].user.username + ' </p>' +
                        '<p>DateTime: ' + ourData[i].formattedDateTime + ' </p>');
                }
            }

        };
    }

    ourRequest.send();


// Add OSM tile layer to the Leaflet map.
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    /*Legend specific*/
    var legend = L.control({ position: "bottomleft" });

    legend.onAdd = function(map) {
        var div = L.DomUtil.create("div", "legend");
        div.innerHTML += '<i style="background: #ca283b"></i><span>Alcotest</span><br>';
        div.innerHTML += '<i style="background: #26ab21"></i><span>Radar</span><br>';
        div.innerHTML += '<i style="background: #257eca"></i><span>Patrol</span><br>';
        div.innerHTML += '<i style="background: #000000"></i><span>Most Common Locations</span><br>';
        return div;
    };

    legend.addTo(map);

// Target's GPS coordinates.
    var target = L.latLng('0','0');
    map.locate({setView: false, watch: true, maxZoom: 20});
    map.on('locationfound', function(ev){
        target.lat = ev.latlng.lat;
        target.lng = ev.latlng.lng;
        map.setView(target,17);
    });

}

function showLatLng(){
    var element = document.getElementById('osm-map');
// Create Leaflet map on map element.
    var map = L.map(element);

    var yellowIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-yellow.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });


// Add OSM tile layer to the Leaflet map.
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    var target = L.latLng('0','0');
    map.locate({setView: false, watch: true, maxZoom: 20});
    map.once('locationfound', function(ev){
        target.lat = ev.latlng.lat;
        target.lng = ev.latlng.lng;
        var marker1 = L.circleMarker([target.lat,target.lng],{
            radius: 8,
            fillColor: "#ca283b",
            color: "#000000",
            weight: 1,
            opacity: 1,
            fillOpacity: 1});
        marker1.bindPopup('<p>You are here!</p>');
        marker1.addTo(map);
        map.setView(target,17);
    });

    var marker;
    map.once('click', function (e){
        marker = L.marker(e.latlng, {
            draggable: true,
            icon: yellowIcon
        });
        map.addLayer(marker);
        marker.bindPopup('<p>Here is police!</p>');
        $('#cord').val(e.latlng.lat + ", "+ e.latlng.lng);
        marker.on('drag',function (e){
            $('#cord').val(e.latlng.lat + ", "+ e.latlng.lng);
        });
    });

}

/**
*
* Modified from Wikitude-sdk-samples
* From:https://github.com/Wikitude/wikitude-sdk-samples
**/


var ServerNearbyInformation = {
	POIDATA_SERVER: "https://maps.googleapis.com/maps/api/place/nearbysearch/",
	POIDATA_SERVER_ARG_LAT: "lat",
	POIDATA_SERVER_ARG_LON: "lon",
	POIDATA_SERVER_ARG_NR_POIS: "nrPois"
};

var ServerTripInformation = {
   POIDATA_SERVER: "https://cobaltwebserver.herokuapp.com/api/trips/findbyid/"
}


// Id of trip
var tripid;

// POI-locations based on group( 1= Nearby locations, 0 = trip locations)
var markerGroup = 0;


// implementation of "World"
var World = {

	//  user's latest location
	userLocation: null,

   //Check if POI-data is being requested
	isRequestingData: false,

	// true once data was fetched
	initiallyLoadedData: false,

	// POI-Marker assets
	markerDrawable_idle: null,
	markerDrawable_selected: null,
	markerDrawable_directionIndicator: null,

	// list of AR.GeoObjects that are currently shown in the World
	markerList: [],

	// The last selected marker
	currentMarker: null,

	locationUpdateCounter: 0,
	updatePlacemarkDistancesEveryXLocationUpdates: 10,

    //Get Trip id from android app
	 tripId : function tripIdFn(string){
    	    tripid = string;
    	},


	// Called to add POI- data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {

		// destroys all existing AR-Objects (markers & radar)
		AR.context.destroyAll();

		// show radar & set click-listener
		PoiRadar.show();
		$('#radarContainer').unbind('click');
		$("#radarContainer").click(PoiRadar.clickedRadar);

		// empty list of visible markers
		World.markerList = [];

		// start loading marker assets
		World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
		World.markerDrawable_selected = new AR.ImageResource("assets/marker_selected.png");
		World.markerDrawable_directionIndicator = new AR.ImageResource("assets/indi.png");

		// loop through POI-information and create a marker per POI based on markerGroup

		if (markerGroup == 1){
		for (var currentPlaceNr = 0; currentPlaceNr < poiData.length; currentPlaceNr++) {
			var Poi = {
            				"id": currentPlaceNr,
            				"latitude": parseFloat(poiData[currentPlaceNr].geometry.location.lat),
            				"longitude": parseFloat(poiData[currentPlaceNr].geometry.location.lng),
            				"title": poiData[currentPlaceNr].name,
            				"description": poiData[currentPlaceNr].vicinity

            			};
            			World.markerList.push(new Marker(Poi));
			}


		}else {
		for (var currentPlaceNr = 0; currentPlaceNr < poiData.length; currentPlaceNr++) {
                 var singlePoi = {
                                    	"id": currentPlaceNr,
                                    	"latitude": parseFloat(poiData[currentPlaceNr].latitude),
                                    	"longitude": parseFloat(poiData[currentPlaceNr].longitude),
                                        "altitude":parseFloat(poiData[currentPlaceNr].altitude),
                                    	"title": poiData[currentPlaceNr].title,
                                    	"description": poiData[currentPlaceNr].description
                                    	};
                                    		World.markerList.push(new Marker(singlePoi));
        			}


		}



		// updates distance information
		World.updateDistanceToUserValues();

		World.updateStatusMessage(currentPlaceNr + ' places loaded');

		// set distance slider to 100%
		$("#panel-distance-range").val(100);
		$("#panel-distance-range").slider("refresh");
	},

	// sets/updates distances of all makers
	updateDistanceToUserValues: function updateDistanceToUserValuesFn() {
		for (var i = 0; i < World.markerList.length; i++) {
			World.markerList[i].distanceToUser = World.markerList[i].markerObject.locations[0].distanceToUser();
		    var distanceToUserValue = (World.markerList[i].distanceToUser > 999) ? ((World.markerList[i].distanceToUser / 1000).toFixed(2) + " km") : (Math.round(World.markerList[i].distanceToUser) + " m");
			 World.markerList[i].descriptionLabel.text = distanceToUserValue  ;
		}
		},


	// updates status message shon in small "i"-button aligned bottom center
	updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

		var themeToUse = isWarning ? "e" : "c";
		var iconToUse = isWarning ? "alert" : "info";

		$("#status-message").html(message);
		$("#popupInfoButton").buttonMarkup({
			theme: themeToUse
		});
		$("#popupInfoButton").buttonMarkup({
			icon: iconToUse
		});
	},


	// user clicked "More" button in POI-detail panel -> go to place fragment of app
	onPoiDetailMoreButtonClicked: function onPoiDetailMoreButtonClickedFn() {
		var currentMarker = World.currentMarker;

		var markerSelectedJSON = {
            action: "present_poi_details",
            id: currentMarker.poiData.id,
            title: currentMarker.poiData.title,
            description: currentMarker.poiData.description,
            latitude: currentMarker.poiData.latitude,
            longitude: currentMarker.poiData.longitude
        };

        AR.platform.sendJSONObject(markerSelectedJSON);

	},

		// user clicked map button in POI-detail panel -> open google maps
    	onPoiDetailMapButtonClicked: function onPoiDetailMapButtonClickedFn() {
    		var currentMarker = World.currentMarker;
    		var lat_lon = currentMarker.poiData.latitude + "," + currentMarker.poiData.longitude

    		var Url = "geo:"+ lat_lon +"?q="+ currentMarker.poiData.title;
    		window.open(Url);
    	},


	// location updates
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {

		// store user's current location in World.userLocation
		World.userLocation = {
			'latitude': lat,
			'longitude': lon,
			'altitude': alt,
			'accuracy': acc
		};




		// request data if not already present
		if (!World.initiallyLoadedData) {
			World.requestDataFromTripServer(lat, lon);
			World.initiallyLoadedData = true;
		} else if (World.locationUpdateCounter === 0) {
			World.updateDistanceToUserValues();
		}

		// helper used to update information every 10 location updates fired
		World.locationUpdateCounter = (++World.locationUpdateCounter % World.updatePlacemarkDistancesEveryXLocationUpdates);
	},

	//Open panel when marker is selected
	onMarkerSelected: function onMarkerSelectedFn(marker) {
		World.currentMarker = marker;

		// update panel values
		$("#poi-detail-title").html(marker.poiData.title);
		$("#poi-detail-description").html(marker.poiData.description);

		//Calculate distance if undefined
		if( undefined == marker.distanceToUser ) {
			marker.distanceToUser = marker.markerObject.locations[0].distanceToUser();
		}
		var distanceToUserValue = (marker.distanceToUser > 999) ? ((marker.distanceToUser / 1000).toFixed(2) + " km") : (Math.round(marker.distanceToUser) + " m");

		$("#poi-detail-distance").html(distanceToUserValue);

		$("#panel-poidetail").panel("open", 123);

		$(".ui-panel-dismiss").unbind("mousedown");

		$("#panel-poidetail").on("panelbeforeclose", function(event, ui) {
			World.currentMarker.setDeselected(World.currentMarker);
		});
	},

	// returns distance in meters with maxdistance * 1.1
	getMaxDistance: function getMaxDistanceFn() {

		// sort places by distance
		World.markerList.sort(World.sortByDistanceSortingDescending);

		// use distanceToUser to get max-distance
		var maxDistanceMeters = World.markerList[0].distanceToUser;

		// return maximum distance with some space to make sure all markers stay on screen
		return maxDistanceMeters * 1.1;
	},

	// updates values show in "range panel"
	updateRangeValues: function updateRangeValuesFn() {


		var slider_value = $("#panel-distance-range").val();

		// max range relative to the maximum distance of all visible places
		var maxRangeMeters = Math.round(World.getMaxDistance() * (slider_value / 100));

		// range in meters
		var maxRangeValue = (maxRangeMeters > 999) ? ((maxRangeMeters / 1000).toFixed(2) + " km") : (Math.round(maxRangeMeters) + " m");

		// number of places within max-range
		var placesInRange = World.getNumberOfVisiblePlacesInRange(maxRangeMeters);

		// update UI labels accordingly
		$("#panel-distance-value").html(maxRangeValue);
		$("#panel-distance-places").html((placesInRange != 1) ? (placesInRange + " Places") : (placesInRange + " Place"));

		// update culling distance, so only places within given range are rendered
		AR.context.scene.cullingDistance = Math.max(maxRangeMeters, 1);

		// update radar's maxDistance so radius of radar is updated too
		PoiRadar.setMaxDistance(Math.max(maxRangeMeters, 1));
	},

	// returns number of places with same or lower distance than given range
	getNumberOfVisiblePlacesInRange: function getNumberOfVisiblePlacesInRangeFn(maxRangeMeters) {

		// sort markers by distance
		World.markerList.sort(World.sortByDistanceSorting);

		// loop through list and stop once a placemark is out of range
		for (var i = 0; i < World.markerList.length; i++) {
			if (World.markerList[i].distanceToUser > maxRangeMeters) {
				return i;
			}
		};

		// in case no placemark is out of range -> all are visible
		return World.markerList.length;
	},

	handlePanelMovements: function handlePanelMovementsFn() {

		$("#panel-distance").on("panelclose", function(event, ui) {
			$("#radarContainer").addClass("radarContainer_left");
			$("#radarContainer").removeClass("radarContainer_right");
			PoiRadar.updatePosition();
		});

		$("#panel-distance").on("panelopen", function(event, ui) {
			$("#radarContainer").removeClass("radarContainer_left");
			$("#radarContainer").addClass("radarContainer_right");
			PoiRadar.updatePosition();
		});
	},

	// display range slider
	showRange: function showRangeFn() {
		if (World.markerList.length > 0) {

			// update labels on every range movement
			$('#panel-distance-range').change(function() {
				World.updateRangeValues();
			});

			World.updateRangeValues();
			World.handlePanelMovements();

			// open panel
			$("#panel-distance").trigger("updatelayout");
			$("#panel-distance").panel("open", 1234);
		} else {

			// no places are visible, because the are not loaded yet
			World.updateStatusMessage('No places available yet', true);
		}
	},

	  ChangeToNearByPlaces: function ChangeToNearByPlacesFn() {
    		if (!World.isRequestingData) {
    			if (World.userLocation) {
    				World.requestDataFromNearbyServer(World.userLocation.latitude, World.userLocation.longitude);
    				markerGroup = 1;
    			} else {
    				World.updateStatusMessage('Unknown user-location.', true);
    			}
    		} else {
    			World.updateStatusMessage('Already requesing places...', true);
    		}
    	},

    	ChangeToTripPlaces: function ChangeToTripPlacesFn() {
        		if (!World.isRequestingData) {
        			if (World.userLocation) {
        				World.requestDataFromTripServer(World.userLocation.latitude, World.userLocation.longitude);
        				markerGroup = 0;
        			} else {
        				World.updateStatusMessage('Unknown user-location.', true);
        			}
        		} else {
        			World.updateStatusMessage('Already requesing places...', true);
        		}
        	},

	// reload places from source
	reloadPlaces: function reloadPlacesFn() {
		if (!World.isRequestingData) {
			if (World.userLocation) {
			// Reloads places from either google or database based on markerGroup
			    if(markerGroup == 0){
			        World.requestDataFromTripServer(World.userLocation.latitude, World.userLocation.longitude);
			    }else{
			        World.requestDataFromNearbyServer(World.userLocation.latitude, World.userLocation.longitude);
			    }

			} else {
				World.updateStatusMessage('Unknown user-location.', true);
			}
		} else {
			World.updateStatusMessage('Already requesing places...', true);
		}
	},


	// request POI data from database
	requestDataFromTripServer: function requestDataFromServerFn(lat, lon) {

		// set helper var to avoid requesting places while loading
		World.isRequestingData = true;
		World.updateStatusMessage('Requesting places from web-service');

		// server-url
		var serverUrl = ServerTripInformation.POIDATA_SERVER + tripid;

		var jqxhr = $.getJSON(serverUrl, function(data) {
		         console.log(data[0]);
				World.loadPoisFromJsonData(data[0].locations);
			})
			.error(function(err) {
				World.updateStatusMessage("Invalid web-service response.", true);
				World.isRequestingData = false;
			})
			.complete(function() {
				World.isRequestingData = false;
			});
	},

     //Request nearby places from google
	requestDataFromNearbyServer: function requestDataFromServerFn(lat, lon) {

    		// set helper var to avoid requesting places while loading
    		World.isRequestingData = true;
    		World.updateStatusMessage('Requesting places from web-service');


    		// server-url
    		var serverUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ lat +","+lon+"&radius=1500&key=AIzaSyDUTuXspBHRrHyFDPzWZ4gtcKP-xYbo4g0";

    		var jqxhr = $.getJSON(serverUrl, function(data) {
    		        console.log(data.status);
    		        console.log(data.results);
    				World.loadPoisFromJsonData(data.results);
    			})
    			.error(function(err) {
    				World.updateStatusMessage("Invalid web-service response.", true);
    				World.isRequestingData = false;
    			})
    			.complete(function() {
    				World.isRequestingData = false;
    			});
    	},




	sortByDistanceSorting: function(a, b) {
		return a.distanceToUser - b.distanceToUser;
	},


	sortByDistanceSortingDescending: function(a, b) {
		return b.distanceToUser - a.distanceToUser;
	}

};



AR.context.onLocationChanged = World.locationChanged;

AR.context.onScreenClick = World.onScreenClick;
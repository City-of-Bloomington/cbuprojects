"use strict";
var maplayers  = [
    {
        "source": "XYZ",
        "options": {
            "url": "http:\/\/api.tiles.mapbox.com\/v4\/mapbox.streets\/{z}\/{x}\/{y}.png?access_token=pk.eyJ1IjoiaW5naGFtbiIsImEiOiJkYjI4YjY4NTQzZmU3YzU1NjNhNWY5YWQ1MGYzNWM1MSJ9.bTz47lsKieDIZoLusUS99A"
        }
    }
];
var eventTypes = [
    {
        "code": 1,
        "name": "Bike/Ped",
        "description": null,
        "default": true,
        "color": [
            215,
            0,
            0
        ]
    },
    {
        "code": 2,
        "name": "Signal",
        "description": null,
        "default": true,
        "color": [
            214,
            81,
            0
        ]
    },
    {
        "code": 3,
        "name": "Stop Control",
        "description": null,
        "default": true,
        "color": [
            235,
            150,
            2
        ]
    },
    {
        "code": 4,
        "name": "Roundabout",
        "description": null,
        "default": false,
        "color": [
            0,
            88,
            235
        ]
    },
    {
        "code": 5,
        "name": "Realignment",
        "description": null,
        "default": false,
        "color": [
            0,
            182,
            235
        ]
    },
    {
        "code": 6,
        "name": "Conversion",
        "description": null,
        "default": false,
        "color": [
            124,
            83,
            29
        ]
    }
];
// WS added popup
var popup = new ol.Overlay.Popup();
var MAPDISPLAY = {
		
    map: new ol.Map({
        target: 'map',
        view: new ol.View({
            center: ol.proj.transform([-86.536806, 39.169927], 'EPSG:4326', 'EPSG:3857'),
            zoom: 14,
            minZoom: 1,
            maxZoom: 20
        })
    }),
    /**
     * The features are not added to a regular vector layer/source,
     * but to a feature overlay which holds a collection of features.
     * This collection is passed to the modify and also the draw
     * interaction, so that both can add or modify features.
     */
    featureOverlay: new ol.FeatureOverlay(),
    wktFormatter: new ol.format.WKT(),
    styles: {
        default: {
            normal: new ol.style.Style({
                image:  new ol.style.Circle({fill:new ol.style.Fill(
                                            {color:[215, 0, 0, .6]}), radius:3}),
                stroke: new ol.style.Stroke({color:[215, 0, 0, .6], width:6, lineCap:'square'}),
                fill:   new ol.style.Fill(  {color:[215, 0, 0, .6]})
            }),
            hover: new ol.style.Style({
                image:  new ol.style.Circle({fill:new ol.style.Fill(
                                            {color:[215, 0, 0, 1]}), radius:4}),
                stroke: new ol.style.Stroke({color:[215, 0, 0, 1], width:8, lineCap:'square'}),
                fill:   new ol.style.Fill(  {color:[215, 0, 0, 1]})
            }),
            selected: new ol.style.Style({
                image:  new ol.style.Circle({fill:new ol.style.Fill(
                                            {color:[215, 0, 0, 1]}), radius:4}),
                stroke: new ol.style.Stroke({color:[215, 0, 0, 1], width:8, lineCap:'square'}),
                fill:   new ol.style.Fill(  {color:[215, 0, 0, 1]})
            })
        }
    },
    /**
     * Colors for features are defined in site_conf.
     * We need to remember to write the PHP variables out as Javascript,
     * so we can load them here
     * See: blocks/html/events/map.inc
     */
    loadEventTypeStyles: function (types) {
        var len = types.length,
            i   = 0,
            c   = [];

        for (i=0; i<len; i++) {
            c = types[i].color;

            MAPDISPLAY.styles[types[i].code] = {
                normal: new ol.style.Style({
                    image:  new ol.style.Circle({fill:new ol.style.Fill(
                                                {color:[c[0], c[1], c[2], .6]}), radius:3}),
                    stroke: new ol.style.Stroke({color:[c[0], c[1], c[2], .6], width:6, lineCap:'square'}),
                    fill:   new ol.style.Fill(  {color:[c[0], c[1], c[2], .6]}),
                }),
                hover: new ol.style.Style({
                    image:  new ol.style.Circle({fill:new ol.style.Fill(
                                                {color:[c[0], c[1], c[2], 1]}), radius:4}),
                    stroke: new ol.style.Stroke({color:[c[0], c[1], c[2], 1], width:8, lineCap:'square'}),
                    fill:   new ol.style.Fill(  {color:[c[0], c[1], c[2], 1]}),
                }),
                selected: new ol.style.Style({
                    image:  new ol.style.Circle({fill:new ol.style.Fill(
                                                {color:[c[0], c[1], c[2], 1]}), radius:4}),
                    stroke: new ol.style.Stroke({color:[c[0], c[1], c[2], 1], width:8, lineCap:'square'}),
                    fill:   new ol.style.Fill(  {color:[c[0], c[1], c[2], 1]}),
                }),
            }
        }
    },
    marker: new ol.Overlay({
        element: document.getElementById('marker'),
        positioning: 'bottom-center'
    }),
    /**
     * Adds features to the map
     *
     * This function handles rezooming and centering the map
     * after adding the features.
     *
     * @param array features
     */
    setFeatures: function (features) {
        var extent = ol.extent.createEmpty(),
        len = features.length,
        i   = 0;

        for (i=0; i<len; i++) {
            ol.extent.extend(extent, features[i].getGeometry().getExtent());
        }
        MAPDISPLAY.featureOverlay.setFeatures(new ol.Collection(features));
        MAPDISPLAY.map.getView().fitExtent(extent, MAPDISPLAY.map.getSize());
    },
    /**
     * Reads features out of the FeatureOverlay and converts them to WSG84 WKT
     *
     * @return string
     */
    getWkt: function () {
        var clones    = [],
        features  = MAPDISPLAY.featureOverlay.getFeatures().getArray(),
        len = features.length,
        i   = 0,
        wkt = '';

        if (len) {
            for (i=0; i<len; i++) {
                clones[i] = features[i].clone();
                clones[i].getGeometry().transform('EPSG:3857', 'EPSG:4326');
            }
            wkt = MAPDISPLAY.wktFormatter.writeFeatures(clones);
        }
        return wkt;
    },
    /**
     * Gets a reference to a feature in the map
     *
     * @param string event_id
     */
    findFeature: function (event_id) {
        var features = MAPDISPLAY.featureOverlay.getFeatures().getArray(),
        len = features.length,
        i   = 0;

        for (i=0; i<len; i++) {
            if (features[i].event_id === event_id) {
                return features[i];
            }
        }
    },
    currentlySelectedEventId: null,
    selectEvent: function (event_id, feature) {
        var article = document.getElementById(event_id),
            coords  = [];

        article.parentElement.classList.add('current');
        MAPDISPLAY.currentlySelectedEventId = event_id;

        if (!feature) {
            feature = MAPDISPLAY.findFeature(event_id);
            if (!feature) {
                return;
            }
        }
        MAPDISPLAY.enableStyle(feature, 'selected');

        coords = ol.extent.getCenter(feature.getGeometry().getExtent());
        MAPDISPLAY.marker.setPosition(coords);
    },
    deselectEvents: function () {
        var a       = document.querySelector('#events .current'),
            feature = {};

        if (a) {
            a.classList.remove('current');

            feature = MAPDISPLAY.findFeature(a.firstElementChild.getAttribute('id'));
            if (feature) { MAPDISPLAY.resetStyle(feature); }
        }

        MAPDISPLAY.currentlySelectedEventId = null;
        MAPDISPLAY.marker.setPosition([0,0]);
    },
    highlightEvent: function (e) {
        var id = e.currentTarget.getAttribute('id'),
             f = MAPDISPLAY.findFeature(id);

        if (f && id != MAPDISPLAY.currentlySelectedEventId) {
            MAPDISPLAY.enableStyle(f, 'hover');
        }
    },
    unhighlightEvent: function (e) {
        var id = e.currentTarget.getAttribute('id'),
             f = MAPDISPLAY.findFeature(id);

        if (f && id != MAPDISPLAY.currentlySelectedEventId) {
            MAPDISPLAY.resetStyle(f);
        }
    },
    enableStyle: function (feature, style) {
        if (feature.type) {
            feature.setStyle(MAPDISPLAY.styles[feature.type][style]);
        }
        else {
            feature.setStyle(MAPDISPLAY.styles.default[style]);
        }
    },
    resetStyle: function (feature) {
        if (feature.type) {
            feature.setStyle(MAPDISPLAY.styles[feature.type].normal);
        }
        else {
            feature.setStyle(null);
        }
    },
    /**
     * Responds to clicks on the map
     *
     * Draws the popup bubble for any feature that's clicked
     */
    handleMapClick: function (e) {
				var coords = e.coordinate;
        var feature = MAPDISPLAY.map.forEachFeatureAtPixel(e.pixel, function (feature, layer) { return feature; });

        MAPDISPLAY.deselectEvents();
        if (feature && feature.event_id) {
						popup.hide();
						popup.setOffset([0, 0]);
						var info = "<h2><a href='"+feature.url+"project.action?id=" + feature.event_id + "'>Project "+feature.event_id+"</a></h2>";
						if(feature.project_name){
								info += "<p> Project " + feature.project_name + "</p>";
						}
						// Offset the popup so it points at the middle not the tip
						popup.setOffset([0, -22]);
						popup.show(coords, info);						
        }
    },
    handleListClick: function (e) {
        var current = e.currentTarget.classList.contains('current');

        e.preventDefault();
        MAPDISPLAY.deselectEvents();
        if (!current) {
            MAPDISPLAY.selectEvent(e.currentTarget.firstElementChild.getAttribute('id'));
        }
        return false;
    }
};

MAPDISPLAY.map.addOverlay(MAPDISPLAY.marker);
MAPDISPLAY.map.addOverlay(popup);
MAPDISPLAY.featureOverlay.setMap(MAPDISPLAY.map);
MAPDISPLAY.featureOverlay.setStyle(MAPDISPLAY.styles.default.normal);
MAPDISPLAY.map.on('click', MAPDISPLAY.handleMapClick);

// Load any initial data the webpage specifies.
(function () {
    var events = [],
        len    = 0,
        i      = 0,
        id        = '',
        type      = '',
        f         = 0,
        geography = '',
        features  = [],
        noscriptMessage = document.getElementById('pleaseEnableJavascript');

    // Remove the prompt to enable Javascript before we begin to render the map
    if (noscriptMessage) {
        noscriptMessage.parentElement.removeChild(noscriptMessage);
    }

    // Maplayers are defined in site_config.
    // We have to remember to write the PHP variables out as Javascript,
    // so we can reference them here.
    // See: blocks/html/events/map.inc
    len = maplayers.length;
    for (i=0; i<len; i++) {
        MAPDISPLAY.map.addLayer(new ol.layer.Tile({
            source: new ol.source[maplayers[i].source](maplayers[i].options)
        }));
    }
    // Colors for features are also defined in site_conf.
    // We need to remember to write the PHP variables out as Javascript,
    // so we can load them here
    // See: blocks/html/events/map.inc
    MAPDISPLAY.loadEventTypeStyles(eventTypes);
		//
    // Event data can be in either the eventList
		//
		// <div id="events"> <article id= class= > <div class="geography"
		// for us we are using .project and div
		//
    events = document.querySelectorAll('.project div');
    len = events.length;
    for (i=0; i<len; i++) {
        id   = events[i].getAttribute('id');
        type = events[i].className;
        var project_class = events[i].querySelector('.project_name');
				var project_name = "";
				var url = "";
				if(project_class && project_class.innerHTML){
						project_name = project_class.innerHTML;
				}
				var url_class = events[i].querySelector('.url');
				if(url_class && url_class.innerHTML){
						url = url_class.innerHTML;
				}				
        geography = events[i].querySelector('.geography');//class type geography
        if (geography && geography.innerHTML) {
            f = features.length;
            features[f] = MAPDISPLAY.wktFormatter.readFeature(geography.innerHTML);
            features[f].getGeometry().transform('EPSG:4326', 'EPSG:3857');
            features[f].event_id = id;
            features[f].type     = type;
						features[f].project_name = project_name;
						features[f].url = url;
            if (MAPDISPLAY.styles[type]) {
                features[f].setStyle(MAPDISPLAY.styles[type].normal);
            }

            // Override the event link and have it open the popup on the map
            document.getElementById(id).addEventListener('mouseenter', MAPDISPLAY.highlightEvent);
            document.getElementById(id).addEventListener('mouseleave', MAPDISPLAY.unhighlightEvent);
        }
    }
    if (features.length) { MAPDISPLAY.setFeatures(features); }
}());

/**
 *   Created by Spike lee on 11/08/2017.
 */
// Routes yo
var express = require('express');
var router = express.Router();

var controller = require('../controllers/controller.js');

// Create new Trip
router.post('/api/trips/create', controller.createTrip);

// Find all Trips
router.get('/api/trips/findall', controller.findAllTrips);

// Find one Trip by id
router.get('/api/trips/:name', controller.findOneTrip);

//Create a location in a trip
router.post('/api/trips/createlocation', controller.createlocation)

//Find all Locations (temp for testing AR)
router.get('/api/locations/all', controller.findAllLocations);

module.exports = router;

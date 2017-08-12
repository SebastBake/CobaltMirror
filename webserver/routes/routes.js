/**
 *   Created by Spike lee on 11/08/2017.
 */
// Routes yo
var express = require('express');
var router = express.Router();

var controller = require('../controllers/controller.js');

// Create new cafe
router.post('/api/trips/create', controller.createTrip);

// Find all cafes
router.get('/api/trips/findall', controller.findAllTrips);

// Find one cafe by id
router.get('/api/trips/:name', controller.findOneTrip);

module.exports = router;

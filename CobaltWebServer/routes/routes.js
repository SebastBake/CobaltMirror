/**
 * Created by Spike lee on 11/08/2017.
 */
var express = require('express');
var router = express.Router();

var tripController = require('../controllers/Tripcontroller.js');
var userController = require('../controllers/Usercontroller.js');

//TRIP ROUTES

// Create new Trip
router.post('/api/trips/create', tripController.createTrip);


//Random search
router.get('/api/trips/findrandom', tripController.findRandomTrips);

//Find trip by mongo id
router.get('/api/trips/findbyid/:id', tripController.findOneTripByID);

// Find one Trip by name
router.get('/api/trips/:name', tripController.findOneTrip);


//Search all trips with text
router.get('/api/trips/search', tripController.findTripsByText);

//Delete Trip
router.put('/api/trips/delete', tripController.deleteTrip);

//Edit Trip
router.put('/api/trips/edit', tripController.editTrip);



// USER ROUTES

// Create User
router.post('/api/user/create', userController.createUser);

//Find one User
router.get('/api/user/:id', userController.findOneUser);

//Add saved trip
router.put('/api/user/addtrip', userController.Addtrip);

//Remove trip from saved trips
router.put('/api/user/removetrip', userController.Removetrip);

//Retrieve one User
router.get('/api/user/find/:username/:password', userController.retrieveOneUser);

//Find all users
router.get('/api/users', userController.findAllUsers);

// Return saved trips data
router.get('/api/user/savedtrips/:id', userController.savedTrips);


module.exports = router;

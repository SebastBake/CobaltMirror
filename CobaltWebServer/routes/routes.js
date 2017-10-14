/**
 * Created by Spike lee on 11/08/2017.
 */
// Routes yo
var express = require('express');
var router = express.Router();

var controller = require('../controllers/controller.js');
var usercontroller = require('../controllers/Usercontroller.js');

// Create new Trip
router.post('/api/trips/create', controller.createTrip);

// Find all Trips
router.get('/api/trips/findall', controller.findAllTrips);

//Random search
router.get('/api/trips/findrandom', controller.findRandomTrips);

//Find trip by mongo id
router.get('/api/trips/findbyid/:id', controller.findOneTripByID);

// Find one Trip by name
router.get('/api/trips/:name', controller.findOneTrip);


//Search all trips with text
router.get('/api/locations/search', controller.findTripsByText);

//Delete Trip
router.put('/api/trips/delete', controller.deleteTrip);

//Edit Trip
router.put('/api/trips/edit', controller.editTrip);



// USER ROUTES

// Create User
router.post('/api/user/create', usercontroller.createUser);

// Login User
//router.post('api/user/login', usercontroller.loginUser);

//Find one User
router.get('/api/user/:id', usercontroller.findOneUser);

//Add saved trip
router.put('/api/user/addtrip', usercontroller.Addtrip);

//Remove trip from saved trips
router.put('/api/user/removetrip', usercontroller.Removetrip);

//Retrieve one User
router.get('/api/user/find/:username/:password', usercontroller.retrieveOneUser);

//Find all users
router.get('/api/users', usercontroller.findAllUsers);

//update current trip of user
router.put('/api/user/updateCurrentTrip/', usercontroller.updateCurrentTripUser);

// Return saved trips data
router.get('/api/user/savedtrips/:id', usercontroller.savedTrips);


module.exports = router;

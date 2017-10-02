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

//Create a location in a trip
router.post('/api/trips/createlocation', controller.createlocation);

//Find all Locations (temp for testing AR)
router.get('/api/locations/all', controller.findAllLocations);

//Search all trips with text
router.get('/api/locations/search', controller.findTripsByText);



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

router.get('/api/users', usercontroller.findAllUsers);

//Chat
router.get('/api/allmsg', controller.showallmsg);
router.post('/api/putmsg', controller.addmsg);


module.exports = router;

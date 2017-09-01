/**
 *  Created by Spike lee on 11/08/2017.
 */
// all the different methods and stuff
// remember the code uses 3.2 mongo stuff for now
// will update if need once mlab moves to 3.4
var mongoose = require('mongoose');
var Trip = mongoose.model('trips');
var Location = mongoose.model('locations');

var createTrip = function(req, res) {
  var trip = new Trip({
    "name": req.body.name,
    "date": req.body.date,
    "size": req.body.size,
    "cost": req.body.cost,
    "locations": []
  });
  trip.save(function(err, newTrip) {
    if (!err) {
      res.send(newTrip);
    } else {
      res.sendStatus(400);
    }
  });
};

var createlocation = function(req, res) {
  Trip.findOneAndUpdate({
      name: "DemoTrip"
    }, {
      $push: {
        locations: {
          "title": req.body.title,
          "latitude": req.body.latitude,
          "longitude": req.body.longitude,
          "altitude": req.body.altitude,
          "description": req.body.description
        } //inserted data is the object to be inserted
      }
    }, {
      safe: true,
      upsert: true
    },
    function(err, trip) {
      console.log(err);
    }
  );
};



var findAllTrips = function(req, res) {
  Trip.find(function(err, trips) {
    if (!err) {
      res.send(trips);
    } else {
      res.sendStatus(404);
    }
  });
};

// there should only be one location so anyone can use this to find it
var findAllLocations = function(req, res) {
  Location.find(function(err, locations) {
    if (!err) {
      res.send(locations);
    } else {
      res.sendStatus(404);
    }
  });
}

// i will call the trip DemoTrip so this can also be used to call the trip
var findOneTrip = function(req, res) {
  var tripInx = req.params.name;
  Trip.find({
    name: req.params.name

  }, (function(err, trip) {
    if (!err) {
      console.log(trip);
      res.send(trip);

    } else {
      res.sendStatus(404);
    }
  }));
};

module.exports.createlocation = createlocation;
module.exports.createTrip = createTrip;
module.exports.findAllTrips = findAllTrips;
module.exports.findOneTrip = findOneTrip;
module.exports.findAllLocations = findAllLocations;

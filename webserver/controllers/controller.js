/**
 *  Created by Spike lee on 11/08/2017.
 */
// all the different methods and stuff
// remember the code uses 3.2 mongo stuff for now
// will update if need once mlab moves to 3.4
var mongoose = require('mongoose');
var Trip = mongoose.model('trips');

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

var findAllTrips = function(req, res) {
  Trip.find(function(err, trips) {
    if (!err) {
      res.send(trips);
    } else {
      res.sendStatus(404);
    }
  });
};

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

module.exports.createTrip = createTrip;
module.exports.findAllTrips = findAllTrips;
module.exports.findOneTrip = findOneTrip;

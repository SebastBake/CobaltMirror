/**
 *  Created by Spike lee on 11/08/2017.
 */
var mongoose = require('mongoose');
var Trip = mongoose.model('trips');
var User = mongoose.model('users');

var createTrip = function(req, res) {

  console.log(JSON.stringify(req.body));

  var trip = new Trip({
    "name": req.body.name,
    "description": req.body.description,
    "date": req.body.date,
    "size": req.body.size,
    "cost": req.body.cost,
    "locations": [],
    "usernames": [req.body.usernames[0]],
    "userids": [req.body.userids],
    "owner": req.body.userids[0]
  });

  for (var i = 0; i < req.body.locations.length; i++) {
    var newlocation = {
      "title": req.body.locations[i].title,
      "description": req.body.locations[i].description,
      "latitude": req.body.locations[i].latitude,
      "longitude": req.body.locations[i].longitude,
      "altitude": req.body.locations[i].altitude
    };
    trip.locations[i] = newlocation;
  }

  console.log(JSON.stringify(trip));

  trip.save(function(err, newTrip) {
    if (!err) {
      User.findOneAndUpdate({
        _id: req.body.userids[0]
      }, {
        $push: {
          'savedtrips': newTrip._id
        }
      }, function(err, data) {
        if (err) {
          return res.status(500).json({
            'error': 'error in adding'
          });
        } else {
          res.send(newTrip);
        }
      });
    } else {
      res.sendStatus(400);
    }
  });
};



var findAllTrips = function(req, res) {
  Trip.find(function(err, trips) {
    if (!err) {
      var n = 10;
      var result = new Array(n);
      for (var i = 0; i < n; i++) {
        result.push(trips[Math.floor(Math.random() * trips.length)]);
      }
      console.log(result);
      res.send(trips);
    } else {
      res.sendStatus(404);
    }
  });
};


// Find trip by name
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

var findOneTripByID = function(req, res) {
  var tripInx = req.params.id;
  Trip.find({
    _id: req.params.id

  }, (function(err, trip) {
    if (!err) {
      console.log(trip);
      res.send(trip);

    } else {
      res.sendStatus(404);
    }
  }));
};

var findTripsByText = function(req, res) {
  console.log(req.param("searchcontent"));
  //if blank input
  if (!req.query.searchcontent) {
    Trip.find({}, function(err, place) {
      if (!err) {
        res.send(place);
      } else {
        res.sendStatus(404);
      }
    });
  } else {
    //regex to do string search in mongodb
    var inputregex = {
      $regex: req.query.searchcontent,
      $options: 'i'
    };
    Trip.find({
      $or: [{
        locations: {
          $elemMatch: {
            title: inputregex
          }
        }
      }, {
        name: inputregex
      }]
    }, function(err, place) {
      if (!err) {
        if (!place.length) {
          var oristring = req.query.searchcontent;
          var sub = oristring.substring(0, 3);
          var match = {
            $regex: sub,
            $options: 'i'
          };
          console.log(sub);
          Trip.find({
            $or: [{
              locations: {
                $elemMatch: {
                  title: match
                }
              }
            }, {
              name: match
            }]
          }, function(err, places) {
            if (!err) {
              res.send(place);
            } else {
              res.sendStatus(404);
            }
          });
        } else {
          console.log("find matching");
          res.send(place);
        }

      } else {
        res.sendStatus(404);
      }
    });

  }
};

var findRandomTrips = function(req, res) {
  Trip.find(function(err, trips) {
    if (!err) {
      var n = 10;
      var result = [];
      for (var i = 0; i < n; i++) {
        var obj = trips[Math.floor(Math.random() * trips.length)]
        result.push(obj);
      }
      console.log(JSON.stringify(result));
      res.contentType('application/json');
      res.send(JSON.stringify(result));
    } else {
      res.sendStatus(404);
    }
  });
};

var deleteTrip = function(req, res) {
  Trip.findOneAndRemove({
    name: req.body.tripname
  }, (function(err, trip) {
    if (!err) {
      console.log(trip);
      res.send(trip);

    } else {
      res.sendStatus(404);
    }
  }));
};

var editTrip = function(req, res) {
  console.log(req.body);
  //console.log(res.body.locations);
  var locations = [];
  for (var i = 0; i < req.body.locations.length; i++) {
    var newlocation = {
      "title": req.body.locations[i].title,
      "description": req.body.locations[i].description,
      "latitude": req.body.locations[i].latitude,
      "longitude": req.body.locations[i].longitude,
      "altitude": req.body.locations[i].altitude
    };
    locations[i] = newlocation;
  }

  Trip.findOneAndUpdate({
    _id: req.body._id
  }, {
    $set: {
      'name': req.body.name,
      "description": req.body.description,
      "date": req.body.date,
      "size": req.body.size,
      "cost": req.body.cost,
      "locations": locations,
      "usernames": req.body.usernames,
      "userids": req.body.userids,
      "owner": req.body.owner
    }
  }, {
    new: true
  }, function(err, data) {
    if (!err) {
      console.log(data);
      res.send(data);

    } else {
      res.sendStatus(404);
    }
  });
}

module.exports.createTrip =
  createTrip;
module.exports.findAllTrips = findAllTrips;
module.exports.findOneTrip =
  findOneTrip;
module
  .exports.findTripsByText = findTripsByText;

module.exports.findRandomTrips =
  findRandomTrips;
module.exports.findOneTripByID = findOneTripByID;
module.exports.deleteTrip = deleteTrip;
module.exports.editTrip = editTrip;

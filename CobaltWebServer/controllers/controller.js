/**
 *  Created by Spike lee on 11/08/2017.
 */
// all the different methods and stuff
// remember the code uses 3.2 mongo stuff for now
// will update if need once mlab moves to 3.4
var mongoose = require('mongoose');
var Trip = mongoose.model('trips');
var Location = mongoose.model('locations');
var Chat = mongoose.model('chat');

var createTrip = function(req, res) {

  console.log(JSON.stringify(req.body));

  var trip = new Trip({
    "name": req.body.name,
    "description": req.body.description,
    "date": req.body.date,
    "size": req.body.size,
    "cost": req.body.cost,
    "locations": []
  });

  for (var i = 0; i < req.body.locations.length; i++) {
    trip.locations[i] = req.body.locations[i];
  }

  console.log(JSON.stringify(trip));

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

// there should only be one location so anyone can use this to find it
var findAllLocations = function(req, res) {
  Location.find(function(err, locations) {
    if (!err) {
      res.send(locations);
    } else {
      res.sendStatus(404);
    }
  });
};

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

//Temp chat testing
var showallmsg = function(req, res) {
  var stream = Chat.find({}, function(err, teams) {
    res.json(teams);
  });
  stream.on('data', function(chat) {
    socket.emit('chat', chat.content);
  });
};

var addmsg = function(req, res) {


  console.log(JSON.stringify(req.body));

  var msg = new Chat({
    "user": req.body.user,
    "msg": req.body.msg,
    "time": req.body.time,
    "room": req.body.room,
  });

  msg.save(function(err, newMsg) {
    if (!err) {
      var io = global.socketIO
      io.on('connection', function(socket) {
        socket.on('chat message', function(newMsg) {
          io.emit('chat message', newMsg);
        });
      });
      res.json(true);
    } else {
      res.sendStatus(400);
    }
  });
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

module.exports.createlocation = createlocation;
module.exports.createTrip = createTrip;
module.exports.findAllTrips = findAllTrips;
module.exports.findOneTrip = findOneTrip;
module.exports.findAllLocations = findAllLocations;
module.exports.findTripsByText = findTripsByText;
module.exports.showallmsg = showallmsg;
module.exports.addmsg = addmsg;
module.exports.findRandomTrips = findRandomTrips;

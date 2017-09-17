/**
 *  Created by Spike lee on 15/09/2017.
 */
// all the different methods and stuff
// remember the code uses 3.2 mongo stuff for now
// will update if need once mlab moves to 3.4
var mongoose = require('mongoose');
var User = mongoose.model('users');



// create a person document
var createUser = function(req, res) {

  var user = new User({
    "username": req.body.username,
    "password": req.body.password,
    "email": req.body.email,
    "savedtrips": [],
    "createdtrips": [],
    "favouritetrips": []
  });
  user.save(function(err, newUser) {
    if (!err) {
      res.send(newUser);
    } else {
      res.sendStatus(400);
    }
  });

};



// find a single person document
var findOneUser = function(req, res) {
  var personInx = req.params.id;
  User.findById(personInx, function(err, person) {
    if (!err) {
      res.send(person);
    } else {
      res.sendStatus(404);
    }
  });
};

// add trip to current user's saved list
var Addtrip = function(req, res) {
  var TripInx = req.body.tripid;
  User.findOneAndUpdate({
    _id: req.body.userid
  }, {
    $push: {
      'savedtrips': TripInx
    }
  }, function(err, data) {
    if (err) {
      return res.status(500).json({
        'error': 'error in adding'
      });
    }
    console.log(data);
    res.json(data);
  });
};

// add trip to current user's saved list
var Removetrip = function(req, res) {
  var TripInx = req.body.tripid;
  User.findOneAndUpdate({
    _id: req.body.userid
  }, {
    $pull: {
      'savedtrips': TripInx
    }
  }, function(err, data) {
    if (err) {
      return res.status(500).json({
        'error': 'error in removing'
      });
    }
    console.log(data);
    res.json(data);
  });
};



module.exports.createUser = createUser;
module.exports.Removetrip = Removetrip;
module.exports.findOneUser = findOneUser;
module.exports.Addtrip = Addtrip;

/**
 *  Created by Spike lee on 15/09/2017.
 */
var mongoose = require('mongoose');
var User = mongoose.model('users');
var Trip = mongoose.model('trips');



// create a person document
var createUser = function(req, res) {
  console.log(JSON.stringify(req.body));
  var user = new User({
    "username": req.body.username,
    "password": req.body.password,
    "email": req.body.email,
    "savedtrips": [],
    "createdtrips": [],
    "currenttrip": ""
  });
  user.save(function(err, newUser) {
    if (!err) {
      res.send(newUser);
    } else {
      console.log(err);
      res.sendStatus(400);
    }
  });

};

var retrieveOneUser = function(req, res) {
  var query = User.find();
  //get the Query String here
  var filterUsername = req.params.username;
  var filterPassword = req.params.password;
  if (filterUsername.length > 0 && filterPassword.length > 0) {
    query.where({
      $and: [{
        username: filterUsername
      }, {
        password: filterPassword
      }]
    });
  }
  query.exec(function(error, user) {
    //send the result back to front-end
    if (error) {
      return res.status(400).send({
        message: 'Server error:' + JSON.stringify(error)
      });
    } else {
      res.send(user);
    }
  });
};

// find all users
var findAllUsers = function(req, res) {
  User.find(function(err, users) {
    if (!err) {
      res.send(users);
    } else {
      res.sendStatus(404);
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
  console.log(req.body);
  User.findOneAndUpdate({
    _id: req.body.userid
  }, {
    $push: {
      'savedtrips': TripInx
    }
  }, {
    new: true
  }, function(err, data) {
    if (err) {
      return res.status(500).json({
        'error': 'error in adding'
      });
    }
    Trip.findOneAndUpdate({
      _id: TripInx
    }, {
      $push: {
        'usernames': req.body.username,
        'userids': req.body.userid
      }
    }, {
      new: true
    }, function(err, trip) {
      if (err) {
        return res.status(500).json({
          'error': 'error in adding'
        });
      }
    });
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
  }, {
    new: true
  }, function(err, data) {
    if (err) {
      return res.status(500).json({
        'error': 'error in removing'
      });
    }
    Trip.findOneAndUpdate({
      _id: TripInx
    }, {
      $pull: {
        'usernames': req.body.username,
        'userids': req.body.userid
      }
    }, function(err, trip) {
      if (err) {
        return res.status(500).json({
          'error': 'error in adding'
        });
      }
    });
    console.log(data);
    res.json(data);
  });
};


var savedTrips = function(req, res) {
  personInx = req.params.id
  User.findById(personInx, function(err, person) {
    if (!err) {
      Trip.find({
        '_id': {
          $in: person.savedtrips
        }
      }, function(err, docs) {
        res.send(docs);
        console.log(docs);
      });
    } else {
      res.sendStatus(404);
    }
  });

}


module.exports.createUser = createUser;
module.exports.Removetrip = Removetrip;
module.exports.findOneUser = findOneUser;
module.exports.Addtrip = Addtrip;
module.exports.retrieveOneUser = retrieveOneUser;
module.exports.findAllUsers = findAllUsers;
module.exports.savedTrips = savedTrips

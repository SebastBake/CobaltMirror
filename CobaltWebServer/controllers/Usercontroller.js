/**
 *  Created by Spike lee on 15/09/2017.
 */
// all the different methods and stuff
// remember the code uses 3.2 mongo stuff for now
// will update if need once mlab moves to 3.4
var mongoose = require('mongoose');
var User = mongoose.model('users');
var Trip = mongoose.model('trips');



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

var retrieveOneUser = function(req, res) {

  var query=User.find();
    //get the Query String here
    var filterUsername=req.params.username;
    var filterPassword=req.params.password;
    if(filterUsername.length>0 && filterPassword.length>0){
      query.where({$and:[{username:filterUsername},{ password:filterPassword}]});
    }
    query.exec(function (error, user) {
    //send the result back to front-end
    if (error) {
      return res.status(400).send({message: 'Server error:' + JSON.stringify(error)});
    } else {
    res.send(user);
    }
    });
  };

// find all users
var findAllUsers = function(req,res){
  User.find(function(err,users){
    if(!err){
      res.send(users);
    }else{
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
    Trip.findOneAndUpdate({
      _id: TripInx
    }, {
      $push: {
        'users': req.body.username
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

    Trip.findOneAndUpdate({
      _id: TripInx
    }, {
      $pull: {
        'users': req.body.username
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

// Send user data back to client if it matches database entry
// var loginUser = function(req, res) {

//     // Find matching user
//     var query = {
//       username: req.username,
//       password: req.password
//     }


//     User.findOne(query, function(err, user) {
//       if (!err) {
//         res.send(user);
//       } else {
//         return res.status(500).json({
//           'error': 'Incorrect username or password.'
//         });
//       }
//   });
// }




module.exports.createUser = createUser;
module.exports.Removetrip = Removetrip;
module.exports.findOneUser = findOneUser;
module.exports.Addtrip = Addtrip;
module.exports.retrieveOneUser = retrieveOneUser;
module.exports.findAllUsers = findAllUsers;

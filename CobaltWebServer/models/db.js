/**
 *  Created by Spike lee on 11/08/2017.
 */
// Create database
var mongoose = require('mongoose');
mongoose.connect(
  'mongodb://admin:admin@ds039145.mlab.com:39145/heroku_5g0994js',
  function(err) {
    if (!err) {
      console.log('Connected to mongo');
    } else {
      console.log('Failed to connect to mongo');
    }
  });

//Schema
require('./trips.js');
require('./users.js');

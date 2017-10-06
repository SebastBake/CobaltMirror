/**
 * Created by Spike lee on 11/08/2017.
 */

var mongoose = require('mongoose');
var userSchema = mongoose.Schema({
  "username": String,
  "password": String,
  "email": String,
  "savedtrips": [String],
  "createdtrips": [String],
  "currenttrip": String,
  "latitude": Number,
  "longitude": Number,

});
mongoose.model('users', userSchema);

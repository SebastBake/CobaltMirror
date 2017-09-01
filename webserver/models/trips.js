/**
 * Created by Spike lee on 11/08/2017.
 */

var mongoose = require('mongoose');
var tripSchema = mongoose.Schema({
  "name": String,
  "date": String,
  "size": String,
  "cost": String,
  "locations": [{
    "title": String,
    "latitude": Number,
    "longitude": Number,
    "altitude": Number,
    "description": String
  }]
});
mongoose.model('trips', tripSchema);

/**
 * Created by Spike lee on 11/08/2017.
 * Temp schema for AR testing
 */

var mongoose = require('mongoose');
var locationsSchema = mongoose.Schema({
  "title": String,
  "latitude": Number,
  "longitude": Number,
  "altitude": Number,
  "Description": String

});
mongoose.model('locations', locationsSchema);

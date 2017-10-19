/**
 * Created by Spike lee on 11/08/2017.
 * Trip Schema used by mongo.
 */

var mongoose = require('mongoose');
var tripSchema = new mongoose.Schema({
  "name": String,
  "description": String,
  "date": String,
  "size": String,
  "cost": String,
  "locations": [{
    "title": String,
    "latitude": Number,
    "longitude": Number,
    "altitude": Number,
    "description": String
  }],
  "usernames": [String],
  "userids": [String],
  "owner": String
});

//Index all relevant text fields to help with search.
tripSchema.index({
  name: 'text',
  'locations.title': 'text',
  description: 'text'
});

mongoose.model('trips', tripSchema);

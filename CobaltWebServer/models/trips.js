/**
 * Created by Spike lee on 11/08/2017.
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
  "users": [String],
  "owner": String
});
//Helps with search
tripSchema.index({
  name: 'text',
  'locations.title': 'text',
  description: 'text'
});

mongoose.model('trips', tripSchema);

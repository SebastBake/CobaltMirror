var mongoose = require('mongoose');
var chatSchema = mongoose.Schema({
  "msg": String,
  "user": String,
  "time": String,
  "room": String

});
mongoose.model('chat', chatSchema);

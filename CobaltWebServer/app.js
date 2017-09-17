/**
 *  Created by Spike lee on 11/08/2017.
 */
// Set up express
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json());

// Database setup
require('./models/db.js');

// Routes setup
var routes = require('./routes/routes.js');
app.use('/', routes);

// Start the server

var port = process.env.PORT || 5000

var server = app.listen(port, function(req, res) {
  var host = server.address().address;

  console.log("Example app listening at http://%s:%s", host, port)
});

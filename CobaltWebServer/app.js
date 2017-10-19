/**
 *  Created by Spike lee on 11/08/2017.
 */
// Set up express
var express = require('express');
var io = require('socket.io')(server);
global.socketIO = io;
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json());

// Database setup
require('./models/db.js');

// Routes setup
var routes = require('./routes/routes.js');
app.use('/', routes);

// Start the server

var port = process.env.PORT || 3000;

var server = require('http').createServer(app);

var firebase = require('firebase-admin');
var request = require('request');

// Firebase Cloud Messaging Server API key
var API_KEY =
  "AAAAK5_LmRw:APA91bG7_VkMa_2gTjhPVo_Pk5TzaZjq3dGedVrv7TTyZDTjUnyIUln4o_3uCjqkklzZOihg8Y_VDxs-LEfS1MozuinAdIKA2qjaODJbA6-LYgdU1di87DsvOkO04J4XImcDH8-BwU5J";


// Fetch the service account key JSON file contents
var serviceAccount = require(
  "./colbalt-179409-firebase-adminsdk-6w8a2-0f7b5e6378.json");

// Initialize the app with a service account, granting admin privileges
firebase.initializeApp({
  credential: firebase.credential.cert(serviceAccount),
  databaseURL: "https://colbalt-179409.firebaseio.com/"
});
ref = firebase.database().ref();

// listen for notifications from app
function listenForNotificationRequests() {
  var requests = ref.child('notificationRequests');
  requests.on('child_added', function(requestSnapshot) {
    var request = requestSnapshot.val();
    sendNotificationToUser(
      request.username,
      request.message,
      request.fromUser,
      function() {
        requestSnapshot.ref.remove();
      }
    );
  }, function(error) {
    console.error(error);
  });
};

// Sends notifications to firebase
function sendNotificationToUser(username, message, fromUser, onSuccess) {
  request({
    url: 'https://fcm.googleapis.com/fcm/send',
    method: 'POST',
    headers: {
      'Content-Type': ' application/json',
      'Authorization': 'key=' + API_KEY
    },
    body: JSON.stringify({
      notification: {
        title: fromUser + ":" + message
      },
      to: '/topics/user_' + username
    })
  }, function(error, response, body) {
    if (error) {
      console.error(error);
    } else if (response.statusCode >= 400) {
      console.error('HTTP Error: ' + response.statusCode + ' - ' + response
        .statusMessage);
    } else {
      onSuccess();
    }
  });
}

// start listening
listenForNotificationRequests();



server.listen(port, function() {
  console.log('Express server listening on port ' + port);
});


// For testing
module.exports = server

/*
 * Server-side Testing
 */


//During the test the env variable is set to test
process.env.NODE_ENV = 'test';

//All requirements and models to test server
var mongoose = require('mongoose');
var Trip = require('../models/trips');
var User = require('../models/users');
var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();
var assert = chai.expect();

chai.use(chaiHttp);



// Testing the find all route
describe('/GET trips', () => {
  it('it should GET all the trips', (done) => {
    chai.request(server)
      .get('/api/trips/findall')
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        done();
      });
  });
});

// Test the /POST route
describe('/POST createTrip', () => {
  it('it should POST Trip with fields', (done) => {
    var trip = {
      name: "DemoTrippy",
      description: "This is the description of the demo trip!",
      date: "13/10/17",
      size: "1-5",
      cost: "$$",
      locations: [{
        description: "University",
        altitude: 50,
        longitude: 144.96117379999998,
        latitude: -37.7963689,
        title: "University of Melbourne"
      }],
      usernames: [
        "sebast",
        "heyhey"
      ],
      userids: [
        "59c0ab8d8159ee001f93a4e8",
        "59bf3b26b2f0fe001fbc703b"
      ],
      owner: "59c0ab8d8159ee001f93a4e8"
    }
    chai.request(server)
      .post('/api/trips/create')
      .send(trip)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        res.body.should.have.property('locations');
        res.body.locations.should.be.a('array')
        done();
      });
  });

});


// Test the /Put delete trip route
describe('/Put delete trip', () => {
  it('it should be able to put trip', (done) => {
    var tripname = "DemoTrippy"
    var putObject = {
      "tripname": tripname
    }
    chai.request(server)
      .put('/api/trips/delete')
      .send(putObject)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        done();
      });
  });
});



//Test the /Get randomTrips route
describe('/Get randomTrips', () => {
  it('it should be able to GET 10 random Trips with fields', (done) => {
    chai.request(server)
      .get('/api/trips/findrandom')
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        res.body.should.have.lengthOf(10);
        done();
      });
  });

});


//Test the /Get find trip by mongo id route
describe('/Get Trip with id', () => {
  it('it should be able to GET Trip with id', (done) => {
    var tripId = "59a89ca1734d1d25a0f4ba31"
    chai.request(server)
      .get('/api/trips/findbyid/' + tripId)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        res.body[0].should.have.property("name");
        res.body[0].name.should.equal("DemoTrip");
        done();
      });
  });

});

//Test the /Get find trip by name route
describe('/Get Trip with name', () => {
  it('it should be able to GET Trip with its name', (done) => {
    var tripName = "DemoTrip"
    chai.request(server)
      .get('/api/trips/' + tripName)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        res.body[0].should.have.property("name");
        res.body[0].name.should.equal("DemoTrip");
        done();
      });
  });

});

//Test the /Get search trips route
describe('/Get search Trip', () => {
  it('it should be able to GET search trips', (done) => {
    var tripName = "searchcontent=DemoTrip"
    chai.request(server)
      .get('/api/locations/search?' + tripName)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        res.body[0].should.have.property("name");
        res.body[0].name.should.equal("DemoTrip");
        done();
      });
  });

});



//Test the /POST route
describe('/POST create user', () => {
  it('it should POST user with fields', (done) => {
    var user = {
      username: "testingCreateUser",
      password: "1223",
      email: "heay@gmail.com"
    }
    chai.request(server)
      .post('/api/user/create')
      .send(user)
      .end((err, res) => {
        console.log(res);
        res.should.have.status(200);
        res.body.should.be.a('object');
        res.body.should.have.property('username');
        res.body.username.should.equal('testingCreateUser');
        done();
      });
  });

});

// Test the /Get find user by id route
describe('/Get user with id', () => {
  it('it should be able to GET user with id', (done) => {
    var userid = "59db139b36c512001feb3ea4"
    chai.request(server)
      .get('/api/user/' + userid)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        res.body.should.have.property("username");
        res.body.username.should.equal("shiba");
        done();
      });
  });

});



// Test the /Put trip int savedtrips route

describe('/Put trip into savedtrips', () => {
  it('it should be able to put trip', (done) => {
    var userid = "59db139b36c512001feb3ea4"
    var username = "shiba"
    var tripid = "59daecf52b53f7001f25e135"
    var putObject = {
      "username": username,
      "userid": userid,
      "tripid": tripid
    }
    chai.request(server)
      .put('/api/user/addtrip')
      .send(putObject)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        res.body.should.have.property("savedtrips");
        res.body.savedtrips.should.have.members([
          "59daecf52b53f7001f25e135"
        ]);
        done();
      });
  });

});

//Test the /Put trip into removetrips route
describe('/Put trip into remove savedtrips', () => {
  it('it should be able to put trip', (done) => {
    var userid = "59db139b36c512001feb3ea4"
    var username = "shiba"
    var tripid = "59daecf52b53f7001f25e135"
    var putObject = {
      "username": username,
      "userid": userid,
      "tripid": tripid
    }
    chai.request(server)
      .put('/api/user/removetrip')
      .send(putObject)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        res.body.should.have.property("savedtrips");
        res.body.savedtrips.should.have.members([]);
        done();
      });
  });

});



//Test the /Get retrieve user
describe('/Get user with id and password', () => {
  it('it should be able to GET user with username and password', (
    done) => {
    var username = "shiba"
    var password = "inu"
    chai.request(server)
      .get('/api/user/find/' + username + "/" + password)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        res.body[0].should.have.property("username");
        res.body[0].username.should.equal("shiba");
        done();
      });
  });

});



//Test the /Get find all users
describe('/Get all users ', () => {
  it('it should be able to GET all users', (done) => {
    chai.request(server)
      .get('/api/users')
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        done();
      });
  });

});


//Test the /PUT editTrip route
describe('/PUT editTrip', () => {
  it('it should put edit trip', (done) => {
    var trip = {
      "_id": "59daecf52b53f7001f25e135",
      "name": "testingedit",
      "description": "testingedit",
      "date": "124",
      "size": "1-5",
      "cost": "$$",
      "owner": "seb",
      "userids": [
        "seb"
      ],
      "usernames": [
        "seb"
      ],
      "locations": [{
        "_id": "59daecf52b53f7001f25e136",
        "altitude": 100,
        "longitude": 144.9472673,
        "latitude": -37.77572610000001,
        "title": "SGA SECURITY SERVICES"
      }, {
        "_id": "59daf3745e2404001f760b28",

        "altitude": 100,
        "longitude": 144.9472529,
        "latitude": -37.774495,
        "title": "Boulton's Cleaning Service"
      }]
    }
    chai.request(server)
      .put('/api/trips/edit')
      .send(trip)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        res.body.should.have.property('locations');
        res.body.locations.should.be.a('array')
        done();
      });
  });

});

//Test the /Get saved trips
describe('/Get user with id', () => {
  it('it should be able to GET trips from savedtrips array', (done) => {
    var userid = "59daebad2b53f7001f25e131"
    chai.request(server)
      .get('/api/user/savedtrips/' + userid)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('array');
        done();
      });
  });

});

//During the test the env variable is set to test
process.env.NODE_ENV = 'test';
var mongoose = require('mongoose');
var Trip = require('../models/trips');
//Require the dev-dependencies
var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();

chai.use(chaiHttp);

/*
* Testing the find all route
*/
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


  /*
  * Test the /POST route
  */
  describe('/POST Trip', () => {
      it('it should POST Trip with fields', (done) => {
        var trip = {
            name: "Fh",
            description: "Ff",
            date: "588",
            size: ">10",
            cost: "$$$",
            locations: [
              {
                title: "University of Melbourne",
                altitude: 100,
                latitude: -37.7963689,
                longitude: 144.96117379999998,
              }
            ]
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

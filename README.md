# Morange - A Travel App
Morange is a travel app for meeting new people and exploring and learning about new places. The app allows people to arrange trips by making it easy to share the locations, date, cost, and size of a trip with other like-minded travellers. The app also has chat and AR functionality, further helping people to meet up and navigate while travelling.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Contact one of the project members to get the required licenses, for development using our proprietary dependencies:

- Wikitude is used for the AR. The team currently uses a student license.
- A google services json file is required to gain access to the Google API (used for location data) and Firebase (used for user location sharing and chat).

### Android App Setup

- The app must be imported into Android Studio.
- The app will not run without the google-services.json file. Paste the google-services.json file into the app folder in TourList.
- Use Android Studio to build the app on to your machine.

## Running the tests

There are a suite of Unit tests in the folder /Cobalt/Client/TourList/app/src/test/java/.
Run these whenever you make a change to ensure the changes haven't created undesired side effects.

### Server Setup & Use

#### Install a copy of the server

To set up the up the server, make sure to install [Node.js and npm](https://nodejs.org/en/).
After installing Node.js and npm, install the server dependencies by running the command `npm install`, from the server root directory.

Once all dependcies are installed you are ready to customize and modify the server.

#### Run the server with the app

We used (heroku)(https://devcenter.heroku.com/articles/getting-started-with-nodejs#introduction) for hosting, but the server can be run on any hosting service with Node.js.

If you want to gain access to the main branch of the server, please request access to the heroku.

If you're running your own copy of the server, make sure to point the app towards your server.

Routes can be found in `./routes/routes.js` and when live will look like this:
`https://<YOUR SERVER ADDRESS>/<NAME OF YOUR ROUTE>`

In the app change all URLs to point to the routes that are needed.
For example in `CreateTripPostRequest.java`, modify:

`private static final String CREATE_TRIP_URL = https://<YOUR SERVER ADDRESS>/<NAME OF YOUR ROUTE>`

#### Server testing

Server-side testing is done using [mocha](https://mochajs.org/) and [chai](http://chaijs.com/).
Both can be installed using `npm install`. 

Tests can be found and added in `./tests/tests.js`.  
To run tests use the `npm test` command.

## Built With

* [Wikitude](http://www.wikitude.com/) - The AR framework used
* [Firebase](https://firebase.google.com/) - Cloud messaging service
* [Mongoose](https://www.mongoosejs.com) - MongoDB library for node.js

## Authors - Team Cobalt

* **Hong** - *Dev Lead* - [linh3@student.unimelb.edu.au](linh3@student.unimelb.edu.au)
* **Spike** - *Dev Team* - [spikel@studenet.unimelb.edu.au](spikel@student.unimelb.edu.au)
* **Sebastian** - *Dev Team* - [sebastianb1@student.unimelb.edu.au](sebastianb1@student.unimelb.edu.au)
* **Alexander** - *QA Lead* - [awhite5@student.unimelb.edu.au](awhite5@student.unimelb.edu.au)
* **An** - *Design Lead* - [thanhn6@student.unimelb.edu.au](thanhn6@student.unimelb.edu.au) 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


# Cobalt

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Contact one of the project members to get the required google-services.json file.

Wikitude

Google API

Firebase

### Installing

- Paste the google-services.json file into the app folder in TourList.
- Use android studio to compile the app on to your machine.

### Server Setup & Use

#### Installation
To set up the up the server, make sure to install nodejs and npm. You can find nodejs and npm [HERE](https://nodejs.org/en/).
After installing nodejs and npm, install all dependencies by using the command `npm install`.

First, in the CobaltWebServer folder, look at your package.JSON:

```
  "dependencies": {
    #...,
    "body-parser": "^1.17.1",
    "express": "^4.15.4",
    "firebase-admin": "^5.3.0",
    "mongoose": "^4.11.6",
    #...,
  }
   ```
 
 Then install all dependencies using the comand `npm install` 
 For example:
 
 `npm install body-parser`
 
 Once all dependcies are installed you are ready to customize and modify the server.
 
 #### Use
 To be able to use your server with the app, please host the app on a hosting platform of your choice
 We used heroku to host and more information about heroku can be found [HERE](https://devcenter.heroku.com/articles/getting-started-with-nodejs#introduction).
 
 After hosting, make sure to point the app towards your server.
 
 Routes can be found in `./routes/routes.js` and when live will look like this:
 `https://<YOUR SERVER ADDRESS>/<NAME OF YOUR ROUTE>`
 
 In the app change all URLs to point to the routes that are needed.
 For example in `CreateTripPostRequest.java`, modify:

 `private static final String CREATE_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/create";`
 
 to
 
 `private static final String CREATE_TRIP_URL = https://<YOUR SERVER ADDRESS>/<NAME OF YOUR ROUTE>`
 
 If you want to gain access to the main branch of the server, please request access to the heroku.


## Running the tests

There are a suite of Unit tests in the folder /Cobalt/Client/TourList/app/src/test/java/.
Run these whenever you make a novel change to ensure the changes haven't created undesired side effects.

## Built With

* [Wikitude](http://www.wikitude.com/) - The AR framework used
* [Firebase](https://firebase.google.com/) - Cloud messaging service
* [Mongoose](https://www.mongoosejs.com) - MongoDB library for node.js

## Authors

* **Hong** - *Dev Lead* - [linh3@student.unimelb.edu.au](linh3@student.unimelb.edu.au)
* **Spike** - *Dev Team* - [spikel@studenet.unimelb.edu.au](spikel@student.unimelb.edu.au)
* **Sebastian** - *Dev Team* - [sebastianb1@student.unimelb.edu.au](sebastianb1@student.unimelb.edu.au)
* **Alexander** - *QA Lead* - [awhite5@student.unimelb.edu.au](awhite5@student.unimelb.edu.au)
* **An** - *Design Lead* - [thanhn6@student.unimelb.edu.au](thanhn6@student.unimelb.edu.au) 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


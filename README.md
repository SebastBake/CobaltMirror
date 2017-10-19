# Morange - A Travel App
Morange is a travel app for meeting new people and exploring new places. The app allows people to arrange trips by making it easy to share the locations, date, cost, and size of a trip with other like-minded travellers. The app also has chat and AR functionality, further helping people to meet up and navigate while travelling.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Contact one of the project members to get the required licenses for development using our proprietary dependencies:

- Wikitude is used for the AR. Team Cobalt currently uses a student license.
- A google services json file is required to gain access to the Google API (used for location data) and Firebase (used for user location sharing and chat).
- Google maps api and static maps api

### Android App Setup

- The app must be imported into Android Studio.
- The app will not run without the google-services.json file. Paste the google-services.json file into the app folder in Morange.
- Use Android Studio to build the app on to your machine.

### Logo and images

The logos and various UI components (excluding Wikitude assets used in AR) were created through Adobe Illustrator.

### API/Libraries setup

Please note that the libraries may not be up to date in the future and the functions in the libraries are subject to being deprecated by the owners.

#### Permissions

To enable correct function of the various libraries and function of the app, the AndroidManifest.xml requires the following permissions:

    <uses-permission android:name="android.permission.INTERNET" />
    
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    
    <uses-permission android:name="android.permission.ACCESS_GPS" />
    
    <uses-permission android:name="android.permission.CAMERA" />
    
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

#### Wikitude

This app currently uses the Wikitude API for the augmented reality function. To retrieve a key to use, go to the wikitude website: https://www.wikitude.com/

To add your own key, head into the strings.xml file and edit the header to your own key:

    <string name="wikitude_key">YOUR_WIKITUDE_KEY</string>

The app is currently using a student license and not used for commercial use. The developer must retrieve the appropriate key from the Wikitude website if they wish to use the develop the app for commercial uses.

Useful documentation can be found at: http://www.wikitude.com/external/doc/documentation/latest/android/poi.html#geo-ar-points-of-interest

The app currently uses a modified JS script, as well as the same CS and image assets in the /assets folder origianlly provided by wikitude.

The Wikitude library is found under the lib folder in the root.
The following dependency should be added:

      compile(name: 'wikitudesdk', ext: 'aar')
      
The activity containing the Wikitude AR view must contain the following in the layout.xml:

    <com.wikitude.architect.ArchitectView android:id="@+id/architectView"
        android:layout_width="fill_parent" android:layout_height="fill_parent"/>

In the AndroidManifest.xml, the following features are utilised and requried as well. Please ensure that the device using the app has the following:

    <uses-feature
        android:name="android.hardware.camera"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.location"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.sensor.accelerometer"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.sensor.compass"
        android:required="true" />
    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true" />
    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true" />

#### Google API setup and Picasso

The app will utilise the following libraries:

- Google Places API for Android 
- Google Maps Android API 
- Google Static Maps API

To get keys for the api, the user must head into: https://console.developers.google.com
The console will allow the user to activate the various APIs and enable them for use in the app.
To add the key, open the AndroidManifest.xml and search for
        
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value= YOUR_ANDROID_KEY/>

Under the application header.
It is important for the following to be in the build.gradle file:

  dependencies:
      
      compile 'com.google.maps.android:android-maps-utils:0.4.+'
      
      compile 'com.google.android.gms:play-services:11.2.0'

      compile 'com.squareup.picasso:picasso:2.3.3'

  repositories:
      
      flatDir {
          dirs 'libs'
      }
      maven {
          url "https://maven.google.com"
      }
      
Picasso is used to load images from the Static Maps API into the image views.

#### Firebase Setup

The app will utilise Firebase for its chat function and the automatic updates for live tracking.
To add a key, a google JSON file must be generated.
To do this, enter the website: https://console.firebase.google.com
And link the account to a google project that has the keys for the Google APIs. This will enable a JSON key to be generated and linked with the app.

The dependencies required include:

    compile 'com.google.firebase:firebase-core:11.2.0'
    
    compile 'com.google.firebase:firebase-database:11.2.0'
    
    compile 'com.google.firebase:firebase-auth:11.2.0'
    
    compile 'com.google.firebase:firebase-messaging:11.2.0'
    
    compile 'com.firebaseui:firebase-ui:0.6.0'
    
    compile 'com.google.firebase:firebase-crash:10.2.0'
    
The JSON can also be directly loaded into the project from Android Studio by opening Tools > Firebase. This will open a Firebase side bar, which allows the user to connect the app to their Firebase account that has been set up with the project.

#### Permission Manager

The PermissionManager.java class utilised by the app is for checking permissions when first running the app.
The author of the code is karanchuri, and the git repository can be found at: https://github.com/karanchuri/PermissionManager


### Android unit testing

There are a suite of unit tests in the folder /Cobalt/Client/TourList/app/src/test/java/.
Run the tests whenever you make a change to ensure the changes haven't created undesired side effects.
This will utilise the [Mockito library](http://site.mockito.org/), JUnit and [Robolectric](http://robolectric.org/).
Ensure the depedencies have been compiled in the build.gradle for testing:

    testCompile 'junit:junit:4.12'
    
    testCompile 'org.robolectric:robolectric:3.4.2'
    
    testCompile 'org.robolectric:shadows-support-v4:3.0'
    
    testCompile 'org.mockito:mockito-core:1.10.19'
    
When running the app from studio, these tests will automatically run before the application is loaded to the device to ensure changes do not break the other functions.
You can run the tests directly from studio from right clicking the test package and directly running the tests.

### Server Setup & Use

#### Install a copy of the server

To set up the up the server, make sure to install [Node.js and npm](https://nodejs.org/en/).
After installing Node.js and npm, install the server dependencies by running the command `npm install`, from the server root directory.

Once all dependcies are installed you are ready to customize and modify the server.

#### Run the server with the app

We used [Heroku](https://devcenter.heroku.com/articles/getting-started-with-nodejs#introduction) for hosting, but the server can be run on any hosting service with Node.js.

If you want to gain access to the main branch of the server, please request access to the Heroku repository.

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
* [Google libraries](https://developers.google.com/api-client-library/java/) - Google APIs
* [PermissionManager](https://github.com/karanchuri/PermissionManager) - Permission manager
* [Picasso](http://square.github.io/picasso/) - Image manager
* [Mockito](http://site.mockito.org/) - Android Testing
* [Robolectric](http://robolectric.org/) - Android Testing

## Authors - Team Cobalt

* **Hong** - *Dev Lead* - [linh3@student.unimelb.edu.au](linh3@student.unimelb.edu.au)
* **Spike** - *Dev Team* - [spikel@studenet.unimelb.edu.au](spikel@student.unimelb.edu.au)
* **Sebastian** - *Dev Team* - [sebastianb1@student.unimelb.edu.au](sebastianb1@student.unimelb.edu.au)
* **Alexander** - *QA Lead* - [awhite5@student.unimelb.edu.au](awhite5@student.unimelb.edu.au)
* **An** - *Design Lead* - [thanhn6@student.unimelb.edu.au](thanhn6@student.unimelb.edu.au) 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


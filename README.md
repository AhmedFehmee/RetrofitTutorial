# RetrofitTutorial
RetrofitTutorial - Example app that makes a simple login and upload file with Multipart with using Retrofit 2.

# API Calls in Android Using Retrofit 2
If you read enough about Android development, you'll inevitably run into a library named [Retrofit](http://square.github.io/retrofit/), a type-safe HTTP client for Android and Java.  Everyone online seems to rave about it, but it can be daunting to learn how or why to use it.  Here we'll start from the ground up and teach you everything you need to know to get up and running with Retrofit.


## Code Example

Let's take a look at some code.  We made a sample app with some practical applications of Retrofit.  The app doesn't do anything fancy, it just takes the followers of a user on GitHub and displays one of them on the screen.

If you want to follow along, create a new app with a blank activity and add a button and a textview to the the activity's layout.

Retrofit is added to the project through the following lines in the the [Gradle file](https://github.com/SiGMobileUIUC/RetrofitTutorial/blob/master/app/build.gradle):

```
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```

Before we start anything else, we need to add the following line to our [Manifest](https://github.com/SiGMobileUIUC/RetrofitTutorial/blob/master/app/src/main/AndroidManifest.xml) to grant our app permission to access the internet.

```<uses-permission android:name="android.permission.INTERNET" />```

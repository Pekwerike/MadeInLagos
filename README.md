# MadeInLagos
MadeInLagos is an android application that displays a collection of products for a user to buy.

## Architecture  
MadeInLagos was built with the Model View ViewModel (MVVM) architectural pattern. MVVM architecture allows the app to utilize the long-term benefits that come with separation of concerns, easier testing for individual components, and emphasis on SOLID principles

## Application layers
### Network 
The app uses Retrofit to make HTTP requests to https://tour.silent.ws/product and Retrofit-Moshi converter to parse HTTP responses into Kotlin objects.

### Cache/Persistence
MadeInLagos utilizes the Room persistence library to cache the network results into an on-device database. The database is made up of two tables the Product table and the Product Review table. The app defines a one-many relationship between the product table and the product review table.

### Repository
MadeInLagos defines the Main Repository API which specifies the various operations for the app Main Repository. The main repository layer acts as a mediator between the network layer and the database layer. This layer is also responsible for providing data to the ViewModels.

### ViewModels
The app ViewModels hold the UI states for activities or other possible UI controllers like fragments. By using this pattern, the app can survive configuration changes, theme switches, and other system events that can lead to the destruction of an activity/ UI controller. 

### Concurrency
To make the most of the device processor and bestow on the app a buttery smooth experience, Kotlin coroutines and flow, along with other java functionalities like
synchronization are used for asynchronous operations.

### Dependency Injection and Management
MadeInLagos uses Dagger-Hilt for automated dependency injection and dependency management across all the other layers of the app.

### UI
MadeInLagos UI is made up of multiple activities. As opposed to the Single Activity Multiple fragment patterns, using multiple activities for this project provided a higher level of stability(less system bug encounter), faster configuration, and better integration with the android material design container transformation library. 

### Test 
The test layer boasts of several instrumentation unit and JVM unit tests for various components of the app. 

## Screenshots 
<img src="https://user-images.githubusercontent.com/43956851/118416093-e3a48080-b6a5-11eb-8c67-886fb083e2a1.jpg" width="310" height="640"> <img src="https://user-images.githubusercontent.com/43956851/118416095-e56e4400-b6a5-11eb-8fc9-f73ffa1296f6.jpg" width="310" height="640"> <img src="https://user-images.githubusercontent.com/43956851/118416096-e606da80-b6a5-11eb-903c-11c429faeca7.jpg" width="310" height="640"> 

### Watch video preview 
<a href="http://www.youtube.com/watch?feature=player_embedded&v=GorNBQDF_RU
" target="_blank"><img src="http://img.youtube.com/vi/GorNBQDF_RU/0.jpg" 
alt="Watch video" width="240" height="180" border="10" /></a>

## Open Source Libraries
- [Android jetpack libraries](https://developer.android.com/jetpack/androidx/explorer?gclid=CjwKCAjwhYOFBhBkEiwASF3KGTac0YRSxUBKOGs8ijgrUnngjG5jASGw5R09q_RvBa8o757jVzWoFRoCywYQAvD_BwE&gclsrc=aw.ds) - Jetpack is a suite of libraries to help developers follow best practices, reduce boilerplate code, and write code that works consistently across Android versions and devices so that developers can focus on the code they care about.
   - [Hilt](https://dagger.dev/hilt/) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
   - [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=CjwKCAjwhYOFBhBkEiwASF3KGd14uH0mczjs1QvWp9T6rrfHQgEaKr4oQ9CH9Rc_c4-HWQ2BRqfICRoC7GYQAvD_BwE&gclsrc=aw.ds) - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
   - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAjwhYOFBhBkEiwASF3KGbiALGtSlPqie66yGppiYX8OudQ-Su9iKz6u7RR4HAje-SqEPGhJnRoCetYQAvD_BwE&gclsrc=aw.ds) - The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. 
   - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData is an observable data holder class 
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi) - Moshi is a modern JSON library for Android and Java.
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
- [Mockito](https://site.mockito.org/) - Tasty mocking framework for unit tests in Java 
- [JUnit](https://junit.org/junit4/) - JUnit is a simple framework to write repeatable tests

## How to install and run the app?
Clone this repository and open it using android studio 4.2 or higher.

## TODOs
1. UI Tests 
2. Integrate CI/CD 

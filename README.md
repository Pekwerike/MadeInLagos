# MadeInLagos
MadeInLagos is an android application that displays a collection of products for a user to buy, 

## Architecture  
MadeInLagos was built with the Model View ViewModel (MVVM) architectural pattern. MVVM architecture allows the app to utilize the long-term benefits that come with separation of concerns, easier testing for individual components, and emphasis on SOLID principles

## Application layers
### Network 
The app uses Retrofit to make HTTP requests to the server and Retrofit-Moshi converter to parse HTTP responses into Kotlin objects.

### Cache/Persistence
MadeInLagos utilizes the Room persistence library to cache the network results into an on-device database. The database is made up of two tables the Product table and the Product Review table. The app defines a one-many relationship between the product table and the product review table.

### Repository
MadeInLagos defines the Main Repository API which specifies the various operations for the app Main Repository. The main repository layer acts as a mediator between the network layer and the database layer. This layer is also responsible for providing data to the ViewModels.

### ViewModels
The app ViewModels hold the UI states for activities and other UI components. By using this pattern, the app can survive configuration changes, theme switches, and other system events that can lead to the destruction of an activity. 

### Concurrency
To make the most of the device processor and bestow on the app a buttery smooth experience, Kotlin coroutines and flow are used for asynchronous operations and other java functionalities like synchronization.

### Dependency Injection and Management
MadeInLagos uses Dagger-Hilt for automated dependency injection and dependency management across all the other layers of the app.

### UI
MadeInLagos UI is made up of multiple activities. As opposed to the Single Activity Multiple fragment patterns, using multiple activities for this project provided a higher level of stability(less system bug encounter), faster configuration, and better integration with the android material design container transformation library. 

### Test 
The test layer boasts of several instrumentation unit and JVM unit tests for various components of the app. 


# Movie Database
## 🎬 An Android application built using Kotlin, Jetpack Compose, MVVM architecture, LiveData, and ViewModel.

Movie Database is a fully functional Android app built entirely with Kotlin and Jetpack Compose. Using MVVM Architecture pattern with View model,Life data and dependency injection with Hilt. For seamless experience between online and offline state, cahcing data with Room local database and for load network image and caching with Glide. Using latest recommended best practices tech stack for Android development. 

# Tech stack & Open-source libraries
- Jetpack Compose: Modern toolkit for building native UI.
- MVVM Architecture: Separation of concerns and testability.
- LiveData: Observable data holder class.
- ViewModel: Manage UI-related data in a lifecycle-conscious way.
- Kotlin
- Room: For offine caching
- Glide: For Network image and cache
- Retrofit: For http request
- OkHttp3: implementing interceptor, logging
- Core Splash Api
- Paging3: pagination load data from network
- Youtube player: for load movie and tv series trailer
- Comopse Animation: Shared Transition Element
- Di: Hilt
- Pager: Carousel View
- Coroutines
- Kotlin Coroutines: Asynchronous computation

Architecture
This project follows the MVVM architecture pattern:
- Model: Represents the data layer. This includes data models and repository classes.
- View: Represents the UI layer. This includes Jetpack Compose UI components.
- ViewModel: Manages the UI-related data and handles the business logic.
- Libraries Used
- Jetpack Compose
- LiveData
- ViewModel
- Kotlin Coroutines

Code Structure
- model: Contains data models and repository classes.
- respository: Contains repository classes.
- dao: Contains data access object for room.
- view: Contains UI components built with Jetpack Compose.
- viewmodel: Contains ViewModel classes.
- di: App module
- db: Room database
- paging: Pagination data source.
- navigation controller: Compose navigation controller
- preview provider: Sample data provide for preview parameter in composable function

<img src="https://github.com/user-attachments/assets/607409ed-3adc-48f7-878c-1646d1492156" width="200" height="400">  <img src="https://github.com/user-attachments/assets/eb320319-60dc-47e0-9af3-d9f12be4db56" width="200" height="400">  <img src="https://github.com/user-attachments/assets/fb5f8016-4070-4d15-9eaf-9567b4988470" width="200" height="400">

<img src="https://github.com/user-attachments/assets/517c2c29-7532-4b05-abb3-c59991ab833e" width="200" height="400">  <img src="https://github.com/user-attachments/assets/e9031c78-d211-40c9-b15d-e6dcc98ca0ed" width="200" height="400">  <img src="https://github.com/user-attachments/assets/9f3aa07d-5b59-466b-beab-fde898ac6e27" width="200" height="400">

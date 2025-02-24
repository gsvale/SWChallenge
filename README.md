# The Cats API

This project will consist of three screens:

1. Cats List Screen
2. Favourites Screen
3. Cat Detail Screen

## How it works

When the user opens the app, the main screen is the Cats List Screen. Navigation is possible due to Jetpack Compose Navigation and one of the main components, besides the Main Navigation, is the bottom bar which allows the user
to switch between the Cats List screen and the Favourites screen.
Both these two screens contains a list of cat breeds, one regarding all of them and the other only with the user favourites saved in the database, which is implemented by Room.
The Cats list screen also allows the user to input in a search bar the query related to the name of the breed he wants to check.
Regarding the network, it was implemented a Retrofit Client/Service to fetch all the breeds or by name, and in both screens there is a laz grid which is responsible to show all the breeds items.
All UI is Compose built, and to show the images/photos of the breeds it was used Coil.
In each item, there is a star icon related to if the breeds is a favourite or not(Filled if it is, Outlined if not) and by clicking it the breed is updated in the database and the UI also afterwards.
If it is a favourite now, it will appear in the favourites screen, if it is deleted from the favourites, it will not happer anymore.
All screens have view models related to each one, initialized previously. The DI used in the project is Hilt.
The project follows the clean architecture patterns:

  UI -> Presentation(ViewModels) -> Domain(UseCases) -> Data(Room,ApiService) 
     <-                          <-                  <- 


The Cat Detail screen is open when a cat breed item is clicked, either in the Cats List or Favourites screen.
It will basically show more information regarding the breed andm just like in the others screens, allow the user to set or unset as favourite.

Offline funtionality is assured with the data saved in the room, so when there is not internet connection, the app will fetch the cat breeds from the database.
Error Handling is implemented regarding mostly network calls, in order to avoid app crashes and leaks, and maintain a good UI behavior.

Unit tests were also implemented using io.mockk, turbine and others.

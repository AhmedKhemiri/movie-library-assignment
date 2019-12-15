# MovieLibraryAssignment
Assignement to manage a movie library
In oder to execute this program you will need to  :

 -download or clone the project
 
 -import it in you IDE
 
 -run a "clean install of the project with Maven
 
 - Maven update project
 
 -run as a SpringBoot Application
 Use the URL : http://localhost:8080/ to access the index page and be able to add a movie
 
 Use the Url http://localhost:8080/movies/list to see the list of movies add be able to editand delete them
 
Use the Url  http://localhost:8080/movies/signup to add a new movie

Use the Url  http://localhost:8080/movies/edit/ to edit a movie


# MovieLibraryAssignment

<p>Welcome to Movie Library application</p>
    <p>This simple application will allow user to </p>
    <ul>
      <li>
        List All movies in grid
      </li>
      <li>
        Create new Movie (with several directors)
      </li>
      <li>
        Update an existing movie
      </li>
      <li>
        Delete an existing movie
      </li>
      <li>
        Sort list of movies by title, director, release date, type
      </li>
      <li>
        Search for a movie by title, director, release date, type
      </li>
      <li>
        Display the list of films of a Director on Hover (author page)
      </li>
    </ul>

## Build and run the backend microservice
### Build

Run `mvn clean install`

### Run
Run `mvn spring-boot:run`

The application will be running on `http://localhost:8080`

You can explore [swagger-ui](http://localhost:8080/swagger-ui.html#/movie-library-controller) for api documentation.


## Build and run the frontend Angular app

### Build
Run `npm install`

### Run
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.


## Further help
To get more help don't hesitate to contact me on [khemiri.ahmed@gmail.com](khemiri.ahmed@gmail.com) .

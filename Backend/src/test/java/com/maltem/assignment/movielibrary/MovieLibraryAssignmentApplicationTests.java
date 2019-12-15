package com.maltem.assignment.movielibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.maltem.assignment.movielibrary.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest(classes = MovieLibraryAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieLibraryAssignmentApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllMovies() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/movies",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetMovieById() {
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/1", Movie.class);
        System.out.println(movie.getTitle());
        assertNotNull(movie);
    }

    @Test
    public void testCreateMovie() {
        Movie movie = new Movie();
        movie.setTitle("test movie");
        movie.setDirector("test director");
        movie.setReleaseDate("12/12/2012");
        movie.setType("Comedy");

        ResponseEntity<Movie> postResponse = restTemplate.postForEntity(getRootUrl() + "/movies/", movie, Movie.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateMovie() {
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/1", Movie.class);
        movie.setTitle("Updated title");

        restTemplate.put(getRootUrl() + "/movies/", movie, Movie.class);

        Movie updatedMovie = restTemplate.getForObject(getRootUrl() + "/movies/1", Movie.class);
        assertEquals("Updated title", updatedMovie.getTitle());
    }

    @Test
    public void testDeleteEmployee() {
        int id = 5;
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        assertNotNull(movie);

        restTemplate.delete(getRootUrl() + "/movies/" + id);

        try {
            movie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}

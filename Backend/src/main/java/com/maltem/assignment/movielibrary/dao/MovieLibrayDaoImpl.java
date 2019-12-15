package com.maltem.assignment.movielibrary.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.maltem.assignment.movielibrary.model.Movie;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MovieLibrayDaoImpl implements MovieLibraryDao {

    private static final String CREATE_ERROR_MSG = "Movie cannot be created, please check your data";
    private static final String CREATE_ERROR_MSG_ALREADY_EXIST = "Movie cannot be created, movie title already exist";
    private static final String UPDATE_ERROR_MSG_NOT_EXIST = "Movie cannot be updated, movie title does not exist";
    private static final String UPDATE_ERROR_MSG = "Movie cannot be updated please check your data";
    private static final String DELETE_ERROR_MSG = "Movie cannot be deleted, the movie title does not exist";
    private static final String dateFormatPattern = "dd/MM/yyyy";
    private final static String JSON_INPUT = "./movies.json";

    private List<Movie> movies;
    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void loadJsonData() throws Exception {
        this.mapper.setDateFormat(new SimpleDateFormat(dateFormatPattern));
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.movies = this.mapper.readValue(
                new ClassPathResource(JSON_INPUT).getFile(),
                this.mapper.getTypeFactory().constructCollectionType(
                        List.class, Movie.class));
    }

    @Override
    public List<Movie> findAllMovies() {
        return this.movies;
    }

    @Override
    public Integer create(Movie movie) throws IOException {
        if (isValidMovie(movie)) {
            if (findByTitle(movie.getTitle()) == null) {
                movie.setId(generateMovieIndex());
                this.movies.add(movie);
                updateJsonDataFile(this.movies);
                return movie.getId();
            } else {
                throw new IllegalArgumentException(CREATE_ERROR_MSG_ALREADY_EXIST);
            }
        } else {
            throw new IllegalArgumentException(CREATE_ERROR_MSG);
        }
    }

    @Override
    public Movie findByTitle(String title) {
        Movie movie = findAllMovies().stream().
                filter(p -> p.getTitle().equalsIgnoreCase(title)).
                findFirst().orElse(null);
        return movie;
    }

    @Override
    public Movie findById(Integer id) {
        Movie movie = findAllMovies().stream().
                filter(p -> p.getId() == id).
                findFirst().orElse(null);
        return movie;
    }

    @Override
    public void update(Movie updatedMovie) throws IOException {
        if (isValidMovie(updatedMovie)) {
            Movie currentMovie = findById(updatedMovie.getId());
            if (currentMovie != null) {
                currentMovie.setTitle(updatedMovie.getTitle());
                currentMovie.setDirector(updatedMovie.getDirector());
                currentMovie.setReleaseDate(updatedMovie.getReleaseDate());
                currentMovie.setType(updatedMovie.getType());
                updateJsonDataFile(this.movies);
            } else {
                throw new NoSuchElementException(UPDATE_ERROR_MSG_NOT_EXIST);
            }
        } else {
            throw new IllegalArgumentException(UPDATE_ERROR_MSG);
        }
    }

    @Override
    public void delete(Integer id) throws IOException {
        if (findById(id) != null) {
            this.movies.removeIf(m -> m.getId() == id);
            updateJsonDataFile(this.movies);
        } else {
            throw new NoSuchElementException(DELETE_ERROR_MSG);
        }
    }

    /*public List<Movie> get(String key, String value)
            throws NoSuchFieldException, IllegalArgumentException {
        // verify field validity
        if (key != null && value != null) {
            Movie.class.getDeclaredField(key);
        } else {
            //throw new IllegalArgumentException(INVALID_SEARCH_KEY);
        }
        return this.movies.stream().filter(m -> m.get(key).contains(value))
                .collect(Collectors.toList());
    }*/

    private boolean isValidMovie(Movie movie) {
        return movie.getTitle() != null && movie.getDirector() != null
                && movie.getType() != null && movie.getReleaseDate() != null
                && isValidDate(movie.getReleaseDate());
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            dateFormat.parse(date);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

    public void updateJsonDataFile(List<Movie> movies) throws IOException {
        this.mapper.writeValue(new ClassPathResource(JSON_INPUT).getFile(), movies);
    }

    public Integer generateMovieIndex() {
        Optional<Integer> max = this.movies.stream().map(Movie::getId).max(Comparator.naturalOrder());
        return max.get() + 1;
    }
}

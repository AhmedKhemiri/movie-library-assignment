package com.maltem.assignment.movielibrary.controller;

import com.maltem.assignment.movielibrary.dao.MovieLibraryDao;
import com.maltem.assignment.movielibrary.exception.MovieNotFoundException;
import com.maltem.assignment.movielibrary.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/movies")
public class MovieLibraryController {

    @Autowired
    MovieLibraryDao movieLibraryDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Movie> getMovies() throws Exception {
        return movieLibraryDao.findAllMovies();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> get(@PathVariable("id") Integer id) {
        Movie movie = movieLibraryDao.findById(id);
        if(movie == null) throw new MovieNotFoundException("Movie not found for this id :: " + id);
        return ResponseEntity.ok().body(movie);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Movie> create(@RequestBody Movie movie) throws IOException{
        Integer id =  this.movieLibraryDao.create(movie);
        movie.setId(id);
        return ResponseEntity.ok().body(movie);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Movie movie) throws IOException {
        this.movieLibraryDao.update(movie);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws IOException {
        this.movieLibraryDao.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

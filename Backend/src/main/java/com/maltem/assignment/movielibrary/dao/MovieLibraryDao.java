package com.maltem.assignment.movielibrary.dao;

import com.maltem.assignment.movielibrary.model.Movie;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

public interface MovieLibraryDao{

    public List<Movie> findAllMovies();
    public Movie findById(Integer id);
    public Integer create(Movie movie) throws IOException;
    public Movie findByTitle(String title);
    public void update(Movie updatedMovie) throws IOException;
    public void delete(Integer id) throws IOException;
}

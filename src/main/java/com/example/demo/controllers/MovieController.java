package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Movie;
import com.example.demo.services.MovieService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	 @Autowired
	    private MovieService movieService;

	    // Get all movies
	    @GetMapping()
	    public ResponseEntity<List<Movie>> getAllMovies() {
	        List<Movie> movies = movieService.getAllMovies();
	        return new ResponseEntity<>(movies, HttpStatus.OK);
	    }

	    // Get movie by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
	        Optional<Movie> movie = movieService.getMovieById(id);
	        if (movie.isPresent()) {
	            return new ResponseEntity<>(movie.get(), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    // Add a new movie with image upload
	    @PostMapping
	    public ResponseEntity<Movie> addMovie(@RequestParam("title") String title,
	                                          @RequestParam("director") String director,
	                                          @RequestParam("year") int year,
	                                          @RequestParam("image") MultipartFile image) {
	        try {
	            String imagePath = movieService.saveImage(image);
	            Movie movie = new Movie(title, director, year);
	            movie.setImage(imagePath);
	            Movie newMovie = movieService.addMovie(movie);
	            return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    // Update an existing movie
	    @PutMapping("/{id}")
	    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
	        Optional<Movie> movie = movieService.getMovieById(id);
	        if (movie.isPresent()) {
	            Movie updatedMovie = movieService.updateMovie(id, movieDetails);
	            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    // Delete a movie by ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
	        Optional<Movie> movie = movieService.getMovieById(id);
	        if (movie.isPresent()) {
	            movieService.deleteMovie(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
}

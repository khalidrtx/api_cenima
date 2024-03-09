package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.MovieRepository;
import com.example.demo.entities.Movie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Value("${upload.path}")
    private String uploadPath; 
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            Movie existingMovie = movie.get();
            existingMovie.setTitle(movieDetails.getTitle());
            existingMovie.setDirector(movieDetails.getDirector());
            existingMovie.setYear(movieDetails.getYear());
            return movieRepository.save(existingMovie);
        } else {
            return null;
        }
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

  

    public String saveImage(MultipartFile image) throws IOException {
        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        // Create the directory if it doesn't exist
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the image file to the upload directory
        Path filePath = Paths.get(uploadPath, fileName);
        Files.write(filePath, image.getBytes());

        // Return the file path or URL
        return fileName;
    }

}
package com.example.demo;

import com.example.demo.dao.MovieRepository;
import com.example.demo.entities.Movie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiCenimaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiCenimaApplication.class, args);
	}



	@Bean
	public CommandLineRunner loadData(MovieRepository movieRepository) {
		return args -> {
			movieRepository.save(new Movie("Movie 1", "Director 1", "71hFmFbA9mL._AC_UF894,1000_QL80_.jpg" ,2000));
			movieRepository.save(new Movie("Movie 2", "Director 2","71hFmFbA9mL._AC_UF894,1000_QL80_.jpg", 2005));
			movieRepository.save(new Movie("Movie 3", "Director 3","71hFmFbA9mL._AC_UF894,1000_QL80_.jpg", 2010));
		};
	}
}

package dev.jae.movies.service;

import dev.jae.movies.models.Movie;
import dev.jae.movies.repo.MovieRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepo movieRepo;

    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> getAllMovies(){
        return movieRepo.findAll();
    }

    public Optional<Movie> findMovieByImdbId(String imdbId){
        return movieRepo.findMovieByImdbId(imdbId);
    }

}

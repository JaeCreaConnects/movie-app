package dev.jae.movies;

import dev.jae.movies.models.Movie;
import dev.jae.movies.repo.MovieRepo;
import dev.jae.movies.service.MovieService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepo movieRepo;

    @BeforeEach
    void setUp() {
        // Define the behavior of mocked methods here if needed
    }

    @Test
    void getAllMoviesTest() {
        // Arrange
        List<Movie> expectedMovies = List.of(
                new Movie(new ObjectId(), "tt123", "Movie 1"),
                new Movie(new ObjectId(), "tt456", "Movie 2")
        );

        when(movieRepo.findAll()).thenReturn(expectedMovies);

        // Act
        List<Movie> actualMovies = movieService.getAllMovies();

        // Assert
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void findMovieByImdbIdTest() {
        // Arrange
        String imdbId = "tt123";
        Movie expectedMovie = new Movie(new ObjectId(), imdbId, "Movie 1");

        when(movieRepo.findMovieByImdbId(imdbId)).thenReturn(Optional.of(expectedMovie));

        // Act
        Optional<Movie> actualMovie = movieService.findMovieByImdbId(imdbId);

        // Assert
        assertEquals(Optional.of(expectedMovie), actualMovie);
    }
}

package dev.jae.movies;

import dev.jae.movies.movies.controller.MovieController;
import dev.jae.movies.movies.controller.ReviewController;
import dev.jae.movies.service.MovieService;
import dev.jae.movies.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MoviesApplicationTests {

	@Autowired
	private MovieController movieController;

	@Autowired
	private ReviewController reviewController;

	@Autowired
	private MovieService movieService;

	@Autowired
	private ReviewService reviewService;

	@Test
	void contextLoads() {
		assertNotNull(movieController);
		assertNotNull(reviewController);
		assertNotNull(movieService);
		assertNotNull(reviewService);
	}

}

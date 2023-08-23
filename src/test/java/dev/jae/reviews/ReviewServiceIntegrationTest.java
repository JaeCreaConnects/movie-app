package dev.jae.reviews;

import dev.jae.movies.models.Movie;
import dev.jae.reviews.models.Review;
import dev.jae.reviews.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        // Create a movie document in the embedded MongoDB
        Movie movie = new Movie("tt123456", "Test Movie");
        mongoTemplate.save(movie);
    }

    @Test
    void createReviewTest() {
        // Arrange
        String reviewBody = "This is a test review";
        String imdbId = "tt123456";

        // Act
        Review createdReview = reviewService.createReview(reviewBody, imdbId);


        // Assert
        assertEquals(reviewBody, createdReview.getBody());

        // Verify that the movie document in MongoDB was updated
        Movie updatedMovie = mongoTemplate.findOne(
                new Query(Criteria.where("imdbId").is(imdbId)), Movie.class);

        assertNotNull(createdReview.getBody());
        assertNotNull(updatedMovie);
        assertTrue(updatedMovie.getReviewIds().contains(createdReview));

    }
}

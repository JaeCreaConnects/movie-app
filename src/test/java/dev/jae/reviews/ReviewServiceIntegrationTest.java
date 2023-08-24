package dev.jae.reviews;

import dev.jae.movies.models.Movie;
import dev.jae.reviews.models.Review;
import dev.jae.reviews.service.ReviewService;
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


    @Test
    void createReviewTest() {
        // Arrange
        String reviewBody = "This is a test review";
        String imdbId = "tt3915174";

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

        //delete the newly created test object (review) from the database
        //TODO: improve this logic.  We should instead send a request to a test db
        mongoTemplate.remove(new Query(Criteria.where("reviewId").is(createdReview.getReviewId())), Review.class);


    }
}

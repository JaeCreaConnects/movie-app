package dev.jae.reviews.service;

import dev.jae.movies.models.Movie;
import dev.jae.reviews.models.Review;
import dev.jae.reviews.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo, MongoTemplate mongoTemplate) {
        this.reviewRepo = reviewRepo;
        this.mongoTemplate = mongoTemplate;
    }

    public Review createReview(String reviewBody, String imdbId) {
        String newReviewId = UUID.randomUUID().toString();
        Review review = reviewRepo.insert(new Review(reviewBody, newReviewId));

        //use template to update the movie class
        //each movie in db contains reviewIds array (empty)
        //apply the update and then push a new update definition
        //get single movie and update it
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }
}

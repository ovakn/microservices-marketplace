package org.example.productService.mappers;

import org.example.productService.DTOs.ReviewRequest;
import org.example.productService.DTOs.ReviewResponse;
import org.example.productService.entities.Review;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTest {
    private final ReviewMapper reviewMapper = new ReviewMapper();

    @Test
    void toDTO() {
        Review review = new Review(1L, null, 10L, 4, "comment", LocalDateTime.MIN);
        ReviewResponse waitedResponse = new ReviewResponse(1L, 10L, 4, "comment", LocalDateTime.MIN);
        ReviewResponse response = reviewMapper.toDTO(review);
        assertEquals(waitedResponse, response);
    }

    @Test
    void toReview() {
        ReviewRequest reviewRequest = new ReviewRequest(10L, 4, "comment");
        Review waitedReview = new Review(null, null, 10L, 4, "comment", null);
        Review review = reviewMapper.toReview(reviewRequest);
        assertEquals(waitedReview.getId(), review.getId());
        assertEquals(waitedReview.getProduct(), review.getProduct());
        assertEquals(waitedReview.getUserId(), review.getUserId());
        assertEquals(waitedReview.getRating(), review.getRating());
        assertEquals(waitedReview.getComment(), review.getComment());
    }
}
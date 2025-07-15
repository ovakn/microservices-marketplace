package org.example.productService.mappers;

import org.example.productService.DTOs.ReviewRequest;
import org.example.productService.DTOs.ReviewResponse;
import org.example.productService.entities.Review;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ReviewMapper {
    public ReviewResponse toDTO(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUserId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }

    public Review toReview(ReviewRequest reviewRequest) {
        return new Review(
                null,
                null,
                reviewRequest.getUserId(),
                reviewRequest.getRating(),
                reviewRequest.getComment(),
                LocalDateTime.now()
        );
    }
}
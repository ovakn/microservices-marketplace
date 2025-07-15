package org.example.productService.repositories;

import org.example.productService.entities.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Slice<Review> findByProductId(Long productId, Pageable pageable);
    @Query("""
            SELECT AVG(r.rating)
            FROM Review as r
            WHERE r.product.id = :productId"""
    )
    Double calculateAverageRating(@Param("productId") Long productId);
}
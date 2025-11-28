package com.ctu.bookstore.repository.display;

import com.ctu.bookstore.entity.display.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.product.id = :productId")
    Double findAverageRatingByProductId(String productId);

    @Query("""
    SELECT r.product.id 
    FROM Rating r
    GROUP BY r.product.id
    HAVING AVG(r.stars) >= :minRating
    """)
    List<String> findProductIdsWithAvgStarsGreaterOrEqual(double minRating);
}

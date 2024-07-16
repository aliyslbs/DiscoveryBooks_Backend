package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("select new map(r.rating as rating, u.email as email) from Rating r join r.user u where r.book.bookId = :bookId")
    List<Map<String, Object>> getAllByBookId(@Param("bookId") int bookId);

    @Query("select r from Rating r where r.user.userId = :userId and r.book.bookId = :bookId")
    Optional<Rating> existsRating(@Param("userId") long userId, @Param("bookId") long bookId);
}

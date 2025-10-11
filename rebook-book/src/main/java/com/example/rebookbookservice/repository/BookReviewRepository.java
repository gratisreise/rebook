package com.example.rebookbookservice.repository;

import com.example.rebookbookservice.model.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    boolean existsByBookIdAndUserId(Long booId, String userId);

    Page<BookReview> findByBookId(Long bookId, Pageable pageable);

    @Query("SELECT COALESCE(AVG(br.score), 0) FROM BookReview br WHERE br.book.id = :bookId")
    Float findAverageScoreByBookId(Long bookId);
}

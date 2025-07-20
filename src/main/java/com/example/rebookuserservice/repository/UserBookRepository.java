package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.entity.UserBook;
import com.example.rebookuserservice.model.entity.compositekey.UserBookId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UserBookId> {
    Page<UserBook> findByUserBookIdUserId(String userId, Pageable pageable);

    @Query("SELECT ub.userBookId.userId FROM UserBook ub WHERE ub.userBookId.bookId = :bookId")
    List<String> findUserIdsByBookId(Long bookId);
}

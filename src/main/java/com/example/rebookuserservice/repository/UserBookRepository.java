package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.entity.UserBook;
import com.example.rebookuserservice.model.entity.compositekey.UserBookId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UserBookId> {
    Page<UserBook> findAllUserBookIdUserId(String userId, Pageable pageable);
}

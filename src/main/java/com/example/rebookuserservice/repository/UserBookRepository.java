package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.entity.UserBook;
import com.example.rebookuserservice.model.entity.compositekey.UserBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UserBookId> {

}

package com.example.rebookuserservice.service;

import com.example.rebookuserservice.model.entity.UserBook;
import com.example.rebookuserservice.repository.UserBookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBookService {
    private final UserBookRepository userBookRepository;
    public List<String> getUserIdsByBookId(Long bookId){
        return userBookRepository.findUserIdsByBookId(bookId);
    }
}

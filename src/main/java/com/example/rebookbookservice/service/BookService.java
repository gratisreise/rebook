package com.example.rebookbookservice.service;

import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.feigns.UserClient;
import com.example.rebookbookservice.model.BookRequest;
import com.example.rebookbookservice.model.BookResponse;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.example.rebookbookservice.repository.BookRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ApiService apiService;
    private final BookReader bookReader;
    private final UserClient userClient;

    public NaverBooksResponse searchNaverBooks(String keyword) {
        return apiService.searchBooks(keyword);
    }

    @Transactional
    public void postBook(BookRequest request) {
        String category = apiService.getCategory(request.getTitle());
        LocalDate publishedDate = LocalDate.parse(request.getPublishedDate(), DateTimeFormatter.BASIC_ISO_DATE);
        Book book = new Book(request, category, publishedDate);
        bookRepository.save(book);
    }


    public PageResponse<BookResponse> searchBooks(String keyword, Pageable pageable) {
        Page<Book> books = bookRepository.findByTitleContaining(keyword, pageable);
        Page<BookResponse> response = books.map(BookResponse::new);
        return new PageResponse<>(response);
    }

    public BookResponse getBook(Long bookId) {
        return new BookResponse(bookReader.readBookById(bookId));
    }

    public List<Long> getRecommendedBookIds(String userId) {
        List<String> categories = userClient.getFavoriteCategories(userId);
        log.info("getRecommendedBookIds categories: {}", categories.toString());
        List<Book> books = bookReader.readBookByCategoryIn(categories);
        return books.stream().map(Book::getId).toList();
    }
}

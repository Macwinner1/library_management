package com.library.backend.repository;

import com.library.backend.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Search by title (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search by author (case-insensitive)
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Search by title or author (case-insensitive) - Bonus feature
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> searchBooks(@Param("searchTerm") String searchTerm);

    // Pagination support - Bonus feature
    Page<Book> findAll(Pageable pageable);

    // Search with pagination
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Book> searchBooksWithPagination(@Param("searchTerm") String searchTerm, Pageable pageable);
}
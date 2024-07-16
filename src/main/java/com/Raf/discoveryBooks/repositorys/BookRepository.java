package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.Dto.BookDto;
import com.Raf.discoveryBooks.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByBookName(String bookName);
}

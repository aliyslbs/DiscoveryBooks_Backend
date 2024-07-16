package com.Raf.discoveryBooks.controllers;

import com.Raf.discoveryBooks.Dto.BookDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.Book;
import com.Raf.discoveryBooks.repositorys.BookRepository;
import com.Raf.discoveryBooks.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping("/getAll")
    public List<Book> getAll() {
        return this.bookService.getAll();
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<Book> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.bookService.getByName(name));
    }

    @GetMapping("/getRandomBooks")
    public ResponseEntity<List<Book>> getRandomFiveBooks() {
        return ResponseEntity.ok(this.bookService.getRandomBooks());
    }

    @GetMapping("/findById/{bookId}")
    public Book findById(@PathVariable Long bookId) {
        return this.bookService.findById(bookId);
    }


    @GetMapping("/getBookImage/{imageId}")
    public ResponseEntity<?> getBookImage(@PathVariable long imageId) throws IOException {
        byte[] imageData = this.bookService.getBookImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}

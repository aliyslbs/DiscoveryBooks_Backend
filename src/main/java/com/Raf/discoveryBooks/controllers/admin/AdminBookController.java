package com.Raf.discoveryBooks.controllers.admin;

import com.Raf.discoveryBooks.Dto.BookDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/book")
@RequiredArgsConstructor
public class AdminBookController {

    private final BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@ModelAttribute BookDto bookDto) {
        return ResponseEntity.ok(this.bookService.addBook(bookDto));
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Result> updateBook(@ModelAttribute BookDto bookDto) {
        return ResponseEntity.ok(this.bookService.updateBook(bookDto));
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<Result> deleteBook(@PathVariable long bookId) {
        return ResponseEntity.ok(this.bookService.deleteBook(bookId));
    }
}

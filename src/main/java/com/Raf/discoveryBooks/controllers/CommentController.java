package com.Raf.discoveryBooks.controllers;

import com.Raf.discoveryBooks.Dto.CommentDto;
import com.Raf.discoveryBooks.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(this.commentService.addComment(commentDto));
    }

    @GetMapping("/getAllByBookId/{bookId}")
    public ResponseEntity<?> getAllByBookId(@PathVariable int bookId){
        return ResponseEntity.ok(this.commentService.getAllByBookId(bookId));
    }
}

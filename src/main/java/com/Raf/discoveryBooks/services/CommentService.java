package com.Raf.discoveryBooks.services;

import com.Raf.discoveryBooks.Dto.CommentDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessDataResult;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.Book;
import com.Raf.discoveryBooks.models.Comment;
import com.Raf.discoveryBooks.models.User;
import com.Raf.discoveryBooks.repositorys.BookRepository;
import com.Raf.discoveryBooks.repositorys.CommentRepository;
import com.Raf.discoveryBooks.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private  final BookRepository bookRepository;

    public Result addComment(CommentDto commentDto){
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Book book = bookRepository.findById(commentDto.getBookId()).orElseThrow(() -> new RuntimeException("book not found"));
        var comment = Comment.builder()
                .user(user)
                .book(book)
                .content(commentDto.getContent())
                .build();
        this.commentRepository.save(comment);
        return new SuccessResult("comment added successfully");
    }

    public List<Map<String, Object>> getAllByBookId(int bookId){
        return this.commentRepository.getAllByBookId(bookId);
    }
}

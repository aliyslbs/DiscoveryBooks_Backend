package com.Raf.discoveryBooks.services;

import com.Raf.discoveryBooks.Dto.RatingDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.Book;
import com.Raf.discoveryBooks.models.Rating;
import com.Raf.discoveryBooks.models.User;
import com.Raf.discoveryBooks.repositorys.BookRepository;
import com.Raf.discoveryBooks.repositorys.RatingRepository;
import com.Raf.discoveryBooks.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    //add or update rating
    public Result giveRating(RatingDto ratingDto) {
        User user = this.userRepository.findById(ratingDto.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Book book = this.bookRepository.findById(ratingDto.getBookId()).orElseThrow(() -> new RuntimeException("book not found"));

        Optional<Rating> optionalRating = this.ratingRepository.existsRating(ratingDto.getUserId(), ratingDto.getBookId());

        if (optionalRating.isEmpty()) {
            Rating newRating = Rating.builder()
                    .rating(ratingDto.getRating())
                    .user(user)
                    .book(book)
                    .build();
            this.ratingRepository.save(newRating);
            return new SuccessResult("rating added successfully");
        } else {
            Rating existRating = optionalRating.get();
            existRating.setRating(ratingDto.getRating());
            this.ratingRepository.save(existRating);
            return new SuccessResult("rating updated successfully");
        }
    }

    //get ratings
    public List<Map<String, Object>> getAllByBookId(int bookId) {
        return this.ratingRepository.getAllByBookId(bookId);
    }
}

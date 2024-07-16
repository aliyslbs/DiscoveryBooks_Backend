package com.Raf.discoveryBooks.controllers;

import com.Raf.discoveryBooks.Dto.RatingDto;
import com.Raf.discoveryBooks.core.utilities.result.DataResult;
import com.Raf.discoveryBooks.core.utilities.result.SuccessDataResult;
import com.Raf.discoveryBooks.models.Rating;
import com.Raf.discoveryBooks.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/api/v1/rating")
@CrossOrigin
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/giveRating")
    public ResponseEntity<?> addRating(@RequestBody RatingDto rating){
        return ResponseEntity.ok(this.ratingService.giveRating(rating));
    }

    @GetMapping("/getAllByBookId/{bookId}")
    public ResponseEntity<?> getAllByBookId(@PathVariable int bookId){
        return ResponseEntity.ok(this.ratingService.getAllByBookId(bookId));
    }
}

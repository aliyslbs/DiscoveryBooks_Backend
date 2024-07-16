package com.Raf.discoveryBooks.controllers;

import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.User;
import com.Raf.discoveryBooks.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/findById/{userId}")
    public User findById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @GetMapping("/getByEmail/{email}")
    public Optional<User> getByEmail(@PathVariable String email) {
        return this.userService.findByEmail(email);
    }


    @GetMapping("/findUserWithToken")
    public ResponseEntity<?> findUserWithToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.findUserWithToken(token.substring(7)));
    }

    @GetMapping("/getUserImage/{imageId}")
    public ResponseEntity<?> getUserImage(@PathVariable long imageId) throws IOException {
        byte[] imageData = this.userService.getUserImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/addUserImage/{userId}")
    public ResponseEntity<?> addImage(@RequestParam("image") MultipartFile image, @PathVariable("userId") long userId) {
        return ResponseEntity.ok(this.userService.addUserImage(image, userId));
    }

}

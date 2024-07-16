package com.Raf.discoveryBooks.services;

import com.Raf.discoveryBooks.core.utilities.result.ErrorResult;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.FileEntity;
import com.Raf.discoveryBooks.models.User;
import com.Raf.discoveryBooks.repositorys.FileRespository;
import com.Raf.discoveryBooks.repositorys.UserRepository;
import com.Raf.discoveryBooks.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FileRespository fileRespository;


    public User findById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }

    public Optional<User> findByEmail(String userEmail) {
        return this.userRepository.getByEmail(userEmail);
    }

    public void deleteById(Long userId) {
        this.userRepository.deleteById(userId);
    }

    public Object findUserWithToken(String token) {
        String email = jwtService.extractUsername(token);
        return userRepository.getSomeInformation(email).orElseThrow();
    }

    //add image for user
    public Result addUserImage(MultipartFile image, long userId) {
        try {
            User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user did not found"));
            String filePath = "/images/users/" + image.getOriginalFilename();
            FileEntity fileEntity = FileEntity.builder()
                    .name(image.getOriginalFilename())
                    .type(image.getContentType())
                    .filePath(filePath)
                    .build();
            this.fileRespository.save(fileEntity);
            image.transferTo(new File(System.getProperty("user.dir") + filePath));
            user.setImage(fileEntity);
            this.userRepository.save(user);
            return new SuccessResult("user image added successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ErrorResult("user image not be saved for some reason");
        }
    }

    //get user image
    public byte[] getUserImage(long imageId) throws java.io.IOException {
        String imageUrl = this.fileRespository.findById(imageId).get().getFilePath();
        return Files.readAllBytes(new File(System.getProperty("user.dir") + imageUrl).toPath());
    }
}

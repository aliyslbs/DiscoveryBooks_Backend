package com.Raf.discoveryBooks.controllers;


import com.Raf.discoveryBooks.Dto.WriterDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessDataResult;
import com.Raf.discoveryBooks.models.Writer;
import com.Raf.discoveryBooks.services.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/writer")
@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Writer>> getAll(){
        return ResponseEntity.ok(this.writerService.getAll());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name){
        return ResponseEntity.ok(this.writerService.findByName(name));
    }

    @GetMapping("/getWriterImage/{imageId}")
    public ResponseEntity<?> getWriterImage(@PathVariable long imageId) throws IOException {
        byte[] imageData = this.writerService.getWriterImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}

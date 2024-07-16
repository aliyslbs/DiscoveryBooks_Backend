package com.Raf.discoveryBooks.controllers.admin;

import com.Raf.discoveryBooks.Dto.WriterDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.services.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/writer")
@RequiredArgsConstructor
public class AdminWriterController {

    private final WriterService writerService;

    @PostMapping("/addWriter")
    public ResponseEntity<?> addWriter(@ModelAttribute WriterDto writerDto) {
        return ResponseEntity.ok(this.writerService.addWriter(writerDto));
    }

    @PutMapping("/updateWriter")
    public ResponseEntity<Result> updateWriter(@ModelAttribute WriterDto writerDto) {
        return ResponseEntity.ok(this.writerService.updateWriter(writerDto));
    }

    @DeleteMapping("/deleteWriter/{writerId}")
    public ResponseEntity<Result> deleteWriter(@PathVariable long writerId) {
        return ResponseEntity.ok(this.writerService.deleteWriter(writerId));
    }
}

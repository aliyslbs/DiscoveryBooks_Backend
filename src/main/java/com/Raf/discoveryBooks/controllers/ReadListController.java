package com.Raf.discoveryBooks.controllers;

import com.Raf.discoveryBooks.Dto.ReadListDto;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.ReadList;
import com.Raf.discoveryBooks.services.ReadListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readList")
@RequiredArgsConstructor
public class ReadListController {

    private final ReadListService readListService;


    @PostMapping("/addReadList")
    public ResponseEntity<?> addReadList(@RequestBody ReadListDto readListDto){
        return ResponseEntity.ok(this.readListService.addToReadList(readListDto));
    }

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<?> getAllByUserID(@PathVariable long userId){
        return ResponseEntity.ok(this.readListService.getAllByUserId(userId));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return ResponseEntity.ok(this.readListService.deleteById(id));
    }
}

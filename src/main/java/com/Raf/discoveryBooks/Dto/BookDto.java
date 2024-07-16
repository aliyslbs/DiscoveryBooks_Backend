package com.Raf.discoveryBooks.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;
    private String bookName;
    private String writerName;
    private String genre;
    private MultipartFile imageFile;
}

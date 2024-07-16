package com.Raf.discoveryBooks.services;

import com.Raf.discoveryBooks.Dto.BookDto;
import com.Raf.discoveryBooks.core.utilities.result.ErrorResult;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.Book;
import com.Raf.discoveryBooks.models.FileEntity;
import com.Raf.discoveryBooks.models.Writer;
import com.Raf.discoveryBooks.repositorys.BookRepository;
import com.Raf.discoveryBooks.repositorys.FileRespository;
import com.Raf.discoveryBooks.repositorys.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/images/books";

    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final FileRespository fileRespository;

    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    public Book findById(Long bookId) {
        return this.bookRepository.findById(bookId).orElseThrow();
    }

    //add book
    public Result addBook(BookDto bookDto) {
        try {
            String filePath = "/images/books/" + bookDto.getImageFile().getOriginalFilename();
            FileEntity fileEntity = FileEntity.builder()
                    .name(bookDto.getImageFile().getOriginalFilename())
                    .type(bookDto.getImageFile().getContentType())
                    .filePath(filePath)
                    .build();
            this.fileRespository.save(fileEntity);
            bookDto.getImageFile().transferTo(new File(System.getProperty("user.dir") + filePath));

            Writer writer = writerRepository.findByName(bookDto.getWriterName()).orElseThrow(() -> new RuntimeException("writer could not found"));
            Book newBook = Book.builder()
                    .bookName(bookDto.getBookName())
                    .writer(writer)
                    .genre(bookDto.getGenre())
                    .image(fileEntity)
                    .build();
            this.bookRepository.save(newBook);
            return new SuccessResult("book added successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ErrorResult("book could not be saved for some reason");
        }
    }

    //update book
    public Result updateBook(BookDto bookDto) {
        try {
            Book book = this.bookRepository.findById(bookDto.getId()).orElseThrow(() -> new RuntimeException("book did not found"));
            Writer writer = this.writerRepository.findByName(bookDto.getWriterName()).orElseThrow(() -> new RuntimeException("writer did not found"));
            book.setBookName(bookDto.getBookName());
            book.setWriter(writer);
            book.setGenre(bookDto.getGenre());

            if (bookDto.getImageFile().isEmpty()) {
                if (book.getImage() != null) {
                    FileEntity fileEntity = this.fileRespository.findByName(book.getImage().getName()).orElseThrow(() -> new RuntimeException("image did not found"));
                    book.setImage(fileEntity);
                }
            } else {
                String filePath = "/images/books/" + bookDto.getImageFile().getOriginalFilename();
                FileEntity fileEntity = FileEntity.builder()
                        .name(bookDto.getImageFile().getOriginalFilename())
                        .type(bookDto.getImageFile().getContentType())
                        .filePath(filePath)
                        .build();
                this.fileRespository.save(fileEntity);
                bookDto.getImageFile().transferTo(new File(System.getProperty("user.dir") + filePath));
                book.setImage(fileEntity);
            }
            this.bookRepository.save(book);
            return new SuccessResult("book updated successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ErrorResult("book could not updated for some reoson");
        }
    }

    //delete book by id
    public Result deleteBook(long bookId) {
        try {
            this.bookRepository.deleteById(bookId);
            return new SuccessResult("book deleted successfully");
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return new ErrorResult("book could not updated for some reoson");
        }
    }

    //get random five book
    public List<Book> getRandomBooks() {
        List<Book> books = new ArrayList<>();
        Random random = new Random();
        long numberOfBooks = this.bookRepository.count();
        int count = (int) Math.min(numberOfBooks, 5);

        List<Book> allBooks = bookRepository.findAll();

        while (books.size() < count) {
            int randomIndex = random.nextInt((int) numberOfBooks);
            Book randomBook = allBooks.get(randomIndex);
            if (!books.contains(randomBook)) {
                books.add(randomBook);
            }
        }
        return books;
    }

    //get book by name
    public Book getByName(String name) {
        return this.bookRepository.findByBookName(name).orElseThrow(() -> new RuntimeException("book not found: " + name));
    }

    //get book images
    public byte[] getBookImage(long imageId) throws IOException {
        String imageUrl = this.fileRespository.findById(imageId).get().getFilePath();
        return Files.readAllBytes(new File(System.getProperty("user.dir") + imageUrl).toPath());
    }
}

package com.Raf.discoveryBooks.services;

import com.Raf.discoveryBooks.Dto.ReadListDto;
import com.Raf.discoveryBooks.core.utilities.result.ErrorResult;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.Book;
import com.Raf.discoveryBooks.models.ReadList;
import com.Raf.discoveryBooks.models.User;
import com.Raf.discoveryBooks.repositorys.ReadListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReadListService {

    private final ReadListRepository readListRepository;
    private final UserService userService;
    private final BookService bookService;


    public Result addToReadList(ReadListDto readListDto) {
        Optional<ReadList> existsReadList = this.readListRepository.existsReadList(readListDto.getUserId(), readListDto.getBookId());

        if(existsReadList.isEmpty()){
            User user = this.userService.findById(readListDto.getUserId());
            Book book = this.bookService.findById(readListDto.getBookId());
            ReadList newReadList = ReadList.builder()
                    .user(user)
                    .book(book)
                    .build();
            this.readListRepository.save(newReadList);
            return new SuccessResult("read list added successfully");
        }else{
            return new ErrorResult("read list already exists");
        }
    }

    public List<Map<String, Object>> getAllByUserId(long userId) {
        return this.readListRepository.getAllByUserId(userId);
    }

    public Result deleteById(long readListId) {
        this.readListRepository.deleteById(readListId);
        return new SuccessResult("removed successfully");
    }
}

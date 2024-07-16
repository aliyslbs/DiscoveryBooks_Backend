package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.models.ReadList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ReadListRepository extends JpaRepository<ReadList, Long> {
    @Query("select rl from ReadList rl where rl.user.userId = :userId and rl.book.bookId = :bookId")
    Optional<ReadList> existsReadList(@Param("userId") long userId, @Param("bookId") long bookId);

    @Query("select new map (b.bookName as bookName, b.genre as genre, b.writer.name as writer, b.bookId as bookId, rl.readListId as id) " +
            "from ReadList rl join rl.book b where rl.user.userId = :userId")
    List<Map<String, Object>> getAllByUserId(@Param("userId") long userId);
}

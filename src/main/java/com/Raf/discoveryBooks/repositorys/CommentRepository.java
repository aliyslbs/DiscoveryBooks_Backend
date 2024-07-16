package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new map(c.id as id, c.content as content, u.name as name, u.email as email, u.role as role) FROM Comment c JOIN c.user u WHERE c.book.bookId = :bookId")
    List<Map<String, Object>> getAllByBookId(int bookId);
}

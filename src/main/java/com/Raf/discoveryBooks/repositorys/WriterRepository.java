package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.models.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {

    Optional<Writer> findByName(String name);
}

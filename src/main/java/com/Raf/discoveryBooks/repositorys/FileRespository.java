package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRespository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByName(String name);
}

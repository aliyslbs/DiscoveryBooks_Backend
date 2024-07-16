package com.Raf.discoveryBooks.repositorys;

import com.Raf.discoveryBooks.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("select new map(u.email as email, u.name as name, u.role as role, u.image.id as imageId, u.userId as userId)  from User u where u.email = :email")
    Optional<Map<String, Object>> getSomeInformation(String email);

 }

package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query(value = "select name from user where email = :email", nativeQuery = true)
    String findNameByEmail(@Param("email")String email);
}

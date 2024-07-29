package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

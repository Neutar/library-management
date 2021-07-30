package com.hexad.librarymanagement.user.repository;

import com.hexad.librarymanagement.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
}

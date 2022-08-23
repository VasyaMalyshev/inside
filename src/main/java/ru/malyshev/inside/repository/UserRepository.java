package com.project.depoit.repository;

import com.project.depoit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findUsersByCurrentLesson_NumLesson(Long numLesson);
}
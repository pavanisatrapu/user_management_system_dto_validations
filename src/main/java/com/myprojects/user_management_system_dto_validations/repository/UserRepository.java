package com.myprojects.user_management_system_dto_validations.repository;

import com.myprojects.user_management_system_dto_validations.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);
}


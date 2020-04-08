package com.soni.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soni.usermanagement.model.UserLogin;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, Integer> {

    // user-defined methods
    Optional<UserLogin> findByUserName(String userName);
    public void deleteByUserName(String userName); 
}
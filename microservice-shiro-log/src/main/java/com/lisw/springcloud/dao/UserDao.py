package com.lisw.springcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lisw.springcloud.entity.User2;

public interface UserDao extends JpaRepository<User2, Integer> {

    User2 findByUsername(String username);

}

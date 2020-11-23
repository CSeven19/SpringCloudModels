package com.lsw.springcloud.service;

import com.lisw.springcloud.entity.User2;

public interface UserService {

    User2 findByUsername(String username);

}
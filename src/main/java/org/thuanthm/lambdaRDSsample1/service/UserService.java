package org.thuanthm.lambdaRDSsample1.service;

import org.springframework.stereotype.Service;
import org.thuanthm.lambdaRDSsample1.dto.User;

@Service
public interface UserService {

    User findUser(int id);

    void insertUser(User user);
}

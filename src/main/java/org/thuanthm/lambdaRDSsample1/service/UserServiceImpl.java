package org.thuanthm.lambdaRDSsample1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thuanthm.lambdaRDSsample1.dao.UserDAO;
import org.thuanthm.lambdaRDSsample1.dto.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User findUser(int id) {
        return userDAO.findUser(id);
    }

    @Override
    public void insertUser(User user) {
        userDAO.insertUser(user);
    }
}

package org.thuanthm.lambdaRDSsample1.dao;

import org.thuanthm.lambdaRDSsample1.dto.User;

import java.util.List;


public interface UserDAO {

    public List<User> findUser(String username);

    public List<User> findUser();
}

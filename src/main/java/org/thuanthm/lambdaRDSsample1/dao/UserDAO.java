package org.thuanthm.lambdaRDSsample1.dao;

import org.thuanthm.lambdaRDSsample1.dto.User;


public interface UserDAO {

//    public List<User> filterUser(String[]fields, int[] ids, String[] usernames, String[] emails);

    public User findUser(int id);

    public void insertUser(User user);

//    public void updateUser(User user);
//
//    public void deleteUser(int id);
}

package org.thuanthm.lambdaRDSsample1.dao;

import org.thuanthm.lambdaRDSsample1.dto.User;
import java.util.List;

public interface UserDAO {

    public List<User> getUsers();

    public void addUser(User user);

    public void deleteUser(long studentId);
}

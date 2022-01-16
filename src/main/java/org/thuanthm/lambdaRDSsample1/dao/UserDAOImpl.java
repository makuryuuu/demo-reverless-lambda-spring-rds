package org.thuanthm.lambdaRDSsample1.dao;

import com.amazon.rdsdata.client.MappingException;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thuanthm.lambdaRDSsample1.dto.User;
import org.thuanthm.lambdaRDSsample1.utils.RDSUtils;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    RDSUtils rdsUtils;

    @Override
    public List<User> findUser(String username) {
        try {
            return rdsUtils.executor("select * from users where username LIKE '%" + username + "%'").execute().mapToList(User.class);
        } catch (MappingException e) {
            return null;
        }
    }

    @Override
    public List<User> findUser() {
        try {
            return rdsUtils.executor("select * from users").execute().mapToList(User.class);
        } catch (MappingException e) {
            return null;
        }
    }
}

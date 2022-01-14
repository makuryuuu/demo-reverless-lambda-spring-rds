package org.thuanthm.lambdaRDSsample1.dao;

import com.amazon.rdsdata.client.MappingException;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thuanthm.lambdaRDSsample1.dto.User;
import org.thuanthm.lambdaRDSsample1.utils.RDSUtils;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    RDSUtils rdsUtils;

//    @Override
//    public List<User> filterUser(String[]fields, int[] ids, String[] usernames, String[] emails) {
//        StringBuilder queryBuilder = new StringBuilder("select " );
//        queryBuilder.append(ArrayUtils.isEmpty(fields) ? "*" : String.join(", ", fields));
//        queryBuilder.append(" from user"));
//        StringBuilder conditionBuilder = new StringBuilder();
//        if (ArrayUtils.isNotEmpty(ids)) {
//            conditionBuilder.append(" id IN (").append(Arrays.stream(ids).map(id->String.valueOf(id)).distinct().)
//        }
//        stringBuilder.append("")
//        return null;
//    }

    @Override
    public User findUser(int id) {
        try {
            return rdsUtils.executor("select * from users where id = " + id).execute().mapToSingle(User.class);
        } catch (MappingException e) {
            return null;
        }
    }

    @Override
    public void insertUser(User user) {
        String transactionId = rdsUtils.beginTransaction();
        ExecuteStatementRequest executeStatementRequest = rdsUtils.executeStatementRequest(
                "INSERT INTO users(username, email) VALUES ('" + user.getUsername() + "','" + user.getEmail() + "')",
                transactionId);
        rdsUtils.awsRDSDataService().executeStatement(executeStatementRequest);
        rdsUtils.commitTransaction(transactionId);
    }
//
//    @Override
//    public void updateUser(User user) {
//        String transactionId = rdsUtils.beginTransaction();
//        rdsUtils.commitTransaction(transactionId);
//    }
//
//    @Override
//    public void deleteUser(int id) {
//        String transactionId = rdsUtils.beginTransaction();
//        rdsUtils.commitTransaction(transactionId);
//    }
}

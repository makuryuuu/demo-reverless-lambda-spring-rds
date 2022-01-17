package org.thuanthm.lambdaRDSsample1.dao;

//region IMPORT
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.amazonaws.services.rdsdata.model.SqlParameter;
import com.amazonaws.services.rdsdata.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.thuanthm.lambdaRDSsample1.dto.User;
import org.thuanthm.lambdaRDSsample1.utils.RDSUtils;
import java.util.List;
import java.util.stream.Collectors;
//endregion

@Repository
public class UserDAOImpl implements UserDAO {

    //region INIT
    @Autowired
    @Lazy
    RDSUtils rdsUtils;
    //endregion

    //region READ
    @Override
    public List<User> getUsers() {
        ExecuteStatementRequest request = rdsUtils.executeStatementRequest("select * from users");
        ExecuteStatementResult result = rdsUtils.awsRDSDataService().executeStatement(request);
        return result.getRecords().stream()
                .map(record -> new User(
                        record.get(0).getLongValue(),
                        record.get(1).getStringValue(),
                        record.get(2).getStringValue()))
                .collect(Collectors.toList());
    }
    //endregion

    //region CREATE
    @Override
    public void addUser(User user) {
        String transactionId = rdsUtils.beginTransaction();
        ExecuteStatementRequest request = rdsUtils
                .executeStatementRequest("INSERT INTO users VALUES (:username, :email)", transactionId)
                .withParameters(
                        new SqlParameter().withName("username").withValue(new Field().withStringValue(user.getUsername())),
                        new SqlParameter().withName("email").withValue(new Field().withStringValue(user.getEmail()))
                );
        rdsUtils.awsRDSDataService().executeStatement(request);
        rdsUtils.commitTransaction(transactionId);
    }
    //endregion

    //region DELETE
    @Override
    public void deleteUser(long id) {
        ExecuteStatementRequest request = rdsUtils
                .executeStatementRequest("DELETE FROM users WHERE id = :id")
                .withParameters(
                        new SqlParameter().withName("id").withValue(new Field().withLongValue(id))
                );
        rdsUtils.awsRDSDataService().executeStatement(request);
    }
    //endregion
}

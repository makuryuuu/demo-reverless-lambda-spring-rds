package org.thuanthm.lambdaRDSsample1.dao;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.thuanthm.lambdaRDSsample1.dto.Account;
import org.thuanthm.lambdaRDSsample1.utils.RDSUtils;

@Component
public class AccountDAOImpl implements AccountDAO{
    @Override
    public void filterAccount(JsonObject conditionObj) {
        RDSUtils.fetchData(
                "select * from accounts where user_id = :user_id",
                conditionObj, Account.class);
    }

    @Override
    public void insertAccount(Account account) {
        RDSUtils.commitTransaction("INSERT INTO accounts(username, email) VALUES ('" + account.getUsername() + "','" + account.getEmail() + "')");
    }
}

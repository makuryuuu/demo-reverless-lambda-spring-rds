package org.thuanthm.lambdaRDSsample1.dao;

import com.google.gson.JsonObject;
import org.thuanthm.lambdaRDSsample1.dto.Account;


public interface AccountDAO {

    public void filterAccount(JsonObject conditionObj);

    public void insertAccount(Account account);
}

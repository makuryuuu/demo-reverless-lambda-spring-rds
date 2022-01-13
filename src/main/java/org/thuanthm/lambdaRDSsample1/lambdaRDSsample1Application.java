package org.thuanthm.lambdaRDSsample1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thuanthm.lambdaRDSsample1.dto.Account;
import org.thuanthm.lambdaRDSsample1.dao.AccountDAO;

@SpringBootApplication
public class lambdaRDSsample1Application {

	public static void main(String[] args) {
		SpringApplication.run(lambdaRDSsample1Application.class, args);
	}

	@Autowired
	AccountDAO accountDAO;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
//			accountDAO.insertAccount(new Account("makuryuuu2", "makuryuuu2@gmail.com"));
			JsonObject jsonObject = new JsonObject();
//			jsonObject.addProperty("username", "%aku%");
//			jsonObject.addProperty("email", "%ryu%");
			jsonObject.addProperty("user_id", 2);
			accountDAO.filterAccount(jsonObject);
		};
	}
}

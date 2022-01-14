package org.thuanthm.lambdaRDSsample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.thuanthm.lambdaRDSsample1.dao.UserDAO;

@SpringBootApplication
@ComponentScan(basePackages = {"org.thuanthm.*"})
public class lambdaRDSsample1Application {

	public static void main(String[] args) {
		SpringApplication.run(lambdaRDSsample1Application.class, args);
	}

//	@Bean
//	ApplicationRunner applicationRunner() {
//		return args -> {
//			accountDAO.insertAccount(new Account("makuryuuu2", "makuryuuu2@gmail.com"));
//			JsonObject jsonObject = new JsonObject();
//			jsonObject.addProperty("username", "%aku%");
//			jsonObject.addProperty("email", "%ryu%");
//			jsonObject.addProperty("user_id", 2);
//			accountDAO.filterAccount(jsonObject);
//		};
//	}
}

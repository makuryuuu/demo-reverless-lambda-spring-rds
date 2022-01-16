package org.thuanthm.lambdaRDSsample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.thuanthm.lambdaRDSsample1.dao.UserDAO;
import org.thuanthm.lambdaRDSsample1.dto.User;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
@ComponentScan(basePackages = {"org.thuanthm.*"})
public class lambdaRDSsample1Application implements ApplicationContextInitializer<GenericApplicationContext> {

	public static void main(String[] args) {
		FunctionalSpringApplication.run(lambdaRDSsample1Application.class, args);
	}

	@Autowired
	UserDAO userDAO;

	public Supplier<List<User>> users(){
		return ()->userDAO.findUser();
	}

	public Function<String, List<User>> findUser() {
		return (input) -> userDAO.findUser(input);
	}

	@Override
	public void initialize(GenericApplicationContext applicationContext) {
		applicationContext.registerBean("findUser", FunctionRegistration.class,
				() -> new FunctionRegistration<>(findUser())
						.type(FunctionType.from(String.class).to(List.class).getType()));
		applicationContext.registerBean("users", FunctionRegistration.class,
				() -> new FunctionRegistration<>(findUser())
						.type(FunctionType.from(String.class).to(User.class).getType()));
	}
}

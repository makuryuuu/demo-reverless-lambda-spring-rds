package org.thuanthm.lambdaRDSsample1.configuration;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thuanthm.lambdaRDSsample1.dao.UserDAO;
import org.thuanthm.lambdaRDSsample1.dto.User;
import java.util.List;
import java.util.function.Function;

@Configuration
public class ProxyServiceConfig {

    @Autowired
    UserDAO userDAO;

    @Bean
    public Function<APIGatewayProxyRequestEvent, List<User>> getUsersProxy() {
        return (proxyRequestEvent) -> userDAO.getUsers();
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, List<User>> addUserProxy() {
        return (mapValues) -> {
            JSONObject body = new JSONObject(mapValues.getBody());
            User user = new User(
                    String.valueOf(body.get("username")),
                    String.valueOf(body.get("email")));
            userDAO.addUser(user);
            return userDAO.getUsers();
        };
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, List<User>> deleteUserProxy() {
        return (mapValues) -> {

            long id = Long.valueOf(mapValues.getPathParameters().getOrDefault("id", "-1"));
            userDAO.deleteUser(id);
            return userDAO.getUsers();
        };
    }
}

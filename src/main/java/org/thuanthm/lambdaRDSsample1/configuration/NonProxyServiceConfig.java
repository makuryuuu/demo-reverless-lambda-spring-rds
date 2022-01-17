package org.thuanthm.lambdaRDSsample1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thuanthm.lambdaRDSsample1.dao.UserDAO;
import org.thuanthm.lambdaRDSsample1.dto.User;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class NonProxyServiceConfig {

    @Autowired
    private UserDAO userDAO;

    @Bean
    public Function<Map<Object, Object>, List<User>> getUsersNonProxy() {
        return (proxyRequestEvent) -> userDAO.getUsers();
    }

    @Bean
    public Function<Map<Object, Object>, List<User>> addUserNonProxy() {

        return (mapValues) -> {
            User user = new User(
                    String.valueOf(mapValues.get("username")),
                    String.valueOf(mapValues.get("email")));
            userDAO.addUser(user);
            return userDAO.getUsers();
        };
    }

    @Bean
    public Function<Map<Object, Object>, List<User>> deleteUserNonProxy() {
        return (mapValues) -> {
            long id = Long.valueOf(String.valueOf(mapValues.getOrDefault("id", "-1")));
            userDAO.deleteUser(id);
            return userDAO.getUsers();
        };
    }
}

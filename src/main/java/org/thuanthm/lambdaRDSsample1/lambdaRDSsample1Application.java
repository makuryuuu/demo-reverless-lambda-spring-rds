package org.thuanthm.lambdaRDSsample1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.thuanthm.*"})
public class lambdaRDSsample1Application {

	public static void main(String[] args) {
		SpringApplication.run(lambdaRDSsample1Application.class, args);
	}

}

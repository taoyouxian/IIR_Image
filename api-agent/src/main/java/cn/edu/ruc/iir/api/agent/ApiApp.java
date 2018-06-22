package cn.edu.ruc.iir.api.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableScheduling
public class ApiApp {

    public static void main(String[] args) {
        SpringApplication.run(ApiApp.class, args);
    }

}


package sec.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        SQLMiddleware.setupSQLMiddleware();

        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }

}

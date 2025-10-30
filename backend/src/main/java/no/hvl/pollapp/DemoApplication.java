package no.hvl.pollapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "no.hvl.pollapp")
@EntityScan(basePackages = "no.hvl.pollapp.domain")
@EnableJpaRepositories(basePackages = "no.hvl.pollapp.repository")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

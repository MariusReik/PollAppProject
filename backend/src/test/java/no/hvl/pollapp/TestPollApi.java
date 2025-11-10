package no.hvl.pollapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TestPollApi implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestPollApi.class, args);
    }

    @Override
    public void run(String... args) {
        RestTemplate rest = new RestTemplate();
        String url = "http://localhost:8080/api/polls";

        try {
            String response = rest.getForObject(url, String.class);
            System.out.println("API Response: " + response);
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

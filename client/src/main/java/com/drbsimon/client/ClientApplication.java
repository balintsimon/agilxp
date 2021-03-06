package com.drbsimon.client;

import com.drbsimon.client.tester.TesterRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class ClientApplication implements CommandLineRunner {
    @Autowired
    private TesterRunner testerRunner;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        log.info("EXECUTING test application");
        testerRunner.run();
    }
}

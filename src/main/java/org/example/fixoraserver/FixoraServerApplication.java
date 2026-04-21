package org.example.fixoraserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FixoraServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixoraServerApplication.class, args);
    }

}

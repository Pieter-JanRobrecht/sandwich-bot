package be.pj.sandwichbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "be.pj.sandwichbot"})
public class SandwichApplication {
    public static void main(String[] args) {
        SpringApplication.run(SandwichApplication.class, args);
    }
}

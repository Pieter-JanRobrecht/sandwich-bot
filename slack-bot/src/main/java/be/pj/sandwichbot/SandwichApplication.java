package be.pj.sandwichbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EntityScan("be.pj.sandwichbot.model")
@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "be.pj.sandwichbot"})
public class SandwichApplication {
  public static void main(String[] args) {
    SpringApplication.run(SandwichApplication.class, args);
  }
}

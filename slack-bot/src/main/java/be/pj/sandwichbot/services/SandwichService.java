package be.pj.sandwichbot.services;

import be.pj.sandwichbot.model.Sandwich;
import be.pj.sandwichbot.repositories.SandwichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SandwichService {

  @Autowired
  private SandwichRepository sandwichRepository;

  public String findAllSandwiches() {
    Iterable<Sandwich> sandwiches = sandwichRepository.findAll();
    StringBuilder message = new StringBuilder("");

    for (Sandwich sandwich : sandwiches) {
      message.append(sandwich.toPrettyFormat());
      message.append("\n");
    }
    return message.toString();
  }
}

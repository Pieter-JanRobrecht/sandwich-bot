package be.pj.sandwichbot.repositories;

import be.pj.sandwichbot.model.SlackUser;
import org.springframework.data.repository.CrudRepository;

public interface SlackUserRepository extends CrudRepository<SlackUser, String> {
}


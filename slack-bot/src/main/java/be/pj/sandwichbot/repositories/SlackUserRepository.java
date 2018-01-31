package be.pj.sandwichbot.repositories;

import be.pj.sandwichbot.model.SlackUserModel;
import org.springframework.data.repository.CrudRepository;

public interface SlackUserRepository extends CrudRepository<SlackUserModel, Long> {
}


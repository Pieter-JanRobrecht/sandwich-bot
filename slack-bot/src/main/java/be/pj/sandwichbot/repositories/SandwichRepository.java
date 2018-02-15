package be.pj.sandwichbot.repositories;

import be.pj.sandwichbot.model.Sandwich;
import org.springframework.data.repository.CrudRepository;

public interface SandwichRepository extends CrudRepository<Sandwich, Long> {
}

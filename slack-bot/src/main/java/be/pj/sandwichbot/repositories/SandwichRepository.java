package be.pj.sandwichbot.repositories;

import be.pj.sandwichbot.model.SandwichModel;
import org.springframework.data.repository.CrudRepository;

public interface SandwichRepository extends CrudRepository<SandwichModel, Long> {
}

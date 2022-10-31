package mongoDB.repository;

import mongoDB.entity.Sight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SightRepository extends MongoRepository<Sight, String> {

    Optional<List<Sight>> findByZoneLike(String zone);
}

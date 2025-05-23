package eci.cvds.demo.repository;

import eci.cvds.demo.model.Specialty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends MongoRepository<Specialty, String> {
}

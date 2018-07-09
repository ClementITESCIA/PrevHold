package guru.springframework.repositories;

import guru.springframework.domain.Capteur;
import org.springframework.data.repository.CrudRepository;

public interface CapteurRepository extends CrudRepository<Capteur, Integer> {
    public Capteur findByCapteurId(Integer id);
}

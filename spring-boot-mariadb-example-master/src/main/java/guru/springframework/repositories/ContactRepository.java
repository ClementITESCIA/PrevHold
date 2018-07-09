package guru.springframework.repositories;

import guru.springframework.domain.Contact;
import org.springframework.data.repository.CrudRepository;

//Id de type Long
public interface ContactRepository extends CrudRepository<Contact, Long> {
    // Requete pour faire base de données. Récupérer Objet Contact à l'intérieur de la base de nom passé en paramètre
    public Contact findByNom(String nom);
}

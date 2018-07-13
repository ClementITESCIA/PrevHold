package guru.springframework.repositories;

import guru.springframework.domain.Mesure;
import guru.springframework.dto.JournaliereMesure;
import org.springframework.data.repository.CrudRepository;

public interface MesureRepository extends CrudRepository<Mesure, Integer> {
    /*
    select DAYNAME(date) as jour, count(mesure.id) as occurence from mesure
 inner join capteur on capteur.id = mesure.capteur_ref
 where capteur.capteur_id = 1 and valeur >= 75 and WEEK(date) = WEEK(NOW())
 group by DAYNAME(date)
 order by WEEKDAY(date)
     */

    //public JournaliereMesure getByCapteur(int capteurId, int seuil);
}

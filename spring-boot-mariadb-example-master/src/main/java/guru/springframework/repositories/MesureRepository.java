package guru.springframework.repositories;

import java.util.List;

import guru.springframework.domain.Mesure;
import guru.springframework.dto.JournaliereMesure;
import guru.springframework.dto.MensuelleMesure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Gestionnaire de l'entité (guru.springframework.dto.Mesure) de la table des mesures des capteurs
 * Operations de base : Create, Read, Update and Delete
 *
 */
public interface MesureRepository extends CrudRepository<Mesure, Integer> {

    /**
     * Service donnant les mesures agrégées par jour sur une semaine.
     * Les jours de la semaine sont calculées à partir de la date du jour.
     *
     * @param capteurId identifiant du capteur
     * @param seuil valeur minimale de la mesure
     * @return Renvoie toutes les mesures de la même semaine que la date du jour (du lundi au dimanche)
     */
    @Query(name="JournaliereMesure.countPerDay")
    List<JournaliereMesure> getByCapteurByJour(String capteurId, int seuil);

    /**
     * Service donnant les mesures agrégées par jour sur une année.
     * Les mois sont calculées à partir de la date du jour.
     *
     * @param capteurId identifiant du capteur
     * @param seuil valeur minimale de la mesure
     * @return Renvoie toutes les mesures du même mois que la date du jour (du janvier à décembre)
     */
    @Query(name="MensuelleMesure.countPerMonth")
    List<MensuelleMesure> getByCapteurByMois(String capteurId, int seuil);
}

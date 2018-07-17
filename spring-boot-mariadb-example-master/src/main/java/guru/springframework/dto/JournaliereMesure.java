package guru.springframework.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 * Entité représentant les mesures des capteurs agrégées par jour
 *
 */
@Entity
@SqlResultSetMapping(name = "mesureJournaliereMapping", classes = {
        @ConstructorResult(targetClass = JournaliereMesure.class, columns = {
                @ColumnResult(name = "jour", type = String.class),
                @ColumnResult(name = "occurence ", type = Integer.class) }) })
@NamedNativeQuery(name = "JournaliereMesure.countPerDay", query = "SELECT DAYNAME(date) as jour, count(mesure.id) as occurence from mesure\r\n"
        + " inner join capteur on capteur.id = mesure.capteur_ref\r\n"
        + " where capteur.capteur_id = ?1 and valeur >= ?2 and WEEK(date) = WEEK(NOW())\r\n"
        + " group by DAYNAME(date)\r\n" + " order by WEEKDAY(date)", resultSetMapping = "mesureJournaliereMapping")
public class JournaliereMesure {
    @Id
    private long id;
    private Integer occurence;
    private String jour;

    /**
     * Methode pour tester le service en mode bouchon (sans accès à la base)
     *
     * @param capteurId
     * @return
     */
    public static List<JournaliereMesure> demo(String capteurId) {
        List<JournaliereMesure> mesures = new ArrayList<JournaliereMesure>(7);

        JournaliereMesure lundi = new JournaliereMesure(Jour.Monday.name(), 2);
        JournaliereMesure mardi = new JournaliereMesure(Jour.Tuesday.name(), 1);
        JournaliereMesure mercredi = new JournaliereMesure(Jour.Wednesday.name(), 0);
        JournaliereMesure jeudi = new JournaliereMesure(Jour.Thursday.name(), 2);
        JournaliereMesure vendredi = new JournaliereMesure(Jour.Friday.name(), 0);
        JournaliereMesure samedi = new JournaliereMesure(Jour.Saturday.name(), 1);
        JournaliereMesure dimanche = new JournaliereMesure(Jour.Sunday.name(), 3);

        mesures.add(lundi);
        mesures.add(mardi);
        mesures.add(mercredi);
        mesures.add(jeudi);
        mesures.add(vendredi);
        mesures.add(samedi);
        mesures.add(dimanche);

        return mesures;
    }

    /**
     * Méthode pour initialiser une semaine de mesure dont la valeur est vide
     *
     * @return
     */
    public static Map<Jour, JournaliereMesure> getSemaineMesureZero() {
        Map<Jour, JournaliereMesure> mesures = new LinkedHashMap<Jour, JournaliereMesure>(7);
        for (Jour jour : Jour.values()) {
            JournaliereMesure mesureVide = new JournaliereMesure(jour.name(), 0);
            mesures.put(jour, mesureVide);
        }
        return mesures;
    }

    /**
     * Constructeur par défaut
     *
     * @param jour
     *            Numéro du jour dans la semaine
     * @param occurence
     *            Nombre de mesures pour le jour de la semaine
     */
    public JournaliereMesure(String jour, Integer occurence) {
        this.jour = jour;
        this.occurence = occurence;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Mesure Journaliere [jour=%s, occurence=%s]", jour, occurence);
    }

}

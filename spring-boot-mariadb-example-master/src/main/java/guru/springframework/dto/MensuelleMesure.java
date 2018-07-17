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
 * Entité représentant les mesures des capteurs agrégées par mois
 *
 */
@Entity
@SqlResultSetMapping(name = "mesureMensuelleMapping", classes = {
        @ConstructorResult(targetClass = MensuelleMesure.class, columns = {
                @ColumnResult(name = "mois", type = String.class),
                @ColumnResult(name = "occurence ", type = Integer.class) }) })
@NamedNativeQuery(name = "MensuelleMesure.countPerMonth", query = "SELECT MONTHNAME(date) as mois, count(mesure.id) as occurence from mesure\r\n"
        + " inner join capteur on capteur.id = mesure.capteur_ref\r\n"
        + " where capteur.capteur_id = ?1 and valeur >= ?2 and MONTH(date) = MONTH(NOW())\r\n"
        + " group by MONTHNAME(date)\r\n" + " order by MONTH(date)", resultSetMapping = "mesureMensuelleMapping")
public class MensuelleMesure {
    @Id
    private long id;
    private int occurence;
    private String mois;

    /**
     * Méthode pour initialiser une année de mesure dont la valeur est vide
     *
     * @return
     */
    public static Map<Mois, MensuelleMesure> getAnneeMesureZero() {
        Map<Mois, MensuelleMesure> mesures = new LinkedHashMap<Mois, MensuelleMesure>(7);
        for (Mois mois : Mois.values()) {
            MensuelleMesure mesureVide = new MensuelleMesure(mois.name(), 0);
            mesures.put(mois, mesureVide);
        }
        return mesures;
    }

    /**
     * Methode pour tester le service en mode bouchon (sans accès à la base)
     *
     * @param capteurId
     * @return
     */
    public static List<MensuelleMesure> demo(String capteurId) {
        List<MensuelleMesure> mesures = new ArrayList<MensuelleMesure>(12);

        MensuelleMesure janvier = new MensuelleMesure(Mois.January.name(), 2);
        MensuelleMesure fevrier = new MensuelleMesure(Mois.February.name(), 1);
        MensuelleMesure mars = new MensuelleMesure(Mois.March.name(), 0);
        MensuelleMesure avril = new MensuelleMesure(Mois.April.name(), 2);
        MensuelleMesure mai = new MensuelleMesure(Mois.May.name(), 0);
        MensuelleMesure juin = new MensuelleMesure(Mois.June.name(), 1);
        MensuelleMesure juillet = new MensuelleMesure(Mois.July.name(), 3);
        MensuelleMesure aout = new MensuelleMesure(Mois.August.name(), 3);
        MensuelleMesure septembre = new MensuelleMesure(Mois.September.name(), 3);
        MensuelleMesure octobre = new MensuelleMesure(Mois.October.name(), 0);
        MensuelleMesure novembre = new MensuelleMesure(Mois.November.name(), 8);
        MensuelleMesure decembre = new MensuelleMesure(Mois.December.name(), 12);

        mesures.add(janvier);
        mesures.add(fevrier);
        mesures.add(mars);
        mesures.add(avril);
        mesures.add(mai);
        mesures.add(juin);
        mesures.add(juillet);
        mesures.add(aout);
        mesures.add(septembre);
        mesures.add(octobre);
        mesures.add(novembre);
        mesures.add(decembre);

        return mesures;
    }

    /**
     * Constructeur par défaut
     *
     * @param jour
     *            Numéro du mois dans l'année
     * @param occurence
     *            Nombre de mesures pour le mois dans l'année
     */
    public MensuelleMesure(String mois, Integer occurence) {
        this.mois = mois;
        this.occurence = occurence;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Mesure Mensuelle [mois=%s, occurence=%s]", mois, occurence);
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }
}

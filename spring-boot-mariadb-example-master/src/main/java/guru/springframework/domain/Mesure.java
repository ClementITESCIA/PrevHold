package guru.springframework.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Mesure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Override
    public String toString() {
        return "Mesure{" +
                "capteurId=" + id +
                ", valeur=" + valeur +
                ", unite='" + unite + '\'' +
                ", date=" + date +
                ", capteur=" + capteur +
                '}';
    }

    @Column
    @NotNull
    private double valeur;

    @Column
    private String unite;

    //Objet qui réprésente date-heure "yyyy-MM-dd HH-mm-ss"
    @Column
    @NotNull
    private LocalDateTime date;

    // Plusieures mesures pour 1 capteur -> fusion entre plusieures mesures et un type de capteur
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "capteur_ref", nullable = false)
    private Capteur capteur;

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValeur() {
        return valeur;
    }

    public String getUnite() {
        return unite;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }
}

package guru.springframework.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Capteur {

    //Clé primaire générée de façon automatique lors de l'insertion dans la base
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Création de colonne portant le nom non nulle
    @Column
    @NotNull
    private int arduinoId;

    @Column
    @NotNull
    private String arduinoNom;

    @Column
    @NotNull
    private int raspberryId;

    @Column
    @NotNull
    private int capteurId;

    @Column
    private String description;

    //Définir la méthode héritée de la classe parente
    @Override
    public String toString() {
        return "Capteur{" +
                "arduinoNom='" + arduinoNom + '\'' +
                ", raspberryId=" + raspberryId +
                ", capteurId=" + capteurId +
                ", description='" + description + '\'' +
                ", arduinoId=" + arduinoId +
                '}';
    }

    //Relation 1...n -> 1 capteur qui fait plusieures mesures
    @OneToMany(mappedBy = "capteur")
    private List<Mesure> mesures = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setArduinoId(int arduinoId) {
        this.arduinoId = arduinoId;
    }

    public void setArduinoNom(String arduinoNom) {
        this.arduinoNom = arduinoNom;
    }

    public void setRaspberryId(int raspberryId) {
        this.raspberryId = raspberryId;
    }

    public void setCapteurId(int capteurId) {
        this.capteurId = capteurId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArduinoId() {
        return arduinoId;
    }

    public String getArduinoNom() {
        return arduinoNom;
    }

    public int getRaspberryId() {
        return raspberryId;
    }

    public int getCapteurId() {
        return capteurId;
    }

    public String getDescription() {
        return description;
    }
}


package guru.springframework.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Objet représentant les paramètres d'une mesure d'un capteur réalisée par l'Arduino.
 *
 */
public class ArduinoMesure {
    // La date de la mesure est au format "yyyy-MM-dd HH:mm:ss.xxxxxx" où x représente le nombre de microsecondes
    private static final DateTimeFormatter arduinoFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Identifiant de l'arduino sur lequel est faite la mesure
    private int arduinoId;
    // Nom de l'arduino
    private String arduinoNom;
    // Identifiant du capteur attaché à l'arduino
    private int capteurId;
    // valeur de la mesure du capteur
    private double valeur;
    // Date de la mesure
    private LocalDateTime date;

    public int getArduinoId() {
        return arduinoId;
    }

    public void setArduinoId(int arduinoId) {
        this.arduinoId = arduinoId;
    }

    public int getCapteurId() {
        return capteurId;
    }

    public void setCapteurId(int capteurId) {
        this.capteurId = capteurId;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public String getArduinoNom() {
        return arduinoNom;
    }

    public void setArduinoNom(String arduinoNom) {
        this.arduinoNom = arduinoNom;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setRawDate(String dateArduino) {
        // Supprime les microsecondes de la date avant de la parser
        String date = dateArduino.substring(0, dateArduino.indexOf("."));

        this.date = LocalDateTime.parse(date, arduinoFmt);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Mesure Arduino [date=%s, arduinoId=%s, arduinoNom=%s, capteurId=%s, valeur=%s]", date, arduinoId,
                arduinoNom, capteurId, valeur);
    }

}

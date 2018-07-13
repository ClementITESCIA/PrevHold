package guru.springframework.dto;

/**
 * Objet représentant les paramètres d'une mesure d'un capteur réalisée par l'Arduino.
 *
 */
public class ArduinoMesure {

    // Identifiant de l'arduino sur lequel est faite la mesure
    private int arduinoId;
    // Nom de l'arduino
    private String arduinoNom;
    // Identifiant du capteur attaché à l'arduino
    private int capteurId;
    // valeur de la mesure du capteur
    private double valeur;

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

    @Override
    public String toString() {
        return "ArduinoMesure{" +
                "arduinoId=" + arduinoId +
                ", arduinoNom=" + arduinoNom +
                ", capteurId=" + capteurId +
                ", valeur=" + valeur + '}';}
}

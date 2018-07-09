package guru.springframework.dto;

public class ArduinoMesure {

    private int arduinoId;
    private String arduinoNom;
    private int capteurId;
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
                ", valeur=" + valeur +
                '}';
    }
}

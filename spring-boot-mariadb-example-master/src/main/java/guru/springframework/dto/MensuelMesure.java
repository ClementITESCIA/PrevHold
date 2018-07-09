package guru.springframework.dto;

import java.time.LocalDateTime;

public class MensuelMesure {

    private int occurence;
    private String mensuel;
    private LocalDateTime date;

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public String getMensuel() {
        return mensuel;
    }

    public void setMensuel(String mensuel) {
        this.mensuel = mensuel;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

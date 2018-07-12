package guru.springframework.repositories;

import guru.springframework.domain.Capteur;
import guru.springframework.domain.Mesure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImportMesureTest {

    private static final int RASPBERRY_ID = 1234;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @Autowired
    private CapteurRepository capteurRepo;

    @Autowired
    private MesureRepository mesureRepo;

    @Test
    void testImport() {
        int capteurId = 2;
        int arduinoId = 1234;
        String arduinoNom = "test";
        double valeur = 5.0;
        String date = "15/02/2018 15:52:07";

        Capteur capteur = capteurRepo.findByCapteurId(capteurId);
        if (capteur == null) {
            capteur = new Capteur();
            capteur.setArduinoId(arduinoId);
            capteur.setArduinoNom(arduinoNom);
            capteur.setCapteurId(capteurId);
            capteur.setRaspberryId(RASPBERRY_ID);
            capteurRepo.save(capteur);
        }

        Mesure mesure = new Mesure();
        mesure.setDate(LocalDateTime.parse(date, formatter));
        mesure.setValeur(valeur);

        mesure.setCapteur(capteur);

        mesureRepo.save(mesure);
    }
}

package guru.springframework.controllers;

import guru.springframework.domain.Capteur;
import guru.springframework.domain.Mesure;
import guru.springframework.dto.ArduinoMesure;
import guru.springframework.dto.JournaliereMesure;
import guru.springframework.repositories.CapteurRepository;
import guru.springframework.repositories.MesureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = {"http://localhost:4200"},
        maxAge = 4800, allowCredentials = "false")
@Controller
public class MesureController {

    private static final int RASPBERRY_ID = 1234;


    @Autowired
    private CapteurRepository capteurRepo;

    @Autowired
    private MesureRepository mesureRepo;

    @PostMapping(value = "/mesure")
    public ResponseEntity enregistrerMesure(@ModelAttribute ArduinoMesure arduinoMesure) {
        System.out.println("arduinoMesure : " + arduinoMesure);
        if ((arduinoMesure.getCapteurId() == 0) || (arduinoMesure.getArduinoId() == 0) || ((arduinoMesure.getArduinoNom() == null) && arduinoMesure.getArduinoNom().isEmpty())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            Capteur capteur = capteurRepo.findByCapteurId(arduinoMesure.getCapteurId());
            if (capteur == null) {
                capteur = new Capteur();
                capteur.setArduinoId(arduinoMesure.getArduinoId());
                capteur.setArduinoNom(arduinoMesure.getArduinoNom());
                capteur.setCapteurId(arduinoMesure.getCapteurId());
                capteur.setRaspberryId(RASPBERRY_ID);
                capteurRepo.save(capteur);
            }

            Mesure mesure = new Mesure();
            mesure.setDate(LocalDateTime.now());
            mesure.setValeur(arduinoMesure.getValeur());

            mesure.setCapteur(capteur);

            mesureRepo.save(mesure);
        } catch (Exception e) {
            System.out.println("Erreur inconnue est survenue : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de la sauvegarde de l'enregistrement");
        }


        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/mesurejournaliere", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JournaliereMesure>> journaliereMesure(@RequestParam("capteurId") int capteurId){

        return new ResponseEntity<>(JournaliereMesure.demo(capteurId), HttpStatus.OK);
    }

    /*private static final Logger log = LoggerFactory.getLogger(MesureController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        log.info("The time is now  depuis le rest{}", dateFormat.format(new Date()));
        File file = new File("C:\\temp\\toto.txt");
        List<String> list = new ArrayList<>();
        if(file.exists()){
            log.info("trouve");
            try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                list = stream
                        .filter(line -> !line.startsWith("line3"))
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

                log.error("list "+ list);
//                Capteur capteur = capteurRepo.findByCapteurId(arduinoMesure.getCapteurId());
//                if (capteur == null) {
//                    capteur = new Capteur();
//                    capteur.setArduinoId(arduinoMesure.getArduinoId());
//                    capteur.setArduinoNom(arduinoMesure.getArduinoNom());
//                    capteur.setCapteurId(arduinoMesure.getCapteurId());
//                    capteur.setRaspberryId(RASPBERRY_ID);
//                    capteurRepo.save(capteur);
//                }
//
//                Mesure mesure = new Mesure();
//                mesure.setDate(LocalDateTime.now());
//                mesure.setValeur(list.get(1));
//
//                mesure.setCapteur(capteur);
//                mesureRepo.save(mesure);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            log.info("fichier non trouve");
        }

    }*/

}

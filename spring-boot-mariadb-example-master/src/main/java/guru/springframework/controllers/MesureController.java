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

/**
 * Classe de services pour les appels depuis le Raspberry (Dashboard) ou l'arduino (Sauvegarde d'une mesure)
 *
 */
@CrossOrigin(origins = {"http://localhost:4200"},
        maxAge = 4800, allowCredentials = "false")
@Controller
public class MesureController {
    // Identifiant du Raspberry
    // Voir si l'identifiant doit être paramétrable ou envoyé par le Raspberry
    private static final int RASPBERRY_ID = 1234;

    private static final Logger log = LoggerFactory.getLogger(MesureController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    private CapteurRepository capteurRepo;

    @Autowired
    private MesureRepository mesureRepo;

    /**
     * Service d'enregistrement dans la base de données des mesures effectuées par l'arduino.
     * La requête est de type POST via un formulaire.
     * Les champs du formaulaire sont:
     * 		- arduinoId : identifiant de l'ardnuino
     * 		- arduinoNom : nom de l'arduino
     * 		- capteurId : identifiant du capteur
     * 		- valeur : mesure effectuée par le capteur
     *
     * @param arduinoMesure objet représentant les champs
     * @return Si les données ont été correctement enregistrées dans la base de données, renvoie OK
     * Sinon envoie un code retour 400 (Bad request) à l'appelant
     */
    @PostMapping(value = "/mesure")
    public ResponseEntity enregistrerMesure(@ModelAttribute ArduinoMesure arduinoMesure) {
        System.out.println("arduinoMesure : " + arduinoMesure);
        // Vérification des paramètres d'entrée (valeurs de paramètres obligatoires)
        if ((arduinoMesure.getCapteurId() == 0) || (arduinoMesure.getArduinoId() == 0) || ((arduinoMesure.getArduinoNom() == null) && arduinoMesure.getArduinoNom().isEmpty())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            // Recherche si le capteur n'a pas déjà été renregistré dans la base de données à partir de son identifiant pour une arduino donnée
            Capteur capteur = capteurRepo.findByCapteurId(arduinoMesure.getCapteurId());
            // Enregistrement du nouveau capteur dans la base de données
            if (capteur == null) {
                capteur = new Capteur();
                capteur.setArduinoId(arduinoMesure.getArduinoId());
                capteur.setArduinoNom(arduinoMesure.getArduinoNom());
                capteur.setCapteurId(arduinoMesure.getCapteurId());
                capteur.setRaspberryId(RASPBERRY_ID);
                capteurRepo.save(capteur);
            }

            // Enregistrement de la mesure du capteur dans la base de données
            Mesure mesure = new Mesure();
            // La date de la mesure est celle de l'instant où l'appel est effectué.
            // Peut-être changé si la donnée est fournie par l'arduino
            mesure.setDate(LocalDateTime.now());
            mesure.setValeur(arduinoMesure.getValeur());

            mesure.setCapteur(capteur);

            mesureRepo.save(mesure);
        } catch (Exception e) {
            // Si la requête soulève une exception, alors renvoyer une erreur à l'appelant et ajout d'une trace
            System.out.println("Erreur inconnue est survenue : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de la sauvegarde de l'enregistrement");
        }


        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Lecture dans la base de données des valeurs mesurées par les capteurs. Les
     * données lues sont agrégées par jour sur la semaine courante. La semaine
     * courante est déterminée à partir du jour où la requête est exécutée.
     *
     * La requête à ce service doit être effectuée via un GET avec les paramètres en argument.
     * Par exemple : http://localhost:3000/mesurejournaliere?capteurId=1
     *
     * @param capteurId
     *            identifiant du capteur
     * @return liste des mesures journalières. Sinon envoie un code retour 400 (Bad request) à l'appelant
     */
    @GetMapping(value = "/mesurejournaliere", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JournaliereMesure>> journaliereMesure(@RequestParam("capteurId") int capteurId){

        // Appel de la méthode permettant de tester le service sans aller jusqu'à la
        // base de données
        // return new ResponseEntity<>(JournaliereMesure.demo(capteurId),
        // HttpStatus.OK);
        return new ResponseEntity<>(JournaliereMesure.demo(capteurId), HttpStatus.OK);
    }

    /*@Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        log.info("The time is now  depuis le rest{}", dateFormat.format(new Date()));
        File file = new File("C:\\temp\\toto.txt");
        List<String> list = new ArrayList<>();
        if(file.exists()){
            log.info("Fichier trouvé");
            try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                list = stream
                        .filter(line -> !line.startsWith("line3"))
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

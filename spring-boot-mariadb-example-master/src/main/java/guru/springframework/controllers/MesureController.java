package guru.springframework.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guru.springframework.domain.Capteur;
import guru.springframework.domain.Mesure;
import guru.springframework.dto.ArduinoMesure;
import guru.springframework.dto.Jour;
import guru.springframework.dto.JournaliereMesure;
import guru.springframework.dto.MensuelleMesure;
import guru.springframework.dto.Mois;
import guru.springframework.repositories.CapteurRepository;
import guru.springframework.repositories.MesureRepository;

/**
 * Classe de services pour les appels depuis le Raspberry (Dashboard) ou
 * l'arduino (Sauvegarde d'une mesure)
 *
 * Note : - Déployer le site sous Tomcat (ng build --base-href=/prevhold/)
 *
 */
@CrossOrigin(origins = { "http://localhost:8080" }, maxAge = 4800, allowCredentials = "false")
@Controller
@Component
public class MesureController {
    // Logger du service de gestion des mesures en base de données
    private static final Logger log = LoggerFactory.getLogger(MesureController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // Index de la dernière ligne lue dans le fichier
    private static long derniereLigneLue = 0L;
    private static FileTime lastModified;

    // Valeur définie dans le fichier application.properties
    @Value("${mesureCSV.path}")
    private String mesureCSVFile;

    // Valeur définie dans le fichier application.properties
    @Value("${dashboard.capteur.seuil}")
    private int seuilCapteur;

    // Identifiant du Raspberry
    @Value("${raspberry.id}")
    private int raspberryId = 1234;

    @Autowired
    private CapteurRepository capteurRepo;

    @Autowired
    private MesureRepository mesureRepo;

    /**
     * Service d'enregistrement dans la base de données des mesures effectuées par
     * l'arduino. La requête est de type POST via un formulaire. Les champs du
     * formaulaire sont: - arduinoId : identifiant de l'ardnuino - arduinoNom : nom
     * de l'arduino - capteurId : identifiant du capteur - valeur : mesure effectuée
     * par le capteur
     *
     * @param arduinoMesure
     *            objet représentant les champs
     * @return Si les données ont été correctement enregistrées dans la base de
     *         données, renvoie OK Sinon envoie un code retour 400 (Bad request) à
     *         l'appelant
     */
    @PostMapping(value = "/mesure")
    public ResponseEntity enregistrerMesure(@ModelAttribute ArduinoMesure arduinoMesure) {
        System.out.println("arduinoMesure : " + arduinoMesure);

        // Vérification des paramètres d'entrée (valeurs de paramètres obligatoires)
        if ((arduinoMesure.getCapteurId() == 0) || (arduinoMesure.getArduinoId() == 0)
                || ((arduinoMesure.getArduinoNom() == null) && arduinoMesure.getArduinoNom().isEmpty())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            // Recherche si le capteur n'a pas déjà été renregistré dans la base de données
            // à partir de son identifiant pour un arduino donné
            Capteur capteur = capteurRepo.findByCapteurId(arduinoMesure.getCapteurId());
            if (capteur == null) {
                // Enregistrement du nouveau capteur dans la base de données
                capteur = new Capteur();
                capteur.setArduinoId(arduinoMesure.getArduinoId());
                capteur.setArduinoNom(arduinoMesure.getArduinoNom());
                capteur.setCapteurId(arduinoMesure.getCapteurId());
                capteur.setRaspberryId(raspberryId);
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
            // Si la requête soulève une exception, alors renvoyer une erreur à l'appelant
            // et ajout d'une trace
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
     * La requête à ce service doit être effectuée via un GET avec les paramètres en
     * argument. Par exemple : http://localhost:3000/mesurejournaliere?capteurId=1
     *
     * @param capteurId
     *            identifiant du capteur
     * @return liste des mesures journalières. Sinon envoie un code retour 400 (Bad
     *         request) à l'appelant
     */
    @GetMapping(value = "/mesurejournaliere", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity journaliereMesure(@RequestParam("capteurId") String capteurId) {
        Map<Jour, JournaliereMesure> semaine = JournaliereMesure.getSemaineMesureZero();

        try {
            // Leture dans la base de données des mesures récoltées par les capteurs
            List<JournaliereMesure> mesures = mesureRepo.getByCapteurByJour(capteurId, seuilCapteur);

            // Il est possible qu'il n'y ait pas de mesure pour tous les jours
            // Donc il faut ajouter les jours manquants
            for (JournaliereMesure mesure : mesures) {
                String jour = mesure.getJour();
                // Remplace la valeur de mesure de la semaine par celle de base de données
                semaine.get(Jour.valueOf(jour)).setOccurence(mesure.getOccurence());
            }

            // Convertit en liste les valeurs des mesures de la semaine
            List<JournaliereMesure> semaineValeurs = semaine.values().stream().collect(Collectors.toList());

            // Appel de la méthode permettant de tester le service sans aller jusqu'à la
            // base de données
            // return new ResponseEntity<>(JournaliereMesure.demo(capteurId),
            // HttpStatus.OK);
            return new ResponseEntity<>(semaineValeurs, HttpStatus.OK);
        } catch (Exception e) {
            // Si la requête soulève une exception, alors renvoyer une erreur à l'appelant
            // et ajout d'une trace
            log.error("Erreur inconnue est survenue : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur de l'exécution de la requête:" + e.getMessage());
        }
    }

    /**
     * Lecture dans la base de données des valeurs mesurées par les capteurs. Les
     * données lues sont agrégées par mois sur l'année courante. L'année courante
     * est déterminée à partir du jour où la requête est exécutée (voir la requête
     * dans le gestionnaire de l'entité Mesure,
     * guru.springframework.repositories.MesureRepository).
     *
     * La requête à ce service doit être effectuée via un GET avec les paramètres en
     * argument. Par exemple : http://localhost:3000/mesuremensuelle?capteurId=1
     *
     * @param capteurId
     *            identifiant du capteur
     * @return liste des mesures mensuelles. Sinon envoie un code retour 400 (Bad
     *         request) à l'appelant
     */
    @GetMapping(value = "/mesuremensuelle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity mensuelleMesure(@RequestParam("capteurId") String capteurId) {
        Map<Mois, MensuelleMesure> annee = MensuelleMesure.getAnneeMesureZero();

        try {
            // Leture dans la base de données des mesures récoltées par les capteurs
            List<MensuelleMesure> mesures = mesureRepo.getByCapteurByMois(capteurId, seuilCapteur);

            // Il est possible qu'il n'y ait pas de mesure pour tous les mois
            // Donc il faut ajouter les mois manquants
            for (MensuelleMesure mesure : mesures) {
                String mois = mesure.getMois();
                // Remplace la valeur de mesure du mois par celle de base de données
                annee.get(Mois.valueOf(mois)).setOccurence(mesure.getOccurence());
            }

            // Convertit en liste les valeurs des mesures de l'année
            List<MensuelleMesure> anneeValeurs = annee.values().stream().collect(Collectors.toList());

            // Appel de la méthode permettant de tester le service sans aller jusqu'à la
            // base de données
            // return new ResponseEntity<>(MensuelleMesure.demo(capteurId),
            // HttpStatus.OK);
            return new ResponseEntity<>(anneeValeurs, HttpStatus.OK);
        } catch (Exception e) {
            // Si la requête soulève une exception, alors renvoyer une erreur à l'appelant
            // et ajout d'une trace
            log.error("Erreur inconnue est survenue : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur de l'exécution de la requête: " + e.getMessage());
        }
    }

    /**
     * Lancement de la lecture toutes les 10 secondes
     */
    @Scheduled(fixedRateString = "${mesureCSV.fixedrate}", initialDelay = 5000)
    public void readMesuresViaFichier() {
        log.info("Analyse du fichier de mesure {}: {}", dateFormat.format(new Date()), mesureCSVFile);
        try {
            processArduinoFile(mesureCSVFile);
        } catch (IOException e) {
            log.error("Erreur inconnue est survenue : {}", e.getMessage());
        }
    }

    /*
     * Lit le fichier contenant les mesures
     *
     * Si au démarrage du serveur, le fichier est présent il est lu intégralement.
     * Si on ne veut pas qu'il soit lu, il faut supprimer le fichier de données avant de démarer le serveur
     *
     */
    private void processArduinoFile(String mesureCSVFile) throws IOException {
        // Teste si le fichier existe
        boolean exists = Files.exists(Paths.get(mesureCSVFile), LinkOption.NOFOLLOW_LINKS);
        if (!exists) {
            // Le fichier n'existe pas ou a été supprimé, réinitialise la dernière ligne lue
            log.info("Initialise l'index de la dernière ligne lue à zéro");
            derniereLigneLue = 0;
            lastModified = null;
            return;
        } else {
            // Lecture des metadonnées sur le fichier (heure de modification)
            lastModified = Files.getLastModifiedTime(Paths.get(mesureCSVFile), LinkOption.NOFOLLOW_LINKS);
            log.info("Lecture du fichier '{}', modifié le '{}'", mesureCSVFile, lastModified);
        }

        List<ArduinoMesure> mesures = parseArduinoCSV(mesureCSVFile);
        int numeroLigne = 0;
        for (ArduinoMesure mesure : mesures) {
            numeroLigne++;
            log.info("Lecture de la ligne n°{}", numeroLigne);
            // Vérifie si la ligne a déjà été lue
            if (numeroLigne <= derniereLigneLue) {
                //log.info("Ligne n°{} déjà lue",numeroLigne);
                continue;
            }
            // Nouvelle ligne non lue, insère la mesure en base
            insertMesure(mesure);
            // Positionne le compteur de dernière ligne lue
            derniereLigneLue = numeroLigne;
            log.info("Positionne l'index de la derniere ligne lue à {}", derniereLigneLue);
        }
    }

    /*
     * Lit intégralement le fichier de mesures
     * Saute l'entête du fichier (CapteurID, NomArd, ArduinoID, Arduino, date)
     */
    private List<ArduinoMesure> parseArduinoCSV(String mesureCSVFile) throws IOException {
        List<ArduinoMesure> mesures = new ArrayList<>(50);

        try (Stream<String> lines = Files.lines(Paths.get(mesureCSVFile))) {
            // Skip header line
            List<List<String>> allLines = lines.skip(1).map((line) -> Arrays.asList(line.split(";")))
                    .collect(Collectors.toList());
            for (List<String> line : allLines) {
                ArduinoMesure mesure = new ArduinoMesure();
                // capteurId
                mesure.setCapteurId(Integer.parseInt(line.get(0)));
                // arduinoNom
                mesure.setArduinoNom(line.get(1));
                // arduinoId
                mesure.setArduinoId(Integer.parseInt(line.get(2)));
                // valeur de la mesure
                mesure.setValeur(Double.parseDouble(line.get(3)));
                String dateArduino = line.get(4);
                mesure.setRawDate(dateArduino);
                log.info("Lecture d'une mesure dans le fichier: {}", mesure);
                mesures.add(mesure);
            }
        }

        return mesures;
    }

    /*
     * Insère une mesure en base de données à partir d'une mesure réalisée par l'Arduino
     */
    private void insertMesure(ArduinoMesure mesure) {
        Capteur capteurDB = capteurRepo.findByCapteurId(mesure.getCapteurId());
        if (capteurDB == null) {
            capteurDB = new Capteur();
            capteurDB.setArduinoId(mesure.getArduinoId());
            capteurDB.setArduinoNom(mesure.getArduinoNom());
            capteurDB.setCapteurId(mesure.getCapteurId());
            capteurDB.setRaspberryId(raspberryId);
            capteurRepo.save(capteurDB);
        }

        Mesure mesureDB = new Mesure();
        mesureDB.setDate(mesure.getDate());
        mesureDB.setValeur(mesure.getValeur());

        mesureDB.setCapteur(capteurDB);

        mesureRepo.save(mesureDB);
    }

}


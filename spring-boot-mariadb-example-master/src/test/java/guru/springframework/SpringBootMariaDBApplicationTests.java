package guru.springframework;

import guru.springframework.domain.Capteur;
import guru.springframework.domain.Mesure;
import guru.springframework.repositories.CapteurRepository;
import guru.springframework.repositories.MesureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMariaDBApplicationTests {
    private static final int RASPBERRY_ID = 1234;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @Autowired
    private CapteurRepository capteurRepo;

    @Autowired
    private MesureRepository mesureRepo;

    @Test
    public void testImport() throws IOException {
        String mesureCSVFile = "C:\\Users\\asus\\Desktop\\mesure.csv";

        parseCSV(mesureCSVFile);

    }

    @Test
    public void doucheImport() throws IOException {
        String mesureCSVFile = "C:\\Users\\asus\\Desktop\\doucheMesure.txt";

        parseCSV(mesureCSVFile);

    }

    private void parseCSV(String mesureCSVFile) throws IOException {
        String capteurId;
        String arduinoId;
        String arduinoNom;
        String valeur;
        String date;
        try (Stream<String> lines = Files.lines(Paths.get(mesureCSVFile))) {
            List<List<String>> allLines = lines
                    .skip(1)
                    .map((line) -> Arrays.asList(line.split(";")))
                    .collect(Collectors.toList());
            for (List<String> line : allLines) {
                capteurId = line.get(0);
                arduinoId = line.get(1);
                arduinoNom = line.get(2);
                valeur = line.get(3);
                date = line.get(4);
                insertMesure(capteurId, arduinoId, arduinoNom, valeur, date);
                System.out.println(line);
                System.out.println("---------");
            }
        }
    }

    private void insertMesure(String capteurId, String arduinoId, String arduinoNom, String valeur, String date) {
        Capteur capteur = capteurRepo.findByCapteurId(Integer.parseInt(capteurId));
        if (capteur == null) {
            capteur = new Capteur();
            capteur.setArduinoId(Integer.parseInt(arduinoId));
            capteur.setArduinoNom(arduinoNom);
            capteur.setCapteurId(Integer.parseInt(capteurId));
            capteur.setRaspberryId(RASPBERRY_ID);
            capteurRepo.save(capteur);
        }

        Mesure mesure = new Mesure();
        mesure.setDate(LocalDateTime.parse(date, formatter));
        mesure.setValeur(Double.parseDouble(valeur));

        mesure.setCapteur(capteur);

        mesureRepo.save(mesure);
    }

    @Test
    public void contextLoads() {
    }

}

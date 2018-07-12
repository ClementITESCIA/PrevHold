package guru.springframework.dto;

import java.util.ArrayList;
import java.util.List;

public class JournaliereMesure {

    private int occurence;
    private String jour;


    public static List<JournaliereMesure> demo(int capteurId){
        List<JournaliereMesure> mesures = new ArrayList<JournaliereMesure>(7);
        JournaliereMesure lundi = new JournaliereMesure("Lundi", 3);
        JournaliereMesure mardi = new JournaliereMesure("Mardi", 1);
        JournaliereMesure mercredi = new JournaliereMesure("Mercredi", 0);
        JournaliereMesure jeudi = new JournaliereMesure("Jeudi", 2);
        JournaliereMesure vendredi = new JournaliereMesure("Vendredi", 0);
        JournaliereMesure samedi = new JournaliereMesure("Samedi", 1);
        JournaliereMesure dimanche = new JournaliereMesure("Dimanche", 3);
        mesures.add(lundi);
        mesures.add(mardi);
        mesures.add(mercredi);
        mesures.add(jeudi);
        mesures.add(vendredi);
        mesures.add(samedi);
        mesures.add(dimanche);
        return mesures;
    }

    private JournaliereMesure(String jour, int occurence){
        this.jour = jour;
        this.occurence = occurence;
    }
    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

}

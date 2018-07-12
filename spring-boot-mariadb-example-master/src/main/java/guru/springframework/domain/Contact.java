package guru.springframework.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nom;

    @Column(unique = true)
    private String email;

    // Association 1 contact pour plusieurs messages
    @OneToMany(mappedBy = "contact")
    private List<Message> messages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

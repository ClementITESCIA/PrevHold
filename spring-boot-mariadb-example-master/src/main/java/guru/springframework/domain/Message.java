package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String texte;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    public Long getId() {
        return id;
    }

    public String getTexte() {
        return texte;
    }

    public Contact getContact() {
        return contact;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}

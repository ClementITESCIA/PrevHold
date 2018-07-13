package guru.springframework.controllers;

import guru.springframework.domain.Contact;
import guru.springframework.domain.Message;
import guru.springframework.repositories.ContactRepository;
import guru.springframework.repositories.MessageRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin(origins = {"http://localhost:4200"},
        maxAge = 4800, allowCredentials = "false")
@Controller
public class AccueilController {


    @Autowired
    private ContactRepository contactRepo;

    @Autowired
    private MessageRepository messageRepo;


    @RequestMapping(value = "/prevhold", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity prevholdPost(@RequestBody String jsonMessage) {
        System.out.println("message" + jsonMessage);
        JSONObject jsonObject = new JSONObject(jsonMessage);
        String nom = jsonObject.getString("nom");
        String email = jsonObject.getString("email");
        String texte = jsonObject.getString("message");


        Contact contact = contactRepo.findByNom(nom);
        if (contact == null) {
            contact = new Contact();
            contact.setNom(nom);
            contact.setEmail(email);
        }
        Message message = new Message();
        message.setTexte(texte);
        message.setContact(contact);
        messageRepo.save(message);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

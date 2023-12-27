package proj.pmail.controller;

import proj.pmail.dto.ContactDTO;
import proj.pmail.entity.Contact;
import proj.pmail.service.ContactService;
import proj.pmail.utils.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private Key JWTKey;

    @GetMapping("/{username}")
    public R getContactsList(@PathVariable String username, @RequestHeader String Authorization) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parserBuilder().setSigningKey(JWTKey).build().parseClaimsJws(Authorization);
            if (!claimsJws.getBody().getSubject().equals(username)) {
                return new R(false, "authentication failed", null);
            }
        } catch (JwtException e) {
            return new R(false, "authentication failed", null);
        }

        List<Contact> result = contactService.getListByCreator(username);
        return new R(result != null, result != null ? null : "failed to get contacts", result);
    }

    @PostMapping("/{username}")
    public R addContact(@PathVariable String username, @RequestBody Contact contact, @RequestHeader String Authorization) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parserBuilder().setSigningKey(JWTKey).build().parseClaimsJws(Authorization);
            if (!claimsJws.getBody().getSubject().equals(username)) {
                return new R(false, "authentication failed", null);
            }
        } catch (JwtException e) {
            return new R(false, "authentication failed", null);
        }

        Contact c = contactService.getContactByAddressAndCreator(contact.getAddress(), username);
        if (c != null) {
            return new R(false, "contact already exists", null);
        }
        boolean success = contactService.addContact(contact);
        return new R(success, success ? null : "failed to add contact", null);
    }

    @DeleteMapping("/{username}")
    public R deleteContact(@PathVariable String username, @RequestBody ContactDTO contactDTO, @RequestHeader String Authorization) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parserBuilder().setSigningKey(JWTKey).build().parseClaimsJws(Authorization);
            if (!claimsJws.getBody().getSubject().equals(username)) {
                return new R(false, "authentication failed", null);
            }
        } catch (JwtException e) {
            return new R(false, "authentication failed", null);
        }

        boolean success = contactService.deleteContact(contactDTO.getIds());
        return new R(success, success ? null : "failed to delete contact", null);
    }

}
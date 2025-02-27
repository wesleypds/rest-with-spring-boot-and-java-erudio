package brr.com.wesleypds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.services.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person findById(@PathVariable String id) {
        return personService.findById(id);
    }

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

}

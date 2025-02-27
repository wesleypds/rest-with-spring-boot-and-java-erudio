package brr.com.wesleypds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.services.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable String id) {
        return personService.findById(id);
    }

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        personService.delete(id);
    }

}

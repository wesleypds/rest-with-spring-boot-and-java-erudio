package curso.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.spring.boot.model.dto.PersonDTO;
import curso.spring.boot.model.entity.PersonEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.service.PersonService;

@RestController
@RequestMapping(value = "api/people/v1")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public List<PersonDTO> findAll() {
        List<PersonDTO> list = mapper.parseListObject(service.findAll(), PersonDTO.class);
        return service.addLinksHateoas(list);
    }

    @GetMapping(
        value = "/{id}",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    public PersonDTO findById(@PathVariable(name = "id") Long id) {
        var model = mapper.parseObject(service.findById(id), PersonDTO.class);
        return service.addLinksHateoas(model);
    }

    @PostMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    public PersonDTO create(@RequestBody PersonDTO model) {
        PersonEntity entity = mapper.parseObject(model, PersonEntity.class);
        model = mapper.parseObject(service.create(entity), PersonDTO.class);
        return service.addLinksHateoas(model);
    }

    @PutMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    public PersonDTO update(@RequestBody PersonDTO model) {
        PersonEntity entity = mapper.parseObject(model, PersonEntity.class);
        model = mapper.parseObject(service.update(entity), PersonDTO.class);
        return service.addLinksHateoas(model);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

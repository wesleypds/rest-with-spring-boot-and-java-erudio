package curso.spring.boot.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import curso.spring.boot.controller.PersonController;
import curso.spring.boot.exception.RequiredObjectIsNullException;
import curso.spring.boot.exception.ResourceNotFoundException;
import curso.spring.boot.model.dto.PersonDTO;
import curso.spring.boot.model.entity.PersonEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.repository.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public Page<PersonEntity> findAll(Pageable pageable) {
        
        logger.info("Finding all people!");
        return repository.findAll(pageable);
    }

    public PersonEntity findById(Long id) {

        logger.info("Finding one person");

        return repository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public PersonEntity create(PersonEntity entity) {

        if (entity == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person");

        return repository.save(entity);
    }

    public PersonEntity update(PersonEntity entity) {

        if (entity == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person");

        PersonEntity e = findById(entity.getId());

        e.setFirstName(entity.getFirstName());
        e.setLastName(entity.getLastName());
        e.setAddress(entity.getAddress());
        e.setGender(entity.getGender());

        return repository.save(e);
    }

    @Transactional
    public PersonEntity disablePerson(Long id) {

        logger.info("Disabling one person");

        findById(id);

        repository.disablePerson(id);

        return findById(id);
    }

    public void delete(Long id) {

        logger.info("Deleting one person");

        PersonEntity entity = findById(id);

        repository.delete(entity);
    }

    public PersonDTO addLinksHateoas(PersonDTO model) {
        model.add(linkTo(methodOn(PersonController.class).findById(model.getId())).withRel("findById").withType("GET"));
        model.add(linkTo(methodOn(PersonController.class).update(model)).withRel("update").withType("PUT"));
        model.add(linkTo(methodOn(PersonController.class).disablePerson(model.getId())).withRel("disablePerson").withType("PATCH"));
        model.add(linkTo(methodOn(PersonController.class).delete(model.getId())).withRel("delete").withType("DELETE"));
        return model;
    }

    public Page<PersonDTO> addLinksHateoasFindAll(Page<PersonEntity> list, ObjectMapper mapper) {
        return list.map(entity -> {
            var convertedValue = mapper.parseObject(entity, PersonDTO.class);
            return addLinksHateoas(convertedValue);
        });
    }

    public Link addLinksHateoasPage(Page<PersonDTO> links, Pageable pageable, String field) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort().toString(),
                    field
                ))
                .withSelfRel();
    }

}

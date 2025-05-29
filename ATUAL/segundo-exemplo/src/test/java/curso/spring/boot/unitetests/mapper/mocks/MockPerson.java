package curso.spring.boot.unitetests.mapper.mocks;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import curso.spring.boot.controller.PersonController;
import curso.spring.boot.model.dto.PersonDTO;
import curso.spring.boot.model.entity.PersonEntity;

public class MockPerson {


    public PersonEntity mockEntity() {
        return mockEntity(1);
    }
    
    public PersonDTO mockDTO() {
        return mockDTO(1);
    }

    public PersonDTO mockDTOWithHateoas() {
        return mockDTOWithHateoas(1);
    }
    
    public List<PersonEntity> mockEntityList() {
        List<PersonEntity> personEntities = new ArrayList<PersonEntity>();
        for (int i = 1; i <= 14; i++) {
            personEntities.add(mockEntity(i));
        }
        return personEntities;
    }

    public List<PersonDTO> mockDTOList() {
        List<PersonDTO> personDTOs = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            personDTOs.add(mockDTO(i));
        }
        return personDTOs;
    }

    public List<PersonDTO> mockDTOWithHateoasList() {
        List<PersonDTO> personDTOs = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            personDTOs.add(mockDTOWithHateoas(i));
        }
        return personDTOs;
    }
    
    public PersonEntity mockEntity(Integer number) {
        PersonEntity PersonEntity = new PersonEntity();
        PersonEntity.setAddress("Address Test" + number);
        PersonEntity.setFirstName("First Name Test" + number);
        PersonEntity.setGender(((number % 2)==0) ? "Male" : "Female");
        PersonEntity.setId(number.longValue());
        PersonEntity.setLastName("Last Name Test" + number);
        return PersonEntity;
    }

    public PersonDTO mockDTO(Integer number) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setAddress("Address Test" + number);
        personDTO.setFirstName("First Name Test" + number);
        personDTO.setGender(((number % 2)==0) ? "Male" : "Female");
        personDTO.setId(number.longValue());
        personDTO.setLastName("Last Name Test" + number);
        return personDTO;
    }

    public PersonDTO mockDTOWithHateoas(Integer number) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setAddress("Address Test" + number);
        personDTO.setFirstName("First Name Test" + number);
        personDTO.setGender(((number % 2)==0) ? "Male" : "Female");
        personDTO.setId(number.longValue());
        personDTO.setLastName("Last Name Test" + number);
        addLinksHateoas(personDTO);
        return personDTO;
    }

    private void addLinksHateoas(PersonDTO personDTO) {
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withRel("findById").withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).update(personDTO)).withRel("update").withType("PUT"));
        personDTO.add(linkTo(methodOn(PersonController.class).delete(personDTO.getId())).withRel("delete").withType("DELETE"));
    }

}
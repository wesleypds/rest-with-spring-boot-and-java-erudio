package curso.spring.boot.unitetests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import curso.spring.boot.model.dto.PersonDTO;
import curso.spring.boot.model.entity.PersonEntity;

public class MockPerson {


    public PersonEntity mockEntity() {
        return mockEntity(0);
    }
    
    public PersonDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<PersonEntity> mockEntityList() {
        List<PersonEntity> PersonEntitys = new ArrayList<PersonEntity>();
        for (int i = 0; i < 14; i++) {
            PersonEntitys.add(mockEntity(i));
        }
        return PersonEntitys;
    }

    public List<PersonDTO> mockDTOList() {
        List<PersonDTO> PersonEntitys = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            PersonEntitys.add(mockDTO(i));
        }
        return PersonEntitys;
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
        PersonDTO PersonEntity = new PersonDTO();
        PersonEntity.setAddress("Address Test" + number);
        PersonEntity.setFirstName("First Name Test" + number);
        PersonEntity.setGender(((number % 2)==0) ? "Male" : "Female");
        PersonEntity.setId(number.longValue());
        PersonEntity.setLastName("Last Name Test" + number);
        return PersonEntity;
    }

}
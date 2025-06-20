package curso.spring.boot.model.dto;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "people")
public class PersonDTO extends GenericDTO<PersonDTO> {

    private String firstName;

    private String lastName;

    private String address;

    private String gender;

    private Boolean enabled;

    public PersonDTO() {super();}

    public PersonDTO(String firstName, String lastName, String address, String gender, Boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

}

package curso.spring.boot.integrationtests.dto.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

import curso.spring.boot.integrationtests.dto.PersonDTO;

public class WrapperPersonDTO implements Serializable {

    @JsonAlias({"_embedded"})
    private WrapperEmbeddedDTO<PersonDTO> embedded;

    public WrapperEmbeddedDTO<PersonDTO> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(WrapperEmbeddedDTO<PersonDTO> embedded) {
        this.embedded = embedded;
    }

}

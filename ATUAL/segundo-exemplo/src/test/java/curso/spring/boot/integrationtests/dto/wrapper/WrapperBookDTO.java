package curso.spring.boot.integrationtests.dto.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

import curso.spring.boot.integrationtests.dto.BookDTO;

public class WrapperBookDTO implements Serializable {

    @JsonAlias({"_embedded"})
    private WrapperEmbeddedDTO<BookDTO> embedded;

    public WrapperEmbeddedDTO<BookDTO> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(WrapperEmbeddedDTO<BookDTO> embedded) {
        this.embedded = embedded;
    }

}

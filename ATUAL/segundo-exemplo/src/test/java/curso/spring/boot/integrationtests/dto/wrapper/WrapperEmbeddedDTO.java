package curso.spring.boot.integrationtests.dto.wrapper;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class WrapperEmbeddedDTO<T> implements Serializable {

    @JsonAlias({"people", "books"})
    private List<T> listObj;

    public List<T> getListObj() {
        return listObj;
    }

    public void setListObj(List<T> listObj) {
        this.listObj = listObj;
    }

}

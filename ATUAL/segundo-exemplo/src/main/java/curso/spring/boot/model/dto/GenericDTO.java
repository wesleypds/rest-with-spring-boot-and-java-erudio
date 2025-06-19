package curso.spring.boot.model.dto;

import org.springframework.hateoas.RepresentationModel;

public class GenericDTO<T> extends RepresentationModel<GenericDTO<T>> {
    private Long id;

    public GenericDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

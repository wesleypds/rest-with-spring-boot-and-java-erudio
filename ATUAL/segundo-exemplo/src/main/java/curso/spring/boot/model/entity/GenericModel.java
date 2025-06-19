package curso.spring.boot.model.entity;

public class GenericModel<T> {
    private Long id;

    public GenericModel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

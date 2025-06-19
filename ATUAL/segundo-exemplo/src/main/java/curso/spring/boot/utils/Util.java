package curso.spring.boot.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class Util {

    // TODO: Implementar Generics
    // public <M,T> M addLinksHateoas(M model, Class<T> classType) {
    //     model.add(linkTo(methodOn(classType).findById(model.getId())).withRel("findById").withType("GET"));
    //     model.add(linkTo(methodOn(classType).update(model)).withRel("update").withType("PUT"));
    //     model.add(linkTo(methodOn(classType).disablePerson(model.getId())).withRel("disable").withType("PATCH"));
    //     model.add(linkTo(methodOn(classType).delete(model.getId())).withRel("delete").withType("DELETE"));
    //     return model;
    // }

    public static Pageable resolvePageable(Integer page, Integer size, String direction, String propertieName) {
        Sort sortDirection = getSortDirection(direction, propertieName);
        return PageRequest.of(page, size, sortDirection);
    }

    private static Sort getSortDirection(String direction, String propertieName) {
        return Direction.DESC.name().equalsIgnoreCase(direction) ? 
                            Sort.by(Direction.DESC, propertieName) : Sort.by(Direction.ASC, propertieName);
    }
}

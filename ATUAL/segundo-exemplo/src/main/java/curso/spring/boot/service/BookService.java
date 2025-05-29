package curso.spring.boot.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.boot.controller.BookController;
import curso.spring.boot.exception.RequiredObjectIsNullException;
import curso.spring.boot.exception.ResourceNotFoundException;
import curso.spring.boot.model.dto.BookDTO;
import curso.spring.boot.model.entity.BookEntity;
import curso.spring.boot.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public List<BookEntity> findAll() {
        
        logger.info("Finding all books!");

        return repository.findAll();
    }

    public BookEntity findById(Long id) {

        logger.info("Finding one book");

        return repository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public BookEntity create(BookEntity entity) {

        if (entity == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book");

        return repository.save(entity);
    }

    public BookEntity update(BookEntity entity) {

        if (entity == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book");

        BookEntity e = findById(entity.getId());

        e.setAuthor(entity.getAuthor());
        e.setLaunchDate(entity.getLaunchDate());
        e.setPrice(entity.getPrice());
        e.setTitle(entity.getTitle());

        return repository.save(e);
    }

    public void delete(Long id) {

        logger.info("Deleting one book");

        BookEntity entity = findById(id);

        repository.delete(entity);
    }

    public BookDTO addLinksHateoas(BookDTO model) {
        model.add(linkTo(methodOn(BookController.class).findById(model.getId())).withRel("findById").withType("GET"));
        model.add(linkTo(methodOn(BookController.class).update(model)).withRel("update").withType("PUT"));
        model.add(linkTo(methodOn(BookController.class).delete(model.getId())).withRel("delete").withType("DELETE"));
        return model;
    }

    public List<BookDTO> addLinksHateoas(List<BookDTO> models) {
        return models.stream().map(m -> addLinksHateoas(m)).toList();
    }

}

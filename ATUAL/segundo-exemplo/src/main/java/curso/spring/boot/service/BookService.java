package curso.spring.boot.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import curso.spring.boot.controller.BookController;
import curso.spring.boot.controller.PersonController;
import curso.spring.boot.exception.RequiredObjectIsNullException;
import curso.spring.boot.exception.ResourceNotFoundException;
import curso.spring.boot.model.dto.BookDTO;
import curso.spring.boot.model.entity.BookEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public Page<BookEntity> findAll(Pageable pageable) {
        
        logger.info("Finding all books!");

        return repository.findAll(pageable);
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

    public Page<BookDTO> addLinksHateoasFindAll(Page<BookEntity> list, ObjectMapper mapper) {
        return list.map(entity -> {
            var convertedValue = mapper.parseObject(entity, BookDTO.class);
            return addLinksHateoas(convertedValue);
        });
    }

    public Link addLinksHateoasPage(Page<BookDTO> links, Pageable pageable, String field) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort().toString(),
                    field
                ))
                .withSelfRel();
    }

}

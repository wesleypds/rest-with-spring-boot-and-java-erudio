package curso.spring.boot.mocks;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import curso.spring.boot.controller.BookController;
import curso.spring.boot.model.dto.BookDTO;
import curso.spring.boot.model.entity.BookEntity;
import curso.spring.boot.model.mapper.ObjectMapper;

public class MockBook {

    public BookEntity mockEntity() {
        return mockEntity(1);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(1);
    }

    public BookDTO mockDTOWithHateoas() {
        return mockDTOWithHateoas(1);
    }
    
    public List<BookEntity> mockEntityList() {
        List<BookEntity> bookEntities = new ArrayList<BookEntity>();
        for (int i = 1; i <= 14; i++) {
            bookEntities.add(mockEntity(i));
        }
        return bookEntities;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            bookDTOs.add(mockDTO(i));
        }
        return bookDTOs;
    }

    public List<BookDTO> mockDTOWithHateoasList() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            bookDTOs.add(mockDTOWithHateoas(i));
        }
        return bookDTOs;
    }
    
    public BookEntity mockEntity(Integer number) {
        BookEntity BookEntity = new BookEntity();
        BookEntity.setAuthor("Author Test" + number);
        BookEntity.setLaunchDate(LocalDateTime.of(2025, 05, 29, 00, 00, 00));
        BookEntity.setPrice(BigDecimal.valueOf(number.longValue()));
        BookEntity.setId(number.longValue());
        BookEntity.setTitle("Title Test" + number);
        return BookEntity;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("Author Test" + number);
        bookDTO.setLaunchDate(LocalDateTime.of(2025, 05, 29, 00, 00, 00));
        bookDTO.setPrice(BigDecimal.valueOf(number.longValue()));
        bookDTO.setId(number.longValue());
        bookDTO.setTitle("Title Test" + number);
        return bookDTO;
    }

    public BookDTO mockDTOWithHateoas(Integer number) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("Author Test" + number);
        bookDTO.setLaunchDate(LocalDateTime.of(2025, 05, 29, 00, 00, 00));
        bookDTO.setPrice(BigDecimal.valueOf(number.longValue()));
        bookDTO.setId(number.longValue());
        bookDTO.setTitle("Title Test" + number);
        addLinksHateoas(bookDTO);
        return bookDTO;
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
package curso.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import curso.spring.boot.controller.docs.BookControllerDocs;
import curso.spring.boot.model.dto.BookDTO;
import curso.spring.boot.model.entity.BookEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.service.BookService;
import curso.spring.boot.utils.Util;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/books/v1")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PagedResourcesAssembler<BookDTO> assembler;

    @Override
    @GetMapping(produces = {
        MediaType.APPLICATION_JSON_VALUE, 
        MediaType.APPLICATION_XML_VALUE, 
        MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                    @RequestParam(name = "field", defaultValue = "id") String field) {
        
        Pageable pageable = Util.resolvePageable(page, size, direction, field);
        Page<BookDTO> list = service.addLinksHateoasFindAll(service.findAll(pageable), mapper);
        Link pageLinks = service.addLinksHateoasPage(list, pageable, field);

        return ResponseEntity.ok().body(assembler.toModel(list, pageLinks));
    }

    @Override
    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE, 
            MediaType.APPLICATION_XML_VALUE, 
            MediaType.APPLICATION_YAML_VALUE})
    public BookDTO findById(@PathVariable(name = "id") Long id) {
        var model = mapper.parseObject(service.findById(id), BookDTO.class);
        return service.addLinksHateoas(model);
    }

    @Override
    @PostMapping(produces = {
                    MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE, 
                    MediaType.APPLICATION_YAML_VALUE},
                consumes = {
                    MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE, 
                    MediaType.APPLICATION_YAML_VALUE})
    public BookDTO create(@RequestBody BookDTO model) {
        BookEntity entity = mapper.parseObject(model, BookEntity.class);
        model = mapper.parseObject(service.create(entity), BookDTO.class);
        return service.addLinksHateoas(model);
    }

    @Override
    @PutMapping(produces = {
                    MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE, 
                    MediaType.APPLICATION_YAML_VALUE},
                consumes = {
                    MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE, 
                    MediaType.APPLICATION_YAML_VALUE})
    public BookDTO update(@RequestBody BookDTO model) {
        BookEntity entity = mapper.parseObject(model, BookEntity.class);
        model = mapper.parseObject(service.update(entity), BookDTO.class);
        return service.addLinksHateoas(model);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

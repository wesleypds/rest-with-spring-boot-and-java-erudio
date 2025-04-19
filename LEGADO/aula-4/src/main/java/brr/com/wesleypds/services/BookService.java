package brr.com.wesleypds.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import brr.com.wesleypds.controllers.BookController;
import brr.com.wesleypds.data.vo.BookVO;
import brr.com.wesleypds.exceptions.RequiredIsNullException;
import brr.com.wesleypds.exceptions.ResourceNotFoundException;
import brr.com.wesleypds.mapper.DozerMapper;
import brr.com.wesleypds.models.Book;
import brr.com.wesleypds.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PagedResourcesAssembler<BookVO> assembler;

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public BookVO create(BookVO vo) {

        if (vo == null)
            throw new RequiredIsNullException();
        logger.info("Creating one book!");

        Book entity = bookRepository.save(DozerMapper.parseObject(vo, Book.class));
        vo = DozerMapper.parseObject(entity, BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO vo) {

        if (vo == null)
            throw new RequiredIsNullException();
        logger.info("Updating one book!");

        Book entity = bookRepository.findById(vo.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID!")));

        entity.setAuthor(vo.getAuthor());
        entity.setLaunchDate(vo.getLaunchDate());
        entity.setPrice(vo.getPrice());
        entity.setTitle(vo.getTitle());

        entity = bookRepository.save(entity);
        vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public BookVO findById(Long id) {

        logger.info("Finding one book!");

        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No records found this ID!", id)));

        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

        logger.info("Finding all people!");

        var bookPage = bookRepository.findAll(pageable);
        var bookVosPage = bookPage.map(b -> DozerMapper.parseObject(b, BookVO.class));
        bookVosPage.map(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
        Link link = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
                .withSelfRel();

        return assembler.toModel(bookVosPage, link);
    }

    public void delete(Long id) {

        logger.info("Deleting one book!");

        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID!")));

        bookRepository.delete(entity);
    }

}

package brr.com.wesleypds.services;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
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

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public BookVO create(BookVO vo) {

        if (vo == null) throw new RequiredIsNullException();
        logger.info("Creating one book!");

        Book entity = bookRepository.save(DozerMapper.parseObject(vo, Book.class));
        vo = DozerMapper.parseObject(entity, BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO vo) {

        if (vo == null) throw new RequiredIsNullException();
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

    public List<BookVO> findAll() {

        logger.info("Finding all people!");

        List<BookVO> list = DozerMapper.parseListObjects(bookRepository.findAll(), BookVO.class);

        list.stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return list;
    }

    public void delete(Long id) {

        logger.info("Deleting one book!");

        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID!")));
        
        bookRepository.delete(entity);
    }

}

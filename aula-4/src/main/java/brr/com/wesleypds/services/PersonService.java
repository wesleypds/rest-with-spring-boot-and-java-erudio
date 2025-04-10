package brr.com.wesleypds.services;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brr.com.wesleypds.controllers.PersonController;
import brr.com.wesleypds.data.vo.PersonVO;
import brr.com.wesleypds.exceptions.RequiredIsNullException;
import brr.com.wesleypds.exceptions.ResourceNotFoundException;
import brr.com.wesleypds.mapper.DozerMapper;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO create(PersonVO vo) {

        if (vo == null) throw new RequiredIsNullException();
        logger.info("Creating one person!");

        Person entity = personRepository.save(DozerMapper.parseObject(vo, Person.class));
        vo = DozerMapper.parseObject(entity, PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO vo) {

        if (vo == null) throw new RequiredIsNullException();
        logger.info("Updating one person!");

        Person entity = personRepository.findById(vo.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID!")));

        entity.setFirstName(vo.getFirstName());
        entity.setLastName(vo.getLastName());
        entity.setAddress(vo.getAddress());
        entity.setGender(vo.getGender());
        entity.setEnabled(vo.getEnabled());

        entity = personRepository.save(entity);
        vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID!")));
        
        personRepository.delete(entity);
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        Person entity = personRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException(String.format("No records found this ID!", id)));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id) {

        logger.info("Disabling one person!");

        personRepository.personDisable(id);

        Person entity = personRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException(String.format("No records found this ID!", id)));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        List<PersonVO> list = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);

        list.stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return list;
    }

}

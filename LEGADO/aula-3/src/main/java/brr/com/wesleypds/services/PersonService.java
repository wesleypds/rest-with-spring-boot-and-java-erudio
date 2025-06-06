package brr.com.wesleypds.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brr.com.wesleypds.controllers.exceptions.ResourceNotFoundException;
import brr.com.wesleypds.data.vo.PersonVO;
import brr.com.wesleypds.mapper.DozerMapper;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.repositories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO create(PersonVO vo) {

        logger.info("Creating one person!");

        Person entity = personRepository.save(DozerMapper.parseObject(vo, Person.class));
        vo = DozerMapper.parseObject(entity, PersonVO.class);

        return vo;
    }

    public PersonVO update(PersonVO vo) {

        logger.info("Updating one person!");

        Person entity = personRepository.findById(vo.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID!")));

        entity.setFirstName(vo.getFirstName());
        entity.setLastName(vo.getLastName());
        entity.setAddress(vo.getAddress());
        entity.setGender(vo.getGender());

        entity = personRepository.save(entity);
        vo = DozerMapper.parseObject(entity, PersonVO.class);

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

        return vo;
    }

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        List<PersonVO> list = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);

        return list;
    }

}

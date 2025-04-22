package curso.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.spring.boot.model.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}

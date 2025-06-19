package curso.spring.boot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import curso.spring.boot.model.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM PersonEntity p WHERE p.firstName LIKE LOWER(CONCAT ('%',:firstName,'%'))")
    Page<PersonEntity> findPeopleByName(@Param("firstName") String firstName, Pageable pageable);
}

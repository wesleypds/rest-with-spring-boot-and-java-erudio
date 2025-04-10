package brr.com.wesleypds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brr.com.wesleypds.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p p.enabled = false WHERE p.id =: id")
    void personDisable(@Param("id") Long id);

}

package brr.com.wesleypds.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import brr.com.wesleypds.models.v1.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}

package brr.com.wesleypds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import brr.com.wesleypds.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}

package curso.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.spring.boot.model.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

}

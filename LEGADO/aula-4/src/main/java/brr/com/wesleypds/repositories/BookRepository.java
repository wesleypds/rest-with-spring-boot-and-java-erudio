package brr.com.wesleypds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import brr.com.wesleypds.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}

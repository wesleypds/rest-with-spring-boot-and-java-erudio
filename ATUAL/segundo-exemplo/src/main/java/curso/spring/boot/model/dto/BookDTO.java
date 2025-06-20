package curso.spring.boot.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "books")
public class BookDTO extends GenericDTO<BookDTO> {

    private String author;

    private LocalDateTime launchDate;

    private BigDecimal price;

    private String title;

    public BookDTO() {super();}

    public BookDTO(Long id, String author, LocalDateTime launchDate, BigDecimal price, String title) {
        this.author = author;
        this.launchDate = launchDate;
        this.price = price;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

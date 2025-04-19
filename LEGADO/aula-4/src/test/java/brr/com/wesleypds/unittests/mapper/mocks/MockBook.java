package brr.com.wesleypds.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import brr.com.wesleypds.data.vo.BookVO;
import brr.com.wesleypds.models.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    @SuppressWarnings("deprecation")
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date(2025, 03, 28));
        book.setPrice(10.0);
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

    @SuppressWarnings("deprecation")
    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date(2025, 03, 28));
        book.setPrice(10.0);
        book.setKey(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

}

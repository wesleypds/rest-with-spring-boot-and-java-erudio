package brr.com.wesleypds.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import brr.com.wesleypds.data.vo.BookVO;
import brr.com.wesleypds.data.vo.PersonVO;
import brr.com.wesleypds.mapper.DozerMapper;
import brr.com.wesleypds.models.Book;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.unittests.mapper.mocks.MockBook;
import brr.com.wesleypds.unittests.mapper.mocks.MockPerson;

public class DozerConverterTest {
    
    MockPerson inputMockPerson;

    MockBook inputMockBook;

    @BeforeEach
    public void setUp() {
        inputMockPerson = new MockPerson();
        inputMockBook = new MockBook();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void parseEntityToVOTest() {
        // PersonVO
        PersonVO outputPersonVO = DozerMapper.parseObject(inputMockPerson.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), outputPersonVO.getKey());
        assertEquals("First Name Test0", outputPersonVO.getFirstName());
        assertEquals("Last Name Test0", outputPersonVO.getLastName());
        assertEquals("Addres Test0", outputPersonVO.getAddress());
        assertEquals("Male", outputPersonVO.getGender());

        // BookVO
        BookVO outputBookVO = DozerMapper.parseObject(inputMockBook.mockEntity(), BookVO.class);
        assertEquals(Long.valueOf(0L), outputBookVO.getKey());
        assertEquals("Author Test0", outputBookVO.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookVO.getLaunchDate());
        assertEquals(10.0, outputBookVO.getPrice());
        assertEquals("Title Test0", outputBookVO.getTitle());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void parseEntityListToVOListTest() {
        // List PersonVO
        List<PersonVO> outputPersonList = DozerMapper.parseListObjects(inputMockPerson.mockEntityList(), PersonVO.class);
        PersonVO outputPersonZero = outputPersonList.get(0);
        
        assertEquals(Long.valueOf(0L), outputPersonZero.getKey());
        assertEquals("First Name Test0", outputPersonZero.getFirstName());
        assertEquals("Last Name Test0", outputPersonZero.getLastName());
        assertEquals("Addres Test0", outputPersonZero.getAddress());
        assertEquals("Male", outputPersonZero.getGender());
        
        PersonVO outputPersonSeven = outputPersonList.get(7);
        
        assertEquals(Long.valueOf(7L), outputPersonSeven.getKey());
        assertEquals("First Name Test7", outputPersonSeven.getFirstName());
        assertEquals("Last Name Test7", outputPersonSeven.getLastName());
        assertEquals("Addres Test7", outputPersonSeven.getAddress());
        assertEquals("Female", outputPersonSeven.getGender());
        
        PersonVO outputPersonTwelve = outputPersonList.get(12);
        
        assertEquals(Long.valueOf(12L), outputPersonTwelve.getKey());
        assertEquals("First Name Test12", outputPersonTwelve.getFirstName());
        assertEquals("Last Name Test12", outputPersonTwelve.getLastName());
        assertEquals("Addres Test12", outputPersonTwelve.getAddress());
        assertEquals("Male", outputPersonTwelve.getGender());

        // List BooKVO
        List<BookVO> outputBookList = DozerMapper.parseListObjects(inputMockBook.mockEntityList(), BookVO.class);
        BookVO outputBookZero = outputBookList.get(0);

        assertEquals(Long.valueOf(0L), outputBookZero.getKey());
        assertEquals("Author Test0", outputBookZero.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookZero.getLaunchDate());
        assertEquals(10.0, outputBookZero.getPrice());
        assertEquals("Title Test0", outputBookZero.getTitle());
        
        BookVO outputBookSeven = outputBookList.get(7);
        
        assertEquals(Long.valueOf(7L), outputBookSeven.getKey());
        assertEquals("Author Test7", outputBookSeven.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookSeven.getLaunchDate());
        assertEquals(10.0, outputBookSeven.getPrice());
        assertEquals("Title Test7", outputBookSeven.getTitle());
        
        BookVO outputBookTwelve = outputBookList.get(12);
        
        assertEquals(Long.valueOf(12L), outputBookTwelve.getKey());
        assertEquals("Author Test12", outputBookTwelve.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookTwelve.getLaunchDate());
        assertEquals(10.0, outputBookTwelve.getPrice());
        assertEquals("Title Test12", outputBookTwelve.getTitle());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void parseVOToEntityTest() {
        // Person
        Person output = DozerMapper.parseObject(inputMockPerson.mockVO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Addres Test0", output.getAddress());
        assertEquals("Male", output.getGender());

        // Book
        Book outputBook = DozerMapper.parseObject(inputMockBook.mockVO(), Book.class);
        assertEquals(Long.valueOf(0L), outputBook.getId());
        assertEquals("Author Test0", outputBook.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBook.getLaunchDate());
        assertEquals(10.0, outputBook.getPrice());
        assertEquals("Title Test0", outputBook.getTitle());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void parserVOListToEntityListTest() {
        // List Person
        List<Person> outputPersonList = DozerMapper.parseListObjects(inputMockPerson.mockVOList(), Person.class);
        Person outputPersonZero = outputPersonList.get(0);
        
        assertEquals(Long.valueOf(0L), outputPersonZero.getId());
        assertEquals("First Name Test0", outputPersonZero.getFirstName());
        assertEquals("Last Name Test0", outputPersonZero.getLastName());
        assertEquals("Addres Test0", outputPersonZero.getAddress());
        assertEquals("Male", outputPersonZero.getGender());
        
        Person outputPersonSeven = outputPersonList.get(7);
        
        assertEquals(Long.valueOf(7L), outputPersonSeven.getId());
        assertEquals("First Name Test7", outputPersonSeven.getFirstName());
        assertEquals("Last Name Test7", outputPersonSeven.getLastName());
        assertEquals("Addres Test7", outputPersonSeven.getAddress());
        assertEquals("Female", outputPersonSeven.getGender());
        
        Person outputPersonTwelve = outputPersonList.get(12);
        
        assertEquals(Long.valueOf(12L), outputPersonTwelve.getId());
        assertEquals("First Name Test12", outputPersonTwelve.getFirstName());
        assertEquals("Last Name Test12", outputPersonTwelve.getLastName());
        assertEquals("Addres Test12", outputPersonTwelve.getAddress());
        assertEquals("Male", outputPersonTwelve.getGender());

        // List BooK
        List<Book> outputBookList = DozerMapper.parseListObjects(inputMockBook.mockVOList(), Book.class);
        Book outputBookZero = outputBookList.get(0);

        assertEquals(Long.valueOf(0L), outputBookZero.getId());
        assertEquals("Author Test0", outputBookZero.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookZero.getLaunchDate());
        assertEquals(10.0, outputBookZero.getPrice());
        assertEquals("Title Test0", outputBookZero.getTitle());
        
        Book outputBookSeven = outputBookList.get(7);
        
        assertEquals(Long.valueOf(7L), outputBookSeven.getId());
        assertEquals("Author Test7", outputBookSeven.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookSeven.getLaunchDate());
        assertEquals(10.0, outputBookSeven.getPrice());
        assertEquals("Title Test7", outputBookSeven.getTitle());
        
        Book outputBookTwelve = outputBookList.get(12);
        
        assertEquals(Long.valueOf(12L), outputBookTwelve.getId());
        assertEquals("Author Test12", outputBookTwelve.getAuthor());
        assertEquals(new Date(2025, 03, 28), outputBookTwelve.getLaunchDate());
        assertEquals(10.0, outputBookTwelve.getPrice());
        assertEquals("Title Test12", outputBookTwelve.getTitle());
    }
}

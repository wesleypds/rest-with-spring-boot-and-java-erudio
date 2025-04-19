package brr.com.wesleypds.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import brr.com.wesleypds.integrationtests.vo.BookVO;

public class WrapperBookVOXmlAndYaml implements Serializable {

    @JsonProperty("content")
    private List<BookVO> books;

    public WrapperBookVOXmlAndYaml() {}

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((books == null) ? 0 : books.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
            WrapperBookVOXmlAndYaml other = (WrapperBookVOXmlAndYaml) obj;
        if (books == null) {
            if (other.books != null)
                return false;
        } else if (!books.equals(other.books))
            return false;
        return true;
    }

}

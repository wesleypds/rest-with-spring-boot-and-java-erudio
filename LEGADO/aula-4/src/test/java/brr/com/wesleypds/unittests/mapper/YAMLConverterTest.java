package brr.com.wesleypds.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import brr.com.wesleypds.integrationtests.controller.withyaml.mapper.YAMLMapper;
import brr.com.wesleypds.integrationtests.vo.PersonVO;
import io.restassured.common.mapper.DataToDeserialize;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class YAMLConverterTest {

    private final YAMLMapper yamlMapper = new YAMLMapper();

    @Test
    void testDeserializeSingleObject() {
        String yaml = "id: 1\nfirstName: João\nlastName: Silva";

        ObjectMapperDeserializationContext context = new FakeDeserializationContext(yaml, PersonVO.class);
        PersonVO person = (PersonVO) yamlMapper.deserialize(context);

        assertNotNull(person);
        assertEquals(1L, person.getId());
        assertEquals("João", person.getFirstName());
        assertEquals("Silva", person.getLastName());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testDeserializeList() {
        String yaml = "- id: 1\n  firstName: João\n  lastName: Silva\n" +
                      "- id: 2\n  firstName: Maria\n  lastName: Souza";

        Type listType = new TypeReference<List<PersonVO>>() {}.getType();
        ObjectMapperDeserializationContext context = new FakeDeserializationContext(yaml, listType);

        List<PersonVO> list = (List<PersonVO>) yamlMapper.deserialize(context);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Maria", list.get(1).getFirstName());
    }

    @Test
    void testSerializeObject() {
        PersonVO person = new PersonVO();
        person.setId(3L);
        person.setFirstName("Carlos");
        person.setLastName("Pereira");

        ObjectMapperSerializationContext context = new FakeSerializationContext(person);
        String yaml = (String) yamlMapper.serialize(context);

        assertTrue(yaml.contains("Carlos"));
        assertTrue(yaml.contains("Pereira"));
        assertTrue(yaml.contains("3"));
    }

    // 👇 Classes fake para simular os contextos usados pelo RestAssured
    static class FakeDeserializationContext implements ObjectMapperDeserializationContext {
        private final String data;
        private final Type type;

        FakeDeserializationContext(String data, Type type) {
            this.data = data;
            this.type = type;
        }

        @Override
        public DataToDeserialize getDataToDeserialize() {
            return new DataToDeserialize() {
                @Override
                public String asString() {
                    return data;
                }

                @Override
                public byte[] asByteArray() {
                    return data.getBytes(); // pode ser necessário dependendo do mapper
                }

                @Override
                public InputStream asInputStream() {
                    return new ByteArrayInputStream(data.getBytes());
                }
            };
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public String getCharset() {
            throw new UnsupportedOperationException("Unimplemented method 'getCharset'");
        }

        @Override
        public String getContentType() {
            throw new UnsupportedOperationException("Unimplemented method 'getContentType'");
        }
    }

    static class FakeSerializationContext implements ObjectMapperSerializationContext {
        private final Object object;

        FakeSerializationContext(Object object) {
            this.object = object;
        }

        @Override
        public Object getObjectToSerialize() {
            return object;
        }

        @Override
        public <T> T getObjectToSerializeAs(Class<T> expectedType) {
            throw new UnsupportedOperationException("Unimplemented method 'getObjectToSerializeAs'");
        }

        @Override
        public String getContentType() {
            throw new UnsupportedOperationException("Unimplemented method 'getContentType'");
        }

        @Override
        public String getCharset() {
            throw new UnsupportedOperationException("Unimplemented method 'getCharset'");
        }
    }
}

package curso.spring.boot.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Component
public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public <O, D> List<D> parseListObject(List<O> origin, Class<D> destination) {
        List<D> destinationList = new ArrayList<>();

        for (O o : origin) {
            destinationList.add(mapper.map(o, destination));
        }

        return destinationList;
    }

}

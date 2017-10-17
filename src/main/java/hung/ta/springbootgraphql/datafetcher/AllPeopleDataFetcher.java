package hung.ta.springbootgraphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hung.ta.springbootgraphql.model.Person;
import hung.ta.springbootgraphql.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllPeopleDataFetcher implements DataFetcher<List<Person>> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> get(DataFetchingEnvironment evn) {
        return personRepository.findAll();
    }
}

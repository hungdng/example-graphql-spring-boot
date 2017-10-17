package hung.ta.springbootgraphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hung.ta.springbootgraphql.model.Person;
import hung.ta.springbootgraphql.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PersonDataFetcher implements DataFetcher<Person> {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Person get(DataFetchingEnvironment env) {
        Map arguments = env.getArguments();
        return personRepository.findOne((String)arguments.get("id"));
    }
}

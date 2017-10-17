package hung.ta.springbootgraphql.loader;

import hung.ta.springbootgraphql.model.Person;
import hung.ta.springbootgraphql.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PersonLoader {

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void loadPerson(){
        personRepository.save(new Person("1", "Adam", "Lim", 30));
        personRepository.save(new Person("2", "Huy", "DP", 30));
        personRepository.save(new Person("3", "Khoa", "TA", 30));
        personRepository.save(new Person("4", "Viet", "TX", 30));
        personRepository.save(new Person("5", "Ngoc", "LH", 30));
    }
}

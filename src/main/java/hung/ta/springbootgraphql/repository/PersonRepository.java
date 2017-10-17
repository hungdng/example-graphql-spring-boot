package hung.ta.springbootgraphql.repository;

import hung.ta.springbootgraphql.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PersonRepository extends JpaRepository<Person, String> {
}
